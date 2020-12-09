package com.conrad.spring.board.controller;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.conrad.spring.board.model.vo.Reply;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.conrad.spring.board.model.service.BoardService;
import com.conrad.spring.board.model.vo.Board;
import com.conrad.spring.common.model.vo.PageInfo;
import com.conrad.spring.common.template.Pagination;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @RequestMapping("detail.bo")
    public String selectBoard(int bno,Model model){
        int result = bService.increaseCount(bno);

        if(result>0){

            Board b = bService.selectBoard(bno);

            model.addAttribute("b",b);

            return "board/boardDetailView";
        }else{
            model.addAttribute("errorMsg","유효하지 않은 게시글이에요^^");
            return "common/errorPage";
        }
    }

    @RequestMapping("delete.bo")
    public String deleteBoard(int bno,String filename, HttpSession session,Model model){
        int result = bService.deleteBoard(bno);
        if(result > 0){
            //기존파일 삭제
            if(!filename.equals("")){
                new File(session.getServletContext().getRealPath(filename)).delete();
            }
            session.setAttribute("alertMsg","성공적으로 삭제되었습니다.");
            return "redirect:list.bo";
        }else{
            model.addAttribute("errorMsg","게시글삭제실패");
            return "common/errorPage";
        }
    }
    @RequestMapping("updateForm.bo")
    public String updateForm(int bno,Model model){
        model.addAttribute("b",bService.selectBoard(bno));
        return "board/boardUpdateForm";
    }

    @RequestMapping("update.bo")
    public String updateBoard(Board b, MultipartFile reupFile, HttpSession session,Model model){
        if(!reupFile.getOriginalFilename().equals("")){
            //만약 기존의 첨부파일이 있었을 경우 => 삭제
            new File(session.getServletContext().getRealPath(b.getChangeName())).delete();
            //새로 전달된 첨부파일 => 업로드
            String changeName =  saveFile(session,reupFile);
            b.setOriginName(reupFile.getOriginalFilename());
            b.setChangeName("resources/uploads/"+changeName);
        }
        int result = bService.updateBoard(b);
        if(result > 0){
            session.setAttribute("alertMsg","성공적으로 게시글이 수정되었습니다");
            return "redirect:detail.bo?bno="+b.getBoardNo();
        }else{
            model.addAttribute("errorMsg","게시글 수정 실패");
            return "common/errorPage";
        }
    }
    @ResponseBody
    @RequestMapping(value="rlist.bo",produces = "application/json; charset=utf-8")
    public String selectReplyList(int bno){
        ArrayList<Reply> list = bService.selectReplyList(bno);
        return new Gson().toJson(list);
    }
}







