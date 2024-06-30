package htwberlin.backend.repository;

import htwberlin.backend.Entity.Textchannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultichannelRepository extends JpaRepository<Textchannel, String> {
    Textchannel findTextchannelById(String id);
    List<Textchannel> findTextchannelsByUsersId(String userId);
    Textchannel findTextchannelByName(String name);
}
