package com.poppyenglish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SignUpServlet
 */
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUpServlet() {
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
		String password = request.getParameter("password");
		String code = "";

		if (request.getParameter("password") == null) {

			String postUrl = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
			String account = "C65926958"; // APIID
			String textpassword = "6a42bfefebcc7f0042d2cf163b846beb"; // APIKEY
			String line, result1 = "";

			for (int i = 0; i < 6; i++) {
				code += String.valueOf((int) (Math.random() * 9));
			}
			HttpSession session = request.getSession();
			session.setAttribute("code", code);
			String content = new String("您的验证码是：" + code + "。请不要把验证码泄露给其他人。");
			try {

				URL url = new URL(postUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.setRequestProperty("Connection", "Keep-Alive");
				StringBuffer sb = new StringBuffer();
				sb.append("account=" + account);
				sb.append("&password=" + textpassword);
				sb.append("&mobile=" + tel);
				sb.append("&content=" + content);
				OutputStream os = connection.getOutputStream();
				os.write(sb.toString().getBytes());
				os.close();

				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
				while ((line = in.readLine()) != null) {
					result1 += line + "\n";
				}
				in.close();

				response.getOutputStream().print(code);

			} catch (IOException e) {
				response.getOutputStream().print("error");
				e.printStackTrace(System.out);
			}
		} else {
			try {
			UserDoDAO userDao = new UserDoDAO();
			User user = new User();
			user.setTel(tel);
			user.setPassword(password);
			userDao.save(user);
			response.getOutputStream().print("Yes");
			}catch (Exception e) {
				response.getOutputStream().print("No");
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
