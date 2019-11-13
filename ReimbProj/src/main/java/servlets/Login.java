package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.UserDao;
import models.User;

@SuppressWarnings("serial")
public class Login extends HttpServlet {

	UserDao userDao = UserDao.currentImplementation;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String username = req.getParameter("uname");
		String password = req.getParameter("psw");
		
		User user = userDao.findByCred(username,password);
		
		if (user!=null) {
			req.getSession().setAttribute("currentUser", username);
			req.getSession().setAttribute("admin", user.getRoleId());
			req.getRequestDispatcher("/main.html").include(req, resp);
			return;
		} else {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
	}
}