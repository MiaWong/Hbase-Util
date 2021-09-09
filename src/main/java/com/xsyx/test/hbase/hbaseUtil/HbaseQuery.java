package com.xsyx.test.hbase.hbaseUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : HbaseQuery
 * @Description : com.xsyx.test.hbase
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/8/17 14:40
 * @Version 1.0
 **/
@Slf4j
public class HbaseQuery {
    @Test
    public void testHbaseQueryWithoutCondition() throws IOException {
        HbaseQuery query = new HbaseQuery();

        query.regexScan("ld-wz9859fs24l4pa1uh-proxy-hbaseue.hbaseue.rds.aliyuncs.com",
                "root", "hbase-fat-build123", "uat_t_store_barrier",
                null);
    }

    @Test
    public void testHbaseQuery() throws IOException {
        HbaseQuery query = new HbaseQuery();

        query.regexScan("ld-wz9859fs24l4pa1uh-proxy-hbaseue.hbaseue.rds.aliyuncs.com",
                "root", "hbase-fat-build123", "uat_t_store_barrier",
                "101-20210814");
    }

    @Test
    public void testHbaseQueryWithAllCondition() throws IOException {
        HbaseQuery query = new HbaseQuery();

        query.regexScan("ld-wz9859fs24l4pa1uh-proxy-hbaseue.hbaseue.rds.aliyuncs.com",
                "root", "hbase-fat-build123", "uat_t_store_barrier",
                "101-20210814");
    }

    public List<String> regexScan(String hbaseZookeeperQuorum, String hbaseClientUsername, String hbaseClientPWD,
                                  String tableName, String queryCondition){
        try {
            Configuration hbaseConf = HbaseConnectUtils.buildHbaseConf(hbaseZookeeperQuorum, hbaseClientUsername, hbaseClientPWD);

            HbaseClient hbaseClient = HbaseClientFactory.getInstance().getHbaseClient(hbaseConf);
            Table queryTableName = hbaseClient.getTable(TableName.valueOf(tableName));

            Scan scan = new Scan();

            /*if (StringUtils.isNotEmpty(queryCondition)) {
                RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL,
                new RegexStringComparator(".*" + queryConditionOnlyShow));
                scan.setFilter(rowFilter);
            }*/
            ResultScanner scanner = queryTableName.getScanner(scan);
            int count = 0;

            List<String> subConditionList = new ArrayList();
         /*   if (StringUtils.isNotEmpty(querySubCondition) && StringUtils.isNotBlank(querySubCondition)) {
                String[] subConditions = querySubCondition.split("#");
                subConditionList.addAll(Arrays.asList(subConditions));
            }*/

            List<String> results = new ArrayList<>();

            for (Result result : scanner) {
                byte[] row = result.getRow();
                byte[] value = result.getValue(Bytes.toBytes("f1"), Bytes.toBytes("val"));

                String rowStr = new String(row);

                if (!subConditionList.isEmpty()) {
                    for (String tempstr : subConditionList) {
                        if (rowStr.contains(new StringBuffer(tempstr).reverse().toString().trim())) {
                            String rowContent = "filter sub content = " + tempstr + ";     rowStr = " + rowStr + "   value = " + new String(value);
                            log.debug(rowContent);
                            results.add(rowContent);
                            count++;
                        }
                    }
                } else {
                    String rowContent = "rowStr = " + rowStr + "   value = " + new String(value);
                    log.debug(rowContent);
                    results.add(rowContent);
                    count++;
                }

            }
            log.debug("hbase result count : " + count);
            return results;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }
}