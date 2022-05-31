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

/**
 * If the user is not logged in, redirect to the login page. If the user is logged in, but not an
 * admin, redirect to the home page. If the user is logged in and an admin, allow the request to
 * continue.
 */
@Component
@Order(1)
public class AdminFilter implements Filter, javax.servlet.Filter {

    private UserService userService;

    public AdminFilter(UserService userService) {
        this.userService = userService;
    }

    /**
     * If the user is not logged in, redirect to the login page. If the user is logged in, but not an
     * admin, redirect to the home page. If the user is logged in and an admin, allow the request to
     * continue
     * 
     * @param request The request object represents the HTTP request that your application receives
     * from the client.
     * @param response The response object.
     * @param chain The FilterChain object that represents the chain of filters that the request will
     * pass through.
     */
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
