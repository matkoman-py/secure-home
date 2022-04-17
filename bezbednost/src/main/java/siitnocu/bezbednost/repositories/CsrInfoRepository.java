package siitnocu.bezbednost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import siitnocu.bezbednost.data.CsrInfo;

import java.util.Optional;

public interface CsrInfoRepository extends JpaRepository<CsrInfo, Integer> {
    Optional<CsrInfo> findByDomainName(String domainName);
}
