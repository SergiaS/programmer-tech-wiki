# CSS
TOOLS:
* [Specificity Calculator](https://specificity.keegan.st/) - показує пріоритети стилів.

EXAMPLES:
* [Responsive web design in 37 minutes + layout. You don’t need Bootstrap!](https://www.youtube.com/watch?v=XbnAKjjlgc4)

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

### data-атрибуты
[Использование data-* атрибутов](https://developer.mozilla.org/ru/docs/Learn/HTML/Howto/Use_data_attributes)
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

### Різна розмальовка рядків
У таблиці для розмальовки рядків у різний колір (парний / не парний) використовується тег `:nth-of-type()`:
```css
tr:nth-of-type(even) {
   background: #ffd98b;
}
```



## Bootstrap
* [All Bootstrap CSS classes](https://bootstrapshuffle.com/ru/classes)
* Классы с `p` задают `padding` + есть комбинации: `py` (по оси Y, т.е. вертикаль),
  `pl`, `pr`, `pt`, `pb` (по сторонам: left, right, top, bottom),

