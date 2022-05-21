package siitnocu.bezbednost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import siitnocu.bezbednost.data.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
