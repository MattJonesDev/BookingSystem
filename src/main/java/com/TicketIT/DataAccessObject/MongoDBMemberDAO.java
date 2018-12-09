package com.TicketIT.DataAccessObject;

import com.TicketIT.Converter.MemberConverter;
import com.TicketIT.Model.Member;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class MongoDBMemberDAO {

    private DBCollection collection;
    private final String DatabaseName = "ticketit";
    private final String CollectionName = "member";

    public MongoDBMemberDAO(MongoClient mongo){
        this.collection = mongo.getDB(DatabaseName).getCollection(CollectionName);
    }

    public Member CreateMember(Member Member) {
        DBObject doc = MemberConverter.ToDatabaseObject(Member);
        this.collection.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        Member.setId(id.toString());
        return Member;
    }

    public Boolean DeleteMember(Member Member) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(Member.getId())).get();
        this.collection.remove(query);
        return true;
    }

    public Boolean UpdateMember(Member Member) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(Member.getId())).get();
        this.collection.update(query, MemberConverter.ToDatabaseObject(Member));
        return true;
    }

    public Member GetMember(Member Member) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(Member.getId())).get();
        DBObject data = this.collection.findOne(query);
        return MemberConverter.ToMemberObject(data);
    }

    public Member GetMemberById(String id) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.collection.findOne(query);
        return MemberConverter.ToMemberObject(data);
    }

    public Member GetMemberByEmail(String email) {
        DBObject query = BasicDBObjectBuilder.start().append("email", email).get();
        DBObject data = this.collection.findOne(query);
        return MemberConverter.ToMemberObject(data);
    }

    public Boolean DoesMemberExist(String email) {
        DBObject query = BasicDBObjectBuilder.start().append("email", email).get();
        DBObject data = this.collection.findOne(query);
        if(data != null)
            return true;
        return false;
    }

    public List<Member> GetAllMembers() {
        List<Member> data = new ArrayList<>();
        DBCursor cursor = collection.find();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Member Member = MemberConverter.ToMemberObject(doc);
            data.add(Member);
        }

        return data;
    }
}
