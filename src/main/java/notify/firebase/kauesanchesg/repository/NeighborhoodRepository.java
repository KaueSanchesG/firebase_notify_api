package notify.firebase.kauesanchesg.repository;

import notify.firebase.kauesanchesg.domain.entity.NeighborhoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeighborhoodRepository extends JpaRepository<NeighborhoodEntity, Long> {
}
