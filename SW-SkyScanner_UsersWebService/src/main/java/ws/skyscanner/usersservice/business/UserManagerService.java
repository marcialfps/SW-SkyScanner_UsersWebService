package main.java.ws.skyscanner.usersservice.business;

import java.util.List;

import main.java.ws.skyscanner.usersservice.model.User;

public interface UserManagerService {
	public List<User> getUsers() throws Exception;
	public User getUser(String username) throws Exception;
	public User addUser(User user) throws Exception;
	public User updateUser(User user) throws Exception;
	public User deleteUser(String username) throws Exception;
}
