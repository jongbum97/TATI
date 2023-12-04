package com.ssafy.attraction.model.service;

import java.util.List;

import com.ssafy.attraction.model.AttractionDto;
import com.ssafy.attraction.model.AttractionLikeDto;

public interface AttractionService {

	List<AttractionDto> getAttractions() throws Exception;
	AttractionDto getDetail(int id) throws Exception;
	List<AttractionDto> getRank() throws Exception;
	AttractionDto getRandom() throws Exception;
	void likeAttraction(AttractionLikeDto attractionlikeDto)  throws Exception;
	void likeCancelAttraction(String userId, String contentId )  throws Exception;
	void updateAttractionLike(String contentId) throws Exception;
	void updateAttractionLikeCancel(String contentId) throws Exception;
	List<String> getLikeList(String userId) throws Exception;

}
