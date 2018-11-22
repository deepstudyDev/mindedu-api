package com.mindedu.api.vo;

import com.mindedu.api.util.DateUtils;
import com.mindedu.api.util.StringUtils;
import lombok.Data;

import javax.rmi.CORBA.Util;

@Data
public class SmsSendVO {

    private String sendNumber;

    private String receiveNumber;

    private String message;

    private String msgType;

    private String reserveTime;

    public SmsSendVO() {}

    public SmsSendVO(String sendNumber, String receiveNumber, String message, String reserveTime) {
        this.sendNumber = sendNumber;
        this.receiveNumber = receiveNumber;
        this.message = message;
        this.msgType = StringUtils.getStringByteLength(message) <= 90 ? "S" : "M";
        this.reserveTime = "".equals(reserveTime) ? DateUtils.nowToStrUTC() : reserveTime ;
    }
}
