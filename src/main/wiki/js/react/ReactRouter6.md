# [React Router 6](https://reactrouter.com/docs/en/v6/getting-started/installation)

> **Note**<br>
> Для роботи **React Router** необхідно встановити пакет `react-router-dom`:
> ```npm
> npm install react-router-dom
> ```

* [YouTube відеокурс React Router v6](https://www.youtube.com/playlist?list=PLiZoB8JBsdznY1XwBcBhHL9L7S_shPGVE)

> У версії 6 є суттєві зміни від милої версії.

* Розробка SPA-додатків (Single Page Application).
Технологія не перезавантажує сторінку, а підвантажує потрібні дані.

* Були проекти **React Router** та **Reach Router** - вони злилися, і актуальним є **React Router**.


## [Routes](https://reactrouter.com/docs/en/v6/components/routes) і [Route](https://reactrouter.com/docs/en/v6/components/route)
* [YouTube - Вложенный роутинг](https://www.youtube.com/watch?v=U7c7k-NBtQg&list=PLiZoB8JBsdznY1XwBcBhHL9L7S_shPGVE&index=7) >> [Приклад з відео на GitHub](https://github.com/SergiaS/c_react/commit/c2a95d02809b104f7f2506c26fbedb9d600226e2)

> Вкладенні роути домальовують додаткові дані.

Та частина, котра буде постійно змінюватися, повинна бути обернута у `<Routes />`.
Далі у `<Routes />` є дочірні структури `<Route />`.

У `<Route />` вказується ендпоінт/адреса.
Останньою вказується сторінка на випадок невірної адреси з ендпоінтом `/*`.
```jsx
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";

import { Home } from "./pages/Home";
import { About } from "./pages/About";
import { Contact } from "./pages/Contact";
import { NotFound } from "./pages/NotFound";

export default function App() {
    return (
        <>
            <Header/>
                <main className="container content">
                    <BrowserRouter>
                        <Routes>
                            <Route path='/' element={<Home />} />
                            <Route path='about' element={<About />} />
                            <Route path='contacts' element={<Contact />} />
                            <Route path='/*' element={<NotFound />} />
                        </Routes>
                    </BrowserRouter>
                </main>
            <Footer/>
        </>
    );
}
```

## [Link](https://reactrouter.com/docs/en/v6/components/link) component
Компонент `Link` надає можливість використання можливостей SPA.

## [NavLink](https://reactrouter.com/docs/en/v6/components/nav-link) component
Компонент `NavLink` виділяється тим що, та адреса, яка у даний момент є активною,
автоматично до компонента додається за замовчуванням клас `active`.

Можна задати бажане ім'я класу, але коду буде більше - перевірка `isActive` - треба писати для кожного `NavLink`:
```jsx
// якщо використовувати дефолтний класс active - додатковий код не потрбен: 
<Link to="/">Home</Link>
<Link to="/posts">Blog</Link>
<Link to="/about">About</Link>

// якщо використовувати свої імена класів css - треба додаткова перевірка:
const setActive = ({isActive}) => isActive ? 'active-link' : '';
<NavLink to="/" className={setActive}>Home</NavLink>
<NavLink to="/posts" className={setActive}>Blog</NavLink>
<NavLink to="/about" className={setActive}>About</NavLink>
```

### Кастомізація компонента NavLink

> <details>
> <summary>EXAMPLE</summary>
>
> ```jsx
> // Файл CustomLink.jsx - кастом NavLink'а
> import {Link, useMatch} from "react-router-dom";
> 
> const CustomLink = ({children, to, ...props}) => {
>     const match = useMatch(to);
>     console.log({match});
> 
>     return (
>         <Link
>             to={to}
>             style={{
>                 color: match ? 'var(--color-active)' : 'white',
>             }}
>             {...props}
>         >
>             {children}
>         </Link>
>     )
> }
> 
> export {CustomLink};
> ```
> ```jsx
> // Використання CustomLink
> import {Outlet} from "react-router-dom";
> import {CustomLink} from "./CustomLink";
> 
> const Layout = () => {
>     return (
>         <>
>             <header>
>                 <CustomLink to="/">Home</CustomLink>
>                 <CustomLink to="/posts">Blog</CustomLink>
>                 <CustomLink to="/about">About</CustomLink>
>             </header>
> 
>             <main className="container">
>                 <Outlet/>
>             </main>
> 
>             <footer className="container">2021</footer>
>         </>
>     )
> }
> 
> export {Layout}
> ```
> </details>


***


## Хук [useParams](https://reactrouter.com/docs/en/v6/hooks/use-params) - параметри у посиланнях
* Параметри дістаються за допомогою хука `useParams`.
* Параметр додається до адреси через `:`, наприклад:
    ```jsx
    <Route path="posts/:id" element={<SinglePage/>}/>
    <Route path="posts/:id/:title/:category" element={<SinglePage/>}/>
    <Link to={`/posts/${id}/edit`}>Edit this post</Link>
    ````
* У React Router версії 6 порядок параметрів не важливий.

> <details>
> <summary>ПРИКЛАД ДИНАМІЧНИХ ПАРАМЕТРІВ У ПОСИЛАННЯХ</summary>
>
> Простий приклад логіки роботи динамічних роутів.
>
> ```jsx
> // Файл App.js з усіма роутами
> import {Routes, Route} from "react-router-dom";
> 
> import {Home} from "./pages/Home";
> import {About} from "./pages/About";
> import {Blog} from "./pages/Blog";
> import {CreatePost} from "./pages/CreatePost";
> import {EditPost} from "./pages/EditPost";
> import {SinglePage} from "./pages/SinglePage";
> import {Notfound} from "./pages/Notfound";
> 
> import {Layout} from "./components/Layout";
> 
> export default function App() {
>     return (
>         <>
>             <Routes>
>                 <Route path="/" element={<Layout />}>
>                     <Route index element={<Home/>}/>
>                     <Route path="about" element={<About/>}/>
>                     <Route path="posts" element={<Blog/>}/>
>                     <Route path="posts/:id" element={<SinglePage/>}/>
>                     <Route path="posts/:id/edit" element={<EditPost/>}/>
>                     <Route path="posts/new" element={<CreatePost/>}/>
>                     <Route path="*" element={<Notfound/>}/>
>                 </Route>
>             </Routes>
>         </>
>     );
> }
> ```
> ```jsx
> // Blog.jsx - надає список постів з API, і передає id далі
> import {useEffect, useState} from "react";
> import {Link} from "react-router-dom";
> 
> export const Blog = () => {
>     const [posts, setPosts] = useState([]);
> 
>     useEffect(() => {
>         fetch('https://jsonplaceholder.typicode.com/posts')
>             .then(res => res.json())
>             .then(data => setPosts(data))
>     }, []);
> 
>     return (
>         <div>
>             <h1>Our news</h1>
>             {
>                 posts.map(post => (
>                     <Link key={post.id} to={`/posts/${post.id}`}>
>                         <li>{post.title}</li>
>                     </Link>
>                 ))
>             }
>         </div>
>     )
> }
> ```
> ```jsx
> // SinglePage.jsx - бере дані з API по наданному id
> import {Link, useParams} from "react-router-dom";
> import {useEffect, useState} from "react";
> 
> export const SinglePage = () => {
>     const {id} = useParams();
>     const [post, setPost] = useState();
> 
>     useEffect(() => {
>         fetch(`https://jsonplaceholder.typicode.com/posts/${id}`)
>             .then(res => res.json())
>             .then(data => setPost(data))
>     }, [id]);
> 
>     return (
>         <div>
>             {post && (
>                 <>
>                     <h1>{post.title}</h1>
>                     <h1>{post.body}</h1>
>                     <Link to={`/posts/${id}/edit`}>Edit this post</Link>
>                 </>
>             )}
>         </div>
>     )
> }
> ```
> ```jsx
> // Відпрацює за переходом posts/:id/edit з SinglePage
> import {useParams} from "react-router-dom";
> 
> export const EditPost = () => {
>     const {id} = useParams();
> 
>     return (
>         <div>
>             <h1>Edit post {id}</h1>
>         </div>
>     )
> }
> ```

</details>


## Хук [useNavigate](https://reactrouter.com/docs/en/v6/hooks/use-navigate) - керування історією браузера
* Прийшов на заміну хуку useHistory.
* Повертає функцію.
  Працює з двома параметрами - перший вказує куди переадресувати, другий - опції.

> <details>
> <summary>ПРИКЛАД СТВОРЕННЯ КНОПКИ Go Back</summary>
> 
> ```jsx
> import {useNavigate} from "react-router-dom";
> 
> export const SomePage = () => {
>     const navigate = useNavigate();
>     const goBack = () => navigate(-1);
> 
>     return (
>         <div>
>             <button onClick={goBack}>Go back</button>
>         </div>
>     )
> }
> ```
> 
> </details>

> <details>
> <summary>ПРИКЛАД СТВОРЕННЯ РЕДІРЕКТУ</summary>
> 
> Декілька різних адрес посилаються на одну сторінку без запису до історії браузера.
> 
> ```jsx
> <Route path="about" element={<About/>}/>
> <Route path="about-us" element={<Navigate to="/about" replace />}/>
> ```
> `replace` - без запису до історії браузера.
> 
> Тут потрібно використовувати `/`.
> 
> </details>



## Хук [useLocation](https://reactrouter.com/docs/en/v6/hooks/use-location)
* [YouTube - приклад простої авторизації](https://youtu.be/jv0ckzkKYzU?t=1535) >> [Приклад з відео на GitHub](https://github.com/SergiaS/c_react/commit/c0d3841fbe765da5fd9edc2e4c45ddb53aec87f5)

**Location** допомагає з роботою приватних роутів (компонент вищого порядку (HOC)).

*** 
```jsx
location.state?.from?.pathname || '/';
```
тут ми дістаємо з `location` стан, перевіряємо чи є ключ `from` і перевіряємо чи є у ключа `from` ключ `pathname`.



## Хук [useSearchParams](https://reactrouter.com/docs/en/v6/hooks/use-search-params) - робота з параметрами запиту
* [YouTube - приклад створення пошуку](https://www.youtube.com/watch?v=C-AFpwNrPRU&list=PLiZoB8JBsdznY1XwBcBhHL9L7S_shPGVE&index=6) >> [Приклад з відео на GitHub](https://github.com/SergiaS/c_react/commit/2f108c1dbd3a2669ec05daab24ca2f61fe95e901)





## Приклади React Router

> <details>
> <summary>ПРИКЛАД РОУТІВ</summary>
>
> З версії 6 компонента `Switch` **вже немає**, на його заміну прийшов `Routes`, який працює дещо інакше.
>
> ```jsx
> import {Routes, Route, Link} from "react-router-dom";
> import {Home} from "./pages/Home";
> import {About} from "./pages/About";
> import {Blog} from "./pages/Blog";
> import {Notfound} from "./pages/Notfound";
> 
> function App() {
>     return (
>         <>
>             <Routes>
>                 <Route path="/" element={<Home/>}/>
>                 <Route path="/about" element={<About/>}/>
>                 <Route path="/posts" element={<Blog/>}/>
>                 <Route path="/*" element={<Notfound/>}/>
>             </Routes>
>         </>
>     );
> }
> 
> export default App;
> ```
> </details>


> <details>
> <summary>ПРИКЛАД КОМПОНЕНТА Outlet</summary>
>
> ```jsx
> // App.js
> import {Routes, Route} from "react-router-dom";
> 
> import {Home} from "./pages/Home";
> import {About} from "./pages/About";
> import {Blog} from "./pages/Blog";
> import {Notfound} from "./pages/Notfound";
> 
> import {Layout} from "./components/Layout";
> 
> function App() {
>     return (
>         <>
>             <Routes>
>                 <Route path="/" element={<Layout />}>
>                     <Route index element={<Home/>}/>
>                     <Route path="about" element={<About/>}/>
>                     <Route path="posts" element={<Blog/>}/>
>                     <Route path="*" element={<Notfound/>}/>
>                 </Route>
>             </Routes>
>         </>
>     );
> }
> 
> export default App;
> ```
> ```jsx
> // Layout.jsx
> import {Link, Outlet} from "react-router-dom";
> 
> const Layout = () => {
>     return (
>         <>
>             <header>
>                 <Link to="/">Home</Link>
>                 <Link to="/posts">Blog</Link>
>                 <Link to="/about">About</Link>
>             </header>
> 
>             <main className="container">
>                 <Outlet/>
>             </main>
> 
>             <footer className="container">2021</footer>
>         </>
>     )
> }
> 
> export {Layout}
> ```
> </details>

***


