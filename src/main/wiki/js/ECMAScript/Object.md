# [Об'єкти](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object)
Об'єкт – це колекція згрупованих змінних. В об'єкті ними виступають властивість/ключ зі значенням.

> Об'єкт використовує фігурні дужки `{}`.

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

## Приклади

***

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
* [YouTube - Деструктуризація об'єктів. Фундаментальний JavaScript](https://www.youtube.com/watch?v=mbov4Rs0F3k)

```js
const myObj = {
    a: 1,
    b: 2,
    c: {
        a: 0,
        b: 10,
    },
    d: 4,
    n: [1,2,3],
};

// деструктуризація
const {
    d: myNewName = 0, // задаємо нове ім'я і дефолтне значення
    a: firstA = 0, // задаємо нове ім'я для першої a
    c: {
        a: secondA = 0, // дістаємо вкладенну a: 0
    } = {},
    n: [
        , deepN = 0 // отримуємо другий елемент масиву, або дефолтне значення
    ] = [], // 
    ...tailm	 // зберігатиме всі інші дані
} = myObj || {}; // коли об'єкт не прийшов, створимо пустий
```

> Вкладенним об'єктам та масивам також необхідно присвоювати дефолтне значення, на випадок якщо об'єкт не прийшов.

При деструктуризації об'єкта потрібно увесь вираз загорнути у круглі дужки:
```js
{a, b} = myObj; // не спрацює - сприйматиме за блок кода

({a, b} = myObj); // треба так
```

***

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



## Створення класу з конструктором
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


## [Прототипи об'єктів / __proto__](https://developer.mozilla.org/ru/docs/Learn/JavaScript/Objects/Object_prototypes)
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


## Наслідування в JS
Таке саме як у Java.
Використовується ключове слово`extend`.
Звернення до батька також через `super`.


## Статичні методи
Таке саме як у Java з ключовим словом `static`.
Звернення через ім'я класу `Animal.myStaticMethod()`.


## Приклад класу з LeetCode
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
