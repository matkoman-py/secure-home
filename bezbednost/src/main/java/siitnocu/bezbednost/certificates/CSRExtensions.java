package siitnocu.bezbednost.certificates;

import extensions.CertificateExtensions;

public class CSRExtensions {
	private String csr;
	private CertificateExtensions extensions;
	public CSRExtensions(String csr, CertificateExtensions extensions) {
		super();
		this.csr = csr;
		this.extensions = extensions;
	}
	public CSRExtensions() {
		super();
	}
	public String getCsr() {
		return csr;
	}
	public void setCsr(String csr) {
		this.csr = csr;
	}
	public CertificateExtensions getExtensions() {
		return extensions;
	}
	public void setExtensions(CertificateExtensions extensions) {
		this.extensions = extensions;
	}
	
	
}
