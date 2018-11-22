package com.mindedu.api.controller;

import com.mindedu.api.dto.SmsInfoDto;
import com.mindedu.api.dto.SmsSendListDto;
import com.mindedu.api.service.MessageService;
import com.mindedu.api.util.JsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * <PRE>
     * 1. Comment : SMS문자 발송
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2018. 07 .23
     * </PRE>
     * @param smsInfoList
     * @return
     */
    @RequestMapping(value = "/send_sms", method=RequestMethod.POST)
    public @ResponseBody String immediatelySendSmsList(@RequestBody List<SmsInfoDto>smsInfoList) {
        messageService.insertSmsList(smsInfoList);
        return new JsonBuilder().add("result", "OK").build();
    }

    /**
     * <PRE>
     * 1. Comment : SMS문자 발송 목록 조회
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2018. 07 .23
     * </PRE>
     * @param sPage
     * @param yyyyMM
     * @return
     */
    @RequestMapping(value = "/sms_send_list", method=RequestMethod.GET)
    public ResponseEntity<List<SmsSendListDto>>getSmsSendLog(
            @RequestParam("sPage") int sPage,
            @RequestParam("yyyyMM") String yyyyMM) {
        return new ResponseEntity(messageService.selectSmsSendList(sPage, yyyyMM), HttpStatus.OK);
    }
}
