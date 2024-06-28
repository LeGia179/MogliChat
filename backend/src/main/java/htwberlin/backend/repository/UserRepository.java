package htwberlin.backend.repository;


import htwberlin.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    User findUserById(String id);
    User findUserByEmail(String email);
    void deleteUserByEmail(String email);
    void deleteUserById(String id);
}
