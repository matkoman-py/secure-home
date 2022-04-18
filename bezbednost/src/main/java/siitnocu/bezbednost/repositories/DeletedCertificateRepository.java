package siitnocu.bezbednost.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import siitnocu.bezbednost.data.DeletedCertificate;

public interface DeletedCertificateRepository extends JpaRepository<DeletedCertificate, Integer> {
	Optional<DeletedCertificate> findByName(String name);
}
