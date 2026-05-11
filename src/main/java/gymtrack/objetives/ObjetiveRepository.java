package gymtrack.objetives;

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
public interface ObjetiveRepository
		extends
			ListCrudRepository<ObjetiveEntity, UUID>,
			ListPagingAndSortingRepository<ObjetiveEntity, UUID> {

	Page<ObjetiveEntity> findAll(Pageable page);

	Optional<ObjetiveEntity> findById(UUID id);

	Page<ObjetiveEntity> findAllByUserId(UUID userId, Pageable page);

	Optional<ObjetiveEntity> findByUserIdAndId(UUID userId, UUID id);

	boolean existsByUserIdAndName(UUID userId, String name);

	boolean existsByName(String name);
}
