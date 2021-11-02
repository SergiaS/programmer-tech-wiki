# JavaScript


## Fetch API
* [Fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch)
* [Отличия fetch() от jQuery](https://developer.mozilla.org/ru/docs/Web/API/Fetch_API#отличия_от_jquery)

После `fetch` получаем объект с ответом - [Response](https://developer.mozilla.org/ru/docs/Web/API/Response).

После `join` получаем объект Promise.

```js
// Пример чтения json-данных (курсы валют) с сервера НБУ:
fetch('https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json')
    .then(response => response.json())
    .then(data => {
        for (let i = 0; i < data.length; i++) {
            console.log(
                "id: " + data[i]["r030"] +
                "; currName: " + data[i]["txt"] +
                "; rate: " + data[i]["rate"] +
                "; cc: " + data[i]["cc"] +
                "; exchangedate: " + data[i]["exchangedate"]
            );
        }
    });
```
```js
// Пример чтения json-данных randomuser.me с деструктаризацией
// https://randomuser.me/documentation
const getRandomUser = n => {
    const fetchRandomUsers = fetch(`https://randomuser.me/api/?results=${n}`);
    fetchRandomUsers.then(data => {
        data.json().then(randomUsers => {
            console.log(JSON.stringify(randomUsers.results.length));
            randomUsers.results.forEach(u => {
                const {gender, email} = u;
                console.log(`${gender} - ${email}`);
            })

            // randomUsers.results
            //     .filter(u => u.gender === "female")
            //     .forEach(u => {
            //         console.log("They are: " + u.name.first + ", age: " + u.dob.age)
            //     });

        });
    });
}

getRandomUser(100);
```

### Пример итерации по мапе
[Object.entries](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Object/entries) используется с деструктаризацией:
```js
for (const [key, value] of Object.entries(object1)) {
    console.log(`${key}: ${value}`);
}
```
Если у значения возвращается `Object object`, тогда можно получить значения через имя в квадратных скобках - `${value["id"]}`. Реальный пример:
```js
let jsonResponseMap;
const jsonUrl = "http://localhost:8080/nbu";

fetch(jsonUrl)
    .then(response => response.json())
    .then(data => {
        jsonResponseMap = data;

        addNewOptions();
        showAllCurrenciesInfo();
    })
    .catch(error => {
        console.log('Возникла проблема с вашим fetch запросом: ', error.message);
    });

function addNewOptions() {
    let result = document.querySelector("#listOfCurrencies");
    let select = document.getElementById("nbuList");
    
    for (const [key, value] of Object.entries(jsonResponseMap)) {
        // console.log(`${key}: ${value["amount"]}`);

        let newOption = document.createElement("option");
        newOption.value = `${value["id"]}`;
        newOption.text = `${value["currencyCode"]}`;
        select.add(newOption);
    }
    
    result.appendChild(select);
    result.insertBefore(select, result.children[0]);
}
```



## [Promise](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Promise) 
Объект Promise (промис) используется для отложенных и асинхронных вычислений.

[Amigoscode](https://youtu.be/dOnAC2Rr-6A?t=11386) > Промис - представляет значение, которое может быть доступно в данный момент, или в будущем, или никогда.

Может имеет 3 состояния: до того как рез-т готовый, промис находится в состоянии ожидания - **Pending**. 
Как только рез-т готовый - если успешно, то это **Fulfilled**, а если с ошибкой - **Rejected**.

> Если есть несколько промисов - порядок выполнения не гарантируется!
  Для фиксированного порядка используй `Promise.all([a,b])`.


### Метод then
В случае успешного вычисления промиса (promise выполнился), чтобы получить от него какой-то рез-т, нужно использовать спец-метод `.then`: 

`somePromise.then()` - в нем (внутри then) указывается что будет, если Promise выполнился. В случае ошибки дело до этого метода не дойдёт.

На промис можно добавить метод `then`. Возвращает промис - надо обработать чтобы использовать.
Метод `then` у промиса называют **Fulfilled handler**.
```js
responsePromise.then(response => { 
    console.log(response) 
});
```



## [Generator](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Generator)
Генераторы являются функциями с возможностью выхода и последующего входа. Их контекст исполнения (значения переменных) сохраняется при последующих входах.
```js
// Пример 1
const getNumbers = function* () {
    yield 1;
    yield "hello";
    yield true;
    yield { name: "Bob"};
    return "That's all folks";
}

const numbersGen = getNumbers();

console.log(numbersGen.next().value); // 1
console.log(numbersGen.next().value); // hello
console.log(numbersGen.next().value); // true
console.log(JSON.stringify(numbersGen.next().value)); // {"name":"Bob"}
console.log(numbersGen.next().value); // That's all folks
```
```js
// Пример 2
const getNumbers = function* (numbers) {
    for (let i = 0; i < numbers.length; i++) {
        yield numbers[i];
    }
}

const getNumbersGen = getNumbers([1,2,3,4,5]);

const interval = setInterval(() => {
    const next = getNumbersGen.next();
    if (next.done) {
        log("this generator is done");
        clearInterval(interval);
    } else {
        const number = next.value;
        log(number);
    }
}, 1000);
```
To use generators with promises, you have to install package called `bluebird` in the web or `co` in backend - node.

### bluebird package
Благодаря `bluebird` кода меньше, читаемость и поддержка лучше:
```js
// обычный пример на fetch:
const getRandomUsers = n => {
    const fetchRandomUsers = fetch(`https://randomuser.me/api/?results=${n}`)
    fetchRandomUsers.then(data => {
        data.json().then(randomUsers => {
            console.log(JSON.stringify(randomUsers.results.length));
            randomUsers.results.forEach(user => {
                const {gender, email} = user;
                console.log(`${gender} - ${email}`);
            });
        })
    });
}

getRandomUsers(100);
```
```js
// переписанный пример на bluebird:

import { coroutine as co } from 'bluebird';

const getRandomUsers = co(function* (n) {
    const fetchRandomUsers = yield fetch(`https://randomuser.me/api/?results=${n}`)
    const data = yield fetchRandomUsers.json();
    return data;
});

getRandomUsers(10).then(randomUsers => {
    randomUsers.results.forEach(user => {
        const {gender, email} = user;
        console.log(`${gender} - ${email}`);
    });
}).catch(err => console.log);
```



## [async await](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Statements/async_function) 
> __The async is match cleaner than using corotines (co) with generators and promises.__
```js
// Пример хорошего использования - раньше так делали
const getRandomUsers = async n => {
    try {
        const fetchRandomUsers = await fetch(`https://randomuser.me/api/?results=${n}`)
        const data = await fetchRandomUsers.json();
        data.results.forEach(user => {
            const {gender, email} = user;
            log(`${gender} - ${email}`);
        });
        return data;
    } catch (err) {
        log(err);
    }
};

getRandomUsers(5);
```
После вызова функция `async` возвращает `Promise`.

Функция `async` может содержать выражение `await`, которое приостанавливает выполнение функции async и ожидает ответа от переданного `Promise`, затем возобновляя выполнение функции `async` и возвращая полученное значение.

Ключевое слово `await` допустимо только в асинхронных функциях. В другом контексте вы получите ошибку `SyntaxError`.

> Цель функций `async`/`await` упростить использование promises синхронно и воспроизвести некоторое действие над группой `Promises`. 
> Точно так же как `Promises` подобны структурированным колбэкам, `async`/`await` подобна комбинации генераторов и `promises`.

```js
// пример с промисами
async function logName(name) {
    console.log(name);
    const transformName = new Promise((resolve, reject) => {
        setTimeout(() => resolve(name.toUpperCase()), 3000);
    });

    let result = await transformName;

    return result;
}

logName("antonio").then(res => {
    console.log("result from async function = " + res);
});
```



## Methods

<hr>

### [Массивы](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array)
> Ни размер JavaScript-массива, ни типы его элементов не являются фиксированными. 

Поскольку размер массива может увеличиваться и уменьшаться в любое время, то нет гарантии, что массив окажется плотным. 
Т.е., при работе с массивом может возникнуть ситуация, что элемент массива, к которому вы обратитесь, будет пустым и вернёт `undefined`. 
В целом, это удобная характеристика, но если эта особенность массива не желательна в вашем специфическом случае, вы можете рассмотреть возможность использования типизированных массивов.

Array can store values of any type like this:
```js
// простые значения, объекты, функции
let arr = [
    "Orange", 
    {
        name: "Insha"
    },
    true,
    function () {
        console.log('hello');
    }
];
```
В примере выше можно обратиться к анонимной функции так: `arr[3]()`.

<hr>

#### `reduce`
Применяет действие для каждого элемента в массиве - условие и дефолтное значение.
```js
const findMaxConsecutiveOnes2 = (nums, cur = 0) =>
    nums.reduce((max, num) => num ? Math.max(++cur, max) : (cur = 0, max), 0);
```

<hr>

#### `filter`
Создание массива на основе другого с использованием фильтра:
```js
let a = [3, -12, 0, 4, 5, -8];
let c = a.filter(function (a) {
    return a >= 0;
});
```

<hr>

#### `push`
Добавление элемента в конец массива, возвращает актуальную длину массива.

<hr>

#### `shift` и `unshift`
Добавляет элемент в начало массива, и удаляет соответственно.

<hr>

#### `pop`
Возвращает последний элемент массива и удаляет его из массива.
```js
let a = [5, 6, 8];
console.log(a); // [ 5, 6, 8 ]
let b = a.pop();
console.log(a); // [ 5, 6 ]
console.log(b); // 8
```

<hr>

#### `every`
Метод возвращает `true` или `false`, если КАЖДЫЙ элемент соответствует заданному условию. 
Для отображения рез-а нужно вывести либо на консоль, либо занести в переменную. 
Метод делает проверки с каждым элементом, если хотя бы в одном `false` - вернет `false`.
```js
let arr = [3, 4, 5, 9];
let a = arr.every(function (b) {
    return b < 5;
});
console.log(a); // false
```

<hr>

#### `some`
Метод возвращает `true` или `false`, если хотя бы один элемент массива соответствует заданному условию. 
Для отображения рез-а нужно вывести либо на консоль, либо занести в переменную.
```js
let arr = [3,4,5,9];
let a = arr.some(function (b) {
    return b<5;
});
console.log(a); // true
```

<hr>

#### `Array.from`
Метод [Array.from()](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array/from) 
создаёт новый экземпляр `Array` из массивоподобного или итерируемого объекта.

```js
console.log(Array.from('foo')); // Array ["f", "o", "o"]
console.log(Array.from([1, 2, 3], x => x + x)); // Array [2, 4, 6]
```

<hr>

#### `map` 
Mapping is an operation which applies a function to every element of a collection and __ALWAYS__ returns a new collection with elements changed by the mentioned function.
```js
const nums = [1, 2, 3, 4];
const biggerNums = nums.map((n) => n * 2);
// >> [2, 4, 6, 8];
```

<hr>

#### Spread and Rest operators - `...` 
[dev.to](https://dev.to/ibn_abubakre/spread-vs-rest-operator-199d)
|| [stackoverflow](https://stackoverflow.com/questions/33898512/spread-syntax-vs-rest-parameter-in-es2015-es6)

Оператор **spread** `...` используется чтобы разбить массив на отдельные элементы, а также используется для копирования массивов.

> Spread-оператор, в отличии от rest-оператора может появляться в любой части массива, и можно применять несколько spread-операторов в одном вызове функции.
```js
// Пример слияние
var abc = ['a', 'b'];
var def = ['c', 'd'];
var alpha = [ ...abc, ...def ];
console.log(alpha) // alpha == ['a', 'b', 'c', 'd'];
```

Оператор **rest** записывается так само как и **spread** т.е. `...`, только у него другие предназначения - он помагает собрать все элементы в массив или объект.
```js
function sum( first, ...others ) {
    for ( var i = 0; i < others.length; i++ )
        first += others[i];
    return first;
}
console.log(sum(1,2,3,4))// sum(1, 2, 3, 4) == 10;
```

<hr>

#### Деструктуризации массива
[learn.javascript.ru](https://learn.javascript.ru/destructuring-assignment)

**«Деструктуризация»** не означает **«разрушение»**. 
**«Деструктурирующее присваивание»** не уничтожает массив. 
Оно вообще ничего не делает с правой частью присваивания, его задача – только скопировать нужные значения в переменные.

**«Деструктуризация»** это просто короткий вариант записи:
```js
// let [firstName, surname] = arr; <- деструктуризация
let firstName = arr[0];
let surname = arr[1];
```
Пропускай элементы, используя запятые
```js
// второй элемент не нужен
let [firstName, , title, , , name] = ["Julius", "Caesar", "Consul", "Republic", "Zip","Mike"];
console.log( title ); // Consul
console.log( name ); // Mike
```
Если в массиве меньше значений, чем в присваивании, то ошибки не будет. Отсутствующие значения считаются неопределёнными.

Если нам необходимо указать значения по умолчанию, то мы можем использовать `=`.

Значения по умолчанию могут быть гораздо более сложными выражениями или даже функциями. Они выполняются, только если значения отсутствуют.

Деструктурирующее присваивание также работает с объектами:
```js
let options = {
    title: "Menu",
    width: 100,
    height: 200
};

let {title, width, height} = options;
console.log(title);  // Menu
console.log(width);  // 100
console.log(height); // 200
```



## [Объекты](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object)
Объект - это коллекция сгрупированных переменных. В объекте ими выступают свойство/ключ со значением.
Объект использует фигурные скобки.

Пример простого объекта:
```js
var obj = {};
```
Пример объекта со значениями:
```js
var person = {
    name: "Bob Singer",
    age: 21,
    hasDriverLicence: true
}
```
Если вывести объект на экран, получим `[object Object]`.

Объект можно представить в виде JSON. Для этого есть метод `JSON.stringify()` - converts a JavaScript object or value to a JSON string.
```js
console.log(JSON.stringify(person));
```
получим:
```json
{
  "name": "Bob Singer",
  "age": 21,
  "hasDriverLicence": true
}
```

### Создание объекта
* В JS объекты создаются с помощью ключа и значения.
* Свойста и методы разделяются запятыми, по после последнего свойства - не ставится запятая.
* В качестве типа ключа для объекта всегда должна быть строка! Строку можно писать без кавычек, даже если там будет пробел.
* Метод в объекте состоит из ключа и функции.

Пример создания объекта 1:
```js
const hotel = {
    "name" : "U doma",
    "stars" : 3,
    "adv" : function () {
        console.log(this.name + ' best hotel. Stats: ' + this.stars);
    }
};
```

Пример создания объекта 2:

* Сначало создаем пустой объект,
* потом создаем свойство `hi` и присваиваем ему значение.
* **Порядок вывода всех свойтв объекта не гарантируется как добавлялся!**
```js
// Пример создания объекта 2:
let a = {};
a.hi = "test";
```

<hr>

### Чтение свойств объекта
Если свойство имеет односоставное имя без пробела, например "hi", к такому свойству можно обратиться как и в JAVA через точку.

Если обратиться к свойству с пробелом через точку, например "how much" - это приведет к ошибке, т.к. обращаться нужно только через квадрартные скобки:
```js
const test1 = {
    hi: "",
    "how much": 500
}
console.log(test1["how much"]); // 500
```

Обращаться к ключу через переменную нужно передавая её в квадратные скобки.
```js
const hotel = {
    "name": "U doma",
    "stars": 3,
    "adv": function () {
        console.log(this.name + ' best hotel. Stats: ' + this.stars);
    },
    hi: "",
    "how match": 500
};

let b = "how match";
console.log(hotel.b);  // undefined
console.log(hotel[b]); // 500
```

Получить коллекцию всех значений объекта можно с помощью команды `values` - аналогично в java получения значений мапы:
```js
Object.values(person); // Bob Singer,21,true
```

Внутри объекта могут быть другие объекты:
```js
var person = {
    name: "Bob Singer",
    age: 21,
    hasDriverLicence: true,
    dateOfBirth: "01/01/2000",
    address: {
        firstLine: "123",
        postCode: "SE1",
        country: "England"
    }
}
```
С помощью `JSON.stringify(person)`, получим json в json:
```json
{
  "name": "Bob Singer",
  "age": 21,
  "hasDriverLicence": true,
  "dateOfBirth": "01/01/2000",
  "address": {
    "firstLine": "123",
    "postCode": "SE1",
    "country": "England"
  }
}
```

<hr>

### Удаление свойства объекта
```js
const hotel = {
    "name": "U doma",
    "stars": 3,
    "adv": function () {
        console.log(this.name + ' best hotel. Stats: ' + this.stars);
    },
    hi: "hello",
    "how match": 500,
    "und" : undefined
};
delete hotel.hi;
```

<hr>

### Проверка заданного свойтва/ключа внутри объекта
Проверяет - есть ли заданное свойтво/ключ внутри объекта - возвращает `boolean`.
Чему равно его значение - не важно.

Есть два варианта - с использование `in` и `hasOwnProperty`:
```js
const hotel = {
    "name": "U doma",
    "stars": 3,
    "adv": function () {
        console.log(this.name + ' best hotel. Stats: ' + this.stars);
    },
    hi: "",
    "how match": 500,
    "und" : undefined
};

// variant 1:
let yes = "und" in hotel;
console.log(yes);  // true

// variant 2:
let yes1 = hotel.hasOwnProperty("und1");
console.log(yes1); // false
```

<hr>

### Клонирование объекта не по ссылке
Пример как сделать клон объекта (не по ссылке) - своего рода заполнение массива:
```js
let a = {
    one : "Hello",
    name : 23
};
let b = {};
for (let key in a) {
    b[key] = a[key];
}
```

<hr>

### Деструктуризация объекта
Во вложенности имя переменной задается с правой стороны - отличается по цвету в IDE.
Имя задается после `:`.
```js
// имеется объект
const getUser = () => {
    return {
        name: 'John',
        surname: 'Doe',
        gender: 'male',
        address: {
            country: 'United States',
            city: 'California',
            postCode: 'CA',
            fullAddress: {
                doorNumber: 22,
                street: 'LA st'
            }
        },
        age: 29
    }
};

// сохраняем объект в переменную
const user = getUser();

// Деструктуризация объекта
const {name, age, address: {country: theCountry}} = user;
const {address: {fullAddress: {doorNumber: number}}} = user;

log(name);
log(age);
log(theCountry);
log(number);
```
```js
// в первом случае, названия name и age должны совпадать с названиями в объекте:
const {name, age, address: {country: theCountry}} = user;

// во втором случае, мы задаем другие имена переменных - для name переменная n...:
const {name : n, age : a, address: {country: theCountry}} = user;
```

<hr>

### Мапа в JS - `new Map`
Map не имеет метода сортировки. 
Для сортировки необходимо мапу обернуть в массив, и на нём вызвать метод sort:
```js
let myJson = {
    "12": {
        "id": 12,
        "currencyRate": 0.1942,
        "currencyCode": "DZD",
    },
    "36": {
        "id": 36,
        "currencyRate": 19.2436,
        "currencyCode": "ZAC",
    },
    "50": {
        "id": 50,
        "currencyRate": 0.31132,
        "currencyCode": "BDT",
    },
    "51": {
        "id": 51,
        "currencyRate": 0.054972,
        "currencyCode": "AMD",
    }
};

let myMap = new Map([...Object.entries(myJson)].sort(([,a], [,b]) => {
    if (a["currencyCode"] > b["currencyCode"]) return 1;
    else if (a["currencyCode"] < b["currencyCode"]) return -1;
    return 0;
}));
```

<hr>

### Создание класса с конструктором
Пример создания класса с конструктором и вызовом экземпляра.
Как и в Java срабатывает 1 раз при создании объекта.
```js
class Animal {
    constructor(name, age) {
        console.log(`${name} is an animal and was created`);
        this.name = name;
        this.age = age;
    }

    eat() {
        console.log(`${this.name} is eating`);
    }

    sleep() {
        console.log(`${this.name} is sleeping`);
    }
}

const bobby = new Animal("bobby", 2);
bobby.eat();
bobby.sleep();
```

<hr>

### Наследование в JS
Такое же как в Java. Используется ключевое слово `extend`. Обращение к родителю также через `super`.

<hr>

### Статические методы
Такое же как в Java с ключевым словом `static`. Обращение - через имя класса `Animal.myStaticMethod()`.

<hr>

### Пример класса с LeetCode
[Пример реализации стека](https://leetcode.com/problems/min-stack/discuss/1539426/JavaScript-Easy-to-understand-detailed-explanation-and-O(1)-for-all)
```js
class MinStack {
  constructor() {
    this._top = -1;
    this.data = [];
    this.min = Number.MAX_SAFE_INTEGER;
  }
  push(n) {
    this.data[++this._top] = this.min;
    this.data[++this._top] = n;
    n < this.min && (this.min = n);
  }
  pop() {
    this.min = this.data[--this._top];
    --this._top;
  }
  top() {
    return this.data[this._top];
  }
  getMin() {
    return this.min;
  }
}
```

<hr>
<hr>





