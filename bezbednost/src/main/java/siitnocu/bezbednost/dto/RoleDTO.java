package siitnocu.bezbednost.dto;

import java.util.List;

import siitnocu.bezbednost.data.Role;

public class RoleDTO {
	private String roleName;
	private List<String> objectAccesPermissions;
	
	public RoleDTO() {
		super();
	}

	public RoleDTO(String roleName, List<String> objectAccesPermissions) {
		this.roleName = roleName;
		this.objectAccesPermissions = objectAccesPermissions;
	}
	
	public RoleDTO(Role role) {
		this.roleName = role.getRoleName();
		this.objectAccesPermissions = role.getObjectAccesPermissions();
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<String> getObjectAccesPermissions() {
		return objectAccesPermissions;
	}

	public void setObjectAccesPermissions(List<String> objectAccesPermissions) {
		this.objectAccesPermissions = objectAccesPermissions;
	}
}
