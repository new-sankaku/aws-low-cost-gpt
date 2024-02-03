package com.yksc.dummy.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yksc.dummy.model.ChatMessage;
import com.yksc.dummy.model.ChatRoom;
import com.yksc.dummy.model.ChatRoomHistory;
import com.yksc.dummy.model.User;
import com.yksc.dummy.util.IdGeneraterUtil;

public class Data {

	public static int dummyChatRoomCount = 0;

	public static final List<ChatRoom> chatRoomList = new ArrayList<ChatRoom>();
	public static final Map<String, ChatRoomHistory> chatRoomHistoryMap = new HashMap<String, ChatRoomHistory>();
	public static final Map<String, User> usersMap = new HashMap<>();

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
		ChatRoomHistory chatRoomHistory = Data.chatRoomHistoryMap.get( roomId );
		if( chatRoomHistory != null ) {
			List<ChatMessage> chatMessageList = chatRoomHistory.getChatMessages();
			return chatMessageList;

		} else {
			return new ArrayList<ChatMessage>();
		}
	}

	static {
		User user1 = createUser( "user1", "John", "Doe", "john@example.com", "1234567890", "US", "123 Street",
				"plan1" );
		User user2 = createUser( "user3", "Alice", "Johnson", "alice@example.com", "5555555555", "UK", "789 Road",
				"plan3" );
		User testUser = createUser( "testUser", "Jane", "Smith", "sakurasakusankaku@gmail.com", "09012345678", "JA",
				"456 Avenue", "plan2" );
		addUser( user1 );
		addUser( user2 );
		addUser( testUser );

		addDummyChatRoom( testUser.getUserId() );
		addDummyChatRoom( testUser.getUserId() );
		addDummyChatRoom( testUser.getUserId() );
	}

	private static void addUser( User user ) {
		usersMap.put( user.getUserId(), user );
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

		ChatMessage chatMessage1 = new ChatMessage();
		chatMessage1.setMessageId( "msg " + (++dummyChatRoomCount) );
		chatMessage1.setSender( "user" );
		chatMessage1.setRecipient( "open ai chat gpt3.5" );
		chatMessage1.setMessageBody( "Hello! this is dummy " + simple.format( Calendar.getInstance().getTime() ) );
		Calendar cal = Calendar.getInstance();
		cal.add( Calendar.HOUR, (1 * 24) );
		chatMessage1.setSendDate( cal.getTime() );
		chatMessage1.setMessageType( "text" );

		ChatMessage chatMessage2 = new ChatMessage();
		chatMessage2.setMessageId( "msg " + (++dummyChatRoomCount) );
		chatMessage2.setSender( "ai" );
		chatMessage1.setRecipient( "open ai chat gpt3.5" );
		chatMessage2.setMessageBody( "Hi!  this is dummy " + simple.format( Calendar.getInstance().getTime() ) );
		chatMessage2.setSendDate( new Date() );
		chatMessage2.setMessageType( "text" );

		ChatRoomHistory chatRoomHistory = new ChatRoomHistory();
		chatRoomHistory.setRoomId( guid );
		chatRoomHistory.setChatMessages( Arrays.asList( chatMessage1, chatMessage2 ) );
		chatRoomHistoryMap.put( guid, chatRoomHistory );

		chatRoomList.add( chatRoom );
	}

}