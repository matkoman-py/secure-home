package siitnocu.bezbednost.services;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import org.springframework.stereotype.Service;

@Service
public class KeyStoreWriterService {

    public void saveKeyStore(String fileName, char[] password) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            // ucitavamo podatke
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));
            ks.load(in, password);
            ks.store(new FileOutputStream(fileName), password);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    public void write(String fileName, String alias, PrivateKey privateKey, char[] password, Certificate[] certificate) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            // ucitavamo podatke
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));
            ks.load(in, password);
//            ks.setKeyEntry(alias, privateKey, password, new Certificate[]{certificate});
            ks.setKeyEntry(alias, privateKey, password, certificate);

            ks.store(new FileOutputStream(fileName), password);
        } catch (KeyStoreException | NoSuchProviderException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void deleteCertificate(String fileName, String alias, char[] password) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            // ucitavamo podatke
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));
            ks.load(in, password);
            ks.deleteEntry(alias);
            ks.store(new FileOutputStream(fileName), password);
        } catch (KeyStoreException | NoSuchProviderException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
