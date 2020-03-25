/**
 * Package servlets.user for
 *
 * @author Maksim Tiunchik
 */
package servlets.user;

import jdk.swing.interop.SwingInterOpUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Class UserServlet - create list of added users
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.2
 * @since 21.03.2020
 */
public class UserServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UserServlet.class.getName());

    /**
     * logic block, have connection to DB
     */
    private static final Validate LOGIC = ValidateService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession ses = req.getSession();
        Role role = (Role) ses.getAttribute("role");
        req.setAttribute("role", role);
        req.setAttribute("users", LOGIC.findALL());
        req.getRequestDispatcher("/WEB-INF/Pages/UserList.jsp").forward(req, resp);
    }

    /**
     * add user to list of users that is consisted into DB
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        //имхо это надо делать через стратегию, но сейчас и switch покатит
        Map<String, String> s = findParametrs(req);
        try {
            if (s.containsKey("id") && s.get("action").equals("delete")) {
                User temp = LOGIC.findByID(new User(Integer.parseInt(s.get("id")), "tempuser"));
                if (temp.getImage() != null) {
                    Path file = Paths.get(temp.getImage());
                    if (Files.exists(file)) {
                        Files.delete(file);
                    }
                }
                LOGIC.delete(temp);
            }
        } catch (IOException e) {
            LOG.error("File delete error", e);
        } finally {
            try {
                resp.sendRedirect(req.getContextPath() + "/");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private Map<String, String> findParametrs(HttpServletRequest req) {
        HashMap<String, String> temp = new HashMap<>();
        req.getParameterNames().asIterator().forEachRemaining(e -> {
            temp.put(e, req.getParameter(e));
        });
        return temp;
    }
}
