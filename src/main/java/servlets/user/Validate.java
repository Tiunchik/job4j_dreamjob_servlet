/**
 * Package servlets.user for
 *
 * @author Maksim Tiunchik
 */

package servlets.user;

import java.util.List;

/**
 * Interface Validate - 
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 25.03.2020
 */
public interface Validate {

    public void add(User user);

    public List<User> findALL();

    public void saveRole(Role role);

    public User findByID(User user);

    public void delete(User user);

    public Role getRole(User user);

    public void update(User user);
}