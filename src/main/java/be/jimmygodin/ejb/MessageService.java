package be.jimmygodin.ejb;

import be.jimmygodin.entity.Message;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class MessageService implements MessageServiceLocal {
    @EJB
    private MessageRepositoryLocal messageRepositoryLocal;

    @Override
    public long addMessage(Message message) {
        return messageRepositoryLocal.addMessage(message);
    }

    @Override
    public Optional<Message> getMessageById(long id) {
        return messageRepositoryLocal.getMessageById(id);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepositoryLocal.getAllMessages();
    }
}
