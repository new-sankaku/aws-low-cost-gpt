package com.yksc.lambda.util;

import org.apache.commons.lang3.StringUtils;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yksc.lambda.controller.ChatRoomController;
import com.yksc.lambda.controller.ChatRoomHistoryController;
import com.yksc.model.rest.RequestInfo;


public class RequestRouterUtil {

	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String PUT = "PUT";
	private static final String DELETE = "DELETE";
	
	public static APIGatewayProxyResponseEvent next( RequestInfo requestInfo ) throws JsonProcessingException {

		String path = requestInfo.getPath();
		String httpMethod = requestInfo.getHttpMethod();
		
		String str = path + ":" + httpMethod;
		
		if( StringUtils.startsWith(path, "/ChatRoom") ) {
			ChatRoomController chatRoomController = new ChatRoomController();
			
			if (path.matches("/ChatRoom/Message/.+")) {
				if(StringUtils.equals( GET, httpMethod ))    return chatRoomController.getChatRoomMessageByRoomId(requestInfo);
			
			}else if (path.matches("/ChatRoom/.+")) {
				if(StringUtils.equals( GET, httpMethod ))    return chatRoomController.getChatRoomById(requestInfo);
				//TODO next version
				if(StringUtils.equals( DELETE, httpMethod )) return ResponseUtil.notFoundResponse(str);
			
			}else if (path.matches("/ChatRoom")) {
				//not use.
				if(StringUtils.equals( PUT, httpMethod ))    return ResponseUtil.notFoundResponse(str);
			
			}
		}else if( StringUtils.startsWith(path, "/ChatRoomHistory") ) {
			ChatRoomHistoryController chatRoomHistoryController = new ChatRoomHistoryController();
			
			if (path.matches("/ChatRoomHistory")) {
				if(StringUtils.equals( GET, httpMethod ))    return chatRoomHistoryController.getChatRoomHistory(requestInfo);
			
			}
		}else if( StringUtils.startsWith(path, "/ChatCompletions") ) {
			if (path.matches("/ChatCompletions")) {
				if(StringUtils.equals( POST, httpMethod ))   return ResponseUtil.notFoundResponse(str);
			}	
		}else if( StringUtils.startsWith(path, "/UsersPlan") ) {
			if (path.matches("/ChatCompletions")) {
				if(StringUtils.equals( POST, httpMethod ))   return ResponseUtil.notFoundResponse(str);
			}	
		}
		
		return ResponseUtil.notFoundResponse(str);
	}

}
