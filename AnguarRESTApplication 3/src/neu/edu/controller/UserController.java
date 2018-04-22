package neu.edu.controller;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import neu.edu.bean.UserAccountBean;
import neu.edu.bean.UserRegistrationBean;
import neu.edu.controller.error.ResponseError;
import neu.edu.controller.input.UserLoginBean;
import neu.edu.controller.output.ResponseSuccess;
import neu.edu.service.UserService;
import neu.edu.util.JWTUtil;

@Controller
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

	@Autowired
	private UserService userService;
	
	private Logger logger = LogManager.getLogger(ReturnsController.class);

	@POST
	@Path("/auth")
	@PermitAll
	public Response validateUser(UserLoginBean loginBean) {
		Integer userId = userService.validateUser(loginBean.getUsername(), loginBean.getPassword());
		if (userId == null) {
			ResponseError authResponseErr = new ResponseError();
			authResponseErr.setMessage("user-not-found");
			logger.error("User not found");
			return Response.ok().status(422).entity(authResponseErr).build();
		} else {
			UserAccountBean userAccountBean = userService.fetchUserAccountDetails(userId);
			logger.info("User Account: "+userAccountBean);
			return Response.ok().status(200).entity(userAccountBean)
					.header(JWTUtil.AUTHORIZATION_PROPERTY, JWTUtil.generateToken(userId.toString(), new String[]{userAccountBean.getRole()})).build();
		}

	}
	
	@POST
	@Path("/registration")
	@PermitAll
	public Response registerUser(UserRegistrationBean userRegistrationBean) {
		boolean status = userService.registerUser(userRegistrationBean);
		if(status) {
			ResponseSuccess responseSuccess = new ResponseSuccess();
			responseSuccess.setSuccess(true);
			responseSuccess.setMessage("User Account created successfully");
			logger.info("User Account created successfully");
			return Response.ok().status(200).entity(responseSuccess).build();
		} else {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("User Name already exists");
			responseError.setSuccess(false);
			logger.error("User Name already exists");
			return Response.ok().status(422).entity(responseError).build();
		}
		
	}

}
