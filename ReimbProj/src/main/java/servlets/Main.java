package servlets;

import java.io.IOException;

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

		if ("/ReimbProj/main".equals(req.getRequestURI()) && loggedIn.getRoleId() == 1/* user is non-manager */) {
			// send huge chunk of html that is described in comment of main.html

		} else if ("/ReimbProj/main".equals(req.getRequestURI()) && loggedIn.getRoleId() == 2/* user is manager */) {

		} else /* redirect to login */ {
			resp.setStatus(301); // if status = 301 == no user is logged in, redirect to main menu
			return;
		}
	}
	// get parameter only works on doGet

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		loggedIn = Login.loggedInUser;
		System.out.println("request incoming");
		if ("/ReimbProj/main".equals(req.getRequestURI())) {
			ObjectMapper om = new ObjectMapper(); // make object mapper
			Reimbursement newReimb = (Reimbursement) om.readValue(req.getReader(), Reimbursement.class); //
			reimbDao.makeReimb(newReimb.getAmount(), newReimb.getDescription(), loggedIn.getId(), newReimb.getType(0));
			resp.setStatus(201);
		}
	}

	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if ("/ReimbProj/main".equals(req.getRequestURI())) {
			ObjectMapper om = new ObjectMapper(); // make object mapper
			Reimbursement resolveReimb = (Reimbursement) om.readValue(req.getReader(), Reimbursement.class); //
			reimbDao.resolve(resolveReimb.getReimbId(), loggedIn.getId(), resolveReimb.getType(0));
		}
	}

	public String rowTextMakerManager(int ticketID) {
		Reimbursement r = reimbDao.findByID(ticketID);

		String rowText = "<tr id=\"row" + r.getReimbId() + "\" title=\"" + r.getDescription() + "\">\n"
				+ "<td id=\"reimbId\">" + r.getReimbId() + "</td>\n" + "<td id=\"amount\">$" + r.getAmount() + "</td>\n"
				+ "<td id=\"requestor\">" + r.getAuthor() + "</td>\n" + "<td id=\"type\">" + r.getType() + "</td>\n"
				+ "<td id=\"status\">" + r.getStatus() + "</td>\n" + "<td id=\"tCreated\">" + r.getCreated() + "</td>\n"
				+ "<td id=\"tResoved\"" + r.getResolved() + "</td>\n"
				+ "<td id=\"aButon\"><button type=\"button\" onclick=\"approveOne(" + r.getReimbId()
				+ ")\" class=\"littleButton\">Approve</button></td>\n"
				+ "<td id=\"dButton\"><button type=\"button\" onclick=\"denyOne(" + r.getReimbId()
				+ ")\" class=\"littleButton\">Deny</button></td>\n"
				+ "<td id=\"checker\"><input type=\"checkbox\" value=\"selected\"></td>\n" + "</tr>";
		return rowText;
	}

	public String rowTextMakerUserOrResolved(int ticketID) {
		Reimbursement r = reimbDao.findByID(ticketID);

		String rowText = "<tr id=\"row" + r.getReimbId() + "\" title=\"" + r.getDescription() + "\">\n"
				+ "<td id=\"reimbId\">" + r.getReimbId() + "</td>\n" + "<td id=\"amount\">$" + r.getAmount() + "</td>\n"
				+ "<td id=\"requestor\">" + r.getAuthor() + "</td>\n" + "<td id=\"type\">" + r.getType() + "</td>\n"
				+ "<td id=\"status\">" + r.getStatus() + "</td>\n" + "<td id=\"tCreated\">" + r.getCreated() + "</td>\n"
				+ "<td id=\"tResoved\"" + r.getResolved() + "</td>\n" + "</tr>";
		System.out.println(rowText);
		return rowText;
	}

}