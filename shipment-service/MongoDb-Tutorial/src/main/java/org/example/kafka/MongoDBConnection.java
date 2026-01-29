package org.example.kafka;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Indexes.ascending;
import static com.mongodb.client.model.Indexes.descending;

public class MongoDBConnection {
    public static void main(String[] args) {
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.
                        getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient mongoClient = MongoClients.
                create(MongoClientSettings.builder().
                                          applyConnectionString(
                                                  new ConnectionString("mongodb://localhost:27017"
                                                  )).codecRegistry(codecRegistry).
                                          build());

        MongoDatabase database = mongoClient.getDatabase("mongo_db");
        System.out.println("Connected to database." + database.getName());
        MongoCollection<Document> document = database.getCollection("users");
        //document.find().forEach(System.out::println);
        document.createIndex(Indexes.compoundIndex(ascending("age"), descending("email")));
        // document.find(eq("email", "rm@example.com")).forEach(System.out::println);
        document.find(and(eq("age", 28), eq("email", "rm@example.com")))
                .forEach(System.out::println);
        mongoClient.close();
    }


}
