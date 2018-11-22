package com.mindedu.api.controller.advice;

import com.mindedu.api.define.error.MindErrCode;
import com.mindedu.api.define.error.MindException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <PRE>
 * 1. Comment : 컨트롤러 에러 처리
 * 2. 작성자 : 안지호
 * 3. 작성일 : 2018. 07. 27
 * </PRE>
 */
@ControllerAdvice
public class ExceptionAdvice {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    private final static MindErrCode[] NONE_ERR_STACK = {
            MindErrCode.NOT_AUTHENTICATE,
            MindErrCode.INTERNAL_ERROR
    };

    @ExceptionHandler(MindException.class)
    public void mindException(HttpServletRequest req, HttpServletResponse res, MindException err) throws IOException {
        if (!isNoneErrStack(err)) {
            logger.error("Request URI >> " + req.getRequestURI());
            logger.error(err.getMessage());
        }
        res.sendError(err.getErrCode());
    }

    private boolean isNoneErrStack(MindException err) {
        boolean result = false;
        for(MindErrCode none : NONE_ERR_STACK){
            if(none.code() == err.getErrCode()){
                result = true;
                break;
            }
        }
        return result;
    }
}
