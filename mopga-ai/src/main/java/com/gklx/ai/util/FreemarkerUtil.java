package com.gklx.ai.util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerUtil {

    private static final Configuration FREEMARKER_CONFIG;

    static {
        FREEMARKER_CONFIG = new Configuration(Configuration.VERSION_2_3_32);
        FREEMARKER_CONFIG.setClassForTemplateLoading(FreemarkerUtil.class, "/prompt");
        FREEMARKER_CONFIG.setDefaultEncoding("UTF-8");
    }

    public static String render(String templateName, Map<String, Object> data) throws Exception {
        Template template = FREEMARKER_CONFIG.getTemplate(templateName);
        StringWriter writer = new StringWriter();
        template.process(data, writer);
        return writer.toString();
    }

    public static String render(String templateName, Object bean) throws Exception {
        Map<String, Object> data = beanToMap(bean);
        return render(templateName, data);
    }

    private static Map<String, Object> beanToMap(Object bean) {
        if (bean == null) {
            return new HashMap<>();
        }

        Map<String, Object> map = new HashMap<>();
        try {
            // 使用 BeanInfo 方式（推荐）
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor pd : propertyDescriptors) {
                String propertyName = pd.getName();
                if (!"class".equals(propertyName)) {
                    Method readMethod = pd.getReadMethod();
                    if (readMethod != null) {
                        Object value = readMethod.invoke(bean);
                        map.put(propertyName, value);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Bean 转换为 Map 失败: " + e.getMessage(), e);
        }
        return map;
    }

}
