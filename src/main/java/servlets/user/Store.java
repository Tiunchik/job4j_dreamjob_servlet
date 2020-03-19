/**
 * Package servlets.user for
 *
 * @author Maksim Tiunchik
 */

package servlets.user;

import java.util.List;

/**
 * Interface Store - 
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 19.03.2020
 */
public interface Store {

    void add(User user);

    void update(User user);

    void delete(User user);

    User findByID(User user);

    List<User> findALL();
}
