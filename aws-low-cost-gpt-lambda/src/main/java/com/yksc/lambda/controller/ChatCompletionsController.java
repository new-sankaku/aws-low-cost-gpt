package com.yksc.lambda.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.yksc.lambda.util.IdGeneraterUtil;
import com.yksc.model.db.ChatMessage;

public class ChatCompletionsController {

	public ChatMessage generateText( List<ChatMessage> chatMessageList ) {
		SimpleDateFormat simple = new SimpleDateFormat( "yyyy/MM/dd hh:mm:ss.SSS" );
		String date = simple.format( Calendar.getInstance().getTime() );
		String result = "This is a pretend generated message. " + date + " (><;)";

		String guid = IdGeneraterUtil.nextGuid();
		Date sendDate = Calendar.getInstance().getTime();
		ChatMessage chatMessage = new ChatMessage( guid, "ai", result, sendDate );

		return chatMessage;
	}

}
