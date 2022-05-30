package com.example.scoredei.data.filters;

import com.example.scoredei.data.User;
import com.example.scoredei.services.UserService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

@Component
@Order(1)
public class AdminFilter implements Filter, javax.servlet.Filter {

    private UserService userService;

    public AdminFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if(session.getAttribute("user") == null){
            httpResponse.sendRedirect("/");
        } else {
            User user = (User) session.getAttribute("user");
            if(userService.getUser(user.getId()).isEmpty()) {
                session.setAttribute("user", null);
                httpResponse.sendRedirect("/games");
            } else {
                user = userService.getUser(user.getId()).get();
                session.setAttribute("user", user);
                if (!user.getIsAdmin())
                    httpResponse.sendRedirect("/");
                else
                    chain.doFilter(request, response);
            }
        }

    }

    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }
}
