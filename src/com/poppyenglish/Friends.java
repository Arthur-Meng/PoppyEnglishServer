package com.poppyenglish;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Friends
 */
public class Friends extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Friends() {
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
		User user = userDao.findByTel(tel);
		if (request.getParameter("find") != null) {
			String find = request.getParameter("find");
			User findfriend = userDao.findByTel(find);
			List<User> userList = userDao.findByName(find);
			// 名字和电话都会查找
			if (findfriend.getName() != null) {
				response.getOutputStream().write((findfriend.getName() + "\n").getBytes("UTF-8"));
				response.getOutputStream().write((findfriend.getGender() + "\n").getBytes("UTF-8"));
				response.getOutputStream().write((findfriend.getHonor() + "\n").getBytes("UTF-8"));
				response.getOutputStream().write((findfriend.getComment() + "\n").getBytes("UTF-8"));
				response.getOutputStream().write((findfriend.getTel() + "\n").getBytes("UTF-8"));
			} else if (userList != null) {
				if (userList.size() > 0) {
					for (int k = 0; k < userList.size(); k++) {
						response.getOutputStream().write((userList.get(k).getName() + "\n").getBytes("UTF-8"));
						response.getOutputStream().write((userList.get(k).getGender() + "\n").getBytes("UTF-8"));
						response.getOutputStream().write((userList.get(k).getHonor() + "\n").getBytes("UTF-8"));
						response.getOutputStream().write((userList.get(k).getComment() + "\n").getBytes("UTF-8"));
						response.getOutputStream().write((userList.get(k).getTel() + "\n").getBytes("UTF-8"));
					}
				} else {
					response.getOutputStream().print("NoUser");
				}
			}
		} else {
			if (request.getParameter("add") != null) {
				String add = request.getParameter("add");
				String[] friendList = null;
				if (user.getFriend() != null)
					friendList = user.getFriendall();
				List list = null;
				if (friendList != null)
					list = (List) Arrays.asList(friendList);
				if (list != null) {
					if (list.contains(add)) {
						response.getOutputStream().print("Have");
					} else {
						String newfriend = user.getFriend() + "-" + add;
						user.setFriend(newfriend);
						userDao.update(user);
						response.getOutputStream().print("Add");
					}
				} else {
					user.setFriend("-" + add);
					userDao.update(user);
					response.getOutputStream().print("Add");
				}
			} else if (request.getParameter("remove") != null) {
				String remove = request.getParameter("remove");
				String[] friendList = null;
				if (user.getFriend() != null)
					friendList = user.getFriendall();
				List list = null;
				if (friendList != null)
					list = (List) Arrays.asList(friendList);
				if (list != null) {
					if (list.contains(remove)) {
						String friendAll = "";
						for (int k = 0; k < list.size(); k++) {
							if (list.get(k).equals(remove)) {
							} else {
								if (!list.get(k).equals("")) {
									friendAll += "-" + list.get(k);
								}
							}
						}
						user.setFriend(friendAll);
						userDao.update(user);
						response.getOutputStream().print("Remove");
					} else {
						response.getOutputStream().print("Error");
					}
				} else {
					response.getOutputStream().print("Error");
				}
			} else {
				String[] friendList = null;
				if (user.getFriend() != null)
					friendList = user.getFriendall();
				List list = null;
				if (friendList != null)
					list = (List) Arrays.asList(friendList);
				if (list != null) {
					for (int k = 1; k < list.size(); k++) {
						User friends = userDao.findByTel(list.get(k).toString());
						response.getOutputStream().write((friends.getName() + "\n").getBytes("UTF-8"));
						response.getOutputStream().write((friends.getGender() + "\n").getBytes("UTF-8"));
						response.getOutputStream().write((friends.getHonor() + "\n").getBytes("UTF-8"));
						response.getOutputStream().write((friends.getComment() + "\n").getBytes("UTF-8"));
						response.getOutputStream().write((friends.getTel() + "\n").getBytes("UTF-8"));
					}
				}
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
