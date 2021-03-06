/**
 * Package servlets.load for
 *
 * @author Maksim Tiunchik
 */
package servlets.mainprogramm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class Donwload -
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 23.03.2020
 */
public class Donwload extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(Donwload.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        if (!name.contains("undefined")) {
            resp.setContentType("name=" + name);
            resp.setContentType("image/png");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
            File file = new File(name);
            if (file.exists()) {
                try (FileInputStream in = new FileInputStream(file)) {
                    resp.getOutputStream().write(in.readAllBytes());
                }
            }
        }
    }
}
