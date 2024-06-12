package htwberlin.backend.repository;

import htwberlin.backend.Entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Repo für Interaktionen mit Nachrichten-DB ohne spezielle SQL-Abfragen
//CRUD-Operationen (erstellen, lesen/filtern, aktualisieren, löschen)
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity,String> {

}
