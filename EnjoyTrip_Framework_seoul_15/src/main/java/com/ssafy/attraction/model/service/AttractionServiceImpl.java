package com.ssafy.attraction.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.attraction.model.AttractionDto;
import com.ssafy.attraction.model.AttractionLikeDto;
import com.ssafy.attraction.model.mapper.AttractionMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AttractionServiceImpl implements AttractionService {
	
	private AttractionMapper attractionMapper;
	
	@Autowired
	public AttractionServiceImpl(AttractionMapper attractionMapper) {
		super();
		this.attractionMapper = attractionMapper;
	}



	@Override
	public List<AttractionDto> getAttractions() throws Exception {
		int k = (int) (Math.random()*26000);
		return attractionMapper.getAttractions(k);
	}



	@Override
	public AttractionDto getDetail(int id) throws Exception {
		return attractionMapper.getDetail(id);
	}



	@Override
	public List<AttractionDto> getRank() throws Exception {
		return attractionMapper.getRank();
	}



	@Override
	public AttractionDto getRandom() throws Exception {
		int k = (int) (Math.random()*26000);
		return attractionMapper.getRandom(k).get(0);
	}
	public void likeAttraction(AttractionLikeDto attractionLikeDto) throws Exception {
		attractionMapper.likeAttraction(attractionLikeDto);
		
	}
	@Override
	public void likeCancelAttraction(String userId, String contentId) throws Exception {
		attractionMapper.likeCancelAttraction(userId, contentId);
		
	}



	@Override
	public List<String> getLikeList(String userId) throws Exception {
		return attractionMapper.getLikeList(userId);
	}



	@Override
	public void updateAttractionLike(String contentId) throws Exception {
		attractionMapper.updateAttractionLike(contentId);
	}
	
	@Override
	public void updateAttractionLikeCancel(String contentId) throws Exception {
		attractionMapper.updateAttractionLikeCancel(contentId);
	}

}
