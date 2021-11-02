# DOM (Document Object Model)
Каждый элемент в HTML это нода (Node). Например:
```html
<p id="head1">Hello there</p>
```
Это такие же ноды, как в алгоритмах - они имеют вложенность.
Внутри `<p>` может быть лист, у листа - другие листы, заголовки, теги оформления...

<hr>

## `getElementById`
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

## `textContent`
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

## `innerHTML`
Позволяет получать текст с HTML элемента.
Также может динамически задавать своё значение.

<hr>

## Разница между `innerText` и `textContent`
[StackOverflow](https://stackoverflow.com/a/35213639).
`innerText` вернет видимый текст содержащийся в ноде, а `textContent` - весь.
Здесь используется стиль `style="display: none;"`.

<hr>

## `querySelector`
Достает только на первый попавшийся селектор.
Вернет содержимое селестора с HTML-кодом.
Если ничего не найдено - возвращает `null`.
```js
let list = document.querySelector("ul");
console.log(list);
```

<hr>

## `querySelectorAll`
Возвращает NodeList - массив элементов с указанным селектором:
```js
let lis = document.querySelectorAll("li");
```

<hr>

## `style`
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
    heading.className += " changeFT";
    // <h1 id="heading" class="changeBG changeFT">Hi there</h1>
    ```

<hr>

## `classList`
Свойство [classList](https://developer.mozilla.org/ru/docs/Web/API/Element/classList) возвращает псевдомассив `DOMTokenList`, содержащий все классы элемента.

Имеет методы управления стилями `add`, `delete` и ещё пару других.
```js
var elementClasses = elem.classList;
```

<hr>

## Events (события)
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

## Attributes (аттрибуты полей)
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

## DOM navigation
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
