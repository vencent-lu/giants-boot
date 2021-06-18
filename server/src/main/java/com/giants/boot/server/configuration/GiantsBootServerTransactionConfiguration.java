package com.giants.boot.server.configuration;

import com.giants.boot.common.configuration.GiantsBootCommonProperties;
import com.giants.common.collections.CollectionUtils;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * GiantsBootServerTransactionConfiguration TODO
 * date time: 2021/6/15 19:33
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@Configuration
public class GiantsBootServerTransactionConfiguration {

    @Bean("transactionManager")
    public TransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("txAdvice")
    public TransactionInterceptor transactionInterceptor(TransactionManager transactionManager,
                                                         GiantsBootServerProperties giantsBootServerProperties){
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        //非只读事务
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        //只读事务
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);

        //配置加事务的规则,没有匹配到的方法将不会有事务，这些方法指的是Pointcut匹配到的方法
        Map<String, TransactionAttribute> map = new HashMap<String, TransactionAttribute>();
        GiantsBootServerProperties.DbTransaction dbTransaction = giantsBootServerProperties.getDbTransaction();
        if (dbTransaction != null && CollectionUtils.isNotEmpty(dbTransaction.getReadOnlyTxMethods())) {
            for (String redOnlyMethod : dbTransaction.getReadOnlyTxMethods()) {
                map.put(redOnlyMethod, readOnlyTx);
            }
        } else {
            map.put("*", readOnlyTx);
        }
        if (dbTransaction != null && CollectionUtils.isNotEmpty(dbTransaction.getRequiredTxMethods())) {
            for (String requiredTxMethod : dbTransaction.getRequiredTxMethods()) {
                map.put(requiredTxMethod, requiredTx);
            }
        }
        source.setNameMap(map);

        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(TransactionInterceptor txAdvice,
                                                         GiantsBootCommonProperties giantsBootCommonProperties){
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setAdvice(txAdvice);
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public * " + giantsBootCommonProperties.getBasePackage() + "..service.impl.*Impl.*(..))");
        advisor.setPointcut(pointcut);
        advisor.setOrder(99);
        return advisor;
    }

}
