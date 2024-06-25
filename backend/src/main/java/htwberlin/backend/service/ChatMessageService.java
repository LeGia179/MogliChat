package htwberlin.backend.service;

import htwberlin.backend.Entity.ChatMessageEntity;
import htwberlin.backend.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


import java.time.LocalDateTime;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public void saveChatMessage(String userName, String message) {
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        chatMessageEntity.setUserName(userName);
        chatMessageEntity.setMessage(message);
        LocalDateTime now = LocalDateTime.now();
        chatMessageEntity.setTimestamp(now);
        chatMessageEntity.setId(UUID.randomUUID().toString());
        chatMessageRepository.save(chatMessageEntity);
        //chatMessageRepository.deleteAll();
    }

    //speichert eine Chatnachricht, die als ChatMessageEntity übergeben wird
    public void saveMessage(ChatMessageEntity chatMessageEntity) {
        if (chatMessageEntity.getId() == null) {
            chatMessageEntity.setId(UUID.randomUUID().toString());
        }
        if (chatMessageEntity.getTimestamp() == null) {
            chatMessageEntity.setTimestamp(LocalDateTime.now());
        }
        chatMessageRepository.save(chatMessageEntity);
    }
}
