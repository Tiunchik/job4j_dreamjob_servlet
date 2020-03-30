/**
 * Package servlets.filter for
 *
 * @author Maksim Tiunchik
 */
package servlets.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import servlets.mainprogramm.Role;
import servlets.mainprogramm.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
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
        req.getRequestDispatcher("/HTMLREF/login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (BufferedReader read = req.getReader()) {
            StringBuilder fullLine = new StringBuilder();
            String oneLine;
            while ((oneLine = read.readLine()) != null) {
                fullLine.append(oneLine);
            }
            JSONObject json = (JSONObject) new JSONParser().parse(fullLine.toString());
            String login = (String) json.get("login"), password = (String) json.get("password");
            if (login != null && password != null) {
                for (var user : LOGIC.findALL()) {
                    Role role = LOGIC.getRole(user);
                    if (user.getLogin().equalsIgnoreCase(login)
                            && role.getPassword().equalsIgnoreCase(password)) {
                        HttpSession session = req.getSession();
                        session.setAttribute("role", role);
                        resp.sendRedirect(String.format("%s/users", req.getContextPath()));
                        break;
                    }
                }
            }
        } catch (IOException | ParseException e) {
            LOG.error("login error", e);
        }
    }
}
