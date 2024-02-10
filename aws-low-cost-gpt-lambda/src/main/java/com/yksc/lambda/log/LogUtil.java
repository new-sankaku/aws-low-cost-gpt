package com.yksc.lambda.log;

import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class LogUtil {
	public static final ObjectMapper objectMapper;
	private static final Logger logger = LoggerFactory.getLogger();

	static {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}
	
	public static String objectToString( Object object ) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			logger.error(e);
		}
		return "";
	}
	public static byte[] convertToByte( Object object ) {
		try {
			return objectMapper.writeValueAsBytes(object);
		} catch (JsonProcessingException e) {
			logger.error(e);
		}
		return new byte[0];
	}
	
	public static void main(String[] args) {

	}

}
