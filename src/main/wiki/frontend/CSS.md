# CSS

> Атрибут html `contenteditable='true'` дозволяє змінювати контент находу.
>
> [Створення контенту для редагування](https://developer.mozilla.org/ru/docs/Web/Guide/HTML/Editable_content)

> Для позиціонування елементів використовують Flexbox або Grid Layout, які вбудовані у CSS.

> Задати висоту контейнера рівню висоти екрана: `min-height: 100vh`.

> Для того, щоб працювали `@media` queries потрібно підключити до html `viewport`:
> ```html
> <meta name="viewport" content="width=device-width,initial-scale=1.0">
> ```

***

ARTICLES:
* [Чи знаєте ви селектори?](https://learn.javascript.ru/css-selectors)

TOOLS:
* [Specificity Calculator](https://specificity.keegan.st/) - показує пріоритети стилів.

EXAMPLES:
* [Responsive web design in 37 minutes + layout. You don’t need Bootstrap!](https://www.youtube.com/watch?v=XbnAKjjlgc4)

***

## Examples
* [GitHub - CSS Demystified - Проста структура статті](https://github.com/SergiaS/c_svg_css/tree/5181369fc157b2e900f454de02cbde60b4574249)


## Properties - Властивості
> Батьківське значення можна перезаписати. Наприклад, селектор `body` має властивість `line-height: 1.6;`,
> але для нащадка це забагато і тут йому можно задати своє значення - просто перевизначити `line-height: 1;`.

> При конфлікті, коли властивість перевизначається в двох класах, буде використовуватися порядок класу у файлі css.
> Порядок у файлі html тут ролі не грає. Але якщо дописати до першого `!important` - запрацює. 

### box-sizing
Якщо контейнер випирає за межі батьківського елемента, тоді ця властивість може допомогти
дотримуватися саме розмірів батьківського елемента:
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




## Bootstrap
* [All Bootstrap CSS classes](https://bootstrapshuffle.com/ru/classes)
* Классы с `p` задают `padding` + есть комбинации: `py` (по оси Y, т.е. вертикаль),
  `pl`, `pr`, `pt`, `pb` (по сторонам: left, right, top, bottom),
