/**
 * Package servlets.filter for
 *
 * @author Maksim Tiunchik
 */
package servlets.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import servlets.user.Role;
import servlets.user.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class SignInServlet -
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 24.03.2020
 */
public class SignInServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(SignInServlet.class.getName());

    private final static ValidateService LOGIC = ValidateService.LOGIC;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/Pages/SingIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> keys = findParametrs(req);
        String login = keys.get("login"), password = keys.get("password");
        if (login != null && password != null) {
            for (var user : LOGIC.findALL()) {
                Role role = LOGIC.getRole(user);
                if (user.getLogin().equalsIgnoreCase(login)
                        && role.getPassword().equalsIgnoreCase(password)) {
                    HttpSession session = req.getSession();
                    session.setAttribute("role", role);
                    break;
                }
            }
        }
        req.getRequestDispatcher(String.format("%s/", req.getContextPath())).forward(req, resp);
    }

    private Map<String, String> findParametrs(HttpServletRequest req) {
        HashMap<String, String> temp = new HashMap<>();
        req.getParameterNames().asIterator().forEachRemaining(e -> {
            temp.put(e, req.getParameter(e));
        });
        return temp;
    }
}
