package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import daos.ReimbDao;
import daos.UserDao;
import models.Reimbursement;
import models.User;

@SuppressWarnings("serial")
public class Main extends HttpServlet {

	private User loggedIn = null;

	UserDao userDao = UserDao.currentImplementation;
	ReimbDao reimbDao = ReimbDao.currentImplementation;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.addHeader("Access-Control-Allow-Origin", "http://localhost:5500");
		resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		resp.addHeader("Access-Control-Allow-Headers",
				"Origin, Methods, Credentials, X-Requested-With, Content-Type, Accept");
		resp.addHeader("Access-Control-Allow-Credentials", "true");
		resp.setContentType("application/json");
		// TODO Auto-generated method stub
		super.service(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ObjectMapper om = new ObjectMapper(); // make object mapper

		if (Login.loggedInUser != null) {
			loggedIn = Login.loggedInUser;
		} else {
			resp.setStatus(301); // if status = 301 == no user is logged in, redirect to main menu
			return;
		}

		List<Reimbursement> reimbs = null;

		if (loggedIn.getRoleId() == 1) {
			reimbs = reimbDao.findByUserID(loggedIn.getId());
		}

		if (loggedIn.getRoleId() == 2) {
			reimbs = reimbDao.findAll();
		}
		
		String json = om.writeValueAsString(reimbs);

		resp.addHeader("content-type", "application/json");
		resp.getWriter().write(json);
	}
	//enter / enterUser

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		loggedIn = Login.loggedInUser;
		System.out.println("request incoming");
		if ("/ReimbProj/main".equals(req.getRequestURI()) && loggedIn.getRoleId() == 1) {
			ObjectMapper om = new ObjectMapper(); // make object mapper
			Reimbursement newReimb = (Reimbursement) om.readValue(req.getReader(), Reimbursement.class); //
			reimbDao.makeReimb(newReimb.getAmount(), newReimb.getDescription(), loggedIn.getId(), newReimb.getType(0));
			resp.setStatus(201);
		} else {
			resp.setStatus(403);
		}
	}
	//create reimbursement
	
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if ("/ReimbProj/main".equals(req.getRequestURI())) {
			ObjectMapper om = new ObjectMapper(); // make object mapper
			Reimbursement resolveReimb = (Reimbursement) om.readValue(req.getReader(), Reimbursement.class); //
			reimbDao.resolve(resolveReimb.getReimbId(), loggedIn.getId(), resolveReimb.getStatus(0));
		}
	}//deny / approve

	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if ("/ReimbProj/main".equals(req.getRequestURI())) {
			ObjectMapper om = new ObjectMapper(); // make object mapper
			Login.loggedInUser = null;
			loggedIn = null;
		}
	}// logout
}