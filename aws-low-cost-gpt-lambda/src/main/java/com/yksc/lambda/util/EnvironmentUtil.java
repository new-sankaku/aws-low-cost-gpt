package com.yksc.lambda.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Obtain values such as API keys from environment variables.
 * Modify the code if you have strict security requirements.
 */
public class EnvironmentUtil {

	public static final String AWS_COGNITO_ACCESS_KEY = "AWS_COGNITO_ACCESS_KEY";
	public static final String AWS_COGNITO_REGION = "AWS_COGNITO_REGION";
	public static final String AWS_COGNITO_USER_POOL_ID = "AWS_COGNITO_USER_POOL_ID";
	public static final String AWS_COGNITO_APP_ID = "AWS_COGNITO_APP_ID";
	public static final String AWS_COGNITO_USER_NAME = "AWS_COGNITO_USER_POOL_ID";

	public static String getEnv( String envKey ) {
		String apiKey = System.getenv( envKey );
		if( StringUtils.isBlank( apiKey ) ) {
			System.out.println( "No value is set for the environment variable." );
			System.out.println( "AWS Management Console : " + envKey );
		}
		return apiKey;
	}

}
