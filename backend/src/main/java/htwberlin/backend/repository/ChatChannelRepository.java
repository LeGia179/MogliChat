package htwberlin.backend.repository;

import htwberlin.backend.Entity.ChatChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatChannelRepository extends JpaRepository<ChatChannelEntity, String> {
}
