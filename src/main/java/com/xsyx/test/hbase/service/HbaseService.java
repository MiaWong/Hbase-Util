package com.xsyx.test.hbase.service;

import com.xsyx.test.hbase.controller.request.HbaseQueryRequest;

import java.util.List;

/**
 * @ClassName : HbaseService
 * @Description : com.xsyx.test.hbase.service
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/8/18 16:48
 * @Version 1.0
 **/
public interface HbaseService {
    List<String> query(HbaseQueryRequest queryRequest);
}