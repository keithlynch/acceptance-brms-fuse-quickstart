package org.jboss.acceptance.tools;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerTemplate {

  public static String build(String templateFile, Map<String, Object> replacements) throws IOException, TemplateException {
    Configuration cfg = new Configuration();
    Template template = cfg.getTemplate("src/test/resources/" + templateFile);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    template.process(replacements, new OutputStreamWriter(out));
    out.flush();
    return out.toString();
  }
}
