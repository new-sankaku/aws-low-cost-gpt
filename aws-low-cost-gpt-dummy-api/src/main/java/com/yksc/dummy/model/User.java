package com.yksc.dummy.model;

import java.util.Date;

import com.yksc.dummy.util.IdGeneraterUtil;

public class User {
	private String userId;
	private String accountName;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private Date createdDate;
	private Date updatedDate;
	private Date lastLoginDate;
	private String country;
	private String address;
	private String contractPlanId;

	public User() {
	}

	public User( String accountName, String firstName, String lastName, String phoneNumber, String email,
			Date createdDate, Date updatedDate, Date lastLoginDate, String country, String address,
			String contractPlanId ) {
		this.userId = IdGeneraterUtil.nextGuid();
		this.accountName = accountName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.lastLoginDate = lastLoginDate;
		this.country = country;
		this.address = address;
		this.contractPlanId = contractPlanId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId( String userId ) {
		this.userId = userId;
	}

	public String getContractPlanId() {
		return contractPlanId;
	}

	public void setContractPlanId( String contractPlanId ) {
		this.contractPlanId = contractPlanId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName( String accountName ) {
		this.accountName = accountName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName( String firstName ) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName( String lastName ) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber( String phoneNumber ) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate( Date createdDate ) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate( Date updatedDate ) {
		this.updatedDate = updatedDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate( Date lastLoginDate ) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry( String country ) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress( String address ) {
		this.address = address;
	}
}
