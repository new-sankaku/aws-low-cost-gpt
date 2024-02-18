package com.yksc.lambda.log;

import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yksc.lambda.util.JsonUtil;

public class LogUtil {
	private static final Logger logger = LoggerFactory.getLogger();
	
	public static String objectToString( Object object ) {
		try {
			return JsonUtil.objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			logger.error(e);
		}
		return "";
	}
	public static byte[] convertToByte( Object object ) {
		try {
			return JsonUtil.objectMapper.writeValueAsBytes(object);
		} catch (JsonProcessingException e) {
			logger.error(e);
		}
		return new byte[0];
	}
	
	public static void main(String[] args) {

	}

}
