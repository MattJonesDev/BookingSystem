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

    /**
     * Ticket Data Access Object constructor.
     */
    public MongoDBTicketDAO(MongoClient mongo){
        this.collection = mongo.getDB(DatabaseName).getCollection(CollectionName);
    }

    /**
     * Creates a Ticket in the database.
     *
     * @param ticket A ticket object to create in MongoDB.
     * @return The ticket object created in MongoDB.
     */
    public Ticket CreateTicket(Ticket ticket) {
        DBObject doc = TicketConverter.ToDatabaseObject(ticket);
        this.collection.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        ticket.setId(id.toString());
        return ticket;
    }

    /**
     * Deletes a ticket in the database.
     *
     * @param ticket The ticket object to delete in the database.
     */
    public Boolean DeleteTicket(Ticket ticket) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(ticket.getId())).get();
        this.collection.remove(query);
        return true;
    }

    /**
     * Updates a ticket in the database.
     *
     * @param ticket The ticket object to update in the database.
     */
    public Boolean UpdateTicket(Ticket ticket) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(ticket.getId())).get();
        this.collection.update(query, TicketConverter.ToDatabaseObject(ticket));
        return true;
    }

    /**
     * Gets the given ticket from the database.
     *
     * @param ticket The ticket object to retrieve.
     * @return The ticket object from MongoDB.
     */
    public Ticket GetTicket(Ticket ticket) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(ticket.getId())).get();
        DBObject data = this.collection.findOne(query);
        return TicketConverter.ToTicketObject(data);
    }

    /**
     * Gets the ticket for the given ticket Id from the database.
     *
     * @param id The ticket Id of the ticket object to retrieve.
     * @return The ticket object from MongoDB.
     */
    public Ticket GetTicketById(String id) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.collection.findOne(query);
        return TicketConverter.ToTicketObject(data);
    }

    /**
     * Gets all tickets from the database.
     *
     * @return All the tickets from MongoDB.
     */
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
