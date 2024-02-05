package com.yksc.model.db;

import java.util.List;

public class ChatRequest {
	
	private List<ChatMessage> chatMessageList ;
	private String selectedModel;
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
	

}
