/**
 * Package servlets.json for
 *
 * @author Maksim Tiunchik
 */
package servlets.json;

import netscape.javascript.JSObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Class JsonServletExample -
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 27.03.2020
 */
public class JsonServletExample extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(JsonServletExample.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/HTML/JsonExample.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter out = resp.getWriter();
             BufferedReader br = req.getReader()) {
        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
            JSONObject parameters = (JSONObject) new JSONParser().parse(sb.toString());
            String login = (String) parameters.get("login");
            String password = (String) parameters.get("password");
            resp.setContentType("text/plain");
            if (login.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
                out.write("message: all ok");
            } else {
                out.write("message: Incorrect login and password");
            }
            out.flush();
        } catch (ParseException | IOException e) {
            LOG.error("Post JsonServletExeption", e);
        }
    }
}
