package com.yksc.dummy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yksc.dummy.data.Data;
import com.yksc.model.db.ChatMessage;
import com.yksc.model.db.ChatRoom;

@RestController
@RequestMapping("/ChatRooms")
public class ChatRoomsController {

	@GetMapping
	public ResponseEntity<Object> getChatRoomHistory( @RequestHeader("user-mail-address") String userMailAddress ) {

		System.out.println( "getChatRoomHistory : userMailAddress=" + userMailAddress );
		List<ChatRoom> chatRoomList = Data.getChatRoomList( userMailAddress );
		System.out.println( "getChatRoomHistory : chatRoomList size=" + chatRoomList.size() );

		return ResponseEntity.ok( chatRoomList );
	}

	@GetMapping("/Message/{roomId}")
	public ResponseEntity<Object> getChatRoomMessageById( @PathVariable String roomId ) {
		List<ChatMessage> chatMessageList = Data.getChatRoomMessage( roomId );

		for (ChatMessage chatMessage : chatMessageList) {
        	System.out.println("get message id:" + chatMessage.getMessageId() + " @ " + chatMessage.getMessage());
		}
		
		if( chatMessageList != null ) {
			return ResponseEntity.ok( chatMessageList );
		} else {
			return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "not found getChatRoomById" );
		}
	}
	
	
	/**
	 * not use.
	 * @param updateRoom
	 * @return
	 */
	@PutMapping
	public ResponseEntity<Object> updateChatRoom( @RequestBody ChatRoom updateRoom ) {
		ChatRoom chatRoom = Data.getChatRoom( updateRoom.getRoomId() );
		if( chatRoom != null ) {
			chatRoom.setRoomTitle( updateRoom.getRoomTitle() );
			return ResponseEntity.ok( chatRoom );
		} else {
			return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "not found updateProduct" );
		}
	}
	
	/**
	 * not use.
	 * @param updateRoom
	 * @return
	 */
	@DeleteMapping("/{roomId}")
	public void deleteChatRoom( @PathVariable String roomId ) {
		Data.chatRoomList.removeIf( chatRoom -> chatRoom.getRoomId().equals( roomId ) );
	}
	

}
