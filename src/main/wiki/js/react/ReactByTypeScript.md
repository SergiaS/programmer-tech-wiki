# React by TypeScript

## [Статична типізація](https://uk.reactjs.org/docs/static-type-checking.html)
Для статичної типізації (перевірка на тип) рекомендується використовувати або **Flow**, або **TypeScript**.
Якщо працюємо лише з **JS**, тоді можна використовувати бібліотеку **PropTypes**.

### Створення проєкту React + TS
Є можливість створення проєкту React + TS на основі шаблону.
Достатньо виконати команду в терміналі (термінал краще запускати з теки `src`):
```commandline
npx create-react-app . --template typescript
```


## Examples

<details>
<summary>Приклад визначення типу івента</summary>

...де `React.ChangeEventHandler<HTMLInputElement>` (у функції `handleChange`) задається по типу
з яким працює `onChange` у `input`'і (ставимо або наводимо курсор на `onChange` і IDEA все покаже).
```tsx
import React, {useState, useEffect, useRef} from "react";

import {ToDoList} from "./ToDoList";
import {ITodo} from "../types/data";

const App: React.FC = () => {
    const [value, setValue] = useState('');
    const [todos, setTodos] = useState<ITodo[]>([]);

    const handleChange: React.ChangeEventHandler<HTMLInputElement> = (e) => {
        setValue(e.target.value);
    }

    const addTodo = () => {
        if (value) {
            setTodos([...todos, {
                id: Date.now(),
                title: value,
                complete: false,
            }]);
            setValue('');
        }
    }

    return (
        <div>
            <div>
                <input
                    value={value}
                    onChange={handleChange}
                />
                <button onClick={addTodo}>Add</button>
            </div>
            <ToDoList items={todos} />
        </div>
    )
}

export {App}
```
</details>

***


<details>
<summary>Приклад передачі івенту по кліку</summary>

```tsx
<Label htmlFor={optionId} onClick={(e) => onClick(e, lang)}>{lang.code}</Label>

function onClick(e: React.MouseEvent<HTMLLabelElement>, lang: any) {
    console.log(e.currentTarget.getAttribute('for'));
    // some code
}
```
</details>

***



