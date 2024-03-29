# TypeScript (TS)
Це типізований JavaScript (JS) - суміш JavaScript з Java + свої особливості.

> Про компіляцію `.ts` -> `.js`:<br>
> * Деякі команди для `tsc` можуть виконуватися через `npx` - тобто замість `tsc main.js`, команда яка не виконується,
>   треба писати `npx tsc main.js`
> * Щоб постійно не перекомпільовувати таким чином файл, можна використати команду `npx tsc main.ts -w`, де `-w` це **watch** -
>   таким чином дані компіляція буде робитися автоматично - **КОМАНДА ЗАХОПИТЬ ТЕРМІНАЛ!**
> * Якщо потрібно щоб файли `.ts` та `.js` знаходилися у різних теках, тоді потрібно створити `tsconfig.json` командою `tsc --init` і
>   налаштувати значення `rootDir` та `outDir` як вказано у налаштуваннях `tsconfig.json` нижче. І все що нам залишається
>   запустити команду **watch** - `tsc -w` - typescript зрозуміє, що потрібно подивитися у налаштування `tsconfig.json` і
>   прочитати значення полів `rootDir` та `outDir`.


## Встановлення та запуск
Щоб автоматично **TypeScript** компілювався у **JavaScript** код, треба запустити глобальну команду:
```shell 
# 1 варіант:
npm install -g typescript
# 2 варіант:
npm i typescript -g
```

Після встановлення можна перевірити версію компілятора за командою:
```shell
tsc -v
```

Далі, відносно нашого `.ts` файлу запускаємо термінал і пишемо команду для компіляції у js...
```shell
tsc index.ts
```
...після чого з'явиться скомпільований наш файл з розширенням `.js`, який можна запустити командою:
```shell
node index.js
```
Але щоб не писати декілька команд після кожної зміни, можна встановити інший пакет глобально:
```shell
npm install -g ts-node
```
...і запустити команду:
```shell
ts-node index.ts
```


## Debug
* [Debugging TypeScript (Node.js) in IntelliJ IDEA](https://dev.to/yutro/debugging-typescript-node-js-in-intellij-idea-3e6d)


## Налаштування tsconfig
* [What is a tsconfig.json](https://www.typescriptlang.org/docs/handbook/tsconfig-json.html)
* [Intro to the TSConfig Reference](https://www.typescriptlang.org/tsconfig)
* [Конфигурация для TypeScript - tsconfig.json](https://www.youtube.com/watch?v=HYWxznNcRPU&list=PLiZoB8JBsdzlG1oAY8U4vrBtVW07j6jil&index=6)

> Щоб створити `tsconfig.json` треба в терміналі виконати команду `tsc --init` після котрої з'явиться файл.

> Ignore any typescript file that are not in the source directory:
> - scroll down into your tsconfig.ts and add:
> ```json
> ,
> "include": [
> 	"src"
> ]
> ```

***
### rootDir
Щоб кожного разу не прописувати адресу до файлів через теки (`./src/script.ts`), 
можна встановити голову теку використовуючи ключ `rootDir`.
```json
// tsconfig.json
{
  "rootDir": "./src",
}
```

***
### outDir
Щоб зкомпілювати (ts => js) усі файли проєкту у вказану теку - використовуй `outDir`. 
Для компіляції треба виконати команду `tsc`.
```json
// tsconfig.json
{
  "outDir": "./build/js",
}
```

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
### noEmitOnError
В режимі **watch**, коли автоматично `.ts` компілюється у `.js` файл, компілятор може повідомляти про помилку у `.ts` файлі,
але все одно скомпілюється у `.js`. Ця опція запобігає компілюванню файлу, якщо компілятор бачить помилку у файлі `.ts`. 
```json
"noEmitOnError": true
```
або запустити команду з цим полем (яке перезапише значення у `tsconfig.json`), де дефолтне значення `true`:
```shell
tsc --noEmitOnError -w
```

***




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
в даному прикладі тільки два типи. Такий тип даних називається `union`.


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
- коли ми хочемо, щоб тип був складно складовим і не визначався автоматично:
    ```ts
    // складносоставний тип, TS за нас визначить тип змінної
    let myScore = 10;
    
    // тому краще вказувати тип
    let myScore: number | string = 10;
    ```

### Масиви
Типізація масивів:
- списки:
- кортежі (tuple):

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
// створення масиву кортежів:
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

> Компілятор потребує **tuples** коли використовується spread оператор
>
> Приклади написаних тестів для **LeetCode** зі spread оператором:
> ```ts
> // Двовимірний масив
> const tests383: [string, string][] = [
>   ["a","b"],
>   ["aa","ab"],
>   ["aa","aab"],
> ];
> tests383.forEach(test => console.log(canConstruct(...(test))));
> ```
> ```ts
> // Тривимірний масив
> const tests729: [number,number][][] = [
>     [[10,20],[15,25],[20,30]],
>     [[10,20],[20,25],[20,30]],
>     [[10,20],[5,10],[20,30]],
>     [[10,20],[9,20],[8,30]],
>     [[10,20],[10,25],[9,25],[8,30],[7,12],[12,18],[19,21]],
> ];
> tests729.forEach(test => {
>     let myCalendar: MyCalendar = new MyCalendar();
>     test.forEach(t => console.log(myCalendar.book(...t)));
>     console.log("========");
> });
> ```


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

Також таким чином можна страхуватися, наприклад, коли значення може не бути і на ньому викликається якийсь-то метод, 
але у такому випадку цей метод спрацює тільки якщо значення є:
```ts
// toUpperCase виконається тільки, якщо name не undefined
const greenGuitarist = (guitarist: Guitarist) => {
	return `Hello ${guitarist.name?.toUpperCase()}` 
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

Приклад зі звичайною функцією:
```ts
// функція з callback параметром
type Callback = (num: number) => string;

function fn2(cb? : Callback) { // необов'язкові параметри функції також помічаються `?`.
  if (cb === undefined) {
    cb = String
  }
  cb(12)
}
```
Приклад зі стрілковою функцією:
```ts
// перевикористовуємо тип функції через type
type mathFunc = (a: number, b: number) => number

let multiply: mathFunc = function (c, d) {
  return c * d
}

let add: mathFunc = function (c, d) {
  return c + d
}
```
Приклад з інтерфейсом:
```ts
// перевикористовуємо тип функції через interface
interface mathFunc {
  (a: number, b: number): number
}

let multiply: mathFunc = function (c, d) {
  return c * d
}

let add: mathFunc = function (c, d) {
  return c + d
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


### Assertions & Type Casting
> `!` - non-null assertion - якщо немає значення у змінної далі не працює<br>
> You do not need to use non-null assertion in combination with an `as` and the type:
> ```ts
> // 1 variant
> const img = document.querySelector('img')!
> ```
> ```ts
> // 2 variant
> const img = document.querySelector('img') as HTMLImageElement
> ```

```ts
// converting Types with Assertions
type One = string
type Two = string | number
type Three = 'hello'

let a: One = 'hello'
let b = a as Two // less specific
let c = a as Three // more specific

// Angel brackets syntax instead of "as"
let d = <One>'world'
let e = <string | number>'world'
```
Деякі варіанти порівняння (**assertions**)
```ts
// The DOM
const img = document.querySelector('img')!
const myImg = document.getElementById('#img')  as HTMLImageElement
// not work in TSX files for React:
const nextImg = <HTMLImageElement>document.getElementById('#img')
```

#### Задача на порівняння
Адаптуйте JS код для TS:
```js
// Original JS code
const year = document.getElementById("year")
const thisYear = new Date().getFullYear()
year.setAttribute("datetime", thisYear)
year.textContent = thisYear
```

> <details>
> <summary>РІШЕННЯ</summary>
> 
> ```ts
> // 1st variation
> const year: HTMLElement | null = document.getElementById("year")
> const thisYear: string = new Date().getFullYear().toString()
> if (year) {
>     year.setAttribute("datetime", thisYear)
>     year.textContent = thisYear
> }
> ```
> ```ts
> // 2nd variation
> const year = document.getElementById("year") as HTMLSpanElement
> const thisYear: string = new Date().getFullYear().toString()
> year.setAttribute("datetime", thisYear)
> year.textContent = thisYear
> ```
> 
> </details>


## Класи
> За замовчуванням усі поля та методи в класі `public`.

***

> <details>
> <summary>Приклад коротшого запису класу</summary>
> 
> Просто використовуй `public` у аргументів конструктора:
> ```ts
> // звичайний запис
> class Coder {
>   name: string
>   music: string
>   age: number
>   lang: string
> 
>   constructor(name: string, music: string, age: number, lang: string) {
>     this.name = name;
>     this.music = music;
>     this.age = age;
>     this.lang = lang;
>   }
> }
> ```
> ```ts
> // коротший запис - той самий результат
> class Coder {
>   constructor(
>           public readonly name: string,
>           public music: string,
>           private age: number,
>           protected lang: string
>   ) {
>     this.name = name;
>     this.music = music;
>     this.age = age;
>     this.lang = lang;
>   }
> }
> ```
> </details>

> <details>
> <summary>Приклад статичних полів і методів</summary>
> 
> ```ts
> class Peeps {
>   static count: number = 0
> 
>   static getCount(): number {
>     return Peeps.count
>   }
> 
>   public id: number
> 
>   constructor(public name: string) {
>     this.name = name
>     this.id = ++Peeps.count
>   }
> }
> 
> const Carl = new Peeps('Carl')
> const Steve = new Peeps('Steve')
> const Amy = new Peeps('Amy')
> 
> console.log(Carl.id)
> console.log(Steve.id)
> console.log(Amy.id)
> console.log(Peeps.count)
> ```
> </details>


> <details>
> <summary>Приклад написання класу</summary>
> 
> ```ts
> class Coder {
>   constructor(
>           public readonly name: string,
>           public music: string,
>           private age: number,
>           protected lang: string = 'TypeScript'
>   ) {
>     this.name = name;
>     this.music = music;
>     this.age = age;
>     this.lang = lang;
>   }
> 
>   getAge() {
>     return `Hello, I'm ${this.age}`;
>   }
> }
> 
> const Bob = new Coder('Bob', 'Rock', 29)
> console.log(Bob.getAge())
> // console.log(Bob.age)
> // console.log(Bob.lang)
> ```
> ```ts
> class WebDeb extends Coder {
>   constructor(
>       public computer: string,
>       name: string,
>       music: string,
>       age: number
>   ) {
>     super(name, music, age);
>     this.computer = computer;
>   }
> 
>   public getLang() {
>     return `I write ${this.lang}`
>   }
> }
> 
> const Sara = new WebDeb('Mac', 'Sara', 'Lofi', 25)
> console.log(Sara.getLang())
> // console.log(Sara.age)
> // console.log(Sara.lang)
> ```
> </details>

> <details>
> <summary>Приклад Getters & Setters</summary>
> 
> ```ts
> class Bands {
>   private dataState: string[]
> 
>   constructor() {
>     this.dataState = [];
>   }
> 
>   public get data(): string[] {
>     return this.dataState
>   }
> 
>   public set data(value: string[]) {
>     if (Array.isArray(value) && value.every(el => 'string')) {
>       this.dataState = value
>       return
>     } else throw new Error('Param is not an array of strings')
>   }
> }
> 
> const MyBands = new Bands()
> MyBands.data = ['Neil Young', 'Led Zep']
> console.log(MyBands.data)
> MyBands.data = [...MyBands.data, 'ZZ Top']
> console.log(MyBands.data)
> ```
> </details>

***

### Динамічні індекси
* [YouTube](https://www.youtube.com/watch?v=2eAqXLi8q70&list=PL0Zuz27SZ-6NS8GXt5nPrcYpust89zq_b&index=7)
|| [Dave Gray - Index Signatures, keyof Assertions & the Record Utility Type](https://github.com/gitdagray/typescript-course/blob/main/lesson07/src/main.ts)

> <details>
> <summary>Приклад запису динамічних індексів - Index Signature</summary>
> 
> ```ts
> // не працюючий варіант
> interface TransactionObj {
>   Pizza: number,
>   Books: number,
>   Job: number
> }
> 
> const todaysTransactions: TransactionObj = {
>   Pizza: -10,
>   Books: -5,
>   Job: 50
> }
> 
> console.log(todaysTransactions.Pizza)
> console.log(todaysTransactions['Pizza'])
> 
> let prop: string = 'Pizza'
> console.log(todaysTransactions[prop]) // error: No index signature...
> ```
> ```ts
> // працюючий варіант
> interface TransactionObj {
>   [index: string]: number
> }
> 
> const todaysTransactions: TransactionObj = {
>   Pizza: -10,
>   Books: -5,
>   Job: 50
> }
> 
> console.log(todaysTransactions.Pizza)
> console.log(todaysTransactions['Pizza'])
> 
> let prop: string = 'Pizza'
> console.log(todaysTransactions[prop])
> ```
> </details>

> <details>
> <summary>Приклад запису динамічних індексів з undefined</summary>
> 
> ```ts
> interface Student {
>   [key: string]: string | number | number[] | undefined
>   name: string,
>   GPA: number,
>   classes?: number[]
> }
> 
> const student: Student = {
>   name: "Doug",
>   GPA: 3.5,
>   classes: [100, 200]
> }
> 
> console.log(student.test) 
> ```
> </details>

> <details>
> <summary>Приклад запису динамічних індексів при ітерації і функції (as keyof typeof)</summary>
> 
> Приклад якщо індекси не оголошені в інтерфейсі:
> ```ts
> interface Student {
>   // [key: string]: string | number | number[] | undefined
>   name: string,
>   GPA: number,
>   classes?: number[]
> }
> 
> const student: Student = {
>   name: "Doug",
>   GPA: 3.5,
>   classes: [100, 200]
> }
> 
> // iterate by cycle
> for (const key in student) {
>   console.log(`${key}: ${student[key as keyof Student]}`)
> }
> 
> // iterate in object
> Object.keys(student).map(key => {
>   console.log(student[key as keyof typeof student])
> })
> 
> // in function
> const logStudentKey = (student: Student, key: keyof Student): void => {
>   console.log(`Student ${key}: ${student[key]}`)
> }
> logStudentKey(student, 'GPA')
> ```
> </details>


## Інтерфейси

> <details>
> <summary>Приклад інтерфейсу і його реалізація</summary>
> 
> ```ts
> interface Musician {
>   name: string
>   instrument: string
>   play(action: string): string
> }
> 
> class Guitarist implements Musician {
>   name: string
>   instrument: string
> 
>   constructor(name: string, instrument: string) {
>     this.name = name
>     this.instrument = instrument
>   }
> 
>   play(action: string): string {
>     return `${this.name} ${action} the ${this.instrument}`
>   }
> }
> 
> const John  = new Guitarist('John', 'guitar')
> console.log(John.play('strums'))
> ```
> </details>


## Generics
* [YouTube: Dave Gray - Typescript Generics | Beginners Tutorial with Examples](https://www.youtube.com/watch?v=RWG66gIo7PM&list=PL0Zuz27SZ-6NS8GXt5nPrcYpust89zq_b&index=8)
|| [GitHub](https://github.com/gitdagray/typescript-course/blob/main/lesson08/src/main.ts)

Більшість аналогічно до Java...

```ts
// без дженеріків
const stringEcho = (arg: string): string => arg
// з дженеріками
const echo = <T>(arg: T): T => arg
```

> <details>
> <summary>Дженерікі у класах</summary>
> 
> ```ts
> // без дженеріків
> class ArrayOfNumbers {
>   constructor(public collection: number[]) {}
> 
>   get(index: number): number {
>     return this.collection[index];
>   }
> }
> class ArrayOfStrings {
>   constructor(public collection: string[]) {}
> 
>   get(index: number): string {
>     return this.collection[index];
>   }
> }
> ```
> ```ts
> // замінюємо на дженерік замість класів вище
> class ArrayOfAnything<T> {
>   constructor(public collection: T[]) {}
> 
>   get(index: number): T {
>     return this.collection[index];
>   }
> }
> 
> console.log(new ArrayOfAnything<string>(['1','2','s']));
> console.log(new ArrayOfAnything<number>([0, 2, 3]));
> ```
> </details>

> <details>
> <summary>Дженерікі у функціях</summary>
> 
> ```ts
> // без дженеріків
> function printStrings(arr: string[]): void {
>   for (let i = 0; i < arr.length; i++) {
>     console.log(arr[i]);
>   }
> }
> function printNumbers(arr: number[]): void {
>   for (let i = 0; i < arr.length; i++) {
>     console.log(arr[i]);
>   }
> }
> ```
> ```ts
> // замінюємо на дженерік замість функцій вище
> function print<T>(arr: T[]): void {
>   for (let i = 0; i < arr.length; i++) {
>     console.log(arr[i]);
>   }
> }
> print<number>([1,4,6,7]); // по конвенції
> print([1,4,6,7]); // краще так не писати, хоча працює
> ```
> </details>


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


## Допоміжні типи
> TypeScript provides several utility types to facilitate common type transformations. These utilities are available globally.

* [DOC: Utility Types](https://www.typescriptlang.org/docs/handbook/utility-types.html)
* [YouTube: Dave Gray - Typescript Utility Types | TS Beginners Tutorial](https://www.youtube.com/watch?v=YN4RoihmVKM&list=PL0Zuz27SZ-6NS8GXt5nPrcYpust89zq_b&index=9)
* [GitHub: Dave Gray - Examples](https://github.com/gitdagray/typescript-course/blob/main/lesson09/src/main.ts)

> <details>
> <summary>EXAMPLES of Typescript Utility Types by Dave Gray</summary>
> 
> Оригінал на сторінці GitHub Dave Gray вище 
> ```ts
> // Utility Types
> 
> /** Partial */
> 
> interface Assignment {
>   studentId: string,
>   title: string,
>   grade: number,
>   verified?: boolean,
> }
> 
> const updateAssignment = (assign: Assignment, propsToUpdate: Partial<Assignment>) => {
>   return { ...assign, ...propsToUpdate }
> }
> 
> const assign1: Assignment = {
>   studentId: "compsci123",
>   title: "Final Project",
>   grade: 0,
> }
> 
> console.log(updateAssignment(assign1, { grade: 95 }))
> const assignGraded: Assignment = updateAssignment(assign1, { grade: 95 })
> // console.log(assignGraded)
> 
> 
> /** Required and Readonly */
> 
> const recordAssignment = (assign: Required<Assignment>): Assignment => {
>   // send to database, etc.
>   return assign
> }
> 
> const assignVerified: Readonly<Assignment> = { ...assignGraded, verified: true } // додаємо нове поле
> 
> // NOTE: assignVerified won't work with recordAssignment!
> // Why? Try it and see what TS tells you :)
> 
> recordAssignment({ ...assignGraded, verified: true })
> 
> 
> /** Record */
> 
> const hexColorMap: Record<string, string> = {
>   red: "FF0000",
>   green: "00FF00",
>   blue: "0000FF",
> }
> 
> 
> type Students = "Sara" | "Kelly"
> type LetterGrades = "A" | "B" | "C" | "D" | "U"
> 
> const finalGrades: Record<Students, LetterGrades> = { // типу список списків
>   Sara: "B",
>   Kelly: "U"
> }
> 
> 
> interface Grades {
>   assign1: number,
>   assign2: number,
> }
> 
> const gradeData: Record<Students, Grades> = {
>   Sara: { assign1: 85, assign2: 93 },
>   Kelly: { assign1: 76, assign2: 15 },
> }
> 
> 
> /** Pick and Omit */
> 
> type AssignResult = Pick<Assignment, "studentId" | "grade"> // бачитиме лише ці поля
> 
> const score: AssignResult = {
>   studentId: "k123",
>   grade: 85,
> }
> 
> 
> type AssignPreview = Omit<Assignment, "grade" | "verified"> // вимикаємо поля - не бачитиме
> 
> const preview: AssignPreview = {
>   studentId: "k123",
>   title: "Final Project",
> }
> 
> 
> /** Exclude and Extract */
> 
> type adjustedGrade = Exclude<LetterGrades, "U">
> 
> type highGrades = Extract<LetterGrades, "A" | "B">
> 
> 
> /** Nonnullable */
> 
> type AllPossibleGrades = 'Dave' | 'John' | null | undefined
> type NamesOnly = NonNullable<AllPossibleGrades>
> 
> 
> /** ReturnType */
> 
> //type newAssign = { title: string, points: number }
> 
> const createNewAssign = (title: string, points: number) => {
>   return { title, points }
> }
> 
> type NewAssign = ReturnType<typeof createNewAssign>
> 
> const tsAssign: NewAssign = createNewAssign("Utility Types", 100)
> console.log(tsAssign)
> 
> 
> /** Parameters */
> 
> type AssignParams = Parameters<typeof createNewAssign>
> 
> const assignArgs: AssignParams = ["Generics", 100]
> 
> const tsAssign2: NewAssign = createNewAssign(...assignArgs)
> console.log(tsAssign2)
> 
> 
> /** Awaited - helps us with the ReturnType of a Promise */
> 
> interface User {
>   id: number,
>   name: string,
>   username: string,
>   email: string,
> }
> 
> const fetchUsers = async (): Promise<User[]> => {
> 
>   const data = await fetch(
>       'https://jsonplaceholder.typicode.com/users'
>   ).then(res => {
>     return res.json()
>   }).catch(err => {
>     if (err instanceof Error) console.log(err.message)
>   })
>   return data
> }
> 
> // type FetchUsersReturnType = ReturnType<typeof fetchUsers> // promise
> type FetchUsersReturnType = Awaited<ReturnType<typeof fetchUsers>> // result from promise
> 
> fetchUsers().then(users => console.log(users))
> ```
> </details>



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

### Приклад написання класу
```ts
class MyCalendar {
    private cal: [number,number][]
    constructor() 
    {
        this.cal  = []
    }

    book(start: number, end: number): boolean 
    {
        for (let i = 0; i <= this.cal.length -1 ; i++)
        {
            const booking: number[] = this.cal[i]
            if ((start >= booking[0] && start < booking[1]) || (end > booking[0] && start < booking[0]))
            {
                return false
            }
        }
        
        this.cal.push([start, end])
        return true;
    }
}
```