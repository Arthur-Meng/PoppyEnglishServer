package com.poppyenglish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		UserDoDAO userDao = new UserDoDAO();
		User user = userDao.findByTel("17816876766");
		List<User> userList=userDao.findByName("MJW");
		System.out.print(userList.get(0).getTel());
	}

}
