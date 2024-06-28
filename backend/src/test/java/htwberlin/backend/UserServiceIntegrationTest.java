package htwberlin.backend;

import htwberlin.backend.Entity.User;
import htwberlin.backend.repository.UserRepository;
import htwberlin.backend.service.UserService;
import htwberlin.backend.Exception.UserAlreadyExistsException;
import htwberlin.backend.Exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    /*
    Hier wird ein user addUser hinzugefügt
    asserEquals prüft die Eingabe, ob sie existiert.
     */
    public void testAddUser() {
        User user = userService.createUser("newuser", "newuser@gmail.com","password");
        assertNotNull(user.getId());
        assertEquals("newuser", user.getUsername());
        assertEquals("newuser@gmail.com", user.getEmail());
    }

    /*
    Hier wird User 1 ein Account erstellen dann wird User 2 ein Account erstellen aber es kommt zu einer UserAlreadyExistsException da die Email existiert
     */
    @Test
    public void testAddUser_UserAlreadyExistsByEmail() {

        userService.createUser("testuser", "password123", "test@gmail.com");

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser("testuser2", "password123", "test@gmail.com");
        });
    }

    @Test
    public void testAuthenticateUser() {
        userService.createUser("testuser", "password123", "test@gmail.com");
        User user = userService.loginUser("testuser", "password123");
        assertNotNull(user);
    }

    @Test
    public void testAuthenticateUser_UserNotFound() {
        assertThrows(UserNotFoundException.class, () -> {
            userService.loginUser("nonexistent@gmail.com", "password123");
        });
    }

    @Test
    public void testFindUserByUsername_UserNotFound() {
        assertThrows(UserNotFoundException.class, () -> {
            userService.findUserByUsername("noUser");
        });
    }

    @Test
    public void testFindUserByUsername() {
        userService.createUser("testuser", "password123", "test@gmail.com");
        User user = userService.findUserByUsername("testuser");
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
    }
}