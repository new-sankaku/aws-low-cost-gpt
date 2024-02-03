package com.yksc.dummy.model;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomHistory {
	private String roomId;
	private List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();

	public ChatRoomHistory() {
	}

	public ChatRoomHistory( String roomId, List<ChatMessage> chatMessages ) {
		this.roomId = roomId;
		this.chatMessages = chatMessages;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId( String roomId ) {
		this.roomId = roomId;
	}

	public List<ChatMessage> getChatMessages() {
		return chatMessages;
	}

	public void setChatMessages( List<ChatMessage> chatMessages ) {
		this.chatMessages = chatMessages;
	}

}
