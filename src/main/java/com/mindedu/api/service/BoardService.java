package com.mindedu.api.service;

import com.mindedu.api.define.datasource.DataSource;
import com.mindedu.api.define.datasource.DataSourceType;
import com.mindedu.api.mapper.BoardMapper;
import com.mindedu.api.util.Util;
import com.mindedu.api.vo.BoardInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @DataSource(DataSourceType.MINDEDU_MYSQL)
    public void insertBoard(String fileName, String title, String contents, String userName, String referUrl) {
        boardMapper.insertBoard(fileName, title, contents, userName, referUrl);
    }

    @DataSource(DataSourceType.MINDEDU_MYSQL)
    public void updateBoard(Long boardId, String fileName, String title, String contents, String userName, String referUrl) {
        boardMapper.updateBoard(boardId, fileName, title, contents, userName, referUrl);
    }

    @DataSource(DataSourceType.MINDEDU_MYSQL)
    public List<BoardInfoVO>getBoardList() {
        return boardMapper.getBoardList();
    }

    @DataSource(DataSourceType.MINDEDU_MYSQL)
    public BoardInfoVO getBoardDetail(Long fileId) {
        return boardMapper.selectBoardInfo(fileId);
    }

    @DataSource(DataSourceType.MINDEDU_MYSQL)
    public void deleteFile(Long fileId, int status) {
        boardMapper.updateFile(fileId, status);
    }
}
