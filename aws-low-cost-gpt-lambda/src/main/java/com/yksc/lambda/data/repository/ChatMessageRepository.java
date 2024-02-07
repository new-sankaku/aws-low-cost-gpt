package com.yksc.lambda.data.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.yksc.model.db.ChatMessage;

public class ChatMessageRepository {

	private static final String TABLE_NAME = "ChatRoomHistory";

    public void save(ChatMessage chatMessage) {
    	DBUtil.dynamoDBMapper.save(chatMessage);
    }

    public ChatMessage findById(String messageId) {
        return DBUtil.dynamoDBMapper.load(ChatMessage.class, messageId);
    }

    public void delete(ChatMessage chatMessage) {
    	DBUtil.dynamoDBMapper.delete(chatMessage);
    }
    
    public void update(ChatMessage chatMessage) {
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
                .build();
    	DBUtil.dynamoDBMapper.save(chatMessage, config);
    }
}
