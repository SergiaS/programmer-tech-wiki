# React Code Snippets, Examples
* [How to Initialize, Add, Remove and Iterate in Set, Object and Array](https://stackoverflow.com/a/70234746)


<details>
<summary>ПРИКЛАД клікера-лічильника</summary>

```jsx
import React, {Component} from "react";

export default class App extends Component {
    state = {
        count: 0
    }

    increment = () => {
        this.setState({count: this.state.count + 1})
    }

    decrement = () => {
        this.setState({count: this.state.count - 1})
    }

    render() {
        return (
            <div 
                className="App" 
                style={
                    {
                        margin: 'auto', 
                        width: '300px'}
                }>
                <button
                    onClick={this.decrement}
                >
                    -
                </button>
                <span
                    style={countStyle}
                >
                    {this.state.count}
                </span>
                <button
                    onClick={this.increment}
                >
                    +
                </button>
            </div>
        );
    }
}

const countStyle = {
    margin: '0 0.75rem',
    display: 'inline-block'
}
```
</details>

***

<details>
<summary>ПРИКЛАД роботи з fetch - componentDidMount, componentDidUpdate, componentWillUnmount</summary>

Якщо просто запросити дані через `fetch` - ми не отримаємо дані:
```js
fetch('https://jsonplaceholder.typicode.com/posts')
    .then(console.log)
```

треба використовувати метод `json()` з `Prototype` (`__proto_`_) - він преобразує результат в той набір даних, котрий нам потрібен:
```js
fetch('https://jsonplaceholder.typicode.com/posts')
    .then(response => response.json())
    .then(data => this.setState({posts: data}))
```
```jsx
export default class App extends Component {
    state = {
        posts: [],
        loading: true,
        comments: [],
    }

    componentDidMount() {
        console.log('componentDidMount')
        fetch('https://jsonplaceholder.typicode.com/posts')
            .then(response => response.json())
            .then(data => this.setState({
                posts: data,
                loading: false
            }))

        this.timerId = setInterval(() => {
            fetch('https://jsonplaceholder.typicode.com/comments')
                .then(response => response.json())
                .then(data => this.setState({
                    comments: data,
                }))
        }, 3000)
    }
    componentDidUpdate() {
        console.log('componentDidUpdate')
    }
    componentWillUnmount() {
        console.log('componentWillUnmount')
        clearInterval(this.timerId)
    }

    render() {
        return (
            <div className="App">
                {this.state.loading
                    ? <h3>Loading...</h3>
                    : <h3>{this.state.posts.length} was loaded</h3>}
            </div>
        );
    }
}
```
</details>


***


<details>
<summary>ПРИКЛАД: Створення таймера на класовому і функціональному компонентах, з життєвим циклом</summary>

Задача створити таймер з використанням `LocalStorage`.

Різниця у тому, що класовий компонент використовує дефолтні методи для керування станом `componentDidMount`, `componentDidUpdate` та `componentWillUnmount`,
а функціональний компонент - `useState`, `useEffect` та `useRef`.
```jsx
// Таймер на класовому компоненті 
import React, {Component} from "react";

export default class TimerByClassComponent extends Component {
    state = {
        count: 0,
        isCounting: false
    }

    // працює при завантаженні сторінки
    componentDidMount() {
        const userCount = localStorage.getItem('timer');
        if (userCount) {
            this.setState({count: +userCount});
        }
    }

    // працює кожного разу при виклику setState
    componentDidUpdate(prevProps, prevState, snapshot) {
        localStorage.setItem('timer', this.state.count);
    }

    // працює при закритті сторінки 
    componentWillUnmount() {
        clearInterval(this.counterId);
    }

    handleStart = () => {
        this.setState({isCounting: true});
        this.counterId = setInterval(() => {
            this.setState({count: this.state.count + 1});
        }, 1000);
    }

    handleStop = () => {
        this.setState({isCounting: false});
        clearInterval(this.counterId);
    }

    handleReset = () => {
        this.setState({count: 0, isCounting: false})
        clearInterval(this.counterId);
    }

    render() {
        return (
            <div className="App">
                <h1>React Timer</h1>
                <h3>{this.state.count}</h3>
                {!this.state.isCounting ? (
                    <button onClick={this.handleStart}>Start</button>
                ) : (
                    <button onClick={this.handleStop}>Stop</button>
                )}
                <button onClick={this.handleReset}>Reset</button>
            </div>
        )
    }
}
```
```jsx
// Таймер на функціональному компоненті
import React, {useState, useEffect, useRef} from "react";

function setDefaultValue() {
    const userCount = localStorage.getItem('timer');
    return userCount ? +userCount : 0;
}

export default function TimerByFunctionalComponent() {
    const[count, setCount] = useState(setDefaultValue());
    const[isCount, setIsCount] = useState(false);
    const timerIdRef = useRef(null);

    const handleStart = () => {
        setIsCount(true);
    }

    const handleStop = () => {
        setIsCount(false);
    }

    const handleReset = () => {
        setCount(0);
        setIsCount(false);
    }

    // записує значення в localStorage
    useEffect(() => {
        localStorage.setItem('timer', count);
    }, [count]);

    // займається підрахунком
    useEffect(() => {
        if (isCount) {
            timerIdRef.current = setInterval(() => {
                setCount((prevCount) => prevCount + 1);
            }, 1000);
        }

        return () => {
            timerIdRef.current && clearInterval(timerIdRef.current);
            timerIdRef.current = null;
        }
    }, [isCount])

    return (
        <div className="timer">
            <h1>React Timer by Functional Component</h1>
            <h3>{count}</h3>
            {!isCount ? (
                <button onClick={handleStart}>Start</button>
            ) : (
                <button onClick={handleStop}>Stop</button>
            )}
            <button onClick={handleReset}>Reset</button>
        </div>
    )
}
```
</details>


***


<details>
<summary>ПРИКЛАД: Створення пошуку на класовому і функціональному компонентах, без життєвого циклу</summary>

```jsx
// 1|2: класовий компонент
import React from "react";

export class Search extends React.Component {
    state = {
        search: '',
        type: 'all',
    }

    handleKey = (event) => {
        if (event.key === 'Enter') {
            this.props.searchMovies(this.state.search, this.state.type);
        }
    }

    handleFilter = (event) => {
        this.setState(() => ({type: event.target.dataset.type}), () => {
            this.props.searchMovies(this.state.search, this.state.type);
        });
    }

    render() {
        return (
            <div className="row">
                <div className="input-field">
                    <input
                        className="validate"
                        placeholder='search'
                        type="search"
                        value={this.state.search}
                        onChange={(e) => this.setState({search: e.target.value})}
                        onKeyDown={this.handleKey}
                    />
                    <button className="btn search-btn"
                            onClick={() => this.props.searchMovies(this.state.search, this.state.type)}>SEARCH
                    </button>
                </div>
                <div>
                    <label>
                        <input className="with-gap"
                               name="type"
                               type="radio"
                               data-type="all"
                               onChange={this.handleFilter}
                               checked={this.state.type === 'all'}
                        />
                        <span>All</span>
                    </label>
                    <label>
                        <input className="with-gap"
                               name="type"
                               type="radio"
                               data-type="movie"
                               onChange={this.handleFilter}
                               checked={this.state.type === 'movie'}
                        />
                        <span>Movies</span>
                    </label>
                    <label>
                        <input className="with-gap"
                               name="type"
                               type="radio"
                               data-type="series"
                               onChange={this.handleFilter}
                               checked={this.state.type === 'series'}
                        />
                        <span>Series</span>
                    </label>
                </div>
            </div>
        )
    }
}
```
```jsx
// 2|2: функціональний компонент
import React, {useState} from "react";

export const Search = (props) => {
    const {
        searchMovies = Function.prototype,
    } = props;

    const [search, setSearch] = useState('');
    const [type, setType] = useState('all');

    const handleKey = (event) => {
        if (event.key === 'Enter') {
            searchMovies(search, type);
        }
    }

    const handleFilter = (event) => {
        setType(event.target.dataset.type);
        searchMovies(search, event.target.dataset.type);
    }

    return (
        <div className="row">
            <div className="input-field">
                <input
                    className="validate"
                    placeholder='search'
                    type="search"
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                    onKeyDown={handleKey}
                />
                <button className="btn search-btn"
                        onClick={() => searchMovies(search, type)}>SEARCH
                </button>
            </div>
            <div>
                <label>
                    <input className="with-gap"
                           name="type"
                           type="radio"
                           data-type="all"
                           onChange={handleFilter}
                           checked={type === 'all'}
                    />
                    <span>All</span>
                </label>
                <label>
                    <input className="with-gap"
                           name="type"
                           type="radio"
                           data-type="movie"
                           onChange={handleFilter}
                           checked={type === 'movie'}
                    />
                    <span>Movies</span>
                </label>
                <label>
                    <input className="with-gap"
                           name="type"
                           type="radio"
                           data-type="series"
                           onChange={handleFilter}
                           checked={type === 'series'}
                    />
                    <span>Series</span>
                </label>
            </div>
        </div>
    )
}
```
</details>


***


<details>
<summary>ПРИКЛАД роботи з CallBack функцією у props</summary>

Якщо передається функція в якості `props`, щоб до неї звернутися з якогось компонента, потрібно це робити через
**CallBack** функцію `() => ...` інакшу при завантаженні сторінка функція буде викликатися, і отримаєш не те на що очікуєш:

```jsx
// не вірно:
return <h2>{name} <button onClick={props.delete(id)}>delete</button></h2>

// вірно:
return <h2>{name} <button onClick={() => props.delete(id)}>delete</button></h2>
```
</details>


***


<details>
<summary>ПРИКЛАД видаляє кнопку по кліку - оновлення стану через дочірні компоненти</summary>

```jsx
// 1/3 file App.js
import React, {Component} from "react";
import {Posts} from "./components/Posts";

export default class App extends Component {
    state = {
        posts: [
            {id: 'abc1', name: 'JS Basics'},
            {id: 'abc2', name: 'JS Advanced'},
            {id: 'abc3', name: 'React JS'},
        ]
    }

    removePost = (id) => {
        this.setState(
            {posts: this.state.posts.filter(post => post.id !== id)}
        )
    }

    render() {
        const {posts} = this.state;
        return (
            <div className="App">
                <Posts posts={posts} removePost={this.removePost} />
            </div>
        )
    }
}
```
```jsx
// 2/3 file Posts.jsx
import {Post} from "./Post";

export function Posts(props) {
    return <div>
        {props.posts.map(post => (
            <Post
                key={post.id}
                id={post.id}
                name={post.name}
                removePost={props.removePost}
            />
        ))}
    </div>
}
```
```jsx
// 3/3 file Post.jsx
export function Post(props) {
    const {id, name, removePost} = props;
    return <h2>{name}
        <button
            onClick={() => removePost(id)}
        >
            delete
        </button>
    </h2>
}
```
</details>


***


<details>
<summary>ПРИКЛАД прослуховування 1 input'у користувача</summary>

> Якщо використовується `state` для `input`, тоді для зміни значення в `input` потрібно використовувати `onChange`.

> `value` увесь час повинен брати значення з нашого `state`.
```jsx
import React from "react";

export class Form extends React.Component {
    state = {
        firstName: '',
    }

    handleChange = (event) => {
        this.setState({firstName: event.target.value})
    }

    render() {
        const {firstName} = this.state;
        
        return (
            <div>
                <input
                    type="text"
                    name="firstName"
                    placeholder="firstName"
                    value={firstName}
                    onChange={this.handleChange}
                />
            </div>
        );
    }
}
```
</details>


***


<details>
<summary>ПРИКЛАД прослуховування N input'ів користувача</summary>

Щоб не дублювати код (кожне ім'я стану - `firstName`, `email`...) тут слід використовувати ім'я `input`'у, а саме `[event.target.name]`:
```jsx
import React from "react";

export class Form extends React.Component {
    state = {
        firstName: '',
        email: '',
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value})
    }

    render() {
        const {firstName, email} = this.state;

        return (
            <div>
                <input
                    type="text"
                    name="firstName"
                    placeholder="firstName"
                    value={firstName}
                    onChange={this.handleChange}
                />
                <input
                    type="text"
                    name="email"
                    placeholder="email"
                    value={email}
                    onChange={this.handleChange}
                />
            </div>
        );
    }
}
```
</details>


***


<details>
<summary>ПРИКЛАД простої валідації по виходу з поля (onBlur)</summary>

Функція `test()` відноситьс я до інтерфейсу **RegExp** - перевірка `email` по заданній регулярці:
```jsx
import React from "react";

export class Form extends React.Component {
    state = {
        firstName: '',
        email: '',
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value})
    }

    validateName = () => {
        if (this.state.firstName.length < 5) {
            alert('Your first name can\'t be less than 5 letters');
        }
    }

    validateEmail = () => {
        if (!/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
            .test(this.state.email)) {
            alert('email is not valid');
        }
    }

    render() {
        const {firstName, email} = this.state;

        return (
            <div>
                <input
                    type="text"
                    name="firstName"
                    placeholder="firstName"
                    value={firstName}
                    onChange={this.handleChange}
                    onBlur={this.validateName}
                />
                <input
                    type="text"
                    name="email"
                    placeholder="email"
                    value={email}
                    onChange={this.handleChange}
                    onBlur={this.validateEmail}
                />
            </div>
        );
    }
}
```
</details>


***


<details>
<summary>ПРИКЛАД створення елементів - textarea, select, radio button, checkbox</summary>

Функція `test()` відноситься до інтерфейсу **RegExp** - перевірка `email` по заданній регулярці:
```jsx
import React from "react";

export class Form extends React.Component {
    state = {
        firstName: '',
        email: '',
        message: '',
        select: '',
        subscription: false,
        gender: '',
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value})
    }

    handleCheckBoxChange = (event) => {
        this.setState({[event.target.name]: event.target.checked})
    }

    render() {
        const {firstName, email, message, select, subscription, gender} = this.state;

        return (
            <div>
                <input
                    type="text"
                    name="firstName"
                    placeholder="firstName"
                    value={firstName}
                    onChange={this.handleChange}
                />
                <input
                    type="text"
                    name="email"
                    placeholder="email"
                    value={email}
                    onChange={this.handleChange}
                />
                <br/>
                <textarea name="message" value={message} onChange={this.handleChange}/>
                <br/>
                <select name="select" value={select} onChange={this.handleChange}>
                    <option value="" disabled/>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                </select>
                <br/>
                <label>
                    <input
                        type="checkbox"
                        name="subscription"
                        checked={subscription}
                        onChange={this.handleCheckBoxChange}
                    />
                    Subscription
                </label>
                <br/>
                <input
                    type="radio"
                    name="gender"
                    value="male"
                    onChange={this.handleChange}
                    checked={gender === 'male'}
                />
                Male
                <input
                    type="radio"
                    name="gender"
                    value="female"
                    onChange={this.handleChange}
                    checked={gender === 'female'}
                />
                Female
            </div>
        );
    }
}
```
</details>


***


<details>
<summary>ПРИКЛАД автоматичного перемикання фокусу (рефи, посилання, Refs) курсора після умови</summary>

```jsx
import React from "react";

export default class FormWithRef extends React.Component {
    constructor() {
        super();
        this.state = {
            card: '',
            email: '',
        }
        this.cardRef = React.createRef();
        this.emailRef = React.createRef();
    }

    // дефолтна функція, яка працює при завантаженні сторінки
    componentDidMount() {
        console.log(this.cardRef)
        // 1 - фокус на елементі card при завантаженні сторінки
        this.cardRef.current.focus();
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value}, () => {
            if (this.state.card.length === 16) {
                // 2 - як у полі card буде 16 символів - фокус перемкнеться на email
                this.emailRef.current.focus();
            }
        })
    }

    render() {
        const {card, email} = this.state;

        return <div>
            <input
                type="text"
                name="card"
                placeholder="card"
                value={card}
                onChange={this.handleChange}
                ref={this.cardRef}
            />
            <input
                type="email"
                name="email"
                placeholder="email"
                value={email}
                onChange={this.handleChange}
                ref={this.emailRef}
            />
        </div>
    }
}
```
</details>


***

<details>
<summary>ПРИКЛАД написання методу підрахунку товарів у кошику</summary>

```jsx
import {useState, useEffect} from "react";
import {API_KEY, API_URL} from "../config";

import {Preloader} from "./Preloader";
import {GoodsList} from "./GoodsList";
import {Cart} from "./Cart";

function Shop() {
    const [goods, setGoods] = useState([]);
    const [loading, setLoading] = useState(true);
    const [order, setOrder] = useState([]);

    const addToBasket = (item) => {
        // чи є товар у кошику
        const itemIndex = order.findIndex(orderItem => orderItem.id === item.id);

        // якщо < 0, тоді товара немає...
        if (itemIndex < 0) {
            // ...і ми додаємо до цього товару q 1
            const newItem = {
                ...item,
                quantity: 1,
            }
            setOrder([...order, newItem])
        }
        // якщо >=0, тоді товар є...
        else {
            // ...шукаємо його позицію в масиві...
            const newOrder = order.map((orderItem, index) => {
                // ...і, коли це той самий товар під індексом як і itemIndex...
                if (index === itemIndex) {
                    return {
                        ...orderItem,
                        // ...тоді змінюємо його q на +1
                        quantity: orderItem.quantity + 1
                    }
                }
                // ...і, якщо індекс інший - товар таким і залишається
                else {
                    return orderItem;
                }
            });
            // оновлюємо стан товарів в заказі
            setOrder(newOrder);
        }
    }

    useEffect(function getGoods() {
        fetch(API_URL, {
            headers: {
                'Authorization': API_KEY,
            },
        })
            .then(response => response.json())
            .then(data => {
                data.featured && setGoods(data.featured);
                setLoading(false);
            })
    }, []);

    return (
        <main className='container content'>
            <Cart quantity={order.length}/>
            {loading
                ? <Preloader/>
                : <GoodsList goods={goods} addToBasket={addToBasket}/>
            }
        </main>
    )
}

export {Shop};
```

</details>


***

<details>
<summary>ПРИКЛАД fetch у useEffect та збереженням в Map + ітерація по значеннях</summary>

```jsx
const [cars, setCars] = useState(new Map());

useEffect(() => {
    // console.log(cars.size === 0);
    if (cars.size === 0) {
      getByUrl("/home/" + brand)
        .then(data => {
          for (let i = 0; i < data.length; i++) {
            let key = data[i]["generation"] + ", " + data[i]["bodyType"];

            let tmpArr;
            if (!cars.has(key)) {
              tmpArr = [];
            } else {
              tmpArr = cars.get(key);
            }
            tmpArr.push(data[i]);
            setCars(new Map(cars.set(key, tmpArr)));

          // показати всі поля
            // console.log(
            //   "id: " + data[i]["id"] +
            //   "; brand: " + data[i]["brand"] +
            //   "; model: " + data[i]["model"] +
            //   "; bodyType: " + data[i]["bodyType"] +
            //   "; generation: " + data[i]["generation"] +
            //   "; trimLevel: " + data[i]["trimLevel"] +
            //   "; engineType: " + data[i]["engineType"] +
            //   "; modification: " + data[i]["modification"]
            // );
          }
        });
    }
  }, []);

return <>
  <h1>{brand} Cars</h1>

  {/* звертаємося до кожного значення кожного ключа */}
  {cars.forEach(function (values, key) {
    console.log(key);
    for (const [index, value] of values.entries()) {
      console.log(`   ${key}[${index}]: ${value.engineType}, ${value.modification}`);
    }})
  }
}
```

</details>

***



## Solutions to the Errors

### [⚠️ ReactJS Error: Objects are not valid as a React child (found: [object Promise])](https://peaku.co/questions/131948-error-de-reactjs:-los-objetos-no-son-validos-como-hijos-de-react-(encontrado:-[promesa-de-objeto]))
Проблема з керуванням даними при роботі функції fetch - Promise

> <details>
> <summary>❌ ПРИКЛАД ПРОБЛЕМИ</summary>
>
> ```jsx
> export default function getWeatherFromApiAsync() {
>   return fetch(
>     "https://api.openweathermap.org/data/2.5/weather?q=brighton,uk&appid=8b609354454cdb6c5a7092a939861ace&units=metric"
>   )
>     .then((response) => response.json())
>     .then((responseJson) => {
>       return (
>         <div className="App">
>           {responseJson.map((weather) => (
>             <div>
>               <h6> {weather.coord.lon}</h6>
>             </div>
>           ))}
>         </div>
>       );
>     })
>     .catch((error) => {
>       console.error(error);
>     });
> }
> ```
> </details>

Притримуйся цих правил:
* You can't render a default component from `Promise` like that.
* You can take an advantages of `useState`, `useEffect` hook to fetch and render data into the component.
* You can learn more about React Hooks from [this link](https://uk.reactjs.org/docs/hooks-intro.html).
* And also your api response is a json object and you're trying to map over it.
* Note: You can map over array type of data only.

> <details>
> <summary>✔️ ВИРІШЕННЯ ПРОБЛЕМИ</summary>
>
> ```jsx
> import { useEffect, useState } from "react";
> 
> const App = () => {
>     const [data, setData] = useState({});
>     useEffect(() => {
>         const getWeatherFromApiAsync = async () => {
>             const resopnse = await fetch(
>                 "https://api.openweathermap.org/data/2.5/weather?q=brighton,uk&appid=8b609354454cdb6c5a7092a939861ace&units=metric"
>             );
>             const resopnseJson = await resopnse.json();
>             console.log("json", resopnseJson);
>             setData(resopnseJson);
>         };
>         getWeatherFromApiAsync();
>     }, []);
> 
>     return (
>         <div className="App">
>             <h6>{data?.coord?.lon}</h6>
>         </div>
>     );
> };
> 
> export default App;
> ```
> </details>


***


