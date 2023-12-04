package com.ssafy.member.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.FileInfoDto;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.ProfileInfoDto;

@Mapper
public interface MemberMapper {

	int idCheck(String userId) throws SQLException;
	MemberDto login(MemberDto memberDto) throws SQLException;
	MemberDto userInfo(String userId) throws SQLException;
	MemberDto otherUserInfo(String userId) throws SQLException;
	String getPassword(MemberDto memberDto) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userid) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	void joinMember(MemberDto memberDto) throws SQLException;
	void modifyMember(MemberDto memberDto) throws SQLException;
	FileInfoDto getFilePath(String file_idx) throws SQLException;
	ProfileInfoDto getProfileFilePath(String file_idx) throws SQLException;
	String getProfileIdx(String userId) throws SQLException;
	void registProfile(MemberDto memberDto) throws SQLException;
	void updateProfile(ProfileInfoDto profileInfoDto) throws SQLException;
	void deleteProfile(String userId) throws SQLException;
	
	/* Admin */
	List<MemberDto> listMember(Map<String, Object> map) throws SQLException;
	MemberDto getMember(String userId) throws SQLException;
	void updateMember(MemberDto memberDto) throws SQLException;
	void deleteMember(String userId) throws SQLException;
	
	/* Friend Search*/
	List<String> searchFriend(String keyword) throws SQLException;
	
	/* Follower Following*/
	List<String> getFollower(String userId) throws SQLException;
	List<String> getFollowing(String userId) throws SQLException;
	List<String> getFollowerWithdraw(String userId) throws SQLException;
	void addFollow(Map<String, String> map) throws SQLException;
	void deleteFollow(String followerId, String followingId) throws SQLException;
	public String checkFollowing(Map<String, String> map) throws SQLException;

	
}
