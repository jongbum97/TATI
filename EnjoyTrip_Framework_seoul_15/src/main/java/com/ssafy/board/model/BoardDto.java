package com.ssafy.board.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class BoardDto {
	
	@NonNull private int articleNo;
	@NonNull private int type;
	private String userId;
	private String userName;
	private String subject;
	private String content;
	@NonNull private int hit;
	private String registerTime;
	private List<FileInfoDto> fileInfos;
	private List<CommentDto> comments;

}
