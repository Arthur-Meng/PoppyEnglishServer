package com.poppyenglish;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddShareServlet
 */
public class AddShareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddShareServlet() {
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
		String number = request.getParameter("number");
		UserDoDAO userDao = new UserDoDAO();
		User user = userDao.findByTel(tel);
		String[] friends = user.getFriendall();

		String url = "jdbc:mysql://127.0.0.1:3306/poppyenglish?useUnicode=true&characterEncoding=utf-8&useSSL=true";
		String driver = "com.mysql.jdbc.Driver";
		String sqluser = "root";
		String psw = "";
		String strsql = "insert into share(me,you,time,number,up) values(?,?,?,?,?)";
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, sqluser, psw);
			PreparedStatement pstmt = ((java.sql.Connection) con).prepareStatement(strsql);
			for (int i = 0; i < friends.length; i++) {
				if (!friends[i].equals("")) {
					pstmt.setString(1, friends[i]);
					pstmt.setString(2, tel);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = df.format(new Date());
					pstmt.setString(3, time);
					pstmt.setString(4, number);
					pstmt.setString(5, "0");
					pstmt.executeUpdate();
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getOutputStream().write(("Error" + "\n").getBytes("UTF-8"));
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
