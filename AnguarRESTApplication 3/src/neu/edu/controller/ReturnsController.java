package neu.edu.controller;

import java.util.ArrayList;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import neu.edu.controller.error.ResponseError;
import neu.edu.controller.output.ResturnsResponseBean;
import neu.edu.service.ReturnsService;

@Controller
@Path("/returns")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReturnsController {
	
	@Autowired
	private ReturnsService returnsService;
	
	private Logger logger = LogManager.getLogger(ReturnsController.class);
	
	@GET
	@Path("/getReturn/projectId/{projectId}")
	@PermitAll
	public Response getReturnsByProject(@PathParam("projectId") String projectId) {
		ArrayList<ResturnsResponseBean> returns = returnsService.getReturnsByProject(new Integer(projectId));
		if(returns.isEmpty()) {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("No returns for project");
			logger.error("No returns for project");
			return Response.ok().status(422).entity(responseError).build();
		} else {
			logger.info("Returns for project: "+ returns);
			return Response.ok().status(200).entity(returns).build();
		}
	}

}
