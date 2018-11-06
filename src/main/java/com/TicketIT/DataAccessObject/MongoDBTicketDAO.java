package com.TicketIT.DataAccessObject;

import java.util.ArrayList;
import java.util.List;
import com.TicketIT.Converter.TicketConverter;
import com.TicketIT.Model.Ticket;
import com.mongodb.*;
import org.bson.types.ObjectId;

public class MongoDBTicketDAO {

    private DBCollection collection;
    private final String DatabaseName = "ticketit";
    private final String CollectionName = "ticket";

    public MongoDBTicketDAO(MongoClient mongo){
        this.collection = mongo.getDB(DatabaseName).getCollection(CollectionName);
    }

    public Ticket CreateTicket(Ticket ticket) {
        DBObject doc = TicketConverter.ToDatabaseObject(ticket);
        this.collection.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        ticket.setId(id.toString());
        return ticket;
    }

    public Boolean DeleteTicket(Ticket ticket) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(ticket.getId())).get();
        this.collection.remove(query);
        return true;
    }

    public Boolean UpdateTicket(Ticket ticket) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(ticket.getId())).get();
        this.collection.update(query, TicketConverter.ToDatabaseObject(ticket));
        return true;
    }

    public Ticket GetTicket(Ticket ticket) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(ticket.getId())).get();
        DBObject data = this.collection.findOne(query);
        return TicketConverter.ToTicketObject(data);
    }

    public Ticket GetTicketById(String id) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.collection.findOne(query);
        return TicketConverter.ToTicketObject(data);
    }

    public List<Ticket> GetAllTickets() {
        List<Ticket> data = new ArrayList<>();
        DBCursor cursor = collection.find();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Ticket ticket = TicketConverter.ToTicketObject(doc);
            data.add(ticket);
        }

        return data;
    }
}
