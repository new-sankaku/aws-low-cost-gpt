package com.yksc.lambda.util;

import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.yksc.lambda.exception.AuthenticationException;
import com.yksc.lambda.log.LogUtil;
import com.yksc.lambda.log.LoggerFactory;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GetUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GetUserResponse;

public class CognitoValidationUtil {
	private static final Logger logger = LoggerFactory.getLogger();

	public static void main( String[] args) {
		
		//test
		String accessToken = "test token";
		getUserUUID(accessToken);
	}
	
	
	public static String getUserUUID( String accessToken ) {
		String regionStr = EnvironmentUtil.getEnv( EnvironmentUtil.AWS_COGNITO_REGION );
        Region region = Region.of(regionStr);

        CognitoIdentityProviderClient cognitoClient = CognitoIdentityProviderClient.builder()
                .region(region)
                .build();

        GetUserRequest getUserRequest = GetUserRequest.builder()
                .accessToken(accessToken)
                .build();
        GetUserResponse getUserResponse = cognitoClient.getUser(getUserRequest);

        //sub is cognito userId(UUID)
        Optional<AttributeType> optional = getUserResponse.userAttributes().stream().filter(attribute -> StringUtils.equals("sub", attribute.name()) ).findFirst();
        if( optional.isPresent() ) {
        	logger.info(optional.get().value());
        	return optional.get().value();
        }else {
        	logger.info("Username: " + getUserResponse.username());
        	return null;
        }
        
    }
	
	public static boolean isValidToken( String token, String mailAddress ) throws AuthenticationException {
		try {
			String applicationClientId = EnvironmentUtil.getEnv( EnvironmentUtil.AWS_COGNITO_APP_CLIENT_ID );
			String region = EnvironmentUtil.getEnv( EnvironmentUtil.AWS_COGNITO_REGION );
//			String userName = EnvironmentUtil.getEnv( EnvironmentUtil.AWS_COGNITO_USER_NAME );
			String userPoolId = EnvironmentUtil.getEnv( EnvironmentUtil.AWS_COGNITO_USER_POOL_ID );

			String jwksUrl = "https://cognito-idp." + region + ".amazonaws.com/" + userPoolId
					+ "/.well-known/jwks.json";
			String expectedIssuer = "https://cognito-idp." + region + ".amazonaws.com/" + userPoolId;

			// Get the 'kid' from the JWT header
			DecodedJWT jwt = JWT.decode( token );
			String kid = jwt.getKeyId();

			// Retrieve the public key from JWKS
			JWKSet jwkSet = JWKSet.load( new URL( jwksUrl ) );
			JWK jwk = jwkSet.getKeyByKeyId( kid );
			RSAPublicKey publicKey = (RSAPublicKey) jwk.toRSAKey().toPublicKey();

			// Use the public key to verify the JWT's signature
			Algorithm algorithm = Algorithm.RSA256( publicKey, null );
			JWTVerifier verifier = JWT.require( algorithm ).build();

			// Verify the token
			DecodedJWT verifiedJwt = verifier.verify( token );

			if( logger.isInfoEnabled() ) {
				logger.info(  "verifiedJwt:" + LogUtil.objectToString(verifiedJwt) );
			}
			
			// Validate the Issuer
			if( !verifiedJwt.getIssuer().equals( expectedIssuer ) ) {
				logger.error( "Validate the Issuer: " + verifiedJwt.getIssuer() + " vs " + expectedIssuer );
				throw new RuntimeException( "Issuer does not match." );
			}

			// Validate the Subject
//			if( !verifiedJwt.getSubject().equals( userName ) ) {
//				logger.error( "userName: " + verifiedJwt.getSubject() + " vs " + userName );
//				throw new RuntimeException( "Subject does not match." );
//			}

			// Validate the Audience
			if( !verifiedJwt.getAudience().contains( applicationClientId ) ) {
				String audience = StringUtils.join(verifiedJwt.getAudience(), ",");
				logger.error( "Validate the Audience: " + audience + " vs " + applicationClientId );
				throw new RuntimeException( "Audience does not match." );
			}

			// Validate the Expiration Time
			if( verifiedJwt.getExpiresAt().before( new Date() ) ) {
				SimpleDateFormat simple = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
				logger.error( "Validate the Expiration Time: " + simple.format(verifiedJwt.getExpiresAt()) + " vs " + simple.format(new Date()) );
				throw new RuntimeException( "The token has expired." );
			}

			// Validate Not Before (optional)
			Date now = new Date();
			Date notBefore = verifiedJwt.getNotBefore();
			if( notBefore != null && notBefore.after( now ) ) {
				SimpleDateFormat simple = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
				logger.error( "Validate Not Before (optional): " + simple.format(notBefore) + " vs " + simple.format(now) );
				throw new RuntimeException( "The token is not yet valid." );
			}

			// Validate Issued At
			if( verifiedJwt.getIssuedAt().after( new Date() ) ) {
				SimpleDateFormat simple = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
				logger.error( "Validate Issued At: " + simple.format(verifiedJwt.getIssuedAt()) + " vs " + simple.format(new Date()) );
				throw new RuntimeException( "The token's issuance time is in the future." );
			}

			// Validate JWT ID
			if( verifiedJwt.getId() == null || verifiedJwt.getId().isEmpty() ) {
				logger.error( "Validate JWT ID: " + verifiedJwt.getId() + " is blank. " );
				throw new RuntimeException( "JWT ID is invalid." );
			}

			String email = jwt.getClaim( "email" ).asString();
			if( !StringUtils.equals( email, mailAddress ) ) {
				logger.error( "Validate email: " + email + " vs " + mailAddress );
				throw new RuntimeException( "Email is invalid." );
			}
		} catch (Exception e) {
			logger.error( e.getMessage(), e );
			throw new AuthenticationException( "Authentication error. " );
		}
		return true;
	}

}