package ru.itmo.tpl.web;

import freemarker.template.*;
import ru.itmo.tpl.util.DataUtil;
import ru.itmo.tpl.util.DebugUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerServlet extends HttpServlet {
    private Configuration freemarkerConfiguration;

    @Override
    public void init() throws ServletException {
        super.init();

        freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_29);

        File freemarkerDirectory = DebugUtil.getFile(getServletContext(), "WEB-INF/templates");
        try {
            freemarkerConfiguration.setDirectoryForTemplateLoading(freemarkerDirectory);
        } catch (IOException e) {
            throw new ServletException("Unable to configure freemarker configuration:"
                    + " freemarkerConfiguration.setDirectoryForTemplateLoading(freemarkerDirectory) failed"
                    + " [freemarkerDirectory=" + freemarkerDirectory + "].", e);
        }

        freemarkerConfiguration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        freemarkerConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        freemarkerConfiguration.setLogTemplateExceptions(false);
        freemarkerConfiguration.setWrapUncheckedExceptions(true);
        freemarkerConfiguration.setFallbackOnNullLoopVariable(false);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Template template;
        try {
            if (request.getRequestURI().matches("[/]*") || request.getRequestURI().equals("") ) {
                template = freemarkerConfiguration.getTemplate(
                        URLDecoder.decode("/index", StandardCharsets.UTF_8.name()) + ".ftlh");
            } else {
                template = freemarkerConfiguration.getTemplate(
                        URLDecoder.decode(request.getRequestURI(), StandardCharsets.UTF_8.name()) + ".ftlh");
            }
        } catch (TemplateNotFoundException ignored) {
            template = freemarkerConfiguration.getTemplate(
                    URLDecoder.decode("/notFound", StandardCharsets.UTF_8.name()) + ".ftlh");
            //response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        response.setContentType("text/html");
        Map<String, Object> data = new HashMap<>();
        putData(request, data);

        try {
            template.process(data, response.getWriter());
        } catch (TemplateException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    private void putData(HttpServletRequest request, Map<String, Object> data) {
        data.put("uri", request.getRequestURI());
        for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
            if (e.getValue() != null && e.getValue().length == 1) {
//                System.out.println(e.getKey());
//                System.out.println(e.getValue()[0]);
                if (e.getKey().endsWith("id"))
                    try {
                        data.put(e.getKey(), Long.parseLong(e.getValue()[0]));
                    } catch (NumberFormatException ignored) {
                    }
                else
                    data.put(e.getKey(), e.getValue()[0]);
            }
        }

        DataUtil.putData(data);
    }
}
