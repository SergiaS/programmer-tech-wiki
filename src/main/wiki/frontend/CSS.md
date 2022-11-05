# CSS

> Атрибут html `contenteditable='true'` дозволяє змінювати контент находу.
>
> [Створення контенту для редагування](https://developer.mozilla.org/ru/docs/Web/Guide/HTML/Editable_content)

> Для позиціювання елементів використовують **Flexbox** або **Grid Layout** (більш функціональна технологія, ніж інші), які вбудовані у CSS.

> Задати висоту контейнера рівню висоти екрана: 
> ```css
> min-height: 100vh;
> ```

> Для того, щоб працювали `@media` queries потрібно підключити до html `viewport`:
> ```html
> <meta name="viewport" content="width=device-width,initial-scale=1.0">
> ```

> Щоб текст не переносився на нову строку коли є пробіли: 
> ```css
> white-space: nowrap;
> ```

> Щоб текст був великими літерами як нормальний: 
> ```css
> font-variant: small-caps;
> ```

> Щоб блоки `div` самі вирівнювалися по центру: 
> ```css
> margin: 0 auto;
> ```

***


## Properties - Властивості
> Батьківське значення можна перезаписати. Наприклад, селектор `body` має властивість `line-height: 1.6;`,
> але для нащадка це забагато і тут йому можно задати своє значення - просто перевизначити `line-height: 1;`.

> При конфлікті, коли властивість перевизначається в двох класах, буде використовуватися порядок класу у файлі css.
> Порядок у файлі html тут ролі не грає. Але якщо дописати до першого `!important` - запрацює. 

### [box-sizing](https://developer.mozilla.org/ru/docs/Web/CSS/box-sizing)
Якщо контейнер випирає за межі батьківського елемента, тоді ця властивість може допомогти
дотримуватися саме розмірів батьківського елемента включно з `padding`:
```css
body {
  box-sizing: border-box;
  width: 100%; /* розтягне елемент на 100% по батьківському розміру */
}
```
***

### height
Якщо потрібно встановлювати висоту блоку, але у майбутньому буде додаватися контент,
тоді краще використовувати `min-height` замість `height`.

***

### display
Відображає блоки за якимось шаблоном, наприклад значення `flex`, відобразить всіх спадкоємців у стовбчик.

***

### line-height
Задає висоту між строками:
```css
body {
  line-height: 1.6;
}
```


## Особливості

***

### `select` and `option`
* [StackOverflow - Custom option example 1](https://stackoverflow.com/a/13968900)
* [StackOverflow - Custom option example 2](https://stackoverflow.com/a/24671889)
* [CodePen - Custom select and option with CSS and JS](https://codepen.io/webDsign/pen/yLgVJqX)

> **Note**<br>
> У HTML і CSS немає можливості змінити колір (тексту і фону) при наведенні `option` - завжди буде синій фон і білий текст!

> **Note**<br>
> У `select` з'являється стрічка прокрутки (scroll bar) у випадаючому списку (dropdown list) 
> тільки якщо кіл-ть елементів більша за 20! 
> Змінити кількість елементів у випадаючому списку засобами HTML або CSS неможливо!

Тег `option`, який вкладається у `select`, стилізувати неможливо (окрім `background-color` і `color`), оскільки рендерінг виконується
операційною системою користувача, і не є html елементом. Тому, якщо є потреба прибрати межі (`border`, `outline`) -
цього зробити не вдасться. Можна спробувати написати код списком (`ul`, `li`...) або через `input` з `label`. [Пояснення](https://stackoverflow.com/a/19217244)

> <details>
> <summary>Приклад кастомного select'а на основі CSS</summary>
> 
> При використанні `input` потрібно додати `label` - дуже важливо щоб `input` йшов першим оскільки це впливає на стилі,
> і поведінку в цілому:
> ```html
> <div class="test select">
>   <input class="selectopt" name="test" type="radio" id="car1" value="1">
>   <label for="car1" class="option">Audi A4</label>
>   <input class="selectopt" name="test" type="radio" id="car2">
>   <label for="car2" class="option">BMW 3-Series</label>
> </div>
> ```
> ```css
> body {
>   font: 14px "Verdana", sans-serif;
>   margin: 0;
>   padding: 0;
>   background-color: #202c37;
>   color: white;
>   text-shadow: 1px 1px #000000;
> }
> 
> 
> .select {
>   display: flex;
>   flex-direction: column;
>   position: relative;
>   width: 200px;
>   height: 30px;
>   background-color: #FDB94C;
> }
> 
> .option {
>   padding: 0 30px 0 10px;
>   min-height: 30px;
>   display: flex;
>   align-items: center;
>   background: #3b4d5d;
>   border-top: #a1becd solid 1px;
>   position: absolute;
>   top: 0;
>   width: 100%;
>   pointer-events: none;
>   order: 2;
>   z-index: 1;
>   transition: background .1s ease-in-out;
>   box-sizing: border-box;
>   overflow: hidden;
>   white-space: nowrap;
> }
> 
> .option:hover {
>   background: #73919f;
> }
> 
> .select:focus .option {
>   position: relative;
>   pointer-events: all;
> }
> 
> input {
>   opacity: 0;
>   position: absolute;
>   left: -99999px;
> }
> 
> input:checked + label {
>   order: 1;
>   z-index: 2;
>   background: #506d7c;
>   border-top: none;
>   position: relative;
> }
> 
> input:checked + label:after {
>   content: '';
>   width: 0;
>   height: 0;
>   border-left: 5px solid transparent;
>   border-right: 5px solid transparent;
>   border-top: 5px solid white;
>   position: absolute;
>   right: 10px;
>   top: calc(50% - 2.5px);
>   pointer-events: none;
>   z-index: 3;
> }
> 
> input:checked + label:before {
>   position: absolute;
>   right: 0;
>   height: 40px;
>   width: 30px;
>   content: '';
>   background: #3b4d5d;
> }
> ```
>
></details>

> <details>
> <summary>Приклад select'а з задіяними стилями CSS</summary>
>
> ```css
> select {
>   height: 2em;
>   border-radius: 0;
>   background: #3B4D5DFF;
>   color: #fff;
> }
> select:hover {
>   background-color: #7a9bb2;
> }
> select:focus {
>   border: 1px solid black;
>   outline: none;
> }
> 
> option {
>   color: #e5e5e5;
> }
> option:checked {
>   color: #fff;
>   background-color: #7a9bb2;
> }
> option:not(:checked) {
>   background-color: #3B4D5DFF;
> }
> 
> /* scrollbar width */
> select::-webkit-scrollbar {
>   width: 10px;
> }
> /* scrollbar track */
> select::-webkit-scrollbar-track {
>   background: #202c37;
> }
> /* scrollbar handle */
> select::-webkit-scrollbar-thumb {
>   background: #e5e5e5;
> }
> ```
> ```html
> <!-- list taken from mobile.de -->
> <label for="select-make">Make</label>
> <select id="select-make" data-selected="7700">
>   <option value="">Any</option>
>   <option value="140">Abarth</option>
>   <option value="203">AC</option>
>   <option value="375">Acura</option>
>   <option value="31930">Aiways</option>
>   <option value="800">Aixam</option>
>   <option value="900">Alfa Romeo</option>
>   <option value="1100">ALPINA</option>
>   <option value="121">Artega</option>
>   <option value="1750">Asia Motors</option>
>   <option value="1700">Aston Martin</option>
>   <option value="1900">Audi</option>
>   <option value="2000">Austin</option>
>   <option value="1950">Austin Healey</option>
>   <option value="31863">BAIC</option>
>   <option value="3100">Bentley</option>
>   <option value="3500">BMW</option>
>   <option value="3850">Borgward</option>
>   <option value="4025">Brilliance</option>
>   <option value="4350">Bugatti</option>
>   <option value="4400">Buick</option>
>   <option value="4700">Cadillac</option>
>   <option value="112">Casalini</option>
>   <option value="5300">Caterham</option>
>   <option value="83">Chatenet</option>
>   <option value="5600">Chevrolet</option>
>   <option value="5700">Chrysler</option>
>   <option value="5900">Citroën</option>
>   <option value="6200">Cobra</option>
>   <option value="6325">Corvette</option>
>   <option value="3">Cupra</option>
>   <option value="6600">Dacia</option>
>   <option value="6800">Daewoo</option>
>   <option value="7000">Daihatsu</option>
>   <option value="7400">DeTomaso</option>
>   <option value="31864">DFSK</option>
>   <option value="7700">Dodge</option>
>   <option value="255">Donkervoort</option>
>   <option value="235">DS Automobiles</option>
>   <option value="31931">e.GO</option>
>   <option value="31932">Elaris</option>
>   <option value="8600">Ferrari</option>
>   <option value="8800">Fiat</option>
>   <option value="172">Fisker</option>
>   <option value="9000">Ford</option>
>   <option value="205">GAC Gonow</option>
>   <option value="204">Gemballa</option>
>   <option value="270">Genesis</option>
>   <option value="9900">GMC</option>
>   <option value="122">Grecav</option>
>   <option value="186">Hamann</option>
>   <option value="10850">Holden</option>
>   <option value="11000">Honda</option>
>   <option value="11050">Hummer</option>
>   <option value="11600">Hyundai</option>
>   <option value="11650">Infiniti</option>
>   <option value="11900">Isuzu</option>
>   <option value="12100">Iveco</option>
>   <option value="12400">Jaguar</option>
>   <option value="12600">Jeep</option>
>   <option value="13200">Kia</option>
>   <option value="13450">Koenigsegg</option>
>   <option value="13900">KTM</option>
>   <option value="14400">Lada</option>
>   <option value="14600">Lamborghini</option>
>   <option value="14700">Lancia</option>
>   <option value="14800">Land Rover</option>
>   <option value="14845">Landwind</option>
>   <option value="31933">LEVC</option>
>   <option value="15200">Lexus</option>
>   <option value="15400">Ligier</option>
>   <option value="15500">Lincoln</option>
>   <option value="15900">Lotus</option>
>   <option value="31934">Lynk&amp;Co</option>
>   <option value="16200">Mahindra</option>
>   <option value="16600">Maserati</option>
>   <option value="16700">Maybach</option>
>   <option value="16800">Mazda</option>
>   <option value="137">McLaren</option>
>   <option value="17200">Mercedes-Benz</option>
>   <option value="17300">MG</option>
>   <option value="30011">Microcar</option>
>   <option value="17500">MINI</option>
>   <option value="17700">Mitsubishi</option>
>   <option value="17900">Morgan</option>
>   <option value="18700">Nissan</option>
>   <option value="18875">NSU</option>
>   <option value="18975">Oldsmobile</option>
>   <option value="19000">Opel</option>
>   <option value="149">Pagani</option>
>   <option value="19300">Peugeot</option>
>   <option value="19600">Piaggio</option>
>   <option value="19800">Plymouth</option>
>   <option value="4">Polestar</option>
>   <option value="20000">Pontiac</option>
>   <option value="20100">Porsche</option>
>   <option value="20200">Proton</option>
>   <option value="20700">Renault</option>
>   <option value="21600">Rolls-Royce</option>
>   <option value="21700">Rover</option>
>   <option value="125">Ruf</option>
>   <option value="21800">Saab</option>
>   <option value="22000">Santana</option>
>   <option value="22500">Seat</option>
>   <option value="22900">Skoda</option>
>   <option value="23000">Smart</option>
>   <option value="188">speedART</option>
>   <option value="100">Spyker</option>
>   <option value="23100">Ssangyong</option>
>   <option value="23500">Subaru</option>
>   <option value="23600">Suzuki</option>
>   <option value="23800">Talbot</option>
>   <option value="23825">Tata</option>
>   <option value="189">TECHART</option>
>   <option value="135">Tesla</option>
>   <option value="24100">Toyota</option>
>   <option value="24200">Trabant</option>
>   <option value="24400">Triumph</option>
>   <option value="24500">TVR</option>
>   <option value="25200">Volkswagen</option>
>   <option value="25100">Volvo</option>
>   <option value="25300">Wartburg</option>
>   <option value="113">Westfield</option>
>   <option value="25650">Wiesmann</option>
>   <option value="1400">Other</option>
> </select>
> ```
>
></details>


***

Щоб прибрати закруглення у `select` треба примусово додати `border-radius: 0`, а щоб прибрати виділення після вибіру опції (також закруглене) треба додати до псевдо-класу `:focus` команду `outline: none`:
```css
select {
  border-radius: 0;
}
select:focus {
  outline: none;
}
```

***

### [data-атрибуты](https://developer.mozilla.org/ru/docs/Learn/HTML/Howto/Use_data_attributes)
У HTML/CSS есть возможность работать по условию - в зависимости что пришло выбрать стиль - используя атрибут `data`:
```css
tr[data-mealExcess="false"] {
    color: green;
}

tr[data-mealExcess="true"] {
    color: red;
}
```
```html
<!-- на вход по JSP приходит значение true или false --> 
<tr data-mealExcess="${meal.excess}">...</tr>
```

***

Читати стилями те що вказано в `data`:
```html
<div data-color="red">Some Words</div>
```
```css
div {
  color: attr(data-color color);
}
```
або
```html
<div class="trans" data-trans="Trans ">Fat</div>
```
```css
.trans:before {
    content: attr(data-trans);
    font-style: italic;
}
```
Результат: '<i>Trans</i> Fat'


***

### Різна розмальовка рядків
У таблиці для розмальовки рядків у різний колір (парний / не парний) використовується тег `:nth-of-type()`:
```css
tr:nth-of-type(even) {background: blue;}
tr:nth-of-type(odd) {background: red;}
```


## Flexbox
* [FLEXBOX FROGGY - visual game](https://flexboxfroggy.com/)

> При роботі з Flexbox використовуй `flex-basic` замість `width`.

***

### Властивості контейнера та елемента:

| Container properties (default)   | Item properties (default) |
|:-------                          |:--------                  |
| `flex-direction` : `row`         | `align-self` : `stretch`  |
| `flex-wrap` : `nowrap`           | `flex-grow` : `0`         |
| `flex-flow` : `nowrap`           | `flex-shrink` : `1`       |
| `gap` : `normal normal`          | `flex-basic` : `auto`     |
| `justify-content` : `flex-start` | `flex` : `0 1 auto`       |
| `align-items` : `stretch`        | `order` : `0`             |
| `align-content` : `stretch`      |                           |

* `flex-direction` - задає напрямок головної (main) вісі.

* `flex-wrap` - дозволяє переніс елементів на новий рядок, якщо у теперішньому не достатньо місця.

* `flex-flow` - з'єднувальна властивість для `flex-direction` і `flex-wrap`.
  ```css
  /* flex-flow: <'flex-direction'> */
  flex-flow: row;
  
  /* flex-flow: <'flex-wrap'> */
  flex-flow: wrap;
  
  /* flex-flow: <'flex-direction'> and <'flex-wrap'> */
  flex-flow: column-reverse wrap-reverse;
  ```

* `gap` - задає порожній простір між елементами.
  Задати можна як одразу для всіх сторін, так і для горизонталі та вертикалі.

* `flex-grow` - визначає можливість збільшення елемента в разі необхідності.
  Якщо для всіх елементів встановлене однакове значення, наприклад `1`, - тоді простір що залишився,
  рівномірно розподілиться поміж усіма дочірніми елементами.
  Якщо у одного елемента значення більше, тоді йому дістанеться більше вільного простору.

* `flex-shrink` - з'ясовує скільки вільного простору об'єкт може віддати в разі необхідності.
  Чим більше значення, тим більше простору віддає.

* `flex-basic` - з'ясовує розмір елементу до розподілення вільного простору.
  Відрізняється тим, що він вираховує розміри по вісі.
  Тобто, при горизонтальному розміщенні вісі, дана властивість встановлює ширину, а при вертикальному - висоту.

* `flex` - це коротший запис одразу трьох властивостей - `flex-grow`, `flex-shrink` і `flex-basic`:
  ```css
  .flex-item {
      flex: 1 0 300px;
  }
  ```

* `order` - задає порядок відображення елементу. Чим більше значення, тим останнім буде елемент.

***

#### Властивості для вирівнювання контейнера та елемента:

| Container alignment (default)    | Item alignment (default) |
|:-------                          |:--------                 |
| `justify-content` : `flex-start` | `align-self` : `stretch` |
| `align-items` : `stretch`        |                          |
| `align-content` : `stretch`      |                          |

* `justify-content` - вирівнює елементи по горизонталі (MAIN вісь).

* `align-items` - вирівнює елементи по вертикалі (CROSS вісь).

* `align-content` - призначений для вирівнювання багато строкових по CROSS вісі.
  Розподіляє елементи аналогічно `justify-content` тільки вздовж CROSS вісі.
  Тут повинен бути контейнер висотою більше, а ніж сумма його елементів - використовуй `width` або `min-height: 100vh`.

* `align-self` - задає вирівнювання конкретного елемента по CROSS вісі в середині контейнера.
  Працює так само як і `align-items`, але його використовують для конкретних об'єктів.
  * `stretch` - растягує елемент по всій висоті контейнера.
  * `flex-start` - висота підстроюється під розмір контенту елементу і розташовується зверху контейнера - зверху осі.
  * `flex-end` - висота підстроюється під розмір контенту елементу і розташовується знизу контейнера - знизу осі.
  * `center` - центруємо елемент вертикально.
  * `baseline` - показує аналогічний результат `flex-start`, тільки вирівнюється відносно строки.
    Більш конкретну різницю можна побачити при більшій висоті контейнера.
  
## Grid system
* [GRID GARDEN GAME](https://cssgridgarden.com/)
* [YouTube - CSS Grid верстка](https://www.youtube.com/playlist?list=PLiZoB8JBsdzk7yebGLJSgZiGXty6YDPBD)

> Елемент **Grid system** примусово отримує `display: block`.

> Можна вкладати **Grid** у **Grid**, або у **Flex**.

> Задати фракцію по вертикалі можна тільки тоді, коли у grid-контейнера є властивість `height`, або є текст всередині блока.

> При встановленні розмірів у відсотках, **grid** не зважає на батьківські налаштування `padding`:
> ```css
> /* не бачить padding */
> grid-template-columns: 50% 50%;
> 
> /* краще використовувати фракції */
> grid-template-columns: 1fr 1fr;
> ```

### Відступи Gap
Рівносильні значення:
```css
gap: 10px 20px;

column-gap: 20px;
row-gap: 10px;
```

### Фракції Fractions
Рівносильні значення:
```css
grid-template-columns: repeat(3, 300px);
grid-template-columns: 300px 300px 300px;
```

Для не фіксованих розмірів (резинові) використовуй фракції (працює як співвідношення, якщо фракцій декілька):
```css
/* Приклад резинової середньої колонки: */
grid-template-columns: 300px 1fr 300px;
```
```css
/* Приклад співвідношення фракцій (66% перша, 33% друга): */
grid-template-columns: 2fr 1fr;
```

За розміром вміста:
```css
grid-template-rows: repeat(4, auto);
```

### Область
Для кожної області задається ім'я, де далі вони використовуються у `grid-template-areas`:
```css
.container {
    display: grid;
    gap: 10px 20px;

    grid-template-columns: repeat(3, 1fr);
    grid-template-areas:
            'myHeader     myHeader      myHeader'
            'myArticle    myArticle     myAside'
            'myFooter     myFooter      myFooter';
}

header {
    grid-area: myHeader;
    background-color: darkgreen;
}
article {
    grid-area: myArticle;
    background-color: black;
}
aside {
    grid-area: myAside;
    background-color: maroon;
}
footer {
    grid-area: myFooter;
    background-color: darkblue;
}
```
Щоб отримати пусту область, використовують крапку `.` (або декілька `...`) замість імені:
```css
grid-template-areas:
        'myHeader     myHeader      myHeader'
        'myArticle    ...           myAside'
        'myFooter     myFooter      myFooter';
```
Подібну структуру сітки можна отримати так:
```css
grid-template-columns: 1fr  50px 1fr;
```
Спрощений запис задають через `grid-template`:
```css
grid-template:
        'myHeader     myHeader      myHeader'	50px
        'myArticle    ...           myAside'	auto
        'myFooter     myFooter      myFooter'	50px / 1fr 50px 1fr;
```
```css
grid-template: auto / 1fr 50px 1fr;
```
...де вказано, що висота автоматична, і потім йдуть розміри колонок (1 фракція, 50 пікселів, 1 фракція).

### Вирівнювання
* `justify-item` - вирівнювання по горизонталі:
  - `stretch` - значення за замовчуванням, розтягує на усю ширину;
  - `start`, `center`, `end` - встановлює ширину колонки по контенту в залежності від сторони вирівнювання.

* `align-items` - робить все те саме що і `justify-item` тільки з вирівнювання по вертикалі.

* `place-items` - це спрощена форма запису `justify-item` і `align-items`:
  ```css
  justify-item: center;
  align-items: left;
  
  /* спрощений запис */
  place-items: left center;
  /* якщо значення однакові */
  place-items: center;
  ```
  ...перший записується `align-items` потім `justify-item`.


### Методи

#### minmax
`minmax(100px, 500px)` - в залежності від умов, може бути або 100px, або 500px.

Приклад, в якому блок буде підлаштовуватися під розміри контенту 100px або автоматично.
```css
grid-auto-rows: minmax(100px, auto);
```

### Приклади
```css
.container {
    /* створює 5 колонок по 1 фракції */
    grid-template-columns: repeat(5, 1fr);
}

.item:nth-child(3) {
    /* встановлює порядок */
    order:-1;
    /* займай рівно 3 ячейки */
    grid-column: 1 / span 3;
}
```
```css
/* займай рівно 3 ячейки - без вирахування */
grid-column: 1 / span 3;
/* те саме тільки - з вирахуванням */
grid-column: 1 / 4;
```



***

## [Sass/SCSS](https://sass-lang.com/guide)
* [Керівництво по SASS. Як верстати сайти вдвічі швидше?](https://tokar.ua/read/6672)

Sass (Syntactically Awesome Stylesheets) — це скриптова метамова, яка компілюється в звичайні CSS-стилі.

> Увесь код **Sass/SCSS** компілюється у **CSS**, щоб браузери змогли його розуміти та коректно відображати.
> 
> Тому не можна просто скопіювати код та вставити його у свій `.scss` - він працювати не буде!

Розширення SASS-файлів можуть бути .sass і .scss — це залежить від обраного синтаксису. 
Браузер, втім, не розуміє жодного з них, тому для взаєморозуміння потрібно використовувати компілятор. 
Його завдання — привести SASS в зрозумілий класичний CSS, який буде розпізнано будь-яким браузером.

**Відмінності між SASS і новішим SCSS:**

* У SASS-синтаксисі немає фігурних дужок, вкладеність елементів в ньому реалізована за допомогою відступів, 
  а стильові правила обов'язково відокремлені новими рядками.
* Незалежно від синтаксису, SCSS назад сумісний з CSS. Тобто будь-який CSS обов'язково буде дійсним SCSS-кодом.
* Через відсутність дужок і крапок з комою зворотної сумісності у SASS-синтаксису з CSS немає.

### Компіляція
Оскільки браузери не розуміють SASS/SCSS, компілюються дані файли локально і переносяться на сервер.
Треба використовувати сервіси для компіляції - гугли.


### Особливості
* Змінні (variables) - починається зі знака долара ($), значення присвоюються за допомогою двокрапки.
  Змінні в Sass можна розділити на 4 типи:
  * число (int);
  * строка (string);
  * логічний тип (так/ні, boolean);
  * кольори (ім’я, імена).
  ```scss
  // SCSS-синтаксис
  $blue: #3bbfce; /* колір */
  $margin: 16px; /* відступ */
  $fontSize: 14px; /* розмір тексту */
  
  .content {
      border: 1px solid $blue; /* синій бордюр */
      color: darken($blue, 20%); /* затемнення кольору на 20% */
  }
  
  .border {
      padding: $margin / 2;
      margin: $margin / 2;
      border-color: $blue;
  }
  ```
  ```sass
  // SASS-синтаксис
  $blue: #3bbfce
  $margin: 16px
  $fontSize: 14px
  
  .content
      border: 1px solid $blue
      color: darken($blue, 20%)
  
  .border
      padding: $margin / 2
      margin: $margin / 2
      border-color: $blue
  ```
  ```css
  /* Результат в CSS */
  .content {
      border: 1px solid #3bbfce;
      color: #217882; 
  }
  
  .border {
      padding: 8px;
      margin: 8px;
      border-color: #3bbfce;
  }
  ```

* Вкладені правила (nesting).
  ```scss
  // scss
  nav {
      ul {
          margin: 0;
          padding: 0;
          list-style: none;
      }
      li { display: inline-block; }
      a {
          display: block;
          padding: 6px 12px;
          text-decoration: none;
      }
  }
  ```
  ```css
  /*css*/
  nav ul {
      margin: 0;
      padding: 0;
      list-style: none;
  }

  nav li {
      display: inline-block;
  }
  
  nav a {
      display: block;
      padding: 6px 12px;
      text-decoration: none;
  }
  ```

* Доповнення (mixin).
  Правило DRY (Do not Repeat Yourself) реалізовано в Sass за допомогою техніки mixin. 
  Ті шматки коду, які в CSS зазвичай вам доводилося дублювати, тут можна зберегти в окремій змінної і вставляти в потрібних місцях. 
  Компілятор, зустрівши таку змінну, збереже замість неї потрібний шматок коду.
  ```scss
  // scss
  @mixin table-base {
      th {
          text-align: center;
          font-weight: bold;
      }
      td, th {
          padding: 2px
      }
  }
  
  #data {
      @include table-base;
  }
  ```
  ```css
  /*css*/
  #data th {
      text-align: center;
      font-weight: bold;
  }
  #data td, #data th {
      padding: 2px;
  }
  ```

* Аргументи (arguments).
  Доповнення вміють змінювати код в залежності від переданих їм аргументів.
  ```scss
  // scss
  @mixin border-radius($radius) {
      -webkit-border-radius: $radius;
      moz-border-radius: $radius;
      -ms-border-radius: $radius;
      border-radius: $radius;
  }

  .box-1 {
      @include border-radius(10px);
  }

  .box-2 {
      @include border-radius(5px);
  }
  ```
  ```css
  /*css*/
  .box-1 {
      -webkit-border-radius: 10px;
      -moz-border-radius: 10px;
      -ms-border-radius: 10px;
      border-radius: 10px;
  }
  
  .box-2 {
      -webkit-border-radius: 5px;
      -moz-border-radius: 5px;
      -ms-border-radius: 5px;
      border-radius: 5px;
  }
  ```
  
* Наслідування (extend).
  Створивши одного разу будь-яке правило, ми можемо використовувати його всередині іншого. 
  Наслідуваний елемент отримає всі властивості вихідного класу, які ми можемо доповнити будь-якими іншими.
  ```scss
  // scss
  .error {
      border: 1px #f00;
      background: #fdd;
  }
  .error.intrusion {
      font-size: 1.3em;
      font-weight: bold;
  }

  .badError {
      @extend .error;
      border-width: 3px;
  }
  ```
  ```css
  /*css*/
  .error, .badError {
      border: 1px #f00;
      background: #fdd;
  }
  
  .error.intrusion,
  .badError.intrusion {
      font-size: 1.3em;
      font-weight: bold;
  }
  
  .badError {
      border-width: 3px;
  }
  ```



## Examples
* [GitHub - CSS Demystified - Проста структура статті](https://github.com/SergiaS/c_svg_css/tree/5181369fc157b2e900f454de02cbde60b4574249)

> <details>
> <summary>ПРИКЛАД створення темізації, використання змінних</summary>
> 
> ```css
> @import url('https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;600;800&display=swap');
> 
> /*  for all elements */
> * {
>     box-sizing: border-box;
> }
> 
> :root {
>     /* Typography */
>     --family: 'Nunito Sans', sans-serif;
>     --fs-sm: 14px;
>     --fs-md: 16px;
>     --fw-light: 300;
>     --fw-normal: 600;
>     --fw-bold: 800;
> 
>     /* Other */
>     --radii: 0.5rem;
> }
> 
> body {
>     margin: 0;
> }
> 
> /* Темізація через data-атрибути */
> body[data-theme='dark'] {
>     --colors-text: hsl(0, 0%, 100%);
>     --colors-bg: hsl(207, 26%, 17%);
>     --colors-ui-base: hsl(209, 23%, 22%);
>     --shadow: rgba(245, 245, 245, 0.2) 0 0 8px;
> }
> body[data-theme='light'] {
>     --colors-text: hsl(200, 15%, 8%);
>     --colors-bg: hsl(0, 0%, 98%);
>     --colors-ui-base: hsl(0, 0%, 100%);
>     --shadow: rgba(149, 157, 165, 0.2) 0 8px 24px;
> }
> 
> body {
>     margin: 0;
>     font-family: var(--family);
>     color: var(--colors-text);
>     font-weight: var(--fw-light);
>     background-color: var(--colors-bg);
> }
> ```
> 
> </details>

## Bootstrap
* [All Bootstrap CSS classes](https://bootstrapshuffle.com/ru/classes)
* Классы с `p` задают `padding` + есть комбинации: `py` (по оси Y, т.е. вертикаль),
  `pl`, `pr`, `pt`, `pb` (по сторонам: left, right, top, bottom),



# Additional info

***

ARTICLES:
* [Чи знаєте ви селектори?](https://learn.javascript.ru/css-selectors)

TOOLS:
* [Specificity Calculator](https://specificity.keegan.st/) - показує пріоритети стилів.

EXAMPLES:
* [Responsive web design in 37 minutes + layout. You don’t need Bootstrap!](https://www.youtube.com/watch?v=XbnAKjjlgc4)
* [Styled "select" options using CSS3 and Flexbox](https://codepen.io/cssinate/pen/KVdYjz?editors=1100)

