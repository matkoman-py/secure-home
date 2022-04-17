package siitnocu.bezbednost.dto;

import siitnocu.bezbednost.data.CsrInfo;

public class CsrDTO {
    private String dn;
    private String on;
    private String ou;
    private String city;
    private String email;
    private String reason;

    public CsrDTO(String dn, String on, String ou, String city, String email, String reason) {
        this.dn = dn;
        this.on = on;
        this.ou = ou;
        this.city = city;
        this.email = email;
        this.reason = reason;
    }

    public CsrDTO() {
    }

    public CsrDTO(CsrInfo csrInfo) {
        this.dn = csrInfo.getDomainName();
        this.on = csrInfo.getOrganizationName();
        this.ou = csrInfo.getOrganizationUnit();
        this.city = csrInfo.getCity();
        this.email = csrInfo.getEmail();
        this.reason = csrInfo.getReason();
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
