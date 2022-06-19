package com.example.myhome.repository;

import com.example.myhome.domain.Estate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EstateRepository extends JpaRepository<Estate, Long> {

}