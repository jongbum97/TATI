package com.ssafy.board.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileInfoDto {

	private String saveFolder;
	private String originalFile;
	private String saveFile;

}
