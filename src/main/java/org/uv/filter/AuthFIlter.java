package org.uv.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.uv.vista.LoginBean;

@WebFilter("*.xhtml")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

   
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 1. Convertir los objetos request/response a sus versiones HTTP
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); 

        // 2. Definir las URIs de login y bienvenida
        String loginURI = req.getContextPath() + "/login.xhtml";
        String welcomeURI = req.getContextPath() + "/empleados.xhtml";

        // 3. Obtener el LoginBean de la sesión
        boolean isLoggedIn = false;
        if (session != null) {
            Object loggedInFlag = session.getAttribute("userIsLoggedIn");
            if (loggedInFlag != null && loggedInFlag instanceof Boolean) {
                isLoggedIn = (Boolean) loggedInFlag;
            }
        }

        boolean isLoginPage = req.getRequestURI().equals(loginURI);

        // 5. Lógica de Redirección
        if (isLoggedIn) {
            if (isLoginPage) {
                res.sendRedirect(welcomeURI);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            if (isLoginPage) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(loginURI);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
