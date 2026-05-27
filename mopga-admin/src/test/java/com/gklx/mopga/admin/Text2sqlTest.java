package com.gklx.mopga.admin;


import cn.hutool.core.map.MapUtil;
import com.gklx.ai.util.FreemarkerUtil;
import com.gklx.mopga.admin.ai.core.DbRule;
import com.gklx.mopga.admin.module.generate.domain.form.text2sql.Text2sqlQueryForm;
import com.gklx.mopga.admin.module.generate.jdbc.JdbcSpiLoader;
import com.gklx.mopga.admin.module.generate.service.GenerateService;
import freemarker.template.TemplateException;
import io.agentscope.core.tool.Toolkit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class Text2sqlTest {

    @Autowired
    private GenerateService generateService;


    @Test
    void contextLoads() throws Exception {
//        Text2sqlQueryForm form = new Text2sqlQueryForm();
//        form.setDatabaseId(1l);
//        String s = generateService.generateSchema(form);
//        System.out.println(s);
        DbRule dbRule = JdbcSpiLoader.RuleDefines.get("3306");

        Toolkit toolkit = new Toolkit();
        Map<String, Object> systemParams = MapUtil.<String, Object>builder()
                .put("engine","3306")
                .put("schema","")
                .put("quotRule",dbRule.getQuotRule())
                .put("limitRule", dbRule.getLimitRule())
                .put("otherRule",dbRule.getOtherRule() )
                .put("basicExample",dbRule.getBasicExample())
                .put("exampleAnswer1",dbRule.getExampleAnswerListWithLimit().get(0))
                .put("exampleAnswer2",dbRule.getExampleAnswerListWithLimit().get(0))
                .put("exampleAnswer3",dbRule.getExampleAnswerListWithLimit().get(0))
                .build();
        String systemPrompt = FreemarkerUtil.render("generate/sql-create-system.md", systemParams);
        System.out.println(systemPrompt);
    }
}
