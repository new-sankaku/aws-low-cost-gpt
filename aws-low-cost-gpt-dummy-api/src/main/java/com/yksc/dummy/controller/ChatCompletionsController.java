package com.yksc.dummy.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import com.yksc.model.db.ChatMessage;
import com.yksc.model.db.ChatRequest;
import com.yksc.model.util.IdGeneraterUtil;

@RestController
@RequestMapping("/ChatCompletions")
public class ChatCompletionsController {

	@PostMapping
    public ResponseEntity<Object> generateText(@RequestBody ChatRequest chatRequest) {
        List<ChatMessage> chatMessageList = chatRequest.getChatMessageList();
        String selectedModel = chatRequest.getSelectedModel();
        
        System.out.println("selectedModel:" + selectedModel);
        System.out.println("chatMessageList.size:" + chatMessageList.size());
        
        boolean isRealChatGpt = false;
        if( isRealChatGpt ) {
    		long start = Calendar.getInstance().getTimeInMillis();
        	String result = realChatGpt(chatMessageList, selectedModel);
        	ChatMessage chatMessage = new ChatMessage( IdGeneraterUtil.nextGuid(), "ai", result, new Date() );
        	long end = Calendar.getInstance().getTimeInMillis();
        	chatMessage.setResponseTime(start - end);
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
        	
    		return ResponseEntity.ok( chatMessage  );
        }
        
		
	}
	
	private String realChatGpt(List<ChatMessage> chatMessageList, String selectedModel ) {
		String apiKey = "";


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
