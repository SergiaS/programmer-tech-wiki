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
Самому створювати проміси майже не доводиться. 

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
* [Axios vs. fetch(): Which is best for making HTTP requests?](https://blog.logrocket.com/axios-vs-fetch-best-http-requests/)
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

### Приклад ітерації по об'єкту 
[Object.entries](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Object/entries) используется с деструктаризацией:
```js
for (const [key, value] of Object.entries(object1)) {
    console.log(`${key}: ${value}`);
}
```
Якщо у значення повертається `Object object`, тоді можна отримати значення через ім'я у квадратних дужках - `${value["id"]}`. 

Реальный приклад:
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

