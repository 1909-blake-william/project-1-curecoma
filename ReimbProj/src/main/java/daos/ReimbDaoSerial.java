package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import models.Reimbursement;
import util.ConnectionUtil;

public class ReimbDaoSerial implements ReimbDao {

	@Override
	public Reimbursement extractReimbursement(ResultSet rs) {
		int reimbId;
		int amount;
		LocalDateTime created;
		LocalDateTime resolved;
		String description;
		int author;
		int resolver;
		int status;
		int type;
		try {
			reimbId = Integer.parseInt(rs.getString("REIMB_ID"));
			amount = Integer.parseInt(rs.getString("REIMB_AMOUNT"));
			created = rs.getTimestamp("REIMB_SUBMITTED").toLocalDateTime();
			author = Integer.parseInt(rs.getString("REIMB_AUTHOR"));
			status = Integer.parseInt(rs.getString("REIMB_STATUS_ID"));
			type = Integer.parseInt(rs.getString("REIMB_TYPE_ID"));

			try {
				description = rs.getString("REIMB_DESCRIPTION");
			} catch (Exception e) {
				description = " ";
			}

			try {
				resolved = rs.getTimestamp("REIMB_RESOLVED").toLocalDateTime();
			} catch (Exception e) {
				resolved = null;
			}

			try {
				resolver = Integer.parseInt(rs.getString("REIMB_RESOLVER"));
			} catch (Exception e) {
				resolver = -1;
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return new Reimbursement(reimbId, amount, created, resolved, description, author, resolver, status, type);
	}

	@Override
	public long save(Reimbursement r) {
		try (Connection c = ConnectionUtil.getConnection()) {
			String str = "INSERT INTO ERS_REIMBURSEMENT (REIMB_ID, REIMB_AMOUNT, REIMB_SUBMITTED, REIMB_RESOLVED, REIMB_DESCRIPTION, REIMB_AUTHOR, REIMB_RESOLVER, REIMB_STATUS_ID, REIMB_TYPE_ID) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement ps = c.prepareStatement(str);
			ps.setString(1, "" + r.getReimbId());
			ps.setString(2, "" + r.getAmount());
			ps.setString(3, "" + r.getResolved());
			ps.setString(4, "" + r.getCreated());
			ps.setString(5, "" + r.getDescription());
			ps.setString(6, "" + r.getAuthor());
			ps.setString(7, "" + r.getResolver());
			ps.setString(8, "" + r.getStatus());
			ps.setString(9, "" + r.getType());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r.getReimbId();
	}

	@Override
	public List<Reimbursement> findAll() {
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "select * from ers_reimbursement" + "" + "INNER JOIN ers_reimbursement_status"
					+ "ON ers_reimbursement.reimb_status_id = ers_reimbursement_status.reimb_status_id" + ""
					+ "INNER JOIN  ers_reimbursement_type"
					+ "ON ers_reimbursement.reimb_type_id = ers_reimbursement_type.reimb_type_id" + ""
					+ "INNER JOIN  ers_users" + "ON ers_reimbursement.reimb_author = ers_users.ers_users_id";

			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Reimbursement> reimbs = new ArrayList<>();
			while (rs.next()) {
				reimbs.add(extractReimbursement(rs));
			}
			return reimbs;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Reimbursement findByID(int reimbId) {
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "select * from ers_reimbursement" + "" + "INNER JOIN ers_reimbursement_status"
					+ "ON ers_reimbursement.reimb_status_id = ers_reimbursement_status.reimb_status_id" + ""
					+ "INNER JOIN  ers_reimbursement_type"
					+ "ON ers_reimbursement.reimb_type_id = ers_reimbursement_type.reimb_type_id" + ""
					+ "INNER JOIN  ers_users" + "ON ers_reimbursement.reimb_author = ers_users.ers_users_id"
					+ "" + "WHERE REIMB_ID = ?";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "" + reimbId);
			ResultSet rs = ps.executeQuery();
			List<Reimbursement> reimbs = new ArrayList<>();
			while (rs.next()) {
				reimbs.add(extractReimbursement(rs));
			}

			return reimbs.get(0);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Reimbursement> findByUserID(int userId) {
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "select * from ers_reimbursement" + "" + "INNER JOIN ers_reimbursement_status"
					+ "ON ers_reimbursement.reimb_status_id = ers_reimbursement_status.reimb_status_id" + ""
					+ "INNER JOIN  ers_reimbursement_type"
					+ "ON ers_reimbursement.reimb_type_id = ers_reimbursement_type.reimb_type_id" + ""
					+ "INNER JOIN  ers_users" + "ON ers_reimbursement.reimb_author = ers_users.ers_users_id"
					+ "" + "WHERE REIMB_AUTHOR = ?";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "" + userId);
			ResultSet rs = ps.executeQuery();
			List<Reimbursement> reimbs = new ArrayList<>();
			while (rs.next()) {
				reimbs.add(extractReimbursement(rs));
			}

			return reimbs;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int makeReimb(int amount, String description, int author, int type) {
		try (Connection c = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO ers_reimbursement(" + "    REIMB_ID,   REIMB_AMOUNT,   REIMB_SUBMITTED,"
					+ "    REIMB_RESOLVED, REIMB_DESCRIPTION,  REIMB_AUTHOR,"
					+ "    REIMB_RESOLVER, REIMB_STATUS_ID,    REIMB_TYPE_ID)" + "    VALUES("
					+ "    REIMB_ID_seq.nextval, ?, CURRENT_TIMESTAMP," + "    null, ?, ?," + "    null,1,?)";

			System.out.println(author);
			System.out.println(type);
			
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "" + amount);
			ps.setString(2, "" + description);
			ps.setString(3, "" + author);
			ps.setString(4, "" + type);

			ps.executeQuery();
			return amount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int resolve(int id, int resolver, int status) {
		try (Connection c = ConnectionUtil.getConnection()) {
			String sql = "UPDATE ers_reimbursement"
					+ "SET REIMB_RESOLVER = ?, REIMB_RESOLVED = CURRENT_TIMESTAMP, REIMB_STATUS_ID = ?"
					+ "WHERE REIMB_ID = ?";

			System.out.println(id);
			System.out.println(resolver);
			System.out.println(status);
			
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "" + resolver);
			ps.setString(2, "" + status);
			ps.setString(3, "" + id);

			return ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

}
