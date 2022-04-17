package extensions;

public class BasicConstraintsExtension {
	private boolean ca;
	public BasicConstraintsExtension(boolean isCa) {

		this.ca = isCa;
	}
	public BasicConstraintsExtension() {
		super();
	}
	public boolean isCa() {
		return ca;
	}
	public void setCa(boolean isCa) {
		this.ca = isCa;
	}
	
	

}
