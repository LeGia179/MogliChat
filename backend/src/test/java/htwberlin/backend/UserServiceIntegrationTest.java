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

}