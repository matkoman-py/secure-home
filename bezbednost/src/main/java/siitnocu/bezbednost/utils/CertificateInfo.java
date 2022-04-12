package siitnocu.bezbednost.utils;

public class CertificateInfo {
	
	private String domainName;
	private String organizationName;
	private String organizationUnit;
	private String city;
	private String state;
	private String country;
	private String email;
	public CertificateInfo(String domainName, String organizationName, String organizationUnit, String city,
			String state, String country, String email) {
		super();
		this.domainName = domainName;
		this.organizationName = organizationName;
		this.organizationUnit = organizationUnit;
		this.city = city;
		this.state = state;
		this.country = country;
		this.email = email;
	}
	public CertificateInfo() {
		super();
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getOrganizationUnit() {
		return organizationUnit;
	}
	public void setOrganizationUnit(String organizationUnit) {
		this.organizationUnit = organizationUnit;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	


}
