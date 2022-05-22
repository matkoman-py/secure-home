package siitnocu.bezbednost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import siitnocu.bezbednost.data.UnsuccessfullLogin;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface UnsuccessfullLoginRepository extends JpaRepository<UnsuccessfullLogin, Integer> {
    List<UnsuccessfullLogin> findByUsername(String username);
}
