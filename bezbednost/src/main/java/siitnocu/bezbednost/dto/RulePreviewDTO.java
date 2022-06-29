package siitnocu.bezbednost.dto;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import siitnocu.bezbednost.data.Device;
import siitnocu.bezbednost.data.Rule;

public class RulePreviewDTO {
	
	private Long id;

    private String device;
	
    private double lowerValue;
	
    private double upperValue;

	public RulePreviewDTO() {
		super();
	}

	public RulePreviewDTO(Long id, String device, double lowerValue, double upperValue) {
		super();
		this.id = id;
		this.device = device;
		this.lowerValue = lowerValue;
		this.upperValue = upperValue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
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

	public RulePreviewDTO(Rule rule) {
		this.id = rule.getId();
		this.device = rule.getDevice().getPathToFile();
		this.lowerValue = rule.getLowerValue();
		this.upperValue = rule.getUpperValue();
	}
	
	
    
    

}
