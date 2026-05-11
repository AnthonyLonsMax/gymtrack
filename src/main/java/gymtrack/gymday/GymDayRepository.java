package gymtrack.gymday;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface GymDayRepository
		extends
			ListCrudRepository<GymDayEntity, UUID>,
			ListPagingAndSortingRepository<GymDayEntity, UUID> {

	Page<GymDayEntity> findAll(Pageable page);
}
