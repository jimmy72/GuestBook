package be.jimmygodin.ejb;

import be.jimmygodin.entity.Message;
import be.jimmygodin.entity.MessageList;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Singleton
public class MessageRepository implements MessageRepositoryLocal {
    @PersistenceContext(unitName = "GuestBook")
    private EntityManager entityManager;

    @Override
    public Message addMessage(Message message) {
        entityManager.persist(message);
        entityManager.flush();
        return message;
    }

    @Override
    public Optional<Message> getMessageById(long id) {
        return Optional.ofNullable(entityManager.find(Message.class, id));
    }

    @Override
    public List<Message> getAllMessages() {
        TypedQuery<Message> query = entityManager.createNamedQuery("findAllMessages", Message.class);
        return query.getResultList();
    }

    @Override
    public Message updateMessage(Message message) {
        entityManager.merge(message);
        return message;
    }
}
