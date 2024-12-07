package org.laborator7.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.servlet.http.HttpSession;
import org.laborator7.cdi.Logged;
import org.laborator7.entities.Role;
import org.laborator7.entities.User;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Logged
public class UserService implements Serializable {

    @PersistenceContext(unitName = "EvaluationPU")
    private EntityManager entityManager;

    @Inject
    private HttpSession httpSession;

    /**
     * Retrieves the currently logged-in user from the session.
     *
     * @return the logged-in user or null if no user is logged in
     */
    public User getLoggedInUser() {
        return (User) httpSession.getAttribute("loggedInUser");
    }

    /**
     * Authenticates a user with the given username and password.
     *
     * @param username the username to authenticate
     * @param password the password to authenticate
     * @return the authenticated user or null if authentication fails
     */
    public User authenticate(String username, String password) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);

        try {
            User user = query.getSingleResult();
            httpSession.setAttribute("loggedInUser", user); // Save the user in the session
            return user;
        } catch (Exception e) {
            return null; // Authentication failed
        }
    }

    /**
     * Retrieves a list of all teachers (users with the 'teacher' role).
     *
     * @return a list of teacher users
     */
    public List<User> findTeachers() {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.role = :role", User.class);
        query.setParameter("role", Role.TEACHER);
        return query.getResultList();
    }

    @Transactional
    public void registerUser(String username, String fullname, String password, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setFullname(fullname);
        user.setPassword(password);
        user.setRole(role);
        entityManager.persist(user);
    }

    public List<User> findAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }
}
