package com.pass.ftl.util;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Freemarker工具类
 *
 * @Author yuanzhonglin
 * @since 2021/4/3
 */
@Data
@Slf4j
public class FreeMarkerTemplateUtils {
    private static Map<String, Template> templates = new HashMap<>();

    public static String render(Object model, String folder, String fileName) {
        return render(model, folder, fileName, "utf-8");
    }

    public static String render(Object model, String folder, String fileName, String encoding) {
        String key = folder + "/" + fileName;
        try {
            Template template;
            if (templates.containsKey(key)) {
                template = templates.get(key);
            } else {
                Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
                cfg.setDefaultEncoding(encoding);
                cfg.setClassLoaderForTemplateLoading(FreeMarkerTemplateUtils.class.getClassLoader(), folder);
                template = cfg.getTemplate(fileName);
                templates.put(key, template);
            }
            StringWriter writer = new StringWriter();
            template.process(model, writer);
            return writer.toString();
        } catch (Exception e) {
            log.error("The freemarker template \'" + folder + "/" + fileName + "\' read failed", e);
            System.out.println(e);
        }

        return null;
    }

    public static String renderString(String templateContent, Map<String, Object> datas, String templateName) {

        String str = null;

        try {

            Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
            StringTemplateLoader stringLoader = new StringTemplateLoader();
            //自定义模板名  模板内容
            stringLoader.putTemplate(templateName, templateContent);
            //加载模板
            cfg.setTemplateLoader(stringLoader);

            Template template = cfg.getTemplate(templateName, "utf-8");
            StringWriter writer = new StringWriter();
            template.process(datas, writer);
            str = writer.toString();
        } catch (Exception e) {
            log.error("templateContent = {},datas = {},e = {}", templateContent, datas, e);
        }

        return str;
    }


}
