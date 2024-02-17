package com.yksc.dummy.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.yksc.model.db.ChatMessage;
import com.yksc.model.db.ChatRoom;
import com.yksc.model.db.User;
import com.yksc.model.db.UserSetting;
import com.yksc.model.rest.AiModel;
import com.yksc.model.util.IdGeneraterUtil;

public class Data {

	public static int dummyChatRoomCount = 0;

	public static final List<ChatRoom> chatRoomList = new ArrayList<ChatRoom>();
	public static final Map<String, ChatMessage> chatMessgaeMap = new HashMap<String, ChatMessage>();
	public static final Map<String, User> usersMap = new HashMap<>();
	public static final Map<String, UserSetting> userSettingMap = new HashMap<>();

	
	
//	public static final Map<String, ArrayList<String>> userPlansMap = new HashMap<>();
	public static final Map<String, AiModel> aiModelMap = new HashMap<>();

	public static List<ChatRoom> getChatRoomList( String userMailAddress ) {
		Optional<User> optionalUser = usersMap.values().stream()
				.filter( user -> user.getEmail().equals( userMailAddress ) )
				.findFirst();

		if( optionalUser.isPresent() ) {
			User user = optionalUser.get();

			for( ChatRoom chatRoom : chatRoomList ) {
				System.out.println( "getChatRoomList OwnerUserId:"
						+ chatRoom.getOwnerUserId() + ", RoomTitle:" + chatRoom.getRoomTitle() );
			}
			List<ChatRoom> chatRooms = Data.chatRoomList.stream()
					.filter( chatRoom -> chatRoom.getOwnerUserId().equals( user.getUserId() ) )
					.collect( Collectors.toList() );

			return chatRooms;
		} else {
			System.out.println("getChatRoomList is notFount user.");
			return new ArrayList<ChatRoom>();
		}
	}

	public static ChatRoom getChatRoom( String roomId ) {
		Optional<ChatRoom> optional = Data.chatRoomList.stream()
				.filter( chatRoom -> chatRoom.getRoomId().equals( roomId ) ).findFirst();
		if( optional.isPresent() ) {
			return optional.get();
		} else {
			return null;
		}
	}

	public static List<ChatMessage> getChatRoomMessage( String roomId ) {
		List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
		
		Optional<ChatRoom> chatRoomOptional = Data.chatRoomList.stream().filter(chatRoom -> StringUtils.equals(chatRoom.getRoomId(), roomId)).findFirst();
		if( chatRoomOptional.isPresent() ) {
			ChatRoom chatRoom = chatRoomOptional.get();
			for (String messageId : chatRoom.getChatMessageIds() ) {
				ChatMessage chatMessgae = chatMessgaeMap.get(messageId);
				chatMessageList.add(chatMessgae);
			}
		}
		
		return chatMessageList;
	}

	static {
		User user1 = createUser( "user1", "John", "Doe", "john@example.com", "1234567890", "US", "123 Street",
				"plan1" );
		User user2 = createUser( "user2", "Alice", "Johnson", "alice@example.com", "5555555555", "UK", "789 Road",
				"plan3" );
		User testUser = createUser( "testUser", "Jane", "Smith", "sakurasakusankaku@gmail.com", "09012345678", "JA",
				"456 Avenue", "plan2" );
		addUser( user1 );
		addUser( user2 );
		addUser( testUser );
		
		addUserSettings( user1 );
		addUserSettings( user2 );
		addUserSettings( testUser );

		addDummyChatRoom( testUser.getUserId() );
		addDummyChatRoom( testUser.getUserId() );
		addDummyChatRoom( testUser.getUserId() );
		
		addAiModel();
	}



	private static void addAiModel() {
        AiModel model1 = new AiModel("1", "gpt-3.5-turbo-0125", "Description for Model 1", new Date(), 1, "Category A", "JSON", "XML", 0.0005, 0.0015);
        AiModel model2 = new AiModel("2", "gpt-3.5-turbo-16k-0613", "Description for Model 2", new Date(), 2, "Category B", "XML", "JSON", 0.003, 0.004);
        AiModel model3 = new AiModel("3", "gpt-4-turbo-preview", "Description for Model 3", new Date(), 2, "Category C", "XML", "JSON", 0.01, 0.03);

		aiModelMap.put("gpt-3.5-turbo-0125", model1);
		aiModelMap.put("gpt-3.5-turbo-16k-0613", model2);
		aiModelMap.put("gpt-4-turbo-preview", model3);
	}
	
	private static void addUser( User user ) {
		usersMap.put( user.getUserId(), user );
	}
	private static void addUserSettings( User user ) {
		
		UserSetting userSettings = new UserSetting();
		// Adding instructions for the beginning of the interaction
		userSettings.getHeadPromptList().add("Please respond in Japanese at all times.");
		userSettings.getHeadPromptList().add("Begin with your answer, followed by the explanation.");
		userSettings.getHeadPromptList().add("I am a beginner; kindly consider this in your responses.");
		userSettings.getHeadPromptList().add("I am an advanced learner; basic explanations are not required.");

		// Adding instructions for the end of the interaction
		userSettings.getTailPromptList().add("Remember to adhere to the initial conditions set forth.");
		userSettings.getTailPromptList().add("If your response is incorrect, please acknowledge the mistake. Misleading statements are not allowed.");
		userSettings.getTailPromptList().add("If unsure about an answer, simply state that you don't know.");
		userSettings.getTailPromptList().add("End of instructions.");


		userSettings.setDarkMode(false);
		userSettings.setUserId(user.getUserId());
		
		userSettingMap.put( user.getUserId(), userSettings );
	}
	
	

	private static User createUser( String accountName, String firstName, String lastName, String email,
			String phoneNumber, String country, String address, String contractPlanId ) {
		User user = new User();
		user.setUserId( IdGeneraterUtil.nextGuid() );
		user.setAccountName( accountName );
		user.setFirstName( firstName );
		user.setLastName( lastName );
		user.setEmail( email );
		user.setPhoneNumber( phoneNumber );
		user.setCountry( country );
		user.setAddress( address );
		user.setContractPlanId( contractPlanId );
		user.setCreatedDate( new Date() );
		user.setUpdatedDate( new Date() );
		user.setLastLoginDate( new Date() );
		return user;
	}

	public static void addDummyChatRoom( String userGuid ) {

		SimpleDateFormat simple = new SimpleDateFormat( "yyyy/MM/dd hh:mm:ss.SSS" );

		ChatRoom chatRoom = new ChatRoom();
		String guid = IdGeneraterUtil.nextGuid();
		chatRoom.setRoomId( guid );
		chatRoom.setOwnerUserId( userGuid );
		chatRoom.setRoomTitle( "DummyRoom " + (++dummyChatRoomCount) );
		chatRoom.setCreateDate( new Date() );
		chatRoom.setUpdateDate( new Date() );
		chatRoom.setAiModel( "gpt-3.5-turbo-0125" );
		chatRoom.setAiModelSource( "Open Ai" );
		
		
		Random rand = new Random(); // Random オブジェクトの生成
        double randDouble = rand.nextDouble( 100 ); //
		chatRoom.setSumTotal( randDouble );

		ChatMessage chatMessage1 = new ChatMessage();
		chatMessage1.setMessageId( IdGeneraterUtil.nextGuid() );
		chatMessage1.setSender( "user" );
		chatMessage1.setMessage( "Hello! this is dummy " + simple.format( Calendar.getInstance().getTime() ) );
		Calendar cal = Calendar.getInstance();
		cal.add( Calendar.HOUR, (1 * 24) );
		chatMessage1.setSendDate( cal.getTime() );
		chatMessgaeMap.put(chatMessage1.getMessageId(), chatMessage1);
		chatRoom.getChatMessageIds().add(chatMessage1.getMessageId());

		ChatMessage chatMessage2 = new ChatMessage();
		chatMessage2.setMessageId( IdGeneraterUtil.nextGuid() );
		chatMessage2.setSender( "ai" );
		chatMessage2.setMessage( "Hi!  this is dummy " + simple.format( Calendar.getInstance().getTime() ) );
		chatMessage2.setSendDate( new Date() );
		chatMessgaeMap.put(chatMessage2.getMessageId(), chatMessage2);
		chatRoom.getChatMessageIds().add(chatMessage2.getMessageId());

		chatRoomList.add( chatRoom );
	}

	public static UserSetting getUserSetting(String userMailAddress) {
		Optional<User> optionalUser = usersMap.values().stream().filter( user -> StringUtils.equals(user.getEmail(), userMailAddress)).findFirst();
		
		if( optionalUser.isPresent() ) {
			User user = optionalUser.get();
			return userSettingMap.get(user.getUserId());
		}else {
			return new UserSetting();
		}
	}
	public static UserSetting updateUserSetting(String userMailAddress, UserSetting userSetting) {
		Optional<User> optionalUser = usersMap.values().stream().filter( user -> StringUtils.equals(user.getEmail(), userMailAddress)).findFirst();
		if( optionalUser.isPresent() ) {
			User user = optionalUser.get();
			userSettingMap.put(user.getUserId(), userSetting);
			return userSetting;
		}else {
			return null;
		}
	}

}