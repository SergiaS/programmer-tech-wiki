# Symbols
## Ссылка на иконке для видео:

![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)
```text
![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)
```

***

## Вставка картинки:
![img](https://miro.medium.com/max/2640/1*jr9u4kZidlDWg4Q0FrILrw.jpeg)
```text
![img](https://miro.medium.com/max/2640/1*jr9u4kZidlDWg4Q0FrILrw.jpeg)
```

***

## Backtick - `` ` ``
For this:

`` `Hi, ${name}!` ``

Use next code:
```text
`` `Hi, ${name}!` ``
```

# Menu
Пример создания меню с ссылками:

<details>
<summary>SHOW MENU</summary>

- [Menu 1]()
- [Menu 2]()
    - [Menu 2.1]()

</details>

```text
<details>
<summary>SHOW MENU</summary>

- [Menu 1]()
- [Menu 2]()
    - [Menu 2.1]()

</details>
```

***

> <details>
> <summary>ПОКАЗАТИ ПРИКЛАД</summary>
>
> ```js
> // наш створений компонент CostItem
> import './CostItem.css';
> 
> function CostItem(props) {
>     return (
>         <div className='cost-item'>
>             <div>{props.date.toISOString()}</div>
>             <div className='cost-item__description'>
>                 <h2>{props.description}</h2>
>                 <div className='cost-item__price'>${props.amount}</div>
>             </div>
>         </div>
>     );
> }
> 
> export default CostItem;
> ```
> </details>


# Horizontal line
***
```text
*** or <hr>
```

# Table
|Column 1 | Column 2 |
|:------- |:-------- |
|Text 1   | Text 2   |

```text
|Column 1 | Column 2 |
|:------- |:-------- |
|Text 1   | Text 2   |
```

# Markdown for GitHub
> **Note**
> This is a note

> **Warning**
> This is a warning


# Emoji
[Коди для емоджі](https://gist.github.com/rxaviers/7360908), приклад
* ℹ️ :information_source:
* ☑️ :ballot_box_with_check:
* ⚠️ :warning:
* ❌    :x:
* ❗     :exclamation:
* ✖️ :heavy_multiplication_x:

