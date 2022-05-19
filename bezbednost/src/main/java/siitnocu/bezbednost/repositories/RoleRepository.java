package siitnocu.bezbednost.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import siitnocu.bezbednost.data.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	List<Role> findByName(String name);
}
