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
    private static final ValidateService LOGIC = ValidateService.LOGIC;

    /**
     * show full list of users that is consisted into DB
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
        out.append("<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>List of users</title>\n"
                + "<style>\n"
                + "body {\n"
                + "font-size: 120%;\n"
                + "font-family: \"Times New Roman\",sans-serif;\n"
                + "color: #333333;\n"
                + "}\n"
                + "table {\n"
                + "border: 1px solid black;\n"
                + "width: 800px;\n"
                + "}\n"
                + "\n"
                + "tr {\n"
                + "border: 1px dashed grey;\n"
                + "}\n"
                + "\n"
                + "th {\n"
                + "border: 1px dashed grey;\n"
                + "}\n"
                + "</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "<table>\n"
                + "<tr>\n"
                + "<th>ID number</th>\n"
                + "<th>Name</th>\n"
                + "<th>login</th>\n"
                + "<th>E-mail</th>\n"
                + "<th>Creation Date</th>\n"
                + "<th>Control buttons</th>\n"
                + "</tr>");
        LOGIC.findALL().forEach(e -> {
            out.append("<tr>\n");
            out.append(String.format("<th> %10s </th>\n<th> %10s  </th>\n<th> %10s  </th>\n<th> %10s  </th>\n<th> %10s </th>\n",
                    e.getId(), e.getName(), e.getLogin(), e.getEmail(), e.getCreateDate()));
            out.append("<th>\n"
                    + "<form action=\"" + req.getContextPath() + "/edit? method=\"get\">\n"
                    + "<input type=\"hidden\" name=\"id\" value=\"" + e.getId() + "\">"
                    + "<input type=\"submit\" value=\"Change\">\n"
                    + "</form>\n"
                    + "<form action=\"" + req.getContextPath() + "/list\" method=\"post\">\n"
                    + "<input type=\"hidden\" name=\"action\" value=\"delete\">"
                    + "<input type=\"hidden\" name=\"id\" value=\"" + e.getId() + "\">"
                    + "<input type=\"submit\" value=\"Delete\">\n"
                    + "</form>\n"
                    + "\n"
                    + "</th>\n"
                    + "</tr>");
        });
        out.append("</table>\n"
                + "<form>"
                + "<input type=\"submit\" value=\"add user\" formaction=\""
                + req.getContextPath() + "/create\" formmethod=\"get\">"
                + "</form>"
                + "</body>\n"
                + "</html>");
        writer.append(out.toString());
        writer.flush();
    }
}
