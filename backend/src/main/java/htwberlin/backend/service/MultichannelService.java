package htwberlin.backend.service;

import htwberlin.backend.Entity.Message;
import htwberlin.backend.Entity.Textchannel;
import htwberlin.backend.Entity.User;
import htwberlin.backend.repository.ChatMessageRepository;
import htwberlin.backend.repository.MultichannelRepository;
import htwberlin.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MultichannelService {
    private final MultichannelRepository multichannelRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository messageRepository;

    public Textchannel createTextchannel(String name, String description, String userId){
        Textchannel findTextchannel = multichannelRepository.findTextchannelByName(name.replace(" ", "-"));
        if(findTextchannel != null) {
            return null;
        }
        Textchannel textchannel = new Textchannel();
        textchannel.setId(UUID.randomUUID().toString().substring(0,5));
        textchannel.setName(name.replace(" ", "-"));
        textchannel.setDescription(description);
        textchannel.setCreatorId(userId);
        User user = userRepository.findUserById(userId);
        user.getTextchannels().add(textchannel);
        return multichannelRepository.save(textchannel);
    }

    public void addUserToTextchannel(String userId, String channelId){
        User user = userRepository.findUserById(userId);
        Textchannel textchannel = multichannelRepository.findTextchannelById(channelId);

        textchannel.getUsers().add(user);
        multichannelRepository.save(textchannel);
    }

    public void addMessageToTextchannel(Message message, String channelId){
        Textchannel textchannel = multichannelRepository.findTextchannelById(channelId);

        textchannel.getMessages().add(message);
        multichannelRepository.save(textchannel);
    }

    public List<Textchannel> getAllTextchannelsByUserId(String userId){
        return multichannelRepository.findTextchannelsByUsersId(userId);
    }

    public List<User> getUsersOfTextChannel(String textChannelId) {
        Textchannel textchannel = multichannelRepository.findTextchannelById(textChannelId);
        List<User> users = new ArrayList<>();
        for (User user : textchannel.getUsers()) {
            user.setMessages(null);
            user.setTextchannels(null);
            users.add(user);
        }
        return textchannel.getUsers();
    }
    public Textchannel getTextchannelById(String textChannelId) {
        return multichannelRepository.findTextchannelById(textChannelId);
    }
    public Textchannel getTextchannelByName(String name) {
        return multichannelRepository.findTextchannelByName(name.replace(" ", "-"));
    }

    public void addUserToChannelByName(String channelName, String userId){
        User user = userRepository.findUserById(userId);
        Textchannel textchannel = multichannelRepository.findTextchannelByName(channelName.replace(" ", "-"));

        textchannel.getUsers().add(user);
        multichannelRepository.save(textchannel);
    }
    public void removeUserFromTextchannel(String userId, String channelId) {
        User user = userRepository.findUserById(userId);
        Textchannel textchannel = multichannelRepository.findTextchannelById(channelId);
        user.getTextchannels().remove(textchannel);
        textchannel.getUsers().remove(user);
        userRepository.save(user);
        multichannelRepository.save(textchannel);
    }

    public void deleteAllMessagesFromTextchannels() {
        List<Textchannel> allTextchannels = multichannelRepository.findAll();
        for (Textchannel textchannel : allTextchannels) {
            textchannel.getMessages().clear();
            multichannelRepository.save(textchannel);
        }
    }
    public void deleteAllTextchannels() {
        multichannelRepository.deleteAll();
    }

    public void deleteTextchannelByName(String name) {
        Textchannel textchannel = multichannelRepository.findTextchannelByName(name.replace(" ", "-"));
        textchannel.getUsers().forEach(user -> {
            Iterator<Message> iterator = user.getMessages().iterator();
            while (iterator.hasNext()) {
                Message message = iterator.next();
                if (message.getTextchannel().getId().equals(textchannel.getId())) {
                    iterator.remove();
                }
            }
        });
        textchannel.getUsers().forEach(user -> user.getTextchannels().remove(textchannel));
        if(!textchannel.getMessages().isEmpty()){
            for (int i = textchannel.getMessages().size() - 1; i >= 0; i--) {
                String messageId = textchannel.getMessages().get(i).getId();
                textchannel.getMessages().remove(i);
                messageRepository.deleteById(messageId);
            }
        }
        textchannel.getUsers().clear();
        multichannelRepository.delete(textchannel);
    }
}
