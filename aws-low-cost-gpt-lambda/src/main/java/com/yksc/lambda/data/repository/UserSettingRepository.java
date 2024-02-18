package com.yksc.lambda.data.repository;

import org.apache.logging.log4j.Logger;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.yksc.lambda.log.LoggerFactory;
import com.yksc.model.db.UserSetting;

public class UserSettingRepository {
	private static final Logger logger = LoggerFactory.getLogger();

    public void save(UserSetting userSetting) {
    	DBUtil.dynamoDBMapper.save(userSetting);
    }

    public UserSetting findById(String userId) {
        return DBUtil.dynamoDBMapper.load(UserSetting.class, userId);
    }

    public void delete(UserSetting userSetting) {
    	DBUtil.dynamoDBMapper.delete(userSetting);
    }
    
    public void update(UserSetting userSetting) {
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
                .build();
    	DBUtil.dynamoDBMapper.save(userSetting, config);
    }
}
