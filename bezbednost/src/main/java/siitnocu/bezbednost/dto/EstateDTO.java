package siitnocu.bezbednost.dto;

import siitnocu.bezbednost.data.Estate;
import siitnocu.bezbednost.data.EstateType;

public class EstateDTO {

	private Long id;
	private String address;
	private EstateType estateType;
	private String description;
	public EstateDTO() {
		super();
	}
	public EstateDTO(String address, Long id,EstateType estateType, String description) {
		super();
		this.id = id;
		this.address = address;
		this.estateType = estateType;
		this.description = description;
	}
	public EstateDTO(Estate e) {
		super();
		this.id = e.getId();
		this.address = e.getAddress();
		this.estateType = e.getEstateType();
		this.description = e.getDesciption();
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public EstateType getEstateType() {
		return estateType;
	}
	public void setEstateType(EstateType estateType) {
		this.estateType = estateType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
