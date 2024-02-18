package com.yksc.lambda.util;

import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yksc.lambda.log.LoggerFactory;

public class JsonUtil {
	public static final ObjectMapper objectMapper;
	private static final Logger logger = LoggerFactory.getLogger();

	static {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}
	
	public static void main(String[] args) {

	}

}
