package com.mindedu.api.vo;

import lombok.Data;

@Data
public class ApiRequestVO {

    private String body;

    private int httpCode;

    public ApiRequestVO(String body, int httpCode) {
        this.body = body;
        this.httpCode = httpCode;
    }
}
