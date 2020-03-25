/**
 * Package servlets.user for
 *
 * @author Maksim Tiunchik
 */
package servlets.user;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
    private static final Validate LOGIC = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/Pages/create.jsp").forward(req, resp);
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
        Map<String, String> s = findParametrs(req);
        User temp = new User();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        Date date = today.getTime();
        temp.setId(Integer.parseInt(s.get("id")));
        temp.setName(s.get("name"));
        temp.setLogin(s.get("name"));
        temp.setCreateDate(date);
        if (s.get("image").contains("tempfile")) {
            Path folders = Paths.get("bin/images");
            if (!Files.exists(folders)) {
                Files.createDirectories(folders);
            }
            Path file = Paths.get("images" + File.separator + "tempfile.jpg");
            String path = folders + File.separator + s.get("id") + ".jpg";
            Path userFile = Paths.get(path);
            OutputStream out = Files.newOutputStream(userFile);
            InputStream in = Files.newInputStream(file);
            out.write(in.readAllBytes());
            out.flush();
            temp.setImage(path);
        }
        LOGIC.add(temp);
        LOGIC.saveRole(new Role(temp, "", "user"));

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
