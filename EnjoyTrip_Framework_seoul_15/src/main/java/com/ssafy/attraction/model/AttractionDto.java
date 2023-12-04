package com.ssafy.attraction.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttractionDto {
	
	private String content_id;
	private String content_type_id;
	private String title;
	private String addr1;
	private String addr2;
	private String zipcode;
	private String first_image;
	private String first_image2;
	private String sido_code;
	private String gugun_code;
	private String latitude;
	private String longitude;
	private String overview;
	private String mlevel;
}
