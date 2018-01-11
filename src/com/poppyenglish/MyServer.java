package com.poppyenglish;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.net.Socket;

public class MyServer {
	// 定义ServerSocket的端口号
	private static final int SOCKET_PORT = 50000;
	// 使用ArrayList存储所有的Socket
	public static ArrayList<Socket> socketList = new ArrayList<Socket>();
	//正在匹配的
	public static ArrayList<Socket> matchtList = new ArrayList<Socket>();
	//正在游戏的
	public static ArrayList<Socket> matchtedList = new ArrayList<Socket>();
	public static Map<String, Object> urlTel = new HashMap<String, Object>();
	private static MyServer instance = new MyServer();
	public static Boolean ifStart = false;

	private MyServer() {
	}

	public void startServer() {
		try {
			ifStart = true;
			// 创建一个ServerSocket，用于监听客户端Socket的连接请求
			ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);

			while (true) {
				// 每当接收到客户端的Socket请求，服务器端也相应的创建一个Socket
				Socket socket = serverSocket.accept();
				if (null != socket) {
					InetAddress clientUrl = socket.getInetAddress();
					ArrayList<Socket> newList = new ArrayList<Socket>();
					newList.addAll(socketList);
					for (Socket one : newList) {
						if (one.getInetAddress().equals(clientUrl)) {
							socketList.remove(one);
						}
					}
					socketList.add(socket);
					System.out.println(socketList);
					// 每连接一个客户端，启动一个ServerThread线程为该客户端服务
					new Thread(new ServerThread(socket)).start();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static MyServer getInstance() {
		return instance;
	}

}
