package neu.edu.interceptors;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.core.interception.PostMatchContainerRequestContext;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.stereotype.Component;

//import com.auth0.jwt.interfaces.DecodedJWT;

import neu.edu.util.JWTUtil;

@Component
@Provider
public class SecurityInterceptor implements ContainerRequestFilter {

	private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401,
			new Headers<Object>());;
	private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403,
			new Headers<Object>());;
	private static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR", 500,
			new Headers<Object>());

	class ResponseMessage {
		private String message;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub

		System.out.println("SecurityInterceptor");

		PostMatchContainerRequestContext pmContext = (PostMatchContainerRequestContext) requestContext;
		Method m = pmContext.getResourceMethod().getMethod();

		if (m.isAnnotationPresent(DenyAll.class)) {
			requestContext.abortWith(ACCESS_DENIED);
			return;
		}

		if (m.isAnnotationPresent(PermitAll.class)) {
			return;
		}

		MultivaluedMap<String, String> headers = requestContext.getHeaders(); // Fetch
																				// authorization
																				// header
		final List<String> authorization = headers.get(JWTUtil.AUTHORIZATION_PROPERTY);

		// If no authorization information present; block access
		if (authorization == null || authorization.isEmpty()) {
			requestContext.abortWith(ACCESS_DENIED);
			return;
		}

		if (m.isAnnotationPresent(RolesAllowed.class)) {

			RolesAllowed rolesAnnotation = m.getAnnotation(RolesAllowed.class);
			Set<String> allowedRoleSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
			// String decodedToken;
			// try {
			String authToken = authorization.get(0).substring(7);
			;
			// decodedToken = new
			// String(Base64.decode(encodedValue.getBytes()));

			if (!isUserAllowed(authToken, allowedRoleSet)) {
				requestContext.abortWith(ACCESS_DENIED);
				return;
			}

			// } catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			// }

		}

	}

	private boolean isUserAllowed(final String token, final Set<String> rolesSet) {
		boolean isAllowed = false;
		ArrayList<String> userRoles = null;

		JwtClaims jwt = JWTUtil.validateToken(token);
		if (jwt != null) {
			try {
				String subject = jwt.getStringClaimValue("sub");
				
				 userRoles = (ArrayList<String>) jwt.getClaimValue("roles");
				
			} catch (MalformedClaimException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			// String [] userRole =
			// jwt.getClaims().get("roles").asArray(String.class);

			// Step 2. Verify user role
			if (rolesSet.contains(userRoles.get(0))) {
				isAllowed = true;
			}
		}
		return isAllowed;
	}

}
