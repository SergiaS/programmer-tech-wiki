# Python3 by amigoscode

## Создание переменных
Имена переменных пишутся по конвенции `camelCase` (редко) или underscore `_` (чаще всего).
```python
# пример создания переменных
name = "Bob"
age = 20
pi = 3.14
numbers = [1, 2, 3, 4]
```
```python
# создание переменных в одну строку:
name, age = "Bob", 20
```

## Типы данных
> Python динамически типизированный язык. Указывать тип переменных не нужно.
> 
> __The data type of any variable or function is checked at runtime.__

Если нужно указать тип переменной явно (но так не делают):
```python
name: str = "Bob"
age: int = 20
pi: float = 3.14
numbers: list = [1, 2, 3, 4]
isAdult: bool = True
```

Узнать тип можно методом `typeof`:
```python
name, age = "Bob", 20
pi = 3.14
numbers = [1, 2, 3, 4]
print(type(isAdult))

print(type(name))    # <class 'str'>
print(type(age))     # <class 'int'>
print(type(pi))      # <class 'float'>
print(type(numbers)) # <class 'list'>
print(type(numbers)) # <class 'bool'>
```

## Комментарии
Однострочный комментарий (single line comment):
```python
# That's all folks
```

Многострочный комментарий (multi line comment):
```python
"""
Once upon
a time
there was...
"""
```

## Strings
Можно помещать как в одинарные так и двойные кавычки - `'bob'`, `"bob"`.

Многострочная строка записывается с указав трижды двойные кавычки - как в последних версиях Java:
```python
name = """
Once upon 
a time 
there was...
"""
```
При этом все переносы строки будут сохранены - нет ныжды добавлять `\n` и/или `\r`.


### Метод `len`
Возвращает длину строки, записывается не как все методы:
```python
name = "Bob"
print(len(name))
```

### Узнать имеется ли в строке
Пример возвразет значение типа `bool`.
```python
name = "Bob Singer"
print("inge" in name)     # True
print("inge" not in name) # False
```

### Форматирование строки
* Вариант 1 - с использование метода `format`:
    ```python
    name = "Bob Singer"
    question = """
    Hello {}.
    How are you?
    """
    
    print(question.format(name))
    # Hello Bob Singer.
    # How are you?
    ```
* Вариант 2 - с вставкой буквы `f` в начало строки и явным указанием переменных в фигурных скобках `{example}`:
    ```python
    # Вариант 2
    name = "Bob Singer"
    question = f"""
    Hello {name}.
    How are you?
    """
    
    print(question)
    # Hello Bob Singer.
    # How are you?
    ```

## Отступы (Indentation)
Python не похож на многие языки, такие как Java.
Здесь большую роль играют отступы, поскольку в методах и классах нет скобок `{тело_метода}` как зоны видимости переменных, нет точки с запятой `;`.

## Зарезервированные слова в Python
Полный актуальный список для своей версии можно посмотреть командой:
```python
import keyword

print(keyword.kwlist)
```

## Арифметические операции
* `**` - возведение в степень, например `3 ** 3`, т.е. (3 * 3 * 3).

## Логические операторы
Помимо стандартных операторов `<`, `>`, `=`, `!` и их комбинаций, здесь используются зарезервированные слова `and`, `or` и `not`.

## List
Лист пишется в квадратных скобках `[]`.
Может хранить объекты любого типа как в JS.
Не имеет фиксированной длины.
Может хранить дублирующиеся значения.
```python
print(type([])) # <class 'list'>
```

### Аналог contains в листе
Для этого указывается конструкция с `in`:
```python
names = ["bob","ted","carl"]
print("sam" in names)     # False
print("sam" not in names) # True

numbers = [3,7,8]
print(4 in numbers)     # False
print(4 not in numbers) # True
```

<hr>

### Удаление по значению - метод `remove`
Здесь у листа вызывается метод `remove`.
Удаляет элемент с листа по названию значения. 
Если два одинаковых значения - удалит только первый:
```python
names = ["bob", "bob", "ted", "carl"]
names.remove("bob")
print(names)  # ['bob', 'ted', 'carl']
```

<hr>

### Удаление по индексу - слово `del`
Здесь перед листом указывается спец слово `del`. В случае выхода ха диапазон - вернет ошибку!
```python
names = ["bob","ted","carl"]
del names[2]
print(names) # ['bob', 'ted']
```
Также можно удалить диапазон от и до. В случае выхода ха диапазон - НЕ вернет ошибку!
```python
numbers = [1, 2, 3, 4, 5, 6, 7, 8]
del numbers[1:4]
print(numbers)  # [1, 5, 6, 7, 8]
```


## Set
Как и в Java, дублирующие элементы не разрешены. Порядок не гарантируется - каждый раз по разному.
`Set` записывается почти также как `List`, только вместо квадратных скобок `[]` используются фигурные `{}`:
```python
numbersList = [1, 1]
numbersSet = {1, 1}
lettersSet = {"A","A","B","C","C"}
print(numbersList)  # [1, 1]
print(numbersSet)   # {1}
print(lettersSet)   # {'B', 'C', 'A'}
```

### Объединение сетов 
#### Union
Простое объединение пары сетов - используется между сетами символ вертикальной палки `|`:
```python
lettersA = {"A", "B", "C", "D"}
lettersB = {"E", "F"}
unionSet = lettersA | lettersB 
print(unionSet)  # {'B', 'F', 'A', 'D', 'C', 'E'}
```
Может работать с тремя сетами точно.

#### Intersection
Объединяет только одинаковые элементы - используется между сетами символ амперсанда `&`:
```python
lettersA = {"A", "B", "C", "D"}
lettersB = {"A", "E", "F"}
intersectionSet = lettersA & lettersB
print(intersectionSet)  # {'A'}
```
Может работать с тремя сетами точно - элемент должен быть во всех сетах.

#### Difference
Показывает каких элементов нет во втором листе - используется между сетами знак минуса `-`: 
```python
lettersA = {"A", "B", "C", "D"}
lettersB = {"A", "E", "F"}
differenceSet = lettersA - lettersB
print(differenceSet)    # {'D', 'C', 'B'}
```


## Аналог мап / Dictionaries data structure
Структура данных Dictionary (словарь) хранит ключ со значением. Ключи должны быть уникальны.
Аналог `entries`, в Python это метод `items`.
```python
person = {
    "name": "Bob",
    "age": 20
}
```
Обратиться к значению можно через квадратные скобки с именем ключа или через метод `get`.
> В случае отсутствующего ключа в словаре метод `get` вернет `None`, а обращение через квадратные скобки выбросит ошибку! 
```python
print(person["age"])      # 20
print(person.get("age"))  # 20
```
Обновиться значение:
```python
person["age"] = 100
print(person["age"])      # 100
```

## Циклы
Есть пару вариантов итерации структур данных через циклы.
```python
dictionaryExample = {
    "name": "Bob",
    "age": 20,
    "address": "USA"
}

# стандартная итерация
for e in dictionaryExample:
    print(f"key: {e}, value: {dictionaryExample[e]}")

# похоже на деструктаризацию в JS
for key, value in dictionaryExample.items():
    print(f"key: {key}, value: {value}")
```

## Функции
В Python методом называется функция, и помечается в начале словом `def`.
В Python есть возможность задать аргументам функции дефолтные значения.
```python
def greet(name, age=-1):
    print(f"Hey {name}, how old are you?")
    if age >= 0:
        print(f"I am {age} years old!")
    else:
        print(f"Not enough!")


greet("Bob", 18)
```

Также можно установить ожидаемый возвращаем тип для каждой функции с помощью `->`:
```python
def address(self, address) -> str:
    return f"This is my {address}!"

def phone(self, phone) -> int:
    return phone
```

## Модули
Модули - это дополнительные библиотеки. Можно использовать встроенные модули в Python, свои собственные или внешние.

### Импорт модулей
Сначало нужно импортировать модуль, потом в коде на этом модуле который импортирован, использовать любой доступный методы.
```python
import math

print(math.isqrt(25))  # 5
```
Если нужно использовать только один метод из модуля:
```python
from math import isqrt

print(math.isqrt(25))  # 5
```
Для создания своего модуля, просто создается обычный файл, и имя файла импортируется как указано выше.


## Classes and Objects
Конструктор в классе это функция, которая имеет имя  `__init__`.

В каждой функции класса указывается первым аргументом слово `self` - аналог `this` в Java.
> ОБЯЗАТЕЛЬНО указывай `self` в каждой функции, иначе переменные будут работать как статические и их данные будут перезатираться везде!
```python
class Phone:
    def __init__(self, brand, price):
        self.brand = brand
        self.price = price
        
    def call(self, phone_number):
        print(f"{self.brand} is calling {phone_number}")


iphone = Phone("Iphone 7+", 300)
samsung = Phone("Samsung S20", 1400)

print(iphone.price)       # 300
print(samsung.brand)      # Samsung S20
iphone.call("856463222")  # Iphone 7+ is calling 856463222
```
Для корректрого представления объекта (вместо отображения адреса в памяти) можно перезаписать метод `__str__`, аналог в Java `toString`.
Но среда разработки НЕ перезаписывает его сама - перезаписать нужно самому!
```python
def __str__(self) -> str:
        return f"Brand {self.brand}, Price {self.price}"
```

## Работа с датами
Впервую очередь для работы нужно импортировать модуль `datetime`.
```python
import datetime

# получить текущую дату и время
print(datetime.datetime.now())

# получить сегодняшнюю дату
print(datetime.date.today())
print(datetime.datetime.now().date())

# получить текущее время
print(datetime.datetime.now().time())
```
Для форматирования даты используй метод `strftime` с модуля `datetime`:
```python
import datetime

now = datetime.datetime.now()
print(now)
print(now.strftime("%d/%m/%Y %H:%M:%S"))
```
- `%B` - указывается полное название месяца: September, October. 
- `%b` - указывается аббревиатура месяца: Sep, Oct, Nov. 


## Работа с файлами
> После работы с файлом всегда нужно закрыть поток функцией `close`. Или используй синтакс с `with`.

Открыть файл можно функцией `open`. Здесь нужно указать два аргумента. Первый - адрес файла, второй режим (чтение/запись/добавление):
```python
file = open("./data.csv", "r+")
file.write("id,name,email\n")
file.write("1,Bob,bobby@gamil.com\n")
file.write("2,Suzy,suz@yahoo.com\n")
file.write("3,Gina,gina@yahoo.com\n")
file.close()
```
* `./` - указывается текущий каталог. Можно и без, если это корень в котором лежит файл.
* `w` - режим записи файла - `write`. Если его нет, создатся новый. Если есть - полностью перезапишет.
* `r+` - открывает файл и перезаписывает инфу, т.е. `read and rewrite`.
* `r` - открывает файл для чтения.
* `a` - открывает файл и добавляет инфу - `append`.

```python
file = open("./data.csv", "r")
print(file.read())       # читает весь файл
print(file.readline())   # читает только строку
print(file.readlines())  # вернет лист строк
file.close()
```

Читает файл построчно:
```python
file = open("./data.csv", "r")

for line in file:
    print(line)
file.close()
```

Синтакс `with` автоматически закрывает поток файла за нас:
```python
with open("./data.csv", "r") as file:
    print(file.read())
```

Проверка на существующий файл в каталоге - нужно импортировать модуль `os.path`:
```python
import os.path

filename = "./data.csv"

if os.path.isfile(filename):
    with open(filename, "r") as file:
        print(file.read())
else:
    print(f"file {filename} does not exist")
```


## Fetching data from internet
Для работы с инетом нужно импортировать либу `urllib`.

Пример работы:
```python
from urllib import request

r = request.urlopen("http://www.google.com")
print(r)            # <http.client.HTTPResponse object at 0x...>
print(r.getcode())  # 200
```
`request` - объект ответа - HTTPResponse.
`getcode()` - возвращает статус объекта.
`read()` - возвращает html документ по запрашиваемому адресу.

## Working with json objects
Для работы с json нужно импортировать либу `json`.
```python
from urllib import request
import json

url = "https://randomuser.me/api/?results=5"
r = request.urlopen(url)
# print(r.getcode())
data = r.read()
jsonData = json.loads(data)
# print(jsonData)


class User:
    def __init__(self, gender, email) -> None:
        self.gender = gender
        self.email = email

    def __str__(self) -> str:
        return f"User=[gender={self.gender}, email={self.email}"


users = []

for u in jsonData["results"]:
    gender = u.get("gender")
    email = u.get("email")
    user = User(gender, email)
    users.append(user)

print(len(users))
for user in users:
    print(user)

```


## [PIP and Modules](https://pypi.org/project/pip/)
Pip is the package installer for Python

You need type commands into your terminal.
```shell
pip
```
```shell
pip list
```

### Example
Устанавливаем модуль:
```shell
pip3 install requests
```
Импортируем в свой `.py` файл:
```python
import requests
```
Применяем:
```python
import requests
import json

url = "https://randomuser.me/api/?results=5"
response = requests.get(url)
print(response.status_code)

jsonData = json.loads(response.text)
print(jsonData)


class User:
    def __init__(self, gender, email) -> None:
        self.gender = gender
        self.email = email

    def __str__(self) -> str:
        return f"User=[gender={self.gender}, email={self.email}"


users = []

for u in jsonData["results"]:
    gender = u.get("gender")
    email = u.get("email")
    user = User(gender, email)
    users.append(user)

print(len(users))
for user in users:
    print(user)
```
