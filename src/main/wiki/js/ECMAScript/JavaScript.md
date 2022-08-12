# JavaScript
* [JavaScript Equality Table](https://dorey.github.io/JavaScript-Equality-Table/)
* [Pure JS Table Filter or Search | How to add Filter In HTML CSS Table](https://morioh.com/p/51dbc30377fc?f=5c21fb01c16e2556b555ab32&fbclid=IwAR0g9F8mHU7gMEGSFde-Sc_hs8y--VSmHxI2PjbOoo0YAyvT2sgaLUAJ3Vc)
* [JSON generator](https://randomuser.me/api/?results=50)
* [Простые решения для сложных задач с Intersection Observer API](https://www.youtube.com/watch?v=ZYqBZmU-tA0)

> Коли ставити дужки у метода `()`, а коли - ні?
> Метод з дужками каже що при читанні скрипта функція одразу виконається, а без - тільки за якоїсь дії користувача.
> ```js
> onclick(doAction());        // виконається одразу
> onclick(() => doAction());  // виконається по кліку
> ```


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



## Теги в **JS**
* [@use JSDoc - Block Tags](https://jsdoc.app/index.html)

Теги дозволяють фіксувати тип вхідного параметра (`@param`), повертаємого результату `@return` та багато іншого.
А також дозволяють фіксувати їх імена.

***

### @param
* [@param - @use JSDoc]([](https://jsdoc.app/tags-param.html))

The `@param` tag provides the <u>**name**</u>, <u>**type**</u>, and <u>**description**</u> of a function parameter.
```js
/**
 * @param {number} start
 * @param {number} end
 * @return {boolean}
 */
MyCalendar.prototype.book = function(start, end) {
    for (const nums of map) {
        if ((start === nums[0]) ||
            (end === nums[1]) ||
            (start < nums[0] && end > nums[1]) ||
            (start > nums[0] && start < nums[1]) ||
            (end > nums[0] && end < nums[1]))
        {
            return false;
        }
    }
    map.push([start, end]);
    return true;
};
```
***



## [Promise](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Promise)
Об'єкт `Promise` (проміс) використовується для відкладених та асинхронних обчислень.

[Amigoscode](https://youtu.be/dOnAC2Rr-6A?t=11386) > `Promise` - представляє значення, яке може бути доступним в даний момент, або в майбутньому, або ніколи.

`Promise` має 3 стани: перш ніж рез-т готовий, `Promise` перебуває у стані очікування - **Pending**.
Як тільки рез-т готовий – якщо успішно, то це **Fulfilled**, а якщо з помилкою - **Rejected**.

> Якщо є кілька промісів – порядок виконання не гарантується!
Для фіксованого порядку використовуй `Promise.all([a,b])`.


### Метод then
У разі успішного обчислення промісу (проміс виконався), щоб отримати від нього якийсь рес-т, потрібно використовувати спеціальний метод через крапку `.then`:

`somePromise.then()` - він (тоді всередині) вказує, що станеться, якщо проміс виконався. У разі помилки справа до цього методу не дійде.

На проміс можна додати метод `then`. Повертає проміс - необхідно обробити, щоб використовувати.
Метод `then` у проміса називають **Fulfilled handler**.
```js
responsePromise.then(response => { 
    console.log(response) 
});
```

***

Приклад послідовного виконання методів з зупинкою якщо метод повертає `false`:

```js
// функція validateEmail() повертає boolean
Promise.resolve(validateEmail())
        // якщо рез-т функції validateEmail() повертає true - ідемо далі
        .then((res) => res && validateAgreement());
```


***


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


***


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




## [Array](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array)
> Ні розмір JavaScript-масиву, ні типи його елементів не є фіксованими. 

> Перебір по масиву робиться завдяки циклу `for of`, а по об'єкту - `for in`.

> Приклад тестів для функції, наприклад LeetCode:
> ```js
> let searchInsert = function(nums, target) {
>     // some code
> };
> 
> const tests = [
>     [[1,3,5,6], 5],
>     [[1,3,5,6], 2],
>     [[1,3,5,6], 7],
>     [[1,3,5,6], 0],
>     [[1], 0],
> ];
> tests.forEach((params) => console.log(searchInsert(...params)));
> ```

> Приклад створення дво вимірного масиву:
> ```js
> const twoDimensionalArray = new Array(3)
>     .fill(0)
>     .map(row => new Array(7)
>             .fill(0))
> 
> console.log(twoDimensionalArray);
> // [
> //   [0, 0, 0, 0, 0, 0, 0],
> //   [0, 0, 0, 0, 0, 0, 0],
> //   [0, 0, 0, 0, 0, 0, 0]
> // ]
> ```

Оскільки розмір масиву може збільшуватися і зменшуватися в будь-який час, немає гарантії, що масив виявиться щільним.
Тобто, при роботі з масивом може виникнути ситуація, що елемент масиву, до якого ви звернетеся, буде пустим і вернеться `undefined`.

В цілому, це зручна характеристика, але якщо ця особливість масиву не бажана у вашому конкретному випадку, 
ви можете розглянути можливість використання типізованих масивів.

Array can store values of any type like this:
```js
// прості значення, об'єкти, функції
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
У прикладі вище можна звернутися до анонімної функції так: `arr[3]()`.

***

### `foreach`
Зазвичай, функція `foreach` лише перебирає елементи, але якщо використати додаткові аргументи, 
то можна навіть модифікувати масив (але це погана практика):
```js
const numbers = [1,2,3,4,5];

const result = numbers.forEach((num,index, arr) => arr[index] = num ** 2);
console.log('result', result);  // result undefined
console.log(numbers);	        // [ 2, 4, 6, 8, 10 ]
```

***
### [`sort`](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array/sort)
Цей метод сортує по символьно, оскільки навіть цифри сприймає за строки.

Для розв'язання цієї проблеми треба написати свою функцію (типу Comparator в JAVA):
```js
let nums = [12,4,27,11,6];

function mySort(a, b) {
  return a-b;
}
nums.sort(mySort);

console.log(nums);  // [ 4, 6, 11, 12, 27 ]
```

***
### [`reduce`](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array/Reduce)
Застосовує дію для кожного елемента в масиві - умова і дефолтне значення.
Поверне те що ми від нього хочемо.
```js
// пошук максимального значення в масиві
// Math.max(++cur, max) або (cur = 0, max) одна з умов
// а 0 дефолтне значенння
const findMaxConsecutiveOnes2 = (nums, cur = 0) =>
    nums.reduce((max, num) => num ? Math.max(++cur, max) : (cur = 0, max), 0);
```

Функція приймає декілька значень, де перший аргумент це callback функція, котра буде виконуватися для кожного елементу.
Приклад з підрахунком ціни товарів у кошику:
```js
const basket = [
    {
        id: 1,
        name: 'JS Book',
        price: 900,
        quantity: 0,
    },
    {
        id: 2,
        name: 'CSS Book',
        price: 700,
        quantity: 2,
    },
    {
        id: 3,
        name: 'PHP Book',
        price: 1200,
        quantity: 3,
    },
];

const result = basket.reduce((acc, item) => {
    if (item.quantity <= 0) return acc;
    return acc + item.quantity * item.price
}, 0);
console.log('result', result);  // result 5000
```


***

### [`filter`](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array/filter)
Фільтрує/Перебирає елементи за написаною умовою у функції, на основі елементів що пройшли умову, буде створює новий масив:
```js
let a = [3, -12, 0, 4, 5, -8];
let c = a.filter(function (a) {
    return a >= 0;
});
```

***

### [`push`](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array/push)
Додає один або більше елементів у кінець масиву та повертає нову довжину масиву.

***

### [`shift`](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array/shift) 
Метод `shift()` видаляє елемент за нульовим індексом, зсуває значення за послідовними індексами вниз, а потім повертає віддалене значення.
Якщо властивість `length` масиву дорівнює `0`, повернеться значення `undefined`.

***

### [`unshift`](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array/unshift)
Метод `unshift()` вставляє передані значення початку масивоподібного об'єкта.

***

### [`pop`](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array/pop)
Метод `pop()` видаляє останній елемент з масиву та повертає віддалене значення.
```js
let a = [5, 6, 8];
console.log(a); // [ 5, 6, 8 ]
let b = a.pop();
console.log(a); // [ 5, 6 ]
console.log(b); // 8
```

***

### [`every`](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array/every)
Метод повертає `true` або `false`, якщо КОЖНИЙ елемент відповідає заданій умові.

Метод робить перевірки з кожним елементом, якщо хоча б в одному `false` - поверне `false`.
```js
let arr = [3, 4, 5, 9];
let a = arr.every(function (b) {
    return b < 5;
});
console.log(a); // false
```

***

### [`some`](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array/some)
Метод повертає `true` або `false`, якщо хоча б один елемент масиву відповідає заданій умові.
```js
let arr = [3,4,5,9];
let a = arr.some(function (b) {
    return b<5;
});
console.log(a); // true
```

***

### [`Array.from`](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array/from)
Метод `Array.from()` створює новий екземпляр `Array` з масивоподібного або ітеруємого об'єкта.
```js
console.log(Array.from('foo')); // Array ["f", "o", "o"]
console.log(Array.from([1, 2, 3], x => x + x)); // Array [2, 4, 6]
```

***

### [`map`](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array/map)
Метод `map()` створює новий масив з результатом виклику зазначеної функції для кожного елемента масиву.

Mapping is an operation which applies a function to every element of a collection and __ALWAYS__ returns a new collection with elements changed by the mentioned function.
```js
const nums = [1, 2, 3, 4];
const biggerNums = nums.map((n) => n * 2);
// >> [2, 4, 6, 8];
```

***

### [`findIndex`](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array/findIndex)
Метод `findIndex()` викликає передану функцію callback один раз для кожного елемента, що є в масиві, доти, доки вона не поверне `true`. 
Якщо такий елемент знайдено, метод `findIndex()` негайно поверне індекс цього елемента. 
В іншому випадку, метод `findIndex()` поверне `-1`.

***

### [`Array.prototype.flat()`](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array/flat)
Метод `flat()` повертає новий масив, у якому всі елементи вкладених підмасивів були рекурсивно "підняті" на 
вказаний рівень `depth`.

Тобто може з многомірного масива зробити одномірний - шляхом перенесенням усіх елементів в новий масив:
```js
let arr = [[1,3,5,7,9], [2,4,6,8,10], [11,13,15,17,19], [12,14,16,18,20], [21,22,23,24,25]];
console.log(arr);
// було:
// [
//   [ 1, 3, 5, 7, 9 ],
//   [ 2, 4, 6, 8, 10 ],
//   [ 11, 13, 15, 17, 19 ],
//   [ 12, 14, 16, 18, 20 ],
//   [ 21, 22, 23, 24, 25 ]
// ]

arr = arr.flat();
console.log(arr);
// стало:
// [
//   1,  3,  5,  7,  9,  2,  4,  6,
//   8, 10, 11, 13, 15, 17, 19, 12,
//   14, 16, 18, 20, 21, 22, 23, 24,
//   25
// ]

```


***

### Spread and Rest operators - `...` 
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

***

### Деструктуризація масиву
* [Деструктурирующее присваивание - learn.javascript.ru](https://learn.javascript.ru/destructuring-assignment)

> **«Деструктуризація»** не означає **«руйнування»**.

> **«Деструктуруюче присвоєння»** не знищує масив. 

> **«Деструктуризація»** взагалі нічого не робить з правою частиною присвоювання, завдання деструктуризації - тільки скопіювати потрібні значення змінних.

**«Деструктуризація»** - це просто коротший варіант запису присвоювання:
```js
// let [firstName, surname] = arr; <- Деструктуризація
let firstName = arr[0];
let surname = arr[1];
```
Пропускай елементи, використовуючи коми `,`:
```js
// другий елемент не потрібен
let [firstName, , title, , , name] = ["Julius", "Caesar", "Consul", "Republic", "Zip","Mike"];
console.log( title ); // Consul
console.log( name ); // Mike
```
Якщо в масиві менше значень, ніж у присвоєння, то помилки не буде. Відсутні значення вважатимуться невизначеними.

Якщо нам необхідно вказати значення за замовчуванням, ми можемо використовувати `=`.

Значення за замовчуванням можуть бути більш складними виразами або навіть функціями. Вони виконуються лише якщо значення відсутні.

Деструктуруюче присвоювання працює також з об'єктами:
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

При деструктуризації можливе переіменування змінних.
Це буває необхідно, наприклад, при роботі з API, де імена зміних нам не подобаються:
```js
const {
    Title: title,
    Year: year,
    imdbID: id,
    Type: type,
    Poster: poster,
} = props;
```

***



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

### Наслідування в JS
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



## Форматування чисел в JavaScript

### [toLocaleString](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Number/toLocaleString)
Один з способів форматування даних - використання методу `toLocaleString()`, котрий автоматично приведе число до локального стандарту. 
Але можна обрати іншу локаль/налаштувати свою - наприклад, передавши першим аргументом `'uk'` чи `'uk-UA'`. А другим - об'єкт з налаштуваннями - [див. усі параметри](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Number/toLocaleString#parameters).

```html
<div id="app" style="font-size: 40px"></div>
```
```jsx
document.getElementById("app").innerHTML = `
    <h1>JS NumberFormat</h1>
    <div>
        ${sum3.toLocaleString('en-GB', {
            style: "currency", // вказує стиль
            currency: "USD", // вказує валюту
            currencyDisplay: 'code', // відобразить тільки код валюти - USD
            // currencyDisplay: 'name', // відобразить тільки ім'я валюти - US dollars
            useGrouping: false, // прибирає сепаратори 
        })}
    </div>
`;
```
...якщо не потрібно відображати `0` у цілого числа (160,0 => 160), додай до об'єкта `minimumFractionDigits: 0`.

***

### [Intl.NumberFormat](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Intl/NumberFormat)

Те саме можна виконати передавши той самий об'єкт (з прикладу toLocaleString вище):
```jsx
document.getElementById("app").innerHTML = `
    <h1>JS NumberFormat</h1>
    <div>
        ${sum3.toLocaleString('en-GB', {
            style: "currency", // вказує стиль
            currency: "USD", // вказує валюту
            currencyDisplay: 'code', // відобразить тільки код валюти - USD
            // currencyDisplay: 'name', // відобразить тільки ім'я валюти - US dollars
            useGrouping: false, // прибирає сепаратори 
        })}
        
        </br>
        
        ${new Intl.NumberFormat('en-GB', {
            style: "currency", // вказує стиль
            currency: "USD", // вказує валюту
            currencyDisplay: 'code', // відобразить тільки код валюти - USD
            // currencyDisplay: 'name', // відобразить тільки ім'я валюти - US dollars
            useGrouping: false, // прибирає сепаратори 
        }).format(sum2)}
    </div>
`;
```

***

