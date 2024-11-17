package fiap.tds.gocycleapi.repository;

import fiap.tds.gocycleapi.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
    Optional<Profile> findByCpf(String cpf);
}
