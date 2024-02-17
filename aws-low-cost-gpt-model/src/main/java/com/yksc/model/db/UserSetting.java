package com.yksc.model.db;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="UserSetting")
public class UserSetting {
	@DynamoDBHashKey
	private String userId;
	
	@DynamoDBAttribute
	private List<String> headPromptList = new ArrayList<String>();
	@DynamoDBAttribute
	private List<String> tailPromptList = new ArrayList<String>();

	@DynamoDBAttribute
	private boolean headPromptEnabled = false;
	@DynamoDBAttribute
	private boolean tailPromptEnabled = false;

	@DynamoDBAttribute
	private boolean darkMode = false;

	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getHeadPromptList() {
		return this.headPromptList;
	}
	public void setHeadPromptList(List<String> headPromptList) {
		this.headPromptList = headPromptList;
	}
	public List<String> getTailPromptList() {
		return this.tailPromptList;
	}
	public void setTailPromptList(List<String> tailPromptList) {
		this.tailPromptList = tailPromptList;
	}
	public boolean isDarkMode() {
		return this.darkMode;
	}
	public void setDarkMode(boolean darkMode) {
		this.darkMode = darkMode;
	}
	public boolean isHeadPromptEnabled() {
		return this.headPromptEnabled;
	}
	public void setHeadPromptEnabled(boolean headPromptEnabled) {
		this.headPromptEnabled = headPromptEnabled;
	}
	public boolean isTailPromptEnabled() {
		return this.tailPromptEnabled;
	}
	public void setTailPromptEnabled(boolean tailPromptEnabled) {
		this.tailPromptEnabled = tailPromptEnabled;
	}

	
}
