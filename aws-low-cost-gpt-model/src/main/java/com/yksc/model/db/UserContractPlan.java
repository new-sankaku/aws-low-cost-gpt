package com.yksc.model.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserContractPlan {

	private Date subscriptionStartDate;
	private Date subscriptionEndDate;
	private double prepaidBalanceDoller;
	private double usageLimit;
	List<String> modelId = new ArrayList<String>();

	public UserContractPlan() {
	}

	public UserContractPlan( Date subscriptionStartDate, Date subscriptionEndDate, double prepaidBalanceDoller,
			double usageLimit, List<String> modelId ) {
		this.subscriptionStartDate = subscriptionStartDate;
		this.subscriptionEndDate = subscriptionEndDate;
		this.prepaidBalanceDoller = prepaidBalanceDoller;
		this.usageLimit = usageLimit;
		this.modelId = modelId;
	}

	public Date getSubscriptionStartDate() {
		return subscriptionStartDate;
	}

	public void setSubscriptionStartDate( Date subscriptionStartDate ) {
		this.subscriptionStartDate = subscriptionStartDate;
	}

	public Date getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	public void setSubscriptionEndDate( Date subscriptionEndDate ) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	public double getPrepaidBalanceDoller() {
		return prepaidBalanceDoller;
	}

	public void setPrepaidBalanceDoller( double prepaidBalanceDoller ) {
		this.prepaidBalanceDoller = prepaidBalanceDoller;
	}

	public double getUsageLimit() {
		return usageLimit;
	}

	public void setUsageLimit( double usageLimit ) {
		this.usageLimit = usageLimit;
	}

	public List<String> getModelId() {
		return modelId;
	}

	public void setModelId( List<String> modelId ) {
		this.modelId = modelId;
	}

}
