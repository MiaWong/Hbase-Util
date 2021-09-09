package com.xsyx.test.common.dbconnect.mysql;

import lombok.Data;

/**
 * @ClassName : DbInstanceDetail
 * @Description : com.xsyx.test.common.dbconnect.mysql
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/8/31 17:07
 * @Version 1.0
 **/
@Data
public class DbInstanceDetail {
    private Integer envId;

    private String envName;

    private String dbHost;

    private String username;

    private String password;
}