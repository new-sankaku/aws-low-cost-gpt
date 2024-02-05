package com.yksc.model.util;

import java.util.UUID;

public class IdGeneraterUtil {

	public static String nextGuid() {
		UUID uuid = UUID.randomUUID();
		String guid = uuid.toString();
		return guid;
	}

	public static void main( String[] args ) {
	}

}
