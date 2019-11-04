package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MessageServlet extends HttpServlet {
    private ArrayList<myPair> messageArray = new ArrayList<>();
    static class myPair {
        String user;
        String text;
        myPair(String user, String text) {
            this.user = user;
            this.text = text;
        }
        public String getUser() { return user; }
        public String getText() { return text; }
        public void setUser(String user) { this.user = user; }
        public void setText(String text) { this.text = text; }
    }
    private void printJson(String jsonRes, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().print(jsonRes);
        response.getWriter().close();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        HttpSession httpSession = request.getSession();
        response.setCharacterEncoding("windows-1251");
        if (uri.endsWith("/message/auth")) {
            String user = request.getParameter("user");
            if (user == null) {
                user = (String)httpSession.getAttribute("user");
            } else {
                httpSession.setAttribute("user", user);
            }
            printJson(new Gson().toJson(user), response);
        } else if (uri.endsWith("/message/findAll")) {
            printJson(new Gson().toJson(messageArray), response);
        } else if (uri.endsWith("/message/add")) {
            String text = request.getParameter("text");
            String user = (String)httpSession.getAttribute("user");
            messageArray.add(new myPair(user, text));
            printJson(new Gson().toJson(text), response);
        }
    }
}
