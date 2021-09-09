package com.xsyx.test.hbase.service.impl;

import com.xsyx.test.hbase.controller.request.HbaseQueryRequest;
import com.xsyx.test.hbase.hbaseUtil.HbaseQuery;
import com.xsyx.test.hbase.service.HbaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName : HbaseServiceImpl
 * @Description : com.xsyx.test.hbase.service.impl
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/8/18 16:49
 * @Version 1.0
 **/
@Slf4j
@Service("hbaseService")
public class HbaseServiceImpl implements HbaseService {
    @Override
    public List<String> query(HbaseQueryRequest queryRequest) {
        HbaseQuery query = new HbaseQuery();

        return query.regexScan(queryRequest.getHbaseZookeeperQuorum(), queryRequest.getHbaseClientUsername(),
                queryRequest.getHbaseClientPWD(), queryRequest.getHbaseTableName(), queryRequest.getQueryCondition());

    }
}