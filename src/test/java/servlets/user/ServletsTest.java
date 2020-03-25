package servlets.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "java.xml.", "javax.management.*", "org.w3c.dom.*"})
public class ServletsTest {

    private Validate validate;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private Vector<String> base = new Vector<>(List.of("name", "id",
            "image", "action", "password", "role", "login", "email"));

    @Before
    public void setUp() {
        validate = ValidateStub.VALIDATE_STUB;
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        PowerMockito.mockStatic(ValidateService.class);
        when(ValidateService.getInstance()).thenReturn(validate);
        when(req.getParameterNames()).thenReturn(base.elements());
    }

    @Test
    public void whenWeAddUserBydoPostThneCheckUser() throws ServletException, IOException {
        when(req.getParameter("name")).thenReturn("Petr Arsentev");
        when(req.getParameter("id")).thenReturn("0001");
        when(req.getParameter("image")).thenReturn("0001");

        new UserCreateServlet().doPost(req, resp);

        assertThat(validate.findALL().iterator().next().getName(), is("Petr Arsentev"));
    }

    @Test
    public void whenWeAddUserAndDeleteThenCheckThrerIsUserIntoBase() throws ServletException, IOException {
        validate.add(new User(1, "Petr Arsentev"));

        when(req.getParameter("id")).thenReturn("0001");
        when(req.getParameter("action")).thenReturn("delete");

        new UserServlet().doPost(req, resp);

        assertNull(validate.findByID(new User(1, "tempname")));
    }

    @Test
    public void whenWePutUserAndUpdateThenCheck() throws ServletException, IOException {
        User temp = new User(1, "Petr Arsentev");

        validate.add(temp);

        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("login")).thenReturn("c");
        when(req.getParameter("name")).thenReturn("Maxim");
        when(req.getParameter("email")).thenReturn("@gmail");

        new UserUpdateServlet().doPost(req, resp);

        temp = validate.findByID(temp);

        assertEquals("Maxim", temp.getName());
        assertEquals("@gmail", temp.getEmail());
    }

    public enum ValidateStub implements Validate {
        VALIDATE_STUB;

        private final Map<Integer, User> store = new HashMap<>();
        private int ids = 0;

        @Override
        public void add(User user) {
            this.store.put(user.getId(), user);
        }

        @Override
        public List<User> findALL() {
            return new ArrayList<User>(this.store.values());
        }

        @Override
        public void saveRole(Role role) {

        }

        @Override
        public User findByID(User user) {
            return store.get(user.getId());
        }

        @Override
        public void delete(User user) {
            store.remove(user.getId());
        }

        @Override
        public Role getRole(User user) {
            return new Role(user, "none", "user");
        }

        @Override
        public void update(User user) {
            store.put(user.getId(), user);
        }


    }
}