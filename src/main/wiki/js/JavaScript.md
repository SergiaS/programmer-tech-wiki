# JavaScript
* [JavaScript Equality Table](https://dorey.github.io/JavaScript-Equality-Table/)
* [Pure JS Table Filter or Search | How to add Filter In HTML CSS Table](https://morioh.com/p/51dbc30377fc?f=5c21fb01c16e2556b555ab32&fbclid=IwAR0g9F8mHU7gMEGSFde-Sc_hs8y--VSmHxI2PjbOoo0YAyvT2sgaLUAJ3Vc)
* [JSON generator](https://randomuser.me/api/?results=50)

> Коли ставити дужки у метода `()`, а коли - ні?
> Метод з дужками каже що при читанні скрипта функція одразу виконається, а без - тільки за якоїсь дії користувача.

> Значення які дорівнюють `false` - `0, '', null, underfined, NaN`.
> А для `true` - `1`.
> Можна приводити значення до типу `boolean` поставивши перед ним два знаки оклику `!!`:
> ```js
> console.log(typeof(!!'444')); // boolean
> ```

> Передача по значенню відбувається при роботі з примітивними типами даних (`Number`, `String`),
> а передача по лінку - коли працюємо з об'єктами (функції, масиви, об'єкти).
> ```js
> // Передача за значенням
> let a = false;
> let b = a;
> b = true;
> console.log(a); // false
> ```
> ```js
> // Передача за посиланням
> let obj = {
>     a: false,
>     b: true
> };
> 
> let copy = obj;
> copy.a = true;
> console.log(obj.a); // true
> obj.a = false;
> console.log(copy.a); // false
> ```

> Як сховати/відобразити html-елемент:
> ```js
> // hide an element
> document.getElementById("hide_0").style.display = "none";
> 
> // show a block element
> document.getElementById("hide_1").style.display = "block";
> 
> // to go back to the default or CSS specified value
> document.getElementById("hide_2").style.display = "";
> ```

## Типи даних JS

* **Прості (примітивні) типи**:
  * числа `1`,`2`,`3`...
  * cтроки `string`,`name`...
  * логічний - `boolean` `true`/`false`
  * `null`
  * `underfined`
  * `Symbol`
* **Об'єкти**:
  * Спеціальні:
    * Масиви `[]`
    * Функції `function`
    * Об'єкт Дати (`Date`)
    * Регулярні вираження
    * Помилки (`Error`)
  * Звичайні об'єкти `{}`


## Функції
* [Замикання - Замыкание - Closure](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/js/ECMAScript/FeaturesAndSnippets.md#%D0%B7%D0%B0%D0%BC%D1%8B%D0%BA%D0%B0%D0%BD%D0%B8%D0%B5-closure)

***

* Function Declaration:
  > ```js
  > function foo() {  
  >     // some code
  > }
  > ```
  > Створюється до початку виконання скрипта, можна викликати перед оголошенням.

* Function Expression:
  > ```js
  > let foo = function() {  
  >     // some code
  > }
  > ```
  > Створюється тільки тоді, коли дійде потік коду, можна викликати тільки після оголошення.

* Стрілкові функції:
  > ```js
  > () => {
  >     // some code
  > }
  > ```
  > Не має свого контексту (`this`).


## JS у прикладах
> 0. JS це динамічна мова - вона може проводити математичні операції з різними НЕ сумісними типами, і видавати різні результати:
> ```js
> // Робота зі строками і плюсом працює як конкатенація дасть на строку:
> console.log(typeof("false" + null));    // string: "falsenull"
> // Робота зі строками і іншими операціями окрім плюсу дасть NaN:
> console.log(typeof("false" - null));    // number: NaN
> console.log(typeof("false" * null));    // number: NaN
> console.log(typeof("false" / null));    // number: NaN
> 
> // Значення які дорівнюють false - це 0, '', null, underfined, NaN:
> console.log(typeof(false + null));      // number: 0
> console.log(typeof(false - null));      // number: 0
> console.log(typeof(false * null));      // number: 0
> // Ділити на null не можна:
> console.log(typeof(false / null));      // number: NaN
> ```

> 1. Чому дорівнює такий вираз: `[] + false - null + true`?
> > <details>
> > <summary>ВІДПОВІДЬ З ПОЯСНЕННЯМИ</summary>
> >
> > **Відповідь: `NaN`.**
> >
> > **Пояснення:**
> > 
> > * При роботі з пустим масивом `[]` - він буде приведений до строкового типу даних `string`. Тобто `[]` -> `""`.
> > * Якщо ми конкатенуємо строку з іншим типом даних, то ми отримаємо тип `string`.
> > * При роботі з не математичними операціями (`-`) отримаємо `NaN` (Not a Number - наприклад, при роботі з `null`).
> > ```js
> > console.log(typeof([] + false))     // string: [] -> "" + false -> "false"
> > console.log(typeof("" + "false"))   // string: "" + "false" = "false"
> > console.log(typeof("false" - null)) // number: NaN
> > console.log(typeof(NaN + true))     // number: NaN
> > ```
> > </details>

> 2. Чому дорівнює такий вираз: `2 && 1 && null && 0 && undefined`?
> 
> > <details>
> > <summary>ВІДПОВІДЬ З ПОЯСНЕННЯМИ</summary>
> >
> > **Відповідь: `null`.**
> > 
> > **Пояснення:**
> >
> > Оператор `&&` спотикається на брехні, тобто на `false`. У виразі `2` та `1` це `true`.
> > А `false` це `0`, `''`, `null`, `underfined`, `NaN`.
> > Тому ми отримуємо `null`.
> > ```js
> > console.log(typeof([] + false))     // string: [] -> "" + false -> "false"
> > console.log(typeof("" + "false"))   // string: "" + "false" = "false"
> > console.log(typeof("false" - null)) // number: NaN
> > console.log(typeof(NaN + true))     // number: NaN
> > ```
> > </details>

> 3. Чому дорівнює такий вираз: `null || 2 && 3 || 4`?
> 
> > <details>
> > <summary>ВІДПОВІДЬ З ПОЯСНЕННЯМИ</summary>
> >
> > **Відповідь: `3`.**
> >
> > **Пояснення:**
> > 
> > 1. Оператор `&&` вищий пріорітет, тому спершу виконується він. При логічному порівнянні якщо значення рівні, буде повертатися останнє значення.
> > Тобто при `2 && 3` (логічні значення **true === true**) результатом буде `3`.
> > 2. Пам'ятаємо, що `false` це `0`, `''`, `null`, `underfined`, `NaN`.
> > Результатом вираження `null || 3` буде `3`, бо `null` це **false**, і `3` це `true`.
> > Оператор `||` достатньо/першого будь-якого `true`.
> > 3. Далі порівнюється `3 || 4`, де обидва значення дають **true**, і береться перше **true** - `3`.
> > </details>

> 4. Що покаже такий код: `+"Infinity"`?
> 
> > <details>
> > <summary>ВІДПОВІДЬ З ПОЯСНЕННЯМИ</summary>
> >
> > **Відповідь: `Infinity`.**
> >
> > **Пояснення:**
> >
> > Це строка `"Infinity"`, а `+` перед строкою змінить йому тип зі **string** на **number**.
> > Тому ми отримаємо `Infinity` типу **number** замість `"Infinity"`типу **string**`.
> > 
> > </details>

> 5. Що покаже такий код: `0 || "" || 2 || undefined || true || false`?
> 
> > <details>
> > <summary>ВІДПОВІДЬ З ПОЯСНЕННЯМИ</summary>
> >
> > **Відповідь: `2`.**
> >
> > **Пояснення:**
> > 
> > 1. Пам'ятаємо, що `false` це `0`, `''`, `null`, `underfined`, `NaN`. 
> > Вираз `0 || ""` дає нам в обох випадках `false`, якщо значення мають однаковий результат - в данному випадку повертається останнє - `""`,
> > оскільки спершу **false**, тоді шукаємо далі.
> > 2. `"" || 2` - це те саме щой `false || true` - оператор повертає **true** - `2`.
> > А надалі код вже не піде, оскільки оператору `||` потрібне будь-яке **true** - результат `2`. 
> > 
> > </details>





## Fetch API
* [Fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch)
* [Отличия fetch() от jQuery](https://developer.mozilla.org/ru/docs/Web/API/Fetch_API#отличия_от_jquery)
* [Fetch для відправки POST запиту](https://developer.mozilla.org/en-US/docs/Web/API/fetch)

После `fetch` получаем объект с ответом - [Response](https://developer.mozilla.org/ru/docs/Web/API/Response).

После `json` получаем объект **Promise**.

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

***

### Promise при роботі з `fetch`

У звичайному синхронному методі коли отримуємо **Promise**, ми працюємо методами `then` для обробки і `catch` для відловлювання помилок, 
а у `async` - асинхронному - методі замість `then` використовуємо `await` і замість `catch` обертати код у блок `try-catch`.
**Await** та **async** це свого роду синтетичний цукор **Promise** який має іншу форму запису.

> Функція, яка є асинхронною `async` - завжди повертає **Promise**.
```js
const delay = ms => {
    return new Promise(r => setTimeout(() => r(), ms))
}

const url = 'https://jsonplaceholder.typicode.com/todos'


// Звичайний метод
function fetchTodos() {
    console.log('Fetch todo started...')
    return delay(2000)
        .then(() => fetch(url))
        .then(response => response.json())
}

fetchTodos()
    .then(data => {
        console.log('Data: ', data)
    })
    .catch(e => console.log(e))


// Той самий код переписаний для асинхронного метода
async function fetchAsyncTodos() {
    console.log('Fetch todo started...')
    try {
        await delay(2000)
        const response = await fetch(url)
        const data = await response.json()
        console.log('Data: ', data)
    } catch (e) {
        console.error(e)
    }
}

fetchAsyncTodos()
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
Генераторы являются функциями с возможностью выхода и последующего входа. 
Их контекст исполнения (значения переменных) сохраняется при последующих входах.
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

Функция `async` может содержать выражение `await`, которое приостанавливает выполнение функции async и ожидает ответа от переданного `Promise`, 
затем возобновляя выполнение функции `async` и возвращая полученное значение.

Ключевое слово `await` допустимо только в асинхронных функциях. 
В другом контексте вы получите ошибку `SyntaxError`.

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
> Ні розмір JavaScript-масиву, ні типи його елементів не є фіксованими. 

> Перебір по масиву робиться завдяки циклу `for of`, а об'єкту - `for in`.

Поскольку размер массива может увеличиваться и уменьшаться в любое время, то нет гарантии, что массив окажется плотным. 
Т.е., при работе с массивом может возникнуть ситуация, что элемент массива, к которому вы обратитесь, будет пустым и вернёт `undefined`. 
В целом, это удобная характеристика, но если эта особенность массива не желательна в вашем специфическом случае, вы можете рассмотреть возможность использования типизированных массивов.

Array can store values of any type like this:
```js
// простые значения, объекты, функции
let arr = [
    "Orange", 
    {
        name: "Bob"
    },
    true,
    function () {
        console.log('hello');
    }
];
```
В примере выше можно обратиться к анонимной функции так: `arr[3]()`.

***

#### `sort`
Цей метод сортирує по символьно, оскільки навідь цифри сприймає за строки.

Для вирішення цієї проблеми треба написати свою функцію (типу Comparator в JAVA):
```js
let nums = [12,4,27,11,6];

function mySort(a, b) {
  return a-b;
}
nums.sort(mySort);

console.log(nums);  // [ 4, 6, 11, 12, 27 ]
```

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

Оператор **spread** `...` используется чтобы разбить массив на отдельные элементы, а также используется для копирования массивов (shallow copy).

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

***

Пример тестов для метода, например LeetCode:
```js
let searchInsert = function(nums, target) {
    // some code
};

const tests = [
    [[1,3,5,6], 5],
    [[1,3,5,6], 2],
    [[1,3,5,6], 7],
    [[1,3,5,6], 0],
    [[1], 0],
];
tests.forEach((params) => console.log(searchInsert(...params)));
```


## [Об'єкти](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object)
Об'єкт – це колекція згрупованих змінних. В об'єкті ними виступають властивість/ключ зі значенням.

> Об'єкт використовує фігурні дужки.

> Об'єкт ітерується циклом `for in`, а `for of` для об'єктів не працює.

```js
// Приклад простого об'єкта - рекомендований запис: 
var obj = {};

// Аналогічнj створений об'єкт:
var obj = new Object();

// Приклад об'єкта зі значеннями:
var person = {
  name: "Bob Singer",
  age: 21,
  hasDriverLicence: true
}
```
Якщо вивести об'єкт на екран, отримаємо `[object Object]` - це тому що всі значення виводяться як строки.

Але цей `[object Object]` (і будь якій інший) можна отримати в якості JSON даних. 
Для цього є метод `JSON.stringify()` - converts a JavaScript object or value to a JSON string:
```js
console.log(JSON.stringify(person));
```
отримаємо:
```json
{
  "name": "Bob Singer",
  "age": 21,
  "hasDriverLicence": true
}
```

### Создание объекта
* В JS объекты создаются с помощью ключа и значения.
* Свойства и методы разделяются запятыми, кроме последнего свойства.
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

* Сначала создаем пустой объект,
* потом создаем свойство `hi` и присваиваем ему значение.
* **Порядок вывода всех свойств объекта не гарантируется как добавлялся!**
```js
// Пример создания объекта 2:
let a = {};
a.hi = "test";
```

***

### Чтение свойств объекта
Если свойство имеет односоставное имя без пробела, например "hi", к такому свойству можно обратиться как и в JAVA через точку.

Если обратиться к свойству с пробелом через точку, например "how much" - это приведет к ошибке, т.к. обращаться нужно только через квадратные скобки:
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

***

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

***

### Проверка заданного свойства/ключа внутри объекта
Проверяет - есть ли заданное свойство/ключ внутри объекта - возвращает `boolean`.
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

***

### Поверхневе клонування
Так би мовити, перший рівень клонується за значенням, а всі вкладенності - за посиланням:
```js
let a = {
  one : "Hello",
  name : 23,
  c: {
    x: 3,
    y: 2
  }
};
let b = {};
for (let key in a) {
  b[key] = a[key];
}

b.one = "World";
b.c.x = 7;
console.log(a); // { one: 'Hello', name: 23, c: { x: 7, y: 2 } }
console.log(b); // { one: 'World', name: 23, c: { x: 7, y: 2 } }
```
Такий самий результат можно отримати використавши замість циклу функцію [`Object.assign()`](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Object/assign):

> Метод `Object.assign()` копіює з вихідних об'єктів у цільовий об'єкт лише перераховані та власні властивості.
```js
Object.assign(b,a);
```

***

### Деструктуризація об'єкту
У вкладеності ім'я змінної визначається праворуч - відрізняється за кольором в IDE.
Ім'я задається після `:`.
```js
// є об'єкт
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

// зберігаємо об'єкт у змінну
const user = getUser();

// Деструктуризація об'єкту
const {name, age, address: {country: theCountry}} = user;
const {address: {fullAddress: {doorNumber: number}}} = user;

console.log(name);       // John
console.log(age);        // 29
console.log(theCountry); // United States
console.log(number);     // 22
```
```js
// у першому випадку, назви name та age повинні збігатися з назвами в об'єкті:
const {name, age, address: {country: theCountry}} = user;

// у другому випадку, ми задаємо інші імена змінних - для name змінна n...:
const {name : n, age : a, address: {country: theCountry}} = user;
```

***

### Мапа в JS - `new Map`
**Map** немає методу сортування.
Для сортування необхідно мапу обернути в масив, і на ньому викликати метод `sort`:
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

***

### Створення класу з конструктором
Приклад створення класу з конструктором та викликом екземпляра.
Як і Java, спрацьовує 1 раз при створенні об'єкта.
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

***

### [Прототипи об'єктів / __proto__](https://developer.mozilla.org/ru/docs/Learn/JavaScript/Objects/Object_prototypes)
Прототипи – це механізм, за допомогою якого об'єкти JavaScript успадковують властивості один від одного.
```js
let human = {
    name: "Bob",
    age: 26,
    profession: "IT"
};

let human2 = Object.create(human);
human2.name = "Carl";
human2.age = 53;

console.log(human2); // { name: 'Carl', age: 53 }
console.log(human2.profession); // IT
```
Тобто можна звертатися до властивостей (також і функцій) іншого об'єкта.

***

### Спадкування в JS
Таке саме як у Java. 
Використовується ключове слово`extend`. 
Звернення до батька також через `super`.

***

### Статичні методи
Таке саме як у Java з ключовим словом `static`.
Звернення через ім'я класу `Animal.myStaticMethod()`.

***

### Приклад класу з LeetCode
[Приклад реалізації стека](https://leetcode.com/problems/min-stack/discuss/1539426/JavaScript-Easy-to-understand-detailed-explanation-and-O(1)-for-all)
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

***

### Відмальовка Ajax після видалення
> Чи можна було видалення робити без перезавантаження таблиці (видалення рядка) і для редагування брати дані зі сторінки, а не з сервера?

У розрахованому на багато користувачів додатку прийнято при зміні даних підтягувати всі зміни з бази,
інакше може бути неузгодженість бази та **UI** (наприклад, коли користувачів редагують кілька адміністраторів одночасно).
Для їжі діставати з бази дані при редагуванні немає потреби, але краще робити все універсально.
У таблиці часто представлені всі дані, які можна редагувати. Додаткове навантаження на основу тут зовсім невелике.
Для їжі нам при кожному додаванні-видаленні-редагуванні ще необхідно перераховувати перевищення `excess`.

***



