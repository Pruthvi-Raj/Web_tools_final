package neu.edu.controller;

import java.util.ArrayList;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import neu.edu.bean.ReportsResponseBean;
import neu.edu.controller.error.ResponseError;
import neu.edu.service.ReportsService;

@Controller
@Path("/reports")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReportsController {
	
	@Autowired
	private ReportsService reportsService;
	
	private Logger logger = LogManager.getLogger(ReportsController.class);
	
	@GET
	@Path("/paymentsInProjects")
	@RolesAllowed({"Admin"})
	public Response getPaymentsInProjects() {
		ArrayList<ReportsResponseBean> reportsResponseBeans = reportsService.getPaymentsInProjects();
		if(reportsResponseBeans == null) {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("No data");
			logger.error("No Data");
			return Response.ok().status(422).entity(responseError).build();
		} else {
			logger.info("Data : "+reportsResponseBeans);
			return Response.ok().status(200).entity(reportsResponseBeans).build();
		}
	}
	
	@GET
	@Path("/projectsInCategory")
	@RolesAllowed({"Admin"})
	public Response getProjectsInCategory() {
		ArrayList<ReportsResponseBean> reportsResponseBeans = reportsService.getProjectsInCategory();
		if(reportsResponseBeans == null) {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("No data");
			logger.error("No Data");
			return Response.ok().status(422).entity(responseError).build();
		} else {
			logger.info("Data : "+reportsResponseBeans);
			return Response.ok().status(200).entity(reportsResponseBeans).build();
		}
	}
	
	@GET
	@Path("/commentsInProjects")
	@RolesAllowed({"Admin"})
	public Response getCommentsInProjects() {
		ArrayList<ReportsResponseBean> reportsResponseBeans = reportsService.getCommentsInProjects();
		if(reportsResponseBeans == null) {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("No data");
			logger.error("No Data");
			return Response.ok().status(422).entity(responseError).build();
		} else {
			logger.info("Data : "+reportsResponseBeans);
			return Response.ok().status(200).entity(reportsResponseBeans).build();
		}
	}

}
