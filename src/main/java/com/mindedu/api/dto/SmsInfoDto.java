package com.mindedu.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SmsInfoDto {
    @JsonProperty(value = "send_number")
    private String sendNumber;
    @JsonProperty(value = "receive_number")
    private String receiveNumber;
    @JsonProperty(value = "message")
    private String message;
    @JsonProperty(value = "reserve_time")
    private String reserveTime;
}
