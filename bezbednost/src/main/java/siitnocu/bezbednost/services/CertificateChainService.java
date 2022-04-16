package siitnocu.bezbednost.services;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.springframework.stereotype.Service;
import javax.security.auth.x500.X500Principal;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class CertificateChainService {
	
    public static final boolean ALLOW_LOG_SELF_SIGN_TESTS = false;
    
    public static List<X509Certificate> buildChainFor(PublicKey key, Collection<X509Certificate> certs) {
        final List<X509Certificate> chain = new ArrayList<X509Certificate>(certs.size());

        final X509Certificate subject = getCertificateFor(key, certs);

        if (subject == null)
            throw new IllegalArgumentException(
                    "Cannot find X509Certificate which corresponds to "
                            + key);

        chain.add(subject);

        // Keep going until we find a root certificate (or until the chain can't be continued)
        {
            X509Certificate old = null;
            X509Certificate current = subject;
            while (current != null && (old == null || !old.equals(current))
                    && !isSelfSigned(current)) {
                old = current;
                current = getIssuer(current, certs);

                if (current != null)
                    chain.add(current);
                else {
                    throw new IllegalArgumentException(
                            "Could not determine issuer for certificate: "
                                    + old.getSubjectX500Principal()
                                    + ". Please ensure certificate list contains all certificates back to the CA's self-signed root!");
                }

                if (chain.size() > certs.size()) {
                    throw new IllegalStateException(
                            "Chain build failed: too many certs in chain (greater than number of input certs)! Chain: "
                                    + Arrays.toString(getPrincipals(chain)));
                }
            }
        }

        return normaliseChain(chain);
    }
    
    public static List<X509Certificate> buildChainFor(KeyPair keypair,
            Collection<X509Certificate> certs) {
        return buildChainFor(keypair.getPublic(), certs);
    }
    
    public static X509Certificate getCertificateFor(PublicKey publicKey,
            Collection<X509Certificate> certs) {
        // Search through the certs until we find the public key we're looking for
        for (X509Certificate cert : certs) {
            if (cert.getPublicKey().equals(publicKey))
                return cert;
        }

        return null;
    }
    /**
     * Determines if a certificate is a self signed certificate
     *
     * @param certificate
     *       the certificate to test
     *
     * @return true if the certificate is self-signed, otherwise false if the certificate was not self-signed or the certificate
     * signature could not be verified
     */
    public static boolean isSelfSigned(X509Certificate certificate) {
        return isSignedBy(certificate, certificate.getPublicKey());
    }
    
    public static X509Certificate getIssuer(X509Certificate subject, Collection<X509Certificate> certs) {
    	
        for (X509Certificate cert : certs) {

            X500Name x500nameSubject = new X500Name( cert.getSubjectX500Principal().getName() );
            RDN cnSubject = x500nameSubject.getRDNs(BCStyle.CN)[0];

            X500Name x500nameIssuer = new X500Name( subject.getIssuerX500Principal().getName() );
            RDN cnIssuer = x500nameIssuer.getRDNs(BCStyle.CN)[0];

            System.out.println("ISSUER " + IETFUtils.valueToString(cnIssuer.getFirst().getValue()));
            System.out.println("SUBJECT U LISTI "  + IETFUtils.valueToString(cnSubject.getFirst().getValue()));
            if (IETFUtils.valueToString(cnSubject.getFirst().getValue()).equals(
                    IETFUtils.valueToString(cnIssuer.getFirst().getValue()))) {
                return cert;
            }
        }

        return null;
    }
    
    public static X500Principal[] getPrincipals(List<X509Certificate> chain) {
        if (chain.contains(null))
            throw new IllegalArgumentException(
                    "Certificate chain contains null!");

        X500Principal[] array = new X500Principal[chain.size()];

        for (int i = 0; i < array.length; i++)
            array[i] = chain.get(i).getSubjectX500Principal();

        return array;
    }
    /**
     * Take a chain and return a (Read-only) chain with the root certificate as the first entry
     *
     * @param chain
     *       a chain with the certificates in order (either leading away from root or leading towards root)
     *
     * @return a read-only chain leading away from the root certificate
     *
     * @throws IllegalArgumentException
     *       if the chain is null or empty
     */
    public static List<X509Certificate> normaliseChain(
            List<X509Certificate> chain) {
        return toRootFirst(chain);
    }
    
    @SuppressWarnings("unused")
    public static boolean isSignedBy(X509Certificate subject,
            PublicKey signer) {
        try {
            subject.verify(signer);

            // if verify does not throw an exception then it's a self-signed certificate
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    /**
     * Take a chain and return a (Read-only) chain with the root certificate as the first entry
     *
     * @param chain
     *       a chain with the certificates in order (either leading away from root or leading towards root)
     *
     * @return a read-only chain leading away from the root certificate
     *
     * @throws IllegalArgumentException
     *       if the chain is null or empty
     */
    public static List<X509Certificate> toRootFirst(
            List<X509Certificate> chain) {
        if (chain == null || chain.isEmpty())
            throw new IllegalArgumentException(
                    "Must provide a chain that is non-null and non-empty");

        final List<X509Certificate> out;
        // Sort the list so the root certificate comes first
        if (!isSelfSigned(chain.get(0))) {
            // Copy the chain List so we can modify it
            out = new ArrayList<X509Certificate>(chain);

            Collections.reverse(out);

            // If, even when reversed, the chain doesn't have a root at the start then the chain's invalid
            if (!isSelfSigned(out.get(0))) {
                throw new IllegalArgumentException(
                        "Neither end of the certificate chain has a Root! "
                                + chain);
            }
        } else {
            out = chain;
        }

        return out;
    }
}
