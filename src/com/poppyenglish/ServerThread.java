package com.poppyenglish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ServerThread implements Runnable {
	// 定义当前线程所处理的Socket
	private Socket socket = null;
	// 该线程所处理的Socket对应的输入流

	private BufferedReader bufferedReader = null;

	public ServerThread(Socket socket) throws IOException {
		this.socket = socket;
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void run() {
		UserDoDAO userDao = new UserDoDAO();
		String contentAll = null;
		String[] content = null;
		try {
			while (true) {
				while (null !=(contentAll = bufferedReader.readLine()) ) {
					int i = 0;
					// content是信息集合
					content = contentAll.split(":");
					// 初始化
					ArrayList<Socket> matchtList = new ArrayList<Socket>();
					matchtList.addAll(MyServer.matchtList);
					String myUrl = socket.getInetAddress().toString();

					if (content[0].equals("remove")) {
						if (MyServer.matchtList.contains(socket)) {
							MyServer.matchtList.remove(socket);
							matchtList.clear();
							matchtList.addAll(MyServer.matchtList);
							System.out.println("remove from matchtList");
						}
						if (MyServer.matchtedList.contains(socket)) {
							MyServer.matchtedList.remove(socket);
							System.out.println("remove from matchtedList");
						}
					} else if (content[0].equals("register")) {
						// 加入url和tel和socket映射关联的map
						if (MyServer.urlTel.get(myUrl) == null) {
							MyServer.urlTel.put(myUrl, content[1]);
							MyServer.urlTel.put(content[1], socket);
						} else {
							MyServer.urlTel.remove(myUrl);
							MyServer.urlTel.remove(content[1]);
							MyServer.urlTel.put(myUrl, content[1]);
							MyServer.urlTel.put(content[1], socket);
						}
					} else {
						// 加入url和tel和socket映射关联的map
						if (MyServer.urlTel.get(myUrl) == null) {
							MyServer.urlTel.put(myUrl, content[0]);
							MyServer.urlTel.put(content[0], socket);
						} else {
							MyServer.urlTel.remove(myUrl);
							MyServer.urlTel.remove(content[0]);
							MyServer.urlTel.put(myUrl, content[0]);
							MyServer.urlTel.put(content[0], socket);
						}

						if (content[1].equals("friendmatch")) {
							// 以match为中间的信息
							try {
								// 好友pk，match后面加了tel字段
								System.out.println("friendpk_matching");
								OutputStream outputStream = new PrintStream(socket.getOutputStream());
								String enemyTel = content[2];
								Socket pkSocket = (Socket) MyServer.urlTel.get(content[2]);
								if (null == pkSocket) {
									// 好友没在线
									outputStream.write((content[0] + ":noonline" + '\n').getBytes("utf-8"));
									System.out.println(content[0] + " wantpk noonline");
								} else {
									if (MyServer.matchtedList.contains(pkSocket)) {
										// 好友在忙
										outputStream.write((content[0] + ":busy" + '\n').getBytes("utf-8"));
										System.out.println(content[0] + " wantpk busy");
									} else {
										// 询问要不要加入pk
										OutputStream enemyOutputStream = new PrintStream(pkSocket.getOutputStream());
										User my = userDao.findByTel(content[0]);
										enemyOutputStream
												.write(((enemyTel + ":ifpk:" + content[0] + ":" + my.getName() + '\n'))
														.getBytes("utf-8"));
										System.out.println(enemyTel + ":ifpk:" + content[0] + ":" + my.getName());
									}
								}
							} catch (Exception e) {

							}
						} else if (content[1].equals("match")) {
							// 正常匹配，match后面没加tel字段
							System.out.println("normal_matching");
							OutputStream outputStream = new PrintStream(socket.getOutputStream());
							if (matchtList.size() == 0) {
								// 没有人在匹配时
								MyServer.matchtList.add(socket);
								outputStream.write(((content[0] + ":noothermatch" + '\n')).getBytes("utf-8"));
								System.out.println(content[0] + ":noothermatch");
							} else {
								String enemyUrl = matchtList.get(0).getInetAddress().toString();
								String enemyTel = (String) MyServer.urlTel.get(enemyUrl);
								if (!enemyTel.equals(content[0])) {
									// 两个加入matchtedList，matchtList少一
									MyServer.matchtedList.add(matchtList.get(0));
									MyServer.matchtList.remove(matchtList.get(0));
									MyServer.matchtedList.add(socket);
									System.out.println(content[0] + "enemy:" + enemyTel);
									// 返回自己的tel+tel+敌人的tel+信息
									User enemy = userDao.findByTel(enemyTel);
									outputStream.write((content[0] + ":tel:" + enemyTel + ":" + enemy.getName() + '\n')
											.getBytes("utf-8"));
									System.out.println((content[0] + ":tel:" + enemyTel + ":" + enemy.getName()));
									// 还需要通知之前正在匹配队列中的用户
									User my = userDao.findByTel(content[0]);
									OutputStream enemyOutputStream = new PrintStream(
											matchtList.get(0).getOutputStream());
									enemyOutputStream
											.write((enemyTel + ":tel:" + content[0] + ":" + my.getName() + '\n')
													.getBytes("utf-8"));
									System.out.println(enemyTel + ":tel:" + content[0] + ":" + my.getName());
									// 返回题库
									String queID = havequeID();
									outputStream.write((content[0] + ":queID:" + queID + '\n').getBytes("utf-8"));
									System.out.println((content[0] + ":queID:" + queID));
									enemyOutputStream.write((enemyTel + ":queID:" + queID + '\n').getBytes("utf-8"));
									System.out.println(enemyTel + ":queID:" + queID);
								}else{
									outputStream.write((content[0] + "您已经再匹配队列了！"+ '\n').getBytes("utf-8"));
								}
							}

						} else if (content[1].equals("laddermatch")) {
							// 天梯的pk
							// 正常匹配，match后面没加tel字段
							System.out.println("laddermatching");
							OutputStream outputStream = new PrintStream(socket.getOutputStream());
							if (matchtList.size() == 0) {
								// 没有人在匹配时
								outputStream.write(((content[0] + ":noothermatch" + '\n')).getBytes("utf-8"));
								System.out.println(content[0] + ":noothermatch");
								MyServer.matchtList.add(socket);
							} else {
								i = 0;
								// 一个一个找过去，honor相差不能超过10
								while (i < matchtList.size()) {
									String enemyUrl = matchtList.get(i).getInetAddress().toString();
									String enemyTel = (String) MyServer.urlTel.get(enemyUrl);
									if (!enemyTel.equals(content[0])) {
										User enemy = userDao.findByTel(enemyTel);
										User my = userDao.findByTel(content[0]);
										int myHonor = Integer.parseInt(my.getHonor());
										int enemyHonor = Integer.parseInt(enemy.getHonor());
										if ((myHonor - enemyHonor > 10) || (enemyHonor - myHonor > 10)) {
											i++;
										} else {
											// 两个加入matchtedList，matchtList少一
											MyServer.matchtedList.add(matchtList.get(0));
											MyServer.matchtList.remove(matchtList.get(0));
											MyServer.matchtedList.add(socket);
											System.out.println(content[0] + "enemy:" + enemyTel);
											// 返回自己的tel+tel+敌人的tel+信息
											outputStream.write(
													(content[0] + ":tel:" + enemyTel + ":" + enemy.getName() + '\n')
															.getBytes("utf-8"));
											System.out.println(content[0] + ":tel:" + enemyTel + ":" + enemy.getName());

											// 还需要通知之前正在匹配队列中的用户
											OutputStream enemyOutputStream = new PrintStream(
													matchtList.get(i).getOutputStream());
											enemyOutputStream
													.write((enemyTel + ":tel:" + content[0] + ":" + my.getName() + '\n')
															.getBytes("utf-8"));
											System.out.println(enemyTel + ":tel:" + content[0] + ":" + my.getName());
											String queID = havequeID();
											Thread.sleep(200);
											outputStream
													.write((content[0] + ":queID:" + queID + '\n').getBytes("utf-8"));
											System.out.println(content[0] + ":queID:" + queID);
											enemyOutputStream
													.write((enemyTel + ":queID:" + queID + '\n').getBytes("utf-8"));
											System.out.println(enemyTel + ":queID:" + queID);
											break;
										}
									}
								}
								if (i == matchtList.size()) {
									outputStream.write(((content[0] + ":noothermatch" + '\n')).getBytes("utf-8"));
									System.out.println(content[0] + ":noothermatch");
									MyServer.matchtList.add(socket);
								}
							}
						} else {
							// 对战中
							if (content[1].equals("queNum")) {
								// 转送答案，queNum前面是对手的tel，后面是第几题的答案
								Socket enemySocket = (Socket) MyServer.urlTel.get(content[2]);
								OutputStream enemyOutputStream = new PrintStream(enemySocket.getOutputStream());
								enemyOutputStream
										.write((content[2] + ":queNum:" + content[3] + '\n').getBytes("utf-8"));
								System.out.println("敌人：" + contentAll);
							} else if (content[1].equals("ifpkyes")) {
								// 同意pk,格式：同意tel+ifpkyes+请求tel
								Socket wantpkSocket = (Socket) MyServer.urlTel.get(content[2]);
								OutputStream wantpkOutputStream = new PrintStream(wantpkSocket.getOutputStream());
								OutputStream outputStream = new PrintStream(socket.getOutputStream());
								// 两个加入matchtedList，matchtList少一
								MyServer.matchtedList.add(socket);
								MyServer.matchtedList.add(wantpkSocket);
								System.out.println(content[2] + " want pk with friend " + content[0]);
								// 返回同意的tel+tel+同意的tel+信息
								User enemy = userDao.findByTel(content[2]);
								outputStream.write((content[0] + ":tel:" + content[2] + ":" + enemy.getName() + '\n')
										.getBytes("utf-8"));
								System.out.println(content[0] + ":tel:" + content[2] + ":" + enemy.getName());
								// 还需要通知之前正在匹配队列中的用户
								User my = userDao.findByTel(content[0]);
								wantpkOutputStream.write((content[2] + ":tel:" + content[0] + ":" + my.getName() + '\n')
										.getBytes("utf-8"));
								System.out.println(content[2] + ":tel:" + content[0] + ":" + my.getName());
								// 发送两个人的题库
								Thread.sleep(400);
								String queID = havequeID();
								outputStream.write((content[0] + ":queID:" + queID + '\n').getBytes("utf-8"));
								System.out.println(content[0] + ":queID:" + queID);
								wantpkOutputStream.write((content[2] + ":queID:" + queID + '\n').getBytes("utf-8"));
								System.out.println(content[2] + ":queID:" + queID);
							} else if (content[1].equals("ifpkno")) {
								// 不同意pk,不同意tel+ifpkyes+请求tel
								Socket wantpkSocket = (Socket) MyServer.urlTel.get(content[2]);
								OutputStream outputStream = new PrintStream(wantpkSocket.getOutputStream());
								outputStream.write((content[2] + ":no" + '\n').getBytes("utf-8"));
							}
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String havequeID() {
		String queID = null;
		Random r = new Random();
		List<Integer> queIDS = new ArrayList<Integer>();
		int i = 0;
		while (i < 10) {
			int n = r.nextInt(20);
			if (queIDS.size() > 0) {
				if (!queIDS.contains(n)) {
					queIDS.add(n);
					i++;
					queID = queID + "-" + Integer.toString(n);
				} else {
					continue;
				}
			} else {
				queIDS.add(n);
				i++;
				queID = Integer.toString(n);
			}
		}

		return queID;
	}

	private String packMessage(String content) {
		String result = null;
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		result = df.format(new Date()) + "\n" + content;
		return result;
	}
}