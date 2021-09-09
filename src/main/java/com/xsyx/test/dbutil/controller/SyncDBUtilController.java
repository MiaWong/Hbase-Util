package com.xsyx.test.dbutil.controller;

import com.xsyx.test.common.ErrorCodeEnum;
import com.xsyx.test.common.response.ResultData;
import com.xsyx.test.dbutil.controller.request.SyncDBRecordRequest;
import com.xsyx.test.dbutil.service.SyncDBUtilService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * @ClassName : SyncDBUtilController
 * @Description : com.xsyx.test.dbutil.controller.request
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/8/30 16:57
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("sync")
@Api
public class SyncDBUtilController {
    @Autowired
    private SyncDBUtilService syncDBUtilService;

    @ApiOperation(value="更新每个库每个表的每条记录，从而更新binlog来同步数据")
    @PostMapping("update")
    public ResultData update(@RequestBody SyncDBRecordRequest request)
    {
        try {
            syncDBUtilService.updateEachRecordToSyncDB(request);
            return ResultData.success();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return ResultData.buildError(ErrorCodeEnum.JDBC_EXECUTE_ERROR.getCode(), ErrorCodeEnum.JDBC_EXECUTE_ERROR.getMsg());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return ResultData.buildError(ErrorCodeEnum.JDBC_EXECUTE_ERROR.getCode(), ErrorCodeEnum.JDBC_EXECUTE_ERROR.getMsg());
        }
    }
}