package ru.itmo.wp.web.page;

import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class IndexPage extends Page {

    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
    }
    @Override
    public void after(HttpServletRequest request, Map<String, Object> view) {
        super.after(request, view);
    }
    public void action() {
    }
}
