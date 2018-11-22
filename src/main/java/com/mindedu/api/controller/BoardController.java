package com.mindedu.api.controller;

import com.mindedu.api.config.ConfigHolder;
import com.mindedu.api.service.BoardService;
import com.mindedu.api.util.FileUploadUtil;
import com.mindedu.api.util.JsonBuilder;
import com.mindedu.api.vo.BoardInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    /**
     * 파일 업로드(입력)
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload_file", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody String uploadBoardFile(MultipartHttpServletRequest request) {
        Map<String, Object> resultInfo = FileUploadUtil.boardFileUpload(request, ConfigHolder.getBoardFileUploadPath());
        String title = request.getParameter("title");
        String contents = request.getParameter("contents");
        String userName = request.getParameter("userName");
        String referUrl = request.getParameter("referUrl");
        String fileName = "";
        if (resultInfo != null) {
            fileName = resultInfo.get("file_name").toString();
        }
        boardService.insertBoard(fileName, title, contents, userName, referUrl);
        return new JsonBuilder().add("result", resultInfo).build();
    }

    /**
     * 수정
     * @param request
     * @return
     */
    @RequestMapping(value = "/update_file", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody String updateBoardInfo(MultipartHttpServletRequest request) {
        Map<String, Object> resultInfo = FileUploadUtil.boardFileUpload(request, ConfigHolder.getBoardFileUploadPath());
        String fileName = "";
        if (resultInfo != null) {
            fileName = resultInfo.get("file_name").toString();
        }
        String boardId = request.getParameter("boardId");
        String title = request.getParameter("title");
        String contents = request.getParameter("contents");
        String userName = request.getParameter("userName");
        String referUrl = request.getParameter("referUrl");
        boardService.updateBoard(Long.parseLong(boardId), fileName, title, contents, userName, referUrl);
        return new JsonBuilder().add("result", resultInfo).build();
    }

    /**
     * 파일목록
     * @return
     */
    @RequestMapping(value = "/file_list", method = RequestMethod.GET)
    public ResponseEntity fileList() {
        List<BoardInfoVO>fileList = boardService.getBoardList();
        return new ResponseEntity(fileList, HttpStatus.OK);
    }

    /**
     * 리스트 상세항목 가져오기
     * @param fileId
     * @return
     */
    @RequestMapping(value = "/file_detail/{fileId}", method = RequestMethod.GET)
    public ResponseEntity fileDetailInfo(@PathVariable Long fileId) {
        return new ResponseEntity(boardService.getBoardDetail(fileId), HttpStatus.OK);
    }

    /**
     * 파일 삭제
     * @param fileId
     * @return
     */
    @RequestMapping(value = "/update_file/{fileId}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteFile(@PathVariable Long fileId) {
        boardService.deleteFile(fileId, 1);
        return new JsonBuilder().add("result", HttpStatus.OK).build();
    }
}
