package com.mindedu.api.define.error;

public enum MindErrCode {

    NOT_AUTHENTICATE(401, "Not authenticated"),
    INTERNAL_ERROR(500, "Internal server error"),
    DATA_NULL(901, "Data is Null");

    int code;
    String msg;

    MindErrCode(int code, String msg){
        this.msg = msg;
        this.code = code;
    }

    public int code(){
        return this.code;
    }

    public String msg() {
        return this.msg;
    }
}
