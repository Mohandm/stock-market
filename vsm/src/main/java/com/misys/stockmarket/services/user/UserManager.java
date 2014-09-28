package com.misys.stockmarket.services.user;

import java.util.List;

import com.misys.stockmarket.model.User;

public interface UserManager {
	
	public List<User> getUserList();
	
	public void addUser(User user);
	
	public void removeUser(User removeUser);
	
	public void updateUser(User user);

	public User getUser(int id);

	public User getUserByEmail(String email);
}
