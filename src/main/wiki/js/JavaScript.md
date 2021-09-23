# JavaScript

* Всплытие / поднятие (Hoisting) переменных (var) относитя только к их объявлению (даже если она инициализирована).

* С помощью `===` можно сравнить две строки или два булевых значения. 
  Если же сравнивать так значения разных типов, ответом всегда будет `false`.

* Двойное равно, `==`, который означает «практически равно». 
  Используйте его для проверки двух значений на соответствие друг другу, даже если одно из них строка, а другое — число.

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
Это функция передана в качестве параметра другой функции. Способ борьбы с асинхронностью.

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
Он не такой как в Java. По умолчанию сравнивает если значение переменной `0` или `1`:
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


## Working with REST
* [Fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch)


## Methods

<hr>

### Массивы

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


### Объекты

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
Попытка обратиться к свойству с пробелом через точку приведет к ошибке - обращаться нужно только через квадрартные скобки:

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

### Создание класса с конструктором
Пример создания класса (с конструктором) и вызовом экземпляра.
```js
class Test {
    constructor(a) {
        this.c = a;
    }
}

let myObj = new Test(5);
myObj.d = 77;
console.log(myObj); // Test { c: 5, d: 77 }
```

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
    heading.className+= " changeFT";
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



## Литература, статьи
* [20 Killer JavaScript One Liners](https://dev.to/saviomartin/20-killer-javascript-one-liners-94f?fbclid=IwAR01WZ6eZokN-vSF5CHQI5pk48N49ULjNWYcN25YHCy7Q6G65DPCKf8YCaE)
