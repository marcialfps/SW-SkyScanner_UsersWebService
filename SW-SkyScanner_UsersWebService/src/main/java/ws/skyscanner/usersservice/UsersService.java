package main.java.ws.skyscanner.usersservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import main.java.ws.skyscanner.usersservice.business.UserManager;
import main.java.ws.skyscanner.usersservice.business.UserManagerService;
import main.java.ws.skyscanner.usersservice.model.User;
import main.java.ws.skyscanner.usersservice.security.AESEncryptor;

@Path("users")
public class UsersService {
	
	private UserManagerService userManager;
	
	public UsersService() {
		this.userManager = new UserManager();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		try {
			return userManager.getUsers();
		} catch (Exception e) {
			return null;
		}
	}
	
	@GET @Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("username") String username) {
		try {
			User user = userManager.getUser(username);
			user.setPassword(AESEncryptor.Encrypt(user.getPassword()));
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	@POST @Path("{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@PathParam("username") String username, User user) {
		try {
			String decryptedPass = AESEncryptor.Decrypt(user.getPassword());
			
			User myuser = userManager.getUser(username);
			
			//User does not exist
			if (myuser == null) {
				return Response.status(Response.Status.FORBIDDEN).build();
			}
			
			if(myuser.getPassword().equals(decryptedPass)) {
				return Response.status(Response.Status.ACCEPTED).entity(user).build();
			} else {
				return Response.status(Response.Status.FORBIDDEN).build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(User user) {
		try {
			User myuser = userManager.getUser(user.getUsername());
			
			//User exist
			if (myuser != null) {
				return Response.status(Response.Status.CONFLICT).build();
			} else {
				user.setPassword(AESEncryptor.Encrypt(user.getPassword()));
				userManager.addUser(user);
				return Response.status(Response.Status.CREATED).entity(user).build();
			}
			
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PUT @Path("{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("username") String username, User user) {
		try {
			if (userManager.getUser(username) == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			
			user.setPassword(AESEncryptor.Encrypt(user.getPassword()));
			userManager.updateUser(user);
			return Response.status(Response.Status.OK).entity(user).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DELETE @Path("{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("username") String username, User user) {
		try {
			User myuser = userManager.getUser(username);
			String decryptedPass = AESEncryptor.Decrypt(user.getPassword());
			if (myuser == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			} else if (!myuser.getPassword().equals(decryptedPass)) {
				return Response.status(Response.Status.FORBIDDEN).build();
			}
			
			userManager.deleteUser(username);
			return Response.status(Response.Status.OK).entity(user).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}
