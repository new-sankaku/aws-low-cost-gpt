package com.yksc.model.rest;

import java.util.List;

import com.yksc.model.db.ChatMessage;

public class ChatRequest {
	
	private List<ChatMessage> chatMessageList ;
	private String selectedModel;
	private String roomId;
	
	public List<ChatMessage> getChatMessageList() {
		return this.chatMessageList;
	}
	public void setChatMessageList(List<ChatMessage> chatMessageList) {
		this.chatMessageList = chatMessageList;
	}
	public String getSelectedModel() {
		return this.selectedModel;
	}
	public void setSelectedModel(String selectedModel) {
		this.selectedModel = selectedModel;
	}
	public String getRoomId() {
		return this.roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	

}
