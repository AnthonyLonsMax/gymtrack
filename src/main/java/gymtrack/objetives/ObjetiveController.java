package gymtrack.objetives;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gymtrack.user.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/{user_id}/objetives")
public class ObjetiveController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjetiveRepository repository;

	@Autowired
	private ObjetiveMapper mapper;

	@GetMapping
	Page<ObjectiveGetDTO> getAll(@PathVariable("user_id") UUID userId,
			Pageable page) {
		if (!userRepository.existsById(userId)) {
			throw new EntityNotFoundException("user not found");
		}
		return repository.findAllByUserId(userId, page).map(mapper::map);
	}

	@GetMapping("/{objetive_id}")
	ObjectiveGetDTO get(@PathVariable("user_id") UUID userId,
			@PathVariable("objetive_id") UUID objetiveId) {
		if (!userRepository.existsById(userId)) {
			throw new EntityNotFoundException("user not found");
		}
		var entity = repository.findByUserIdAndId(userId, objetiveId).orElseThrow(
				() -> new EntityNotFoundException("objetive not found"));
		return mapper.map(entity);
	}

	@DeleteMapping("/{objetive_id}")
	ObjectiveGetDTO delete(@PathVariable("user_id") UUID userId,
			@PathVariable("objetive_id") UUID objetiveId) {
		if (!userRepository.existsById(userId)) {
			throw new EntityNotFoundException("user not found");
		}
		var entity = repository.findByUserIdAndId(userId, objetiveId).orElseThrow(
				() -> new EntityNotFoundException("objetive not found"));
		repository.delete(entity);
		return mapper.map(entity);
	}

	@PatchMapping("/{objetive_id}")
	ObjectiveGetDTO update(@PathVariable("user_id") UUID userId,
			@PathVariable("objetive_id") UUID objetiveId,
			@Valid @RequestBody ObjetiveUpdateDTO body) {
		if (!userRepository.existsById(userId)) {
			throw new EntityNotFoundException("user not found");
		}
		var entity = repository.findByUserIdAndId(userId, objetiveId).orElseThrow(
				() -> new EntityNotFoundException("objetive not found"));
		Optional.ofNullable(body.name()).ifPresent(entity::setName);
		Optional.ofNullable(body.description()).ifPresent(entity::setDescription);
		repository.save(entity);
		return mapper.map(entity);
	}

	@PostMapping
	ObjectiveGetDTO create(@PathVariable("user_id") UUID userId,
			@Valid @RequestBody ObjetiveCreateDTO body) {
		var userEntity = userRepository.findById(userId).orElseThrow(
				() -> new EntityNotFoundException("user not found"));
		if (repository.existsByUserIdAndName(userId, body.name())) {
			throw new EntityExistsException("objetive already registred");
		}
		var entity = mapper.map(body);
		entity.setUser(userEntity);
		repository.save(entity);
		return mapper.map(entity);
	}
}