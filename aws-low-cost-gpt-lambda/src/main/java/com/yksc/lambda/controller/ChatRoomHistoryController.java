package com.yksc.lambda.controller;

import java.util.List;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yksc.lambda.data.repository.ChatRoomRepository;
import com.yksc.lambda.util.ResponseUtil;
import com.yksc.model.db.ChatRoom;
import com.yksc.model.rest.RequestInfo;

public class ChatRoomHistoryController {

	/**
	 * 
	 * @param requestInfo
	 * @return List<ChatRoom>
	 * @throws JsonProcessingException 
	 */
	public APIGatewayProxyResponseEvent getChatRoomHistory( RequestInfo requestInfo ) throws JsonProcessingException {

		ChatRoomRepository chatRoomRepo = new ChatRoomRepository();
		List<ChatRoom> chatRoomList = chatRoomRepo.findByOwnerUserId( requestInfo.getUserUUID() );
		return ResponseUtil.createResponseByOK(chatRoomList );
	}

}
