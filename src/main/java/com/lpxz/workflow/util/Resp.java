package com.lpxz.workflow.util;

import lombok.Data;

/**
 * 请求返回结构体
 *
 * @author LPxz
 */
@Data
public class Resp {

    private int code;

    private String message;

    private Object data;

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    private static final String DEFAULT_ERROR_MESSAGE = "ERROR";

    public Resp setCode(int respCode) {
        this.code = respCode;
        return this;
    }

    public Resp setMessage(String message) {
        this.message = message;
        return this;
    }

    public Resp setData(Object data) {
        this.data = data;
        return this;
    }

    // 只返回状态
    public static Resp success() {
        return new Resp()
                .setCode(ResCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    // 只返回信息
    public static Resp success(String message) {
        return new Resp()
                .setCode(ResCode.SUCCESS)
                .setMessage(message);
    }

    // 只返回数据
    public static Resp success(Object data) {
        return new Resp()
                .setCode(ResCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    // 成功返回数据
    public static Resp success(Object data, String message) {
        return new Resp()
                .setCode(ResCode.SUCCESS)
                .setMessage(message)
                .setData(data);
    }

    // 失败 只返回状态
    public static Resp error() {
        return new Resp()
                .setCode(ResCode.ERROR)
                .setMessage(DEFAULT_ERROR_MESSAGE);
    }

    // 失败 只返回信息
    public static Resp error(String message) {
        return new Resp()
                .setCode(ResCode.ERROR)
                .setMessage(message);
    }

    // 失败 设置状态码
    public static Resp error(int code, String message) {
        return new Resp()
                .setCode(code)
                .setMessage(message);
    }

    private static class ResCode {
        /**
         * 操作成功
         */
        public static final int SUCCESS = 200;

        /**
         * 对象创建成功
         */
        public static final int CREATED = 201;

        /**
         * 请求已经被接受
         */
        public static final int ACCEPTED = 202;

        /**
         * 操作已经执行成功，但是没有返回数据
         */
        public static final int NO_CONTENT = 204;

        /**
         * 资源已被移除
         */
        public static final int MOVED_PERM = 301;

        /**
         * 重定向
         */
        public static final int SEE_OTHER = 303;

        /**
         * 资源没有被修改
         */
        public static final int NOT_MODIFIED = 304;

        /**
         * 参数列表错误（缺少，格式不匹配）
         */
        public static final int BAD_REQUEST = 400;

        /**
         * 未授权
         */
        public static final int UNAUTHORIZED = 401;

        /**
         * 访问受限，授权过期
         */
        public static final int FORBIDDEN = 403;

        /**
         * 资源，服务未找到
         */
        public static final int NOT_FOUND = 404;

        /**
         * 不允许的 http 方法
         */
        public static final int BAD_METHOD = 405;

        /**
         * 资源冲突，或者资源被锁
         */
        public static final int CONFLICT = 409;

        /**
         * 不支持的数据，媒体类型
         */
        public static final int UNSUPPORTED_TYPE = 415;

        /**
         * 系统内部错误
         */
        public static final int ERROR = 500;

        /**
         * 接口未实现
         */
        public static final int NOT_IMPLEMENTED = 501;
    }
}
