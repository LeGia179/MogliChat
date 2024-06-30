package htwberlin.backend.service;

import htwberlin.backend.Entity.Directchannel;
import htwberlin.backend.Entity.User;
import htwberlin.backend.repository.SinglechannelRepository;
import htwberlin.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SinglechannelService {
    private final SinglechannelRepository singlechannelRepository;
    private final UserRepository userRepository;

    public Directchannel createDirectchannel(String userId1, String userId2){
        Directchannel directchannel = new Directchannel();
        User user1 = userRepository.findUserById(userId1);
        User user2 = userRepository.findUserById(userId2);
        directchannel.setId(UUID.randomUUID().toString().substring(0,5));
        directchannel.getUsers().add(user1);
        directchannel.getUsers().add(user2);
        return singlechannelRepository.save(directchannel);
    }
}
