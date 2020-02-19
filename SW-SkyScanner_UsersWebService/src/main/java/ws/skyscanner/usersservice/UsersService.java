package main.java.ws.skyscanner.usersservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
	private AESEncryptor encryptor;
	
	public UsersService() {
		this.userManager = new UserManager();
		this.encryptor = new AESEncryptor();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		try {
			return "Text - Hello World!!"+userManager.getUser("test").getName();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user) {
		try {
			String decryptedUser = encryptor.decrypt(user.getUsername());
			String decryptedPass = encryptor.decrypt(user.getPassword());
			
			User myuser = userManager.getUser(decryptedUser);
			
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
}
