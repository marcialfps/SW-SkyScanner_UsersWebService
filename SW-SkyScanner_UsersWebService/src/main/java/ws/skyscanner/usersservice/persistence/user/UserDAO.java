package main.java.ws.skyscanner.usersservice.persistence.user;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import main.java.ws.skyscanner.usersservice.model.User;
import main.java.ws.skyscanner.usersservice.persistence.Dba;

public class UserDAO implements UserDataService {
	
	protected Logger logger = Logger.getLogger(getClass());

	@Override
	public User getUser(String username) throws Exception {
		List<User> userList = null;

		Dba dba = new Dba();
		try {
			EntityManager em = dba.getActiveEm();

			userList = em.createQuery("Select u From User u Where u.username= ?", User.class)
					.setParameter(1, username)
					.getResultList();

			if(!userList.isEmpty()) {
				logger.debug("Returning user: "+ userList.get(0).toString());
			} else {
				return null;
			}

		} finally {
			// 100% sure that the transaction and entity manager will be closed
			dba.closeEm();
		}

		// We return the result
		return userList.get(0);
	}

	@Override
	public User addUser(User user) throws Exception {
		Dba dba = new Dba();
		try {
			EntityManager em = dba.getActiveEm();
			em.persist(user);
			em.getTransaction().commit();

			logger.debug("Created user: "+ user.toString());

		} finally {
			// 100% sure that the transaction and entity manager will be closed
			dba.closeEm();
		}

		// We return the result
		return user;
	}

	@Override
	public User updateUser(User user) throws Exception {
		return addUser(user);
	}

	@Override
	public User deleteUser(String username) throws Exception {
		User user = getUser(username);
		Dba dba = new Dba();
		try {
			EntityManager em = dba.getActiveEm();
			em.remove(user);
			em.getTransaction().commit();

			logger.debug("Removed user: "+ user.toString());

		} finally {
			// 100% sure that the transaction and entity manager will be closed
			dba.closeEm();
		}
		return user;
	}

}
