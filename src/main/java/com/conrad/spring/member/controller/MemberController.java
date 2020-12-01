package com.conrad.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.conrad.spring.member.model.service.MemberService;
import com.conrad.spring.member.model.vo.Member;

@Controller //어노테이션을 붙여주면 빈 스캐닝을 통해 자동을 빈 등록
public class MemberController {
	
	@Autowired
	private MemberService mService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	
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
//	@RequestMapping("login.me")
//	public void loginMember(Member m,HttpSession session) {
//		System.out.print("ID:"+m.getUserId());
//		System.out.print("PWD:"+m.getUserPwd());
//		
//		Member loginUser = mService.loginMember(m);
//		
//		if(loginUser != null) {
//			
//		}else {
//			
//		}
//	}
	/*
	 * 5.Model 이라는 객체를 이용하는 방법
	 * 
	 * Model이라는 객체를 이용해서 응답뷰에 전달하고자 하는 
	 * 응답데이터를 맵형식 (key,value)로 담을 수 있다.
	 * scope는 requestScope다 == 응답페이지에서만 꺼내 쓸 수 있다.
	 */
//	@RequestMapping("login.me")
//	public String loginMember(Member m,HttpSession session,Model model) {
//		Member loginUser = mService.loginMember(m);
//		
//		if(loginUser != null) {
//			session.setAttribute("loginUser",loginUser);
//			return "redirect:/";				
//		}else {
//			model.addAttribute("errorMsg","로그인 실패입니다^^");
//			return "common/errorPage";	
//		}
//
//	}
//	
	/*
	 * 6.ModelAndView 이라는 객체를 이용하는 방법
	 *
	 */
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m,HttpSession session, ModelAndView mv) {
		Member loginUser = mService.loginMember(m);
		
		if(loginUser != null && bCryptPasswordEncoder.matches(m.getUserPwd(),loginUser.getUserPwd())) {
			session.setAttribute("loginUser",loginUser);
			mv.setViewName("redirect:/");			
		}else {
			mv.addObject("errorMsg","로그인실패요^^");
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
	@RequestMapping("logout.me")
	public ModelAndView logoutMember(HttpSession session,ModelAndView mv) {
		session.invalidate();
		mv.setViewName("redirect:/");
		return mv;
	}
	@RequestMapping("enrollForm.me")
	public String enrollForm() {
		return "member/memberEnrollForm";
	}

	@RequestMapping("insert.me")
	public String insertMember(Member m, Model model,HttpSession session) {
		//System.out.println(m.getUserPwd());
		m.setUserPwd(bCryptPasswordEncoder.encode(m.getUserPwd()));

		int result = mService.insertMember(m);

		if(result>0){
			session.setAttribute("alertMsg","성공");
			return "redirect:/";
		}else{
			model.addAttribute("errorMsg","회원가입 실패요^^");
			return "common/errorPage";
		}
	}
	@RequestMapping("myPage.me")
	public String myPage() {
		return "/member/myPage";
	}
	@RequestMapping("update.me")
	public String updateMember(Model model,HttpSession session,Member m){
		int result = mService.updateMember(m);

		if(result>0){

			session.setAttribute("loginUser",mService.loginMember(m));
			session.setAttribute("alertMsg","정보변경성공");
			return "main";
		}else{
			model.addAttribute("errorMsg","회원가입 실패요^^");
			return "common/errorPage";
		}
	}
}
