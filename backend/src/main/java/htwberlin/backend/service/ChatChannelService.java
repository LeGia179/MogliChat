package htwberlin.backend.service;

import htwberlin.backend.Entity.ChatChannelEntity;
import htwberlin.backend.Exception.ChannelNotFoundException;
import htwberlin.backend.repository.ChatChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatChannelService {
    private final ChatChannelRepository chatChannelRepository;

    public ChatChannelEntity createChannel(String name) {
        ChatChannelEntity channel = new ChatChannelEntity();
        channel.setName(name);
        channel.setId(UUID.randomUUID().toString());
        return chatChannelRepository.save(channel);
    }

    public List<ChatChannelEntity> getAllChannels() {
        return chatChannelRepository.findAll();
    }
    public ChatChannelEntity getChannel(String id) {
        return chatChannelRepository.findById(id).orElseThrow(() -> new ChannelNotFoundException("Channel not found"));
    }
}
