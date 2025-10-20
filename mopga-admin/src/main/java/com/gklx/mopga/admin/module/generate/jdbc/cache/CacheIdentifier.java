package com.gklx.mopga.admin.module.generate.jdbc.cache;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

/**
 * 缓存key唯一标识符
 * @author gklx
 * @date 2023/1/1 21:30
 */
@Data
@Builder
public class CacheIdentifier {

    private String ip;

    private String port;

    private String username;

    private String password;

    @Override
    public String toString() {
        return "CacheIdentifier {" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", username+password=>hash='" + Objects.hash(username, password) + '\'' +
                '}';
    }
}
