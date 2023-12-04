package com.ssafy.chat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatDto {
	
	private String id;
	private String name;
	private String content;
	
}
