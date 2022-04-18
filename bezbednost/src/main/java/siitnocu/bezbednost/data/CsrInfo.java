package siitnocu.bezbednost.data;

import javax.persistence.*;

@Entity
public class CsrInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String domainName;
    @Column(nullable = false)
    private String organizationName;
    @Column(nullable = false)
    private String organizationUnit;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String email;
    @Column(columnDefinition="text", nullable = false)
    private String csrString;
    @Column(columnDefinition="text", nullable = true)
    private String reason;

    public CsrInfo(String domainName, String organizationName, String organizationUnit, String city, String state, String country, String email, String csrString, String reason) {
        this.domainName = domainName;
        this.organizationName = organizationName;
        this.organizationUnit = organizationUnit;
        this.city = city;
        this.state = state;
        this.country = country;
        this.email = email;
        this.csrString = csrString;
        this.reason = reason;
    }

    public CsrInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCsrString() {
        return csrString;
    }

    public void setCsrString(String csrString) {
        this.csrString = csrString;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
