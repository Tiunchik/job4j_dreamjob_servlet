/**
 * Package servlets.user for
 *
 * @author Maksim Tiunchik
 */
package servlets.mainprogramm;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Class UserUpdateServlet - call page for changing information about user. Can be called from list page.
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 20.03.2020
 */
public class MainPostServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(MainPostServlet.class.getName());

    /**
     * logic block, have connection to DB
     */
    private static final ValidateService LOGIC = ValidateService.LOGIC;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/HTMLREF/edit.html").forward(req, resp);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        HttpSession ses = req.getSession();
        Role sesrole = (Role) ses.getAttribute("role");
        try (BufferedReader read = req.getReader();
             PrintWriter out = resp.getWriter()) {
            StringBuilder fullLine = new StringBuilder();
            String oneLine;
            while ((oneLine = read.readLine()) != null) {
                fullLine.append(oneLine);
            }
            JSONObject json = (JSONObject) new JSONParser().parse(fullLine.toString());
            String temp = (String) json.get("action");
            if (temp.equalsIgnoreCase("addUser")) {
                User user = new User();
                Date date = new Date();
                user.setId(Integer.parseInt((String) json.get("id")));
                user.setName((String) json.get("name"));
                user.setLogin((String) json.get("login"));
                user.setCreateDate(new Timestamp(date.getTime()));
                LOGIC.add(user);
                LOGIC.saveRole(new Role(user, "", "user"));
            }
            if (temp.equalsIgnoreCase("getRole")) {
                temp = (String) json.get("id");
                Role role = LOGIC.getRole(new User(Integer.parseInt(temp), "null"));
                String jsonData = new Gson().toJson(role);
                out.write(jsonData);
            }
            if (temp.equalsIgnoreCase("getUser")) {
                temp = (String) json.get("id");
                User user = LOGIC.findByID(new User(Integer.parseInt(temp), "null"));
                String jsonData = new Gson().toJson(user);
                out.write(jsonData);
            }
            if (temp.equalsIgnoreCase("getCities")) {
                JSONArray array = new JSONArray();
                LOGIC.getCity().forEach(array::add);
                String jsonData = array.toJSONString();
                out.write(jsonData);
                out.flush();
            }
            if (sesrole.getUser().getId() == Integer.parseInt((String) json.get("id"))
                    || sesrole.getRole().equalsIgnoreCase("admin")) {
                if (temp.equalsIgnoreCase("update")) {
                    temp = (String) json.get("id");
                    User user = LOGIC.findByID(new User(Integer.parseInt(temp), "null"));
                    user.setName((String) json.get("name"));
                    user.setLogin((String) json.get("login"));
                    user.setEmail((String) json.get("email"));
                    user.setCountry((String) json.get("country"));
                    user.setCity((String) json.get("city"));
                    LOGIC.update(user);
                    Role role = LOGIC.getRole(new User(Integer.parseInt(temp), "null"));
                    role.setRole((String) json.get("role"));
                    role.setPassword((String) json.get("password"));
                    LOGIC.saveRole(role);
                }
                if (temp.equalsIgnoreCase("saveImage")) {
                    temp = (String) json.get("id");
                    User user = LOGIC.findByID(new User(Integer.parseInt(temp), "null"));
                    Path folders = Paths.get("bin/images");
                    if (!Files.exists(folders)) {
                        Files.createDirectories(folders);
                    }
                    Path file = Paths.get("images" + File.separator + "tempfile.jpg");
                    String path = "bin/images/" + temp + ".jpg";
                    Path userFile = Paths.get(path);
                    OutputStream outFile = Files.newOutputStream(userFile);
                    InputStream in = Files.newInputStream(file);
                    outFile.write(in.readAllBytes());
                    outFile.flush();
                    user.setImage(path);
                    LOGIC.update(user);
                }
            }
            if (sesrole.getRole().equalsIgnoreCase("admin")) {
                if (temp.equalsIgnoreCase("delete")) {
                    temp = (String) json.get("id");
                    LOGIC.delete(new User(Integer.parseInt(temp), "null"));
                }
            }
            out.flush();
        } catch (IOException | ParseException e) {
            LOG.error("Load information by Id error", e);
        }
    }
}
