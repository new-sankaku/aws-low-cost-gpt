package com.yksc.dummy.model;

import java.util.Date;

public class ChatMessage {

	String messageId;
	//ai or user
	String sender;
	//ai or user
	String recipient;
	String messageBody;
	Date sendDate;
	String messageType;

	public ChatMessage() {
	}

	public ChatMessage( String messageId, String sender, String recipient, String messageBody, Date sendDate,
			String messageType ) {
		this.messageId = messageId;
		this.sender = sender;
		this.recipient = recipient;
		this.messageBody = messageBody;
		this.sendDate = sendDate;
		this.messageType = messageType;
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

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient( String recipient ) {
		this.recipient = recipient;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody( String messageBody ) {
		this.messageBody = messageBody;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate( Date sendDate ) {
		this.sendDate = sendDate;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType( String messageType ) {
		this.messageType = messageType;
	}

}
