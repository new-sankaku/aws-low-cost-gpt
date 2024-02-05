package com.yksc.model.rest;

import java.util.Map;

public class RequestInfo {
	private String path;
	private String httpMethod;
	private String body;
	private String token;
	private String accessToken;
	private String email;
	private Map<String, String> pathParameters;

	public Map<String, String> getPathParameters() {
		return this.pathParameters;
	}

	public void setPathParameters( Map<String, String> pathParameters ) {
		this.pathParameters = pathParameters;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken( String token ) {
		this.token = token;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail( String email ) {
		this.email = email;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath( String path ) {
		this.path = path;
	}

	public String getHttpMethod() {
		return this.httpMethod;
	}

	public void setHttpMethod( String httpMethod ) {
		this.httpMethod = httpMethod;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody( String body ) {
		this.body = body;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
