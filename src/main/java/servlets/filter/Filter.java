/**
 * Package servlets.filter for
 *
 * @author Maksim Tiunchik
 */
package servlets.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import servlets.mainprogramm.ValidateService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class Filter -
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 24.03.2020
 */
public class Filter implements javax.servlet.Filter {
    private static final Logger LOG = LogManager.getLogger(Filter.class.getName());

    private final static ValidateService LOGIC = ValidateService.LOGIC;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = ((HttpServletRequest) req);
        if (request.getRequestURI().contains("/login")) {
            chain.doFilter(req, resp);
        } else {
            HttpServletResponse response = ((HttpServletResponse) resp);
            HttpSession session = request.getSession();
            if (session.getAttribute("role") == null) {
                response.sendRedirect(String.format("%s/login", request.getContextPath()));
                return;
            }
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}
