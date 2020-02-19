package main.java.ws.skyscanner.usersservice.business;

import java.util.List;

import main.java.ws.skyscanner.usersservice.model.User;
import main.java.ws.skyscanner.usersservice.persistence.user.UserDAO;
import main.java.ws.skyscanner.usersservice.persistence.user.UserDataService;

public class UserManager implements UserManagerService {
	
	private UserDataService userDataService;
	
	public UserManager() {
		this.userDataService = new UserDAO();
	}

	@Override
	public User getUser(String username) throws Exception {
		return userDataService.getUser(username);
	}

	@Override
	public User addUser(User user) throws Exception {
		return userDataService.addUser(user);
	}

	@Override
	public User updateUser(User user) throws Exception {
		return userDataService.updateUser(user);
	}

	@Override
	public User deleteUser(String username) throws Exception {
		return userDataService.deleteUser(username);
	}

	@Override
	public List<User> getUsers() throws Exception {
		return userDataService.getUsers();
	}

}
