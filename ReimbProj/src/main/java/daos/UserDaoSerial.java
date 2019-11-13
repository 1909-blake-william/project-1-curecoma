package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.User;
import util.ConnectionUtil;

public class UserDaoSerial implements UserDao {

	public User extractUser(ResultSet rs) {
		int id;
		String name;
		String password;
		String firstname;
		String lastname;
		String email;
		int roleId;
		try {
			id = Integer.parseInt(rs.getString("ERS_USERS_ID"));
			name = rs.getString("ERS_USERNAME");
			password = rs.getString("ERS_PASSWORD");
			firstname = rs.getString("USER_FIRST_NAME");
			lastname = rs.getString("USER_LAST_NAME");
			email = rs.getString("USER_EMAIL");
			roleId = Integer.parseInt(rs.getString("USER_ROLE_ID"));

		} catch (NumberFormatException e) {
			return null;
		} catch (SQLException e) {
			return null;
		}
		return new User(id, name, password, firstname, lastname, email, roleId);
	}

	@Override
	public long save(User u) {
		try (Connection c = ConnectionUtil.getConnection()) {
			String str = "INSERT INTO ERS_USERS (ERS_USERS_ID, ERS_USERNAME, ERS_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_ROLE_ID) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement ps = c.prepareStatement(str);
			ps.setString(1, "" + u.getId());
			ps.setString(2, "" + u.getName());
			ps.setString(3, "" + u.getPassword());
			ps.setString(4, "" + u.getFirstName());
			ps.setString(5, "" + u.getLastName());
			ps.setString(6, "" + u.getEmail());
			ps.setString(7, "" + u.getRoleId());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u.getId();
	}

	@Override
	public List<User> findAll() {
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM ERS_USERS";

			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<User> users = new ArrayList<>();
			while (rs.next()) {
				users.add(extractUser(rs));
			}

			return users;

		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public User findByCred(String username, String password) {
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM ERS_USERS WHERE ERS_USERNAME = ? AND ERS_PASSWORD = ?";

			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<User> users = new ArrayList<>();
			while (rs.next()) {
				users.add(extractUser(rs));
			}

			return users.get(0);

		} catch (SQLException e) {
			return null;
		}
	}
}
