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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Class UserServlet -
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 19.03.2020
 */
public class UserServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UserServlet.class.getName());

    private static final ValidateService LOGIC = ValidateService.LOGIC;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append("<h2>");
        writer.append(
                String.format("| %10s | %10s | %10s | %10s | %10s |", "ID", "Name", "Login", "Email", "CreateDate"));
        writer.append("</h2>");
        writer.append("\n");
        LOGIC.findALL().forEach(e -> {
            writer.append("<p>");
            writer.append(String.format("| %10s | %10s | %10s | %10s | %10s |",
                    e.getId(), e.getName(), e.getLogin(), e.getEmail(), e.getCreateDate()));
            writer.append("</p>");
            writer.append("\n");
        });
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //имхо это надо делать через стратегию, но сейчас и switch покатит
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        Map<String, String> s = findParametrs(req);
        switch (s.get("action")) {
            case ("add"):
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
                }
                break;
            case ("update"):
                if (s.containsKey("id") && (s.containsKey("login") || s.containsKey("email"))) {
                    User temp = new User();
                    temp.setLogin(s.get("login"));
                    temp.setEmail(s.get("name"));
                    LOGIC.update(temp);
                    writer.append("User is updated\n");
                    writer.flush();
                }
                break;
            case ("delete"):
                if (s.containsKey("id")) {
                    User temp = new User();
                    temp.setId(Integer.parseInt(s.get("id")));
                    LOGIC.delete(temp);
                    writer.append("User is deleted\n");
                    writer.flush();
                }
                break;
            case ("findbyid"):
                if (s.containsKey("id")) {
                    User temp = new User();
                    temp.setId(Integer.parseInt(s.get("id")));
                    temp = LOGIC.findByID(temp);
                    writer.append("<h2>");
                    writer.append(
                            String.format("| %10s | %10s | %10s | %10s | %10s |", "ID", "Name", "Login", "Email", "CreateDate"));
                    writer.append("</h2>");
                    writer.append("\n");
                    writer.append("<p>");
                    writer.append(String.format("| %10s | %10s | %10s | %10s | %10s |",
                            temp.getId(), temp.getName(), temp.getLogin(), temp.getEmail(), temp.getCreateDate()));
                    writer.append("</p>");
                    writer.append("\n");
                    writer.flush();
                }
                break;
            default:
                writer.append("Incorrect input");
                writer.flush();
                break;
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
