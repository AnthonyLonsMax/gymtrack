package gymtrack.gymday;

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

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/gymdays/{gymday_id}/workouts")
public class GymDayWorkoutController {

	@Autowired
	private GymDayRepository gymDayRepository;

	@Autowired
	private GymDayWorkoutReposioty workoutRepository;

	@Autowired
	private GymDayWorkoutMapper mapper;

	@GetMapping
	Page<GymDayWorkoutGetDTO> getAll(@PathVariable("gymday_id") UUID gymdayId,
			Pageable page) {
		if (!gymDayRepository.existsById(gymdayId)) {
			throw new EntityNotFoundException("gymday not found");
		}
		return workoutRepository.findAllByGymdayId(gymdayId, page).map(mapper::map);
	}

	@GetMapping("/{workout_id}")
	GymDayWorkoutGetDTO get(@PathVariable("gymday_id") UUID gymdayId,
			@PathVariable("workout_id") UUID workoutId) {
		var workout = workoutRepository.findByGymdayIdAndId(gymdayId, workoutId)
				.orElseThrow(() -> new EntityNotFoundException(
						"gymday or workout not found"));
		return mapper.map(workout);
	}

	@DeleteMapping("/{workout_id}")
	GymDayWorkoutGetDTO delete(@PathVariable("gymday_id") UUID gymdayId,
			@PathVariable("workout_id") UUID workoutId) {
		var workout = workoutRepository.findByGymdayIdAndId(gymdayId, workoutId)
				.orElseThrow(() -> new EntityNotFoundException(
						"gymday or workout not found"));
		workoutRepository.delete(workout);
		return mapper.map(workout);
	}

	@PatchMapping("/{workout_id}")
	GymDayWorkoutGetDTO update(@PathVariable("gymday_id") UUID gymdayId,
			@PathVariable("workout_id") UUID workoutId,
			@Valid @RequestBody GymDayWorkoutUpdateDTO body) {
		var workout = workoutRepository.findByGymdayIdAndId(gymdayId, workoutId)
				.orElseThrow(() -> new EntityNotFoundException(
						"gymday or workout not found"));
		Optional.ofNullable(body.name()).ifPresent(workout::setName);
		workoutRepository.save(workout);
		return mapper.map(workout);
	}

	@PostMapping
	GymDayWorkoutGetDTO create(@PathVariable("gymday_id") UUID gymdayId,
			@Valid @RequestBody GymDayWorkoutCreateDTO body) {
		var gymdayEntity = gymDayRepository.findById(gymdayId).orElseThrow(
				() -> new EntityNotFoundException("gymday not found"));
		if (workoutRepository.existsByGymdayIdAndName(gymdayId, body.name())) {
			throw new EntityExistsException("workout already registred");
		}
		var entity = mapper.map(body);
		entity.setGymday(gymdayEntity);
		workoutRepository.save(entity);
		return mapper.map(entity);
	}
}