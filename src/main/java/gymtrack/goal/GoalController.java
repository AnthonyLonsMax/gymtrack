package gymtrack.goal;

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

import gymtrack.objetives.ObjetiveRepository;
import gymtrack.user.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/{user_id}/objetives/{objetive_id}/goals")
public class GoalController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjetiveRepository objetiveRepository;

	@Autowired
	private GoalRepository goalRepository;

	@Autowired
	private GoalMapper mapper;

	@GetMapping
	Page<GoalGetDTO> getAll(@PathVariable("user_id") UUID userId,
			@PathVariable("objetive_id") UUID objetiveId,
			Pageable page) {
		if (!userRepository.existsById(userId)) {
			throw new EntityNotFoundException("user not found");
		}
		if (!objetiveRepository.existsById(objetiveId)) {
			throw new EntityNotFoundException("objetive not found");
		}
		return goalRepository.findAllByObjetiveId(objetiveId, page).map(mapper::map);
	}

	@GetMapping("/{goal_id}")
	GoalGetDTO get(@PathVariable("user_id") UUID userId,
			@PathVariable("objetive_id") UUID objetiveId,
			@PathVariable("goal_id") UUID goalId) {
		if (!userRepository.existsById(userId)) {
			throw new EntityNotFoundException("user not found");
		}
		var goal = goalRepository.findByObjetiveIdAndId(objetiveId, goalId)
				.orElseThrow(() -> new EntityNotFoundException(
						"objetive or goal not found"));
		return mapper.map(goal);
	}

	@DeleteMapping("/{goal_id}")
	GoalGetDTO delete(@PathVariable("user_id") UUID userId,
			@PathVariable("objetive_id") UUID objetiveId,
			@PathVariable("goal_id") UUID goalId) {
		if (!userRepository.existsById(userId)) {
			throw new EntityNotFoundException("user not found");
		}
		var goal = goalRepository.findByObjetiveIdAndId(objetiveId, goalId)
				.orElseThrow(() -> new EntityNotFoundException(
						"objetive or goal not found"));
		goalRepository.delete(goal);
		return mapper.map(goal);
	}

	@PatchMapping("/{goal_id}")
	GoalGetDTO update(@PathVariable("user_id") UUID userId,
			@PathVariable("objetive_id") UUID objetiveId,
			@PathVariable("goal_id") UUID goalId,
			@Valid @RequestBody GoalUpdateDTO body) {
		if (!userRepository.existsById(userId)) {
			throw new EntityNotFoundException("user not found");
		}
		var goal = goalRepository.findByObjetiveIdAndId(objetiveId, goalId)
				.orElseThrow(() -> new EntityNotFoundException(
						"objetive or goal not found"));
		Optional.ofNullable(body.name()).ifPresent(goal::setName);
		Optional.ofNullable(body.description()).ifPresent(goal::setDescription);
		Optional.ofNullable(body.done()).ifPresent(goal::setDone);
		goalRepository.save(goal);
		return mapper.map(goal);
	}

	@PostMapping
	GoalGetDTO create(@PathVariable("user_id") UUID userId,
			@PathVariable("objetive_id") UUID objetiveId,
			@Valid @RequestBody GoalCreateDTO body) {
		if (!userRepository.existsById(userId)) {
			throw new EntityNotFoundException("user not found");
		}
		var objetiveEntity = objetiveRepository.findById(objetiveId).orElseThrow(
				() -> new EntityNotFoundException("objetive not found"));
		if (goalRepository.existsByObjetiveIdAndName(objetiveId, body.name())) {
			throw new EntityExistsException("goal already registred");
		}
		var entity = mapper.map(body);
		entity.setObjetive(objetiveEntity);
		goalRepository.save(entity);
		return mapper.map(entity);
	}
}