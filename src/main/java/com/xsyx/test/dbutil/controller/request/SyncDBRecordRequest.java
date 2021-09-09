package com.xsyx.test.dbutil.controller.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

/**
 * @ClassName : UpdateEachRecordRequest
 * @Description : com.xsyx.test.dbutil.controller.request
 * @Author 兴盛优选研发中心 业务测试 王淼
 * @Date 2021/8/30 16:23
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SyncDBRecord请求对象", description = "更新指定数据指定表的每条记录，用于从多库多表binlog同步失败后的补充同步")
public class SyncDBRecordRequest implements Serializable {
    @NonNull
    private String dbSourceId;

    @NonNull
    private String dbName;
    @NonNull
    private boolean isMultipleDB;
    private int multipleDBStartIndex;
    private int multipleDBEndIndex;
    private String multipleDBIndexConnect;

    @NonNull
    private String tableName;
    @NonNull
    private boolean isMultipleTable;
    private int multipleTableStartIndex;
    private int multipleTableEndIndex;
    private String multipleTableIndexConnect;

    @NonNull
    private String fieldToUpdate;

    @NonNull
    private String primaryKeyField;

    public SyncDBRecordRequest(@NonNull String dbSourceId, @NonNull String dbName, @NonNull boolean isMultipleDB,
                                   @NonNull String tableName, @NonNull boolean isMultipleTable,
                                   @NonNull String fieldToUpdate, @NonNull String primaryKeyField) {
        this.dbSourceId = dbSourceId;
        this.dbName = dbName;
        this.isMultipleDB = isMultipleDB;
        this.tableName = tableName;
        this.isMultipleTable = isMultipleTable;
        this.fieldToUpdate = fieldToUpdate;
        this.primaryKeyField = primaryKeyField;
    }
}