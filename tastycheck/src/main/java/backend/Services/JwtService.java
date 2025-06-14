package backend.Services;

import backend.Models.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

public class JwtService {

	private final String secret = "d5XUOIxm1YrBdBCINamPeXOiWQGaLREj"; // ou lê do application.properties (para já fixa aqui)
	private final long expirationMs = 86400000; // 1 dia em ms

	private SecretKey getSignInKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	// Gera um token
	public String buildToken(Map<String, Object> extraClaims, User user, long expiration) {
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(user.getEmail())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	// Extrai o username (ou subject)
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public String extractEmail(String token) {
		final Claims claims = extractAllClaims(token);
		return claims.get("email", String.class);
	}

	public String extractUserId(String token) {
		final Claims claims = extractAllClaims(token);
		return claims.get("id", String.class);
	}

	public String extractUserProfilePicture(String token) {
		final Claims claims = extractAllClaims(token);
		return claims.get("profilePicture", String.class);
	}

	public String extractUserRole(String token) {
		final Claims claims = extractAllClaims(token);
		return claims.get("role", String.class);
	}

	public String extractName(String token) {
		final Claims claims = extractAllClaims(token);
		return claims.get("username", String.class);
	}

	// Extrai qualquer claim
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String generateToken(User user) {
		return buildToken(user.claimsForJwt(), user, expirationMs);
	}


	// Valida token (verifica se não expirou)
	public boolean validateToken(String token) {
		final String tokenUsername = extractUsername(token);
		return !isTokenExpired(token);
	}

	// Verifica se token está expirado
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}