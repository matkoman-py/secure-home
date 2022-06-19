package com.example.myhome.repository;

import com.example.myhome.domain.UnsuccessfullLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnsuccessfullLoginRepository extends JpaRepository<UnsuccessfullLogin, Integer> {
    List<UnsuccessfullLogin> findByUsername(String username);
}
