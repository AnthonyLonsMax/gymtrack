package gymtrack.gymday;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/gymdays")
public class GymDayController {

	@Autowired
	private GymDayRepository repository;

	@Autowired
	private GymDayMapper mapper;

	@GetMapping
	Page<GymDayGetDTO> getAll(Pageable page) {
		return repository.findAll(page).map(mapper::map);
	}

	@GetMapping("/{gymday_id}")
	GymDayGetDTO get(@PathVariable("gymday_id") UUID gymdayId) {
		var entity = repository.findById(gymdayId).orElseThrow(
				() -> new EntityNotFoundException("gymday not found"));
		return mapper.map(entity);
	}

	@DeleteMapping("/{gymday_id}")
	GymDayGetDTO delete(@PathVariable("gymday_id") UUID gymdayId) {
		var entity = repository.findById(gymdayId).orElseThrow(
				() -> new EntityNotFoundException("gymday not found"));
		repository.delete(entity);
		return mapper.map(entity);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	GymDayGetDTO create() {
		var entity = new GymDayEntity();
		repository.save(entity);
		return mapper.map(entity);
	}
}