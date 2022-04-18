package extensions;

public class CertificateExtensions {

	private BasicConstraintsExtension basicConstraints;
	private ExtendedKeyUsageExtension extendedKeyUsage;
	private KeyUsageExtension keyUsage;
	private SubjectAlternativeNameExtension subjectAlternativeName;
	private boolean BCCrit;
	private boolean EKUCrit;
	private boolean KUECrit;
	private boolean SANCrit;
	private boolean SKICrit;
	private boolean AKICrit;
	private boolean AKIExt;
	private boolean SKIExt;
	
	
	public CertificateExtensions(BasicConstraintsExtension basicConstraints, ExtendedKeyUsageExtension extendedKeyUsage,
			KeyUsageExtension keyUsage, SubjectAlternativeNameExtension subjectAlternativeName, boolean bCCrit,
			boolean eKUCrit, boolean kUECrit, boolean sANCrit, boolean sKICrit, boolean aKICrit, boolean aKIExt,
			boolean sKIExt) {
		super();
		this.basicConstraints = basicConstraints;
		this.extendedKeyUsage = extendedKeyUsage;
		this.keyUsage = keyUsage;
		this.subjectAlternativeName = subjectAlternativeName;
		BCCrit = bCCrit;
		EKUCrit = eKUCrit;
		KUECrit = kUECrit;
		SANCrit = sANCrit;
		SKICrit = sKICrit;
		AKICrit = aKICrit;
		AKIExt = aKIExt;
		SKIExt = sKIExt;
	}
	public CertificateExtensions() {
		super();
	}
	public BasicConstraintsExtension getBasicConstraints() {
		return basicConstraints;
	}
	public void setBasicConstraints(BasicConstraintsExtension basicConstraints) {
		this.basicConstraints = basicConstraints;
	}
	public ExtendedKeyUsageExtension getExtendedKeyUsage() {
		return extendedKeyUsage;
	}
	public void setExtendedKeyUsage(ExtendedKeyUsageExtension extendedKeyUsage) {
		this.extendedKeyUsage = extendedKeyUsage;
	}
	public KeyUsageExtension getKeyUsage() {
		return keyUsage;
	}
	public void setKeyUsage(KeyUsageExtension keyUsage) {
		this.keyUsage = keyUsage;
	}
	public SubjectAlternativeNameExtension getSubjectAlternativeName() {
		return subjectAlternativeName;
	}
	public void setSubjectAlternativeName(SubjectAlternativeNameExtension subjectAlternativeName) {
		this.subjectAlternativeName = subjectAlternativeName;
	}
	public boolean isBCCrit() {
		return BCCrit;
	}
	public void setBCCrit(boolean isBCCrit) {
		this.BCCrit = isBCCrit;
	}
	public boolean isEKUCrit() {
		return EKUCrit;
	}
	public void setEKUCrit(boolean isEKUCrit) {
		this.EKUCrit = isEKUCrit;
	}
	public boolean isKUECrit() {
		return KUECrit;
	}
	public void setKUECrit(boolean isKUECrit) {
		this.KUECrit = isKUECrit;
	}
	public boolean isSANCrit() {
		return SANCrit;
	}
	public void setSANCrit(boolean isSANCrit) {
		this.SANCrit = isSANCrit;
	}
	public boolean isSKICrit() {
		return SKICrit;
	}
	public void setSKICrit(boolean isSKICrit) {
		this.SKICrit = isSKICrit;
	}
	public boolean isAKICrit() {
		return AKICrit;
	}
	public void setAKICrit(boolean isAKICrit) {
		this.AKICrit = isAKICrit;
	}
	public boolean isAKIExt() {
		return AKIExt;
	}
	public void setAKIExt(boolean aKIExt) {
		AKIExt = aKIExt;
	}
	public boolean isSKIExt() {
		return SKIExt;
	}
	public void setSKIExt(boolean sKIExt) {
		SKIExt = sKIExt;
	}

	@Override
	public String toString() {
		return "CertificateExtensions{" +
				"basicConstraints=" + basicConstraints +
				", extendedKeyUsage=" + extendedKeyUsage +
				", keyUsage=" + keyUsage +
				", subjectAlternativeName=" + subjectAlternativeName +
				", BCCrit=" + BCCrit +
				", EKUCrit=" + EKUCrit +
				", KUECrit=" + KUECrit +
				", SANCrit=" + SANCrit +
				", SKICrit=" + SKICrit +
				", AKICrit=" + AKICrit +
				", AKIExt=" + AKIExt +
				", SKIExt=" + SKIExt +
				'}';
	}
}
