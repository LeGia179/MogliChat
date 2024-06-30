package htwberlin.backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    private String id;
    private String content;
    private String date;

    @ManyToOne
    private Textchannel textchannel;

    @ManyToOne
    private User sender;

    @ManyToOne
    private Directchannel directchannel;
}
