package com.ssafy.member.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.board.model.FileInfoDto;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.ProfileInfoDto;
import com.ssafy.member.model.mapper.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
	
	private MemberMapper memberMapper;

	@Autowired
	public MemberServiceImpl(MemberMapper memberMapper) {
		super();
		this.memberMapper = memberMapper;
	}

	@Override
	public int idCheck(String userId) throws Exception {
//		return sqlSession.getMapper(MemberMapper.class).idCheck(userId);
		return memberMapper.idCheck(userId);
	}

	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		return memberMapper.login(memberDto);
	}
	
	@Override
	public MemberDto userInfo(String userId) throws Exception {
		MemberDto memberDto = memberMapper.userInfo(userId);
		if(memberDto.getFileIdx()!=null) {
			ProfileInfoDto file = memberMapper.getProfileFilePath(memberDto.getFileIdx());
			memberDto.setFilePath("/file/"+file.getSaveFolder()+"/"+file.getOriginalFile());
		}
		return memberDto;
	}
	
	@Override
	public MemberDto otherUserInfo(String userId) throws Exception {
		MemberDto memberDto = memberMapper.otherUserInfo(userId);
		if(memberDto.getFileIdx()!=null) {
			ProfileInfoDto file = memberMapper.getProfileFilePath(memberDto.getFileIdx());
			memberDto.setFilePath("/file/"+file.getSaveFolder()+"/"+file.getOriginalFile());
		}
		return memberDto;
	}
	
	
	

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		memberMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return memberMapper.getRefreshToken(userId);
	}

	@Override
	public void deleRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		memberMapper.deleteRefreshToken(map);
	}
	
	@Override
	public void joinMember(MemberDto memberDto) throws Exception {
//		sqlSession.getMapper(MemberMapper.class).joinMember(memberDto);
		registProfile(memberDto);
		memberMapper.joinMember(memberDto);
	}
	
	@Override
	public String getProfileIdx(String userId) throws Exception{
		return memberMapper.getProfileIdx(userId);
	}
	
	@Override
	public String getProfileFilePath(String profileIdx) throws Exception {
		if (profileIdx != null) {
			ProfileInfoDto file = memberMapper.getProfileFilePath(profileIdx);
			return ("/file/"+file.getSaveFolder()+"/"+file.getOriginalFile());
		}
		return "";
	}

	@Override
	public void deleteProfile(String profileIdx) throws Exception {
		memberMapper.deleteProfile(profileIdx);
		
	}
	
	
	@Override
	public void registProfile(MemberDto memberDto) throws Exception {
		// TODO Auto-generated method stub
		memberMapper.registProfile(memberDto);
	}
	public void updateProfile(ProfileInfoDto profileInfoDto) throws Exception{
		memberMapper.updateProfile(profileInfoDto);
		
	}

	
	@Override
	public void modifyMember(MemberDto memberDto) throws Exception {
		// TODO Auto-generated method stub
		memberMapper.modifyMember(memberDto);
		
	}

	
	/* ADMIN */
	@Override
	public List<MemberDto> listMember(Map<String, Object> map) throws Exception {
		return memberMapper.listMember(map);
	}

	@Override
	public MemberDto getMember(String userId) throws Exception {
		return memberMapper.getMember(userId);
	}

	@Override
	public void updateMember(MemberDto memberDto) throws Exception {
		memberMapper.updateMember(memberDto);
	}

	@Override
	public void deleteMember(String userId) throws Exception {
		memberMapper.deleteMember(userId);
		memberMapper.deleteProfile(userId);
		List<String> followings = memberMapper.getFollowing(userId);
		List<String> followers = memberMapper.getFollowerWithdraw(userId);
		log.debug("deleteMember followings{}",followings);
		for(String followingId : followings) {
			memberMapper.deleteFollow(userId, followingId);	
		}
		log.debug("deleteMember followings{}",followings);
		for(String followerId : followers) {
			memberMapper.deleteFollow(followerId, userId);	
		}
		
	}

	@Override
	public List<String> getFollower(String userId) throws Exception{
		return memberMapper.getFollower(userId);
	}

	@Override
	public List<String> getFollowing(String userId) throws Exception{
		return memberMapper.getFollowing(userId);
	}

	@Override
	public void addFollow(Map<String, String> map) throws Exception{
		log.debug("{}",map);
		memberMapper.addFollow(map);
	}

	@Override
	public void deleteFollow(String followerId, String followingId) throws Exception{
		memberMapper.deleteFollow(followerId, followingId);
	}

	@Override
	public List<String> searchFriend(String keyword) throws Exception{
		return memberMapper.searchFriend(keyword);
		
	}
	
	@Override
	public String checkFollowing(Map<String, String> map) throws Exception{
		return memberMapper.checkFollowing(map);
		
	}

	@Override
	public String getPassword(MemberDto memberDto) throws Exception {
		return memberMapper.getPassword(memberDto);
	}



	

	

	

}
