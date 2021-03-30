# MongoClient (Spring Data MongoDB)

## DOC. [MongoOperators](https://docs.mongodb.com/manual/reference/operator/)
Examples: $set, $lt...

## How to manage MongoClient:
1. Get MongoClient object (connection).
2. Get database from MongoClient.
3. Get collection from database.
4. Perform operation on Collection.
```java
@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    BooksService booksService;

    @GetMapping("/")
    public List<Object> getAllBooks() {
        return booksService.getAllBooks();
    }

    @PostMapping("/")
    public String addBooks(@RequestBody Map<String, Object> book) {
        return booksService.addBooks(book);
    }

    @PutMapping("/")
    public String update(@RequestBody Map<String, Object> book) {
        return booksService.update(book);
    }

    @DeleteMapping("/{id}")
    public String delete(@RequestParam String id) {
        return booksService.delete(id);
    }
}
```

```java
@Service
public class BooksService {

    @Autowired
    BooksRepository repository;

    public List<Object> getAllBooks() {
        return repository.getAllBooks();
    }

    public String addBooks(Map<String, Object> book) {
        Document doc = new Document(book);
        return repository.add(doc);
    }

    public String update(Map<String, Object> book) {
        Document doc = new Document(book);
        String id = doc.getString("id");
        doc.remove("id");
        return repository.update(doc, id);
    }

    public String delete(String id) {
        return repository.delete(id);
    }
}
```

```java
@Repository
public class BooksRepository {

    MongoClient mongoClient;

    MongoClient getMongoClient() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
        }
        return mongoClient;
    }

    public List<Object> getAllBooks() {
        MongoClient mongoClient = getMongoClient(); // 1
        MongoDatabase database = mongoClient.getDatabase("test"); // 2
        MongoCollection<Document> collection = database.getCollection("books"); // 3
        FindIterable<Document> findIterable = collection.find(); // 4
        
        List<Object> booksResponse = new ArrayList<>();
        for (Document doc : findIterable) {
            booksResponse.add(doc);
        }
        return booksResponse;
    }

    public String add(Document doc) {
        MongoClient mongoClient = getMongoClient(); // 1
        MongoDatabase database = mongoClient.getDatabase("test"); // 2
        MongoCollection<Document> collection = database.getCollection("books"); // 3

        try {
            collection.insertOne(doc); // 4
            return "Successfully inserted";
        } catch (Exception e) {
            /* some exception */
        }
        return "Successfully Not inserted, Please try again";
    }

    public String update(Document doc, String id) {
        MongoClient mongoClient = getMongoClient(); // 1
        MongoDatabase database = mongoClient.getDatabase("test"); // 2
        MongoCollection<Document> collection = database.getCollection("books"); // 3

        BasicDBObject filter = new BasicDBObject("_id", id);
        BasicDBObject update = new BasicDBObject("$set", doc);
        try {
            collection.updateOne(filter, update); // 4
            return "Successfully updated!";
        } catch (Exception e) {
            /* some exception */
        }
        return "Error updating, Please try again later";
    }

    public String delete(String id) {
        MongoClient mongoClient = getMongoClient(); // 1
        MongoDatabase database = mongoClient.getDatabase("test"); // 2
        MongoCollection<Document> collection = database.getCollection("books"); // 3

        BasicDBObject filter = new BasicDBObject("_id", id);
        try {
            collection.deleteOne(filter); // 4
            return "Successfully deleted!";
        } catch (Exception e) {
            /* some exception */
        }
        return "Error deleting, Please try again later";
    }
}
```


