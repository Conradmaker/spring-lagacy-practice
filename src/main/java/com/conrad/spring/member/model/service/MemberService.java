package com.conrad.spring.member.model.service;

import com.conrad.spring.member.model.vo.Member;

public interface MemberService {
	
	//1.로그인
	Member loginMember(Member m);
	
	//2.회원가입
	int insertMember(Member m);
	
	//3.회원정보 수정
	int updateMember(Member m);
	
	//4.회원탈퇴
	int deleteMember(String userId);
	
	//5.아이디중복체크(Ajax)
	int idCheck(String userId);
	
}
