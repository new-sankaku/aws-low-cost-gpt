package com.yksc.lambda.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yksc.lambda.controller.ChatCompletionsController;
import com.yksc.lambda.controller.ChatRoomsController;
import com.yksc.lambda.controller.UserSettingsController;
import com.yksc.lambda.controller.UsersPlanController;
import com.yksc.lambda.log.LoggerFactory;
import com.yksc.model.rest.RequestInfo;


public class RequestRouterUtil {
	private static final Logger logger = LoggerFactory.getLogger();

	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String PUT = "PUT";
	private static final String DELETE = "DELETE";
	
	public static APIGatewayProxyResponseEvent next( RequestInfo requestInfo ) throws JsonProcessingException {

		String path = requestInfo.getPath();
		String httpMethod = requestInfo.getHttpMethod();
		
		String str = path + ":" + httpMethod;
		logger.info("next:" + str);
		
		if( StringUtils.startsWith(path, "/UserSettings") ) {
			UserSettingsController userSettingsController = new UserSettingsController();
			
			if(StringUtils.equals( GET, httpMethod ))    return userSettingsController.getUserSettings(requestInfo);
			if(StringUtils.equals( POST, httpMethod ))    return userSettingsController.postUserSettings(requestInfo);
			
		}else if( StringUtils.startsWith(path, "/ChatRooms") ) {
			ChatRoomsController chatRoomController = new ChatRoomsController();
			
			if (path.matches("/ChatRooms/Message/.+")) {
				if(StringUtils.equals( GET, httpMethod ))    return chatRoomController.getChatRoomMessageById(requestInfo);
			
			}else if (path.matches("/ChatRooms/.+")) {
				//TODO next version
				if(StringUtils.equals( DELETE, httpMethod )) return ResponseUtil.notFoundResponse(str);
			
			}else if (path.matches("/ChatRooms")) {
				if(StringUtils.equals( GET, httpMethod ))    return chatRoomController.getChatRoomHistory(requestInfo);
			
			}
		}else if( StringUtils.startsWith(path, "/ChatCompletions") ) {
			
			ChatCompletionsController chatCompletionsController = new ChatCompletionsController();
			if (path.matches("/ChatCompletions")) {
				if(StringUtils.equals( POST, httpMethod ))   return chatCompletionsController.generateText(requestInfo);
			}	
		}else if( StringUtils.startsWith(path, "/UsersPlan") ) {
			UsersPlanController usersPlanController = new UsersPlanController();

			if (path.matches("/UsersPlan")) {
				if(StringUtils.equals( GET, httpMethod ))   return usersPlanController.getUserPlanByUserId(requestInfo);
			}	
		}
		logger.error("ERROR UNKNOWN PATH:" + str);
		return ResponseUtil.notFoundResponse(str);
	}

	
}
