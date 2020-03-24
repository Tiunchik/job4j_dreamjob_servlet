/**
 * Package servlets.user for
 *
 * @author Maksim Tiunchik
 */
package servlets.user;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Class DBStore - class for work with PSQL DB during multithreading sessions
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 21.03.2020
 */
@ThreadSafe
public class DBStore implements Store {
    /**
     * inner logger
     */
    private static final Logger LOG = LogManager.getLogger(DBStore.class.getName());

    /**
     * special version of JDBC for multithreading sessions
     */
    private static final BasicDataSource SOURCE = new BasicDataSource();

    /**
     * singleton for DB
     */
    private static final DBStore BASE = new DBStore();

    /**
     * private constructor to set all properties for connection
     */
    private DBStore() {
        SOURCE.setDriverClassName("org.postgresql.Driver");
        SOURCE.setUrl("jdbc:postgresql://127.0.0.1:5432/servletuserdb");
        SOURCE.setUsername("postgres");
        SOURCE.setPassword("password");
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
        createDB();
        createTB();
    }

    /**
     * static method to get link to DBStore
     *
     * @return link to DBStore
     */
    public static DBStore getInstance() {
        return BASE;
    }

    /**
     * add user to DB
     *
     * @param user user for adding
     */
    @Override
    public void add(User user) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection
                     .prepareStatement("INSERT INTO USERTABLE (userid, username, userlogin, usercrdate, userimage) VALUES (?,?,?,?,?)")) {
            st.setInt(1, user.getId());
            st.setString(2, user.getName());
            st.setString(3, user.getLogin());
            Date date = new Date(user.getCreateDate().getTime());
            st.setDate(4, date);
            st.setString(5, user.getImage());
            st.execute();
        } catch (SQLException e) {
            LOG.error("Add method SQL ecxeption", e);
        }
    }

    /**
     * update information about user into db
     *
     * @param user new information about user
     */
    @Override
    public void update(User user) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection
                     .prepareStatement("UPDATE USERTABLE SET username=?, userlogin=?, useremail=?, userimage=? WHERE userid=? ")) {
            st.setString(1, user.getName());
            st.setString(2, user.getLogin());
            st.setString(3, user.getEmail());
            st.setString(4, user.getImage());
            st.setInt(5, user.getId());
            st.execute();
        } catch (SQLException e) {
            LOG.error("Add method SQL ecxeption", e);
        }
    }

    /**
     * delte user from db
     *
     * @param user information about user for deleting
     */
    @Override
    public void delete(User user) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection
                     .prepareStatement("DELETE FROM USERTABLE WHERE userid=?")) {
            st.setInt(1, user.getId());
            st.execute();
        } catch (SQLException e) {
            LOG.error("Add method SQL ecxeption", e);
        }
    }

    /**
     * select and return user from db
     *
     * @param user user for searching
     * @return user
     */
    @Override
    public User findByID(User user) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection
                     .prepareStatement("SELECT * FROM USERTABLE WHERE userid=?")) {
            st.setInt(1, user.getId());
            ResultSet answer = st.executeQuery();
            if (answer.next()) {
                user.setName(answer.getString("username"));
                user.setLogin(answer.getString("userlogin"));
                user.setEmail(answer.getString("useremail"));
                user.setCreateDate(answer.getDate("usercrdate"));
                user.setImage(answer.getString("userimage"));
                return user;
            }
        } catch (SQLException e) {
            LOG.error("Add method SQL ecxeption", e);
        }
        return null;
    }

    /**
     * retrun full list of users from DB
     *
     * @return full list of users
     */
    @Override
    public List<User> findALL() {
        LinkedList<User> list = new LinkedList<>();
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection
                     .prepareStatement("SELECT * FROM USERTABLE")) {
            ResultSet answer = st.executeQuery();
            while (answer.next()) {
                User user = new User();
                user.setId(answer.getInt("userid"));
                user.setName(answer.getString("username"));
                user.setLogin(answer.getString("userlogin"));
                user.setEmail(answer.getString("useremail"));
                user.setCreateDate(answer.getDate("usercrdate"));
                user.setImage(answer.getString("userimage"));
                list.add(user);
            }
        } catch (SQLException e) {
            LOG.error("Add method SQL ecxeption", e);
        }
        return list;
    }

    /**
     * create database
     */
    private void createDB() {
        try (Connection connection = SOURCE.getConnection();
             Statement st = connection.createStatement()) {
            st.execute("SELECT 'CREATE DATABASE servletuserdb'"
                    + " WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'servletuserdb')");
        } catch (SQLException e) {
            LOG.error("create DB method SQL ecxeption", e);
        }
    }

    /**
     * create tabla
     */
    private void createTB() {
        try (Connection connection = SOURCE.getConnection();
             Statement st = connection.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS USERTABLE ("
                    + "userid integer primary key, "
                    + "username varchar(100), "
                    + "userlogin varchar(100) DEFAULT '',"
                    + "useremail varchar(100) DEFAULT '',"
                    + "userimage varchar(255),"
                    + "usercrdate date"
                    + ")");
            st.execute("CREATE TABLE IF NOT EXISTS USERROLLS ("
                    + "userid_role integer REFERENCES usertable (userid) ON DELETE CASCADE, "
                    + "userrole varchar(10) CHECK ((userrole = 'user') OR (userrole = 'admin')), "
                    + "userpassword varchar(20), "
                    + "UNIQUE(userid_role))");
        } catch (SQLException e) {
            LOG.error("create TB method SQL ecxeption", e);
        }
    }

    public void saveRole(Role role) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection
                     .prepareStatement("INSERT INTO USERROLLS (userid_role, userrole, userpassword) VALUES (?,?,?)"
                             + "ON CONFLICT (userid_role) DO UPDATE SET userrole = ?, userpassword = ? where USERROLLS.userid_role = ?")) {
            st.setInt(1, role.getUser().getId());
            st.setString(2, role.getRole());
            st.setString(3, role.getPassword());

            st.setString(4, role.getRole());
            st.setString(5, role.getPassword());
            st.setInt(6, role.getUser().getId());
            st.execute();
        } catch (SQLException e) {
            LOG.error("Add Role SQL ecxeption", e);
        }
    }

    public Role getRole(User user) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement st = connection
                     .prepareStatement("SELECT * FROM USERROLLS WHERE userid_role=?")) {
            st.setInt(1, user.getId());
            ResultSet answer = st.executeQuery();
            if (answer.next()) {
                return new Role(findByID(user),
                        answer.getString("userpassword"),
                        answer.getString("userrole"));
            }
        } catch (SQLException e) {
            LOG.error("Get Role SQL ecxeption", e);
        }
        return null;
    }
}
