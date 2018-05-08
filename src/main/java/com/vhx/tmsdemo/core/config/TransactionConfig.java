package com.vhx.tmsdemo.core.config;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 基于注解式声明事务
 */
public class TransactionConfig implements TransactionManagementConfigurer {

    @Resource
    private DataSource dataSource;

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return null;
    }
}
