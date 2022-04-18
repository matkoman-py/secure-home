package siitnocu.bezbednost.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CertificateDTO {
	private String entryName;
	private String algorithm;
	private int keySize;
	
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date expirationDate;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date startDate;
	private String subjectName;
	private String issuerName;
	private int version;
	private int serialNo;
	private String format;
	private boolean isValid;
	private String reason;
	
	
	
	
	public CertificateDTO(String entryName, String algorithm, int keySize, Date expirationDate, Date startDate,
			String subjectName, String issuerName, int version, int serialNo, String format,boolean isValid, String reason) {
		super();
		this.entryName = entryName;
		this.algorithm = algorithm;
		this.keySize = keySize;
		this.expirationDate = expirationDate;
		this.startDate = startDate;
		this.subjectName = subjectName;
		this.issuerName = issuerName;
		this.version = version;
		this.serialNo = serialNo;
		this.format = format;
		this.isValid = isValid;
		this.reason = reason;
	}
	public CertificateDTO() {
		super();
	}
	public String getEntryName() {
		return entryName;
	}
	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}
	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	public int getKeySize() {
		return keySize;
	}
	public void setKeySize(int keySize) {
		this.keySize = keySize;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getIssuerName() {
		return issuerName;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	
	
	
	
	
	
	
	
	

}
