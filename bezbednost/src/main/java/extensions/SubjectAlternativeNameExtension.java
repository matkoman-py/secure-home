package extensions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;

public class SubjectAlternativeNameExtension {
	Map<Integer, String> names;

	public SubjectAlternativeNameExtension(Map<Integer, String> names) {
		super();
		this.names = names;
	}

	public SubjectAlternativeNameExtension() {
		super();
		names = new HashMap<Integer, String>();
	}

	public Map<Integer, String> getNames() {
		return names;
	}

	public void setNames(Map<Integer, String> names) {
		this.names = names;
	}
	
	private GeneralName mapToGeneralName(Integer tag, String value) {
		GeneralName gn = null;
		switch (tag) {
		case 1:
			gn = new GeneralName(GeneralName.rfc822Name, value);
			break;
		case 2:
			gn = new GeneralName(GeneralName.dNSName, value);
			break;
		case 4:
			gn = new GeneralName(GeneralName.directoryName, value);
			break;
		case 6:
			gn = new GeneralName(GeneralName.uniformResourceIdentifier, value);
			break;
		case 7:
			gn = new GeneralName(GeneralName.iPAddress, value);
			break;
		default:
			gn = new GeneralName(GeneralName.registeredID, value);
			break;
		}
		return gn;
	}
	
	public GeneralNames transform() {
		List<GeneralName> generalNames = new ArrayList<GeneralName>();
		for (Map.Entry<Integer,String> entry : names.entrySet()) {
			GeneralName name = mapToGeneralName(entry.getKey(), entry.getValue());
			generalNames.add(name);
		}
		return new GeneralNames((GeneralName[]) generalNames.toArray(new GeneralName[names.size()]));
	}
}
	
	

