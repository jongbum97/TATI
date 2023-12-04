package com.ssafy.attraction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.attraction.model.AttractionDto;
import com.ssafy.attraction.model.AttractionLikeDto;
import com.ssafy.attraction.model.service.AttractionService;
import com.ssafy.board.model.BoardDto;
import com.ssafy.member.model.MemberDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/atrraction")
@Slf4j
@Api(tags = {"Attraction Controller"})
public class AttractionController {
	
	private AttractionService attractionService;
	
	@Autowired
	public AttractionController(AttractionService attractionService) {
		super();
		this.attractionService = attractionService;
	}

	
	@GetMapping
	public ResponseEntity<?> getAttraction() {
		log.info("getAttraction - 호출 ");
		try {
			List<AttractionDto> attractionDto = attractionService.getAttractions();
			return ResponseEntity.status(HttpStatus.OK).body(attractionDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<?> getAttraction(@PathVariable("id") String id) {
		log.info("getAttractionDetail - 호출 ");
		try {
			AttractionDto attractionDto =  attractionService.getDetail(Integer.parseInt(id));
			return ResponseEntity.status(HttpStatus.OK).body(attractionDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	
	@ApiOperation(value = "관광지 좋아요", notes = "관광지 좋아요 기능.")
	@PostMapping("/like")
	public ResponseEntity<?> likeAttraction(@RequestBody AttractionLikeDto attractionLikeDto) {
		log.debug("likeAttraction : {}", attractionLikeDto);
		try {
			attractionService.likeAttraction(attractionLikeDto);
			log.debug("좋아용 성공");
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	@ApiOperation(value = "관광지 좋아요 취소 ", notes = "관광지 좋아요 취소 기능.")
	@DeleteMapping("/likeCancel/{userId}/{contentId}")
	public ResponseEntity<?> likeCancelAttraction(@PathVariable String userId, @PathVariable String contentId) {
		log.debug("likeCancelAttraction : {} {}", userId, contentId);
		try {
			attractionService.likeCancelAttraction(userId, contentId);
			log.debug("좋아용취소 성공");
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	
	@GetMapping("/random")
	public ResponseEntity<?> getRandom() {
		log.info("getRandom - 호출 ");
		try {
			AttractionDto attractionDto = attractionService.getRandom();
			return ResponseEntity.status(HttpStatus.OK).body(attractionDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	
	@GetMapping("/like/{userId}")
	public ResponseEntity<?> getListLike(@PathVariable("userId") String userId) {
		log.info("getListLike - 호출 ");
		try {
			List<String> likeList =  attractionService.getLikeList(userId);
			return ResponseEntity.status(HttpStatus.OK).body(likeList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	

	
	@GetMapping("/rank")
	public ResponseEntity<?> getRank() {
		log.info("getRank - 호출 ");
		try {
			List<AttractionDto> attractionDtos = attractionService.getRank();
			return ResponseEntity.status(HttpStatus.OK).body(attractionDtos);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	
	@ApiOperation(value = "좋아요 개수 더하기", notes = "좋아요 개수 더하기", response = String.class)
	@PutMapping("like/updatelike/{contentId}")
	public ResponseEntity<String> updateAttractionLike(@PathVariable String contentId) throws Exception {
		try {
			attractionService.updateAttractionLike(contentId);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	
	@ApiOperation(value = "좋아요 개수 빼기", notes = "좋아요 개수 빼기", response = String.class)
	@PutMapping("like/updatelikecancel/{contentId}")
	public ResponseEntity<String> updateAttractionLikeCancel(@PathVariable String contentId) throws Exception {
		try {
			attractionService.updateAttractionLikeCancel(contentId);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
}
