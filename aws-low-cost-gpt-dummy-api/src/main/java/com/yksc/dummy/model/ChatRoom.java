package com.yksc.dummy.model;

import java.util.Date;

public class ChatRoom {

	private String roomId;
	private String ownerUserId;
	private String roomTitle;
	private Date createDate;
	private Date updateDate;
	private String aiModel;
	private String aiModelSource;
	private double sumTotal;

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
