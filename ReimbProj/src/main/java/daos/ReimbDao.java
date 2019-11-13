package daos;

import java.sql.ResultSet;
import java.util.List;

import models.Reimbursement;

public interface ReimbDao {


	UserDao currentImplementation = new UserDaoSerial();

	/**
	 * used to save a new user
	 * 
	 * @param u the user to be created
	 * @return the generated id for the user
	 */
	Reimbursement extractReimbursement(ResultSet rs);
	
	long save(Reimbursement u);

	List<Reimbursement> findAll();

}
