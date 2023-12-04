package com.ssafy.chat.model.service;

import java.util.List;

import com.ssafy.chat.model.ChatDto;

public interface ChatService {

	List<ChatDto> getChatting() throws Exception;
	void addChatting(ChatDto chatDto) throws Exception;
	
}
