package com.ssafy.exception;

public class UnAuthorizedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UnAuthorizedException() {
		super("JWTInterceptor에서 비로그인 이동을 막았습니다.");
	}

}
