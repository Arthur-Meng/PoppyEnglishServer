package com.poppyenglish;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * 接收图片
 * 
 */

public class ReceiveImgServlet extends HttpServlet {

	/**
	 * 
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		SmartUpload smartUpload = new SmartUpload();
		try {
			smartUpload.initialize(this.getServletConfig(), request, response);
			smartUpload.upload();
			String msg = smartUpload.getRequest().getParameter("msg");
			if (msg != null) {
				com.jspsmart.upload.Files files = smartUpload.getFiles();
				for (int i = 0; i < files.getCount(); i++) {
					com.jspsmart.upload.File file = files.getFile(i);
					if (!file.isMissing()) {
						String filename = this.getServletContext().getRealPath("/") + "..\\Pic\\" + msg + ".jpg";
						file.saveAs(filename);
					}
				}
			}

		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}