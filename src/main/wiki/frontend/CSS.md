# CSS
* [Responsive web design in 37 minutes + layout. You don’t need Bootstrap!](https://www.youtube.com/watch?v=XbnAKjjlgc4)

> В таблице для раскраски строк в разный цвет используется тег `:nth-of-type()`:
> ```css
> tr:nth-of-type(even) {
>    background: #ffd98b;
> }
> ```


## data-атрибуты
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


## Bootstrap
* [All Bootstrap CSS classes](https://bootstrapshuffle.com/ru/classes)
* Классы с `p` задают `padding` + есть комбинации: `py` (по оси Y, т.е. вертикаль),
  `pl`, `pr`, `pt`, `pb` (по сторонам: left, right, top, bottom),

