package com.ssafy.member.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileInfoDto {

	private String userId;
	private String saveFolder;
	private String originalFile;
	private String saveFile;

}
