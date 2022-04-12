package siitnocu.bezbednost.services;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.bouncycastle.operator.OperatorCreationException;

import siitnocu.bezbednost.utils.CertificateInfo;

public interface ITestService {
	
	String generateCSR(CertificateInfo csrInfo) throws NoSuchAlgorithmException, OperatorCreationException, IOException, KeyStoreException, CertificateException;

	CertificateInfo decodeCSR(String csrString);
}
