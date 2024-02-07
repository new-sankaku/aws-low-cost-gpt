package com.yksc.lambda.data.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class DBUtil {
    public static AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder.standard().build();
    public static DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(dynamoDBClient);
}
