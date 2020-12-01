package com.conrad.spring.board.model.service;

import java.util.ArrayList;

import com.conrad.spring.board.model.dao.BoardDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conrad.spring.board.model.vo.Board;
import com.conrad.spring.common.model.vo.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private SqlSessionTemplate sqlSession;
    @Autowired
    private BoardDao bDao;

    @Override
    public int selectListCount() {
        return bDao.selectListCount(sqlSession);
    }

    @Override
    public ArrayList<Board> selectBoardList(PageInfo pi) {
        return bDao.selectBoardList(sqlSession, pi);
    }

//    @Override
//    public int insertBoard(Board b) {
//        return 0;
//    }
//
//    @Override
//    public int increaseCount(int bno) {
//        return 0;
//    }
//
//    @Override
//    public Board selectBoard(int bno) {
//        return null;
//    }
//
//    @Override
//    public int updateBoard(Board b) {
//        return 0;
//    }
//
//    @Override
//    public int deleteBoard(int bno) {
//        return 0;
//    }

}