package com.yksc.dummy.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import com.yksc.dummy.data.Data;
import com.yksc.model.db.ChatMessage;
import com.yksc.model.db.ChatRoom;
import com.yksc.model.db.User;
import com.yksc.model.rest.ChatRequest;
import com.yksc.model.util.IdGeneraterUtil;

@RestController
@RequestMapping("/ChatCompletions")
public class ChatCompletionsController {

	@PostMapping
    public ResponseEntity<Object> generateText(@RequestHeader("user-mail-address") String userMailAddress, @RequestBody ChatRequest chatRequest) {
        List<ChatMessage> chatMessageList = chatRequest.getChatMessageList();
        String selectedModel = chatRequest.getSelectedAiModel();
        
        ChatMessage userChatMessage =  chatMessageList.get(chatMessageList.size()-1);
        userChatMessage.setMessageId(IdGeneraterUtil.nextGuid());

        boolean first = false;
        
        //new Chat
        ChatRoom chatRoom = null;
        if( StringUtils.isBlank(chatRequest.getRoomId()) ) {
    		Optional<User> optionalUser = Data.usersMap.values().stream()
    				.filter( user -> user.getEmail().equals( userMailAddress ) )
    				.findFirst();

    		if( optionalUser.isPresent() ) {
    			first = true;
    			chatRoom = new ChatRoom();
        		String guid = IdGeneraterUtil.nextGuid();
        		chatRoom.setRoomId( guid );
        		chatRoom.setOwnerUserId( optionalUser.get().getUserId() );
        		chatRoom.setRoomTitle( "include server" );
        		chatRoom.setCreateDate( new Date() );
        		chatRoom.setUpdateDate( new Date() );
        		chatRoom.setAiModel( "gpt-3.5-turbo-0125" );
        		chatRoom.setAiModelSource( "Open Ai" );
        		chatRoom.setSumTotal( 0 );
        		
            	Data.chatRoomList.add( chatRoom );
    		}
        }else {
        	String roomId = chatRequest.getRoomId();
        	Optional<ChatRoom> optional = Data.chatRoomList.stream().filter(temp -> StringUtils.equals(roomId, temp.getRoomId())).findFirst();
        	chatRoom = optional.get();
        }
        
        System.out.println("selectedModel:" + selectedModel);
        System.out.println("chatMessageList.size:" + chatMessageList.size());
        
        boolean isRealChatGpt = true;
        if( isRealChatGpt ) {
        	if( first ) {
        		chatRoom.setRoomTitle( thumbnailText( userChatMessage.getMessage() ) );
        	}
        	
        	long start = Calendar.getInstance().getTimeInMillis();
        	String result = realChatGpt(chatMessageList, selectedModel);
        	ChatMessage chatMessage = new ChatMessage( IdGeneraterUtil.nextGuid(), "ai", result, new Date() );
        	long end = Calendar.getInstance().getTimeInMillis();
        	chatMessage.setResponseTime(start - end);
        	
        	System.out.println("new user message id:" + userChatMessage.getMessageId());
        	
        	Data.chatMessgaeMap.put(userChatMessage.getMessageId(), userChatMessage);
        	chatRoom.getChatMessageIds().add(userChatMessage.getMessageId());

        	Data.chatMessgaeMap.put(chatMessage.getMessageId(), chatMessage);
        	chatRoom.getChatMessageIds().add(chatMessage.getMessageId());
        	
        	chatMessage.setRoomTitle(chatRoom.getRoomTitle());
        	chatMessage.setRoomId(chatRoom.getRoomId());
        	
        	return ResponseEntity.ok( chatMessage  );
    	}else {
    		long start = Calendar.getInstance().getTimeInMillis();

    		SimpleDateFormat simple = new SimpleDateFormat( "yyyy/MM/dd hh:mm:ss.SSS" );
    		String date = simple.format( Calendar.getInstance().getTime() );
    		String result = "This is a pretend generated message. " + date + " (><;)";
    		
    		String guid = IdGeneraterUtil.nextGuid();
    		Date sendDate = Calendar.getInstance().getTime();
    		ChatMessage chatMessage = new ChatMessage( guid, "ai", result, sendDate );

    		try {
    			//Pretending to work
    			Thread.sleep( 3000L );
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
        	long end = Calendar.getInstance().getTimeInMillis();
        	chatMessage.setResponseTime(start - end);
        	
        	Data.chatMessgaeMap.put(userChatMessage.getMessageId(), userChatMessage);
        	chatRoom.getChatMessageIds().add(userChatMessage.getMessageId());
        	
        	Data.chatMessgaeMap.put(chatMessage.getMessageId(), chatMessage);
        	chatRoom.getChatMessageIds().add(chatMessage.getMessageId());

        	
        	chatMessage.setRoomTitle(chatRoom.getRoomTitle());
        	chatMessage.setRoomId(chatRoom.getRoomId());

    		return ResponseEntity.ok( chatMessage  );
        }
        
		
	}
	
	private String thumbnailText( String aiMessage  ) {
		
		aiMessage = "Please summarize in 20 characters or less. No explanation needed. Base your summary on the language of the message." + 
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
		String apiKey = System.getenv( "OPEN_AI_API_KEY" );

		OpenAiService service = new OpenAiService(apiKey);
		List<com.theokanning.openai.completion.chat.ChatMessage> messages = new ArrayList<>();

		for (ChatMessage chatMessage : chatMessageList) {
			String message = chatMessage.getMessage();
			String sender = chatMessage.getSender();
			
			if( StringUtils.equals(sender, ChatMessage.SENDER_AI) ) {
				messages.add(
						new com.theokanning.openai.completion.chat.ChatMessage(ChatMessageRole.ASSISTANT.value(), message));
			}else if( StringUtils.equals(sender, ChatMessage.SENDER_USER) ) {
				messages.add(
						new com.theokanning.openai.completion.chat.ChatMessage(ChatMessageRole.USER.value(), message));
			}
		}

		System.out.println("selectedModel:" + selectedModel);
		
		ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder().model(selectedModel)
				.messages(messages).build();
//		.maxTokens(16 * 1000)
		
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
