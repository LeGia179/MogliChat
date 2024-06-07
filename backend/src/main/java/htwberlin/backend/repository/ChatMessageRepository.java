package htwberlin.backend.repository;

import htwberlin.backend.Entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Benutze ich um etwas zu finden
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity,String> {

}
