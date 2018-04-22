package neu.edu.controller;

import java.util.ArrayList;

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

import neu.edu.bean.CategoryBean;
import neu.edu.controller.error.ResponseError;
import neu.edu.controller.input.AddCategoryBean;
import neu.edu.controller.output.ResponseSuccess;
import neu.edu.service.CategoryService;

@Controller
@Path("/category")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	private Logger logger = LogManager.getLogger(CategoryController.class);
	
	@GET
	@Path("/getAllActiveCategories")
	@PermitAll
	public Response getAllActiveCategories() {
		ArrayList<CategoryBean> categoryBeans = categoryService.getAllActiveCategories();
		
		if(categoryBeans == null) {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("No active categories");
			logger.debug("No active categories");
			return Response.ok().status(422).entity(responseError).build();
		} else {
			logger.info("category beans found"+categoryBeans);
			return Response.ok().status(200).entity(categoryBeans).build();
		}
	}
	
	@GET
	@Path("/getAllUndeletedCategories")
	@PermitAll
	public Response getAllUndeletedCategories() {
		ArrayList<CategoryBean> categoryBeans = categoryService.getAllUndeletedCategories();
		
		if(categoryBeans == null) {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("No categories");
			logger.debug("No categories");
			return Response.ok().status(422).entity(responseError).build();
		} else {
			logger.info("category beans found"+categoryBeans);
			return Response.ok().status(200).entity(categoryBeans).build();
		}
	}
	
	@GET
	@Path("/getAllCategories")
	@RolesAllowed({"Admin"})
	public Response getAllCategories() {
		ArrayList<CategoryBean> categoryBeans = categoryService.getAllCategories();
		
		if(categoryBeans == null) {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("No active categories");
			logger.error("No Categories");
			return Response.ok().status(422).entity(responseError).build();
		} else {
			logger.info("category beans found"+categoryBeans);
			return Response.ok().status(200).entity(categoryBeans).build();
		}
	}
	
	@PUT
	@Path("/disableCategory/catId/{catID}")
	@RolesAllowed({"Admin"})
	public Response disableCategory(@PathParam("catID") String catID) {
		Integer categoryId = Integer.parseInt(catID);
		boolean success = categoryService.disableCategory(categoryId);
		if(success) {
			ResponseSuccess responseSuccess = new ResponseSuccess();
			responseSuccess.setSuccess(true);
			responseSuccess.setMessage("Category Disabled");
			logger.info("Category Disabled");
			return Response.ok().status(200).entity(responseSuccess).build();
		} else {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("Disable failed");
			responseError.setSuccess(false);
			logger.error("Disable Failed");
			return Response.ok().status(422).entity(responseError).build();
		}
	}
	
	@PUT
	@Path("/enableCategory/catId/{catID}")
	@RolesAllowed({"Admin"})
	public Response enableCategory(@PathParam("catID") String catID) {
		Integer categoryId = Integer.parseInt(catID);
		boolean success = categoryService.enableCategory(categoryId);
		if(success) {
			ResponseSuccess responseSuccess = new ResponseSuccess();
			responseSuccess.setSuccess(true);
			responseSuccess.setMessage("Category Enabled");
			logger.info("Category Enabled");
			return Response.ok().status(200).entity(responseSuccess).build();
		} else {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("Enable failed");
			responseError.setSuccess(false);
			logger.error("Enable Failed");
			return Response.ok().status(422).entity(responseError).build();
		}
	}
	
	@PUT
	@Path("/deleteCategory/catId/{catID}")
	@RolesAllowed({"Admin"})
	public Response deleteCategory(@PathParam("catID") String catID) {
		Integer categoryId = Integer.parseInt(catID);
		boolean success = categoryService.deleteCategory(categoryId);
		if(success) {
			ResponseSuccess responseSuccess = new ResponseSuccess();
			responseSuccess.setSuccess(true);
			responseSuccess.setMessage("Category Deleted");
			logger.info("Category Deleted");
			return Response.ok().status(200).entity(responseSuccess).build();
		} else {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("Deletion failed");
			responseError.setSuccess(false);
			logger.error("Deletion Failed");
			return Response.ok().status(422).entity(responseError).build();
		}
	}
	
	@PUT
	@Path("/undeleteCategory/catId/{catID}")
	@RolesAllowed({"Admin"})
	public Response undeleteCategory(@PathParam("catID") String catID) {
		Integer categoryId = Integer.parseInt(catID);
		boolean success = categoryService.undeleteCategory(categoryId);
		if(success) {
			ResponseSuccess responseSuccess = new ResponseSuccess();
			responseSuccess.setSuccess(true);
			responseSuccess.setMessage("Category Undeleted");
			logger.info("Category Undeleted");
			return Response.ok().status(200).entity(responseSuccess).build();
		} else {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("Undelete failed");
			responseError.setSuccess(false);
			logger.error("Undelete Failed");
			return Response.ok().status(422).entity(responseError).build();
		}
	}
	
	@POST
	@Path("/addCategory")
	@RolesAllowed({"Admin"})
	public Response addCategory(AddCategoryBean addCategoryBean) {
		boolean success = categoryService.addCategory(addCategoryBean);
		if(success) {
			ResponseSuccess responseSuccess = new ResponseSuccess();
			responseSuccess.setSuccess(true);
			responseSuccess.setMessage("Category Added");
			logger.info("Category Added");
			return Response.ok().status(200).entity(responseSuccess).build();
		} else {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("Category already exists");
			responseError.setSuccess(false);
			logger.error("Category already exists.");
			return Response.ok().status(422).entity(responseError).build();
		}
	}

}
