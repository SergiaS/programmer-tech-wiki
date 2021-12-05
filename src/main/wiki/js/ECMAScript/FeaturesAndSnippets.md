# Features And Snippets


## JS features:

### Import and Export

#### Подключение scss:
```js
require('styles/main.scss');
```

#### Подключение переменных/функций с другого файла
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

### Как программист Java учил JavaScript...

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
* Определение позиции символа:
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
* Предикат в JS:
    ```js
    const all = (arr, fn = Boolean) => arr.every(fn);

    all([4, 2, 3], x => x > 1); // true
    all([1, 2, 3]); // true
    ```

<hr>

## Литература, статьи, видео
* [Примеры записи функций, свойст объекта](https://www.youtube.com/watch?v=dOnAC2Rr-6A)
* [Деструктурирующее присваивание](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Operators/Destructuring_assignment)
* [20 Killer JavaScript One Liners](https://dev.to/saviomartin/20-killer-javascript-one-liners-94f?fbclid=IwAR01WZ6eZokN-vSF5CHQI5pk48N49ULjNWYcN25YHCy7Q6G65DPCKf8YCaE)
* [Top 35 JavaScript Shorthands for Beginners](https://morioh.com/p/05414714e685?f=5c21fb01c16e2556b555ab32&fbclid=IwAR2mgqPBKtBzkyQ1CgwdwkddazUDcV3aFZ22RKOoOMi90_LcqqLMfddFbP8)
* [Random User Generator API](https://randomuser.me/documentation)
* [127 Useful JavaScript Snippets You Can Understand in 30 Seconds](https://morioh.com/p/a76bc7d72226?fbclid=IwAR1-GSnx2r_2hD-yLZt7-bibFqzz3Tv655yddI4Yf2MXEg3EZahPzZT3u7I)

## Additional info
* `ESLint` - следит за установленным стандартом, т.е. используются качывки одинарные или двойные, ставится точка с запятой в конце или нет... В IDEA есть плагин.