package siitnocu.bezbednost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import siitnocu.bezbednost.data.UserRequest;

public interface UserRequestRepository extends JpaRepository<UserRequest, Long>{

}
