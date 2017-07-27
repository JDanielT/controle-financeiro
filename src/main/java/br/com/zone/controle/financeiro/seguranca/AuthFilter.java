package br.com.zone.controle.financeiro.seguranca;

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

/**
 *
 * @author daniel
 */
@WebFilter(urlPatterns = "*.xhtml")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest rq = (HttpServletRequest) request;
        HttpServletResponse rp = (HttpServletResponse) response;

        Object object = rq.getSession().getAttribute("usuario");

        String url = rq.getRequestURL().toString();

        if (object == null) {

            if (!url.contains("login") && !url.endsWith(".css")
                    && !url.endsWith(".js") && !url.endsWith(".jpg")
                    && !url.endsWith(".gif") && !url.contains("javax")
                    && !url.contains(".png") && !url.contains("erro")
                    && !url.contains("redefinicao-senha.xhtml")) {

                rp.sendRedirect("login.xhtml");

            }

        }

        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
    }

}
