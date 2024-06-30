package htwberlin.backend.repository;

import htwberlin.backend.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<Message, String> {
    List<Message> findMessagesByTextchannelIdAndSenderId(String textchannelId, String userId);
    void deleteById(String id);
}
