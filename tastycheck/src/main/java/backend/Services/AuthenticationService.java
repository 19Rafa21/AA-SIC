package backend.Services;

import backend.DTOs.UserDTO;
import backend.Exceptions.UnauthorizedException;
import backend.Exceptions.UserException;
import backend.Models.User;
import backend.Services.JwtService;
import backend.Services.UserService;  // ou o teu DAO direto
import org.orm.PersistentException;

import javax.servlet.http.Cookie;

public class AuthenticationService {

	private final JwtService jwtService;
	private final UserService userService;

	public AuthenticationService() {
		this.jwtService = new JwtService();
		this.userService = new UserService();
	}

	public boolean register(UserDTO user) throws UnauthorizedException, PersistentException {
		if (userService.emailExists(user.getEmail())){
			throw new UnauthorizedException("User with email '" + user.getEmail() + "' already exists!");
		}

		return userService.registerUser(user);
	}

	public String checkLogin(User user) throws UnauthorizedException, PersistentException, UserException {
		if (user == null) throw new UnauthorizedException("User is null");
		User userSaved = userService.getUserByEmail(user.getEmail());
		if (userSaved == null) {
			throw new UnauthorizedException("User not found");
		}

		if (!userService.checkPassword(user.getPassword(), userSaved.getPassword())){
			throw new UnauthorizedException("Invalid password");
		}
		return jwtService.generateToken(userSaved);
	}

	public String login(String email, String password) throws PersistentException, UserException {
		if (email == null) throw new UnauthorizedException("Email is null");
		if (password == null) throw new UnauthorizedException("Password is null");

		User user = new User();
		user.setEmail(email);
		user.setPassword(password);

		// Se tudo ok → gera e devolve token:
		return checkLogin(user);
	}

	public String getUserIdFromToken(String token) {
		return jwtService.extractUserId(token);
	}

	public String getUserNameFromToken(String token) {
		return jwtService.extractName(token);
	}

	public String getUserProfilePictureFromToken(String token) {
		return jwtService.extractUserProfilePicture(token);
	}

	public boolean checkToken(String token) {
		try {
			return token != null && jwtService.validateToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Cookie createCookie(String email, String password) throws PersistentException, UserException {
		String token = login(email, password);
		Cookie cookie = new Cookie("token", token);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setMaxAge(24 * 60 * 60);
		return cookie;
	}

	public Cookie deleteCookie(){
		Cookie cookie = new Cookie("token", "");
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		return cookie;
	}

}