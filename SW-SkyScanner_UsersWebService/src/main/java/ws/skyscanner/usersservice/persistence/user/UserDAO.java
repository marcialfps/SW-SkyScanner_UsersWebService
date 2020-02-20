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
		Dba dba = new Dba();
		try {
			EntityManager em = dba.getActiveEm();
			User actualUser = this.getUser(user.getUsername());
			actualUser = em.find(User.class, actualUser.getId());
			actualUser.setAirport(user.getAirport());
			actualUser.setMail(user.getMail());
			actualUser.setName(user.getName());
			actualUser.setPassword(user.getPassword());
			actualUser.setSurname(user.getSurname());
			actualUser.setUsername(user.getUsername());
			
			em.persist(actualUser);
			em.getTransaction().commit();

			logger.debug("Updated user: "+ user.toString());
			return actualUser;

		} finally {
			// 100% sure that the transaction and entity manager will be closed
			dba.closeEm();
		}

	}

	@Override
	public User deleteUser(String username) throws Exception {
		User user = getUser(username);
		Dba dba = new Dba();
		try {
			EntityManager em = dba.getActiveEm();
			user = em.find(User.class, user.getId());
			em.remove(user);
			em.getTransaction().commit();
			
			logger.debug("Removed user: "+ user.toString());
			return user;


		} finally {
			// 100% sure that the transaction and entity manager will be closed
			dba.closeEm();
		}
	}

	@Override
	public List<User> getUsers() throws Exception {
		List<User> userList = null;

		Dba dba = new Dba();
		try {
			EntityManager em = dba.getActiveEm();

			userList = em.createQuery("Select u From User u", User.class)
					.getResultList();

			if(!userList.isEmpty()) {
				logger.debug("Returning users");
			} else {
				return null;
			}

		} finally {
			// 100% sure that the transaction and entity manager will be closed
			dba.closeEm();
		}

		// We return the result
		return userList;
	}

}
