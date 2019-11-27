package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import daos.ReimbDao;
import daos.UserDao;
import models.User;

@SuppressWarnings("serial")
public class Login extends HttpServlet {

	static User loggedInUser = null;

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
		req.setAttribute("user", null);
		System.out.println("login info reset");
	}

	// get parameter only works on doGet

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if ("/ReimbProj/login".equals(req.getRequestURI())) {
			ObjectMapper om = new ObjectMapper(); // make object mapper
			User credentials = (User) om.readValue(req.getReader(), User.class); //
			System.out.println(credentials.getUsername() + ":" + credentials.getPassword());
			loggedInUser = userDao.findByCred(credentials.getUsername(), credentials.getPassword());
			if (loggedInUser == null) {
				resp.setStatus(401); // Unauthorized status code
				return;
			} else if (loggedInUser.getRoleId() == 1) {
				resp.setStatus(201);
				req.getSession().setAttribute("user", loggedInUser);
				resp.getWriter().write(om.writeValueAsString(loggedInUser));
				User u = (User) req.getSession().getAttribute("user");
				return;
			} else if (loggedInUser.getRoleId() == 2) {
				resp.setStatus(251);
				req.getSession().setAttribute("user", loggedInUser);
				resp.getWriter().write(om.writeValueAsString(loggedInUser));
				return;
			}
		}
	}

}