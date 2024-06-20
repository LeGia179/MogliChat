package htwberlin.backend.repository;

import htwberlin.backend.Entity.ChatChannelEntity;
import htwberlin.backend.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatChannelRepository extends JpaRepository<ChatChannelEntity, String> {
    List<ChatChannelEntity> findChatChannelEntitiesByUsersContaining(UserEntity user);
}