package siitnocu.bezbednost.certificates;

import extensions.CertificateExtensions;

public class CSRExtensions {
	private CertificateExtensions extensions;
	public CSRExtensions(CertificateExtensions extensions) {
		super();
		this.extensions = extensions;
	}
	public CSRExtensions() {
		super();
	}
	public CertificateExtensions getExtensions() {
		return extensions;
	}
	public void setExtensions(CertificateExtensions extensions) {
		this.extensions = extensions;
	}

	@Override
	public String toString() {
		return "CSRExtensions{" +
				"extensions=" + extensions +
				'}';
	}
}
