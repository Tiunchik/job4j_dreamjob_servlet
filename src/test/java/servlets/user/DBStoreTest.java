package servlets.user;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class DBStoreTest {

    private final static DBStore STORE = DBStore.getInstance();

    private final static Calendar CAL = Calendar.getInstance();

    @Before
    public void setUP() {
        CAL.set(Calendar.HOUR_OF_DAY, 0);
    }

    @Test
    public void add() {
        STORE.delete(new User(1, "User", CAL.getTime()));
        STORE.add(new User(1, "User", CAL.getTime()));

        User user = STORE.findByID(new User(1, "null"));

        assertNotNull(user);

        STORE.delete(user);
    }

    @Test
    public void update() {
        STORE.delete(new User(1, "User", CAL.getTime()));
        STORE.add(new User(1, "User", CAL.getTime()));
        User temp = new User(1, "Max", CAL.getTime());
        temp.setLogin("Senebh");
        STORE.update(temp);

        temp = STORE.findByID(new User(1, "null", CAL.getTime()));

        assertEquals("Senebh", temp.getLogin());

        STORE.delete(temp);
    }

    @Test
    public void findByID() {
        STORE.delete(new User(1, "User", CAL.getTime()));
        STORE.add(new User(1, "User", CAL.getTime()));

        User user = STORE.findByID(new User(1, "null"));

        assertEquals(1, user.getId());

        STORE.delete(user);
    }

    @Test
    public void findALL() {
        STORE.delete(new User(1, "User", CAL.getTime()));
        STORE.delete(new User(2, "User", CAL.getTime()));
        STORE.delete(new User(3, "User", CAL.getTime()));

        STORE.add(new User(1, "User", CAL.getTime()));
        STORE.add(new User(2, "User", CAL.getTime()));
        STORE.add(new User(3, "User", CAL.getTime()));

        List<User> temp = STORE.findALL();

        assertTrue(temp.size() >= 3);


    }
}