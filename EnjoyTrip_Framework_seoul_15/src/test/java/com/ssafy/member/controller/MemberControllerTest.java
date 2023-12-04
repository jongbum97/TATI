package com.ssafy.member.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@AutoConfigureMockMvc
@SpringBootTest(
		properties= {
				"spring.config.location=classpath:application.properties"
		}
)      
@Slf4j
@ComponentScan(basePackages= {"com.ssafy"})
@ContextConfiguration(classes = MemberController.class)
class MemberControllerTest {

//	@Value("${userid}")
	private String userId;
//	@Value("${userpwd}")
	private String userPwd;
//	@Value("${usrname}")
	private String userName;
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private MemberService memberService;
	
	@Test
//	@Disabled
	@Transactional
	@DisplayName("#### 회원  등록 테스트####")
	void testJoin() throws Exception {
		log.debug("#### 회원 등록 테스트 시작 ####");
		MemberDto memberDto = new MemberDto();
		memberDto.setUserId("unittest");
		memberDto.setUserName("유닛이야");
		memberDto.setUserPwd("7777");
		memberDto.setEmailId("unittest");
		memberDto.setEmailDomain("google.com");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(memberDto);
		
		mockMvc.perform(post("/user/join").content(content).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().is2xxSuccessful())//isCreated() 도 가능
		.andExpect((content().contentType(MediaType.APPLICATION_JSON)))
		.andDo(print());
		log.debug("#### 회원 등록 테스트 종료 ####");
	}

//	@Test
//	@Disabled
//	void testIdCheck() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	@Disabled
//	void testUserRegister() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	@Disabled
//	void testLogin() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	@Disabled
//	void testLoginMapOfStringStringStringModelHttpSessionHttpServletResponse() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	@Disabled
//	void testLogout() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	@Disabled
//	void testList() {
//		fail("Not yet implemented");
//	}

}
