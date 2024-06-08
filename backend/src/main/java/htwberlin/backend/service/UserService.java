package htwberlin.backend.service;

import htwberlin.backend.Entity.UserEntity;
import htwberlin.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void addUser(String username, String password, String email) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setId(UUID.randomUUID().toString());
        userRepository.save(user);
    }
}
