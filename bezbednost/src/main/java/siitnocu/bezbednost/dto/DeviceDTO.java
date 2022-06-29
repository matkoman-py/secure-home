package siitnocu.bezbednost.dto;

import siitnocu.bezbednost.data.Device;

public class DeviceDTO {

	private Long id;
	private String pathFile;
	public DeviceDTO(Long id, String pathFile) {
		super();
		this.id = id;
		this.pathFile = pathFile;
	}
	public DeviceDTO() {
		super();
	}
	
	public DeviceDTO(Device d) {
		this.id = d.getId();
		this.pathFile = d.getPathToFile();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPathFile() {
		return pathFile;
	}
	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}
	
	
	
}
