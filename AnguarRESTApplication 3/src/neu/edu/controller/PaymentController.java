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

import neu.edu.bean.MakePaymentBean;
import neu.edu.bean.PaymentBean;
import neu.edu.controller.error.ResponseError;
import neu.edu.controller.output.ResponseSuccess;
import neu.edu.service.PaymentService;

@Controller
@Path("/payment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	private Logger logger = LogManager.getLogger(PaymentController.class);
	
	@GET
	@Path("/getPayments/projectId/{projectid}")
	@PermitAll
	public Response getPaymentsByProject(@PathParam("projectid") String projectId) {
		ArrayList<PaymentBean> paymentBeans = paymentService.getPaymentByProject(new Integer(projectId));
		if(paymentBeans == null) {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("No payments for project");
			logger.error("No payments for project");
			return Response.ok().status(422).entity(responseError).build();
		} else {
			logger.info("Payment Beans "+paymentBeans);
			return Response.ok().status(200).entity(paymentBeans).build();
		}
	}
	
	@POST
	@Path("/makePayment")
	@RolesAllowed({"Donor"})
	public Response makePayment(MakePaymentBean makePaymentBean) {
		boolean status = paymentService.makePayment(makePaymentBean);
		if(status) {
			ResponseSuccess responseSuccess = new ResponseSuccess();
			responseSuccess.setSuccess(true);
			responseSuccess.setMessage("Payment Saved");
			logger.info("Payment Saved ");
			return Response.ok().status(200).entity(responseSuccess).build();
		} else {
			ResponseError responseError = new ResponseError();
			responseError.setMessage("Payment Save failed");
			logger.error("Payment Save failed");
			return Response.ok().status(422).entity(responseError).build();
		}
	}

}
