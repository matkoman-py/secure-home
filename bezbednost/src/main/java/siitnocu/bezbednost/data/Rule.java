package siitnocu.bezbednost.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RULES")
public class Rule {
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    private Device device;
	
	@Column(name = "lower_value")
    private double lowerValue;
	
	@Column(name = "upper_value")
    private double upperValue;

	public Rule(Long id, Device device, double lowerValue, double upperValue) {
		super();
		this.id = id;
		this.device = device;
		this.lowerValue = lowerValue;
		this.upperValue = upperValue;
	}
	
	
	





	public Rule(Device device, double lowerValue, double upperValue) {
		super();
		this.device = device;
		this.lowerValue = lowerValue;
		this.upperValue = upperValue;
	}



	public Rule() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		device.setRule(this);
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
	
	
	
	
	
	
	
	
	

}
