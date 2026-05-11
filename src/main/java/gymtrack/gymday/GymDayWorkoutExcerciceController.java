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
@RequestMapping("/api/gymdays/{gymday_id}/workouts/{workout_id}/excercices")
public class GymDayWorkoutExcerciceController {

	@Autowired
	private GymDayWorkoutReposioty workoutRepository;

	@Autowired
	private GymDayWorkoutExcerciceRepository excerciceRepository;

	@Autowired
	private GymDayWorkoutExcerciceMapper mapper;

	@GetMapping
	Page<GymDayWorkoutExcerciceGetDTO> getAll(@PathVariable("gymday_id") UUID gymdayId,
			@PathVariable("workout_id") UUID workoutId, Pageable page) {
		if (!workoutRepository.existsById(workoutId)) {
			throw new EntityNotFoundException("workout not found");
		}
		return excerciceRepository.findAllByWorkoutId(workoutId, page).map(mapper::map);
	}

	@GetMapping("/{excercice_id}")
	GymDayWorkoutExcerciceGetDTO get(@PathVariable("gymday_id") UUID gymdayId,
			@PathVariable("workout_id") UUID workoutId,
			@PathVariable("excercice_id") UUID excerciceId) {
		var excercice = excerciceRepository.findByWorkoutIdAndId(workoutId, excerciceId)
				.orElseThrow(() -> new EntityNotFoundException(
						"workout or excercice not found"));
		return mapper.map(excercice);
	}

	@DeleteMapping("/{excercice_id}")
	GymDayWorkoutExcerciceGetDTO delete(@PathVariable("gymday_id") UUID gymdayId,
			@PathVariable("workout_id") UUID workoutId,
			@PathVariable("excercice_id") UUID excerciceId) {
		var excercice = excerciceRepository.findByWorkoutIdAndId(workoutId, excerciceId)
				.orElseThrow(() -> new EntityNotFoundException(
						"workout or excercice not found"));
		excerciceRepository.delete(excercice);
		return mapper.map(excercice);
	}

	@PatchMapping("/{excercice_id}")
	GymDayWorkoutExcerciceGetDTO update(@PathVariable("gymday_id") UUID gymdayId,
			@PathVariable("workout_id") UUID workoutId,
			@PathVariable("excercice_id") UUID excerciceId,
			@Valid @RequestBody GymDayWorkoutExcerciceUpdateDTO body) {
		var excercice = excerciceRepository.findByWorkoutIdAndId(workoutId, excerciceId)
				.orElseThrow(() -> new EntityNotFoundException(
						"workout or excercice not found"));
		Optional.ofNullable(body.name()).ifPresent(excercice::setName);
		Optional.ofNullable(body.sets()).ifPresent(excercice::setSets);
		Optional.ofNullable(body.reps()).ifPresent(excercice::setReps);
		Optional.ofNullable(body.weigth()).ifPresent(excercice::setWeigth);
		excerciceRepository.save(excercice);
		return mapper.map(excercice);
	}

	@PostMapping
	GymDayWorkoutExcerciceGetDTO create(@PathVariable("gymday_id") UUID gymdayId,
			@PathVariable("workout_id") UUID workoutId,
			@Valid @RequestBody GymDayWorkoutExcerciceCreateDTO body) {
		var workoutEntity = workoutRepository.findById(workoutId).orElseThrow(
				() -> new EntityNotFoundException("workout not found"));
		if (excerciceRepository.existsByWorkoutIdAndName(workoutId, body.name())) {
			throw new EntityExistsException("excercice already registred");
		}
		var entity = mapper.map(body);
		entity.setWorkout(workoutEntity);
		excerciceRepository.save(entity);
		return mapper.map(entity);
	}
}