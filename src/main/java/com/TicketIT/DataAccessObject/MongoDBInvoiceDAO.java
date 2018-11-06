package com.TicketIT.DataAccessObject;

import java.util.ArrayList;
import java.util.List;
import com.TicketIT.Converter.InvoiceConverter;
import com.TicketIT.Model.Invoice;
import com.mongodb.*;
import org.bson.types.ObjectId;

public class MongoDBInvoiceDAO {

    private DBCollection collection;
    private final String DatabaseName = "ticketit";
    private final String CollectionName = "invoice";

    public MongoDBInvoiceDAO(MongoClient mongo){
        this.collection = mongo.getDB(DatabaseName).getCollection(CollectionName);
    }

    public Invoice CreateInvoice(Invoice invoice) {
        DBObject doc = InvoiceConverter.ToDatabaseObject(invoice);
        this.collection.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        invoice.setId(id.toString());
        return invoice;
    }

    public Boolean DeleteInvoice(Invoice invoice) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(invoice.getId())).get();
        this.collection.remove(query);
        return true;
    }

    public Boolean UpdateInvoice(Invoice invoice) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(invoice.getId())).get();
        this.collection.update(query, InvoiceConverter.ToDatabaseObject(invoice));
        return true;
    }

    public Invoice GetInvoice(Invoice invoice) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(invoice.getId())).get();
        DBObject data = this.collection.findOne(query);
        return InvoiceConverter.ToInvoiceObject(data);
    }

    public Invoice GetInvoiceById(String id) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.collection.findOne(query);
        return InvoiceConverter.ToInvoiceObject(data);
    }

    public List<Invoice> GetAllInvoices() {
        List<Invoice> data = new ArrayList<>();
        DBCursor cursor = collection.find();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Invoice invoice = InvoiceConverter.ToInvoiceObject(doc);
            data.add(invoice);
        }

        return data;
    }
}
