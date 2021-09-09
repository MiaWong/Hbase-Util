package com.xsyx.test.common.response;

import com.xsyx.test.common.ErrorCodeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * WebResponse
 *
 * @author xiemao
 * @version ResultData.java, v 1.0.0 2019年09月26日 17:12
 */
@Data
public class ResultData<T> implements Serializable {

    private static final long serialVersionUID = -7200205552995274976L;
    /**
     * 错误码code值
     *
     * @see ErrorCodeEnum
     */
    private String code;

    /**
     * 错误信息message
     */
    private String msg;

    /**
     * 数据data
     */
    private T data;

    /**
     * 分页参数，分页的时候要用到
     */
    private Long total;

    public static <T> ResultData<T> success(T data) {
        return createResponse(ErrorCodeEnum.SUCCESS, data, null);
    }

    public static <T> ResultData<T> success(T data, Long total) {
        return createResponse(ErrorCodeEnum.SUCCESS, data, total);
    }

    public static <T> ResultData<T> success() {
        return createResponse(ErrorCodeEnum.SUCCESS, null, null);
    }


    public static <T> ResultData<T> buildError(String errorCode, String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(errorCode);
        resultData.setMsg(msg);
        return resultData;
    }

    private static <T> ResultData<T> createResponse(ErrorCodeEnum errorCode, T data, Long total) {
        ResultData resultData = new ResultData();
        resultData.setCode(errorCode.getCode());
        resultData.setMsg(errorCode.getMsg());
        resultData.setData(data);
        resultData.setTotal(total);
        return resultData;
    }

    private static <T> ResultData<T> createResponse(ErrorCodeEnum errorCode, T data) {
        ResultData resultData = new ResultData();
        resultData.setCode(errorCode.getCode());
        resultData.setMsg(errorCode.getMsg());
        resultData.setData(data);
        return resultData;
    }

    private static <T> ResultData<T> createResponse(ErrorCodeEnum errorCode) {
        ResultData resultData = new ResultData();
        resultData.setCode(errorCode.getCode());
        resultData.setMsg(errorCode.getMsg());
        return resultData;
    }

    public boolean boolSuccess() {
        return Objects.equals(ErrorCodeEnum.SUCCESS.getCode(), this.getCode());
    }

    public boolean boolFail() {
        return !boolSuccess();
    }

}