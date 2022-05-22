package siitnocu.bezbednost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import siitnocu.bezbednost.data.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE %?1%"
            + " OR LOWER(u.lastName) LIKE %?1%"
            + " OR LOWER(u.username) LIKE %?1%"
            + " OR LOWER(u.email) LIKE %?1%"
            + " OR LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE %?1%")
    List<User> search(String keyword);
}