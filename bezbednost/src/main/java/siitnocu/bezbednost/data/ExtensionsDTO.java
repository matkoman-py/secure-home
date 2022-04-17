package siitnocu.bezbednost.data;

import java.util.List;

public class ExtensionsDTO {

	private List<String> extendedKeyUsages;
	private List<String> keyUsages;
	private List<String> subjectAlternativeNames;
	private int ca;
	private String aki;
	private String ski;
	private boolean ekucrit;
	private boolean kucrit; //
	private boolean sancrit; //
	private boolean bccrit; //
	private boolean akicrit;
	private boolean skicrit; //
	public ExtensionsDTO(List<String> extendedKeyUsages, List<String> keyUsages, List<String> subjectAlternativeNames,
			int ca, String aki, String ski, boolean ekucrit, boolean kucrit, boolean sancrit, boolean bccrit,
			boolean akicrit, boolean skicrit) {
		super();
		this.extendedKeyUsages = extendedKeyUsages;
		this.keyUsages = keyUsages;
		this.subjectAlternativeNames = subjectAlternativeNames;
		this.ca = ca;
		this.aki = aki;
		this.ski = ski;
		this.ekucrit = ekucrit;
		this.kucrit = kucrit;
		this.sancrit = sancrit;
		this.bccrit = bccrit;
		this.akicrit = akicrit;
		this.skicrit = skicrit;
	}
	public ExtensionsDTO() {
		super();
	}
	public List<String> getExtendedKeyUsages() {
		return extendedKeyUsages;
	}
	public void setExtendedKeyUsages(List<String> extendedKeyUsages) {
		this.extendedKeyUsages = extendedKeyUsages;
	}
	public List<String> getKeyUsages() {
		return keyUsages;
	}
	public void setKeyUsages(List<String> keyUsages) {
		this.keyUsages = keyUsages;
	}
	public List<String> getSubjectAlternativeNames() {
		return subjectAlternativeNames;
	}
	public void setSubjectAlternativeNames(List<String> subjectAlternativeNames) {
		this.subjectAlternativeNames = subjectAlternativeNames;
	}
	public int getCa() {
		return ca;
	}
	public void setCa(int ca) {
		this.ca = ca;
	}
	public String getAki() {
		return aki;
	}
	public void setAki(String aki) {
		this.aki = aki;
	}
	public String getSki() {
		return ski;
	}
	public void setSki(String ski) {
		this.ski = ski;
	}
	public boolean isEkucrit() {
		return ekucrit;
	}
	public void setEkucrit(boolean ekucrit) {
		this.ekucrit = ekucrit;
	}
	public boolean isKucrit() {
		return kucrit;
	}
	public void setKucrit(boolean kucrit) {
		this.kucrit = kucrit;
	}
	public boolean isSancrit() {
		return sancrit;
	}
	public void setSancrit(boolean sancrit) {
		this.sancrit = sancrit;
	}
	public boolean isBccrit() {
		return bccrit;
	}
	public void setBccrit(boolean bccrit) {
		this.bccrit = bccrit;
	}
	public boolean isAkicrit() {
		return akicrit;
	}
	public void setAkicrit(boolean akicrit) {
		this.akicrit = akicrit;
	}
	public boolean isSkicrit() {
		return skicrit;
	}
	public void setSkicrit(boolean skicrit) {
		this.skicrit = skicrit;
	}
	
	
	
	
	
}
