package com.mindedu.api.dto;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.Date;

@Data
public class SmsSendListDto {

    private int seqNo;

    private String destNo;

    private String callBack;

    private String msgContents;

    private Date msgInstm;

    private Date sendreqTime;

    private Date mobsendTime;

    private Date reqmsgRecvtm;

    private int statusCode;

    private String mobCompany;

    private String titleStr;

    private String msgType;

    private String fileName1;

    private String fileName2;

    private String fileName3;

    private String fileName4;

    private String fileName5;

    private String billId;

    private String appEtc1;

    private String appEtc2;
}
