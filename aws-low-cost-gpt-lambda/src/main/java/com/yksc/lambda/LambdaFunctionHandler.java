package com.yksc.lambda;

import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.yksc.lambda.exception.AuthenticationException;
import com.yksc.lambda.log.LogUtil;
import com.yksc.lambda.log.LoggerFactory;
import com.yksc.lambda.util.CognitoValidationUtil;
import com.yksc.lambda.util.RequestRouterUtil;
import com.yksc.lambda.util.ResponseUtil;
import com.yksc.model.rest.RequestInfo;

import software.amazon.awssdk.http.HttpStatusCode;

public class LambdaFunctionHandler
		implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	private static final Logger logger = LoggerFactory.getLogger();

	@Override
	public APIGatewayProxyResponseEvent handleRequest( APIGatewayProxyRequestEvent request, Context context ) {
		try {
			RequestInfo requestInfo = setRequestInfo( request );
			if( CognitoValidationUtil.isValidToken( requestInfo.getToken(), requestInfo.getEmail() ) ) {
				
				APIGatewayProxyResponseEvent result = RequestRouterUtil.next( requestInfo );
				
				if( logger.isInfoEnabled() && result.getStatusCode() == HttpStatusCode.OK) {
					logger.info(LogUtil.objectToString(result));
				}else if( logger.isInfoEnabled() && result.getStatusCode() != HttpStatusCode.OK ){
					logger.error(LogUtil.objectToString(result));
				}
				
				return result;
			}
		} catch (AuthenticationException e) {
			logger.error(e);
			return ResponseUtil.unauthorizedResponse( "is ValidToken is error."  + e.getMessage());
		} catch (Exception e) {
			logger.error(e);
			return ResponseUtil.unknownErrorResponse( "unknown error. " + e.getMessage() );
		}

		return ResponseUtil.unknownErrorResponse( "unknown error. router error." );
	}

	public RequestInfo setRequestInfo( APIGatewayProxyRequestEvent request ) {
		logger.info( "setRequestInfo start" );

		if( logger.isInfoEnabled()) {
			logger.info( "setRequestInfo APIGatewayProxyRequestEvent" + LogUtil.objectToString(request) );
		}
		
		String httpMethod = request.getHttpMethod();
		String path = request.getPath();
		String body = request.getBody();

		Map<String, String> headers = request.getHeaders();
		String authorizationHeader = headers.get( "Authorization" );
		String email = headers.get( "user-mail-address" );
		String token = authorizationHeader != null && authorizationHeader.startsWith( "Bearer " )
				? authorizationHeader.substring( 7 )
				: null;
		String accessToken = headers.get( "access-token" );
		
		Map<String, String> pathParameters = request.getPathParameters();

		RequestInfo requestInfo = new RequestInfo();
		requestInfo.setBody( body );
		requestInfo.setEmail( email );
		requestInfo.setHttpMethod( httpMethod );
		requestInfo.setPath( path );
		requestInfo.setToken( token );
		requestInfo.setAccessToken( accessToken );
		requestInfo.setPathParameters( pathParameters );

		String userUuid = CognitoValidationUtil.getUserUUID(accessToken);
		requestInfo.setUserUUID(userUuid);

		if( logger.isInfoEnabled()) {
			logger.info( "setRequestInfo requestInfo" + LogUtil.objectToString(requestInfo) );
		}
		
		
		logger.info( "setRequestInfo end" );
		return requestInfo;
	}
}
