package htwberlin.backend.service;

import htwberlin.backend.Entity.UserEntity;
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
        UserEntity userByEmail = userRepository.findUserEntityByEmail(email);
        UserEntity userByUsername = userRepository.findUserEntityByUsername(username);
        if (userByEmail != null) {
            throw new UserAlreadyExistsException("Ein Benutzer mit dieser E-Mail-Adresse existiert bereits.");
        }
        if (userByUsername != null) {
            throw new UserAlreadyExistsException("Ein Benutzer mit diesen Benutzernamen existiert bereits.");
        }
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setId(UUID.randomUUID().toString());
        return userRepository.save(user);
        //userRepository.findByEmailContaining("gmail");
    }


}
