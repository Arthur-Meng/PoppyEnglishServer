package com.poppyenglish;


import java.util.List;

public interface UserDAO {
	public List<User> findByName(String name);
	public List<User> rank();
	public User findByTel(String tel);
	public User save(User user);
	public User update(User user);
	public User delete(User user);
}
