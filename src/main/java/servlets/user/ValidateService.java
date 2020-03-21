/**
 * Package servlets.user for
 *
 * @author Maksim Tiunchik
 */
package servlets.user;

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
public enum ValidateService {
    LOGIC;

    private static final Logger LOG = LogManager.getLogger(ValidateService.class.getName());

    private static final Store STORE = DBStore.getInstance();

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

}

