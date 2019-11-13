package daos;

import java.sql.ResultSet;
import java.util.List;

import models.User;

public interface UserDao {

	UserDao currentImplementation = new UserDaoSerial();

	/**
	 * used to save a new user
	 * 
	 * @param u the user to be created
	 * @return the generated id for the user
	 */
	User extractUser(ResultSet rs);
	
	long save(User u);

	List<User> findAll();

	User findByCred(String username, String password);


}
