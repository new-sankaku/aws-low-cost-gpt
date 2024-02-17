package com.yksc.lambda.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import com.yksc.lambda.data.repository.ChatMessageRepository;
import com.yksc.lambda.data.repository.ChatRoomRepository;
import com.yksc.lambda.log.LogUtil;
import com.yksc.lambda.log.LoggerFactory;
import com.yksc.lambda.util.EnvironmentUtil;
import com.yksc.lambda.util.ResponseUtil;
import com.yksc.model.db.ChatMessage;
import com.yksc.model.db.ChatRoom;
import com.yksc.model.rest.ChatRequest;
import com.yksc.model.rest.RequestInfo;
import com.yksc.model.util.IdGeneraterUtil;

public class ChatCompletionsController {
	private static final Logger logger = LoggerFactory.getLogger();


    public APIGatewayProxyResponseEvent generateText(RequestInfo requestInfo ) throws JsonProcessingException {
    	
    	String body = requestInfo.getBody();
		ChatRequest chatRequest = LogUtil.objectMapper.readValue(body, ChatRequest.class);
    	
        List<ChatMessage> chatMessageList = chatRequest.getChatMessageList();
        String selectedModel = chatRequest.getSelectedAiModel();
        
        ChatMessage userChatMessage =  chatMessageList.get(chatMessageList.size()-1);
        userChatMessage.setMessageId(IdGeneraterUtil.nextGuid());
        
        logger.info("selectedModel:" + selectedModel);
        logger.info("chatMessageList.size:" + chatMessageList.size());
        
        long start = Calendar.getInstance().getTimeInMillis();
    	String result = realChatGpt(chatMessageList, selectedModel);
    	ChatMessage aiChatMessage = new ChatMessage( IdGeneraterUtil.nextGuid(), "ai", result, new Date() );
    	long end = Calendar.getInstance().getTimeInMillis();
    	aiChatMessage.setResponseTime(start - end);

		ChatRoomRepository chatRoomRepo = new ChatRoomRepository();
		ChatRoom chatRoom = null;
        if( StringUtils.isBlank(chatRequest.getRoomId()) ) {
        	chatRoom = new ChatRoom();
    		chatRoom.setRoomId( IdGeneraterUtil.nextGuid() );
    		chatRoom.setOwnerUserId( requestInfo.getUserUUID() );
    		chatRoom.setRoomTitle( "include server" );
    		chatRoom.setCreateDate( new Date() );
    		chatRoom.setUpdateDate( new Date() );
    		chatRoom.setAiModel( chatRequest.getSelectedAiModel() );
    		chatRoom.setAiModelSource( "Open Ai" );
    		chatRoom.setSumTotal( 0 );
    		chatRoomRepo.save(chatRoom);
        	chatRoom.setRoomTitle( thumbnailText( userChatMessage.getMessage() ) );
        }else {
        	chatRoom = chatRoomRepo.findById(chatRequest.getRoomId(), requestInfo.getUserUUID());
        }
    	chatRoom.getChatMessageIds().add(userChatMessage.getMessageId());
    	chatRoom.getChatMessageIds().add(aiChatMessage.getMessageId());

    	ChatMessageRepository chatMessageRepository = new ChatMessageRepository();
    	chatMessageRepository.save(userChatMessage);
    	chatMessageRepository.save(aiChatMessage);
		
    	chatRoomRepo.update(chatRoom);

    	aiChatMessage.setRoomId( chatRoom.getRoomId() );
    	aiChatMessage.setRoomTitle( chatRoom.getRoomTitle());
        return ResponseUtil.createResponseByOK( aiChatMessage  );
	}
    

	private String thumbnailText( String aiMessage  ) {
		
		aiMessage = "以下のメッセージを10文字以内で要約してください。要約はメッセージの言語に基づいてください。" + 
		"\r\n" + aiMessage;
		
		String apiKey = System.getenv( "OPEN_AI_API_KEY" );
		
		List<com.theokanning.openai.completion.chat.ChatMessage> messages = new ArrayList<>();
		messages.add( new com.theokanning.openai.completion.chat.ChatMessage(ChatMessageRole.USER.value(), aiMessage));
		ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder().model("gpt-3.5-turbo-0125").maxTokens(10)
				.messages(messages).build();
		StringBuilder contentBuilder = new StringBuilder();

		OpenAiService service = new OpenAiService(apiKey);
		service.streamChatCompletion(chatCompletionRequest).doOnError(Throwable::printStackTrace)
				.blockingForEach(chatCompletion -> {
					for (ChatCompletionChoice choice : chatCompletion.getChoices()) {
						com.theokanning.openai.completion.chat.ChatMessage message = choice.getMessage();
						if (message != null && message.getContent() != null) {
							contentBuilder.append(message.getContent());
						}
					}
				});

		return contentBuilder.toString();
	}
	
    

	private String realChatGpt(List<ChatMessage> chatMessageList, String selectedModel ) {
		String apiKey = EnvironmentUtil.getEnv( EnvironmentUtil.OPEN_AI_API_KEY );

		OpenAiService service = new OpenAiService(apiKey);
		List<com.theokanning.openai.completion.chat.ChatMessage> messages = new ArrayList<>();

		for (ChatMessage chatMessage : chatMessageList) {
			String message = chatMessage.getMessage();
			String sender = chatMessage.getSender();
			
			if( StringUtils.equals(sender, ChatMessage.SENDER_AI) ) {
				messages.add( new com.theokanning.openai.completion.chat.ChatMessage(ChatMessageRole.ASSISTANT.value(), message));

			}else if( StringUtils.equals(sender, ChatMessage.SENDER_USER) ) {
				messages.add( new com.theokanning.openai.completion.chat.ChatMessage(ChatMessageRole.USER.value(), message));
			
			}
		}

		logger.info("selectedModel:" + selectedModel);
		ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder().model(selectedModel).messages(messages).maxTokens(4000).build();
		
		StringBuilder contentBuilder = new StringBuilder();
		service.streamChatCompletion(chatCompletionRequest).doOnError(Throwable::printStackTrace)
				.blockingForEach(chatCompletion -> {
					for (ChatCompletionChoice choice : chatCompletion.getChoices()) {
						com.theokanning.openai.completion.chat.ChatMessage message = choice.getMessage();
						if (message != null && message.getContent() != null) {
							contentBuilder.append(message.getContent());
						}
					}
				});

		return contentBuilder.toString();
	}
}
