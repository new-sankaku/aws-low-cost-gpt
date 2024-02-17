package com.yksc.model.db;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.yksc.model.db.date.DateConverter;

@DynamoDBTable(tableName="ChatMessage")
public class ChatMessage {
	public static final CharSequence SENDER_AI = "ai";
	public static final CharSequence SENDER_USER = "user";

	@DynamoDBHashKey
	String messageId;
	@DynamoDBAttribute
	String sender;
	@DynamoDBAttribute
	String message; 
	@DynamoDBTypeConverted(converter = DateConverter.class) 
	Date sendDate;
	@DynamoDBAttribute
	long responseTime;
	@DynamoDBAttribute
	long token;
	
	@DynamoDBIgnore
	String roomId;
	@DynamoDBIgnore
	String roomTitle;
	
	public ChatMessage() {
	}

	public ChatMessage( String sender, String message ) {
		this.sender = sender;
		this.message = message;
	}

	public ChatMessage( String messageId, String sender, String message, Date sendDate ) {
		this.messageId = messageId;
		this.sender = sender;
		this.message = message;
		this.sendDate = sendDate;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId( String messageId ) {
		this.messageId = messageId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender( String sender ) {
		this.sender = sender;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate( Date sendDate ) {
		this.sendDate = sendDate;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage( String message ) {
		this.message = message;
	}

	public long getResponseTime() {
		return this.responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public long getToken() {
		return this.token;
	}

	public void setToken(long token) {
		this.token = token;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomTitle() {
		return this.roomTitle;
	}

	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}

}
