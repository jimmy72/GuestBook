package be.jimmygodin.ejb;

import be.jimmygodin.entity.Message;
import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface MessageServiceLocal {
    public Message addMessage(Message message);
    public Optional<Message> getMessageById(long id);
    public List<Message> getAllMessages();
    public Message updateMessage(Message message);
}
