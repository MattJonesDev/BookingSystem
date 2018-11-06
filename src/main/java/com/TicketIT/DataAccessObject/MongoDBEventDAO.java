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

    public MongoDBEventDAO(MongoClient mongo){
        this.collection = mongo.getDB(DatabaseName).getCollection(CollectionName);
    }

    public Event CreateEvent(Event event) {
        DBObject doc = EventConverter.ToDatabaseObject(event);
        this.collection.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        event.setId(id.toString());
        return event;
    }

    public Boolean DeleteEvent(Event event) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(event.getId())).get();
        this.collection.remove(query);
        return true;
    }

    public Boolean UpdateEvent(Event event) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(event.getId())).get();
        this.collection.update(query, EventConverter.ToDatabaseObject(event));
        return true;
    }

    public Event GetEvent(Event event) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(event.getId())).get();
        DBObject data = this.collection.findOne(query);
        return EventConverter.ToEventObject(data);
    }

    public Event GetEventById(String id) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.collection.findOne(query);
        return EventConverter.ToEventObject(data);
    }

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
