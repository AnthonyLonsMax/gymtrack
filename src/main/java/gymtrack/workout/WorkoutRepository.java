package gymtrack.workout;

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
public interface WorkoutRepository
		extends
			ListCrudRepository<WorkoutEntity, UUID>,
			ListPagingAndSortingRepository<WorkoutEntity, UUID> {

	Page<WorkoutEntity> findAllByUserId(UUID userID, Pageable page);

	Optional<WorkoutEntity> findByUserIdAndId(UUID userID, UUID workoutId);

	boolean existsByUserIdAndName(UUID userID, String name);
}
