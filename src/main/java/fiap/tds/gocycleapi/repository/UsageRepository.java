package fiap.tds.gocycleapi.repository;

import fiap.tds.gocycleapi.model.Usage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsageRepository extends JpaRepository<Usage, Long> {

    Optional<Usage> findById(Long id);
}
