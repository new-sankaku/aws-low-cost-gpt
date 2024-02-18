package com.yksc.lambda.controller;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yksc.lambda.data.repository.UserSettingRepository;
import com.yksc.lambda.util.JsonUtil;
import com.yksc.lambda.util.ResponseUtil;
import com.yksc.model.db.UserSetting;
import com.yksc.model.rest.RequestInfo;

//  "/UserSettings")
public class UserSettingsController {
	

//	GET 
	public APIGatewayProxyResponseEvent getUserSettings( RequestInfo requestInfo ) throws JsonProcessingException {
		UserSettingRepository repo = new UserSettingRepository();
		UserSetting userSetting = repo.findById(requestInfo.getUserUUID());
		
		if( userSetting == null ) {
			userSetting = defaultUserSetting(requestInfo.getUserUUID());
			repo.save(userSetting);
		}
		
		if( userSetting != null ) {
			return ResponseUtil.createResponseByOK( userSetting );
		} else {
			return ResponseUtil.notFoundResponse( "getUserSettings: isEmpty" );
		}
	}
	
//	POST
	public APIGatewayProxyResponseEvent postUserSettings( RequestInfo requestInfo ) throws JsonProcessingException {
		UserSettingRepository repo = new UserSettingRepository();
		
		UserSetting resultUserSetting = null;
		String body = requestInfo.getBody();
		UserSetting restUserSettings = JsonUtil.objectMapper.readValue(body, UserSetting.class);
		
		UserSetting dbUserSetting = repo.findById(requestInfo.getUserUUID());
		
		if( dbUserSetting == null ) {
			repo.save(restUserSettings);
			resultUserSetting = restUserSettings;
		}else {
			repo.delete(dbUserSetting);
			restUserSettings.setUserId(requestInfo.getUserUUID());
			repo.save(restUserSettings);
			resultUserSetting = restUserSettings;
		}
		
		if( resultUserSetting != null ) {
			return ResponseUtil.createResponseByOK( resultUserSetting );
		} else {
			return ResponseUtil.notFoundResponse( "getUserSettings: isEmpty" );
		}
	}


	public UserSetting defaultUserSetting( String userId ) {
		UserSetting userSettings = new UserSetting();
		userSettings.getHeadPromptList().add("Please respond in Japanese at all times.");
		userSettings.getHeadPromptList().add("Begin with your answer, followed by the explanation.");
		userSettings.getHeadPromptList().add("I am a beginner; kindly consider this in your responses.");
		userSettings.getHeadPromptList().add("I am an advanced learner; basic explanations are not required.");

		userSettings.getTailPromptList().add("Remember to adhere to the initial conditions set forth.");
		userSettings.getTailPromptList().add("If your response is incorrect, please acknowledge the mistake. Misleading statements are not allowed.");
		userSettings.getTailPromptList().add("If unsure about an answer, simply state that you don't know.");
		userSettings.getTailPromptList().add("End of instructions.");

		userSettings.setDarkMode(false);
		userSettings.setUserId(userId);
		return userSettings;
	}
	
}
