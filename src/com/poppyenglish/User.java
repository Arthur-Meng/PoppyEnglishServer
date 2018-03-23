package com.poppyenglish;

import java.util.List;
import java.util.Arrays;

public class User {
	String tel;
	String name;
	String password;
	String gender;
	String honor;
	String comment;
	String[] friendall;
	String friend;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getHonor() {
		return honor;
	}

	public void setHonor(String honor) {
		this.honor = honor;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFriend() {
		return friend;
	}

	public void setFriend(String friend) {
		this.friend = friend;
	}

	public String[] getFriendall() {
		if (friend != null) {
			friendall = friend.split("-");
		}
		return friendall;
	}

	public Boolean Iffrined(String searchName) {
		if (this.getFriend() != null) {
			String[] friendList = this.getFriendall();
			if (friendList != null) {
				List list = (List) Arrays.asList(friendList);
				if (list != null) {
					if (list.contains(searchName))
						return true;
				}
			}
		}
		return false;
	}

}
