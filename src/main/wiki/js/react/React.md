# React
* [React за лаштунками - VIDEO](https://coursehunter.net/course/tvorcheskiy-kurs-po-react-i-redux?lesson=9)
* [Розділення логіки та виду - YouTube](https://www.youtube.com/watch?v=KWT8OKzrMZ4)
    > Коли компонент розростається, тоді використовують або hocs (хокі), або кастомні hooks (хукі).
    > 
    > Хокі є більш застарілим варіантом, його використовують все меньше і меньше. 
* [Приклади React дивися в курсі на GitHub](https://github.com/SergiaS/c_react)
* Современный React с Нуля:
  * 03 - Состояние и Работа с Событиями
    [YouTube](https://www.youtube.com/playlist?list=PLcBbiWbF2bIxMEzvYdCEGoMn_0oB1T-s4) ||
    [GitHub](https://github.com/SergiaS/c_react/tree/478c6f3c3e33a341057c9516f9fad3a07fdae133)
  * 08 - Самостоятельная Работа - Челлендж №1:
    [YouTube](https://www.youtube.com/playlist?list=PLcBbiWbF2bIxORYMi7R7t_Ga_SpTHTcmu) ||
    [GitHub](https://github.com/SergiaS/c_react_challenge/commits/main)
 
> Дізнатися версію **React** можна в терміналі командою `npm view react version`.

> За допомогою **React** ми створюємо дерево компонентів.

> `index.js` файл запускається найперший.

> Щоб запустити сервер **React**, потрібно перейти у теку з `package.json`. Працюємо з терміналом.
> При запуску сервера **React** `npm start` (те саме, щой `npm run start`) використовується `index.html`, який знаходиться в теці `public`.


> Перед рендерінгом (показ в браузері) код преобразиться у файл з розширенням `.jsx` (JavaScript XML - 
> не підтримується браузером), завдяки чому `.html` код читається у `.js` файлі.
> 
> Преобразований код можна подивитися у браузері - в інструментах для розробника перейти на вкладку 
> **Source**, вибрати теку `static` і файл `bundle.js`.


> Імперативний підхід - це коли даються чіткі покрокові інструкції, як в чистому **JS**.
> **React** ж - використовує декларативний підхід.


> У фігурних дужках `{}` при написанні синтаксису `.jsx` не дозволені оператори типу `if` та інші statement'и. 
> Тут дозволені тільки **js expressions** - тобто використовуй тернарний оператор.
> ```js
> // Приклад
> return (
>     <div>
>         <Card className="costs">
>             <CostsFilter year={selectedYear} onChangeYear={yearChangeHandler} />
>             {filteredCosts.length === 0 ?
>                 (<p>В цьому році витрат немає</p>) :
>                 (filteredCosts.map(cost => (
>                     <CostItem
>                         key={cost.id}
>                         date={cost.date}
>                         description={cost.description}
>                         amount={cost.amount}
>                     />
>                 )))
>             }
>         </Card>
>     </div>
> );
> ```


> `&&` використовується не тільки як **OR**. 
> Якщо перша умова дає `true`, а друга частині немає умови, тоді буде виведена друга частина:
> ```js
> // Приклад використання && як тернарного оператора
> return (
>     <div>
>         <Card className="costs">
>             <CostsFilter year={selectedYear} onChangeYear={yearChangeHandler}/>
>             {filteredCosts.length === 0 && <p>В цьому році витрат немає</p>}
>             {filteredCosts.length > 0 &&
>             filteredCosts.map(cost => (
>                 <CostItem
>                     key={cost.id}
>                     date={cost.date}
>                     description={cost.description}
>                     amount={cost.amount}
>                 />
>             ))}
>         </Card>
>     </div>
> );
> ```


> Портали - використовуються для створення коректної html-структури, інаше можливі сематичні (зміст) проблеми - наприклад, 
> коли в якомусь `div` з одним змістом лежить інший `div` з іншим змістом - який сюди не підходить. 
> Портал пересуває код туди куди треба розробнику. Часто юзають при роботі з модальними вікнами.


> You can't render a default component from promise like that.


> Щоб закоментувати конструкцію `.jsx`, окрім стандартного синтаксису html `/*...*/` або `//`, 
> треба додатково обертати у фігурні дужки: `{ /* <p>{props.name}</p> */ }`


> Щоб React не підсвічував якісь нестикування, їх можна вимкнути прописавши над цією строкою це:
> ```js
> // eslint-disable-next-line
> ```


> High Order Component (HOC) / [Компонент вищого порядку (КВП)](https://uk.reactjs.org/docs/higher-order-components.html) 
> — це просунута технологія для повторного використання логіки компоненту.
> Сам по собі КВП не є частиною React API, але через композиційну природу компонентів він є розповсюдженним патерном проектування.
>
> Тобто, компонент вищого порядку — це функція, яка приймає компонент та повертає новий компонент.


> Для роботи з валідацією у формах можно використовувати пакет [react-hook-form](https://react-hook-form.com/) - подробиці у [відео](https://www.youtube.com/watch?v=Jxfun6Jnt5Q).

> Препроцесор **Babel** дозволяє писати на `.jsx` - суміш `.html` з `.js`.


## Як працює React
> **Warning**<br>
> **НАДВАЖЛИВО!!!**<br>
> Усі функції які викликаються в компоненті (саме в блоці return) будуть викликані двічі (навідь якщо StrictMode вимкнений). Порядок роботи наступний:
> * 1 - спочатку стартує ініціалізація, будуть викликані усі функції з `return`, тому тут можливі помилки з `underfine` і `null`;
> * 2 - потім буде залучений useEffect;
> * 3 - на прикінці буде викликані усі функції з `return`, але вже з урахуванням даних.
> * 4 - з кожним re-render'ing будуть викликатися усі функції з `return` блоку.

```jsx
// приклад порядку роботи елементів 
export const TestT = () => {
  const [data, setData] = useState("empty");

  useEffect(() => {
    console.log("useEffect")
    setData("Bob")
  }, []);


  function showData() {
    console.log(data)
  }
  function test1() {
    console.log("test1")
  }
  function test2() {
    console.log("test2")
  }
  function test3() {
    console.log("test3")
  }

  return <>
    {showData()}
    {test1()}
    {test2()}
    <hr/>
  </>
}
```
```txt
-- console.log:
empty
test1
test2
useEffect
Bob
test1
test2
```

> Спочатку з завантаженням сторінки викликається функція `showData` і показує змінну `data` з дефолтним значенням `empty`.
> 
> Надалі будуть викликані функції `test1()` і `test2()` зі значеннями `test1` і `test2` відповідно.
> 
> Наразі ініціалізувалися всі три функції в блоці `return`.
> 
> Тепер буде залучений `useEffect`, котрий оновить змінну `data`.
> 
> І знову буде відмальований блок return зі своїми функціями.
> `showData` виведе оновлене значення `Bob`, а `test1()` і `test2()` покажуть свої фіксовані значення.
> 
> Потім з кожним ре-рендерінгом будуть викликатися усі методи блока `return`.




## Створення проекту React
Найлегший спосіб створення React-проекту, це використання інструменту
**[create-react-app](https://github.com/facebook/create-react-app)**.

Але спочатку необхідно встановити **NodeJS** (з ним набагато легче працювати - буде виконувати команди в терміналі) - він відноситься не тільки до React-фреймворка,
це середа для виконання JavaScript, яка дозволяє запускати js-код поза браузером.

### Використання змінних середовища - `env.local`
* [importing env variable react front end](https://stackoverflow.com/a/59509027)
* [Adding Temporary Environment Variables In Your Shell](https://create-react-app.dev/docs/adding-custom-environment-variables/)

If you need to hide your API key in **React**:
1. Create a file called `.env` (maybe `.env.local`) in the root of your project's directory.
2. Всередині вашого файлу `.env` використовуй приставку `REACT_APP_` для свого ключа, наприклад `REACT_APP_API_KEY`.
3. Add the `.env` file to your `.gitignore` file.
4. Restart your IDE program.
```js
// читаємо ключ з файлу .env
const API_KEY = process.env.REACT_APP_PROJECT_SHOWCASE_API_KEY;
```

## Завантаження проєкту React
У завантажених проєктах буде відсутня тека `node_modules` яка зберігає всі залежності від проєкту і важить достобіса.
Тому її не включають у commit'и, і доводиться завантажувати відсутні залежності на свій ПК самому.

Спочатку переходимо у теку завантаженого проєкту і потім пишемо у терміналі команду `npm install`.
Чекаємо поки завантажаться всі залежності з файлу проєкту `package.json`, і все.



## Збірка проекту
* [ReactJS + github pages – разворачиваем проект deploy на GitHub](https://it-shpora.pp.ua/reactjs-github-pages-разворачиваем-проект-deploy-на-github/)
* [package gh-pages](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/js/NodeJS.md#package-gh-pages)

> `npm install gh-pages --save-dev` - це залежність для розробки. 
> Щоб вона не збиралася до підсумкового проєкту, ми додаємо ключ `--save-dev`



## Компонент

> Компонентами є кастомні html-елементи.

> Кожен елемент повинен находитися у новому файлі.

> По суті це функція **JS** в якій використовується комбінація синтаксисів мов `.html`, `.css` та `.js`. За конвенцією функцію називають так само як і файл.
> Вкінці файлу пишуть код для використання в інших файлах, наприклад:
> ```js
> export default CostItem;
> ```
> А для використання компоненту потрібно додати зверху у файл:
> ```js
> import CostItem from "./components/CostItem";
> ```

> Компонент повинен повертати тільки один **html root** елемент, наприклад `<div>`.
> Як варіант, можна обернути увесь **html** код елементом  `<div>`.

> Щоб надати стилю компоненту, його потрібно спочатку імпортувати.

> В **React**'і аргументи компонента називаються **props** або **properties**.

> **Коли використовувати функціональні компоненти, а коли класові?**
> Функціональний компонент (наприклад, без наслідування класу Component і інші реалізації React) пишуть тоді,
> коли не потрібно працювати ані зі станом (setState), ані з життєвим циклом компонента.


***

### Концепція props
Як використовувати атрибути компонента так, щоб вони працювали, а не копіювався один й той самий компонент з тими ж самими даними?

Є 2 варіанти:

**<u>Перший</u>** - в компоненті вказують всього один параметр.
Назва його може бути будь-яка, але найчастіше називають **props**.
Далі через **props** викликають потрібний атрибут. Це свого роду масив масивів.

> <details>
> <summary>ПРИКЛАД</summary>
> 
> ```js
> // наш створений компонент CostItem
> import './CostItem.css';
> 
> function CostItem(props) {
>     return (
>         <div className='cost-item'>
>             <div>{props.date.toISOString()}</div>
>             <div className='cost-item__description'>
>                 <h2>{props.description}</h2>
>                 <div className='cost-item__price'>${props.amount}</div>
>             </div>
>         </div>
>     );
> }
> 
> export default CostItem;
> ```
> ```js
> // використання нашого компонента CostItem
> import CostItem from "./components/CostItem";
> 
> function App() {
>     const costs = [
>         {
>             date: new Date(2021, 2, 12),
>             description: "Холодильник",
>             amount: 999.99
>         },
>         {
>             date: new Date(2021, 11, 25),
>             description: "MacBook",
>             amount: 1254.72
>         },
>         {
>             date: new Date(2021, 4, 1),
>             description: "Джинси",
>             amount: 49.99
>         }
>     ];
> 
>     return (
>         <div>
>             <h1>Ready to learn</h1>
>             <CostItem
>                 date={costs[0].date}
>                 description={costs[0].description}
>                 amount={costs[0].amount}
>             ></CostItem>
>             <CostItem
>                 date={costs[1].date}
>                 description={costs[1].description}
>                 amount={costs[1].amount}
>             ></CostItem>
>             <CostItem
>                 date={costs[2].date}
>                 description={costs[2].description}
>                 amount={costs[2].amount}
>             ></CostItem>
>         </div>
>     );
> }
> 
> export default App;
> ```
> </details>

**<u>Другий варіант</u>** - це деструктуризація, де замість аргумента props параметрах компонента використовуємо
спеціальний синтаксис `{ costs }`:
> <details>
> <summary>ПРИКЛАД</summary>
>
> ```js
> // наш створений компонент CostItem
> export default function CostItem({costs}) {
>     return <>
>         <div className='cost-item'>
>             <div>{costs.date.toISOString()}</div>
>             <div className='cost-item__description'>
>                 <h2>{costs.description}</h2>
>                 <div className='cost-item__price'>${costs.amount}</div>
>             </div>
>         </div>
>     </>;
> }
> ```
> ```js
> // використання нашого компонента CostItem
> import CostItem from "./components/CostItem";
> 
> function App() {
> const costs = [
>     {
>         id: 1,
>         date: new Date(2021, 2, 12),
>         description: "Холодильник",
>         amount: 999.99
>     },
>     {
>         id: 2,
>         date: new Date(2021, 11, 25),
>         description: "MacBook",
>         amount: 1254.72
>     },
>     {
>         id: 3,
>         date: new Date(2021, 4, 1),
>         description: "Джинси",
>         amount: 49.99
>     }
> ];
> 
> return <>
>     <h1>Ready to learn</h1>
>     {costs.map(cost =>
>         <CostItem costs={cost} key={cost.id}/>
>     )}
> </>;
> }
> 
> export default App;
> ```
> </details>

***

### Концепція розділення компонентів
Це потрібно коли компоненти дуже розростаються.

Тобто це коли один компонент тримає в собі інші компоненти.

> <details>
> <summary>ПРИКЛАД</summary>
>
> ```js
> // основний компонент CostItem, внутрішній CostDate
> import './CostItem.css';
> import CostDate from "./CostDate";
> 
> function CostItem(props) {
>     return (
>         <div className='cost-item'>
>             <CostDate date={props.date}/>
>             <div className='cost-item__description'>
>                 <h2>{props.description}</h2>
>                 <div className='cost-item__price'>${props.amount}</div>
>             </div>
>         </div>
>     );
> }
> 
> export default CostItem;
> ```
> ```js
> // внутрішній компонент CostDate 
> import './CostDate.css';
> 
> function CostDate(props) {
>     const month = props.date.toLocaleString('ua-UA', {month: "long"});
>     const year = props.date.getFullYear();
>     const day = props.date.toLocaleString('ua-UA', {day: "2-digit"});
> 
>     return (
>         <div className='cost-date'>
>             <div className='cost-date__month'>{month}</div>
>             <div className='cost-date__year'>{year}</div>
>             <div className='cost-date__day'>{day}</div>
>         </div>
>     );
> }
> 
> export default CostDate;
> ```
> </details>

***

### Композиція
Конфігурування компонента БЕЗ **props**.
Композиція, це передача даних поміж тегами компонента.
Це робиться для того, щоб мінімізувати шаблонний код, об'єднати однакові властивості, наприклад в `.css`.
Для цього також потрібно передавати props - навіть якщо його немає, використовуй властивість `props.children`.
children це зарезервоване слово, його результат є все що стоїть поміж тегів компонента.

Щоб працювали вказані класи компонента, потрібно в самому компоненті використовувати змінну або спеціальний код `props.className`.

> <details>
> <summary>ПРИКЛАД</summary>
>
> ```js
> // Головний компонент
> import "./Costs.css";
> import CostItem from "./CostItem";
> import Card from "./Card";
> 
> function Costs(props) {
>     return (
>         <Card className="costs">
>             <CostItem
>                 date={props.costs[0].date}
>                 description={props.costs[0].description}
>                 amount={props.costs[0].amount}
>             />
>             <CostItem
>                 date={props.costs[1].date}
>                 description={props.costs[1].description}
>                 amount={props.costs[1].amount}
>             />
>             <CostItem
>                 date={props.costs[2].date}
>                 description={props.costs[2].description}
>                 amount={props.costs[2].amount}
>             />
>         </Card>
>     );
> }
> 
> export default Costs;
> ```
> ```js
> // Вкладенний компонет в головний
> import './CostItem.css';
> import CostDate from "./CostDate";
> import Card from "./Card";
> 
> function CostItem(props) {
>     return (
>         <Card className='cost-item'>
>             <CostDate date={props.date}/>
>             <div className='cost-item__description'>
>                 <h2>{props.description}</h2>
>                 <div className='cost-item__price'>${props.amount}</div>
>             </div>
>         </Card>
>     );
> }
> 
> export default CostItem;
> ```
> ```js
> // Компонент з композицією
> import './Card.css'
> 
> function Card(props) {
>     const classes = 'card ' + props.className;
>     return <div className={classes}>{props.children}</div>
> }
> 
> export default Card;
> ```
> </details>

***


#### Як використовувати один стан замість декількох в одному компоненті
Коли ми використовуємо один стан з кількома властивостями замість кількох станів для кожного, 
при зміні стану потрібно також дбати і про всі інші властивості - інакше вони зникнуть!
Тут треба використовувати **spread** `...`, 
тобто властивості передадуть собі ж всі дані що маються у `userInput` + змініться значення однієї властивості:

> <details>
> <summary>ПРИКЛАД</summary>
>
> ```js
> // Закоментоване використовувалось при декількох станах
> import React, { useState } from "react";
> import './CostForm.css';
> 
> const CostForm = () => {
>     // const[name, setName] = useState('');
>     // const[amount, setAmount] = useState('');
>     // const[date, setDate] = useState('');
> 
>     const [userInput, setUserInput] = useState({
>         name: '',
>         amount: '',
>         date: ''
>     });
> 
>     const nameChangeHandler = (event) => {
>         // setName(event.target.value);
>         setUserInput({
>             ...userInput,
>             name: event.target.value
>         })
>     }
> 
>     const amountChangeHandler = (event) => {
>         // setAmount(event.target.value);
>         setUserInput({
>             ...userInput,
>             amount: event.target.value
>         })
>     }
> 
>     const dateChangeHandler = (event) => {
>         // setDate(event.target.value);
>         setUserInput({
>             ...userInput,
>             date: event.target.value
>         })
>     }
>     
>     return (
>         <form>
>             <div className="new-cost__controls">
>                 <div className="new-cost__control">
>                     <label>Назва</label>
>                     <input type="text" onChange={nameChangeHandler}/>
>                 </div>
>                 <div className="new-cost__control">
>                     <label>Сума</label>
>                     <input type="number" min='0.01' step='0.01' onChange={amountChangeHandler} />
>                 </div>
>                 <div className="new-cost__control">
>                     <label>Дата</label>
>                     <input type="date" min='2019-01-01' max='2022-12-31' />
>                 </div>
>                 <div className='new-cost__actions'>
>                     <button type='submit' onChange={dateChangeHandler}>Додати витрату</button>
>                 </div>
>             </div>
>         </form>
>     );
> }
> 
> export default CostForm;
> ```
> </details>

> Але, це поганий спосіб оновлення стану, тому що **React** має затримку оновлення!

Технічно все ок, але іноді це не буде спрацьовувати.
І це не дуже хороша практика оновлення стану.

Кожного разу коли ви оновлюєте стан і по якийсь причині залежите від минулого стану,
тоді вам необхідно використовувати спеціальну альтернативну форму оновлюючої функції - 
в якості аргумента передати стрілкову функцію з даними які треба змінити:

> <details>
> <summary>ПРИКЛАД</summary>
>
> ```js
> // Небезпечний спосіб
> const amountChangeHandler = (event) => {
>     setUserInput({
>         ...userInput,
>         amount: event.target.value
>     })
> }
> ```
> ```js
> // Безпечний спосіб - React гарантує останню версію цього стану
> const nameChangeHandler = (event) => {
>     setUserInput((previousState) => {
>         return {
>             ...previousState,
>             name: event.target.value
>         }
>     })
> }
> ```
> </details>

***


#### Коли потрібно слухати натискання кнопки від форми?
Краще **EventListener** ставити не на `<button onClick={}>`, а на тегу `<form onSubmit={}`>.

**Мінус**: при натисканні на кнопку буде оновлена сторінка.
Щоб цього не було, використовуй `event.preventDefault()` - для форми не буде відправляти запити (дефолтна поведінка):

> <details>
> <summary>ПРИКЛАД</summary>
>
> ```js
> const submitHandler = (event) => {
>     event.preventDefault();
> 
>     const costData = {
>         name: inputName,
>         amount: inputAmount,
>         date: new Date(inputDate)
>     };
> 
>     console.log(costData);
> };
> 
> return (
>     <form onSubmit={submitHandler}>
>         // some code
>     </form>
> );
> ```
> </details>

***


#### Як стирати значення в полях після введення даних у формі?
Використання стану компонента дозволить це зробити - двостороннє зв'язування (two way binding). 
Просто потрібно додати атрибут в `input` який слухаємо й оновлювати стан при відправці даних з форми:

> <details>
> <summary>ПРИКЛАД</summary>
>
> ```js
> const submitHandler = (event) => {
>     event.preventDefault();
> 
>     const costData = {
>         name: inputName,
>         amount: inputAmount,
>         date: new Date(inputDate)
>     };
> 
>     console.log(costData);
>     setInputName('');
>     setInputAmount('');
>     setInputDate('');
> };
> 
> return (
>     <form onSubmit={submitHandler}>
>         <div className="new-cost__controls">
>             <div className="new-cost__control">
>                 <label>Назва</label>
>                 <input type="text" value={inputName} onChange={nameChangeHandler}/>
>             </div>
>             <div className="new-cost__control">
>                 <label>Сума</label>
>                 <input type="number" value={inputAmount} min='0.01' step='0.01' onChange={amountChangeHandler} />
>             </div>
>             <div className="new-cost__control">
>                 <label>Дата</label>
>                 <input type="date" value={inputDate} min='2019-01-01' step='2022-12-31' onChange={dateChangeHandler} />
>             </div>
>             <div className='new-cost__actions'>
>                 <button type='submit'>Додати витрату</button>
>             </div>
>         </div>
>     </form>
> );
> ```
> </details>

***


#### Ітерація структури з даними та ідентифікування компонентів
> `key` and `ref` aren't really `props`. They're used internally by **React** and not passed to components as `props`.
> If you need `id` `props` you need to create it.

Щоб при ітерації листа чи масиву **React** не сповіщав про помилку типу - 
`Warning: Each child in a list should have a unique "key" prop.` (відображається в консолі), 
необхідно до компоненту додати аргумент `key` - це якесь унікальне значення, краще використовувати `id` котрий повинен буди в об'єкті json:

> <details>
> <summary>ПРИКЛАД</summary>
>
> ```jsx
> // Ідентифікуємо кожний компонент
>  return (
>     <div>
>         <Card className="costs">
>             <CostsFilter year={selectedYear} onChangeYear={yearChangeHandler}/>
>             {props.costs.map(cost => (
>                 <CostItem
>                     key={cost.id}   // виріше проблему
>                     date={cost.date}
>                     description={cost.description}
>                     amount={cost.amount}
>                 />
>             ))}
>         </Card>
>     </div>
> );
> ```
> </details>

> З помилкою код наче і працює, проте він не надійний. React може проходити по масиву декілька разів - це не ефективно!
> Тому треба ідентифікувати компоненти завжди!
>
> Також не слід використовувати індекси при ітерації функцією `map()`, краще щоб об'єкти мали свої `id`. 
> Це пов'язано з тим, що **React** для ідентифікування `key` через `index` в функції `map()` потребує більш ресурсів:
> 
> <details>
> <summary>ПРИКЛАД з index в функції map</summary>
>
> ```jsx
> // НЕ РЕКОМЕНДУЄТЬСЯ
> return (
>     <div className="App">
>         {this.state.posts.map((post, index) => (
>             <h2 key={index}>{post.name}</h2>
>         ))}
>     </div>
> )
> ```
> </details>

***


### Класові компоненти
При роботі з класовим компонентом (наприклад, `React.Component`) треба працювати через контекст `this`,
коли звертаєшся до змінних чи передаєш їх у `props`:

> <details>
> <summary>ПРИКЛАД</summary>
>
> ```jsx
> // Приклад з лічильником по кліку кнопки
> import React, {Component} from "react";
> 
> class App extends Component {
>     constructor(props) {
>         super(props);
>         this.state = {
>             count: 0
>         }
>     }
> 
>     // альтернативна форма запису конструктора
>     // state = {
>     //    count: 0
>     // }
> 
>     handleClick = () => {
>         this.setState({count: this.state.count + 1})
>         // this.setState((prevState) => ({count: prevState.count + 1}))
>     }
>     render() {
>         return (
>             <div>
>                 <h1>
>                     Ready to learn
>                     <button onClick={this.handleClick}>{this.state.count}</button>
>                 </h1>
>             </div>
>         );
>     }
> }
> 
> export default App;
> ```
</details>

***


#### `Fragment`
Оскільки при синтаксисі JSX потрібно повертати 1 елемент, існує ймовірність великої вкладеності, наприклад тегу `<div>`.
Щоб цього не було, треба використовувати існуючий функціонал **React** - обертати свій код в компонент `Fragment`,
який треба імпортувати:
```jsx
import React, { Fragment } from "react";
```

***


#### Нюанси створення методів у класових компонентах
Три різних способа роботи з функціями:

1. Використовувати звичайну функцію `handleClick()`.
З таким варіантом необхідно в конструкторі вказувати прив'язку до контексту функції через метод `bind()` - `this.handleClick = this.handleClick.bind(this)`.
Інакше контекст буде губитися та отримаємо помилку: **Cannot read properties of undefined (reading 'setState')**.
    ```jsx
    export default class App extends Component {
        constructor(props) {
            super(props);
            this.state = {
                count: 0
            }
            this.handleClick = this.handleClick.bind(this)
        }
    
        handleClick() {
            this.setState({count: this.state.count + 1})
        }
    
        render() {
            return (
                <div>
                    <h1>
                        <button onClick={this.handleClick}>{this.state.count}</button>
                    </h1>
                </div>
            );
        }
    }
    ```

2. Використовувати стрілкову функцію `handleClick = () => { ... }`.
При цьому прив'язка контекста `this` через конструктор методом `bind()` тут **НЕ** потрібна:
    ```jsx
    export default class App extends Component {
        state = {
            count: 0
        }
    
        handleClick = () => {
            this.setState({count: this.state.count + 1})
        }
    
        render() {
            return (
                <div>
                    <h1>
                        Ready to learn
                        <button onClick={this.handleClick}>{this.state.count}</button>
                    </h1>
                </div>
            );
        }
    }
    ```

3. Використовувати анонімну функцію напряму `() => { ... }`:
    ```jsx
    export default class App extends Component {
        state = {
            count: 0
        }
    
        render() {
            return (
                <div>
                    <h1>
                        Ready to learn
                        <button onClick={() => this.setState({count: this.state.count + 1})}>
                            {this.state.count}
                        </button>
                    </h1>
                </div>
            );
        }
    }
    ```

***


#### [Рефи та DOM](https://uk.reactjs.org/docs/refs-and-the-dom.html)
**Коли використовувати рефи**

Існує декілька ситуацій, коли доцільно використовувати рефи:

* Контроль фокусу, виділення тексту чи контроль програвання медіа.
* Виклик імперативної анімації.
* Інтеграція зі сторонніми DOM-бібліотеками.
Уникайте використання рефів для будь-чого, що можна зробити декларативно.

Наприклад, замість виклику методів open() та close() компоненту Dialog, передайте йому проп isOpen.


**Реф** (Refs, посилання) - це об'єкт (створюється за допомогою `React.createRef` (часто в конструкторі) - дефолтне значення `null`), котрий можна прікрипити до React-елементів через атрибут `ref`.
```jsx
class MyComponent extends React.Component {
  constructor(props) {
    super(props);
    this.inputRef = React.createRef();
  }

  render() {
    return <input type="text" ref={this.inputRef} />;
  }

  componentDidMount() {
    this.inputRef.current.focus();
  }
}
```

***


#### Підходи створення форм в компонентах
Є форми `<form>` 2 підходів: **керовані компонентами** та **некеровані компонентами**.

**Керовані компоненти** - все що ми робимо з формою, ми повинні зберігати в `state`.
Наприклад, користувач щось набрав, натиснув `checkbox` або `radiobutton` - все це робить компонент керованим.

Тут описують принцип єдиної відповідальності, де все довіряють **React**'у, щоб він усім керував.

Бажано використовувати керовані компоненти.

***


## Робота з CSS
Існують декілька варіантів задання стиля якомусь елементу html:

1. Додавання стилю динамічно. Вказати **inline**. Такий спосіб вказування `.css`-стилю має найвищій пріорітет - будуть перевизначені усі стилі які були до цього!
    ```jsx
    // Приклад задання стилю inline:
    <form onSubmit={formSubmitHandler}>
        <div className="form-control">
            <label style={{color: !isInputValid ? 'red' : 'black' }}>Задачи</label>
            <input
                style={{
                    borderColor: !isInputValid ? "red" : "black",
                    background: !isInputValid ? "salmon" : "transparent",
                }}
                type="text" onChange={taskInputChangeHandler} />
        </div>
        <Button type="submit">Добавить Задачу</Button>
    </form>
    ```

    Коли задаються стилі **CSS** в синтаксисі `.jsx` треба писати їх у подвійних фігурних дужках `{{}}`.
    Значення пишуть в лапках завжди, коли це не змінна.
    Cтилі пишуть в лапках `''` тільки якщо є дефіс `-`, тобто так: `{{'background-color': 'yellow'}}`,
    або без лапок у CamelCase - `backgroundColor: 'yellow'`.
    ```js
    // ПРИКЛАД ЗАДАННЯ СТИЛЮ У СИНТАКСИСІ JSX
    <div className='diagram-bar__fill' style={{ height: barFillHeight }}>
      // some code
    </div>
    ```

2. Додавання класу динамічно.
Конструкція записується у зворотних лапках `` {`...`} `` всередині фігурних дужок.
У прикладі клас `form-control` додається до елемента `div` завжди, а клас `invalid` - тільки,
коли змінна `isInputValid` дає `true`:
    ```jsx
    // Той самий приклад, але тепер стилі задаються класами:
    <form onSubmit={formSubmitHandler}>
        <div className={`form-control ${!isInputValid ? 'invalid' : ''}`}>
            <label>Задачи</label>
            <input type="text" onChange={taskInputChangeHandler} />
        </div>
        <Button type="submit">Добавить Задачу</Button>
    </form>
    ```
    ```css
    .form-control.invalid input {
        border-color: red;
        background: rgb(243, 157, 157);
    }
    
    .form-control.invalid label {
        color: red;
    }
    ```

***


### Область видимості стилів
* [Приклади дивися у проєкті на GitHub](https://github.com/SergiaS/c_react_styling_components)

Є можливість завдання стиля тільки для якогось конкретного компонента. 2 найбільш популярних варіанти застосування:

1. Використовувати npm-пакет [styled-components](https://styled-components.com/) - дозволяє створювати стилізовані компоненти.
   framework в своєму js-синтаксисі (for more google **tagged template literals**) використовує backtick `` ` `` замість дужок `()` як у методів:

> <details>
> <summary>ДОКЛАДНІШЕ ПО СИНТАКСИСУ</summary>
> 
> ```jsx
> // Приклад створення кнопки за допомогою пакету styled-components
> import styled from "styled-components";
> 
> const Button = styled.button`
>     font: inherit;
>     padding: 0.5rem 1.5rem;
>     color: white;
>     background: #00358b;
>     border-radius: 4px;
>     box-shadow: 0 0 4px rgba(50, 50, 50, 0.25);
>     cursor: pointer;
>   
>   &:focus {
>     outline: none;
>   }
>   
>   &:hover,
>   &:active {
>     background: #245fbd;
>     box-shadow: 0 0 8px rgba(50, 50, 50, 0.25);
>   }
> `;
> 
> export default Button;
> ```
> 
> ***
> 
> `& ` для селектора:
> 
> ```css
> & label {
>   font-weight: bold;
>   display: block;
>   margin-bottom: 0.5rem;
> }
> ```
> `&:` для псевдоселекторів:
> ```css
> &:hover,
> &:active {
>   background: #245fbd;
>   box-shadow: 0 0 8px rgba(50, 50, 50, 0.25);
> }
> ```
> 
> `&.` для вкладених селекторів;
> ```css
> &.input:focus {
>   outline: none;
>   background: #c8e1e4;
>   border-color: #00358b;
> }
> ```
> </details>


> <details>
> <summary>ДИНАМІЧНІ PROPS В styled-components</summary>
>
> ```jsx
> // Приклад компоненту та використання props з styled-components
> import React, { useState } from "react";
> import styled from "styled-components";
> 
> import Button from "../../UI/Button/Button";
> import "./TaskInput.css";
> 
> const FormControl = styled.div`
>     margin: 0.5rem 0;
>   
>   & label {
>     font-weight: bold;
>     display: block;
>     margin-bottom: 0.5rem;
>     color: ${props => props.invalid ? "red" : "black"};
>   }
>   
>   & input {
>     display: block;
>     width: 100%;
>     border: 1px solid ${props => props.invalid ? "red" : "#ccc"};
>     background: ${props => props.invalid ? "rgb(243, 157, 157)" : "transparent"};
>     font: inherit;
>     line-height: 1.5rem;
>     padding: 0 0.25rem;
>   }
>   
>   &.input:focus {
>     outline: none;
>     background: #c8e1e4;
>     border-color: #00358b;
> `;
> 
> 
> const TaskInput = (props) => {
>     const [inputText, setInputText] = useState("");
>     const [isInputValid, setIsInputValid] = useState(true);
> 
>     const taskInputChangeHandler = (event) => {
>         if (event.target.value.trim().length > 0) {
>             setIsInputValid(true);
>         }
>         setInputText(event.target.value);
>     };
> 
>     const formSubmitHandler = (event) => {
>         event.preventDefault();
>         if(inputText.trim().length === 0) {
>             setIsInputValid(false);
>             return;
>         }
>         props.onAddTask(inputText);
>     };
> 
>     return (
>         <form onSubmit={formSubmitHandler}>
>             {/*<FormControl className={!isInputValid && 'invalid'}>*/}
>             <FormControl invalid={!isInputValid}>
>                 <label>Задачи</label>
>                 <input type="text" onChange={taskInputChangeHandler} />
>             </FormControl>
>             <Button type="submit">Добавить Задачу</Button>
>         </form>
>     );
> };
> 
> export default TaskInput;
> ```
> </details>


2. Використовувати CSS-модуль - для цього проєкт повинен бути налаштований спеціальним чином:
   * в ім'я файлу таблиці перед розширенням потрібно додати слово `.module`: `Button.module.css`
   * імпорт писати так: `import styles from './Button.module.css';`
> <details>
> <summary>ПРИКЛАД CSS-МОДУЛЯ</summary>
>
> ```jsx
> import styles from './Button.module.css';
> 
> const Button = (props) => {
>   return (
>     <button type={props.type} className={styles.button}>
>       {props.children}
>     </button>
>   );
> };
> 
> export default Button;
> ```
> </details>


3. Динамічні стилі та CSS модулі




## [Хукі / Hooks](https://uk.reactjs.org/docs/hooks-reference.html)
* [DOC: Ознайомлення з Хуками](https://uk.reactjs.org/docs/hooks-intro.html)
* [DOC: Правила хуків](https://uk.reactjs.org/docs/hooks-rules.html)

> Призначені для роботи з функціональними компонентами

Готовий функціонал присутній у **React**.
Починаються зі слова **use**.
Хуки працюють тільки всередині функції компонента - не може використовувати їх за межами компонента чи всередині функції.

***

### Хук `useState` - стан
* [Як використовувати один стан замість декількох в одному компоненті](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/js/react/React.md#як-використовувати-один-стан-замість-декількох-в-одному-компоненті)
* [Коли потрібно слухати натискання кнопки від форми?](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/js/react/React.md#коли-потрібно-слухати-натискання-кнопки-від-форми)
* [Як стирати значення в полях після введення даних у формі?](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/js/react/React.md#як-стирати-значення-в-полях-після-введення-даних-у-формі)

> Без використання концепції стану (useState), користувацький інтерфейс ніколи б не змінювався!

> В функціональних компонентах можна працювати зі звичайними об'єктами (наприклад JSON дані), 
> але тут буде складніше аніж у класовому компоненті, оскільки тут використовується useState під кожну змінну.

Хук надає змогу змінювати значення змінної, так потрібно для **React**.
Тобто, ми вказуємо **React**'у, щоб він ще раз звернувся до цього коду.

Коли потрібно змінити дані, наприклад по кліку, **React** їх не оновить,
оскільки на старті код вже був прочитаний, і більше ніде немає виклику цієї функції.
Для цього треба використовувати стан. Тобто **React** ще раз викличе функцію.
Для цього потрібно додати спеціальний імпорт з функцією:
```js
import React, { useState } from "react";
```
Далі, на початку нашої функції компонента, викликати цю імпортовану функцію.
Вона працює тільки всередині функції компонента.

В якості аргумента функції треба передати змінні які необхідно оновлювати.

**useState** повертає масив, де перший аргумент само значення, а другий - це оновлена функція.
Тут часто використовують деструктуризацію.

> <details>
> <summary>ПРИКЛАД</summary>
>
> ```js
> // Цей код буде оновлювати значення змінної description
> import './CostItem.css';
> import CostDate from "./CostDate";
> import Card from "../UI/Card";
> import React, { useState } from "react";
> 
> const CostItem = (props) => {
>     const [description, setDescription] = useState(props.description);
> 
>     const changeDescriptionHandler = () => {
>         // description = 'New cost';
>         setDescription("New description");  // update value
>         console.log(description);
>     }
>     return (
>         <Card className='cost-item'>
>             <CostDate date={props.date}/>
>             <div className='cost-item__description'>
>                 <h2>{description}</h2>
>                 <div className='cost-item__price'>${props.amount}</div>
>             </div>
>             <button onClick={changeDescriptionHandler}>Змінити опис</button>
>         </Card>
>     );
> }
> 
> export default CostItem;
> ```
> </details>

Коли робиться присвоювання (`setDescription`), також робиться і виклик функції компоненту (тобто увесь компонент оновлюється).

При створенні, потрібно вказати дефолтне значення стану, або реалізувати функцію.

> Важливо знати! Дані в оновлювальній функції будуть старі, оскільки присвоювання робиться із затримкою!
> Тому, якщо потрібне нове значення, тут краще використовувати функцію замість значення:
> ```jsx
> // присвоєння через значення
> setCount(count + 1);
> console.log(count);     // old value, count = 0
> ```
> ```jsx
> // присвоєння через функцію
> setCount((previousCount) => {
>     previousCount = previousCount + 1;
>     console.log(previousCount);  // new value, count = 1
>     return previousCount;
> })
> console.log(count);     // old value, count = 0
> ```

> <details>
> <summary>ПРИКЛАД оновлення стану з усіма змінними - використовуй spread ...</summary>
> 
> Щоб значення не загубилося, потрібно використовувати **spread** оператор `...`, котрий як би каже, що у `setState` ми беремо усі
> дані що були, і оновлюємо якість окремі змінні, наприклад `count`:
> ```jsx
> import React, { useState, useEffect } from "react";
> 
> export const State = () => {
>     const [state, setState] = useState({
>         count: 0,
>         isCounting: false,
>     });
> 
>     const handleCount = () => {
>         setState({
>             ...state,
>             count: state.count + 1
>         });
>     }
> 
>     const handleStatus = () => {
>         setState({
>             ...state,
>             isCounting: !state.isCounting
>         });
>     }
> 
>     useEffect(() => {
>         console.log(state);
>     }, [state]);
> 
>     return <div>
>         <button onClick={handleCount}>count</button>
>         <button onClick={handleStatus}>status</button>
>     </div>
> }
> ```
> </details>


> <details>
> <summary>ПРИКЛАД оновлення стану N раз</summary>
> 
> ```jsx
> export const WithState = () => {
>     const [count, setCount] = useState(0);
> 
>     const handleClick = () => {
>         // count оновиться лише на 1
>         setCount(count + 1);
>         setCount(count + 1);
>         setCount(count + 1);
> 
>         // count оновиться на 3
>         // setCount((prevCount) => prevCount + 1)
>         // setCount((prevCount) => prevCount + 1)
>         // setCount((prevCount) => prevCount + 1)
>     }
> 
>     return (
>         <div>
>             <button onClick={handleClick}>{count}</button>
>         </div>
>     )
> }
> ```
> </details>


***

### Хук `useRef`
> Аналог у класовому компоненті - `createRef`.

**Refs** (лінк) - дозволяє встановлювати з'єднання між html-елементів та js-кодом.
```jsx
import React, { useRef } from "react";
```

***

### [Хук useEffect](https://uk.reactjs.org/docs/hooks-effect.html)
**useEffect** - використовується для переоцінки компонентів, тобто відповідає за роботу життєвого циклу компонента. 

`useEffect`, сам по собі повністю асинхроний, тобто той код який буде всередині нього виконуватися - не буде блокувати спільну роботу додатку.

Даний **хук `useEffect` функціонального компонента тримає у собі можливості класового компонента**, а саме: 
* `componentDidMount` (що працює перед завантаження сторінки - перед змонтуванням компоненту):
    ```jsx
    // аналогічно componentDidMount
    useEffect(() => {
        console.log("Hello from clicker")
    }, [])
    ```
* `componentDidUpdate` (що дозволяє оновлювати дані компоненту):
    ```jsx
    // аналогічно componentDidMount і componentDidUpdate
    // буде працювати при завантажені сторінки/компонента і при його оновлені
    useEffect(() => {
    	console.log("Hello from clicker")
    }, [count])
    ```
* `componentWillUnmount` (що працює на кінцевій стадії компоненту, наприклад вивільняє ресурси).
    ```jsx
    // аналогічно componentDidUpdate і componentWillUnmount
    // використовується оператор `return` у callback-функції
    // тут буде спрацьовувати кожен раз при оновлені залежностей + при розмонтуванні 
    useEffect(() => {
        console.log("Hello from clicker", count);
        return () => console.log('goodbye clicker');
    }, [count])
    ```

`useEffect` - приймає дві сутності (аргумента):
- **перша** - деяка функція/дія котру хочемо зробити у якийсь-то момент життєвого циклу;
- **друга** - якийсь набір залежностей, по доброму - повинен бути завжди, інакше компонент буде постійно перемальовуватися (render);

Метод буде виконуватися тільки коли завантажується сторінка, або коли змінюються його залежності (другий аргумент).
```jsx
import React, { useEffect } from "react";
```
```jsx
// Функція працює тільки при завантаженні сторінки без залежностей 
useEffect(() => {
    const storedLoginInfo = localStorage.getItem('isLoggedIn');
    if (storedLoginInfo === '1') {
        setIsLoggedIn(true);
    }
}, [])
```
```jsx
// Функція буде працювати при зміні залежностей (inputEmail чи inputPassword)
useEffect(() => {
    setFormIsValid(
        inputEmail.includes("@") && inputPassword.trim().length > 7
    );
}, [inputEmail, inputPassword])
```
<br>

`useEffect`'ів може бути багато на сторінці.
Тому тут часто над кожним `useEffect` пишуть коменти - за що конкретний `useEffect` відповідає, або іменувати функції:
```jsx
// what effect does
useEffect(() => {}, [])

useEffect(function initPlugin() {
	somePlugin.init();
}, [])
```

***

### Хук `useContext`
* [Приклад: Переключение всех компонентов на контекст вместо пропсов](https://github.com/SergiaS/c_react/tree/ffa9a8027173a86be699c9f26b39801e494b9c11)

Надає доступ до вказаних даних усім хто буде його дочірнім елементом.

> <details>
> <summary>ПРИКЛАД створення контексту</summary>
> 
> ```jsx
> // 1|4: файл з контекстом
> // створюємо контекст за допомою createContext
> import React, { createContext, useState} from "react";
> 
> export const CustomContext = createContext();
> 
> export const Context = (props) => {
>     const [books, setBooks] = useState([
>         {id: 1, title: 'JS'},
>         {id: 2, title: 'React'},
>         {id: 3, title: 'NodeJS'},
>     ]);
> 
>     const addBook = (book) => {
>         setBooks([book], ...books);
>     }
> 
>     const removeBook = (id) => {
>         setBooks(books.filter(book => book.id !== id));
>     }
> 
>     // вказуємо функції та змінні, які зможуть мати дочірні компоненти
>     const value = {
>         books,
>         addBook,
>         removeBook,
>     }
> 
>     // передаємо дані через Provider
>     return <CustomContext.Provider value={value}>
>         {props.children}
>     </CustomContext.Provider>
> }
> ```
> ```jsx
> // 2|4: головний файл App
> // обертаємо наші компоненти у контекст
> import {Context} from "./hooks/Context";
> import {Books} from './components/Books'
> 
> export function App() {
>     // обертаємо return у наш контекст
>     return <Context>
>         {/* тепер у всіх дочірніх компонентів (після налаштувань у них) */}
>         {/* зможуть отримати дані Context */}
>         <Books />
>     </Context>
> }
> ```
> ```jsx
> // 3|4: компонент Books
> // дочірній компонент звертається до контексту
> import React, { useContext } from "react";    // імпортуємо хук контексту
> import { CustomContext } from "../hooks/Context"; // імпортуємо наш контекс
> 
> import { Book } from './Book';
> 
> export function Books() {
>     // отримуємо книги через наш контекст CustomContext
>     const { books = [] } = useContext(CustomContext);
>     console.log(books);
> 
>     return <div className="books">
>         {
>             books.map(book => {
>                 return <Book key={book.id} {...book} />
>             })
>         }
>     </div>
> }
> ```
> ```jsx
> // 4|4: компонент Book
> // дочірній компонент звертається до контексту
> import React, { useContext } from "react";  // імпортуємо хук контексту
> import { CustomContext } from "../hooks/Context";   // імпортуємо наш контекс
> 
> export function Book(props) {
>     // отримуємо функцію для видалення з нашого контексту CustomContext
>     const { removeBook } = useContext(CustomContext);
>     return <h2 onClick={() => removeBook(props.id)}>{props.title}</h2>
> }
> ```
> </details>

***

### Хук `useLayoutEffect`
`useLayoutEffect` дуже схожий на `useEffect`.
Основна відмінність: якщо `useEffect` працює асинхроно (не блокує відмальовку сторінки), то `useLayoutEffect` виконується синхроно (може пригальмувати завантаження сторінки).

Загалом `useLayoutEffect` використовують коли у рідкісних випадках (коли псують візуальну поведінку) потрібно лізти у звичайний DOM, і щось там змінювати.
Але у 99% використовують саме `useEffect`.

***

### Хукі `useCallback` і `useMemo`
`useCallback` і `useMemo` використовуються коли є складні функції та розрахунки з великими числами.

`useCallback` - повертає мемоізовану версію функції, не виконує саму функцію в середині хука.

`useMemo` - завжди повертає мемоізоване значення - результат виконання самої функції.

***

### Хук `useImperativeHandle`
**React** передає дані знизу у верх (props), але `useImperativeHandle` дає можливість змінити напрямок руху props.

***

### [Хук `useReducer`](https://uk.reactjs.org/docs/hooks-reference.html#usereducer)
* [Code Example](https://codesandbox.io/s/vigorous-tree-74hfc?file=/src/App.js)

> Прийшло від REDUX.

**useReducer** - допомагає керувати станом додатка як і `useState`, але `useReducer` призначений для більш складних станів. 
Використовується, наприклад, для роботи з декількома станами, з декількома способами змін стану, в залежності від інших станів, 
там де `useState` важко використовувати - де велика ймовірність багів.

`useReducer` повертає дві сутності, як і `useState`:
* Перша сутність (деструктурують) - це стан за замовчуванням - те, з чим ми будемо працювати.
* Друга сутність - якась функція оновлення, як правило, її називають **dispatch**.

Коли працюють з **dispatch**, загалом використовують один чи пару ключів:
* `type` - тип івента, який прийде в **reducer**;
* `payload` - додаткові/корисні дані, наприклад **id**.

> <details>
> <summary>ПРИКЛАД таймеру на useReducer</summary>
> 
> ```jsx
> import React, {useEffect, useReducer} from "react";
> 
> const countReducer = (state, {type}) => {
>     if (type === 'START') {
>         return {
>             ...state,
>             isCounting: true,
>         }
>     }
> 
>     if (type === 'STOP') {
>         return {
>             ...state,
>             isCounting: false,
>         }
>     }
> 
>     if (type === 'RESET') {
>         return {
>             count: 0,
>             isCounting: false,
>         }
>     }
> 
>     if (type === 'TICK') {
>         return {
>             ...state,
>             count: state.count + 1,
>         }
>     }
> 
>     return state;
> }
> 
> function setDefaultValue() {
>     const userCount = localStorage.getItem('timer');
>     return userCount ? +userCount : 0;
> }
> 
> export default function TimerByFunctionalComponent() {
>     const[{count, isCounting}, dispatch] = useReducer(countReducer, {count: setDefaultValue(), isCounting: false});
> 
>     // записує значення в localStorage
>     useEffect(() => {
>         localStorage.setItem('timer', count);
>     }, [count]);
> 
>     // займається підрахунком
>     useEffect(() => {
>         let timerId = null;
>         if (isCounting) {
>             timerId = setInterval(() => {
>                 dispatch({type: 'TICK'})
>             }, 1000);
>         }
> 
>         return () => {
>             timerId && clearInterval(timerId);
>             timerId = null;
>         }
>     }, [isCounting])
> 
>     return (
>         <div className="timer">
>             <h1>React Timer by Functional Component</h1>
>             <h3>{count}</h3>
>             {!isCounting ? (
>                 <button onClick={() => dispatch({type: 'START'})}>Start</button>
>             ) : (
>                 <button onClick={() => dispatch({type: 'STOP'})}>Stop</button>
>             )}
>             <button onClick={() => dispatch({type: 'RESET'})}>Reset</button>
>         </div>
>     )
> }
> ```
> </details>

***

### Користувацькі хуки
* [Code Example](https://codesandbox.io/s/staging-resonance-rhpl4?file=/src/App.js)

***


## Запобіжники
* [DOC - Запобіжники](https://uk.reactjs.org/docs/error-boundaries.html)
* [react-error-boundary](https://www.npmjs.com/package/react-error-boundary)

> Запобіжники працюють як блок `catch {}` в **JavaScript**, тільки для компонентів.

Запобіжники - це можливість оброблювати помилки для клієнта (те що клієнт буде бачити), - є тільки у класових компонентів.

Запобіжники – це React-компоненти, які відстежують помилки **JavaScript** в усьому дереві своїх дочірніх компонентів, логують їх, а також відображають запасний UI замість дерева компонентів, що зламалось.


### react-error-boundary package
* [Look examples at GitHub](https://github.com/SergiaS/t_react/tree/error-boundary)
Можна написати свої запобіжники, або використовувати вже готові, наприклад встановити пакет `react-error-boundary`, котрий надає компонент вищого порядку `withErrorBoundary`:
```jsx
// імпортуємо хок
import {withErrorBoundary} from "react-error-boundary";

// обертаємо наш App у хок, функція виконається у разі помилки
export default withErrorBoundary(App, {
    fallback: <div>Something went wrong</div>
});
```

`withErrorBoundary` має три опції, повертає лише одну з них, пріорітет наступний
- fallback (проста розмітка):
```jsx
fallback: <div>Something went wrong</div>
```
- fallbackRender (функція, котра поверне якусь розмітку):
```jsx
fallbackRender: () => <div>Something went wrong</div>
```
- FallbackComponent (готовий написаний компонент з розміткою):
```jsx
FallbackComponent={ErrorFallback} // own component
```




## Debugging
> Для браузера можна встановити Google-розширення 
> [React Developer Tools](https://chrome.google.com/webstore/detail/react-developer-tools/fmkadmapgofadopljbjfkapdkoienihi?hl=ua).
> В панелі інструментів розробника з'являться нові вкладки.


## [Статична типізація](https://uk.reactjs.org/docs/static-type-checking.html)
Для статичної типізації (перевірка на тип) рекомендується використовувати або **Flow**, або **TypeScript**. 
Якщо працюємо лише з **JS**, тоді можна використовувати бібліотеку **PropTypes**.



## Security
* [Modify React to handle CSRF and be identity-aware](https://developer.okta.com/blog/2022/06/17/simple-crud-react-and-spring-boot#modify-react-to-handle-csrf-and-be-identity-aware)
