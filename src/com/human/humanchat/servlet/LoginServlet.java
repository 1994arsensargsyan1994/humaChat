package com.human.humanchat.servlet;


import com.human.humanchat.model.User;
import com.human.humanchat.util.DataValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
@WebServlet({"/login"})
public class LoginServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RequestValidator<LoginPayload> validator = LoginServlet.validate(req);
            if (validator.isValid()) {
                Optional<User> user =  super.userService.getUser(validator.getEntity().email, validator.getEntity().password);
                if (user.isPresent()) {
                    req.getSession().setAttribute("user", user.get());
                    resp.sendRedirect("/home");
                    return;
                }
                req.setAttribute("ErrorEmailPassword", "Incorrect Email/Paswword");
            }
            req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private static RequestValidator<LoginPayload> validate(HttpServletRequest request) {
        boolean isValid = true;

        String email = request.getParameter("email");
        if (DataValidator.isNullOrBlank(email)) {
            request.setAttribute("errorEmail", "Email is Required");
            isValid = false;
        }
        String password = request.getParameter("password");
        if (DataValidator.isNullOrBlank(password)) {
            request.setAttribute("errorPassword", "Password is Required");
            isValid = false;
        }
        RequestValidator<LoginPayload> validator = new RequestValidator<>();
        validator.setValid(isValid);
        if (isValid) {
            LoginPayload loginPayload = new LoginPayload();
            loginPayload.email = email;
            loginPayload.password = password;
            validator.setEntity(loginPayload);
        }
        return validator;

    }

    private static class LoginPayload {
        String email;
        String password;
    }

}
