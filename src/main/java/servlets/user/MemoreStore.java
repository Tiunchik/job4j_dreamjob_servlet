/**
 * Package servlets.user for
 *
 * @author Maksim Tiunchik
 */
package servlets.user;

import net.jcip.annotations.ThreadSafe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class MemoreStore -
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 19.03.2020
 */
@ThreadSafe
public enum MemoreStore implements Store {
    MEMORE_STORE;

    private static final Logger LOG = LogManager.getLogger(MemoreStore.class.getName());

    private final ConcurrentHashMap<Integer, User> base = new ConcurrentHashMap<>();

    @Override
    public void add(User user) {
        base.put(user.getId(), user);
    }

    @Override
    public void update(User user) {
        add(user);
    }

    @Override
    public void delete(User user) {
        base.remove(user.getId());
    }

    @Override
    public User findByID(User user) {
        return base.get(user.getId());
    }

    @Override
    public List<User> findALL() {
        return new ArrayList<>(base.values());
    }
}
