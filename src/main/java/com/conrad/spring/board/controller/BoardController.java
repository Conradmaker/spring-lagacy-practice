package com.conrad.spring.board.controller;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.conrad.spring.board.model.service.BoardService;
import com.conrad.spring.board.model.vo.Board;
import com.conrad.spring.common.model.vo.PageInfo;
import com.conrad.spring.common.template.Pagination;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
public class BoardController {
  
    @Autowired
    private BoardService bService;


    @RequestMapping("list.bo")  // list.bo?currentPage=xx   또는   list.bo
    public String selectBoardList(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
                                  Model model) {

        //System.out.println(currentPage);
        int listCount = bService.selectListCount();
        PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
        ArrayList<Board> list = bService.selectBoardList(pi);

        model.addAttribute("pi", pi);
        model.addAttribute("list", list);

        return "board/boardListView";

    }

    @RequestMapping("enrollForm.bo")
    public String enrollForm(){
        return "board/boardEnrollForm";
    }

    @RequestMapping("insert.bo")
    public String insertBoard(Board b, MultipartFile upfile, HttpSession session,Model model){
        //System.out.println(b);
        //System.out.println(upfile.getOriginalFilename());
        //전달된 파일이 있을 경우 =>서버에 업로드 => 원본명 => 저장경로 b에 담기
        if(!upfile.getOriginalFilename().equals("")){
            String changeName = saveFile(session , upfile);

            b.setOriginName(upfile.getOriginalFilename());
            b.setChangeName("resources/uploadFiles/"+changeName);

        }

        int result = bService.insertBoard(b);
        if(result>0){
            session.setAttribute("alertMsg","게시글작성 성공");
            return "redirect:list.bo";
        }else{
            model.addAttribute("errorMsg","작성실패!");
            return "common/errorPage";
        }
    }
    public String saveFile(HttpSession session , MultipartFile upfile){
        //파일 업로드 시킬 폴더의 물리경로 (savePath)
        String savePath = session.getServletContext().getRealPath("/resources/uploads/");
        //어떤이름? 파일명 수정(changeName)
        String originName = upfile.getOriginalFilename(); //flower.png

        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int ranNum = (int)(Math.random()*90000+10000);
        String ext = originName.substring(originName.lastIndexOf("."));

        String changeName =  currentTime + ranNum + ext;

        try {
            upfile.transferTo(new File(savePath+changeName));
        } catch (IllegalStateException|IOException e) {
            e.printStackTrace();
        }

        return changeName;
    }

}







