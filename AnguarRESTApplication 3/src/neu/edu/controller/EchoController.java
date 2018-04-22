package neu.edu.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Controller;

@Controller
@Path("/echo")
public class EchoController {
	
	@GET
	public String echo() {
		return "Hello World";

	}


	@GET
	@Path("/time")
	public String getTime(@QueryParam("format") String xyz) {
		String dateFormat = null;

		if ("LONG".equals(xyz)) {
			dateFormat = "dd-MM-yyyy HH:mm:ss";
		}else if ("SHORT".equals(xyz)) {
			dateFormat = "HH:mm:ss";
		} else {
			dateFormat = "EEE MMM dd HH:mm:ss zzz yyyy";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Date fromDate = new Date();

		TimeZone timezone = TimeZone.getTimeZone("US/Eastern");
		formatter.setTimeZone(timezone);
		return "Hello World... Time (" + xyz + ") is  " + formatter.format(fromDate);
	}
	
	


	@GET
	@Path("/time/{state}")
	public String getTimeWithZone(@PathParam("state") String state, @QueryParam("format") String format) {
		String dateFormat = null;

		if ("LONG".equals(format)) {
			dateFormat = "dd-MM-yyyy HH:mm:ss";
		}else if ("SHORT".equals(format)) {
			dateFormat = "HH:mm:ss";
		} else {
			dateFormat = "EEE MMM dd HH:mm:ss zzz yyyy";
		}
		
		
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Date fromDate = new Date();
		TimeZone timezone;
		if (state.equalsIgnoreCase("Boston") || state.equals("New York")) {
			timezone = TimeZone.getTimeZone("US/Eastern");
		} else if (state.equalsIgnoreCase("Arizona") || state.equals("Colorado")) {
			timezone = TimeZone.getTimeZone("US/Mountain");
		} else {
			timezone = TimeZone.getTimeZone("US/Pacific");
		}

		formatter.setTimeZone(timezone);
		return "Hello World... Time for " + state + " is " + formatter.format(fromDate);
	}

	
}