package com.conrad.spring.board.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.conrad.spring.board.model.vo.Board;
import com.conrad.spring.common.model.vo.PageInfo;

@Repository
public class BoardDao {

    public int selectListCount(SqlSessionTemplate sqlSession) {
        return sqlSession.selectOne("boardMapper.selectListCount");
    }

    public ArrayList<Board> selectBoardList(SqlSessionTemplate sqlSession, PageInfo pi){

        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());

        return (ArrayList)sqlSession.selectList("boardMapper.selectBoardList", null, rowBounds);
    }


    public int insertBoard(SqlSessionTemplate sqlSession,Board b){
        return sqlSession.insert("boardMapper.insertBoard",b);
    }





}
