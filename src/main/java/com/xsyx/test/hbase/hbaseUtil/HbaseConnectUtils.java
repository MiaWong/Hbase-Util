package com.xsyx.test.hbase.hbaseUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

/**
 * @ClassName : HbaseConnectUtils
 * @Description : com.xsyx.test.hbase
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/8/17 15:15
 * @Version 1.0
 **/
@Slf4j
public class HbaseConnectUtils {
    public static Configuration buildHbaseConf(String zookeeperQuorum, String hbaseUserName, String hbasePWD) {
        Configuration conf = HBaseConfiguration.create();
        log.info("conf===" + conf);
        // 公用配置
        // 集群的连接地址(VPC内网地址)在控制台页面的数据库连接界面获得
        conf.set(HBaseConstant.HBASE_ZOOKEEPER_QUORUM, zookeeperQuorum);
        conf.set(HBaseConstant.HBASE_CLIENT_USERNAME, hbaseUserName);
        conf.set(HBaseConstant.HBASE_CLIENT_PASSWORD, hbasePWD);
        return conf;
    }
}