package siitnocu.bezbednost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import siitnocu.bezbednost.data.Estate;
import siitnocu.bezbednost.data.User;

import java.util.List;

public interface EstateRepository extends JpaRepository<Estate, Long> {

}