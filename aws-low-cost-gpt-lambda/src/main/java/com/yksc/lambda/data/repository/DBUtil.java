package com.yksc.lambda.data.repository;

import org.apache.logging.log4j.Logger;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.yksc.lambda.log.LoggerFactory;

public class DBUtil {
	
	private static final Logger logger = LoggerFactory.getLogger();

	
    public static AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder.standard().build();
    public static DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(dynamoDBClient);
}
