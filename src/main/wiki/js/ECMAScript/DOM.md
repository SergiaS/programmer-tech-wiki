# DOM (Document Object Model)
Каждый элемент в HTML это нода (Node). Например:
```html
<p id="head1">Hello there</p>
```
Это такие же ноды, как в алгоритмах - они имеют вложенность.
Внутри `<p>` может быть лист, у листа - другие листы, заголовки, теги оформления...


# Коротко про Document у прикладах
```js
// Отримуємо елемент або лист елементів:
const box = document.getElementById('box'),
      btns = document.getElementsByTagName('button'),
      circles = document.getElementsByClassName('circle'),
      hearts = document.querySelectorAll('.heart'),
      oneHeart = document.querySelector('.heart'),
      wrapper = document.querySelector('.wrapper');

// задаємо стилі по одинці:
box.style.backgroundColor = 'blue';
box.style.width = '500px';

// задаємо одразу кількох стилів за раз:
box.style.cssText = `background-color: blue; width: 500px`;

// додавання всім елементам колікції стилю через forEach:
hearts.forEach(item => item.style.backgroundColor = 'blue');

// через звичайний цикл:
for (let i = 0; i < hearts.length; i++) {
    hearts[i].style.backgroundColor = 'blue';
}

// створюємо новий елемент для html сторінки:
const newDiv = document.createElement('newDiv');

// наділяємо створений елемент класом:
newDiv.classList.add('black');

wrapper.append(newDiv);   // додає елемент в кінець
wrapper.prepend(newDiv);  // додає елемент на початок

hearts[0].before(newDiv); // додає елемент перед
hearts[0].after(newDiv);  // додає елемент після

circles[0].remove();      // видаляє елемент

hearts[0].replaceWith(circles[0]); // замінює елемент
```


# Детальний опис Document

## `getElementById`
Будет браться только первый элемент.
`id` элемент должен быть всегда уникален.
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
Вернет содержимое селектора с HTML-кодом.
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

## Events (події)

### addEventListener
Івенти це екшени, що відбуваються на веб-сторінці під час дії користувача- клік по кнопці, натискання на клавішу...

Є кілька способів призначити подію `event`:
1. Приклад появи тексту у консолі при натисканні на кнопку та зміна стилю при наведенні миші:
    > Але такий спосіб не дуже гарний та він не дозволяє використовувати декілька функцій!

    ```js
    let heading = document.querySelector(".heading");
    let btn = document.querySelector(".btn");
    
    // 1 - не спрацює
    btn.onclick = () => {
        console.log("1 Click");
    };
    
    // 2 - спрацює тільки цей останній
    btn.onclick = () => {
        console.log("2 Click");
    };
    
    btn.onmouseover = () => {
        heading.style.cssText = "background-color: brown; color: white;"
    }
    ```

***

2. Тут потрібно вказати подію в HTML:
   У цьому прикладі подія по натисканні на кнопці відпрацює метод `clickButton`.
   > Такий спосіб теж не дуже гарний, бо він вшивається у html (hardcode)
    ```html
    <button class="btn" onclick="clickButton()">Click here</button>
    ```
    ```js
    const clickButton = () => {
        console.log("Clicked");
    }
    ```

***

3. Можна вішати прослуховувач події (`eventListener`) за допомогою функції [addEventListener](https://developer.mozilla.org/ru/docs/Web/API/EventTarget/addEventListener).
    Такий спосіб завжди передає першим аргумент об'єкт події (часто має назву `event` або просто `e`) у стрілкову функцію (можна цей аргумент не писати, якщо він не потрібен, але завжди першим JS буде сприймати саме подію/event):
    ```html
    <button class="btn">Click here</button>
    ```
    ```js
    btn.addEventListener('click',() => {
        heading.style.cssText = "background-color: brown; color: white;";
    })
    ```
    
    > При кожній повішеній події автоматично створюється об'єкт події, який зберігає всю інфу - на якому елементі сталося, що було натиснуто і т.д.
    ```js
    btn.addEventListener('click',(event) => {
        console.log(event);
    })
    ```
    Одне з головних полів - `target`:
 
***

Одні з головних властивостей об'єкту event, це:
* `type` - той тип події котра трапилася;
* `target` - найглибший елемент ієрархії на котрому трапилася подія.
* `currentTarget` - той елемент на котрому трапилася подія.
```js
btn.addEventListener('click',(e) => {
    console.log(e.target);
})
```

***

### removeEventListener
Іноді буває потреба у видаленні події - тут потрібно передати функцію другим аргументом.
Тут не потрібно визивати функцію - ставити круглі дужки `()`, - ми повинні просто передати посилання на цю функцію, яка буде виконана після події юзера:
```js
let i = 0;
const deleteElement = (e) => {
    console.log(e.target);
    i++;
    if(i == 1) {
        btn.removeEventListener('click', deleteElement);
    }
};
btn.addEventListener('click', deleteElement);
```
> Але краще до функції `addEventListener` додавати третім аргументом опцію **once**.
```js
overlay.addEventListener('click', deleteElement, {once: true});
```

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


### Snippets
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
* Пример сортировки JSON объекта => [Sort JSON Object Array Based On A Key Attribute](https://www.c-sharpcorner.com/UploadFile/fc34aa/sort-json-object-array-based-on-a-key-attribute-in-javascrip/)
    ```js
    fetch('https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json')
        .then(response => response.json())
        .then(data => {
            ans.sort(sortByProperty("cc"));
            addNewOptions();
        });
    
    function sortByProperty(property) {
        return (a, b) => {
            if (a[property] > b[property]) return 1;
            else if (a[property] < b[property]) return -1;
            return 0;
        }
    }
    
    function addNewOptions() {
        let result = document.querySelector("#listOfCurrencies");
        let newSelect = document.createElement("select");
        for (let i = 0; i < ans.length; i++) {
            let newOption = document.createElement("option");
            newOption.value = ans[i]["txt"];
            newOption.text = ans[i]["cc"];
            newSelect.add(newOption);
        }
        result.appendChild(newSelect);
    }
    ```
* Добавление `event` к `select`:
    ```js
    let newSelect = document.createElement("select");
    newSelect.setAttribute("onchange","getRate()");
    ```
