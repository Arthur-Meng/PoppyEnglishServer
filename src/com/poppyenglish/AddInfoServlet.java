package com.poppyenglish;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddInfoServlet
 */
public class AddInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddInfoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String tel = request.getParameter("tel");
		
		UserDoDAO userDao = new UserDoDAO();

		if (userDao.findByTel(tel) != null) {
			User user = userDao.findByTel(tel);
			try {
				if (request.getParameter("name") != null) {
					String name = request.getParameter("name");
					user.setName(name);
				}
				if (request.getParameter("gender") != null) {
					String gender = request.getParameter("gender");
					user.setGender(gender);
				}
				if (request.getParameter("password") != null) {
					String password = request.getParameter("password");
					user.setPassword(password);
				}
				if (request.getParameter("comment") != null) {
					String comment = request.getParameter("comment");
					user.setComment(comment);
				}
				
				userDao.update(user);
				response.getOutputStream().print("AddSuccess");
				}catch (Exception e) {
					response.getOutputStream().print("AddFail");
					e.printStackTrace(System.out);
				}
				
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
