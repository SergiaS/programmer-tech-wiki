Любой объект, который будет записан  в коллекцию - это будет JavaScript объект.

## Intro
### Прежде чем использовать конкретные команды, необходимо сначала указать коллекцию, с которой будем работать.

#### Использует конкретную таблицу:
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

#### Команда для добавления нескольких записей. *Квадратные скобки обязательны!*
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

#### Выборка с сортировкой.
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

#### Обновление нескольких значений при найденном фильтре. Т.е. необязательно менять теже самые данные, которые были указаны в фильтре
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
#### Функция bulkWrite()
Позволяет объединить несколько команд.
- поскольку команд несколько, то квадратные скобки необходимы;
- каждая команда помещается в фигурные скобки.
- для каждой команды необъодимо прописывать `document`
- пустой `filter { ... }` удалит все объекты в БД

Пример запроса:
```javascript
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

Пример ответа:
```json
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
