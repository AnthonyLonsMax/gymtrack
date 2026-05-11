package gymtrack.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository
		extends
			ListCrudRepository<UserEntity, UUID>,
			ListPagingAndSortingRepository<UserEntity, UUID> {

	boolean existsByUserName(String userName);

	boolean existsById(UUID id);

	Optional<UserEntity> findByUserName(String userName);
}
