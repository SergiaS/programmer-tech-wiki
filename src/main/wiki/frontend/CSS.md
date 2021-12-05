# CSS
* [Responsive web design in 37 minutes + layout. You don’t need Bootstrap!](https://www.youtube.com/watch?v=XbnAKjjlgc4)


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
