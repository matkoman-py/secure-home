package siitnocu.bezbednost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import siitnocu.bezbednost.data.NonValidToken;

import java.util.Optional;

public interface NonValidTokenRepository extends JpaRepository<NonValidToken, Integer> {
    Optional<NonValidToken> findByToken(String token);
}
