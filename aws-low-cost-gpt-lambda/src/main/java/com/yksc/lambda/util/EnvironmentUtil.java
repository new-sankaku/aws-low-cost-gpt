package com.yksc.lambda.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import com.yksc.lambda.log.LoggerFactory;

/**
 * Obtain values such as API keys from environment variables.
 * Modify the code if you have strict security requirements.
 */
public class EnvironmentUtil {
	private static final Logger logger = LoggerFactory.getLogger();

	public static final String AWS_COGNITO_ACCESS_KEY = "AWS_COGNITO_ACCESS_KEY";
	public static final String AWS_COGNITO_REGION = "AWS_COGNITO_REGION";
	public static final String AWS_COGNITO_USER_POOL_ID = "AWS_COGNITO_USER_POOL_ID";
	public static final String AWS_COGNITO_APP_CLIENT_ID = "AWS_COGNITO_APP_CLIENT_ID";
	public static final String AWS_COGNITO_USER_NAME = "AWS_COGNITO_USER_NAME";

	public static String getEnv( String envKey ) {
		String envValue = System.getenv( envKey );
		if( StringUtils.isBlank( envValue ) ) {
			logger.error( "No value is set for the environment variable.　Please set lambda method enbironment : " + envKey );
		}else{
			logger.info(  envKey + ":" + envValue );
		}
		return envValue;
	}

}
