package com.mindedu.api.define.error;

public class MindException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "No message";

    private final int errCode;
    private String message = "";

    public MindException(int errCode) {
        super(DEFAULT_MESSAGE);
        this.errCode = errCode;
        this.message = DEFAULT_MESSAGE;
    }

    public MindException(int errCode, String message) {
        super(message);
        this.errCode = errCode;
        this.message = message;
    }

    public MindException(MindErrCode mindErrCode) {
        super(mindErrCode.msg());
        this.errCode = mindErrCode.code();
        this.message = mindErrCode.msg();
    }

    public MindException(MindErrCode mindErrCode, String message) {
        this(mindErrCode.code(), message);
    }

    public int getErrCode() {
        return errCode;
    }

    public String getMessage() {
        return message;
    }
}
