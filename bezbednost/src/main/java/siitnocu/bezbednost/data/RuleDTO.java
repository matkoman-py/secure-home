package siitnocu.bezbednost.data;


public class RuleDTO {
	
	private Long deviceId;
    private double lowerValue;
    private double upperValue;
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public double getLowerValue() {
		return lowerValue;
	}
	public void setLowerValue(double lowerValue) {
		this.lowerValue = lowerValue;
	}
	public double getUpperValue() {
		return upperValue;
	}
	public void setUpperValue(double upperValue) {
		this.upperValue = upperValue;
	}
	public RuleDTO(Long deviceId, double lowerValue, double upperValue) {
		super();
		this.deviceId = deviceId;
		this.lowerValue = lowerValue;
		this.upperValue = upperValue;
	}
	public RuleDTO() {
		super();
	}
    
    

}
