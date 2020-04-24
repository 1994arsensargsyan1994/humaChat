package com.human.humanchat.servlet;


import com.human.humanchat.model.User;
import com.human.humanchat.util.DataValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/register"})
public class RegisterServlet extends BaseServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/register.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestValidator<User> validator = validate(req);
        try {
            if (validator.isValid()) {
                User user = validator.getEntity();
                if (this.userService.userExist(user.getEmail())) {
                    req.setAttribute("errorEmail", "user already exist");
                } else {
                    //  TODO save user and to login page
                    this.userService.save(user);

                    req.getSession().setAttribute("successfully", "user successfully registered.");
                    resp.sendRedirect("/login");
                    return;
                }
            }
            req.getRequestDispatcher("WEB-INF/register.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    private static RequestValidator<User> validate(HttpServletRequest request) {
        boolean isValid = true;
        String name = request.getParameter("name");
        if (DataValidator.isNullOrBlank(name)) {
            request.setAttribute("errorName", "Name is Required");
            isValid = false;
        }
        String surname = request.getParameter("surname");
        if (DataValidator.isNullOrBlank(surname)) {
            request.setAttribute("errorSurname", "Surname is Required");
            isValid = false;
        }
        String email = request.getParameter("email");
        if (DataValidator.isNullOrBlank(email)) {
            request.setAttribute("errorEmail", "Email is Required");
            isValid = false;
        }
        String password = request.getParameter("password");
        if (DataValidator.isNullOrBlank(password)) {
            request.setAttribute("errorPassword", "Password is Required");
            isValid = false;
        } else {
            String confirmPassword = request.getParameter("confirmPassword");
            if (!password.trim().equals(confirmPassword)) {
                request.setAttribute("errorConfirmPassword", "Password does not match!");
                isValid = false;
            }
        }
        RequestValidator<User> validator = new RequestValidator<>();
        validator.setValid(isValid);
        if (isValid) {
            User user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setPassword(password);
            validator.setEntity(user);
        }
        return validator;

    }

}
