package com.conrad.spring.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.conrad.spring.member.model.service.MemberService;
import com.conrad.spring.member.model.vo.Member;

@Controller //어노테이션을 붙여주면 빈 스캐닝을 통해 자동을 빈 등록
public class MemberController {
	
	@Autowired
	private MemberService mService;

	
//	@RequestMapping("login.me") //HandlerMapping으로 등록
//	public void loginMember(HttpServletRequest request) {
//		String userId = request.getParameter("id");
//		String userPwd = request.getParameter("pwd");
//		
//		System.out.print(userId+userPwd);
//	}

	
	
	/*
	 * 2.@RequestParam어노테이션 방식
	 */
//	@RequestMapping("login.me")
//	public void loginMember(@RequestParam(value="id")String userId,
//							@RequestParam("pwd")String userPwd) {
//		System.out.print(userId+userPwd);
//	}
	
	
	/*
	 * 3.가장 편한 방식 name값이랑 같아야함
	 */
//	@RequestMapping("login.me")
//	public void loginMember(String userId,String userPwd) {
//		System.out.print(userId+userPwd);
//	}
	
	/*
	 * 4.바로 객체의 필드에 담는 방법
	 */
	@RequestMapping("login.me")
	public void loginMember(Member m) {
		System.out.print("ID:"+m.getUserId());
		System.out.print("PWD:"+m.getUserPwd());
		
		Member loginUser = mService.loginMember(m);
	}
	@RequestMapping("insert.me")
	public void insertMember() {
		
	}
	
	public void updateMember() {
		
	}
}
