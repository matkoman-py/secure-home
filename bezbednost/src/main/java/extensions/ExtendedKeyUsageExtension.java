package extensions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bouncycastle.asn1.x509.KeyPurposeId;

public class ExtendedKeyUsageExtension {
	private List<Integer> keyPurposes;
	public ExtendedKeyUsageExtension(List<Integer> keyPurporses) {
		super();
		this.keyPurposes = keyPurporses;
	}
	public ExtendedKeyUsageExtension() {
		keyPurposes = new ArrayList<Integer>();
	}
	

	public List<Integer> getKeyPurposes() {
		return keyPurposes;
	}
	public void setKeyPurposes(List<Integer> keyPurposes) {
		this.keyPurposes = keyPurposes;
	}
	public KeyPurposeId mapToKeyPurpose(int value) {
		KeyPurposeId kp = null;
		switch (value) {
		case 1:
			kp = KeyPurposeId.anyExtendedKeyUsage;
			break;
		case 2:
			kp = KeyPurposeId.id_kp_codeSigning;
			break;
		case 3:
			kp = KeyPurposeId.id_kp_emailProtection;
			break;
		case 4:
			kp = KeyPurposeId.id_kp_ipsecEndSystem;
			break;
		case 5:
			kp = KeyPurposeId.id_kp_ipsecTunnel;
			break;
		case 6:
			kp = KeyPurposeId.id_kp_ipsecUser;
			break;
		case 7:
			kp = KeyPurposeId.id_kp_OCSPSigning;
			break;		
		case 8:
			kp = KeyPurposeId.id_kp_smartcardlogon;
			break;
		case 9:
			kp = KeyPurposeId.id_kp_timeStamping;
			break;
		case 10:
			kp = KeyPurposeId.id_kp_clientAuth;
			break;
		case 11:
			kp = KeyPurposeId.id_kp_serverAuth;
			break;
		default:
			kp = null;
			break;
		}
		return kp;
	}
	
	public KeyPurposeId[] transform() {
		
		return (KeyPurposeId[]) keyPurposes.stream().map(kp -> mapToKeyPurpose(kp)).collect(Collectors.toList()).toArray(new KeyPurposeId[keyPurposes.size()]);
	}
	

}
