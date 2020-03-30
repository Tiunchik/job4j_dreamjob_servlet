/**
 * Package servlets.user for
 *
 * @author Maksim Tiunchik
 */
package servlets.mainprogramm;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class UserRoles -
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 24.03.2020
 */
public class UserRoles extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UserRoles.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession ses = req.getSession();
        ses.setAttribute("role", null);
        req.getRequestDispatcher("/HTMLREF/login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Role role = (Role) session.getAttribute("role");
        JSONObject jsonAnswer = new JSONObject();
        jsonAnswer.put("role", role.getRole());
        jsonAnswer.put("id", role.getUser().getId());
        String s = jsonAnswer.toJSONString();
        resp.setContentType("json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(s);
        out.flush();
    }
}
