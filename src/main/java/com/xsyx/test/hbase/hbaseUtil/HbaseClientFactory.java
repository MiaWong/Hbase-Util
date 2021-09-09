package com.xsyx.test.hbase.hbaseUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

/**
 * @ClassName : HbaseClientFactory
 * @Description : com.xsyx.test.hbase
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/8/17 15:14
 * @Version 1.0
 **/
@Slf4j
public class HbaseClientFactory {
    private HbaseClient hBaseClient;

    private HbaseClientFactory() {
    }

    public static final HbaseClientFactory getInstance() throws RuntimeException {
        return HbaseClientFactory.SingletonHolder.INSTANCE;
    }

    /**
     * 创建初始化HBase连接
     *
     * <p> open时调用，其他地方不要调用了 </p>
     *
     * @return HBaseClient
     */
    public synchronized HbaseClient initHbaseClient(Configuration hbaseConf) {
        if (hBaseClient != null && hBaseClient.getConnection() != null && !hBaseClient.getConnection().isClosed()) {
            return hBaseClient;
        }

        try {
            log.debug("initClient coming....");
            Connection connection = ConnectionFactory.createConnection(hbaseConf);
            this.hBaseClient = new HbaseClient(connection);
        } catch (Exception ex) {
            throw new RuntimeException("init hbase connection fail.", ex);
        }

        // 进程唯一的hbase connection, 统一close
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                this.hBaseClient.close();
            } catch (Exception ignored) {
            }
        }));

        return hBaseClient;
    }

    public HbaseClient getHbaseClient(Configuration hbaseConf) {
        if(hBaseClient != null && hBaseClient.getConnection() != null &&
                !hBaseClient.getConnection().getConfiguration().get(HBaseConstant.HBASE_ZOOKEEPER_QUORUM)
                .equals(hbaseConf.get(HBaseConstant.HBASE_ZOOKEEPER_QUORUM)))
        {
            try {
                hBaseClient.close();
            } catch (Exception e) {
                e.printStackTrace();
                log.warn("");
            }

            return null;
        }

        if (hBaseClient == null || hBaseClient.getConnection() == null || hBaseClient.getConnection().isClosed()) {
            initHbaseClient(hbaseConf);
        }

        return hBaseClient;
    }

    private static class SingletonHolder {

        private static final HbaseClientFactory INSTANCE = new HbaseClientFactory();

        private SingletonHolder() {
        }
    }
}