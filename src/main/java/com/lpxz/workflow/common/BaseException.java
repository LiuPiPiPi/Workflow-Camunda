package com.lpxz.workflow.common;

/**
 * @author LPxz
 * @date 2023/6/3
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected String message;

    public BaseException() {}

    public BaseException(String message) {
        this.message = message;
    }

    public BaseException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
