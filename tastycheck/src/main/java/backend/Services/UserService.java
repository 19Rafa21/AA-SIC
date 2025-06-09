package backend.Services;

import backend.DAOs.OwnerDAO;
import backend.DAOs.UserDAO;
import backend.Exceptions.UserException;
import backend.Models.Owner;
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

	public boolean registerOwner(Owner owner) throws  PersistentException {
		if (usernameExists(owner.getUsername())) {
			throw new IllegalArgumentException("Username já existe!");
		}

		String hashedPassword = BCrypt.hashpw(owner.getPassword(), BCrypt.gensalt());
		owner.setPassword(hashedPassword);

		return OwnerDAO.save(owner);
	}

	public User getUserById(String id) throws PersistentException, UserException {
		User user = UserDAO.getUserByORMID(id);
		if (user == null) {
			throw new UserException("User with ID: '" + id + "' does not exist");
		}
		return user;
	}

	public User getUserByUsername(String username) throws PersistentException, UserException {
		User user = UserDAO.loadUserByQuery("username = '" + username + "'", null);
		if (user == null) {
			throw new UserException("User with username '" + username + "' does not exist");
		}
		return user;
	}

	public boolean usernameExists(String username) throws PersistentException {
		User user = UserDAO.loadUserByQuery("username = '" + username + "'", null);
		return user != null;
	}

	public boolean checkPassword(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);
	}

	public User login(String username, String password) throws PersistentException {
		try {
			User user = getUserByUsername(username);

			if (user == null) {
				System.out.println("Login falhou: username não existe.");
				return null;
			}

			boolean passwordMatch = checkPassword(password, user.getPassword());

			if (passwordMatch) {
				System.out.println("Login bem-sucedido para o user: " + username);
				return user;
			} else {
				System.out.println("Login falhou: password incorreta.");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}

	public boolean deleteUser(User user) throws PersistentException {
		return UserDAO.delete(user);
	}

	public boolean deleteUserByUsername(String username) throws PersistentException, UserException {
		User user = getUserByUsername(username);
		if (user != null) {
			return UserDAO.delete(user);
		} else {
			return false;
		}
	}
}
