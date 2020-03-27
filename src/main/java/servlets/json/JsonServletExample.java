/**
 * Package servlets.json for
 *
 * @author Maksim Tiunchik
 */
package servlets.json;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class JsonServletExample -
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 27.03.2020
 */
public class JsonServletExample extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(JsonServletExample.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/HTML/JsonExample.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter out = resp.getWriter()) {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            resp.setStatus(200);
            resp.setContentType("text/plain");
            if (login.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
                out.write("message: all ok");
            } else {
                out.write("message: Incorrect login and password");
            }
            out.flush();
        }
    }
}
