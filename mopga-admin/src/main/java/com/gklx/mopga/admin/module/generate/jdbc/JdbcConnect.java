package com.gklx.mopga.admin.module.generate.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.gklx.mopga.admin.module.generate.jdbc.cache.CacheCloseable;
import lombok.extern.slf4j.Slf4j;

/**
 * jdbc common connection
 *
 * @author gklx
 * @date 2023/1/1 21:24
 */
@Slf4j
public class JdbcConnect implements CacheCloseable {

    private final DruidDataSource dataSource;

    public JdbcConnect(DruidDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void close() {
        try {
            if (dataSource != null) {
                dataSource.close();
            }
        } catch (Exception e) {
            log.error("close jdbc connect error: {}", e.getMessage());
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    public DruidDataSource getDruidDataSource() {
        return dataSource;
    }
}
