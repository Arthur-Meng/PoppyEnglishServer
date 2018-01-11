package com.poppyenglish;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShareServlet
 */
public class ShareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShareServlet() {
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

		String url = "jdbc:mysql://127.0.0.1:3306/poppyenglish?useUnicode=true&characterEncoding=utf-8&useSSL=true";
		String driver = "com.mysql.jdbc.Driver";
		String sqluser = "root";
		String psw = "";

		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, sqluser, psw);
			PreparedStatement pstmt = ((java.sql.Connection) con)
					.prepareStatement("SELECT * FROM share where me='" + tel + "'");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				response.getOutputStream().write((rs.getString("you") + "\n").getBytes("UTF-8"));
				response.getOutputStream().write((rs.getString("time") + "\n").getBytes("UTF-8"));
				response.getOutputStream().write((rs.getString("number") + "\n").getBytes("UTF-8"));
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getOutputStream().write(("\n").getBytes("UTF-8"));
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
