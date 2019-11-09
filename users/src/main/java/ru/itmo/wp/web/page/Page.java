package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

class Page {
    private final UserService userService = new UserService();
    private HttpServletRequest request = null;

    public void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;
        putUser(view);
        putMessage(view);
        view.put("userCount", userService.findCount());
    }

    public void after(HttpServletRequest request, Map<String, Object> view) {
    }

    private void putUser(Map<String, Object> view) {
        User user = getUser();
        if (user != null) {
            view.put("user", user);
        }
    }

    public User getUser() {
        return (User)request.getSession().getAttribute("user");
    }

    public void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

    void putMessage(Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }
}
