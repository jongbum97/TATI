package com.ssafy.attraction.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.attraction.model.AttractionDto;
import com.ssafy.attraction.model.AttractionLikeDto;

@Mapper
public interface AttractionMapper {
	
	List<AttractionDto> getAttractions(int k) throws Exception;
	AttractionDto getDetail(int id) throws Exception;
	List<AttractionDto> getRank() throws Exception;
	List<AttractionDto> getRandom(int k) throws Exception;
	void likeAttraction(AttractionLikeDto attractionlikeDto) throws Exception;
	void likeCancelAttraction(String userId, String contentId) throws Exception;
	void updateAttractionLike(String contentId) throws Exception;
	void updateAttractionLikeCancel(String contentId) throws Exception;
	List<String> getLikeList(String userId) throws Exception;
}
