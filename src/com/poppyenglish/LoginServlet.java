package com.poppyenglish;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		String tel = request.getParameter("tel");
		String password = request.getParameter("password");

		UserDoDAO userDao = new UserDoDAO();
		Thread thread = new Thread() {
			public void run() {

				MyServer myServer = MyServer.getInstance();
				if (myServer.ifStart == false) {
					myServer.startServer();
				}
			}
		};
		thread.start();
		if (userDao.findByTel(tel) != null) {
			User user = userDao.findByTel(tel);
			if (user.getTel() == null) {
				response.getOutputStream().print("NoUser");
			} else if (user.getPassword() == null) {
				response.getOutputStream().print("Error");
			} else {
				if (user.getPassword().equals(password)) {
					response.getOutputStream().write((user.getName() + "\n").getBytes("UTF-8"));
					response.getOutputStream().write((user.getGender() + "\n").getBytes("UTF-8"));
					response.getOutputStream().write((user.getHonor() + "\n").getBytes("UTF-8"));
					response.getOutputStream().write((user.getComment() + "\n").getBytes("UTF-8"));
				} else {
					response.getOutputStream().print("WrongPassword");
				}
			}
		} else {
			response.getOutputStream().print("NoUser");
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
