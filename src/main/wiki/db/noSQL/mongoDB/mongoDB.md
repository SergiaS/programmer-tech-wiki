Любой объект, который будет записан  в коллекцию - это будет JavaScript объект.

## Intro
### Прежде чем использовать конкретные команды, необходимо сначала указать коллекцию, с которой будем работать.
<br>

#### Использует конкретную таблицу:
```javascript
// запрос
use itproger

// ответ
switched to db itproger
```
<br>

#### После создания БД нужно создать коллекцию
```javascript
// запрос
db.createCollection("users")

// ответ
{ 
    "ok": 1 
}
```
<br>

#### Удалить коллекцию
```javascript
// запрос
db.articles.drop()

// ответ
true
```
*Если удалить последнюю коллекцию, то вместе с ней удалится и сама БД.*
<br>
<br>

#### Команда для добавления одной конкретной записи
```javascript
// запрос
db.users.insertOne(
    {
        "name": "George",
        "email": "george@goo.com",
        "age": 22,
        "hasCar": false,
        "birthday":new Date('1996-11-27')
    }
)

// ответ
{
    "acknowledged" : true,
    "insertedId" : ObjectId("605c8c25063c2d39c089ea9e")
}
```
<br>

#### Команда для добавления нескольких записей. *Квадратные скобки обязательны!*
```javascript
// запрос
db.users.insertMany([
    {
        "name": "Bob",
        "email": "bob@goo.com",
        "age": 37,
        "hasCar": false,
        "birthday":new Date('1996-11-27')
    },
    {
        "name": "Carl",
        "email": "carl@goo.com",
        "age": 32,
        "hasCar": false,
        "birthday":new Date('1993-10-20')
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
<br>

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
<br>

#### Выборка первых нескольких записей из нужной коллекции
```javascript
// запрос
db.users.find().limit(2)

// ответ
{ "_id" : ObjectId("605c8c25063c2d39c089ea9e"), "name" : "George", "email" : "george@goo.com", "age" : 22, "hasCar" : false, "birthday" : ISODate("1996-11-27T00:00:00Z") }
{ "_id" : ObjectId("605c8de2063c2d39c089ea9f"), "name" : "Bob", "email" : "bob@goo.com", "age" : 37, "hasCar" : false, "birthday" : ISODate("1996-11-27T00:00:00Z") }
```
<br>

#### Выборка первых нескольких записей из нужной коллекции с исключением поля id (указывается во вторых скобках)
```javascript
// запрос
db.users.find({}, {_id: 0}).limit(2)

// ответ
{ /* some_response */ }
```
<br>

#### Выборка с сортировкой. Внутри функции указываем по какому полю сортируем, значение 1 указывает сортировку по возростанию, -1 - по убыванию. Можно указывать несколько сортировок через запятую.
```javascript
// запрос
db.users.find({}, {_id: 0}).sort({age: 1})

// ответ
{ /* some_response */ }
```
<br>

#### Выборка ИЛИ - по любому из указанных значений
```javascript
// запрос
db.users.find({$or: [{age: 22}, {email: "jack@goo.com"}]}, {_id: 0})

// ответ
{ /* some_response */ }
```
<br>

#### Выбока на значение больше чем (\$gt) и меньше чем (\$lt)
- \$lte - меньше или равно;
- \$gte - больше или равно;
- \$eq - равно
```javascript
// запрос
db.users.find({$or: [{age: 22}, {email: "jack@goo.com"}]}, {_id: 0})

// ответ
{ /* some_response */ }
```
<br>

#### Выборка одно из значений в (\$in) одном элементе (столбце)
```javascript
// запрос
db.users.find({name: {$in: ["Jack","John","Bob"]}}, {_id: 0})

// ответ
{ /* some_response */ }
```
<br>

#### Выборка любого из значений кроме ($nin - not in) одном элементе (столбце)
```javascript
// запрос
db.users.find({name: {$nin: ["Jack","John","Bob"]}}, {_id: 0})

// ответ
{ /* some_response */ }
```
<br>

#### Выборка всех объектов с конкретным полем - child: {$exists: true}
```javascript
// запрос
db.users.find({child: {$exists: true}}, {_id: 0})

// ответ
{ /* some_response */ }
```
<br>

#### Выборка с объектами содержащие внутренний массив favColor с рамером 2
```javascript
// запрос
db.users.find({favColor: {$size: 2}}, {_id: 0})

// ответ
{ /* some_response */ }
```
<br>

#### Выбираем все элементы, у которых есть поле favColor и в этом поле первый элемент по индексу будет равен конкретному значению
```javascript
// запрос
db.users.find({"favColor.1": "Red"}, {_id: 0})

// ответ
{ /* some_response */ }
```
<br>

#### Выбираем все элементы, у которых есть поле favColor и в этом поле элементы меньше/больше/равно значению
```javascript
// запрос
db.users.find({favColor: {$elemMatch: {$lte: 3}} }, {_id: 0})

// ответ
{ /* some_response */ }
```
<br>

## Обновление
#### Прежде чем обновить данные, необходимо создать фильтр - какие конкретные поля выбираем, например, объект с age = 22
#### updateOne - обновит только первый объект**
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
<br>

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
<br>

#### Обновление нескольких значений при найденном фильтре. Т.е. необязательно менять теже самые данные, которые были указаны в фильтре
```javascript
// запрос
db.users.updateMany({age: 30}, {$set: {name: "User", email: "test@com.go"}})

// ответ
{ /* some_response */ }
```
<br>

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
<br>

## Удаление
#### Удалить все записи у которых age >= 22 && age < 38
```javascript
// запрос
db.users.deleteMany({age: {$gte: 22}, age: {$lt: 38}})

// ответ
{ /* some_response */ }
```
<br>



