package com.yksc.lambda.util;

import java.util.UUID;

import org.apache.logging.log4j.Logger;

import com.yksc.lambda.log.LoggerFactory;

public class IdGeneraterUtil {
	private static final Logger logger = LoggerFactory.getLogger();

	public static String nextGuid() {
		UUID uuid = UUID.randomUUID();
		String guid = uuid.toString();
		return guid;
	}

	public static void main( String[] args ) {
	}

}
