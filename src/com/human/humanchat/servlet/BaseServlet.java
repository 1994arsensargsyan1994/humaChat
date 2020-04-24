package com.human.humanchat.servlet;


import com.human.humanchat.service.UserService;
import com.human.humanchat.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class BaseServlet extends HttpServlet {
    protected UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserServiceImpl();
    }
}
