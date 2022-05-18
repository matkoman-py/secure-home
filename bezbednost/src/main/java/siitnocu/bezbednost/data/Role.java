package siitnocu.bezbednost.data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@Column(nullable = false)
    private String roleName;
    
	@Column(nullable = false)
    private List<String> objectAccesPermissions;

	public Role(String roleName, List<String> objectAccesPermissions) {
		this.roleName = roleName;
		this.objectAccesPermissions = objectAccesPermissions;
	}

	public Role() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
