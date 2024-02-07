package com.yksc.lambda;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.yksc.lambda.exception.AuthenticationException;
import com.yksc.lambda.util.CognitoValidationUtil;
import com.yksc.lambda.util.RequestRouterUtil;
import com.yksc.lambda.util.ResponseUtil;
import com.yksc.model.rest.RequestInfo;

public class LambdaFunctionHandler
		implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	@Override
	public APIGatewayProxyResponseEvent handleRequest( APIGatewayProxyRequestEvent request, Context context ) {
		try {
			RequestInfo requestInfo = setRequestInfo( request );
			if( CognitoValidationUtil.isValidToken( requestInfo.getToken(), requestInfo.getEmail() ) ) {
				return RequestRouterUtil.next( requestInfo );
			}
		} catch (AuthenticationException e) {
			System.out.println(e);
			return ResponseUtil.unauthorizedResponse( "is ValidToken is error."  + e.getMessage());
		} catch (Exception e) {
			System.out.println(e);
			return ResponseUtil.unknownErrorResponse( "unknown error. " + e.getMessage() );
		}

		return ResponseUtil.unknownErrorResponse( "unknown error. router error." );
	}

	public RequestInfo setRequestInfo( APIGatewayProxyRequestEvent request ) {
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
		return requestInfo;
	}
}
