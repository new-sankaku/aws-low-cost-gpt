package com.yksc.lambda.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yksc.lambda.util.ResponseUtil;
import com.yksc.model.rest.AiModel;
import com.yksc.model.rest.RequestInfo;

public class UsersPlanController {


	/**
	 * 
	 * @param requestInfo
	 * @return Collection<AiModel>
	 * @throws JsonProcessingException 
	 */
	public APIGatewayProxyResponseEvent getUserPlanByUserId( RequestInfo requestInfo ) throws JsonProcessingException {
		
		List<AiModel> aiModels = new ArrayList<AiModel>();
		
		AiModel model1 = new AiModel("1", "gpt-3.5-turbo-0125", "Description for Model One", new Date(), 1, "Category A", "JSON", "XML", 0.0005, 0.0015);
        AiModel model2 = new AiModel("2", "gpt-3.5-turbo-16k-0613", "Description for Model Two", new Date(), 2, "Category B", "XML", "JSON", 0.003, 0.004);
        aiModels.add(model1);
        aiModels.add(model2);
        return ResponseUtil.createResponseByOK(aiModels);
	}

}
