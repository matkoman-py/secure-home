package siitnocu.bezbednost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import siitnocu.bezbednost.data.DeletedCertificate;

public interface DeletedCertificateRepository extends JpaRepository<DeletedCertificate, Integer> {
}
