package com.ssafy.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.chat.model.ChatDto;
import com.ssafy.chat.model.service.ChatService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {
	
	private ChatService chatService;
	
	@Autowired
	public ChatController(ChatService chatService) {
		super();
		this.chatService = chatService;
	}

	@GetMapping
	public ResponseEntity<?> getChatting(){
		List<ChatDto> list;
		try {
			list = chatService.getChatting();
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	
	@PostMapping
	public ResponseEntity<?> addChatting(@RequestBody ChatDto chatDto){
		try {
			chatService.addChatting(chatDto);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	
}
