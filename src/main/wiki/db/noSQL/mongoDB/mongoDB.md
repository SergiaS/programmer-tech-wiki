# MongoDB
> [Mongo Express](https://github.com/mongo-express/mongo-express) - Web-based MongoDB admin interface written with Node.js, Express and Bootstrap3.

> Mongo stores documents (rows) in collections (table).
A Document (row) is some json object, and a collection is a batch of json objects (table which holds all rows).

> MongoDB stores data records as BSON documents.
BSON is a representation of JSON documents.

## Comparing Mongo technologies
Comparing MongoClient vs MongoTemplate vs MongoRepository technologies.

|               | MongoClient                                  | MongoTemplate                                                   | MongoRepository                                   |
| ------------- |:--------------------------------------------:| :--------------------------------------------------------------:|:-------------------------------------------------:|
| From          | Java MongoDriver                             | Spring Data Mongo                                               | Spring Data Mongo                                 |
| How to use it | Create MongoClient object                    | Create bean of MongoTemplate<br>Java Config, Auto Configuration | Interface repository and extends MongoRepository  |
| Result Object | Database Object<br>(BasicDBObject, Document) | Entity class and domain objects both                            | Класс сущности обязателен<br>Ex. Book.class       |
| Mapping       | Need Mapping Explicitly with Entity class    | No need of mapping with Entity class<br>With domain objects need| No need of Mapping, Repository does that automatic|
| Projection    | Possible through proper query                | Through Built in Method chain                                   | Possible with JSON Query                          |
| Aggregation   | Possible through proper query                | Through Built in Method chain                                   | Not possible                                      |
| Кол-во кода   | Много кода                                   | Сравнительно меньше кода                                        | Минимум кода                                      |

## Dependency
Для использования необходимо подключить зависимость:
```xml
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>mongo-java-driver</artifactId>
    <version>3.12.7</version>
</dependency>
```

Дополнительно могут понадобиться зависимости:
> С версией `3.0.0` могут быть конфликты!
```xml
<dependency>
    <groupId>de.flapdoodle.embed</groupId>
    <artifactId>de.flapdoodle.embed.mongo</artifactId>
    <version>2.2.0</version>
</dependency>
```
или с функционалом побольше
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```


# Источник: Гоша Дударь
Любой объект, который будет записан  в коллекцию - это будет JavaScript объект.

## Вступление
> Прежде чем использовать конкретные команды, необходимо сначала указать коллекцию, с которой будем работать.

#### Сначала выбираем конкретную таблицу:
```javascript
// request
use itproger

// response
switched to db itproger
```

#### После создания БД нужно создать коллекцию
Чтобы создать коллекцию, можно использовать функцию createCollection() или поместить данные в определенную коллекцию и она автоматически будет создана, если такой ещё такой коллекции не существует.
```javascript
// request
db.createCollection("users")

// response
{ 
    "ok": 1 
}
```

#### Удалить коллекцию
```javascript
// request
db.articles.drop()

// response
true
```
> Если удалить последнюю коллекцию, то вместе с ней удалится и сама БД.

#### Команда для добавления одной конкретной записи
```javascript
// request
db.users.insertOne(
    {
        "name": "George",
        "email": "george@goo.com",
        "age": 22,
        "hasCar": false,
        "birthday": new Date('1996-11-27')
    }
)

// response
{
    "acknowledged" : true,
    "insertedId" : ObjectId("605c8c25063c2d39c089ea9e")
}
```

#### Команда для добавления нескольких записей.
*Квадратные скобки обязательны!*
```javascript
// request
db.users.insertMany([
    {
        "name": "Bob",
        "email": "bob@goo.com",
        "age": 37,
        "hasCar": false,
        "birthday": new Date('1996-11-27')
    },
    {
        "name": "Carl",
        "email": "carl@goo.com",
        "age": 32,
        "hasCar": false,
        "birthday": new Date('1993-10-20')
    }
])

// response
{
    "acknowledged" : true,
    "insertedIds" : [
        ObjectId("605c8de2063c2d39c089ea9f"),
        ObjectId("605c8de2063c2d39c089eaa0")
    ]
}
```

## Выборка
#### Выборка всех данных из нужной коллекции
```javascript
// request
db.users.find()

// response
{ "_id" : ObjectId("605c8c25063c2d39c089ea9e"), "name" : "George", "email" : "george@goo.com", "age" : 22, "hasCar" : false, "birthday" : ISODate("1996-11-27T00:00:00Z") }
{ "_id" : ObjectId("605c8de2063c2d39c089ea9f"), "name" : "Bob", "email" : "bob@goo.com", "age" : 37, "hasCar" : false, "birthday" : ISODate("1996-11-27T00:00:00Z") }
{ "_id" : ObjectId("605c8bd4063c2d39c089ea9d"), "name" : "Jack", "email" : "jack@goo.com", "age" : 47, "hasCar" : false, "favColor" : [ "Red", "White" ], "password" : "asdad234" }
```

#### Выборка первых нескольких записей из нужной коллекции
```javascript
// request
db.users.find().limit(2)

// response
{ "_id" : ObjectId("605c8c25063c2d39c089ea9e"), "name" : "George", "email" : "george@goo.com", "age" : 22, "hasCar" : false, "birthday" : ISODate("1996-11-27T00:00:00Z") }
{ "_id" : ObjectId("605c8de2063c2d39c089ea9f"), "name" : "Bob", "email" : "bob@goo.com", "age" : 37, "hasCar" : false, "birthday" : ISODate("1996-11-27T00:00:00Z") }
```

#### Выборка первых нескольких записей 
Выборка первых нескольких записей из нужной коллекции с исключением поля id (указывается во вторых скобках)
```javascript
// request
db.users.find({}, {_id: 0}).limit(2)

// response
{ /* some_response */ }
```

#### Выборка с сортировкой
Внутри функции указываем по какому полю сортируем, значение 1 указывает сортировку по возростанию, -1 - по убыванию. Можно указывать несколько сортировок через запятую.
```javascript
// request
db.users.find({}, {_id: 0}).sort({age: 1})

// response
{ /* some_response */ }
```

#### Выборка ИЛИ - по любому из указанных значений
```javascript
// request
db.users.find({$or: [{age: 22}, {email: "jack@goo.com"}]}, {_id: 0})

// response
{ /* some_response */ }
```

#### Выбока на значение больше чем (\$gt) и меньше чем (\$lt)
- **\$gt** - больше чем;
- **\$lt** - меньше чем;
- **\$lte** - меньше или равно;
- **\$gte** - больше или равно;
- **\$eq** - равно
```javascript
// request
db.users.find({$or: [{age: 22}, {email: "jack@goo.com"}]}, {_id: 0})

// response
{ /* some_response */ }
```

#### Выборка одно из значений в (\$in) одном элементе (столбце)
```javascript
// request
db.users.find({name: {$in: ["Jack","John","Bob"]}}, {_id: 0})

// response
{ /* some_response */ }
```

#### Выборка любого из значений кроме ($nin - not in) одном элементе (столбце)
```javascript
// request
db.users.find({name: {$nin: ["Jack","John","Bob"]}}, {_id: 0})

// response
{ /* some_response */ }
```

#### Выборка всех объектов с конкретным полем - child: {$exists: true}
```javascript
// request
db.users.find({child: {$exists: true}}, {_id: 0})

// response
{ /* some_response */ }
```

#### Вибірка з об'єктами що містять внутрішній масив favColor з розміром 2
```javascript
// request
db.users.find({favColor: {$size: 2}}, {_id: 0})

// response
{ /* some_response */ }
```

#### Выбираем все элементы, у которых есть поле favColor и в этом поле первый элемент по индексу будет равен конкретному значению
```javascript
// request
db.users.find({"favColor.1": "Red"}, {_id: 0})

// response
{ /* some_response */ }
```

#### Выбираем все элементы, у которых есть поле favColor и в этом поле элементы меньше/больше/равно значению
```javascript
// request
db.users.find({favColor: {$elemMatch: {$lte: 3}} }, {_id: 0})

// response
{ /* some_response */ }
```

## Обновление
#### Прежде чем обновить данные, необходимо создать фильтр - какие конкретные поля выбираем, например, объект с age = 22
#### updateOne - обновит только первый объект
```javascript
// request
db.users.updateOne({age: 22}, {$set: {age: 25}})

// response
{ 
    "acknowledged" : true, 
    "matchedCount" : 1, 
    "modifiedCount" : 1 
}
```

#### updateMany - обновит все объекты с указаным фильтром
```javascript
// request
db.users.updateMany({age: 25}, {$set: {age: 30}})

// response
{ 
    "acknowledged" : true, 
    "matchedCount" : 2, 
    "modifiedCount" : 2 
}
```

#### Обновление нескольких значений при найденном фильтре.
Т.е. необязательно менять теже самые данные, которые были указаны в фильтре
```javascript
// request
db.users.updateMany({age: 30}, {$set: {name: "User", email: "test@com.go"}})

// response
{ /* some_response */ }
```

#### Помимо обновления полей, существует функция замены объекта - replaceOne
```javascript
// request
db.users.replaceOne(
    {
        age: 22
    },
    {
        name: "New user", 
        hasCar: 23, password: "234", 
        hasWife: true
    }
)

// response
{ /* some_response */ }
```

## Удаление
#### Удалить все записи у которых age >= 22 && age < 38
```javascript
// request
db.users.deleteMany({
    age: {
        $gte: 22
    }, 
    age: {
        $lt: 38
    }
})

// response
{ /* some_response */ }
```

## Объединенные запросы
#### Функция *bulkWrite()*
Позволяет объединить несколько команд.
- поскольку команд несколько, то квадратные скобки необходимы;
- каждая команда помещается в фигурные скобки.
- для каждой команды необъодимо прописывать `document`
- пустой `filter { ... }` удалит все объекты в БД

```javascript
// request
db.users.bulkWrite([
    {
        insertOne: {
            "document": {
                name: "Mike",
                age: 45,
                email: "mike@goo.com"
            }
        }
    },
    {
        deleteOne: {
            filter: {
                age: 22
            }
        }
    },
    {
        updateOne: {
            filter: {
                name: "user"
            },
            update: {
                $set: {
                    email: "new_email@test.ru"
                }
            }
        }
    },
    {
        replaceOne: {
            filter: {
                name: "Bob"
            },
            replacement: {
                name: "Bobby",
                age: 45,
                email: "bobby@gov.com"
            }
        }
    }
])
```

```json
// request
{
    "acknowledged": true,
    "deletedCount": 1,
    "insertedCount": 1,
    "matchedCount": 1,
    "upsertedCount": 0,
    "insertedIds": {
        "0": ObjectId(
            "605da1029909d22500df4286"
        )
    },
    "upsertedIds": {
        
    }
}
```

## Поиск в тексте на частичное совпадение
В первую очередь необходимо создать индексы, т.е. указать в каких полях мы будем отслеживать информацию.
```javascript
db.articles.createIndex(
    {   // указываем с какими полями мы работаем - название поля/тип поля
        title: "text",
        anons: "text",
        text: "text"
    }
)
```

Теперь ищем кусочек текста в указанных полях выше (которые имеют индекс).
> В примере ниже поиск происходит по каждому слову отдельно!
```javascript
db.articles.find(
    {
        $text: {
            $search: "Новые жители"
        }
    }
)
```
Получить наибольшую схожесть при запросе (как в поиске google) можно выводить дополнительное поле схожести - score, и сразу сортировать его:
```javascript
// request
db.articles.find(
    {
        $text: {
            $search: "Открытие кофейня"
        }
    },
    {
        score: {
            $meta: "textScore"
        }
    }
).sort({
    score: {
        $meta: "textScore"
    }
})
```
```javascript
// response
{
    "_id": ObjectId(
        "605da7319909d22500df4288"
    ),
    "title": "Открытие кофейни",
    "anons": "Новая кофейня была открыта в городе Чишки",
    "text": "Все жители страны празднуют этот день!",
    "date": ISODate(
        "2021-11-11T00:00:00Z"
    ),
    "score": 1.3214285714285714
}
{
    "_id": ObjectId(
        "605da7319909d22500df4287"
    ),
    "title": "Акции компаний растут",
    "anons": "Компании стремительно набирают обороты",
    "text": "Открытие торгов. Рост акций по всем фронтам",
    "date": ISODate(
        "2020-11-11T00:00:00Z"
    ),
    "score": 0.5714285714285714
}
```

## Моментальная обработка данных
### Функция count()
Позволяет подсчитать кол-во записей в коллекции по определенному фильтру.
```javascript
db.users.count({
    age: 18
})
```

### Функция distinct()
Позволяет получить уникальные записи - без дубликатов. Т.е. позволяет получить массив элементов без повторяющихся элементов.
```javascript
// пример запроса
db.users.distinct("email")
```
```javascript
// пример ответа - при этом в базе было 2 "test@com.go"
[ "bobby@gov.com", "jack@goo.com", "mike@goo.com", "test@com.go" ]
```

### Агрегация
Объединение различных данных:
```javascript
db.users.aggregate([
    { // фильтр
        $match: {
            // name: "Mike" - записи содержащие имя Майк
            // если пусто, то все записи перебираются
        }
    },
    { // как будем в найденном группировать
        $group: {
            _id: "$name", // по какому полю всё объединяем
            age: { 
                $sum: "$age" // суммируем по полю age
            }
        }
    }
])
```

# Articles and interesting quotes
> [Install MongoDB Community Edition on Windows](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/)

### Run MongoDB Community Edition from the Command Interpreter
You can run MongoDB Community Edition from the Windows command prompt/interpreter (cmd.exe).
Open a Windows command prompt/interpreter (cmd.exe) as an __Administrator__.

### Start your MongoDB database
To start MongoDB, run `mongod.exe`
```shell
"C:\Program Files\MongoDB\Server\4.4\bin\mongod.exe" --dbpath="c:\data\db"
```
*The --dbpath option points to your database directory.*

### Connect to MongoDB 
```shell
"C:\Program Files\MongoDB\Server\4.4\bin\mongo.exe"
```
<p style="color:blueviolet; text-align: center; font-weight: bold">+ + + NEXT + + +</p>

> When Mongo sees that database doesn't exist, it will create it for us<br>
> ### SOURCE: [GitHub](https://github.com/eugenp/tutorials/blob/master/persistence-modules/java-mongodb/src/main/java/com/baeldung/MongoExample.java) and article - **[A Guide to MongoDB with Java](https://www.baeldung.com/java-mongodb)**
<p style="color:blueviolet; text-align: center; font-weight: bold">+ + + NEXT + + +</p>

> ### [Java и MongoDB: базовые операции](https://alexkosarev.name/2019/01/30/java-and-mongo-basic-operations/)

### Подключение к MongoDB
- `MongoClients.create()`.
```java
try (var mongoClient = MongoClients.create()) {
    // statements
}
```

### Работа с базами данных:
- Получение списка баз данных: `MongoClient.listDatabases()` и `MongoClient.listDatabaseNames()`.
```java
try (var mongoClient = MongoClients.create()) {
    mongoClient.listDatabases()
        .forEach((Consumer<Document>) System.out::println);
    // show dbs
    // Document{{name=test, sizeOnDisk=1.385336832E9, empty=false}}
    mongoClient.listDatabaseNames()
        .forEach((Consumer<String>) System.out::println);
    // test
}
```
- Создание и получение базы данных: `MongoClient.get()`.
```java
try (var mongoClient = MongoClients.create()) {
    // use test
    var database = mongoClient.getDatabase("test")
}
```
- Удаление базы данных: `MongoDatabase.drop()`.
```java
try (var mongoClient = MongoClients.create()) {
    // db.dropDatabase()
    var database = mongoClient.getDatabase("test")
    database.drop();
}
```

### Работа с коллекциями
- Получение списка коллекций: `MongoDatabase.listCollections()` и `MongoDatabase.listCollectionNames()`.
```java
try (var mongoClient = MongoClients.create()) {
    var database = mongoClient.getDatabase("test");
    // show collections
    database.listCollectionNames()
            .forEach((Consumer<String>) System.out::println);
    // todo
    database.listCollections()
            .forEach((Consumer<Document>) System.out::println);
    // Document{{name=todo, type=collection, options=Document{{}}, 
    //  info=Document{{readOnly=false, uuid=ec6302c9-3b3a-4c61-9bf0-27f54044c193}}, 
    //  idIndex=Document{{v=2, key=Document{{_id=1}}, name=_id_, ns=newtest.todo}}}}
}
```
- Создание и получение коллекции: `MongoDatabase.getCollection()`. Также коллекция может быть создана при помощи метода `MongoDatabase.createCollection()`.
```java
try (var mongoClient = MongoClients.create()) {
    var database = mongoClient.getDatabase("test");
    // db.createCollection("todo")
    // database.createCollection("todo")
    var todoCollection = database.getCollection("todo");
}
```
- Удаление коллекции: `MongoCollection.drop()`.
```java
try (var mongoClient = MongoClients.create()) {
    var database = mongoClient.getDatabase("test");
    var todoCollection = database.getCollection("todo");
    // db.getCollection("todo").drop()
    todoCollection.drop();
}
```

### Работа с индексами
- Получение списка индесов: `MongoCollection.listIndexes()`.
```java
try (var mongoClient = MongoClients.create()) {
    var database = mongoClient.getDatabase("test");
    var todoCollection = database.getCollection("todo");
    // db.getCollection("todo").getIndexes()
    todoCollection.listIndexes()
        .forEach((Consumer<Document>) System.out::println);
}

// Document{{v=2, key=Document{{_id=1}}, name=_id_, ns=test.todo}}
```
- Создание индекса: `MongoCollection.createIndex()`
```java
try (var mongoClient = MongoClients.create()) {
    var database = mongoClient.getDatabase("test");
    var todoCollection = database.getCollection("todo");
    // db.getCollection("todo").createIndex({dateCreated: 1}, {name: "idxDateCreated"})
    todoCollection.createIndex(new Document("dateCreated", 1), 
        new IndexOptions().name("idxDateCreated"));
}
```
- Удаление индекса: `MongoCollection.dropIndex()`
```java
try (var mongoClient = MongoClients.create()) {
    var database = mongoClient.getDatabase("test");
    var todoCollection = database.getCollection("todo");
    // db.getCollection("todo").dropIndex("idxDateCreated")
    todoCollection.dropIndex("idxDateCreated");
}
```

### Работа с документами
Данные в коллекциях MongoDB хранятся в виде документов. Формат хранения — `BSON`, или Binary JSON, очень похожий на стандартный JSON, но имеющий свои особенности.


- Создание документа: `MongoCollection.insertOne()` или `MongoCollection.insertMany()`.
```java
try (var mongoClient = MongoClients.create()) {
    var database = mongoClient.getDatabase("test");
    var todoCollection = database.getCollection("todo");
    // db.getCollection("todo").insert({_id: ObjectId(), task: "Drink some coffee", 
    //  dateCreated: ISODate(), done: falsedb})
    var todoDocument = new Document(Map.of("_id", new ObjectId(), 
        "task", "Drink some coffee", "dateCreated", LocalDateTime.now(), 
        "done", false));
        
    todoCollection.insertOne(todoDocument);
    // Document{{_id=5c516a862384dd4e9dc1277e, task=Drink some coffee, 
    //  done=false, dateCreated=Tue Jan 29 19:12:38 YEKT 2019}}
}
```

- Поиск по документам: `MongoCollection.find()`. Дополнительные методы `findOneAndUpdate()`, `findOneAndReplace()` и `findOneAndDelete()`.
```java
try (var mongoClient = MongoClients.create()) {
    var database = mongoClient.getDatabase("test");
    var todoCollection = database.getCollection("todo");
    // db.getCollection("todo").find({task: {$regex: "coffee"}})
    todoCollection.find(new Document("task", new Document("$regex", "coffee")))
        .forEach((Consumer<Document>) System.out::println);
    // Document{{_id=5c516a862384dd4e9dc1277e, task=Drink some coffee, 
    //  done=false, dateCreated=Tue Jan 29 19:12:38 YEKT 2019}}
}
```

- Изменение документов: `MongoCollection.updateOne()` и `MongoCollection.updateMany()`.

```java
try (var mongoClient = MongoClients.create()) {
    var database = mongoClient.getDatabase("test");
    var todoCollection = database.getCollection("todo");
    // db.todo.update({_id: ObjectId('5c516a862384dd4e9dc1277e')}, 
    //    {$set: {done: true}, $currentDate: {dateDone: true}, $unset: {dateCreated: true}})
    todoCollection.updateOne(new Document("_id", new ObjectId("5c516a862384dd4e9dc1277e")),
        new Document(Map.of("$set", new Document("done", true),
            "$currentDate", new Document("dateDone", true),
            "$unset", new Document("dateCreated", true))));
    
    // Document{{_id=5c516a862384dd4e9dc1277e, task=Drink some coffee, done=true, 
    //  dateDone=Tue Jan 29 19:40:10 YEKT 2019}}
}
```

- Удаление документов: `MongoCollection.deleteOne()` и `MongoCollection.deleteMany()`.
```java
try (var mongoClient = MongoClients.create()) {
    var database = mongoClient.getDatabase("test");
    var todoCollection = database.getCollection("todo");
    // db.todo.remove({done: true})
    todoCollection.deleteMany(new Document("done", true));
}
```
<p style="color:blueviolet; text-align: center; font-weight: bold">+ + + NEXT + + +</p>

## Mongo Shell
Interactive JavaScript interface to MongoDB. 
Used to run commands against the database. For example:
```commandline
mongo mongodb://localhost:27017 -u rootuser -p rootpass
```

### Як увійти в Docker у контейнер Mongo з терміналу
Для входу нам потрібен ID контейнеру - дивимося які образи у нас працюють командою `docker ps`:
```commandline
$ docker ps
CONTAINER ID   IMAGE                  COMMAND                  CREATED          STATUS              PORTS                      NAMES
c444a5dda48d   mongo                  "docker-entrypoint.s…"   18 minutes ago   Up About a minute   0.0.0.0:27017->27017/tcp   mongodb
74110d31697c   mongo-express:0.54.0   "tini -- /docker-ent…"   18 minutes ago   Up About a minute   0.0.0.0:8081->8081/tcp     mongo-express
```
Беремо потрібний ID і заходимо у контейнер:
```commandline
docker exec -it c444a5dda48d bash
```
P.S. ... якщо використовується bash термінал - додай префікс
```commandline
winpty docker exec -it c444a5dda48d bash
```

Подивитися вміст можна командою 
```commandline
ls
```

Потім звертаємося до mongodb:
```commandline
mongo mongodb://localhost:27017 -u rootuser -p rootpass
```
> ВАЖЛИВО! Starting from Mongodb version 6.0 mongo was replaced by mongosh
> Тоді тут буде така команда
> ```commandline
> mongosh mongodb://localhost:27017 -u rootuser -p rootpass
> ```

Ось тепер, ми можемо побачити наші БД задіяв команду `show dbs`.

Створити БД можна за допомогою команди:
```commandline
use amigoscode
```

Після створення БД, якщо виконати команду `show dbs`, створена БД не відобразиться оскільки вона не має жодної колекції.

Всередині БД можна використовувати різні команди, які можна побачити - `db.help()`.

Подивитися ім'я БД - `db.getName()`.

Для створення колекції є команда `db.createCollection("hello")`.
Тепер після створення колекції, наша БД з'явиться у списку  `show dbs`.

Видалення теперишньої БД - `db.dropDatabase()` - response: `{ ok: 1, dropped: 'amigoscode' }`.

