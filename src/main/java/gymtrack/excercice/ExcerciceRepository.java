package gymtrack.excercice;

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
public interface ExcerciceRepository
		extends
			ListCrudRepository<ExcercieEntity, UUID>,
			ListPagingAndSortingRepository<ExcercieEntity, UUID> {

	Page<ExcercieEntity> findAllByWorkoutId(UUID workoutId, Pageable page);

	Optional<ExcercieEntity> findByWorkoutIdAndId(UUID workoutId, UUID excerciceId);

	boolean existsByWorkoutIdAndName(UUID workoutId, String name);
}
