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
			created = LocalDateTime.parse(rs.getString("REIMB_SUBMITTED"));
			resolved = LocalDateTime.parse(rs.getString("REIMB_RESOLVED"));
			description = rs.getString("REIMB_DESCRIPTION");
			author = Integer.parseInt(rs.getString("REIMB_AUTHOR"));
			resolver = Integer.parseInt(rs.getString("REIMB_RESOLVER"));
			status = Integer.parseInt(rs.getString("REIMB_STATUS_ID"));
			type = Integer.parseInt(rs.getString("REIMB_TYPE_ID"));

		} catch (NumberFormatException e) {
			return null;
		} catch (SQLException e) {
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

			String sql = "SELECT * FROM ERS_REIMBURSEMENT";

			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Reimbursement> users = new ArrayList<>();
			while (rs.next()) {
				users.add(extractReimbursement(rs));
			}

			return users;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
