package com.xsyx.test.common.dbconnect.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ClassName : tk.DBExecUtils
 * @Description : utils
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/4/21 17:43
 * @Version 1.0
 **/
public class DBExecUtils {

    public static void exeUpdateSql(Connection conn, String sql) {
        try {
            Statement stmt = conn.createStatement();
            int rs = stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static int exeCountSql(Connection conn, String sql) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
        return 0;
    }

}