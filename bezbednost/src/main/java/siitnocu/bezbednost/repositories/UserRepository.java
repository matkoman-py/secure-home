package siitnocu.bezbednost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import siitnocu.bezbednost.data.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
}
