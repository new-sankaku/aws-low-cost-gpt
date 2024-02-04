package com.yksc.dummy.model;

import java.util.Date;

public class ChatMessage {

	String messageId;
	//ai or user
	String sender;
	String message;
	Date sendDate;

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

}
