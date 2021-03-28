## MongoDB

<detail>
<summary>"Click to expand"</summary>

- [Источник: Гоша Дударь]()
  * [Вступление]()
    * [Сначала выбираем конкретную таблицу]()
    * [После создания БД нужно создать коллекцию](#после-создания-бд-нужно-создать-коллекцию)
    * [Удалить коллекцию](#удалить-коллекцию) 
    * [Команда для добавления одной конкретной записи](#команда-для-добавления-одной-конкретной-записи) 
    * [Команда для добавления нескольких записей](#команда-для-добавления-нескольких-записей)
  * [Выборка](#выборка)
    * [Выборка всех данных из нужной коллекции](#выборка-всех-данных-из-нужной-коллекции)
    * [Выборка первых нескольких записей из нужной коллекции](#выборка-первых-нескольких-записей-из-нужной-коллекции)
    * [Выборка первых нескольких записей](#выборка-первых-нескольких-записей)
    * [Выборка с сортировкой](#выборка-с-сортировкой)
    * [Выборка ИЛИ - по любому из указанных значений](#выборка-или---по-любому-из-указанных-значений)
    * [Выбока на значение больше чем ($gt) и меньше чем ($lt)](#выбока-на-значение-больше-чем-gt-и-меньше-чем-lt)
    * [Выборка одно из значений в ($in) одном элементе (столбце)](#выборка-одно-из-значений-в-in-одном-элементе-столбце)
    * [Выборка любого из значений кроме ($nin - not in) одном элементе (столбце)](#выборка-любого-из-значений-кроме-nin---not-in-одном-элементе-столбце)
    * [Выборка всех объектов с конкретным полем - child: {$exists: true}](#выборка-всех-объектов-с-конкретным-полем---child-exists-true)
    * [Выборка с объектами содержащие внутренний массив favColor с рамером 2](#выборка-с-объектами-содержащие-внутренний-массив-favcolor-с-рамером-2)
    * [Выбираем все элементы, у которых есть поле favColor с индексом](#выбираем-все-элементы-у-которых-есть-поле-favcolor-и-в-этом-поле-первый-элемент-по-индексу-будет-равен-конкретному-значению)
    * [Выбираем все элементы, у которых есть поле favColor и в этом поле элементы меньше/больше/равно значению](#выбираем-все-элементы-у-которых-есть-поле-favcolor-и-в-этом-поле-элементы-меньшебольшеравно-значению)
  * [Обновление](#обновление)
    * [updateOne - обновит только первый объект](#updateone---обновит-только-первый-объект)
    * [updateMany - обновит все объекты с указаным фильтром](#updatemany---обновит-все-объекты-с-указаным-фильтром)
    * [Обновление нескольких значений при найденном фильтре](#обновление-нескольких-значений-при-найденном-фильтре)
    * [Функция замены объекта - replaceOne](#помимо-обновления-полей-существует-функция-замены-объекта---replaceone)
  * [Удаление](#удаление)
    * [Удалить все записи у которых age >= 22 && age < 38](#удалить-все-записи-у-которых-age--22--age--38)
  * [Объединенные запросы](#объединенные-запросы)
    * [Функция `bulkWrite()`](#функция-bulkwrite)
  * [Поиск в тексте на частичное совпадение](#поиск-в-тексте-на-частичное-совпадение)
  * [Моментальная обработка данных](#моментальная-обработка-данных)
    * [Функция `count()`](#функция-count)
    * [Функция `distinct()`](#функция-distinct)
    * [Агрегация](#агрегация)
- [Articles and interesting quotes](#articles-and-interesting-quotes)
  * Install MongoDB Community Edition on Windows
    * [Run MongoDB Community Edition from the Command Interpreter](#run-mongodb-community-edition-from-the-command-interpreter)
    * [Start your MongoDB database](#start-your-mongodb-database)
    * [Connect to MongoDB](#connect-to-mongodb)
  * [A Guide to MongoDB with Java](#source-github-and-article---a-guide-to-mongodb-with-java)
  * [Java и MongoDB: базовые операции](#java-и-mongodb-базовые-операции)
    * [Подключение к MongoDB](#подключение-к-mongodb)
    * [Работа с базами данных](#работа-с-базами-данных)
    * [Работа с коллекциями](#работа-с-коллекциями)
    * [Работа с индексами](#работа-с-индексами)
    * [Работа с документами](#работа-с-документами)
  
</detail>

# Источник: Гоша Дударь
Любой объект, который будет записан  в коллекцию - это будет JavaScript объект.

## Вступление
> Прежде чем использовать конкретные команды, необходимо сначала указать коллекцию, с которой будем работать.

#### Сначала выбираем конкретную таблицу:
```javascript
// запрос
use itproger

// ответ
switched to db itproger
```

#### После создания БД нужно создать коллекцию
Чтобы создать коллекцию, можно использовать функцию createCollection() или поместить данные в определенную коллекцию и она автоматически будет создана, если такой ещё такой коллекции не существует.
```javascript
// запрос
db.createCollection("users")

// ответ
{ 
    "ok": 1 
}
```

#### Удалить коллекцию
```javascript
// запрос
db.articles.drop()

// ответ
true
```
> Если удалить последнюю коллекцию, то вместе с ней удалится и сама БД.

#### Команда для добавления одной конкретной записи
```javascript
// запрос
db.users.insertOne(
    {
        "name": "George",
        "email": "george@goo.com",
        "age": 22,
        "hasCar": false,
        "birthday": new Date('1996-11-27')
    }
)

// ответ
{
    "acknowledged" : true,
    "insertedId" : ObjectId("605c8c25063c2d39c089ea9e")
}
```

#### Команда для добавления нескольких записей.
*Квадратные скобки обязательны!*
```javascript
// запрос
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

// ответ
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
// запрос
db.users.find()

// ответ
{ "_id" : ObjectId("605c8c25063c2d39c089ea9e"), "name" : "George", "email" : "george@goo.com", "age" : 22, "hasCar" : false, "birthday" : ISODate("1996-11-27T00:00:00Z") }
{ "_id" : ObjectId("605c8de2063c2d39c089ea9f"), "name" : "Bob", "email" : "bob@goo.com", "age" : 37, "hasCar" : false, "birthday" : ISODate("1996-11-27T00:00:00Z") }
{ "_id" : ObjectId("605c8bd4063c2d39c089ea9d"), "name" : "Jack", "email" : "jack@goo.com", "age" : 47, "hasCar" : false, "favColor" : [ "Red", "White" ], "password" : "asdad234" }
```

#### Выборка первых нескольких записей из нужной коллекции
```javascript
// запрос
db.users.find().limit(2)

// ответ
{ "_id" : ObjectId("605c8c25063c2d39c089ea9e"), "name" : "George", "email" : "george@goo.com", "age" : 22, "hasCar" : false, "birthday" : ISODate("1996-11-27T00:00:00Z") }
{ "_id" : ObjectId("605c8de2063c2d39c089ea9f"), "name" : "Bob", "email" : "bob@goo.com", "age" : 37, "hasCar" : false, "birthday" : ISODate("1996-11-27T00:00:00Z") }
```

#### Выборка первых нескольких записей 
Выборка первых нескольких записей из нужной коллекции с исключением поля id (указывается во вторых скобках)
```javascript
// запрос
db.users.find({}, {_id: 0}).limit(2)

// ответ
{ /* some_response */ }
```

#### Выборка с сортировкой
Внутри функции указываем по какому полю сортируем, значение 1 указывает сортировку по возростанию, -1 - по убыванию. Можно указывать несколько сортировок через запятую.
```javascript
// запрос
db.users.find({}, {_id: 0}).sort({age: 1})

// ответ
{ /* some_response */ }
```

#### Выборка ИЛИ - по любому из указанных значений
```javascript
// запрос
db.users.find({$or: [{age: 22}, {email: "jack@goo.com"}]}, {_id: 0})

// ответ
{ /* some_response */ }
```

#### Выбока на значение больше чем (\$gt) и меньше чем (\$lt)
- **\$gt** - больше чем;
- **\$lt** - меньше чем;
- **\$lte** - меньше или равно;
- **\$gte** - больше или равно;
- **\$eq** - равно
```javascript
// запрос
db.users.find({$or: [{age: 22}, {email: "jack@goo.com"}]}, {_id: 0})

// ответ
{ /* some_response */ }
```

#### Выборка одно из значений в (\$in) одном элементе (столбце)
```javascript
// запрос
db.users.find({name: {$in: ["Jack","John","Bob"]}}, {_id: 0})

// ответ
{ /* some_response */ }
```

#### Выборка любого из значений кроме ($nin - not in) одном элементе (столбце)
```javascript
// запрос
db.users.find({name: {$nin: ["Jack","John","Bob"]}}, {_id: 0})

// ответ
{ /* some_response */ }
```

#### Выборка всех объектов с конкретным полем - child: {$exists: true}
```javascript
// запрос
db.users.find({child: {$exists: true}}, {_id: 0})

// ответ
{ /* some_response */ }
```

#### Выборка с объектами содержащие внутренний массив favColor с рамером 2
```javascript
// запрос
db.users.find({favColor: {$size: 2}}, {_id: 0})

// ответ
{ /* some_response */ }
```

#### Выбираем все элементы, у которых есть поле favColor и в этом поле первый элемент по индексу будет равен конкретному значению
```javascript
// запрос
db.users.find({"favColor.1": "Red"}, {_id: 0})

// ответ
{ /* some_response */ }
```

#### Выбираем все элементы, у которых есть поле favColor и в этом поле элементы меньше/больше/равно значению
```javascript
// запрос
db.users.find({favColor: {$elemMatch: {$lte: 3}} }, {_id: 0})

// ответ
{ /* some_response */ }
```

## Обновление
#### Прежде чем обновить данные, необходимо создать фильтр - какие конкретные поля выбираем, например, объект с age = 22
#### updateOne - обновит только первый объект
```javascript
// запрос
db.users.updateOne({age: 22}, {$set: {age: 25}})

// ответ
{ 
    "acknowledged" : true, 
    "matchedCount" : 1, 
    "modifiedCount" : 1 
}
```

#### updateMany - обновит все объекты с указаным фильтром
```javascript
// запрос
db.users.updateMany({age: 25}, {$set: {age: 30}})

// ответ
{ 
    "acknowledged" : true, 
    "matchedCount" : 2, 
    "modifiedCount" : 2 
}
```

#### Обновление нескольких значений при найденном фильтре.
Т.е. необязательно менять теже самые данные, которые были указаны в фильтре
```javascript
// запрос
db.users.updateMany({age: 30}, {$set: {name: "User", email: "test@com.go"}})

// ответ
{ /* some_response */ }
```

#### Помимо обновления полей, существует функция замены объекта - replaceOne
```javascript
// запрос
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

// ответ
{ /* some_response */ }
```

## Удаление
#### Удалить все записи у которых age >= 22 && age < 38
```javascript
// запрос
db.users.deleteMany({
    age: {
        $gte: 22
    }, 
    age: {
        $lt: 38
    }
})

// ответ
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
// запрос
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
// ответ
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
<p align="center"> + + + NEXT + + +</p>

> When Mongo sees that database doesn't exist, it will create it for us<br>
> ### SOURCE: [GitHub](https://github.com/eugenp/tutorials/blob/master/persistence-modules/java-mongodb/src/main/java/com/baeldung/MongoExample.java) and article - **[A Guide to MongoDB with Java](https://www.baeldung.com/java-mongodb)**
<p align="center"> + + + NEXT + + +</p>

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
<p align="center"> + + + NEXT + + +</p>
