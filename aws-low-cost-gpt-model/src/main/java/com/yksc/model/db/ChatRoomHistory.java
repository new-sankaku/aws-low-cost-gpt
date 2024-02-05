package com.yksc.model.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="ChatRoomHistory")
public class ChatRoomHistory {
	
	@DynamoDBHashKey
	private String roomId;
    
	@DynamoDBRangeKey
    private Date sendDate;

    private String messageId; 
    
	@DynamoDBIgnore
	private List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();


	public ChatRoomHistory() {
	}

	public ChatRoomHistory( String roomId, String messageId ) {
		this.roomId = roomId;
		this.messageId = messageId;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId( String roomId ) {
		this.roomId = roomId;
	}

	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public List<ChatMessage> getChatMessages() {
		return this.chatMessages;
	}

	public void setChatMessages(List<ChatMessage> chatMessages) {
		this.chatMessages = chatMessages;
	}

}
