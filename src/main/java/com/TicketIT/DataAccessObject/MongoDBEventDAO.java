package com.TicketIT.DataAccessObject;

import java.util.ArrayList;
import java.util.List;
import com.TicketIT.Converter.EventConverter;
import com.TicketIT.Model.Event;
import com.mongodb.*;
import org.bson.types.ObjectId;

public class MongoDBEventDAO {

    private DBCollection collection;
    private final String DatabaseName = "ticketit";
    private final String CollectionName = "event";

    /**
     * Event Data Access Object constructor.
     */
    public MongoDBEventDAO(MongoClient mongo){
        this.collection = mongo.getDB(DatabaseName).getCollection(CollectionName);
    }

    /**
     * Creates a Event in the database.
     *
     * @param event A event object to create in MongoDB.
     * @return The event object created in MongoDB.
     */
    public Event CreateEvent(Event event) {
        DBObject doc = EventConverter.ToDatabaseObject(event);
        this.collection.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        event.setId(id.toString());
        return event;
    }

    /**
     * Deletes a event in the database.
     *
     * @param event The event object to delete in the database.
     */
    public Boolean DeleteEvent(Event event) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(event.getId())).get();
        this.collection.remove(query);
        return true;
    }

    /**
     * Updates a event in the database.
     *
     * @param event The event object to update in the database.
     */
    public Boolean UpdateEvent(Event event) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(event.getId())).get();
        this.collection.update(query, EventConverter.ToDatabaseObject(event));
        return true;
    }

    /**
     * Gets the given event from the database.
     *
     * @param event The event object to retrieve.
     * @return The booking object from MongoDB.
     */
    public Event GetEvent(Event event) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(event.getId())).get();
        DBObject data = this.collection.findOne(query);
        return EventConverter.ToEventObject(data);
    }

    /**
     * Gets the event for the given event Id from the database.
     *
     * @param id The event Id of the event object to retrieve.
     * @return The event object from MongoDB.
     */
    public Event GetEventById(String id) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.collection.findOne(query);
        return EventConverter.ToEventObject(data);
    }

    /**
     * Gets all events from the database.
     *
     * @return All the events from MongoDB.
     */
    public List<Event> GetAllEvents() {
        List<Event> data = new ArrayList<>();
        DBCursor cursor = collection.find();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Event event = EventConverter.ToEventObject(doc);
            data.add(event);
        }

        return data;
    }
}
