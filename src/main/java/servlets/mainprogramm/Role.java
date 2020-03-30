/**
 * Package servlets.user for
 *
 * @author Maksim Tiunchik
 */
package servlets.mainprogramm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 * Class Role -
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 24.03.2020
 */
public class Role {
    private static final Logger LOG = LogManager.getLogger(Role.class.getName());

    private final User user;

    private String password;

    private String role;

    public Role(User user, String password, String role) {
        this.user = user;
        this.password = password;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role1 = (Role) o;
        return (user.equals(role1.user) && role.equals(role1.role));
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }
}
