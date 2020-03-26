/**
 * Package servlets.ajax for
 *
 * @author Maksim Tiunchik
 */
package servlets.ajax;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class PageStartServer -
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 26.03.2020
 */
public class PageStartServer extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(PageStartServer.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/HTML/ajaxExample.html").forward(req, resp);
    }
}
