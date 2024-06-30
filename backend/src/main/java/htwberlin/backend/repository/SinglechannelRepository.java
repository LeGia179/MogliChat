package htwberlin.backend.repository;

import htwberlin.backend.Entity.Directchannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SinglechannelRepository extends JpaRepository<Directchannel, String> {
}
