# React
* [Приклади React дивися в курсі на GitHub](https://github.com/SergiaS/c_react)
* Современный React с Нуля (08 - Самостоятельная Работа - Челлендж №1: 
[YouTube](https://www.youtube.com/playlist?list=PLcBbiWbF2bIxORYMi7R7t_Ga_SpTHTcmu) ||
[GitHub](https://github.com/SergiaS/c_react_challenge/commits/main)

> Дізнатися версію **React** можна в терміналі командою `npm view react version`.

> За допомогою **React** ми створюємо дерево компонентів.

> `index.js` файл запускається найперший. 

> Щоб запустити сервер **React**, потрібно перейти у теку з `package.json`. Працюємо з терміналом.
> При запуску сервера **React** `npm start` використовується `index.html`, який знаходиться в теці `public`.


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
> Якщо перша умова дає TRUE, а друга частині немає умови, тоді буде виведена друга частина:
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
> коли в якомусь `div` з одним змістом лежить інший `div` з іншим змістом - який суди не підходить. 
> Портал пересуває код туди куди треба розробнику. Часто юзають при роботі з модальними вікнами.



## Створення проекту React
Найлегший спосіб створення React-проекту, це використання інструменту
**[create-react-app](https://github.com/facebook/create-react-app)**.

Але спочатку необхідно встановити **NodeJS** (з ним набагато легче працювати - буде виконувати команди в терміналі) - він відноситься не тільки до React-фреймворка,
це середа для виконання JavaScript, яка дозволяє запускати js-код поза браузером.



## Завантаження проєкту React
У завантажених проєктах буде відсутня тека `node_modules` яка зберігає всі залежності від проєкту і важить достобіса.
Тому її не включають у commit'и, і доводиться завантажувати відсутні залежності на свій ПК самому.

Спочатку переходимо у теку завантаженого проєкту і потім пишемо у терміналі команду `npm install`.
Чекаємо поки завантажаться всі залежності з файлу проєкту `package.json`, і все.



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

> Для прослуховування введення даних від користувача потрібно додати **EventListeners**.

***

### Концепція props
Як використовувати атрибути компонента так, щоб вони працювали, а не копіювався один й той самий компонент з тими ж самими даними?

Для цього в компоненті вказують всього один параметр.
Назва його може бути будь-яка, але найчастіше називають **props**.
Далі через **props** викликають потрібний атрибут.

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

**Як використовувати один стан замість декількох в одному компоненті**

Коли ми використовуємо один стан з кількома властивостями замість кількох станів для кожного, 
при зміні стану потрібно також дбати і про всі інші властивості! 
Тут треба використовувати spread `...`, 
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

Техніко все ок, але іноді це не бути спрацьовувати.
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

**Коли потрібно слухати натискання кнопки від форми?**

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

**Як стирати значення в полях після введення даних у формі?**

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

**Ітерація структури з даними та ідентифікування компонентів**

Щоб при ітерації листа чи масиву **React** не сповіщав про помилку типу - 
`Warning: Each child in a list should have a unique "key" prop.` (відображається в консолі), 
необхідно до компоненту додати аргумент `key` - це якесь унікальне значення, краще використовувати `id`:

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


### Робота з CSS
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

#### Область видимості стилів
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
>   font: inherit;
>   padding: 0.5rem 1.5rem;
>   color: white;
>   background: #00358b;
>   border-radius: 4px;
>   box-shadow: 0 0 4px rgba(50, 50, 50, 0.25);
>   cursor: pointer;
> 
> &:focus {
>   outline: none;
> }
> 
> &:hover,
> &:active {
>   background: #245fbd;
>   box-shadow: 0 0 8px rgba(50, 50, 50, 0.25);
> }
> 
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
>   margin: 0.5rem 0;
> 
> & label {
>   font-weight: bold;
>   display: block;
>   margin-bottom: 0.5rem;
>   color: ${props => props.invalid ? "red" : "black"};
> }
> 
> & input {
>   display: block;
>   width: 100%;
>   border: 1px solid ${props => props.invalid ? "red" : "#ccc"};
>   background: ${props => props.invalid ? "rgb(243, 157, 157)" : "transparent"};
>   font: inherit;
>   line-height: 1.5rem;
>   padding: 0 0.25rem;
> }
> 
> &.input:focus {
>   outline: none;
>   background: #c8e1e4;
>   border-color: #00358b;
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

## Хукі
Готовий функціонал присутній у **React**.
Починаються зі слова **use**.
Хуки працюють тільки всередині функції компонента - не може використовувати їх за межами компонента чи всередині функції.

***

### Хук `useState` - стан
> Без використання концепції стану (useState), користувацький інтерфейс ніколи б не змінювався!

> Важливо знати! Дані в оновлювальній функції будуть старі, оскільки присвоювання робиться із затримкою!

Хук надає змогу змінювати значення змінної, так потрібно для **React**.
Тобто, ми вказуємо **React**'у, щоб він ще раз звернувся до цього коду.

При створенні, потрібно вказати дефолтне значення стану.

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

***

### Хук `Fragment`
Оскільки при синтаксисі JSX потрібно повертати 1 елемент, існує ймовірність великої вкладеності, наприклад тегу `<div>`.
Щоб цього небуло, треба використовувати існуючий функціонал **React** - обертати свій код в компонент `Fragment`,
який треба імпортувати: 
```jsx
import React, { Fragment } from "react";
```

***

### Хук `useRef`
**Refs** (лінк) - дозволяє встановлювати з'єднання між html-елементів та js-кодом.
```jsx
import React, { useRef } from "react";
```

***

### Хук `useEffect`
**useEffect** - використовується для переоцінки компонентів. 
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

***

### Хук `useReducer`
**useReducer** - допомагає керувати станом додатка як і useState, але useReducer призначений для більш складних станів. 
Використовується, наприклад, для роботи з декількома станами, з декількома способами змін стану, в залежності від інших станів, 
там де useState важко використовувати - де велика ймовірність багів.


### Debugging
> Для браузера можна встановити Google-розширення 
> [React Developer Tools](https://chrome.google.com/webstore/detail/react-developer-tools/fmkadmapgofadopljbjfkapdkoienihi?hl=ua).
> В панелі інструментів розробника з'являться нові вкладки.
