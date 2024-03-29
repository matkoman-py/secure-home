package siitnocu.bezbednost.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siitnocu.bezbednost.data.Role;
import siitnocu.bezbednost.repositories.RoleRepository;

@Service
public class RoleService {

  @Autowired
  private RoleRepository roleRepository;

  public Role findById(Long id) {
    Role auth = this.roleRepository.getOne(id);
    return auth;
  }

  public Role findByName(String name) {
	Role roles = this.roleRepository.findByName(name);
    return roles;
  }
}
