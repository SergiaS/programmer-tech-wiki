# [Array](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Array)
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

Приклад створення масиву символів - це по суті, строки з 1 літерою:
```js
let str = "corvette";
// варіант 1:
const wordArray1 = Array.from(str);
// варіант 2:
const wordArray2 = str.split("");
```



## Функції

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
Метод `unshift()` додає один або більше елементів на початок масиву і повертає нову довжину масиву.

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



## Spread and Rest operators - `...`
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


## Деструктуризація масиву
* [YouTube - Деструктуризация массивов. Фундаментальный JavaScript](https://www.youtube.com/watch?v=qhpqykBJoz4)
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


