# SVG


## Змінити колір
* [css-color-filter-generator online](https://angel-rs.github.io/css-color-filter-generator/)

Щоб змінити лише колір достатньо додати/змінити поле зі значенням:
```css
fill="#cedde3"
```

### Динамічна зміна кольору в CSS
Потрібно щоб в кожному файлі було поле `fill="black"`.

Далі краще створити css зміну і використовувати задане значення в усіх своїх іконках.

Щоб змінити колір, потрібно відтворити HEX колір у фільтрі.
Для цього шукаємо сервіс **HEX to Filter**, і вставляємо згенероване значення у нашу змінну:
```css
:root {
  --svg-filter: invert(91%) sepia(40%) saturate(290%) hue-rotate(169deg) brightness(94%) contrast(88%);
}
```
```css
.search-icon {
  position: absolute;
  top: 28px;
  left: 30.5%;
  width: 25px;
  height: 25px;
  background: url('/img/icon/search.svg') no-repeat;
  filter: var(--svg-filter);
}
```

## Додаємо анімацію
Анімація SVG створюється за допомогою блока `@keyframes` в `.css`, і потім ця анімація використовується в якомусь класі:
```css
.my-svg circle {
  fill: none;
  stroke-width: 5px;
  stroke: #fafafa;
  stroke-dasharray: 800;
  stroke-dashoffset: 800;
  animation: 4s circle-outline forwards;
}

@keyframes circle-outline {
  from {
    stroke-dashoffset: 800;
    opacity: 0;
  }
  to {
    stroke-dashoffset: 0;
    opacity: 1;
  }
}
```


