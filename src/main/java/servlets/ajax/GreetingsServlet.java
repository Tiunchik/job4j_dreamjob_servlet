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
import java.io.PrintWriter;

/**
 * Class GreetingsServlet - 
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 26.03.2020
 */
public class GreetingsServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(GreetingsServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println("Nice to meet you, " + name);
        writer.flush();
    }
}
