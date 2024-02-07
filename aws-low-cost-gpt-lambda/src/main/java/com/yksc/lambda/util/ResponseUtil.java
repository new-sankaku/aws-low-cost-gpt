package com.yksc.lambda.util;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseUtil {

	public static APIGatewayProxyResponseEvent createResponseByOK( Object object )
			throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString( object );
		return createResponse( json, 200 );
	}

	public static APIGatewayProxyResponseEvent createResponseByOK( List<Object> objectList )
			throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString( objectList );

		return createResponse( json, 200 );
	}
	
	
	public static APIGatewayProxyResponseEvent createResponse( Object object, int statusCode )
			throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString( object );
		return createResponse( json, statusCode );
	}

	public static APIGatewayProxyResponseEvent createResponse( List<Object> objectList, int statusCode )
			throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString( objectList );

		return createResponse( json, statusCode );
	}

	public static APIGatewayProxyResponseEvent notFoundResponse( String message ) {
		System.out.println( "message :" + message );
		String responseBody = "{\"message\": \"not found.\"}";
		return createResponse( responseBody, 404 );

	}

	public static APIGatewayProxyResponseEvent unauthorizedResponse( String message ) {
		System.out.println( "message :" + message );
		String responseBody = "{\"message\": \"Authentication error.\"}";
		return createResponse( responseBody, 401 );
	}

	public static APIGatewayProxyResponseEvent unknownErrorResponse( String message ) {
		System.out.println( "message :" + message );
		String responseBody = "{\"message\": \"unknown error.\"}";
		return createResponse( responseBody, 400 );
	}

	private static APIGatewayProxyResponseEvent createResponse( String message, int httpCode ) {
		System.out.println( "message :" + message );
		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
		response.setStatusCode( httpCode );
		response.setBody( message );
		response.setHeaders( Map.of( "Content-Type", "application/json" ) );
		return response;
	}

	public static void main( String[] args ) {

	}

}
