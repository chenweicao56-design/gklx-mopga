package com.gklx.mopga.admin.util;

import com.gklx.mopga.admin.module.generate.util.VelocityInitializer;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.Map;

public class VelocityUtil {

    public static String text(String path, Map<String,Object> map) {
        VelocityInitializer.initVelocity();
        VelocityContext velocityContext = new VelocityContext();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            velocityContext.put(entry.getKey(), entry.getValue());
        }
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(path, "UTF-8");
        tpl.merge(velocityContext, sw);
        return sw.toString();
    }
}
