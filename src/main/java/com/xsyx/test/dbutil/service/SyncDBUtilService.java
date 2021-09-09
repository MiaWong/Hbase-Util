package com.xsyx.test.dbutil.service;

import com.xsyx.test.dbutil.controller.request.SyncDBRecordRequest;

import java.sql.SQLException;

/**
 * @ClassName : SyncDBUtilService
 * @Description : com.xsyx.test.dbutil.service
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/8/30 17:32
 * @Version 1.0
 **/
public interface SyncDBUtilService {
    boolean updateEachRecordToSyncDB(SyncDBRecordRequest request) throws SQLException, ClassNotFoundException;
}
