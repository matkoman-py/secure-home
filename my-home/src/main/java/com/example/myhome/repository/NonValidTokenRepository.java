package com.example.myhome.repository;

import com.example.myhome.domain.NonValidToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NonValidTokenRepository extends JpaRepository<NonValidToken, Integer> {
    Optional<NonValidToken> findByToken(String token);
}
