package htwberlin.backend.repository;

import htwberlin.backend.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findUserEntityByEmailAndUsername(String email, String username);
    UserEntity findUserEntityByEmail(String email);
    UserEntity findUserEntityByUsername(String username); // Neue Methode zur Überprüfung des Benutzernamens
}

