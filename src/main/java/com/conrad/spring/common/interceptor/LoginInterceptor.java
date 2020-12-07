package com.conrad.spring.common.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//public class LoginInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
//        HttpSession session = request.getSession();
//        if(session.getAttribute("loginUser")==null){
//            session.setAttribute("alertMsg","로그인후 이용 가능한 서비스입니다.");
//            response.sendRedirect(request.getContextPath());
//            return false;
//        }else{
//            return true;
//        }
//    }
//}
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request , HttpServletResponse response , Object handler) throws  Exception{
        // 이 메소드는 url요청시 HandlerMapping을 통해서 해당 Controller 구동되기 이전에 낚아채서 실행됨!!
        // 이때 true리턴될 경우 => 기존의 그 요청 흐름대로 Controller 구동시키겠다라는걸 의미
        //     false 리턴될 경우 => 기존의 요청 흐름대로 실행될 Controller를 구동시키지 않겠다라는걸 의미!!

        // 현재 로그인되어있지 않을 경우 => 알람 출력, 메인페이지 요청
        HttpSession session  = request.getSession();
        if(session.getAttribute("loginUser") == null){

            session.setAttribute("alertMsg","로그인 후 이용가능한 서비스입니다");
            response.sendRedirect(request.getContextPath());

            return false;
        }else {
            // 현재 로그인한 사용자만 => 기존의 흐름대로 진행되게끔
            return true;
        }

        // Interceptor 등록해야하고 등록시 어떤 url로 요청시 이 Interceptor거쳐가게할껀지 직접 작성!
        // =>DispatcherServlet 클래스와 관련된 servlet-context.xml 문서에 등록


    }


}