package com.ssafy.member.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.FileInfoDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.service.MemberService;
import com.ssafy.util.JWTUtil;
import com.ssafy.member.model.ProfileInfoDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
//@Controller
@RequestMapping("/user")
@Api(tags = { "MemberController" })
@Slf4j
public class MemberController {

//	private final Logger logger = LoggerFactory.getLogger(MemberController.class);
	@Value("${file.path}")
	private String uploadPath;
	
	private MemberService memberService;
	
	private JWTUtil jwtUtil;
	

	public MemberController(MemberService memberService, JWTUtil jwtUtil) {
		super();
		this.memberService = memberService;
		
		this.jwtUtil = jwtUtil;
	}
	
	@ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 이용하여 로그인 처리.")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
		log.debug("login user : {}", memberDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			MemberDto loginUser = memberService.login(memberDto);
			if(loginUser != null) {
				String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
				String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId());
				log.debug("access token : {}", accessToken);
				log.debug("refresh token : {}", refreshToken);
				
//				발급받은 refresh token을 DB에 저장.
				memberService.saveRefreshToken(loginUser.getUserId(), refreshToken);
				
//				JSON으로 token 전달.
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				
				status = HttpStatus.CREATED;
			} else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
				status = HttpStatus.UNAUTHORIZED;
			} 
			
		} catch (Exception e) {
			log.debug("로그인 에러 발생 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
//		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			log.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userId);
				resultMap.put("userInfo", memberDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/otheruserinfo/{userId}")
	public ResponseEntity<Map<String, Object>> getOtherUserInfo(
			@PathVariable("userId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
//		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.otherUserInfo(userId);
				resultMap.put("userInfo", memberDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> removeToken(@PathVariable ("userId") @ApiParam(value = "로그아웃할 회원의 아이디.", required = true) String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleRefreshToken(userId);
			status = HttpStatus.OK;
		} catch (Exception e) {
			log.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		log.debug("token : {}, memberDto : {}", token, memberDto);
		if (jwtUtil.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(memberDto.getUserId()))) {
				String accessToken = jwtUtil.createAccessToken(memberDto.getUserId());
				log.debug("token : {}", accessToken);
				log.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			log.debug("리프레쉬토큰도 사용불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "idCheck", notes = "아이디확인.")
	@GetMapping("/{userid}")
	public String idCheck(@PathVariable("userid") String userId) throws Exception {
		log.debug("idCheck userid : {}", userId);
		int cnt = memberService.idCheck(userId);
		return cnt + "";
	}

	@ApiOperation(value = "회원가입", notes = "회원의 정보를 받아 처리.")
	@PostMapping("/regist")
	public ResponseEntity<?> regist(@RequestBody MemberDto memberDto) {
		log.debug("memberDto : {}", memberDto);
		try {
			memberService.joinMember(memberDto);
			log.debug("회원가입 성공");
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	
	@ApiOperation(value = "비밀번호조회", notes = "회원의 정보를 받아 비밀번호조회.")
	@PostMapping("/findpassword")
	public ResponseEntity<?> findPassword(@RequestBody MemberDto memberDto) {
		log.debug("memberDto : {}", memberDto);
		try {
			String find = memberService.getPassword(memberDto);
			log.debug("find {}",find);
			if (find!=null)
				return new ResponseEntity<String>(find, HttpStatus.OK);
			return new ResponseEntity<String>(find, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	
	@ApiOperation(value = "프로필인덱스 조회", notes = "프로필인덱스 조회.")
	@GetMapping("/profile/{userId}")
	public String getProfileIdx(@PathVariable String userId) throws Exception {
		log.debug("profile idx : {}", userId);
		return memberService.getProfileIdx(userId);
		
	}
	
	@ApiOperation(value = "프로필파일경로 조회", notes = "프로필파일경로 조회.")
	@GetMapping("/profilefilepath/{fileIdx}")
	public String getProfileFilePath(@PathVariable String fileIdx) throws Exception {
		log.debug("profilefileIdx : {}", fileIdx);
		return memberService.getProfileFilePath(fileIdx);
		
	}
	
	
	@ApiOperation(value = "회원정보수정", notes = "회원의 정보를 수정.")
	@PutMapping("/modify")
	public ResponseEntity<?> modify(MemberDto memberDto, @RequestParam(value = "files", required = false) MultipartFile[] files) {
		log.debug("memberDto : {}", memberDto);
		ProfileInfoDto profileInfoDto = new ProfileInfoDto();

//		FileUpload 관련 설정.
//		log.debug("MultipartFile.isEmpty : {}", files[0].isEmpty());
		if (files!=null && !files[0].isEmpty()) {
			String today = new SimpleDateFormat("yyMMddhhmmss").format(new Date());
			String saveFolder = uploadPath + File.separator + today;
			log.debug("저장 폴더 : {}", saveFolder);
			File folder = new File(saveFolder);
			if (!folder.exists()) {
				log.debug("no exists");
				folder.mkdirs();
			}
			
			List<FileInfoDto> fileInfos = new ArrayList<FileInfoDto>();
			for (MultipartFile mfile : files) {
				FileInfoDto fileInfoDto = new FileInfoDto();
				String originalFileName = mfile.getOriginalFilename();
				if (!originalFileName.isEmpty()) {
//					fileInfoDto.setSaveFolder(today);
					profileInfoDto.setSaveFolder(today);
//					fileInfoDto.setOriginalFile(originalFileName);
					profileInfoDto.setOriginalFile(originalFileName);
//					fileInfoDto.setSaveFile(originalFileName);
					profileInfoDto.setSaveFile(originalFileName);
					log.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), originalFileName);
					try {
						mfile.transferTo(new File(folder, originalFileName));
					} catch (IOException e) {
						return exceptionHandling(e);
					}
				}
			}
			profileInfoDto.setUserId(memberDto.getUserId());
		}
		
		try {
			memberService.updateProfile(profileInfoDto);
			memberService.modifyMember(memberDto);
			log.debug("회원정보수정 성공");
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	
	
	@ApiOperation(value = "회원 삭제", notes = "회원 삭제.")
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable String userId) throws Exception {

		log.debug("회원 탈퇴 {} {}", userId);
		memberService.deleteMember(userId);
		return ResponseEntity.ok().build();
	}
	

//	@ApiOperation(value = "로그인", notes = "회원의 ID, PWD를 받아 로그인.")
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody MemberDto memberDto, HttpSession session, HttpServletResponse response) throws Exception {
//			System.out.println("login gogogogo");
//			Map<String, String> map = new HashMap<>();
//			map.put("userid", memberDto.getUserId());
//			map.put("userpwd", memberDto.getUserPwd());
//			log.debug("login map : {}", map);
//			MemberDto login = memberService.loginMember(map);
//			if (login!= null) {
//				session.setAttribute("userinfo", memberDto);
//				
//				return new ResponseEntity<MemberDto>(login, HttpStatus.OK);
//			} else {
//				 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호 확인 후 다시 로그인하세요!");
//		}
//	}
//	@ApiOperation(value = "로그아웃", notes = "로그아웃.")
//	@GetMapping("/logout")
//	public ResponseEntity<?> logout(HttpSession session) throws Exception{
//		session.invalidate();
//		return ResponseEntity.ok("Logout successful");
//	}
//
	@ApiOperation(value = "회원목록", notes = "회원의 <big>전체 목록</big>을 반환해 줍니다.")
	@GetMapping("/list")
	public ResponseEntity<?> list() throws Exception {
		log.debug("userlist call");
		List<MemberDto> list = memberService.listMember(null);
		if(list != null && !list.isEmpty()) {
			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "친구찾기", notes = "친구 검색 결과를 반환해줍니다.")
	@GetMapping("/searchfriend/{keyword}")
	public ResponseEntity<?> searchFriend(@PathVariable("keyword") String keyword) throws Exception {
		log.debug("searchfriend call");
		List<String> list = memberService.searchFriend(keyword);
		log.debug("{}",list);
		if(list != null && !list.isEmpty()) {
			return new ResponseEntity<List<String>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "팔로워목록", notes = "회원의 <big>팔로워 목록</big>을 반환해 줍니다.")
	@GetMapping("/followerlist/{userid}")
	public ResponseEntity<?> followerlist(@PathVariable("userid") String userId) throws Exception {
		log.debug("followerlist call");
		List<String> list = memberService.getFollower(userId);
		if(list != null && !list.isEmpty()) {
			return new ResponseEntity<List<String>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "팔로워확인", notes = "팔로우 중인지 확인")
	@PostMapping("/checkfollowing")
	public ResponseEntity<?> checkFollowing(@RequestBody Map<String, String> map) throws Exception {
		log.debug("follow확인 {}", map);
		String find = memberService.checkFollowing(map);
		log.debug("check{}",find);
		if(find != null) {
			return new ResponseEntity<String>(find, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "팔로잉목록", notes = "회원의 <big>팔로잉 목록</big>을 반환해 줍니다.")
	@GetMapping("/followinglist/{userid}")
	public ResponseEntity<?> followinglist(@PathVariable("userid") String userId) throws Exception {
		log.debug("followinglist call");
		List<String> list = memberService.getFollowing(userId);
		if(list != null && !list.isEmpty()) {
			return new ResponseEntity<List<String>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "팔로우 추가", notes = "회원의 <big>팔로워, 팔로잉 추가</big>해 줍니다.")
	@PutMapping("/addfollow")
	public ResponseEntity<?> follow(@RequestBody Map<String, String> map) throws Exception {
		log.debug("follow추가 {}", map);
		memberService.addFollow(map);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "팔로우 삭제", notes = "회원의 <big>팔로워, 팔로잉 삭제</big>해 줍니다.")
	@DeleteMapping("/deletefollow/{followerId}/{followingId}")
	public ResponseEntity<?> articleD(@PathVariable String followerId, @PathVariable String followingId ) throws Exception {
		log.debug("follow삭제");
		memberService.deleteFollow(followerId, followingId);
		return ResponseEntity.ok().build();
	}
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
