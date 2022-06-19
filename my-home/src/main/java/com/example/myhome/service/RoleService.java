package com.example.myhome.service;

import com.example.myhome.domain.Role;
import com.example.myhome.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
