package com.xsyx.test.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName : ErrorCodeEnum
 * @Description : com.xsyx.test.common
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/9/1 14:33
 * @Version 1.0
 **/
@AllArgsConstructor
public enum ErrorCodeEnum {

    /**
     * 成功
     */
    SUCCESS("200", "ok"),
    /**
     * JDBC接口sql执行错误
     */
    JDBC_EXECUTE_ERROR("E0001","JDBC连接错误或sql执行错误，请检查数据源连接信息。");


    @Getter
    private String code;

    @Getter
    private String msg;
}