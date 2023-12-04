package com.ssafy.member.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.attraction.model.AttractionDto;
import com.ssafy.board.model.FileInfoDto;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.ProfileInfoDto;

public interface MemberService {

	int idCheck(String userId) throws Exception;
	MemberDto login(MemberDto memberDto) throws Exception;
	MemberDto userInfo(String userId) throws Exception;
	MemberDto otherUserInfo(String userId) throws Exception;
	void joinMember(MemberDto memberDto) throws Exception;
	void modifyMember(MemberDto memberDto) throws Exception;
	String getPassword(MemberDto memberDto) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleRefreshToken(String userId) throws Exception;
	void registProfile(MemberDto memberDto) throws Exception;
	void updateProfile(ProfileInfoDto profileInfoDto) throws Exception;
	String getProfileIdx(String userId) throws Exception;
	String getProfileFilePath(String profileIdx) throws Exception;
	void deleteProfile(String userId) throws Exception;
	


	
	/* Admin */
	List<MemberDto> listMember(Map<String, Object> map) throws Exception;
	MemberDto getMember(String userId) throws Exception;
	void updateMember(MemberDto memberDto) throws Exception;
	void deleteMember(String userid) throws Exception;
	
	/* Friend Search*/
	List<String> searchFriend(String keyword) throws Exception;
	
	/* Follower Following*/
	List<String> getFollower(String userId) throws Exception;
	List<String> getFollowing(String userId) throws Exception;
	void addFollow(Map<String, String> map) throws Exception;
	void deleteFollow(String followerId, String followingId) throws Exception;
	public String checkFollowing(Map<String, String> map) throws Exception;

	
	
}
