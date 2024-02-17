package com.yksc.model.rest;

import java.util.List;

import com.yksc.model.db.ChatMessage;

public class ChatRequest {
	
	private List<ChatMessage> chatMessageList ;
	private String selectedAiModel;
	private String roomId;
	
	public List<ChatMessage> getChatMessageList() {
		return this.chatMessageList;
	}
	public void setChatMessageList(List<ChatMessage> chatMessageList) {
		this.chatMessageList = chatMessageList;
	}

	public String getSelectedAiModel() {
		return this.selectedAiModel;
	}
	public void setSelectedAiModel(String selectedAiModel) {
		this.selectedAiModel = selectedAiModel;
	}
	public String getRoomId() {
		return this.roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	

}
