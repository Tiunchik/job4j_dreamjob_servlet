/**
 * Package servlets.filter for
 *
 * @author Maksim Tiunchik
 */
package servlets.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class LogOutServlet -
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 29.03.2020
 */
public class LogOutServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(LogOutServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession ses = req.getSession();
        ses.setAttribute("role", null);
        req.getRequestDispatcher("/HTMLREF/login.html").forward(req, resp);
    }
}
