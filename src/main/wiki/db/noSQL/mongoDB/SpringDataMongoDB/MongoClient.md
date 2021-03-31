# MongoClient (Spring Data MongoDB)
***This information enought for creating Dynamic APIs.***

You can look all code at **[GitHub](https://github.com/SergiaS/testMongo/tree/MongoClient_with_Aggregation%2C_Projection%2C_and_Dynamic_APIs/src/main/java/com/mongo/testmongo)**.  

## DOC. [MongoOperators](https://docs.mongodb.com/manual/reference/operator/)
Examples: `$set`, `$lt`...


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

## Paging and Sorting, Projection with MongoClient
## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  <a href="https://youtu.be/pW1abAOXiMo?t=1300">Paging and Sorting example</a>
В следующем примере говорим чтобы все объекты в БД умещаются на 20 страниц, нам нужны поля `title, shortDescription, pageCount`, отсортировать позиции по `pageCount` и показать 19 страницу:    
`.../page?pageSize=20&fields=title,shortDescription,pageCount&sortBy=pageCount&pageno=19`
- `pageSize` - общее кол-во объектов в БД делится на указанное число.
- `sortBy` - сортировка по указанному полю, за который отвечает объект `new BasicDBObject(sortBy, 1)`.
- `pageno` - выводит список объектов соответствующей страницы, в коде это пропуск первых позиций по формуле `skip(pageNo * pageSize)`.
- `fields` - будет отображать только указанные поля, все остальные будут скрыты. Благодаря этому можно получать только те поля, которые нужны, и таким образом уменьшить размер JSON в разы!
- Дополнительно в ответе в мапе будут добавлены 2 поля с данными: `"No. Of Pages": 21.0` и `"No. Of Elements": 432`.

```java
// controller
@GetMapping("/page")
public Map<String, Object> getAllBooksByPage(
        @RequestParam(value = "pageno", defaultValue = "0") int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
        @RequestParam(value = "fields", defaultValue = "title, pageCount") String[] fields,
        @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
    return booksService.getAllBooksByPage(pageNo, pageSize, fields, sortBy);
}

// service
public Map<String, Object> getAllBooksByPage(int pageNo, int pageSize, String[] fields, String sortBy) {
    Map<String, Object> response = new HashMap<>();
    response.put("data", repository.getAllBooksByPage(pageNo, pageSize, fields, sortBy));
    long count = repository.countOfElements();
    response.put("No. Of Elements", count);
    response.put("No. Of Pages", Math.ceil(count / pageSize));
    return response;
}

// repository
public List<Object> getAllBooksByPage(int pageNo, int pageSize, String[] fields, String sortBy) {
    MongoClient mongoClient = getMongoClient();
    MongoDatabase database = mongoClient.getDatabase("test");
    MongoCollection<Document> collection = database.getCollection("books");

    BasicDBObject projection = new BasicDBObject("_id", 0); // skip id
    for (String field : fields) {
        projection.append(field, 1);
    }

    BasicDBObject sort = new BasicDBObject(sortBy, 1);

    FindIterable<Document> findIterable = collection.find()
            .projection(projection)
            .sort(sort)
            .skip(pageNo * pageSize)
            .limit(pageSize);

    List<Object> booksResponse = new ArrayList<>();
    for (Document doc : findIterable) {
        booksResponse.add(doc);
    }
    return booksResponse;
}

public long countOfElements() {
    MongoClient mongoClient = getMongoClient(); // 1
    MongoDatabase database = mongoClient.getDatabase("test"); // 2
    MongoCollection<Document> collection = database.getCollection("books"); // 3
    return collection.countDocuments(); // 4
}
```

## MongoClient with Aggregation
**Аггрегация** - объединение различных данных.
## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  <a href="https://youtu.be/pW1abAOXiMo?t=2167">Example</a>

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
Аналог JSON-запроса на Java:
```java
BasicDBObject sum = new BasicDBObject("$sum", "$pageCount");
BasicDBObject grp = new BasicDBObject();
grp.append("_id", null);
grp.append("count", sum);

List<BasicDBObject> pipeLine = new ArrayList<>();
pipeLine.add(group);
collection.aggregate(pipeLine);
```
Если сконвертировать объект `group` (т.е. Java код в JSON), то мы получим тот же JSON:
```java
// просим сгруппировать и отобразить в формате JSON
BasicDBObject group = new BasicDBObject("$group", grp);
System.out.println(group.toJson());

// отображается в консоли
{
    "$group": {
        "_id": null,
        "count": {
            "$sum": "$pageCount"
        }
    }
}
```

### Пример полной реализации аггрегации:
```java
// controller
@GetMapping("/countPage")
public Map<String, Object> countPage() {
    return booksService.countPage();
}

// service
public Map<String, Object> countPage() {
    Map<String, Object> response = new HashMap<>();
    response.put("Total No. Of pages", repository.countPage());
    return response;
}

// repository
public String countPage() {
    MongoClient mongoClient = getMongoClient();
    MongoDatabase database = mongoClient.getDatabase("test");
    MongoCollection<Document> collection = database.getCollection("books");

    BasicDBObject sum = new BasicDBObject("$sum", "$pageCount");
    BasicDBObject grp = new BasicDBObject();
    grp.append("_id", null);
    grp.append("count", sum);

    BasicDBObject group = new BasicDBObject("$group", grp);
    System.out.println(group.toJson());

    List<BasicDBObject> pipeLine = new ArrayList<>();
    pipeLine.add(group);
    AggregateIterable<Document> ans = collection.aggregate(pipeLine);

    return ans.first().get("count").toString();
}
```
В ответе мы получим сумму всех страниц `pageCount`:
```json
{
    "Total No. Of pages": "125087"
}
```

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  <a href="https://youtu.be/pW1abAOXiMo?t=2713">Filtering inside another object. Projection</a>
Пример, нужно вывести объекты, только в которых содержится объект(массив) с определенными значениями.

Запрос на JSON (тоже самое нужно получить на Java):
```json
{
    "categories": {
        $elemMatch: {
            $in: [
                "Mobile",
                "Java"
            ]
        }
    }
}
```
> **JSON-запрос выше проверяет содержит ли документ в своем массиве запрошенные данные.**
>
> - Говорим искать только в поле `categories`.
> - Оператор `$elemMatch` проверяет, что элемент `categories` является массивом.
> - Оператор `$in` проверяет содержит ли документ запрошенные данные.

```java
// repository
public List<Object> getByCategories(String[] categories) {
    MongoClient mongoClient = getMongoClient();
    MongoDatabase database = mongoClient.getDatabase("test");
    MongoCollection<Document> collection = database.getCollection("books");

    // нужно получить:
//  {"categories": {$elemMatch : {$in: ["Mobile", "Java"]}}}

    // выполняем сначала внутреннюю часть - {$in: ["Mobile", "Java"]}
    BasicDBObject in = new BasicDBObject("$in", categories);

    // выполняем внутреннюю часть на уровень выше - {$elemMatch : {$in: ["Mobile", "Java"]}}
    BasicDBObject elemMatch = new BasicDBObject("$elemMatch", in);

    // и еще на уровень выше - {"categories": {$elemMatch : {$in: ["Mobile", "Java"]}}
    BasicDBObject cate = new BasicDBObject("categories", elemMatch);

    // делаем запрос созданным JSON'ом
    FindIterable<Document> result = collection.find(cate);

    List<Object> response = new ArrayList<>();

    // собираем данные
    for (MongoCursor<Document> iterator = result.iterator(); iterator.hasNext(); ) {
        Document doc = iterator.next();
        response.add(doc);
    }
    return response;
}
```
If we need not all information from response, for example just `title`, than we need use `projection`.
```java
// repository
public List<Object> getByCategories(String[] categories) {
    MongoClient mongoClient = getMongoClient();
    MongoDatabase database = mongoClient.getDatabase("test");
    MongoCollection<Document> collection = database.getCollection("books");

    // нужно получить:
//  {"categories": {$elemMatch : {$in: ["Mobile", "Java"]}}}

    // выполняем сначала внутреннюю часть - {$in: ["Mobile", "Java"]}
    BasicDBObject in = new BasicDBObject("$in", categories);

    // выполняем внутреннюю часть на уровень выше - {$elemMatch : {$in: ["Mobile", "Java"]}}
    BasicDBObject elemMatch = new BasicDBObject("$elemMatch", in);

    // и еще на уровень выше - {"categories": {$elemMatch : {$in: ["Mobile", "Java"]}}
    BasicDBObject cate = new BasicDBObject("categories", elemMatch);

    // Projection - вкл. нужные/выкл. не нужные поля
    BasicDBObject projection = new BasicDBObject();
    projection.append("title", 1); // говорим нужно это поле
    projection.append("_id", 0); // а это нет

    // делаем запрос созданным JSON'ом
    FindIterable<Document> result = collection.find(cate).projection(projection);

    List<Object> response = new ArrayList<>();

    // собираем данные
    for (MongoCursor<Document> iterator = result.iterator(); iterator.hasNext(); ) {
        Document doc = iterator.next();
        response.add(doc);
    }
    return response;
    }
```
