package siitnocu.bezbednost.repositories;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import siitnocu.bezbednost.data.Rule;

public interface RuleRepository extends JpaRepository<Rule, Long> {
	Optional<Rule> findByDeviceId(Long id);

}
