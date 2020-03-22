/**
 * Package servlets.user for
 *
 * @author Maksim Tiunchik
 */
package servlets.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Class UserUpdateServlet - call page for changing information about user. Can be called from list page.
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 20.03.2020
 */
public class UserUpdateServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UserUpdateServlet.class.getName());

    /**
     * logic block, have connection to DB
     */
    private static final ValidateService LOGIC = ValidateService.LOGIC;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String s = req.getParameter("id");
        req.setAttribute("user", LOGIC.findByID(new User(Integer.parseInt(s), "test")));
        req.getRequestDispatcher("/WEB-INF/Pages/update.jsp").forward(req, resp);
    }

    /**
     * post request for changing of user
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> s = findParametrs(req);
        if (s.containsKey("id")) {
            User temp = new User();
            temp.setId(Integer.parseInt(s.get("id")));
            temp.setLogin(s.get("login"));
            temp.setEmail(s.get("email"));
            temp.setName(s.get("name"));
            LOGIC.update(temp);
        }
        resp.sendRedirect(req.getContextPath() + "/");

    }

    private Map<String, String> findParametrs(HttpServletRequest req) {
        HashMap<String, String> temp = new HashMap<>();
        req.getParameterNames().asIterator().forEachRemaining(e -> {
            temp.put(e, req.getParameter(e));
        });
        return temp;
    }
}
