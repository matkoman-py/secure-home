package siitnocu.bezbednost.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import siitnocu.bezbednost.data.Role;
import siitnocu.bezbednost.dto.RoleDTO;
import siitnocu.bezbednost.services.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {

	private RoleService roleService;
	
	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/create")
	public ResponseEntity<String> create(@RequestBody RoleDTO roleData) throws Exception {
		return ResponseEntity.ok(roleService.create(roleData));
	}
	
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/delete")
	public ResponseEntity<String> create(@PathVariable("Id") Integer Id) throws Exception {
		return ResponseEntity.ok(roleService.delete(Id));
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/get-all")
	public ResponseEntity<List<Role>> getAll() throws Exception {
		return ResponseEntity.ok(roleService.getAll());
	}
}
