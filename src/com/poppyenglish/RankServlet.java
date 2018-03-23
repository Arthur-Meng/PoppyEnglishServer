package com.poppyenglish;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RankServlet
 */
public class RankServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RankServlet() {
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
		UserDoDAO userDao = new UserDoDAO();
		ArrayList<User> userList = (ArrayList<User>) userDao.rank();
		int myRank = userDao.findMyRank(tel, userList);
		response.getOutputStream().write((myRank + "\n").getBytes("UTF-8"));
		response.getOutputStream().write((userList.get(0).getName() + "\n").getBytes("UTF-8"));
		response.getOutputStream().write((userList.get(0).getTel() + "\n").getBytes("UTF-8"));
		response.getOutputStream().write((userList.get(1).getName() + "\n").getBytes("UTF-8"));
		response.getOutputStream().write((userList.get(1).getTel() + "\n").getBytes("UTF-8"));
		response.getOutputStream().write((userList.get(2).getName() + "\n").getBytes("UTF-8"));
		response.getOutputStream().write((userList.get(2).getTel() + "\n").getBytes("UTF-8"));
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
