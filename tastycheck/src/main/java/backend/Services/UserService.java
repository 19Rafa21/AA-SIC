package backend.Services;

import backend.Criteria.UserCriteria;
import backend.DAOs.OwnerDAO;
import backend.DAOs.UserDAO;
import backend.DTOs.EditUserDTO;
import backend.DTOs.RestaurantDTO;
import backend.DTOs.UserDTO;
import backend.Exceptions.UserException;
import backend.Models.Owner;
import backend.Models.Restaurant;
import backend.Models.Review;
import backend.Models.User;
import org.orm.PersistentException;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static backend.Services.RestaurantService.toRestaurantEdit;

public class UserService {

	public boolean registerUser(UserDTO dto) throws PersistentException {
		if (usernameExists(dto.getUsername())) {
			throw new IllegalArgumentException("Username já existe!");
		}

		String hashedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
		dto.setPassword(hashedPassword);

		String discriminator = dto.getDiscriminator();

		if (discriminator.equalsIgnoreCase("User")){
			User user = toUser(dto);
			return UserDAO.save(user);
		}
		else if (discriminator.equalsIgnoreCase("Owner")){
			Owner user = toOwner(dto);
			return UserDAO.save(user);
		}

		return false;
	}


	public boolean updateUser(EditUserDTO dto,String id) {
		try {
			User u2 = getUserById(id);

			u2 = toEditUser(dto,u2);

			UserDAO.save(u2);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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

	public Owner getOwnerByUsername(String username) throws PersistentException, UserException {
		Owner owner = OwnerDAO.loadOwnerByQuery("username = '" + username + "' AND Discriminator = 'Owner'", null);
		if (owner == null) {
			throw new UserException("User with username '" + username + "' does not exist or isn't a owner!");
		}
		return owner;
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

	public boolean deleteUser(String id) throws PersistentException, UserException {
		User user = getUserById(id);
		if(user==null) throw new UserException("User with ID: '" + id + "' does not exist");

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

	public List<User> getAllUsers() throws PersistentException {
		User[] users = UserDAO.listUserByQuery(null, null);
		return List.of(users);
	}

	public List<Review> getReviewsByUserId(String userId) throws PersistentException, UserException {
		User user = getUserById(userId);
		return new ArrayList<>(user.getReviews());
	}

	public List<Review> getReviewsByUsername(String username) throws PersistentException, UserException {
		User user = getUserByUsername(username);
		return new ArrayList<>(user.getReviews());
	}

	public List<Restaurant> getRestaurantsByOwner(String username) throws PersistentException, UserException {
		Owner owner = getOwnerByUsername(username);
		return new ArrayList<>(owner.getRestaurants());
	}

	public List<User> listAllUsers() {
		try {
			UserCriteria criteria = new UserCriteria();
			// Sem filtros → devolve todos os utilizadores
			return List.of(criteria.listUser());
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public static User toUser(UserDTO dto) {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		return user;
	}

	public static Owner toOwner(UserDTO dto) {
		Owner user = new Owner();
		user.setId(UUID.randomUUID().toString());
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		return user;
	}

	public static User toEditUser(EditUserDTO dto, User user) {
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		return user;
	}
}
