package com.conrad.spring.board.model.service;

import com.conrad.spring.board.model.vo.Board;
import com.conrad.spring.common.model.vo.PageInfo;

import java.util.ArrayList;

public interface BoardService {
    //1.게시글 리스트 조회용
    int selectListCount();
    ArrayList<Board> selectBoardList(PageInfo pi);
    //2.게시판 작성용 서비스
    int insertBoard(Board b);

    int increaseCount(int bno);

    Board selectBoard(int bno);

    int deleteBoard(int bno);

}
