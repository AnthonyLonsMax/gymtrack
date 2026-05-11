package gymtrack.config;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import gymtrack.user.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET = "dGhpcyBpcyBhIHZlcnkgc2VjdXJlIGtleSBmb3Igand0IHRva2VuIGdlbmVyYXRpb24gYW5kIHZhbGlkYXRpb24gZm9yIHRoZSBneW10cmFjayBhcHA=";
	private static final long ACCESS_EXPIRATION = 1000 * 60 * 30; // 30 min
	private static final long REFRESH_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 days

	private SecretKey getSigningKey() {
		var keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateAccessToken(UserEntity user) {
		return buildToken(user, ACCESS_EXPIRATION);
	}

	public String generateRefreshToken(UserEntity user) {
		return buildToken(user, REFRESH_EXPIRATION);
	}

	private String buildToken(UserEntity user, long expiration) {
		return Jwts.builder()
			.subject(user.getId().toString())
			.claim("userName", user.getUserName())
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + expiration))
			.signWith(getSigningKey())
			.compact();
	}

	public String extractUserName(String token) {
		try {
			var claims = extractAllClaims(token);
			return claims.get("userName", String.class);
		} catch (Exception e) {
			return null;
		}
	}

	public String extractUserId(String token) {
		try {
			var claims = extractAllClaims(token);
			return claims.getSubject();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean isTokenValid(String token) {
		try {
			var claims = extractAllClaims(token);
			return !claims.getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
			.verifyWith(getSigningKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}
}
