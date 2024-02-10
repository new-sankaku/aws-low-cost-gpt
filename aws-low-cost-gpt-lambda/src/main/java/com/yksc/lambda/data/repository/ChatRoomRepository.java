package com.yksc.lambda.data.repository;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.yksc.lambda.log.LoggerFactory;
import com.yksc.model.db.ChatRoom;

public class ChatRoomRepository {
	private static final Logger logger = LoggerFactory.getLogger();

	private static final String TABLE_NAME = "ChatRoom";

    public void save(ChatRoom chatRoom) {
    	DBUtil.dynamoDBMapper.save(chatRoom);
    }

    public ChatRoom findById(String roomId, String ownerUserId) {
        return DBUtil.dynamoDBMapper.load(ChatRoom.class, roomId, ownerUserId);
    }
    
    public List<ChatRoom> findByOwnerUserId(String ownerUserId) {
        DynamoDBQueryExpression<ChatRoom> queryExpression = new DynamoDBQueryExpression<ChatRoom>()
                .withIndexName("ownerUserId-index") 
                .withConsistentRead(false)
                .withKeyConditionExpression("ownerUserId = :ownerUserId")
                .withExpressionAttributeValues(Map.of(":ownerUserId", new AttributeValue().withS(ownerUserId)));

        List<ChatRoom> results = DBUtil.dynamoDBMapper.query(ChatRoom.class, queryExpression);
        return results;
    }
    
    public void delete(ChatRoom chatRoom) {
    	DBUtil.dynamoDBMapper.delete(chatRoom);
    }
    
    public void update(ChatRoom chatRoom) {
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
                .build();
    	DBUtil.dynamoDBMapper.save(chatRoom, config);
    }
}
