package main.java.ws.skyscanner.usersservice.persistence.user;

import java.util.List;

import main.java.ws.skyscanner.usersservice.model.User;

public interface UserDataService {
	public List<User> getUsers() throws Exception;
	public User getUser(String username) throws Exception;
	public User addUser(User user) throws Exception;
	public User updateUser(User user) throws Exception;
	public User deleteUser(String username) throws Exception;
}
