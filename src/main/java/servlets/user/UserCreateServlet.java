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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class UserCreateServlet - create new user and add to database
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 20.03.2020
 */
public class UserCreateServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UserCreateServlet.class.getName());

    /**
     * logic block, have connection to DB
     */
    private static final ValidateService LOGIC = ValidateService.LOGIC;

    /**
     * show page for creating new user
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        StringBuilder out = new StringBuilder("<html>");
        out.append("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Create User</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<form>\n"
                + "<br>\n"
                + "<label for=\"name\">User name</label>\n"
                + "<input type=\"text\" name=\"name\" id=\"name\" value=\"\">\n"
                + "<br>\n"
                + "<label for=\"id\">User ID</label>\n"
                + "<input type=\"number\" name=\"id\" id=\"id\" value=\"\">\n"
                + "<br><br>\n"
                + "<input type=\"submit\" value=\"Create\" formaction=\""
                + req.getContextPath() + "/create\" formmethod=\"post\">\n"
                + "</form>\n"
                + "</body>\n"
                + "</html>");
        writer.append(out.toString());
        writer.flush();
    }

    /**
     * add user to db
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        Map<String, String> s = findParametrs(req);
        if (s.containsKey("id") && s.containsKey("name")) {
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            Date date = today.getTime();
            User temp = new User();
            temp.setId(Integer.parseInt(s.get("id")));
            temp.setName(s.get("name"));
            temp.setCreateDate(date);
            LOGIC.add(temp);
            writer.append("User is added\n");
            writer.flush();
        } else {
            writer.append("Incorrect input");
            writer.flush();
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
