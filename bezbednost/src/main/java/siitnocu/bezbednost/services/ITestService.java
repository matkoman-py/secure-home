package siitnocu.bezbednost.services;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import org.bouncycastle.operator.OperatorCreationException;

import extensions.CertificateExtensions;
import siitnocu.bezbednost.certificates.CSRExtensions;
import siitnocu.bezbednost.data.SubjectData;
import siitnocu.bezbednost.utils.CertificateInfo;

public interface ITestService {
	
	String generateCSR(CertificateInfo csrInfo) throws NoSuchAlgorithmException, OperatorCreationException, IOException, KeyStoreException, CertificateException, NoSuchProviderException;

	SubjectData decodeCSR(String csrString) throws ParseException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, CertificateException, KeyStoreException, NoSuchProviderException;

    String signCSR(CSRExtensions csr, String alias) throws ParseException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, CertificateException, KeyStoreException, NoSuchProviderException;
}
