package com.yksc.model.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.yksc.model.db.date.DateConverter;

@DynamoDBTable(tableName="ChatRoom")
public class ChatRoom {

	@DynamoDBHashKey
	private String roomId;
	@DynamoDBRangeKey
	private String ownerUserId;
	@DynamoDBAttribute
	private String roomTitle; 
	@DynamoDBTypeConverted(converter = DateConverter.class) 
	private Date createDate;
	@DynamoDBTypeConverted(converter = DateConverter.class) 
	private Date updateDate;
	@DynamoDBAttribute
	private String aiModel;
	@DynamoDBAttribute
	private String aiModelSource;
	@DynamoDBAttribute
	private double sumTotal;
	@DynamoDBAttribute
	private List<String> chatMessageIds = new ArrayList<String>();
    

	public ChatRoom() {
	}

	public ChatRoom( String roomId, String ownerUserId, String roomTitle, Date createDate, Date updateDate, String aiModel, String aiModelSource, double sumTotal ) {
		this.roomId = roomId;
		this.ownerUserId = ownerUserId;
		this.roomTitle = roomTitle;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.aiModel = aiModel;
		this.aiModelSource =aiModelSource;
		this.sumTotal = sumTotal;
	}


	public static void main( String[] args ) {
	}

	public List<String> getChatMessageIds() {
		return this.chatMessageIds;
	}

	public void setChatMessageIds(List<String> chatMessageIds) {
		this.chatMessageIds = chatMessageIds;
	}

	public String getOwnerUserId() {
		return this.ownerUserId;
	}

	public void setOwnerUserId( String ownerUserId ) {
		this.ownerUserId = ownerUserId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId( String roomId ) {
		this.roomId = roomId;
	}

	public String getRoomTitle() {
		return roomTitle;
	}

	public void setRoomTitle( String roomTitle ) {
		this.roomTitle = roomTitle;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate( Date createDate ) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate( Date updateDate ) {
		this.updateDate = updateDate;
	}

	public String getAiModel() {
		return this.aiModel;
	}

	public void setAiModel(String aiModel) {
		this.aiModel = aiModel;
	}

	public String getAiModelSource() {
		return this.aiModelSource;
	}

	public void setAiModelSource(String aiModelSource) {
		this.aiModelSource = aiModelSource;
	}

	public double getSumTotal() {
		return this.sumTotal;
	}

	public void setSumTotal(double sumTotal) {
		this.sumTotal = sumTotal;
	}

}
