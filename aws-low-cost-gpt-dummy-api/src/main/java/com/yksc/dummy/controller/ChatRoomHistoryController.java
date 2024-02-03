package com.yksc.dummy.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yksc.dummy.data.Data;
import com.yksc.dummy.model.ChatRoom;

@RestController
@RequestMapping("/ChatRoomHistory")
public class ChatRoomHistoryController {

	@GetMapping
	public ResponseEntity<Object> getChatRoomHistory( @RequestHeader("user-mail-address") String userMailAddress ) {

		System.out.println( "getChatRoomHistory : userMailAddress=" + userMailAddress );
		List<ChatRoom> chatRoom = Data.getChatRoomList( userMailAddress );
		System.out.println( "getChatRoomHistory : size=" + chatRoom.size() );

		return ResponseEntity.ok( chatRoom );
	}

}
