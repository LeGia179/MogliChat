package htwberlin.backend.service;

import htwberlin.backend.Entity.UserEntity;
import htwberlin.backend.Exception.IncorrectPasswordException;
import htwberlin.backend.Exception.UserNotFoundException;
import htwberlin.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import htwberlin.backend.Exception.UserAlreadyExistsException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserEntity addUser(String username, String password, String email) {
        UserEntity userByEmailAndUsername = userRepository.findUserEntityByEmailAndUsername(email, username);
        UserEntity userByEmail = userRepository.findUserEntityByEmail(email);
        UserEntity userByUsername = userRepository.findUserEntityByUsername(username);
        if (userByEmailAndUsername != null) {
            throw new UserAlreadyExistsException("Ein Benutzer mit dieser E-Mail-Adresse und diesem Benutzernamen existiert bereits.");
        }
        if (userByEmail != null) {
            throw new UserAlreadyExistsException("Ein Benutzer mit dieser E-Mail-Adresse existiert bereits.");
        }
        if (userByUsername != null) {
            throw new UserAlreadyExistsException("Ein Benutzer mit diesen Benutzernamen existiert bereits.");
        }
        UserEntity user = new UserEntity();
        user.setUsername(username);
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);
        user.setEmail(email);
        user.setId(UUID.randomUUID().toString());
        //userRepository.deleteAll();
        return userRepository.save(user);
        //userRepository.findByEmailContaining("gmail");

    }

    public UserEntity authenticateUser(String email, String password) {
        UserEntity user = userRepository.findUserEntityByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("Kein Benutzer mit dieser E-Mail-Adresse vorhanden.");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectPasswordException("Falsches Passwort.");
        }
        return user;
    }
}
