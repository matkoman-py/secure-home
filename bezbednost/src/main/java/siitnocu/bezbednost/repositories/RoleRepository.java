package siitnocu.bezbednost.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import siitnocu.bezbednost.data.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByRoleName(String roleName);
}