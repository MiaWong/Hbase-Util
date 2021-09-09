package com.xsyx.test.hbase.controller;

import com.xsyx.test.common.response.ResultData;
import com.xsyx.test.hbase.controller.request.HbaseQueryRequest;
import com.xsyx.test.hbase.service.HbaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName : HbaseController
 * @Description : com.xsyx.test.hbase.controller
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/8/18 10:18
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("hbase")
@Api
public class HbaseController {
    @Autowired
    private HbaseService hbaseService;

    @ApiOperation(value="hbase查询工具接口")
    @PostMapping("query")
    public ResultData hbaseQuery(@RequestBody HbaseQueryRequest queryRequest)
    {
        List<String> result = hbaseService.query(queryRequest);
        return ResultData.success(result, result == null? 0L: result.size());
    }
}