package ws.skyscanner.usersservice.persistence.user;

import ws.skyscanner.usersservice.model.User;

public interface UserDataService {
	public User getUser(String username) throws Exception;
	public User addUser(User user) throws Exception;
	public User updateUser(User user) throws Exception;
	public User deleteUser(String username) throws Exception;
}
