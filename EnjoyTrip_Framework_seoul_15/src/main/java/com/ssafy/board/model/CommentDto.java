package com.ssafy.board.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDto {
	
	private String idx;
	private String article_no;
	private String user_id;
	private String user_name;
	private String comment;
	
}
