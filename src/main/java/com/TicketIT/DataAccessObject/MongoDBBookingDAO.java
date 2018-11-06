package com.TicketIT.DataAccessObject;

import java.util.ArrayList;
import java.util.List;

import com.TicketIT.Converter.BookingConverter;
import com.TicketIT.Model.Booking;
import com.mongodb.*;
import org.bson.types.ObjectId;

public class MongoDBBookingDAO {

    private DBCollection collection;
    private final String DatabaseName = "ticketit";
    private final String CollectionName = "booking";

    public MongoDBBookingDAO(MongoClient mongo){
        this.collection = mongo.getDB(DatabaseName).getCollection(CollectionName);
    }

    public Booking CreateBooking(Booking booking) {
        DBObject doc = BookingConverter.ToDatabaseObject(booking);
        this.collection.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        booking.setId(id.toString());
        return booking;
    }

    public Boolean DeleteBooking(Booking booking) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(booking.getId())).get();
        this.collection.remove(query);
        return true;
    }

    public Boolean UpdateBooking(Booking booking) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(booking.getId())).get();
        this.collection.update(query, BookingConverter.ToDatabaseObject(booking));
        return true;
    }

    public Booking GetBooking(Booking booking) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(booking.getId())).get();
        DBObject data = this.collection.findOne(query);
        return BookingConverter.ToBookingObject(data);
    }

    public Booking GetBookingById(String id) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.collection.findOne(query);
        return BookingConverter.ToBookingObject(data);
    }

    public List<Booking> GetAllBookings() {
        List<Booking> data = new ArrayList<>();
        DBCursor cursor = collection.find();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Booking booking = BookingConverter.ToBookingObject(doc);
            data.add(booking);
        }

        return data;
    }
}
