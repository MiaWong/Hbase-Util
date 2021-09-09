package com.xsyx.test.hbase.hbaseUtil;

/**
 * @ClassName : HBaseConstant
 * @Description : com.xsyx.test.hbase
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/8/17 15:16
 * @Version 1.0
 **/
public interface HBaseConstant {
    String HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
    String HBASE_CLIENT_USERNAME = "hbase.client.username";
    String HBASE_CLIENT_PASSWORD = "hbase.client.password";
    String HBASE_COLUMN_FAMILY_ONE = "f1";
    String[] HBASE_COLUMN_FAMILIES = {HBASE_COLUMN_FAMILY_ONE};
}
