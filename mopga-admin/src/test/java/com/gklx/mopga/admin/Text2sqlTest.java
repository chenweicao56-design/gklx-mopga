package com.gklx.mopga.admin;


import com.gklx.mopga.admin.module.generate.domain.form.text2sql.Text2sqlQueryForm;
import com.gklx.mopga.admin.module.generate.service.GenerateService;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class Text2sqlTest {

    @Autowired
    private GenerateService generateService;


    @Test
    void contextLoads() throws TemplateException, IOException {
        Text2sqlQueryForm form = new Text2sqlQueryForm();
        form.setDatabaseId(1l);
        String s = generateService.generateSchema(form);
        System.out.println(s);

    }
}
