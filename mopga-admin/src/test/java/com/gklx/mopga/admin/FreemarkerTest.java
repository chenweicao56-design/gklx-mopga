package com.gklx.mopga.admin;

import cn.hutool.core.map.MapUtil;
import com.gklx.mopga.admin.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FreemarkerTest {

    @Test
    void contextLoads() throws TemplateException, IOException {

        Map<String, Object> params = MapUtil.<String, Object>builder()
                .build();

        FreemarkerUtil.text("Hello, ${name}!",params);

    }


}
