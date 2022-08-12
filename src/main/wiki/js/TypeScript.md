# TypeScript (TS)
Це типізований JavaScript (JS) - суміш JavaScript з Java + свої особливості.


## Налаштування tsconfig
* [What is a tsconfig.json](https://www.typescriptlang.org/docs/handbook/tsconfig-json.html)
* [Intro to the TSConfig Reference](https://www.typescriptlang.org/tsconfig)
* [Конфигурация для TypeScript - tsconfig.json](https://www.youtube.com/watch?v=HYWxznNcRPU&list=PLiZoB8JBsdzlG1oAY8U4vrBtVW07j6jil&index=6)

> Щоб створити `tsconfig.json` треба в терміналі виконати команду `tsc --init` після котрої з'явиться файл.

***
### rootDir
Щоб кожного разу не прописувати адресу до файлів через теки (`./src/script.ts`), 
можна встановити голову теку використовуючи ключ `rootDir`.

***
### outDir
Щоб зкомпілювати (ts => js) усі файли проєкту у вказану теку - використовуй `outDir`. 
Для компіляції треба виконати команду `tsc`.

***
### extends
Можна створювати декілька `tsconfig`'ів, щоб їх вкладати один в одного, створи ключ `extends` на рівні `compilerOptions`.

***
### files
Є можливість вказати які саме файли компілювати (конкретно) - треба створи ключ `files` на рівні `compilerOptions` та переразувати назви в масиві `[]`:
```json
"files": [
    "./src/index.ts"
],
```

*** 
### include
або які саме теки підлягають компіляції - `include`:
```json
"include": [
    "./src/**/*"
],
```
** - все що є у теці `src`;
/* - включно з усіма можливими вкладеннями.

*** 
### exclude
так само можливо виключати файли - `exclude`:
```json
"exclude": [
    "node_modules",
    "./src/**/*.spec.ts"
],
```

*** 
### target 
`target` - те, у що нащ код буде преобразований.
```json
"target": "es2016"
```

***




## Встановлення та запуск
Щоб автоматично TypeScript компілювався у JavaScript код, треба запустити глобальну команду: 
```commandline 
npm install -g typescript
```

Після встановлення можна перевірити версію компілятора за командою:
```commandline
tsc -v
```

Далі, відносно нашого `.ts` файлу запускаємо термінал і пишемо команду для компіляції у js...
```commandline
tsc index.ts
```
...після чого з'явиться скомпільований наш файл з розширенням `.js`, який можна запустити командою:
```commandline
node index.js
```
Але щоб не писати декілька команд після кожної зміни, можно встановити інший пакет глобально:
```commandline
npm install -g ts-node
```
...і запустити команду:
```commandline
ts-node index.ts
```

## Синтаксис

### Статичні змінні
TS відрізняється від JS тим що тут при створенні змінної вказуються типи:
```ts
// приклад створення статичних змінних
let name1: string = 'carl';

let name2: string;
name2 = 'bob';
```

### Динамічні змінні - `union`
При необхідності використовувати декілька варіантів типів для змінної, треба використовувати оператор `|` (pipe, пайп):
```ts
// приклад створення динамічної змінної - називається union
let myScore: number | string;   // union
```
Такий запис типу каже, що наша змінна динамічна, але вона контрольована в межах котрі ми встановлюємо при створенні - 
в данному прикладі тільки два типи. Такий тип даних називається `union`.


### Аліаси, псевдоніми змінних
Для задання аліасу змінної використовується ключове слово `type`:
```ts
// приклад запису аліасу
type Score = number | string;  // визначаємо тип
const myScore: Score = 7;       // присвоюємо значення
```
Ім'я аліасів (псевдонімів), зазвичай, вказують з великої літери.


### Коли вказувати тип потрібно:
- коли функція повертає `any` і ми хочемо уточнити значення.
  Тип даних `any` (відсутність типу) не рекомендовано використовувати! Краще завжди уточнювати тип!
    ```ts
    // при parse краще вказувати який тип даних ми очікуєио отримати
    let x = JSON.parse('6');             // тип any
    let num: number = JSON.parse('6');   // тип number
    let str: string = JSON.parse('bla'); // тип string
    ```
- коли оголошення змінної та присвоювання їй значення виконується на різних строках:
    ```ts
    let isOdd: boolean;
    if (6 % 2 === 0) {
        isOdd = false;
    } else {
        isOdd = true;
    }
    ```
- коли ми хочемо, щоб тип був складносоставним і не визначався автоматично:
    ```ts
    // складносоставний тип, TS за нас визначить тип змінної
    let myScore = 10;
    
    // тому краще вказувати тип
    let myScore: number | string = 10;
    ```

### Масиви
Типізація масивів:
- списки:
  
- кортежи (tuple):

#### Списки
- зазвичай, усі об'єкти мають однаковий тип;
- довжина даного масиву змінна.
```ts
// рівнозначний запис:
const arr1: number[] = [1,2,3];
const arr2: Array<number> = [1,2,3];
```
```ts
// масив буде зберігати строки і числа:
const arr3: (string | number)[] = [];

// те саме, тільки з псевдонімом:
type MyType = (string | number);
const arr4: MyType[] = [];
```

#### Кортежі (tuples)
- елементи можуть бути різних типів;
- довжина даного масиву фіксована;
```ts
// вказується 3 типи і повинно бути 3 значення
const tuple1: [string, boolean, number] = ['', true, 0];
```
Добре підходить для роботи з `.csv` файлами:
```ts
// створення масику кортежів:
const example1: [string, string, number][] = [
    ['str1', 'str2', 32],
    ['str3', 'str4', 16],
];

// те саме тільки з псевдонімом:
type SimpleCsv = [string, string, number];
const example2: SimpleCsv[] = [
  ['str1', 'str2', 32],
  ['str3', 'str4', 16],
];
```


### Об'єкти
```ts
// створення об'єкту
const obj1: {
    a: number;
    b: number;
    c: string;
} = {a: 1, b: 2, c: 'test'};

// те саме тільки з псевдонімом:
type MyObj = {
  a: number;
  b: number;
  c: string;
}
const obj2: MyObj = {a: 1, b: 2, c: 'test'};
```

Описувати об'єкти можна також в інтерфейсі:
```ts
// об'єкт з обов'язковими 3 ключами:
interface MyObjectInterface {
    a: number;
    b: number;
    c: string;
}
const obj3: MyObjectInterface = {
    a: 1,
    b: 2,
    c: 'weq'
}
```

***

##### Необов'язковий ключ
...вказується зі знаком запитання `?`, наприклад `c?` - цей ключ, при створенні об'єкта, як може бути, так і ні:
```ts
// об'єкт з необов'язковим ключем - c?:
interface MyObjectInterface {
    a: number;
    b: number;
    c?: string;
}
const obj3: MyObjectInterface = {
    a: 1,
    b: 2
}
```

***

##### Ключ readonly
Також можна заборонити змінювати значення ключа додавши до ключа спеціальне слово `readonly`:
```ts
interface MyObjectInterface {
    readonly a: number;
    b: number;
    c?: string;
}
const obj3: MyObjectInterface = {
  a: 1,
  b: 2
}
obj3.a = 32; // компілятор не дозволить
```
***

##### Метод в об'єкті 
...записується так:
```ts
interface MyObjectInterface {
    readonly a: number;
    b: number;
    c?: string;
    print(): number;        // приклад запису метода 1
    print2?: () => number;  // приклад запису метода 2
}
```
Якщо використовується перший спосіб оголошення методу - `print(): number;`, тоді його необхідно буде реалізувати в новостворених об'єктах, використовувати `?` як необов'язково, не можна!

Якщо використовується другий спосіб оголошення методу - `print2?: () => number;`, тоді його необхідно буде реалізувати тільки якщо це не опція (не стоїть `?`).

***

##### Додаткові ключі
Іноді при роботі з інтерфейсами можуть знадобитися опціональні ключі.
Їх можна додавати скільки завгодно:
```ts
// в прикладі опціональний ключ типу string
// і значення його буде string або number
interface MyObjectInterface {
    readonly a: number;
    b: number;
    [key: string]: string | number;
}
const obj3: MyObjectInterface = {
    a: 1,
    b: 2,
    t1: 'test1',
    t2: 'test2',
    t3: 3,
}
```

***

##### Об'єднання інтерфейсів
TypeScript об'єднає різні інтерфейси з однаковими іменами, і буде очікувати при створенні ключі з усіх інтерфейсів:
```ts
interface Person {
    name: string; 
}
interface Person {
    age: number; 
}

const john: Person = {
    name: 'john',
    age: 40
}
```
...але якщо такі імена будуть перетинатися з бібліотеками, це вже проблема.
Тому в такому випадку рекомендують давати назву своїм інтерфейсам з літери `I` - `IPerson`, `IVehicle`... 

Якщо потрібно використовувати ключі з різних інтерфейсів, тоді тут використовується наслідування.
Дозволено наслідувати будь-яку кількість інтерфейсів:
```ts
interface IPerson {
    name: string;
}
interface IPerson {
    age: number;
}

interface IAccount {
    email: string;
    login: string;
    active: boolean;
}

interface IDeveloper extends IPerson, IAccount {
    // містить усі ключі з IPerson та IAccount
    skills: string[],
    level?: string;
}

const bob: IDeveloper = {
  name: 'Bob',
  age: 42,
  email: 'bob@com.ua',
  login: 'bobcat',
  active: false,
  skills: ['JavaScript', 'TypeScript']
}
```

***

##### Об'єднання типів
```ts
type Person = {
    name: string;
    age: number;
}
type MyAccount = {
    email: string;
    login: string;
    active: boolean;
}
type MyDeveloper = {
    skills: string[];
    level?: string;
}

type FrontendDeveloper = Person & MyAccount & MyDeveloper;

// створюємо масив розробників
const devArr: FrontendDeveloper[] = [];
```

***

##### Типізація функцій
```ts
const fn1 = (num: number): string => {
    return String(num);
}
```
...де `(num: number)` ім'я параметру та його тип, а після цього вказується повертаємий тип - `: string` 

Необов'язкові параметри функції також помічаються `?`.
```ts
// функція з callback параметром
type Callback = (num: number) => string;

function fn2(cb? : Callback) {
    if (cb === undefined) {
        cb = String;
    }
    cb(12);
}
```

Дефолтні значення в функціях можна записувати як і в JavaScript:
```ts
// результат однаковий
function createPoint1(x = 0, y = 0): [number, number] {
    return [x, y];
}
function createPoint2(x: number = 0, y: number = 0): [number, number] {
    return [x, y];
}
```
Функція з параметром типізованого масиву:
```ts
function fn3(...nums: number[]): string {
    return nums.join('-');
}
```

Функція без повертаємого типу - використовуй `void`:
```ts
interface Printable {
    label: string;
}

function printReport(obj: Printable): void {
    console.log(obj.label);
}

const drink = {
    label: 'pepsi',
    price: 90,
}
printReport(drink);
```
...в даному прикладі виведе `pepsi`, і це при тому, що хоча й очікується об'єкт `Printable` без ключа `price`,
передається об'єкт `drink` з ключем `price`. Але, як не дивно, все працює!

Функція, яка приймає на вхід число, або масив об'єктів з ключами `suit` і `card`, та повертає об'єкт чи число:
```ts
function pickCard(x: number | {suit: string; card: number}[]): {suit: string; card: number} | number {
    /* some code */
    return 0;
}
```
...але така функція складно читаєма, тому є простіший спосіб, в якому описуються функції з окремими параметрами, 
а сама функція за допомогою з'ясовування типу вхідного параметра, описується тільки раз:
```ts
function getCard(x: number): {suit: string; card: number};
function getCard(x: {suit: string; card: number}[]): number;
function getCard(x): any {
    if (typeof x === 'object') {
        /* some code */
        return 5;
    } else if (typeof x === 'number') {
        /* some code */
        return {suit: 'test', card: 13}
    }
}
console.log(getCard(7));
console.log(getCard([{suit: 'asd', card: 18}]));
```
> Щоб не було помилки типу `Parameter 'x' implicitly has an 'any' type...` потрібно створити файл з налаштуваннями
> TypeScript під назвою tsconfig.json і додати ключ `"noImplicitAny": false` - гугли приклади!


## Generics
Більшість аналогічно до Java...
```ts
// Дженерікі у класах
class ArrayOfNumbers {
    constructor(public collection: number[]) {}

    get(index: number): number {
        return this.collection[index];
    }
}
class ArrayOfStrings {
    constructor(public collection: string[]) {}

    get(index: number): string {
        return this.collection[index];
    }
}

// замінюємо на дженерік замість класів вище
class ArrayOfAnything<T> {
    constructor(public collection: T[]) {}

    get(index: number): T {
        return this.collection[index];
    }
}

console.log(new ArrayOfAnything<string>(['1','2','s']));
console.log(new ArrayOfAnything<number>([0, 2, 3]));
```
```ts
// Дженерікі у функціях
function printStrings(arr: string[]): void {
    for (let i = 0; i < arr.length; i++) {
        console.log(arr[i]);
    }
}
function printNumbers(arr: number[]): void {
    for (let i = 0; i < arr.length; i++) {
        console.log(arr[i]);
    }
}

// замінюємо на дженерік замість функцій вище
function print<T>(arr: T[]): void {
    for (let i = 0; i < arr.length; i++) {
        console.log(arr[i]);
    }
}
print<number>([1,4,6,7]); // по конвенції
print([1,4,6,7]); // краще так не писати, хоча працює
```

### keyof
Буде працювати тільки з ключами того об'єкта, котрого ми передаємо.

По суті `keyof` являє собою **union** всіх ключів з об'єкта:
```ts
function getProperty<T, K extends keyof T>(obj: T, key: K) {
    return obj[key]; // T[K]
}
const myObj = {
    a: 1,
    b: 2,
    c: 3,
}
// K === 'a' | 'b' | 'c' // union
getProperty(myObj, 'a');
```
> Якщо передати ключ, якого немає в об'єкті - **TypeScript** повідомить про помилку



## Приклади

### Приклад запиту

`Todo` це інтерфейс з полями, в response дані засовуються у цей об'єкт через `as Todo` + маппінг кожного поля 
(типу моделі в Java):
```ts
import axios from 'axios';

const url = 'https://jsonplaceholder.typicode.com/todos/1';

interface Todo {
    id: number;
    title: string;
    completed: boolean;
}

axios.get(url)
    .then(response => {
        const todo = response.data as Todo;

        const id = todo.id;
        const title = todo.title;
        const finished = todo.completed;

        console.log(`
            Todo ID: ${id}
            Todo title: ${title}
            IS todo finished: ${finished}        
        `);
    });
```

### Приклад фіксованого типу даних аргументів
```ts
function logTodo(id: number, title: string, completed: boolean) {
    console.log(`
            Todo ID: ${id}
            Todo title: ${title}
            IS todo finished: ${completed}        
        `);
}
```
...тобто `id` обов'язково повинен бути типу `number`, `title` - `string`, а `completed` - `boolean`.

