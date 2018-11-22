package com.mindedu.api.service;

import com.mindedu.api.define.error.MindErrCode;
import com.mindedu.api.define.error.MindException;
import com.mindedu.api.dto.SmsInfoDto;
import com.mindedu.api.dto.SmsSendListDto;
import com.mindedu.api.mapper.MessageMapper;
import com.mindedu.api.util.PagingBean;
import com.mindedu.api.vo.SmsSendVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class MessageService extends PagingBean {

    static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    private static final String sendNumber = "15660800";

    @Autowired
    private MessageMapper messageMapper;

    /**
     * SMS발송정보 저장히기
     * @param smsInfoDtoList
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertSmsList(List<SmsInfoDto>smsInfoDtoList) {
        if (smsInfoDtoList.size() == 0) {
            throw new MindException(MindErrCode.DATA_NULL);
        }
        List<SmsSendVO>sendList = new ArrayList<>();
        smsInfoDtoList.forEach(dto -> {
            SmsSendVO smsSendVO = new SmsSendVO(
                sendNumber, dto.getReceiveNumber(), dto.getMessage(), dto.getReserveTime()
            );
            sendList.add(smsSendVO);
        });
        messageMapper.insertSmsSendList(sendList);
    }

    /**
     * SMS발송정보 목록 가져오기
     * @param sPage
     * @param yyyyMM
     * @return
     */
    @Transactional(readOnly = true)
    public List<SmsSendListDto>selectSmsSendList(int sPage, String yyyyMM) {
        if ("".equals(yyyyMM)) return null;
        int start = getPageStartNum(sPage);
        return messageMapper.selectSmsSendList(start, pageListCount, yyyyMM);
    }
}
