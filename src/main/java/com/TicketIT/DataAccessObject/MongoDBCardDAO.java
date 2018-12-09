package com.TicketIT.DataAccessObject;

import java.util.ArrayList;
import java.util.List;
import com.TicketIT.Converter.CardConverter;
import com.TicketIT.Model.Card;
import com.mongodb.*;
import org.bson.types.ObjectId;

public class MongoDBCardDAO {

    private DBCollection collection;
    private final String DatabaseName = "ticketit";
    private final String CollectionName = "card";

    public MongoDBCardDAO(MongoClient mongo){
        this.collection = mongo.getDB(DatabaseName).getCollection(CollectionName);
    }

    public Card CreateCard(Card card) {
        DBObject doc = CardConverter.ToDatabaseObject(card);
        this.collection.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        card.setId(id.toString());
        return card;
    }

    public Boolean DeleteCard(Card card) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(card.getId())).get();
        this.collection.remove(query);
        return true;
    }

    public Boolean UpdateCard(Card card) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(card.getId())).get();
        this.collection.update(query, CardConverter.ToDatabaseObject(card));
        return true;
    }

    public Card GetCard(Card card) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(card.getId())).get();
        DBObject data = this.collection.findOne(query);
        return CardConverter.ToCardObject(data);
    }

    public Card GetCardById(String id) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.collection.findOne(query);
        return CardConverter.ToCardObject(data);
    }

    public Card GetCardByNumber(String cardNumber) {
        DBObject query = BasicDBObjectBuilder.start().append("number", cardNumber).get();
        DBObject data = this.collection.findOne(query);
        return CardConverter.ToCardObject(data);
    }

    public Boolean DoesCardExist(String cardNumber) {
        DBObject query = BasicDBObjectBuilder.start().append("number", cardNumber).get();
        DBObject data = this.collection.findOne(query);
        if(data != null)
            return true;
        return false;
    }

    public List<Card> GetAllCards() {
        List<Card> data = new ArrayList<>();
        DBCursor cursor = collection.find();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Card card = CardConverter.ToCardObject(doc);
            data.add(card);
        }

        return data;
    }

}
