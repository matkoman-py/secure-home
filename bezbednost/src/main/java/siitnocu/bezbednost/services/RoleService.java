package siitnocu.bezbednost.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import siitnocu.bezbednost.data.Role;
import siitnocu.bezbednost.dto.RoleDTO;
import siitnocu.bezbednost.repositories.RoleRepository;

@Service
@Transactional
public class RoleService {

	@Autowired
	public RoleService() {
		
	}
	
	@Autowired
	private RoleRepository roleRepository;
	
	public String create(RoleDTO roleData) {
		Role newRole = new Role(roleData.getRoleName(),
								roleData.getObjectAccesPermissions());
		
		roleRepository.save(newRole);
		return "Role " + roleData.getRoleName() + " succesfully created";
	}
	
	public String delete(Integer Id) {
		if(roleRepository.findById(Id) != null) {
			roleRepository.deleteById(Id);
			return "Role with id: " + Id + " succesfully deleated";
		}
		
		return "No role with id: " + Id + " was found";
	}

	public List<Role> getAll() {
		return roleRepository.findAll();
	}
}
