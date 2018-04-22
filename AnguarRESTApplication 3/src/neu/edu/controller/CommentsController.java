package neu.edu.controller;

import java.util.ArrayList;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import neu.edu.bean.AddCommentsBean;
import neu.edu.bean.CommentsResponseBean;
import neu.edu.controller.error.ResponseError;
import neu.edu.controller.output.ResponseSuccess;
import neu.edu.service.CommentsService;

@Controller
@Path("/comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentsController {
	
	@Autowired
	private CommentsService commentsService;
	
	private Logger logger = LogManager.getLogger(CommentsController.class);
	
	@GET
	@Path("/getComments/projectId/{projectId}")
	@PermitAll
	public Response getCommentsByProject(@PathParam("projectId") String projectId) {
		ArrayList<CommentsResponseBean> commentsResponseBeans = commentsService.getCommentsByProject(new Integer(projectId));
		if(commentsResponseBeans!=null) {
			logger.info("comments "+commentsResponseBeans);
			return Response.ok().status(200).entity(commentsResponseBeans).build();
		} else {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("No Data Available");
			responseError.setSuccess(false);
			logger.error("No Comments Available");
			return Response.ok().status(422).entity(responseError).build();
		}
	}
	
	@POST
	@Path("/addComment")
	@RolesAllowed({"Donor"})
	public Response addComment(AddCommentsBean addCommentsBean) {
		boolean status = commentsService.addComment(addCommentsBean);
		if(status) {
			ResponseSuccess responseSuccess = new ResponseSuccess();
			responseSuccess.setSuccess(true);
			responseSuccess.setMessage("Comment Added");
			logger.info("comments Added");
			return Response.ok().status(200).entity(responseSuccess).build();
		} else {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("Comment couldn't be added");
			responseError.setSuccess(false);
			logger.error("Comment couldn't be added");
			return Response.ok().status(422).entity(responseError).build();
		}
	}

	
}
