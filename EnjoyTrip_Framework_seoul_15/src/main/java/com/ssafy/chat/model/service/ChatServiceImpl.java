package com.ssafy.chat.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.chat.model.ChatDto;
import com.ssafy.member.model.service.MemberServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {
	
	private List<ChatDto> chattings = new ArrayList<ChatDto>();
	

	@Override
	public List<ChatDto> getChatting() throws Exception {
		return chattings;
	}

	@Override
	public void addChatting(ChatDto chatDto) throws Exception {
		chattings.add(chatDto);
	}

}
