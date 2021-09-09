package com.xsyx.test.common.dbconnect.mysql;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

/**
 * @ClassName : DatabaseSetting
 * @Description :
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2020/11/16 18:17
 * @Version 1.0
 **/
// TODO V20210901 仅支持mysql
public class DBConnection {
    private static final Logger logger = LoggerFactory.getLogger(DBConnection.class.getName());
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://";
    private static final String JDBC_OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public static Connection createDataConnection(String dbServer, String userName, String password)
            throws ClassNotFoundException, SQLException {
        // 注册 JDBC 驱动
        Class.forName(JDBC_DRIVER);
        logger.debug("开始连接数据库...");

        final StringBuffer connectionStringBuffer = new StringBuffer(JDBC_URL);
        connectionStringBuffer.append(dbServer).append(JDBC_OPTION);
        return DriverManager.getConnection(connectionStringBuffer.toString(), userName, password);
    }

    // 根据dbSourceId获取，数据库连接信息
    // 如果没有取到则返回null TODO Mia 用户自定义exception
    public static Connection getDataConByDBSourceId(String dbSourceId) throws SQLException, ClassNotFoundException {
        // TODO Mia 数据结构出来后需要替换为建筑师内的表与结构
        Connection dbSourceSrc = createDataConnection("172.16.10.114:3306", "api_prod", "XsyX@2019_prod");

        String getDBConInfoSql = "select dbinstanceDetail from frxs_pocket_ift.t_dbsource_instance where dbSourceId = " +dbSourceId;

        Statement stmt = dbSourceSrc.createStatement();
        ResultSet rs = stmt.executeQuery(getDBConInfoSql);
        while (rs.next()) {
            String dbinstanceDetail = rs.getString(1);

            if(dbinstanceDetail !=null && StringUtils.isNotEmpty(dbinstanceDetail))
            {
                List<DbInstanceDetail> dbSourceDetails = JSON.parseArray(dbinstanceDetail, DbInstanceDetail.class);

                // 处理默认端口问题
                String hostStr = dbSourceDetails.get(0).getDbHost();
                if(!hostStr.contains(":"))
                {
                    hostStr = hostStr + ":3306";
                }

                return createDataConnection(hostStr,  dbSourceDetails.get(0).getUsername(),
                        dbSourceDetails.get(0).getPassword());
            }
        }
        return null;
    }

    @Test
    // 单元测试，确定getDataConByDBSourceId working
    public void testGetDataConByDBSourceId() throws SQLException, ClassNotFoundException {
        Connection con  = getDataConByDBSourceId("5");

        String sqlTemp = "select count(*) from xsyx_trade_area_0.t_trade_order_area_0 ";

        int i = DBExecUtils.exeCountSql(con, sqlTemp);
        System.out.println(i);
    }
}