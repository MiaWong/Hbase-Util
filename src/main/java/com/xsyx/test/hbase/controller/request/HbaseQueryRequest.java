package com.xsyx.test.hbase.controller.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

/**
 * @ClassName : HbaseQueryRequest
 * @Description : com.xsyx.test.hbase.controller.request
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/8/18 10:19
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Hbase查询请求对象", description = "用于Hbase查询")
public class HbaseQueryRequest implements Serializable {
    // hbase config
    @NonNull
    private String hbaseZookeeperQuorum;
    @NonNull
    private String hbaseClientUsername;
    @NonNull
    private String hbaseClientPWD;

    @NonNull
    private String hbaseTableName;

    // 非必填，查询条件
    private String queryCondition;

    public HbaseQueryRequest(@NonNull String hbaseZookeeperQuorum, @NonNull String hbaseClientUsername,
                              @NonNull String hbaseClientPWD, @NonNull String hbaseTableName) {
        this.hbaseZookeeperQuorum = hbaseZookeeperQuorum;
        this.hbaseClientUsername = hbaseClientUsername;
        this.hbaseClientPWD = hbaseClientPWD;
        this.hbaseTableName = hbaseTableName;
    }
}