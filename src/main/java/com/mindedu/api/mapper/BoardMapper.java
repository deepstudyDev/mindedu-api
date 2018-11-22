package com.mindedu.api.mapper;

import com.mindedu.api.vo.BoardInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardMapper {

    void insertBoard(@Param("fileName")String fileName, @Param("title")String title, @Param("contents")String contents,
                     @Param("userName")String userName, @Param("referUrl")String referUrl);

    void updateBoard(@Param("boardId")Long boardId, @Param("fileName")String fileName, @Param("title")String title,
                     @Param("contents")String contents,@Param("userName")String userName, @Param("referUrl")String referUrl);

    List<BoardInfoVO>getBoardList();

    BoardInfoVO selectBoardInfo(@Param("fileId")Long fileId);

    void updateFile(@Param("fileId")Long fileId, @Param("deleteYn")int deleteYn);
}
