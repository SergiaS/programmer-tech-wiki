# JavaScript
Как программист Java учил JavaScript...

* Всплытие / поднятие (Hoisting) переменных (var) относитя только к их объявлению (даже если она инициализирована).

* С помощью `===` можно сравнить две строки или два булевых значения. 
  Если же сравнивать так значения разных типов, ответом всегда будет `false`.
  ```js
  console.log("0" === 0); // false
  console.log(0 === false); // false
  ```
* Двойное равно, `==`, означает «практически равно». Не проверяет тип.
  Используйте его для проверки двух значений на соответствие друг другу, даже если одно из них строка, а другое — число.
  ```js
  console.log("0" == 0); // true
  console.log(0 == false); // true
  ```

* JavaScript использует значение `undefined`, когда не может найти иного значения. 
  Например, если, создав новую переменную, вы не присвоите ей значение с помощью оператора `=`, ее значением будет `undefined`.
    ```js
    var myVariable;
    console.log(myVariable); // undefined
    ```
* Значение `null` обычно используется, чтобы явно обозначить — «тут пусто»
    ```js
    var myNullVariable = null;
    myNullVariable; // null
    ```
* JavaScript при сравнении первым делом пытается преобразовать значения к одному типу. 
  В данном случае булево значение он преобразует в числовое — при этом `false` становится `0`, а `true` — `1`. 
  Поэтому, сравнивая `0 == false`, вы получите `true`!
    ```js
    0 == false; // true
    "false" == false; // false
    ```
* При присваивании элементу стиля, название css-свойства следует писать по стандарту **camelCase** - касается тех свойст, в названии которых присутствует дефис `-`.

* В JavaScript есть две чаще всего используемые структуры данных – это `Object` и `Array`.

* Методы в JS называются функциями, - могут записываться по-разному:
    ```js
    // обычный вариант
    function addNewElements() {
        // some code
    }
  
    function addNewElements2(arr) {
        // some code
    }
    ```
    ```js
    // стрелочная функция
    addNewElements = () => {
        // some code
    }
    addNewElements2 = (arr) => {
        // some code
    }
    ```
* Regex в JS указывается без кавычек, например в методе `replace`. 
  А `replaceAll` походу не работает - используй модификатор `g`.
  Не забываем про экранирование, в отличии от Java в JS слеш ставится в другую сторону - `/`.
  Паттерн указывается в квадратных скобках.
  > [Модификаторы regex](https://www.w3schools.com/jsref/jsref_obj_regexp.asp). 
  > The `g` character makes it a "global" match, meaning it repeats the search through the entire string.
    ```js
    // паттерн на поиск и замену всех точек
    let email = "test.e.mail+bob.cathy@leetcode.com";
    
    console.log(email.substring(0, 21).replace(/[.]/g, ""));
    // testemail+bobcathy
    ```
* In JavaScript, all functions return a value. 
  Even if we don't use the `return` keyword, the function will return `undefined` implicitly. 
  That's how the language has been designed. 
  This rule also applies to callbacks - they are functions too.



## Import and Export

### Подключение scss:
```js
require('styles/main.scss');
```

### Подключение переменных/функций с другого файла
```js
import { log, logTitle } from 'logger';
```
при этом, данные в файле должны быть помечены словом `export`:
```js
import $ from 'jquery';
export const log = content => $('#content').append("<i style = 'color: black' class = 'fa fa-terminal'> </i>  " + content + "<br>" );
export const logTitle = title => $('#title').append(title);
```
Можно указать как мы будем обращаться к данным в другом файле, например `as someMath`.
При импорте указывать `.js` не нужно.
```js
import * as someMath from './Math';

console.log(someMath.add(2,2));
console.log(someMath.substract(2,2));
console.log(someMath.divide(2,2));
console.log(someMath.multiply(2,2));
console.log(someMath.PI);
```
Если нужно использовать только некоторые функции, а не все (`*`) - тогда не нужно использовать `as someName` и обращаемся на прямую:
```js
import {add, divide, PI} from './Math';

console.log(add(2,2));
console.log(divide(2,2));
console.log(PI);
```


## JS features:

<hr>

### Указания классов и идентификаторов
Класс указываются как `.list` - для `<div class="list">...</div>`.

Идентификатор - `#carl` - для `<div id="carl">...</div>`.

Для обычных селекторов типа `<p>, <ul> ...` - `ul`.

<hr>

### Узнать тип переменной
```js
console.log(typeof arr); // object
```

<hr>

### Присвоение значений в функциях
Присвоение значений в функциях по умолчанию, без костылей, возможно с ES6.
```js
function summa(a = 0, b = 0) {
console.log(a+b);
}

let c = 10;
summa(4);
```

<hr>

### Стрелочная функция
Стрелочная функция похожа на лямбду. 
Если параметров нет - нужно указывать скобки. 
Если параметр один - можно не указывать скобки.
```js
let myFunc = () => {
    console.log('work');
    console.log("Hello" + " world");
}
```

<hr>

### Callback функции
Это функция, которая выполняется в качестве параметра внутри другой функции. Способ борьбы с асинхронностью.

Можно передавать не только функцию обертку, но и анонимную.
В примере в анонимной функции запускается функция `second(x,y)` с двумя аргументами.
```js
function first(y){
    console.log(1);
    y();
}

function second(x,y){
    console.log(x*y);
}

first(function () {
    second(5,7);
});
```

Пример от amigoscode:
```js
function callbackExample(name, callback) {
    console.log(callback(name));
}

callbackExample("Bob Singer", function (name) {
    return "Hello " + name;
});
```

<hr>

### Псевдомассив
Даже если функция не ожидает на вход параметры, а мы их принудительно передаем, то они будут доступны в псевдомассиве `arguments`.
```js
function one(){
    console.log(arguments);
    for (let i = 0; i < arguments.length; i++) {
        console.log(arguments[i]);
    }
}
one(5,78,3,2);
```

<hr>

### Тернарный оператор
Имеет больше возможностей, чем в Java. По умолчанию сравнивает если значение переменной `0` или `1`:
```js
num ? ++cur > max && (max = cur) : (cur = 0);
```
Т.е., если `num === 1` тогда выполняется часть `++cur > max && (max = cur)`, а если `num === 0`, тогда вторая `(cur = 0)`.

При этом в первой части `&&` производит не сравнение, а запись - если `++cur > max`, тогда (`&&`) присваиваем `max` значение `cur`.

<hr>

### Строгий режим
Ставится в начале скрипта или метода! - говорит браузеру, что скрипт жёстко придерживается стандарта ECMAScript.
```js
use strict
```

<hr>

### `defer` - загрузка скрипта после загрузки страницы
Аттрибут, который используется при подключении скрипта на HTML-странице. 
Означает, что скрипт будет загружен только после загрузки всей страницы. 
Но в основном так не делают, а просто подключают скрипты внутри тега `<body>`, т.е. после всех данных.
```html
<script src="js/script.js" defer></script>
```

<hr>

### `async` - выполнение скрипта асинхронно
Данный аттрибут выполняется асинхронно, т.е. недожидается выполнения данного скрипта, и начинает читать следущий после него (если конечно он там есть) - что-то типа многопоточности.
```html
<script src="js/script.js" async></script>
```

<hr>

### Замыкание
Замкнул одну функцию в другой функции. 
Пример счётчика на которого никто не может повлиять. 
Типа статика в JAVA %)
```js
function cs2() {
    let c = 0;
    return function () {
        c++;
        console.log(c);
    }
}

let a1 = cs2();
a1(); // 1
a1(); // 2
a1(); // 3
```

<hr>

### Константа в JS
Константа для примитивов - это константа.

Констанка для объектов - всего лишь гарантирует, что значение переменной не изменится. Но свойства и методы подлежат изменению. 
Т.е., константа у объекта означает, что ссылка на данный объект меняться не может!
Но благодаря спец.методу объект таки можно заморозить:
`const d = Object.freeze({"d" : 33});`
```js
const c = {};
c.a = 88;
c.a++;
console.log(c); // { a: 89 }
```

<hr>

### Интерполяция
Интерполяция - способ соединения строк через вставку значений переменных в строку-шаблон с помощью фигурных скобок. 
Например, `` `Hi, ${name}!` ``.

> Интерполяция не работает с одинарными и двойными кавычками!
>
> Нужно ставить обратные кавычки (backtick - `` ` ``) и оборачивать действия в них `` `${}\` ``:
```js
for (let i = 1; i <= 9; i++) {
    out.innerHTML += "3*" + i + "=" + (i * 3) + "<br>";
    out.innerHTML += `3 * ${i} = ${3 * i}<br>`;
}

for (let i = 1; i <= 9; i++) {
    for (let k = 1; k < 10; k++) {
        out.innerHTML += `${i} * ${k} = ${3 * i}<br>`;
    }
    out.innerHTML += "<hr>";
}
```


<hr>
<hr>


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

<hr>
<hr>


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

<hr>
<hr>

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


<hr>
<hr>


### [Объекты](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object)
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

#### Создание объекта
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

#### Чтение свойств объекта
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

#### Удаление свойства объекта
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

#### Проверка заданного свойтва/ключа внутри объекта
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

#### Клонирование объекта не по ссылке
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

#### Деструктуризация объекта
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

### Наследование в JS
Такое же как в Java. Используется ключевое слово `extend`. Обращение к родителю также через `super`.

### Статические методы
Такое же как в Java с ключевым словом `static`. Обращение - через имя класса `Animal.myStaticMethod()`.

<hr>
<hr>



## DOM (Document Object Model)
Каждый элемент в HTML это нода (Node). Например:
```html
<p id="head1">Hello there</p>
```
Это такие же ноды, как в алгоритмах - они имеют вложенность.
Внутри `<p>` может быть лист, у листа - другие листы, заголовки, теги оформления...

<hr>

### `getElementById`
Будет браться только первый элемент.
`id` элемент должен быть вссегда уникален.
```html
<h1 id="heading">Hi there</h1>
<h3 id="heading">Test</h3>
```
```js
let heading = document.getElementById("heading");
console.log(heading); // <h1 id="heading">Hi there</h1>

let heading2 = document.getElementById("heading");
console.log(heading2); // <h1 id="heading">Hi there</h1>
```

<hr>

### `textContent`
С `getElementById` можно получить только один текст без тегов:
```js
const list = document.getElementById("list");
console.log(list.textContent);
```

Также можно изменить значение элемента динамически просто указав своё:
```js
list.textContent = "Hi there";
```

<hr>

### `innerHTML`
Позволяет получать текст с HTML элемента.
Также может динамически задавать своё значение.

<hr>

### Разница между `innerText` и `textContent`
[StackOverflow](https://stackoverflow.com/a/35213639).
`innerText` вернет видимый текст содержащийся в ноде, а `textContent` - весь.
Здесь используется стиль `style="display: none;"`.

<hr>

### `querySelector` 
Достает только на первый попавшийся селектор. 
Вернет содержимое селестора с HTML-кодом.
Если ничего не найдено - возвращает `null`.
```js
let list = document.querySelector("ul");
console.log(list);
```

<hr>

### `querySelectorAll`
Возвращает NodeList - массив элементов с указанным селектором: 
```js
let lis = document.querySelectorAll("li");
```

<hr>

### `style`
Изменить стиль можно очень просто - есть пара вариантов:
1. каждый раз указывая стиль который нужно добавить элементу:
    ```js
    let heading = document.getElementById("heading");
    heading.style.color = "red";
    heading.style.textAlign = "center";
    // <h1 id="heading" style="color: red; text-align: center;">Hi there</h1>
    ```
    Пример изменения одного элемента в массиве:
    ```js
    let lis = document.querySelectorAll("li");
    lis[1].style.backgroundColor = "red";
    ```

2. Указав метод `cssText` - далее нужно будет написать все нужные стили.
    ```js
    let lis = document.querySelectorAll("li");
    lis[0].style.cssText = "background-color: coral; color: white"
    ```

3. Уже готовый стиль с названием можно задать выбранному элементу.
   Если таким образом указано несколько стилей к одному и тому же элементу, тогда применится самый последний.
    ```js
    let heading = document.getElementById("heading");
    heading.className = "changeBG";
    // <h1 id="heading" class="changeBG">Hi there</h1>
    ```

4. Если нужно указать пару стилей одному и тому же элементу, нужно использовать `+=` 
   и следующие имя стиля писать вначале с пробелом:
    ```js
    heading.className = "changeBG";
    heading.className += " changeFT";
    // <h1 id="heading" class="changeBG changeFT">Hi there</h1>
    ```

<hr>

### `classList`
Свойство [classList](https://developer.mozilla.org/ru/docs/Web/API/Element/classList) возвращает псевдомассив `DOMTokenList`, содержащий все классы элемента.

Имеет методы управления стилями `add`, `delete` и ещё пару других.
```js
var elementClasses = elem.classList;
```

<hr>

### Events (события)
Ивенты это экшены, которые происходят на веб-странице - клик по кнопке, нажатие на клавишу...

Есть несколько способов назначить событие:
1. Пример появления надписи в консоли при нажатии на кнопку и смена стиля при наведении мыши:
    ```js
    let heading = document.querySelector(".heading");
    let btn = document.querySelector(".btn");
    
    btn.onclick = () => {
        console.log("Clicked");
    };
    
    btn.onmouseover = () => {
        heading.style.cssText = "background-color: brown; color: white;"
    }
    ```

2. Здесь нужно указать событие в HTML. 
   В данном примере событие по нажатию на кнопке отработает метод `clickButton`.
    ```html
    <button class="btn" onclick="clickButton()">Click here</button>
    ```
    ```js
    const clickButton = () => {
        console.log("Clicked");
    }
    ```
   
3. Можно вешать события с помощью [addEventListener](https://developer.mozilla.org/ru/docs/Web/API/EventTarget/addEventListener).
    ```html
    <button class="btn">Click here</button>
    ```
    ```js
    btn.addEventListener('click',() => {
        heading.style.cssText = "background-color: brown; color: white;";
    })
    ```

4. При каждом повешанном событии, автоматически создается объект события, который хранит всю инфу - на каком элементе произошло, что было нажато и т.д.
    ```js
    btn.addEventListener('click',(e) => {
        console.log(e);
    })
    ```
   Одно из главных полей - `target`:
    ```js
    btn.addEventListener('click',(e) => {
        console.log(e.target);
    })
    ```
<hr>

### Attributes (аттрибуты полей)
С помощью данных команд, можно читать/задавать значения для аттрибутов.

* __Получение аттрибута (get)__<br>
    Если какого-то аттрибута нет, вернется `null`.
    
    В примере узнаем название `id` элемента `p` с именем класса `paragraph`:
    ```html
    <p id="main-paragraph" class="paragraph">Lorem ipsum dolor</p>
    ```
    ```js
    let paragraph = document.querySelector(".paragraph");
    console.log(paragraph.getAttribute("id"));
    // main-paragraph
    ```

* __Установка аттрибута (set)__<br>
    Здесь нужно задавать два параметра - имя аттрибута и значение аттрибута.
    ```js
    let paragraph = document.querySelector(".paragraph");
    paragraph.setAttribute("style", "background-color: coral");
    ```

* __Удаление аттрибута (remove)__<br>
    Полностью удалит аттрибут:
    ```js
    paragraph.removeAttribute("style");
    ```

* __Проверка наличие аттрибута (has)__<br>
    Возвращет `boolean`:
    ```js
    console.log(paragraph.hasAttribute("style"))
    ```
<hr>

### DOM navigation
Навигация имеется ввиду вызов родителя (parent), наследника (child), брата или сестры (sibling).

1. __Чтение__<br>
    `parentNode` и `parentElement` работают практически одинаково.
    
    Например, вызов родителя текущего элемента:
    ```html
    <div class="wrapper">
        <ul class="list">
            <li>Home</li>
            <li id="list-item">About</li>
            <li>Content</li>
        </ul>
    </div>
    ```
    ```js
    let listItem = document.getElementById("list-item");
    console.log(listItem.parentNode); 
    // <ul class="list">...</ul>
    console.log(listItem.parentNode.parentNode); 
    // <div class="wrapper">...</div>
    ```
    Или, вызов наследника:
    ```js
    let list = document.querySelector(".list");
    console.log(list.childNodes); 
    // NodeList(7) [text, li, text, li#list-item, text, li, text]
    ```
    При этом `childNodes` не работает с использованием `getElementById("list)`.
    
    Пример с вызовом брата или сестры:
    ```js
    let listItem = document.getElementById("list-item");
    console.log(listItem.nextElementSibling.textContent);
    // Content
    ```

2. __Создание__<br>
   
    Пример с созданием элемента и добавлением к существующему. Новый элемент будет добавлен в конец.
    ```js
    let list = document.querySelector(".list");
    const newElem = document.createElement("li");
    const text = document.createTextNode("Blog");
    newElem.appendChild(text);
    console.log(newElem);
    list.appendChild(newElem);
    console.log(list);
    ```
    Для добавления нового элемента на конкретное место используй метод `insertBefore`:
    ```js
    list.insertBefore(newElem, list.children[1]);
    ```

3. __Удаление__<br>
   Используй метод `removeChild`:
    ```js
    let list = document.querySelector(".list");
    const newElem = document.createElement("li");
    const text = document.createTextNode("Blog");
    newElem.appendChild(text);
    console.log(newElem);
    list.appendChild(newElem);
    list.insertBefore(newElem, list.children[1]);
    list.removeChild(newElem)
    console.log(list);
    ```

<hr>
<hr>

## Examples

### [LeetCode - 485. Max Consecutive Ones](https://leetcode.com/problems/max-consecutive-ones/)

Нужно найти самую длинную последовательность из 1 в массиве:
```js
const findMaxConsecutiveOnes = (nums) => {
    let max = 0;
    let cur = 0;

    for (const num of nums) {
        num ? ++cur > max && (max = cur) : (cur = 0);
    }
    return max;
}
```

### Snippets
* Код будет возвращать случайное число от 0 до 3 (включая 0 и 3)
    ```js
    Math.floor(Math.random() * 4);
    ```
* Задержка выполнения метода:
    ```js
    setTimeout(move, 250); // метод, время в мс
    ```
* Пример окрашивания кнопки в выбранный цвет.
    ```html
    <button id="one">Click me</button>
    <button id="two">Click me</button>
    ```
    ```js
    document.querySelector("button#two").onclick = () => {
        // присваиваем кнопке стиль
        document.querySelector("button#two").style.backgroundColor = "red";
    };
    ```
* Вешаем событие на блок/элемент.
  Помещаем в досье элемента (Elements - Properties -имя_элемента) one в поле `onclick` ссылку на функцию `myClick` (указывать надо без скобок).
    ```html
    <button id="one">Click me</button>
    ```
    ```js
    let one = document.querySelector("#one");
    console.log(one);
    function myClick() {
        console.log("click"); // click - при нажатии
        one.onclick = null;
    }
    one.onclick = myClick;
    ```
* Получаем данные с поля по селектору с HTML-страницы и добавляем инфу на HTML-страницу (то что было + Hello):
    ```js
    function func(param) {
        document.querySelector(param).innerHTML += " Hello";
    }
    func("#three");
    func("#two");
    func("#four");
    ```
* Пример сортировки JSON объекта => [Sort JSON Object Array Based On A Key Attribute](https://www.c-sharpcorner.com/UploadFile/fc34aa/sort-json-object-array-based-on-a-key-attribute-in-javascrip/)
    ```js
    fetch('https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json')
        .then(response => response.json())
        .then(data => {
            ans.sort(sortByProperty("cc"));
            addNewOptions();
        });
    
    function sortByProperty(property) {
        return (a, b) => {
            if (a[property] > b[property]) return 1;
            else if (a[property] < b[property]) return -1;
            return 0;
        }
    }
    
    function addNewOptions() {
        let result = document.querySelector("#listOfCurrencies");
        let newSelect = document.createElement("select");
        for (let i = 0; i < ans.length; i++) {
            let newOption = document.createElement("option");
            newOption.value = ans[i]["txt"];
            newOption.text = ans[i]["cc"];
            newSelect.add(newOption);
        }
        result.appendChild(newSelect);
    }
    ```
* Добавление `event` к `select`:
    ```js
    let newSelect = document.createElement("select");
    newSelect.setAttribute("onchange","getRate()");
    ```
* Проверка на `null || undefined || ''` и присваивание значения через `||` - выполняется коротко:
    ```js
    // длинный вариант 
    if (variable1 !== null || variable1 !== undefined || variable1 !== '') {
         let variable2 = variable1;
    }
    ```
    ```js
    // короткий вариант
    let variable1 = undefined;
    const variable2 = variable1  || 'new';
    console.log(variable2); // new
    ```
* Пример итерации подобно лямбда в JAVA. 
  В методе сохраняется значение `index`, но только на массиве - итеративна (передавая каждое значение через =>) не работает:
    ```js
    function logArrayElements(element, index, array) {
      console.log("a[" + index + "] = " + element);
    }
    [2, 5, 9].forEach(logArrayElements);
    // a[0] = 2
    // a[1] = 5
    // a[2] = 9
    ```
    ```js
    // для тестов в LeetCode
    function logArrayElements(element) {
        return element;
    }
    [2, 5, 9].forEach(n => console.log(logArrayElements(n)));
    ```

* Пример записи значений объекта:
    ```js
    // длинный вариант
    const x = 1920, y = 1080;
    const obj = { x:x, y:y };
    ```
    ```js
    // короткий вариант
    const obj = { x, y };
    ```
* Примеры записи стрелочной функции:
    ```js
    // обычные методы
    function sayHello(name) {
        console.log('Hello', name);
    }
    
    setTimeout(function() {
        console.log('Loaded')
    }, 2000);
    
    list.forEach(function(item) {
        console.log(item);
    });
    ```
    ```js
    // аналог в стрелочных функциях
    sayHello = name => console.log('Hello', name);
    
    setTimeout(() => console.log('Loaded'), 2000);
    
    list.forEach(item => console.log(item));
    ```
* Пример не явного возврата `return`:
    ```js
    // длинный вариант
    function calcCircumference(diameter) {
      return Math.PI * diameter
    }
    ```
    ```js
    // короткий вариант
    calcCircumference = diameter => (
        Math.PI * diameter
    );
    console.log(calcCircumference(3.14));
    ```
* Деструктурирующее присвоение:
    ```js
    // длинный вариант
    const observable = require('mobx/observable');
    const action = require('mobx/action');
    const runInAction = require('mobx/runInAction');
    
    const store = this.props.store;
    const form = this.props.form;
    const loading = this.props.loading;
    const errors = this.props.errors;
    const entity = this.props.entity;
    ```
    ```js
    // короткий вариант
    import { observable, action, runInAction } from 'mobx';
    
    const { store, form, loading, errors, entity } = this.props;
    ```
* Многострочная запись с использованием backtick `` ` `` - без плюсов и переносов:
    ```js
    // длинный вариант
    const lorem = 'Lorem ipsum dolor sit amet, consectetur\n\t'
        + 'adipisicing elit, sed do eiusmod tempor incididunt\n\t'
        + 'ut labore et dolore magna aliqua. Ut enim ad minim\n\t'
    ```
    ```js
    // короткий вариант
    const lorem = `Lorem ipsum dolor sit amet, consectetur
        adipisicing elit, sed do eiusmod tempor incididunt
        ut labore et dolore magna aliqua. Ut enim ad minim`
    ```
* Exponent Power:
    ```js
    Math.pow(2,3); // 8 = 2 * 2 * 2
    Math.pow(2,2); // 4 = 2 * 2
    Math.pow(4,3); // 64 = 4 * 4 * 4
    ```
    ```js
    // сокращенный вариант
    2**3 // 8
    2**4 // 4
    4**3 // 64
    ```
* Converting a String into a Number:
    ```js
    const num1 = parseInt("100");
    const num2 = +"100";
    ```
* Назначение свойств объекта
    ```js
    let fname = { firstName : 'Black' };
    let lname = { lastName : 'Panther'}
    
    let full_names = Object.assign(fname, lname);
    ```
* Побитовое `IndexOf`:
    ```js
    if(arr.indexOf(item) > -1) { // Confirm item IS found
        // some code
    }
    
    if(~arr.indexOf(item)) { // Confirm item IS found
    // some code
    }
    ```
* Опраделение позиции символа:
    ```js
    var myString = "Happy birthday";
    
    /* Shorthand */
    console.log(myString[4]); // y
    
    /* Longhand */
    console.log(myString.charAt(4)); // y
    ```
* Декларирование объекта:
    ```js
    /* Shorthand */
    var myObj = { name: "Sean Connery", placeOfBirth: "Edinburgh",
    age: 86, wasJamesBond: true };
    
    /* Longhand */
    var myObj = new Object();
    myObj.name = "Sean Connery";
    myObj.placeOfBirth = "Edinburgh";
    myObj.age = 86;
    myObj.wasJamesBond = true;
    ```
* Преобразование `String` в `Array`:
    ```js
    const name = "Bob Singer";
    const nameToArray = [...name];
    for (const ch of nameToArray) {
        console.log(ch);
    }
    ```


<hr>

## Литература, статьи, видео
* [Примеры записи функций, свойст объекта](https://www.youtube.com/watch?v=dOnAC2Rr-6A)
* [Деструктурирующее присваивание](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Operators/Destructuring_assignment)
* [20 Killer JavaScript One Liners](https://dev.to/saviomartin/20-killer-javascript-one-liners-94f?fbclid=IwAR01WZ6eZokN-vSF5CHQI5pk48N49ULjNWYcN25YHCy7Q6G65DPCKf8YCaE)
* [Top 35 JavaScript Shorthands for Beginners](https://morioh.com/p/05414714e685?f=5c21fb01c16e2556b555ab32&fbclid=IwAR2mgqPBKtBzkyQ1CgwdwkddazUDcV3aFZ22RKOoOMi90_LcqqLMfddFbP8)
* [Random User Generator API](https://randomuser.me/documentation)

## Additional info
* ESLint - следит за установленным стандартом, т.е. используются качывки одинарные или двойные, ставится точка с запятой в конце или нет... В IDEA есть плагин.
