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
			// Session, cookies, hidden form field => worst practice
			// On a cookie, you specify a name, a value, and path (At least). Cookies are stored on the browser,
			// and every time the browser send an HTTP request to that path, the cookie will be included
			req.getSession().setAttribute("currentUser", username);
			//req.getSession().setAttribute("admin", user.getIsAdmin);
			
			// At this point in time, what are you trying to do? 
			// Go to the home page!
			// This is not used in REST API's. Only when using Server Side Rendering (In our case, JSP).
			req.getRequestDispatcher("/main.html").include(req, resp);
			return;
		} else {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
	}
}