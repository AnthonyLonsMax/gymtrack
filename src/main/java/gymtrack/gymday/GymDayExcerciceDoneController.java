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
@RequestMapping("/api/gymdays/{gymday_id}/excercices-done")
public class GymDayExcerciceDoneController {

	@Autowired
	private GymDayRepository gymDayRepository;

	@Autowired
	private GymDayExcercieDoneRepository excerciceRepository;

	@Autowired
	private GymDayExcerciceDoneMapper mapper;

	@GetMapping
	Page<GymDayExcerciceDoneGetDTO> getAll(@PathVariable("gymday_id") UUID gymdayId,
			Pageable page) {
		if (!gymDayRepository.existsById(gymdayId)) {
			throw new EntityNotFoundException("gymday not found");
		}
		return excerciceRepository.findAllByGymdayId(gymdayId, page).map(mapper::map);
	}

	@GetMapping("/{excercice_id}")
	GymDayExcerciceDoneGetDTO get(@PathVariable("gymday_id") UUID gymdayId,
			@PathVariable("excercice_id") UUID excerciceId) {
		var excercice = excerciceRepository.findByGymdayIdAndId(gymdayId, excerciceId)
				.orElseThrow(() -> new EntityNotFoundException(
						"gymday or excercice not found"));
		return mapper.map(excercice);
	}

	@DeleteMapping("/{excercice_id}")
	GymDayExcerciceDoneGetDTO delete(@PathVariable("gymday_id") UUID gymdayId,
			@PathVariable("excercice_id") UUID excerciceId) {
		var excercice = excerciceRepository.findByGymdayIdAndId(gymdayId, excerciceId)
				.orElseThrow(() -> new EntityNotFoundException(
						"gymday or excercice not found"));
		excerciceRepository.delete(excercice);
		return mapper.map(excercice);
	}

	@PatchMapping("/{excercice_id}")
	GymDayExcerciceDoneGetDTO update(@PathVariable("gymday_id") UUID gymdayId,
			@PathVariable("excercice_id") UUID excerciceId,
			@Valid @RequestBody GymDayExcerciceDoneUpdateDTO body) {
		var excercice = excerciceRepository.findByGymdayIdAndId(gymdayId, excerciceId)
				.orElseThrow(() -> new EntityNotFoundException(
						"gymday or excercice not found"));
		Optional.ofNullable(body.name()).ifPresent(excercice::setName);
		Optional.ofNullable(body.sets()).ifPresent(excercice::setSets);
		Optional.ofNullable(body.reps()).ifPresent(excercice::setReps);
		Optional.ofNullable(body.weigth()).ifPresent(excercice::setWeigth);
		excerciceRepository.save(excercice);
		return mapper.map(excercice);
	}

	@PostMapping
	GymDayExcerciceDoneGetDTO create(@PathVariable("gymday_id") UUID gymdayId,
			@Valid @RequestBody GymDayExcerciceDoneCreateDTO body) {
		var gymdayEntity = gymDayRepository.findById(gymdayId).orElseThrow(
				() -> new EntityNotFoundException("gymday not found"));
		if (excerciceRepository.existsByGymdayIdAndName(gymdayId, body.name())) {
			throw new EntityExistsException("excercice already registred");
		}
		var entity = mapper.map(body);
		entity.setGymday(gymdayEntity);
		excerciceRepository.save(entity);
		return mapper.map(entity);
	}
}