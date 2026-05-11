package gymtrack.excercice;

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

import gymtrack.workout.WorkoutRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/{user_id}/workouts/{workout_id}/excercices")
public class ExcerciceController {

	@Autowired
	private WorkoutRepository workoutRepository;

	@Autowired
	private ExcerciceRepository excerciceRepository;

	@Autowired
	private ExcerciceMapper mapper;

	@GetMapping
	Page<ExcerciceGetDTO> getAll(@PathVariable("user_id") UUID userID,
			@PathVariable("workout_id") UUID workoutID, Pageable page) {
		if (!workoutRepository.existsById(workoutID)) {
			throw new EntityNotFoundException("workout not found");
		}
		return excerciceRepository.findAllByWorkoutId(workoutID, page).map(mapper::map);
	}

	@GetMapping("/{excercice_id}")
	ExcerciceGetDTO get(@PathVariable("user_id") UUID userID,
			@PathVariable("workout_id") UUID workoutID,
			@PathVariable("excercice_id") UUID excerciceID) {
		var excercice = excerciceRepository.findByWorkoutIdAndId(workoutID, excerciceID)
				.orElseThrow(() -> new EntityNotFoundException(
						"workout or excercice not found"));
		return mapper.map(excercice);
	}

	@DeleteMapping("/{excercice_id}")
	ExcerciceGetDTO delete(@PathVariable("user_id") UUID userID,
			@PathVariable("workout_id") UUID workoutID,
			@PathVariable("excercice_id") UUID excerciceID) {
		var excercice = excerciceRepository.findByWorkoutIdAndId(workoutID, excerciceID)
				.orElseThrow(() -> new EntityNotFoundException(
						"workout or excercice not found"));
		excerciceRepository.delete(excercice);
		return mapper.map(excercice);
	}

	@PatchMapping("/{excercice_id}")
	ExcerciceGetDTO update(@PathVariable("user_id") UUID userID,
			@PathVariable("workout_id") UUID workoutID,
			@PathVariable("excercice_id") UUID excerciceID,
			@Valid @RequestBody ExcercieUpdateDTO body) {
		var excercice = excerciceRepository.findByWorkoutIdAndId(workoutID, excerciceID)
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
	ExcerciceGetDTO create(@PathVariable("user_id") UUID userID,
			@PathVariable("workout_id") UUID workoutID,
			@Valid @RequestBody ExcerciceCreateDTO body) {
		var workoutEntity = workoutRepository.findById(workoutID).orElseThrow(
				() -> new EntityNotFoundException("workout not found"));
		if (excerciceRepository.existsByWorkoutIdAndName(workoutID, body.name())) {
			throw new EntityExistsException("excercice already registred");
		}
		var entity = mapper.map(body);
		entity.setWorkout(workoutEntity);
		excerciceRepository.save(entity);
		return mapper.map(entity);
	}
}