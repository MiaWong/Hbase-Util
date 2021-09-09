package com.xsyx.test.hbase.hbaseUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

/**
 * @ClassName : HbaseClient
 * @Description : com.xsyx.test.hbase
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/8/17 15:07
 * @Version 1.0
 **/
@Slf4j
public class HbaseClient {
    private Connection conn;

    public HbaseClient(Connection conn) {
        this.conn = conn;
    }

    public Table getTable(TableName tableName) throws IOException {
        return conn.getTable(tableName);
    }

    public Result getOneRow(TableName tableName, String rowKey) {
        Table table = null;
        Result rsResult = null;
        try {
            table = conn.getTable(tableName);
            Get get = new Get(rowKey.getBytes());
            rsResult = table.get(get);
        } catch (Exception e) {
            log.error("query hbase exception tableName:{} rowKey:{}", tableName, rowKey, e);
        } finally {
            try {
                if (null != table) {
                    table.close();
                }
            } catch (IOException e) {
                log.error("close hbase exception tableName:{} rowKey:{}", tableName, rowKey, e);
            }
        }
        return rsResult;
    }

    public Connection getConnection() {
        return conn;
    }

    public void close() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }
}