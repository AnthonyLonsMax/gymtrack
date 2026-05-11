package gymtrack.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import gymtrack.APIException;
import gymtrack.ErrorCodes;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserMapper mapper;

	@Autowired
	private UserRepository repository;

	@GetMapping
	private Page<UserGetDTO> users(Pageable page) {
		return repository.findAll(page).map(mapper::map);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	private UserGetDTO create(@Valid @RequestBody UserCreateDTO dto) {
		if (repository.existsByUserName(dto.userName())) {
			throw APIException.of(ErrorCodes.ALREADY_FOUND,
					"username already taken");
		}
		var entity = mapper.map(dto);
		repository.save(entity);
		return mapper.map(entity);
	}

	@PatchMapping("/{user_id}")
	private UserGetDTO update(@Valid @RequestBody UserUpdateDTO dto,
			@PathVariable("user_id") UUID user_id) {
		var entity = repository.findById(user_id).orElseThrow(
				() -> new EntityNotFoundException("user not found"));
		Optional.ofNullable(dto.password()).ifPresent(entity::setPassword);
		Optional.ofNullable(dto.userName()).ifPresent(entity::setUserName);
		Optional.ofNullable(dto.picture()).ifPresent(entity::setPicture);
		repository.save(entity);
		return mapper.map(entity);
	}

	@GetMapping("/{user_id}")
	private UserGetDTO get(@PathVariable("user_id") UUID user_id) {
		var entity = repository.findById(user_id).orElseThrow(
				() -> new EntityNotFoundException("user not found"));
		return mapper.map(entity);
	}

	@DeleteMapping("/{user_id}")
	private UserGetDTO delete(@PathVariable("user_id") UUID user_id) {
		var entity = repository.findById(user_id).orElseThrow(
				() -> new EntityNotFoundException("user not found"));
		repository.delete(entity);
		return mapper.map(entity);
	}

}
