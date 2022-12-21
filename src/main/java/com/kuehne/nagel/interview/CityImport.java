package com.kuehne.nagel.interview;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

@Component
public class CityImport {

    @Autowired
    private MongoClient mongoClient;

    public void importCities() {
        MongoDatabase database = mongoClient.getDatabase("City");
        MongoCollection<Document> collection = database.getCollection("Cities");
        System.out.println(collection.countDocuments());

        // Check if the collection  is empty
        if (collection.countDocuments() == 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:cities.csv")))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Split the line into fields
                    String[] fields = line.split(",");
                    String id = fields[0];
                    String name = fields[1];
                    String photo = fields[2];
                    System.out.println(name);

                    // Create a document
                    Document document = new Document("id", id)
                            .append("name", name)
                            .append("photo", photo);

                    // Insert the document into the collection
                    collection.insertOne(document);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

