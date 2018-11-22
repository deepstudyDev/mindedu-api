package com.mindedu.api.vo;

import lombok.Data;

@Data
public class BoardInfoVO {

    private Long idx;

    private String title;

    private String contents;

    private String fileName;

    private String createUserName;

    private String createDate;

    private String referUrl;
}
