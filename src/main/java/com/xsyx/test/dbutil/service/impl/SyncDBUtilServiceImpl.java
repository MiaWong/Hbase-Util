package com.xsyx.test.dbutil.service.impl;

import com.xsyx.test.common.dbconnect.mysql.DBConnection;
import com.xsyx.test.common.dbconnect.mysql.DBExecUtils;
import com.xsyx.test.dbutil.controller.request.SyncDBRecordRequest;
import com.xsyx.test.dbutil.service.SyncDBUtilService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ClassName : SyncDBUtilServiceImpl
 * @Description : com.xsyx.test.dbutil.service.impl
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/8/31 9:32
 * @Version 1.0
 **/
@Slf4j
@Service("syncDBUtilService")
public class SyncDBUtilServiceImpl implements SyncDBUtilService {
    @Override
    public boolean updateEachRecordToSyncDB(SyncDBRecordRequest request) throws SQLException, ClassNotFoundException {
        boolean isSuccessfullyUpated = false;
        //2. 更新前先检查主键字段是否正确：方法根据主键字段group by，count（*），有大于1的抛异常
        SyncDBUtilServiceImpl service = new SyncDBUtilServiceImpl();

        if (service.isPrimaryKey(request)) {
            isSuccessfullyUpated = updateEachRecordToSync(request);
        }

        return isSuccessfullyUpated;
    }

    /**
     *
     * @param request
     * @return  0 非主键，通过主键查回来的记录数超过1
     *          1 主键，满足主键的特性
     */
    private boolean isPrimaryKey(SyncDBRecordRequest request) throws SQLException, ClassNotFoundException {
        boolean isPrimaryKey = true;

        Connection con = DBConnection.getDataConByDBSourceId(request.getDbSourceId());

        StringBuffer DBTableName = new StringBuffer(request.getDbName());
        if(request.isMultipleDB())
            DBTableName.append(request.getMultipleDBIndexConnect()).append(request.getMultipleDBStartIndex());
        DBTableName.append(".").append(request.getTableName());
        if(request.isMultipleTable())
            DBTableName.append(request.getMultipleTableIndexConnect()).append(request.getMultipleTableStartIndex());

        String countByPrimaryKeySql = "select count(*) from " + DBTableName.toString() + " group by " + request.getPrimaryKeyField();


        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(countByPrimaryKeySql);
        while (rs.next()) {
            int count = rs.getInt(1);
            if (count > 1) {
                isPrimaryKey = false;
                break;
            }
        }

        con.close();
        return isPrimaryKey;
    }

    private boolean updateEachRecordToSync(SyncDBRecordRequest request) throws SQLException, ClassNotFoundException {
        Connection JYSSKConnect = DBConnection.getDataConByDBSourceId(request.getDbSourceId());
        for(int j = request.getMultipleDBStartIndex(); j <= request.getMultipleDBEndIndex(); j++) {
            // 单库
            StringBuffer dbName = new StringBuffer(request.getDbName());
            // 多库
            if (request.isMultipleDB())
                dbName.append(request.getMultipleDBIndexConnect()).append(j);

            for (int i = request.getMultipleTableStartIndex(); i <= request.getMultipleTableEndIndex(); i++) {
                StringBuffer tableName = new StringBuffer(request.getTableName());
                if (request.isMultipleTable())
                    tableName.append(request.getMultipleTableIndexConnect()).append(i);

                String getUpdateRecordSql = "select " + request.getPrimaryKeyField() + ", " + request.getFieldToUpdate() +
                        " from " + dbName.toString() + "." + tableName.toString();
                log.debug("select sql : " + getUpdateRecordSql);

                Statement stmt = JYSSKConnect.createStatement();
                ResultSet rs = stmt.executeQuery(getUpdateRecordSql);
                while (rs.next()) {
                    String primaryKeyValue = rs.getString(1);
                    String fieldToUpdateValue = rs.getString(2);

                    if (fieldToUpdateValue.charAt(fieldToUpdateValue.length() - 1) != '9')
                        fieldToUpdateValue = fieldToUpdateValue.substring(0, fieldToUpdateValue.length() - 1) + "9";
                    else
                        fieldToUpdateValue = fieldToUpdateValue.substring(0, fieldToUpdateValue.length() - 1) + "8";

                    String sqlUpdate = "update " + dbName + "." + tableName + " set " + request.getFieldToUpdate() + " = \'"
                            + fieldToUpdateValue + "\' where " + request.getPrimaryKeyField() + " = \'" + primaryKeyValue + "\'";

                    log.debug("update sql : " + sqlUpdate);

                    DBExecUtils.exeUpdateSql(JYSSKConnect, sqlUpdate);
                }
            }
        }

        JYSSKConnect.close();
        return true;
    }

    @Test
    public void testMultipleDBTableUpdateFunc()
    {
        SyncDBRecordRequest request = new SyncDBRecordRequest("5",
                "xsyx_trade_area", true, 0,9, "_",
                "t_trade_order_area",true, 0,99,"_",
                "tmSmp", "orderId");
        try {
            updateEachRecordToSyncDB(request);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSingleDBTableUpdateFunc()
    {
        SyncDBRecordRequest request = new SyncDBRecordRequest("5", "test", false,
                "rule_engine_script_detail",false, "tmUpdate", "id");
        try {
            updateEachRecordToSyncDB(request);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}