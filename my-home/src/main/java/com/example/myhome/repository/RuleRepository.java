package com.example.myhome.repository;

import java.util.Optional;

import com.example.myhome.domain.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, Long> {
	Optional<Rule> findByDeviceId(Long id);

}
