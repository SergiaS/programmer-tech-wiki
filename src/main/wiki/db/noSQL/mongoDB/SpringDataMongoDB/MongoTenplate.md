# MongoTemplate
Это аналог RestTemplate, JdbcTemplate, EntityManager... только для MongoDB.

<details>
<summary>Open Menu</summary>

- [Facts](#facts)
- [MongoTemplate with Aggregation, Projection and Dynamic APIs](#-1-mongotemplate-with-aggregation-projection-and-dynamic-apis)
  - [Paging and Sorting, Projection with MongoTemplate](#paging-and-sorting-projection-with-mongotemplate)
  - [Adding Aggregation](#adding-aggregation)
  - [GitHub Examples](#github-examples)
  
</details>

## Facts
> 1. **MongoTemplate is from Spring Data Mongo not from Driver.** You need use dependency `spring-data-mongodb`.

> 2. MongoTemplate doesn't support Paging and Sorting by default, but there is a trick. [Example](#paging-and-sorting-projection-with-mongotemplate)

> 3. Once configured, MongoTemplate is thread-safe and can be reused across multiple instances.
> __[SOURCE - docs.spring.io](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo-template)__


## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://youtu.be/Nfb67sEYDek?t=139">MongoTemplate with Aggregation, Projection and Dynamic APIs</a>
All courses by JR ACADEMY you can see at [YOUTUBE](https://www.youtube.com/channel/UC2J07tM000x5WKburzZIPGw).

### Paging and Sorting, Projection with MongoTemplate
This video shows how to create Dynamic API including projection, paging and sorting.

**Final result of the video without aggregation:**
```java
// controller
@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    BooksService booksService;

    @GetMapping("/")
    public List<Document> getAllBooks() {
        return booksService.getAllBooks();
    }

    @PostMapping("/")
    public Document addBooks(@RequestBody Map<String, Object> book) {
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

    @GetMapping("/page")
    public Map<String, Object> getAllBooksByPage(
            @RequestParam(value = "pageno", defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "fields", defaultValue = "title, pageCount") String[] fields,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
        return booksService.getAllBooksByPage(pageNo, pageSize, fields, sortBy);
    }
}
```
```java
// service
@Service
public class BooksService {

    @Autowired
    BooksRepository repository;

    public List<Document> getAllBooks() {
        return repository.getAllBooks();
    }

    public Document addBooks(Map<String, Object> book) {
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

    public Map<String, Object> getAllBooksByPage(
            int pageNo, int pageSize, String[] fields, String sortBy) {
        return repository.getAllBooksByPage(pageNo, pageSize, fields, sortBy);
    }
}
```
```java
// repository
@Repository
public class BooksRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Document> getAllBooks() {
        return mongoTemplate.findAll(Document.class, "books");
    }

    public Document add(Document doc) {
        return mongoTemplate.insert(doc, "books");
    }

    public String update(Document doc, String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new BasicUpdate(doc);
        UpdateResult ur = mongoTemplate.updateFirst(query, update, "books");
        return "Updated Documents " + ur.getModifiedCount();
    }

    public String delete(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        DeleteResult dr = mongoTemplate.remove(query, "books");
        return "No. of Deleted documents: " + dr.getDeletedCount();
    }

    public Map<String, Object> getAllBooksByPage(
            int pageNo, int pageSize, String[] fields, String sortBy) {
        Query query = new Query();
        for (String field : fields) { // this query ready for projection after iteration
            query.fields().include(field);
        }
        Sort sort = Sort.by(sortBy); // doing sorting
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort); // doing Pagination
        query.with(pageable);

        List<Document> books = mongoTemplate.find(query, Document.class, "books");
        Page<Document> docPage = PageableExecutionUtils.getPage(
                books, pageable, () -> mongoTemplate.count(query, "books"));

        Map<String, Object> response = new HashMap<>();
        response.put("data", docPage.getContent());
        response.put("Total No. of Pages", docPage.getTotalPages());
        response.put("Current Page", pageNo);
        return response;
    }
```

### Adding Aggregation
Aggregation is very easy with MongoTemplate.

Пример JSON-запроса в MongoDBCompass. Подсчет суммы всех `pageCount` каждого объекта.
```json
{
    $group{
        _id: null,
        count: {
            $sum: "$pageCount"
        }
    }
}
```
Аналог JSON-запроса на Java с использованием MongoTemplate:
```java
// repository
public Map<String, Object> countPage() {
    Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.group().sum("$pageCount").as("count"));
    return mongoTemplate.aggregate(aggregation, "books", Document.class).getRawResults();
}
```

### GitHub Examples
All code from course you can look at [GitHub](https://github.com/SergiaS/testMongo/tree/MongoTemplate_with_Aggregation%2C_Projection_and_Dynamic_APIs/src/main/java/com/mongo/testmongo).
