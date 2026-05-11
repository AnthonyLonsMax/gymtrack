package gymtrack.workout;

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
@RequestMapping("/api/users/{user_id}/workouts")
public class WorkoutController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WorkoutRepository workoutRepository;

	@Autowired
	private WorkoutMapper mapper;

	@GetMapping
	Page<WorkoutGetDTO> getAll(@PathVariable("user_id") UUID userID,
			Pageable page) {
		if (!userRepository.existsById(userID)) {
			throw new EntityNotFoundException("user not found");
		}
		return workoutRepository.findAllByUserId(userID, page).map(mapper::map);
	}

	@GetMapping("/{workout_id}")
	WorkoutGetDTO get(@PathVariable("user_id") UUID userID,
			@PathVariable("workout_id") UUID workoutID) {
		var workout = workoutRepository.findByUserIdAndId(userID, workoutID)
				.orElseThrow(() -> new EntityNotFoundException(
						"user or workout not found"));

		return mapper.map(workout);
	}

	@DeleteMapping("/{workout_id}")
	WorkoutGetDTO delete(@PathVariable("user_id") UUID userID,
			@PathVariable("workout_id") UUID workoutID) {
		var workout = workoutRepository.findByUserIdAndId(userID, workoutID)
				.orElseThrow(() -> new EntityNotFoundException(
						"user or workout not found"));
		workoutRepository.delete(workout);
		return mapper.map(workout);
	}

	@PatchMapping("/{workout_id}")
	WorkoutGetDTO update(@PathVariable("user_id") UUID userID,
			@PathVariable("workout_id") UUID workoutID,
			@Valid @RequestBody WorkoutUpdateDTO body

	) {
		var workout = workoutRepository.findByUserIdAndId(userID, workoutID)
				.orElseThrow(() -> new EntityNotFoundException(
						"user or workout not found"));
		Optional.ofNullable(body.name()).ifPresent(workout::setName);
		Optional.ofNullable(body.picture()).ifPresent(workout::setPicture);
		workoutRepository.save(workout);
		return mapper.map(workout);
	}

	@PostMapping
	WorkoutGetDTO create(@PathVariable("user_id") UUID userID,
			@Valid @RequestBody WorkoutCreateDTO body

	) {
		var userEntity = userRepository.findById(userID).orElseThrow(
				() -> new EntityNotFoundException("user not found"));
		if (workoutRepository.existsByUserIdAndName(userID, body.name())) {
			throw new EntityExistsException("workout already registred");
		}
		var entity = mapper.map(body);
		entity.setUser(userEntity);
		workoutRepository.save(entity);
		return mapper.map(entity);
	}

}
