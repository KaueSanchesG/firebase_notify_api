package notify.firebase.kauesanchesg.repository;

import notify.firebase.kauesanchesg.domain.entity.WarningsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarningsRepository extends JpaRepository<WarningsEntity, Long> {
}
