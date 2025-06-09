package backend.Services;

import backend.DAOs.UserDAO;
import backend.Models.User;
import org.orm.PersistentException;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {

	public boolean registerUser(User user) throws PersistentException {
		if (usernameExists(user.getUsername())) {
			throw new IllegalArgumentException("Username já existe!");
		}

		String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashedPassword);

		return UserDAO.save(user);
	}

	public User getUserById(String id) throws PersistentException {
		return UserDAO.getUserByORMID(id);
	}

	public boolean usernameExists(String username) throws PersistentException {
		User user = UserDAO.loadUserByQuery("username = '" + username + "'", null);
		return user != null;
	}

	public boolean checkPassword(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);
	}
}
