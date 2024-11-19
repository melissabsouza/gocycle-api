package fiap.tds.gocycleapi.repository;

import fiap.tds.gocycleapi.model.Address;
import fiap.tds.gocycleapi.model.Telephone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TelephoneRepository extends JpaRepository<Telephone, Long> {
    Optional<Telephone> findById(Long id);
}
