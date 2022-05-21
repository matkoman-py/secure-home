package siitnocu.bezbednost.dto;

import java.util.List;

public class RoleUpdateInfo {
	private List<String> roles;
	private Long id;
	
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
