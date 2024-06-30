package htwberlin.backend.service;

import htwberlin.backend.Exception.InvalidPasswordException;
import htwberlin.backend.Exception.UserNotFoundException;
import htwberlin.backend.Entity.Message;
import htwberlin.backend.Entity.User;
import htwberlin.backend.repository.ChatMessageRepository;
import htwberlin.backend.repository.MultichannelRepository;
import htwberlin.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MultichannelRepository multichannelRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User createUser(String username, String email, String password){
        User user_exists = userRepository.findUserByEmail(email);
        if(user_exists != null){
            return null;
        }
        User user = new User();
        user.setId(UUID.randomUUID().toString().substring(0,5));
        user.setUsername(username);
        user.setEmail(email);
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User getUserById(String userId){
        return userRepository.findUserById(userId);
    }

    public User getUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public User loginUser(String email, String password){
        User user = userRepository.findUserByEmail(email);
        if (user == null)
            throw new UserNotFoundException("Der Benutzer mit dieser Email: " + email + " Existiert nicht.");
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new InvalidPasswordException("Ungültiges Passwort für die Email: " + email);
        return user;
    }
    public void deleteAllMessagesFromUsers() {
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            user.getMessages().clear();
            userRepository.save(user);
        }
    }
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Transactional
    public void deleteUserByEmail(String email){
        User foundUser = getUserByEmail(email);
        if (foundUser == null) {
            throw new UserNotFoundException("Benutzer mit dieser Email: " + email + " existiert nicht!");
        }
        for (Message message : foundUser.getMessages()) {
            multichannelRepository.findTextchannelsByUsersId(foundUser.getId()).forEach(textchannel -> {
                textchannel.getMessages().remove(message);
                multichannelRepository.save(textchannel);
            });
        }
        multichannelRepository.findTextchannelsByUsersId(foundUser.getId()).forEach(textchannel -> {
            textchannel.getUsers().remove(foundUser);
            multichannelRepository.save(textchannel);
        });
        userRepository.deleteUserByEmail(email);
    }
    @Transactional
    public void deleteUserById(String userId){
        User foundUser = getUserById(userId);
        if (foundUser == null) {
            throw new UserNotFoundException("Benutzer mit dieser ID: " + userId + " existiert nicht!");
        }
        for (Message message : foundUser.getMessages()) {
            multichannelRepository.findTextchannelsByUsersId(foundUser.getId()).forEach(textchannel -> {
                textchannel.getMessages().remove(message);
                multichannelRepository.save(textchannel);
            });
        }
        multichannelRepository.findTextchannelsByUsersId(foundUser.getId()).forEach(textchannel -> {
            textchannel.getUsers().remove(foundUser);
            multichannelRepository.save(textchannel);
        });
        userRepository.deleteUserById(userId);
    }

    public User findUserByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UserNotFoundException("Dieser Benutzername: " + username + " existiert nicht!");
        }
        return user;
    }

}
