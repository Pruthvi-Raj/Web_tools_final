package neu.edu.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTCreationException;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtil {

	public static final String AUTHORIZATION_PROPERTY = "Authorization";
	// private static final String AUTHENTICATION_SCHEME = "Basic";
	public static final String AUTHENTICATION_SCHEME = "Bearer";
	
	private final static  String SECRET_KEY = "Ahasdf&#$klse";
	
	private static RsaJsonWebKey rsaJsonWebKey = null;

	
    // Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK

	static {
		try {
			 rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
		} catch (JoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

	}


	public static String generateToken(String id,String[] roles) {

	    // Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
	   
		try {
			
			
		    // Give the JWK a Key ID (kid), which is just the polite thing to do
		    rsaJsonWebKey.setKeyId("k1");

		    // Create the Claims, which will be the content of the JWT
		    JwtClaims claims = new JwtClaims();
		    claims.setIssuer("Divija");  // who creates the token and signs it
		    claims.setStringClaim("sub", id);
		    claims.setStringListClaim("roles", roles);
		    
		    // A JWT is a JWS and/or a JWE with JSON claims as the payload.
		    // In this example it is a JWS so we create a JsonWebSignature object.
		    JsonWebSignature jws = new JsonWebSignature();

		    // The payload of the JWS is JSON content of the JWT Claims
		    jws.setPayload(claims.toJson());

		    // The JWT is signed using the private key
		    jws.setKey(rsaJsonWebKey.getPrivateKey());

		    // Set the Key ID (kid) header because it's just the polite thing to do.
		    // We only have one key in this example but a using a Key ID helps
		    // facilitate a smooth key rollover process
		    jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());

		    // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
		    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

		    // Sign the JWS and produce the compact serialization or the complete JWT/JWS
		    // representation, which is a string consisting of three dot ('.') separated
		    // base64url-encoded parts in the form Header.Payload.Signature
		    // If you wanted to encrypt it, you can simply set this jwt as the payload
		    // of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
		    String jwt = jws.getCompactSerialization();


		    // Now you can do something with the JWT. Like send it to some other party
		    // over the clouds and through the interwebs.
		    System.out.println("JWT: " + jwt);
		    
			return wrapAuthenticationHeader(jwt);
		    
		} catch (JoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // Give the JWK a Key ID (kid), which is just the polite thing to do
	    
	    
//		Map<String, Object> headerClaims = new HashMap<>();
//		headerClaims.put("typ", "JWT");
//		
//		try {
//			Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
//			String token = JWT.create()
//					.withHeader(headerClaims)
//					.withClaim("sub", id)
//					.withArrayClaim("roles",roles )
//					.withIssuer("auth0")
//					.sign(algorithm);
//
//			return wrapAuthenticationHeader(token);
//		} catch (UnsupportedEncodingException exception) {
//			// UTF-8 encoding not supported
//		} catch (JWTCreationException exception) {
//			// Invalid Signing configuration / Couldn't convert Claims.
//		}

		return null;
	}
	
	private static String wrapAuthenticationHeader(String token){
		return AUTHENTICATION_SCHEME+" "+token;
	}

	public static JwtClaims validateToken(String token) {
		
		


	    // Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
	    // be used to validate and process the JWT.
	    // The specific validation requirements for a JWT are context dependent, however,
	    // it typically advisable to require a (reasonable) expiration time, a trusted issuer, and
	    // and audience that identifies your system as the intended recipient.
	    // If the JWT is encrypted too, you need only provide a decryption key or
	    // decryption key resolver to the builder.
	    JwtConsumer jwtConsumer = new JwtConsumerBuilder()
	            .setExpectedIssuer("Divija") // whom the JWT needs to have been issued by
	            .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
	            .setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
	                    new AlgorithmConstraints(ConstraintType.WHITELIST, // which is only RS256 here
	                            AlgorithmIdentifiers.RSA_USING_SHA256))
	            .build(); // create the JwtConsumer instance

	    try
	    {
	        //  Validate the JWT and process it to the Claims
	        JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
	        System.out.println("JWT validation succeeded! " + jwtClaims);
	        
	        return jwtClaims;
	    }
	    catch (InvalidJwtException e)
	    {
	        // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
	        // Hopefully with meaningful explanations(s) about what went wrong.
	        System.out.println("Invalid JWT! " + e);
	    }

//		try {
//			Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
//			JWTVerifier verifier = JWT.require(algorithm)
//									  .withIssuer("auth0")
//									  .build(); // Reusable verifier instance
//			DecodedJWT jwt = verifier.verify(token);
//			
//			return jwt;
//
//		} catch (UnsupportedEncodingException exception) {
//			// UTF-8 encoding not supported
//		} catch (JWTVerificationException exception) {
//			// Invalid signature/claims
//		}

		return null;
	}

}
