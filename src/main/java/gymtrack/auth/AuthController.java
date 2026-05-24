package gymtrack.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import gymtrack.APIException;
import gymtrack.ErrorCodes;
import gymtrack.security.JwtService;
import gymtrack.user.UserCreateDTO;
import gymtrack.user.UserEntity;
import gymtrack.user.UserMapper;
import gymtrack.user.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public AuthResponse register(@Valid @RequestBody RegisterRequest req) {
		if (userRepository.existsByUserName(req.userName())) {
			throw APIException.of(ErrorCodes.ALREADY_FOUND,
					"username already taken");
		}

		var entity = new UserEntity();
		entity.setUserName(req.userName());
		entity.setPicture(req.picture());
		entity.setPassword(passwordEncoder.encode(req.password()));
		userRepository.save(entity);

		return buildAuthResponse(entity);
	}

	@PostMapping("/login")
	public AuthResponse login(@Valid @RequestBody LoginRequest req) {
		var entity = userRepository.findByUserName(req.userName())
				.orElseThrow(() -> APIException.of(ErrorCodes.UNAUTHENTICATED,
						"invalid credentials"));

		if (!passwordEncoder.matches(req.password(), entity.getPassword())) {
			throw APIException.of(ErrorCodes.UNAUTHENTICATED,
					"invalid credentials");
		}

		return buildAuthResponse(entity);
	}

	@PostMapping("/refresh")
	public AuthResponse refresh(@Valid @RequestBody RefreshRequest req) {
		var userName = jwtService.extractUserName(req.refreshToken());
		if (userName == null || !jwtService.isTokenValid(req.refreshToken())) {
			throw APIException.of(ErrorCodes.UNAUTHENTICATED,
					"invalid refresh token");
		}

		var entity = userRepository.findByUserName(userName)
				.orElseThrow(() -> APIException.of(ErrorCodes.UNAUTHENTICATED,
						"user not found"));

		return buildAuthResponse(entity);
	}

	private AuthResponse buildAuthResponse(UserEntity entity) {
		var accessToken = jwtService.generateAccessToken(entity);
		var refreshToken = jwtService.generateRefreshToken(entity);
		return new AuthResponse(accessToken, refreshToken,
				entity.getId().toString(), entity.getUserName(),
				entity.getPicture());
	}
}
