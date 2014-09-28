package com.misys.stockmarket.services.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.misys.stockmarket.model.User;

@Service("userManager")
public class InMemoryUserManager implements UserManager {

	private static List<User> userList;

	private static int userId;

	static {
		userId = 1;
		userList = new ArrayList<User>();
		User user1 = new User();
		user1.setFirstName("First Name");
		user1.setLastName("Last Name");
		user1.setEmailAddress("test@misys.com");
		user1.setPassword("password");
		user1.setActive(true);
		user1.setId(userId);
		userList.add(user1);
		userId++;
	}

	@Override
	public List<User> getUserList() {
		return userList;
	}

	@Override
	public void addUser(User newUser) {
		try {
			newUser.setId(userId);
			newUser.setActive(false);
			userList.add(newUser);
			userId++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeUser(User removeUser) {
		try {
			for (Iterator<User> iterator = userList.iterator(); iterator.hasNext();) {
				User user = iterator.next();
				if (user.getId() == removeUser.getId()) {
					iterator.remove();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateUser(User updatedUser) {
		try {
			removeUser(updatedUser);
			userList.add(updatedUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public User getUser(int id) {
		for (User user : userList) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		for (User user : userList) {
			if (user.getEmailAddress().equals(email)) {
				return user;
			}
		}
		return null;
	}
}
