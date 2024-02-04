package com.yksc.dummy.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yksc.dummy.model.ChatMessage;
import com.yksc.dummy.util.IdGeneraterUtil;

@RestController
@RequestMapping("/ChatCompletions")
public class ChatCompletionsController {

	@PostMapping
	public ResponseEntity<Object> generateText( @RequestBody List<ChatMessage> chatMessageList ) {
		SimpleDateFormat simple = new SimpleDateFormat( "yyyy/MM/dd hh:mm:ss.SSS" );
		String date = simple.format( Calendar.getInstance().getTime() );
		String result = "This is a pretend generated message. " + date + " (><;)";

		String guid = IdGeneraterUtil.nextGuid();
		Date sendDate = Calendar.getInstance().getTime();
		ChatMessage chatMessage = new ChatMessage( guid, "ai", result, sendDate );

		return ResponseEntity.ok( chatMessage );
	}

}
