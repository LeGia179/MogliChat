package htwberlin.backend.service;

import htwberlin.backend.WebSocketTransfer.MessageTransfer;
import htwberlin.backend.WebSocketTransfer.UserTransfer;
import htwberlin.backend.Entity.Message;
import htwberlin.backend.Entity.Textchannel;
import htwberlin.backend.Entity.User;
import htwberlin.backend.repository.ChatMessageRepository;

import htwberlin.backend.repository.MultichannelRepository;
import htwberlin.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MultichannelRepository multichannelRepository;
    final static DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public Message createMessage(String userId, String channelId, String content){
        Message message = new Message();
        User user = userRepository.findUserById(userId);
        Textchannel textchannel = multichannelRepository.findTextchannelById(channelId);
        message.setId(UUID.randomUUID().toString().substring(0,5));
        message.setSender(user);
        message.setContent(content);
        message.setTextchannel(textchannel);
        LocalDateTime ldt = LocalDateTime.now();
        String formattedString = ldt.format(CUSTOM_FORMATTER);
        message.setDate(formattedString);
        user.getMessages().add(message);
        return messageRepository.save(message);
    }
    public void deleteAllMessages() {
        messageRepository.deleteAll();
    }

    public List<Message> getMessagesByTextchannelAndUser(String textchannelId, String userId){
        return messageRepository.findMessagesByTextchannelIdAndSenderId(textchannelId, userId);
    }

    public List<MessageTransfer> getMessagesByTextchannel(String channelId){
        Textchannel textchannel = multichannelRepository.findTextchannelById(channelId);
        List<MessageTransfer> messageTransfers = new ArrayList<>();
        for (Message message : textchannel.getMessages()) {
            MessageTransfer messageTransfer = new MessageTransfer();
            UserTransfer userTransfer = new UserTransfer();
            userTransfer.setUsername(message.getSender().getUsername());
            messageTransfer.setSender(userTransfer);
            messageTransfer.setMessage(message.getContent());
            messageTransfer.setDate(message.getDate());
            messageTransfers.add(messageTransfer);
        }
        return messageTransfers;
    }

}
