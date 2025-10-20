package com.gklx.mopga.admin.module.generate.jdbc.cache;


/**
 * 连接资源关闭回调接口
 *
 * @author gklx
 * @date 2023/1/9 21:03
 */
public interface CacheCloseable {

    /**
     * 在缓存remove掉此对象前，回调接口对连接对象进行相关资源的释放
     */
    void close();
}
