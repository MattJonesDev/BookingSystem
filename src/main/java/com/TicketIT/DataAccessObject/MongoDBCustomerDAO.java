package com.TicketIT.DataAccessObject;

import java.util.ArrayList;
import java.util.List;

import com.TicketIT.Converter.CustomerConverter;
import com.TicketIT.Model.Customer;
import com.mongodb.*;
import org.bson.types.ObjectId;

public class MongoDBCustomerDAO {

    private DBCollection collection;
    private final String DatabaseName = "ticketit";
    private final String CollectionName = "customer";

    public MongoDBCustomerDAO(MongoClient mongo){
        this.collection = mongo.getDB(DatabaseName).getCollection(CollectionName);
    }

    public Customer CreateCustomer(Customer customer) {
        DBObject doc = CustomerConverter.ToDatabaseObject(customer);
        this.collection.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        customer.setId(id.toString());
        return customer;
    }

    public Boolean DeleteCustomer(Customer customer) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(customer.getId())).get();
        this.collection.remove(query);
        return true;
    }

    public Boolean UpdateCustomer(Customer customer) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(customer.getId())).get();
        this.collection.update(query, CustomerConverter.ToDatabaseObject(customer));
        return true;
    }

    public Customer GetCustomer(Customer customer) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(customer.getId())).get();
        DBObject data = this.collection.findOne(query);
        return CustomerConverter.ToCustomerObject(data);
    }

    public Customer GetCustomerById(String id) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.collection.findOne(query);
        return CustomerConverter.ToCustomerObject(data);
    }

    public List<Customer> GetAllCustomers() {
        List<Customer> data = new ArrayList<>();
        DBCursor cursor = collection.find();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Customer customer = CustomerConverter.ToCustomerObject(doc);
            data.add(customer);
        }

        return data;
    }
}
