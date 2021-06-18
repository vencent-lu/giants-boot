package com.giants.boot.server.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * GiantsBootServerProperties TODO
 * date time: 2021/6/15 19:47
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@ConfigurationProperties(prefix = "giants.boot")
public class GiantsBootServerProperties {
    /**
     * 数据库事务配置
     */
    private DbTransaction dbTransaction;

    public DbTransaction getDbTransaction() {
        return dbTransaction;
    }

    public void setDbTransaction(DbTransaction dbTransaction) {
        this.dbTransaction = dbTransaction;
    }

    public static class DbTransaction {
        /**
         * 只读事务方法列表，为空时默认 * 所有方法默认只读
         */
        private List<String> readOnlyTxMethods;
        /**
         * 非只读事务方法列表
         */
        private List<String> requiredTxMethods;

        public List<String> getReadOnlyTxMethods() {
            return readOnlyTxMethods;
        }

        public void setReadOnlyTxMethods(List<String> readOnlyTxMethods) {
            this.readOnlyTxMethods = readOnlyTxMethods;
        }

        public List<String> getRequiredTxMethods() {
            return requiredTxMethods;
        }

        public void setRequiredTxMethods(List<String> requiredTxMethods) {
            this.requiredTxMethods = requiredTxMethods;
        }
    }

}
