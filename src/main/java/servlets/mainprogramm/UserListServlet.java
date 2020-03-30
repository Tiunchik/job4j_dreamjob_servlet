/**
 * Package servlets.user for
 *
 * @author Maksim Tiunchik
 */
package servlets.mainprogramm;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class UserServlet - create list of added users
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.2
 * @since 21.03.2020
 */
public class UserListServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UserListServlet.class.getName());

    /**
     * logic block, have connection to DB
     */
    private static final Validate LOGIC = ValidateService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/HTMLREF/usersList.html").forward(req, resp);
    }

    /**
     * add user to list of users that is consisted into DB
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (PrintWriter out = resp.getWriter()) {
            resp.setCharacterEncoding("UTF-8");
            String jsonOut = new Gson().toJson(LOGIC.findALL());
            out.write(jsonOut);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
