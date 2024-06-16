package htwberlin.backend;

import htwberlin.backend.Entity.UserEntity;
import htwberlin.backend.Exception.UserAlreadyExistsException;
import htwberlin.backend.Exception.UserNotFoundException;
import htwberlin.backend.repository.UserRepository;
import htwberlin.backend.service.UserService;
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
    public void testAddUser_Success() {
        UserEntity user = userService.addUser("newuser", "newpassword", "newuser@example.com");
        assertNotNull(user.getId());
        assertEquals("newuser", user.getUsername());
        assertEquals("newuser@example.com", user.getEmail());
    }

    /*
    Hier wird User 1 ein Account erstellen dann wird User 2 ein Account erstellen aber es kommt zu einer UserAlreadyExistsException da die Email existiert
     */
    @Test
    public void testAddUser_UserAlreadyExistsByEmail() {
        userService.addUser("testuser", "password123", "test@example.com");

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.addUser("anotheruser", "password123", "test@example.com");
        });
    }

    @Test
    public void testAuthenticateUser_Success() {
        userService.addUser("testuser", "password123", "test@example.com");
        UserEntity user = userService.authenticateUser("test@example.com", "password123");
        assertNotNull(user);
    }

    @Test
    public void testAuthenticateUser_UserNotFound() {
        assertThrows(UserNotFoundException.class, () -> {
            userService.authenticateUser("nonexistent@example.com", "password123");
        });
    }

    @Test
    public void testFindUserByUsername_UserNotFound() {
        assertThrows(UserNotFoundException.class, () -> {
            userService.findUserByUsername("nonexistentuser");
        });
    }

    @Test
    public void testFindUserByUsername_Success() {
        userService.addUser("testuser", "password123", "test@example.com");
        UserEntity user = userService.findUserByUsername("testuser");
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
    }
}