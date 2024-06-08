package htwberlin.backend.repository;

import htwberlin.backend.Entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Benutze ich um etwas zu finden
//SQL filter abfrage um etwas zu suchen
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity,String> {

}
