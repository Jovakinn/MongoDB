package info.sjd.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import info.sjd.model.Product;
import org.bson.Document;
import org.bson.types.Decimal128;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public static void save(Product product) {
        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoDatabase db = mongo.getDatabase("local");
        MongoCollection collection = db.getCollection("products");

        Document dbObject = new Document();

        dbObject.put("article", product.getArticle());
        dbObject.put("name", product.getName());
        dbObject.put("price", product.getPrice());

        collection.insertOne(dbObject);

        mongo.close();
    }

    public static List<Product> getAll() {
        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoDatabase db = mongo.getDatabase("local");
        MongoCollection collection = db.getCollection("products");

        List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

        List<Product> products = new ArrayList<>();

        for (Document document : documents) {
            Product product = new Product();

            product.setArticle(document.getString("article"));
            product.setPrice(new BigDecimal(((Decimal128) document.get("price")).doubleValue()));
            product.setName(document.getString("name"));

            products.add(product);

        }
        mongo.close();

        return products;
    }

}