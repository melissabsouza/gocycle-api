package fiap.tds.gocycleapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fiap.tds.gocycleapi.model.Address;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findById(Long id);
}
