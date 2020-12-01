package com.conrad.spring.board.controller;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.conrad.spring.board.model.service.BoardService;
import com.conrad.spring.board.model.vo.Board;
import com.conrad.spring.common.model.vo.PageInfo;
import com.conrad.spring.common.template.Pagination;

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



}







