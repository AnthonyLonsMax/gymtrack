package gymtrack.goal;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface GoalRepository
		extends
			ListCrudRepository<GoalEntity, UUID>,
			ListPagingAndSortingRepository<GoalEntity, UUID> {

	Page<GoalEntity> findAllByObjetiveId(UUID objetiveId, Pageable page);

	Optional<GoalEntity> findByObjetiveIdAndId(UUID objetiveId, UUID goalId);

	boolean existsByObjetiveIdAndName(UUID objetiveId, String name);
}
