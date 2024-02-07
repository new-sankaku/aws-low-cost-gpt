package com.yksc.lambda.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yksc.lambda.controller.param.PathParam;
import com.yksc.lambda.data.repository.ChatMessageRepository;
import com.yksc.lambda.data.repository.ChatRoomRepository;
import com.yksc.lambda.util.ResponseUtil;
import com.yksc.model.db.ChatMessage;
import com.yksc.model.db.ChatRoom;
import com.yksc.model.rest.RequestInfo;

import software.amazon.awssdk.http.HttpStatusCode;

public class ChatRoomController {

	/**
	 * 
	 * @param requestInfo
	 * @return ChatRoom
	 * @throws JsonProcessingException
	 */
	public APIGatewayProxyResponseEvent getChatRoomById( RequestInfo requestInfo ) throws JsonProcessingException {
		String roomId = requestInfo.getPathParameters().get( PathParam.ROOM_ID );
		if( roomId == null ) {
			return ResponseUtil.notFoundResponse( "getChatRoomById:roomId == null" );
		}
		
		ChatRoomRepository chatRoomRepo = new ChatRoomRepository();
		ChatRoom chatRoom = chatRoomRepo.findById(roomId, requestInfo.getUserUUID());
		if( chatRoom == null ) {
			return ResponseUtil.notFoundResponse("getChatRoomById:chatRoom == null");
		}
		
		return ResponseUtil.createResponseByOK(chatRoom );
	}

	/**
	 * 
	 * @param requestInfo
	 * @return List<ChatMessage>
	 * @throws JsonProcessingException
	 */
	public APIGatewayProxyResponseEvent getChatRoomMessageByRoomId( RequestInfo requestInfo  ) throws JsonProcessingException {

		String roomId = requestInfo.getPathParameters().get( PathParam.ROOM_ID );
		if( roomId == null ) {
			return ResponseUtil.notFoundResponse("getChatRoomMessageByRoomId:roomId == null");
		}
		
		ChatRoomRepository chatRoomRepo = new ChatRoomRepository();
		ChatRoom chatRoom = chatRoomRepo.findById(roomId, requestInfo.getUserUUID());
		if( chatRoom == null ) {
			return ResponseUtil.notFoundResponse("getChatRoomMessageByRoomId:chatRoom == null");
		}
		if( CollectionUtils.isEmpty(chatRoom.getChatMessageIds()) ) {
			return ResponseUtil.notFoundResponse("getChatRoomMessageByRoomId:CollectionUtils.isEmpty(chatRoom.getChatMessageIds())");
		}

		ChatMessageRepository chatMessageRepo = new ChatMessageRepository();
		List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
		
		for (String chatMessageId : chatRoom.getChatMessageIds()) {
			ChatMessage chatMessage = chatMessageRepo.findById(chatMessageId);
			if( chatMessage != null ) {
				chatMessageList.add(chatMessage);
			}
		}
		return ResponseUtil.createResponseByOK(chatMessageList );
	}

	/**
	 * 
	 * @param updateRoom
	 * @return ChatRoom
	 * @throws JsonProcessingException
	 */
	public APIGatewayProxyResponseEvent updateChatRoom( ChatRoom updateRoom )  throws JsonProcessingException {
		
		ChatRoomRepository chatRoomRepo = new ChatRoomRepository();
		chatRoomRepo.update(updateRoom);
		return ResponseUtil.createResponseByOK(updateRoom );
	}

	/**
	 * ChatRoom
	 * @param requestInfo
	 * @return
	 * @throws JsonProcessingException
	 */
	public APIGatewayProxyResponseEvent deleteChatRoom( RequestInfo requestInfo  )  throws JsonProcessingException {
		String roomId = requestInfo.getPathParameters().get( PathParam.ROOM_ID );
		if( roomId == null ) {
			return ResponseUtil.notFoundResponse("deleteChatRoom:roomId == null");
		}
		
		ChatRoomRepository chatRoomRepo = new ChatRoomRepository();
		ChatRoom chatRoom = chatRoomRepo.findById(roomId, requestInfo.getUserUUID());
		if( chatRoom == null ) {
			return ResponseUtil.notFoundResponse("deleteChatRoom:chatRoom == null ");
		}
		
		chatRoomRepo.delete(chatRoom);
		return ResponseUtil.createResponse("delete success", HttpStatusCode.OK);
	}
}
