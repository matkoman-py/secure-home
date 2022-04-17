package extensions;

import java.util.HashSet;
import java.util.Set;

import org.bouncycastle.asn1.x509.KeyUsage;


public class KeyUsageExtension {
	private Set<Integer> keyUsages;
	public KeyUsageExtension(Set<Integer> keyUsages) {
		super();
		this.keyUsages = keyUsages;
	}
	public KeyUsageExtension() {
		keyUsages = new HashSet<Integer>();
	}

	public Set<Integer> getKeyUsages() {
		return keyUsages;
	}
	public void setKeyUsages(Set<Integer> keyUsages) {
		this.keyUsages = keyUsages;
	}
	
	public int calculateKeyUsages() {
		int number = 0;
		for (Integer keyUsage : keyUsages) {
			number |= keyUsage;
		}
		return number;
	}
}
