/**
 * Package servlets.user for
 *
 * @author Maksim Tiunchik
 */
package servlets.mainprogramm;

import net.jcip.annotations.ThreadSafe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Class ValidateService -
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 19.03.2020
 */
@ThreadSafe
public enum ValidateService implements Validate {
    LOGIC;

    private static final Logger LOG = LogManager.getLogger(ValidateService.class.getName());

    private static final DBStore STORE = DBStore.getInstance();

    public static Validate getInstance() {
        return LOGIC;
    }

    public void add(User user) {
        if (findByID(user) == null) {
            STORE.add(user);
        }
    }

    public void update(User user) {
        User temp = new User();
        temp.setId(user.getId());
        if (findByID(temp) == null) {
            STORE.add(user);
        } else {
            STORE.update(user);
        }
    }

    public void delete(User user) {
        STORE.delete(user);
    }

    public User findByID(User user) {
        return STORE.findByID(user);
    }

    public List<User> findALL() {
        return STORE.findALL();
    }

    public void saveRole(Role role) {
        STORE.saveRole(role);
    }

    public Role getRole(User user) {
        return STORE.getRole(user);
    }

    public List<String> getCity() {
        return DBStore.getInstance().getCity();
    }
}

