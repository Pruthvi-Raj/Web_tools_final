package neu.edu.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import neu.edu.bean.AddProjectBean;
import neu.edu.bean.UserProjectBean;
import neu.edu.controller.error.ResponseError;
import neu.edu.controller.output.ResponseSuccess;
//import neu.edu.service.ProjectService;
import neu.edu.service.ProjectService;

@Controller
@Path("/project")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	private Logger logger = LogManager.getLogger(ProjectController.class);
	
	@GET
	@Path("/getAllProjects/catID/{catID}")
	@RolesAllowed({"Admin"})
	public Response getAllProject(@PathParam("catID") String id){
		List<UserProjectBean> userProjectBeans =  projectService.getAllProjects(new Integer(id));
		if(userProjectBeans==null) {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("No Projects");
			logger.error("No Projects");
			return Response.ok().status(422).entity(responseError).build();
		}
		logger.info("Projects based on category "+userProjectBeans);
		return  Response.ok().status(200).entity(userProjectBeans).build();
		
	}
	
	@GET
	@Path("/getAllUndeletedProjects/catID/{catID}")
	@PermitAll
	public Response getAllUndeletedProject(@PathParam("catID") String id){
		List<UserProjectBean> userProjectBeans =  projectService.getAllUndeletedProjects(new Integer(id));
		if(userProjectBeans==null) {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("No Projects");
			logger.error("No Projects");
			return Response.ok().status(422).entity(responseError).build();
		}
		logger.info("Undeleted Projects based on category "+userProjectBeans);
		return  Response.ok().status(200).entity(userProjectBeans).build();
		
	}
	
	@GET
	@Path("/getAllActiveProjects/catID/{catID}")
	@PermitAll
	public Response getAllActiveProject(@PathParam("catID") String id){
		List<UserProjectBean> userProjectBeans =  projectService.getAllActiveProjects(new Integer(id));
		if(userProjectBeans==null) {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("No Projects");
			logger.error("No Projects");
			return Response.ok().status(422).entity(responseError).build();
		}
		logger.info("Active Projects based on category "+userProjectBeans);
		return  Response.ok().status(200).entity(userProjectBeans).build();
		
	}
	
	@PUT
	@Path("/deleteProject/projectId/{projectID}")
	@RolesAllowed({"Admin"})
	public Response deleteCategory(@PathParam("projectID") String projectID) {
		boolean success = projectService.deleteProject(new Integer(projectID));
		if(success) {
			ResponseSuccess responseSuccess = new ResponseSuccess();
			responseSuccess.setSuccess(true);
			responseSuccess.setMessage("Project Deleted");
			logger.info("Project Deleted");
			return Response.ok().status(200).entity(responseSuccess).build();
		} else {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("Deletion failed");
			responseError.setSuccess(false);
			logger.error("Deletion failed");
			return Response.ok().status(422).entity(responseError).build();
		}
	}
	
	@PUT
	@Path("/undeleteProject/projectId/{projectID}")
	@RolesAllowed({"Admin"})
	public Response undeleteCategory(@PathParam("projectID") String projectID) {
		boolean success = projectService.undeleteProject(new Integer(projectID));
		if(success) {
			ResponseSuccess responseSuccess = new ResponseSuccess();
			responseSuccess.setSuccess(true);
			responseSuccess.setMessage("Project Undeleted");
			logger.info("Project Undeleted");
			return Response.ok().status(200).entity(responseSuccess).build();
		} else {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("Undelete failed");
			responseError.setSuccess(false);
			logger.error("Undelete failed");
			return Response.ok().status(422).entity(responseError).build();
		}
	}
	
	@GET
	@Path("/getAllProjects/userId/{userId}")
	@RolesAllowed({"Admin"})
	public Response getAllProjectByUserId(@PathParam("userId") String id){
		List<UserProjectBean> userProjectBeans =  projectService.getAllProjectsByUser(new Integer(id));
		if(userProjectBeans==null) {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("No Projects");
			logger.error("No Projects");
			return Response.ok().status(422).entity(responseError).build();
		}
		logger.info("Projects based on category "+userProjectBeans);
		return  Response.ok().status(200).entity(userProjectBeans).build();
		
	}
	
	@POST
	@Path("/addProject")
	@RolesAllowed({"Creator"})
	public Response addProject(AddProjectBean addProjectBean) {
		boolean status = projectService.addProject(addProjectBean);
		if(status) {
			ResponseSuccess responseSuccess = new ResponseSuccess();
			responseSuccess.setSuccess(true);
			responseSuccess.setMessage("Project Added");
			logger.info("Project Added");
			return Response.ok().status(200).entity(responseSuccess).build();
		} else {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("Couldn't Add Project");
			logger.error("Couldn't Add Project");
			return Response.ok().status(422).entity(responseError).build();
		}
	}
	

}
