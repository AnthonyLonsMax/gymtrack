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
public interface GymDayExcercieDoneRepository
		extends
			ListCrudRepository<GymDayExcerciceDone, UUID>,
			ListPagingAndSortingRepository<GymDayExcerciceDone, UUID> {

	Page<GymDayExcerciceDone> findAllByGymdayId(UUID gymdayId, Pageable page);

	Optional<GymDayExcerciceDone> findByGymdayIdAndId(UUID gymdayId, UUID excerciceId);

	boolean existsByGymdayIdAndName(UUID gymdayId, String name);
}
