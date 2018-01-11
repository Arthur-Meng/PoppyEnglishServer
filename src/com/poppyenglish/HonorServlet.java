package com.poppyenglish;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HonorServlet
 */
public class HonorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HonorServlet() {
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
		String change = request.getParameter("change");
		String score = request.getParameter("score");
		UserDoDAO userDao = new UserDoDAO();
		try {
			if (userDao.findByTel(tel) != null) {
				User user = userDao.findByTel(tel);
				if (change.equals("plus")) {
					user.setHonor(String.valueOf(Integer.parseInt(user.getHonor()) + Integer.parseInt(score)));
				}
				if (change.equals("reduce")) {
					user.setHonor(String.valueOf(Integer.parseInt(user.getHonor()) - Integer.parseInt(score)));
				}
				userDao.update(user);
				response.getOutputStream().print("true");
			} else {
				response.getOutputStream().print("false");
			}
		} catch (IOException e) {
			response.getOutputStream().print("false");
			e.printStackTrace();
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
