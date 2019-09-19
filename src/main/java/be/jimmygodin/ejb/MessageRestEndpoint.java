package be.jimmygodin.ejb;

import be.jimmygodin.entity.Message;
import be.jimmygodin.entity.MessageList;
import org.apache.commons.lang3.StringUtils;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Stateless
@Path("messages")
public class MessageRestEndpoint {
    @EJB
    private MessageServiceLocal messageServiceLocal;

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getMessage(@PathParam("id") long id) {
        Optional<Message> message = messageServiceLocal.getMessageById(id);
        return message.isPresent()
                ? Response.ok(message.get()).build()
                : Response.status(Response.Status.NOT_FOUND).entity("").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllMessages(){
        List<Message> list = messageServiceLocal.getAllMessages();

        return Response.ok(new MessageList(list)).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addMessage(Message message, @Context UriInfo uriInfo) {
        if(message.getId() != 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Long id = messageServiceLocal.addMessage(message);
        URI uri = URI.create(uriInfo.getPath() + "/" + id);

        return Response.created(uri).entity("").build();
    }

    @POST
    @Path("/{name}/{message}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addMessage( @PathParam("name") String name, @PathParam("message") String message,
                                @Context UriInfo uriInfo  )    {
        if(StringUtils.isBlank(name) || StringUtils.isBlank(message)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Long id = messageServiceLocal.addMessage(new Message(name, message));
        URI uri = URI.create(uriInfo.getBaseUri() + "messages" +"/" + id);

        return Response.created(uri).entity("").build();
    }

}
