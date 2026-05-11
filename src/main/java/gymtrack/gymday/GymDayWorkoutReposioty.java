package gymtrack.gymday;

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
public interface GymDayWorkoutReposioty
		extends
			ListCrudRepository<GymDayWorkout, UUID>,
			ListPagingAndSortingRepository<GymDayWorkout, UUID> {

	Page<GymDayWorkout> findAllByGymdayId(UUID gymdayId, Pageable page);

	Optional<GymDayWorkout> findByGymdayIdAndId(UUID gymdayId, UUID workoutId);

	boolean existsByGymdayIdAndName(UUID gymdayId, String name);
}
