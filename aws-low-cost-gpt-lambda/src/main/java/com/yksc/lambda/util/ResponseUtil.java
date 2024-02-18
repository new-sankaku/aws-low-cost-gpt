package com.yksc.lambda.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yksc.lambda.log.LoggerFactory;

public class ResponseUtil {
	private static final Logger logger = LoggerFactory.getLogger();

    public static void main(String[] args) {
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warning message");
        logger.error("This is an error message");
    }
	
	
	public static APIGatewayProxyResponseEvent createResponseByOK( Object object )
			throws JsonProcessingException {
		String json = JsonUtil.objectMapper.writeValueAsString( object );
		return createResponse( json, 200 );
	}

	public static APIGatewayProxyResponseEvent createResponseByOK( List<Object> objectList )
			throws JsonProcessingException {
		String json = JsonUtil.objectMapper.writeValueAsString( objectList );

		return createResponse( json, 200 );
	}
	
	
	public static APIGatewayProxyResponseEvent createResponse( Object object, int statusCode )
			throws JsonProcessingException {
		String json = JsonUtil.objectMapper.writeValueAsString( object );
		return createResponse( json, statusCode );
	}

	public static APIGatewayProxyResponseEvent createResponse( List<Object> objectList, int statusCode )
			throws JsonProcessingException {
		String json = JsonUtil.objectMapper.writeValueAsString( objectList );

		return createResponse( json, statusCode );
	}

	public static APIGatewayProxyResponseEvent notFoundResponse( String message ) {
		logger.info( "message :" + message );
		String responseBody = "{\"message\": \"not found.\"}";
		return createResponse( responseBody, 404 );

	}

	public static APIGatewayProxyResponseEvent unauthorizedResponse( String message ) {
		logger.info( "message :" + message );
		String responseBody = "{\"message\": \"Authentication error.\"}";
		return createResponse( responseBody, 401 );
	}

	public static APIGatewayProxyResponseEvent unknownErrorResponse( String message ) {
		logger.info( "message :" + message );
		String responseBody = "{\"message\": \"unknown error.\"}";
		return createResponse( responseBody, 400 );
	}

	private static APIGatewayProxyResponseEvent createResponse(String message, int httpCode) {
	    logger.info("message :" + message);
	    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
	    response.setStatusCode(httpCode);
	    response.setBody(message);

	    // CORS header.
	    Map<String, String> corsHeaders = new HashMap<String, String>();
	    corsHeaders.put("Content-Type", "application/json");
	    corsHeaders.put("Access-Control-Allow-Origin", "*");
	    corsHeaders.put("Access-Control-Allow-Methods", "PATCH,PUT,HEAD,DELETE,GET,POST,OPTIONS");
	    corsHeaders.put("Access-Control-Allow-Headers", "user-token,access-token,user-mail-address,Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token");
	    response.setHeaders(corsHeaders);
	    return response;
	}


}
