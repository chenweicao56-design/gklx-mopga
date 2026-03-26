package com.gklx.mopga.admin.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class FreemarkerUtil {
    static Configuration cfg = new Configuration(Configuration.VERSION_2_3_33);

    static {
        cfg.setDefaultEncoding("UTF-8");
        cfg.setClassForTemplateLoading(FreemarkerUtil.class, "/freemarker");
    }

    public static String text(String path, Map<String, Object> map) throws IOException, TemplateException {
        Template template = cfg.getTemplate(path);
        StringWriter writer = new StringWriter();
        template.process(map, writer);
        return writer.toString();
    }
}
