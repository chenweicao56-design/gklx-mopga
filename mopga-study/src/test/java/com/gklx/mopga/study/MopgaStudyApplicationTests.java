package com.gklx.mopga.study;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Properties;

@SpringBootTest
class MopgaStudyApplicationTests {

    @Test
    void contextLoads() throws NacosException {

        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "public");
        ConfigService configService = NacosFactory.createConfigService(properties);
        String config = configService.getConfig("manifest.json", "agentspec__enc.61636164656d69632d67656f67726170686572__enc.302e302e31", 3000);
        System.out.println(config);
    }

}
