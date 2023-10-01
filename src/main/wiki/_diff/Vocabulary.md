# Программирование
* `Бизнес логика` - Основная логика метода.
* `Code tangling` (из AOP) - Переплетение бизнес-логики со служебным функционалом. 
  Метод становится громоздким, и его основной функционал сразу не заметно.
* `Code scattering` (из AOP) - Разбросанность служебного функционала по всему проекту. 
  При необходимости что-то изменить в служебном функционале, мы должны будем делать изменения во всех классах.
* `cross-cutting logic` (из AOP) - сквозная логика.
* `Аггрегация` - объединение различных данных.
* `Релевантность` - схожесть.
* `varargs` - variable-length arguments.
* `LUW (Logical Unit of Work)` - Логическая единица работы.
* `Мемоизация` - кеширование значений. 
  Т.е., единожды вычисляя значение, мы заносим его в какую-то структуру данных.
* `Чем отличается библиотека от фреймворка?` - 
Библиотека это дополнительные классы и методы, с помощью которых можно создать функционал (Apache Commons, Guava), 
а фреймворк - это уже готовый функционал (Spring, Hibernate).
* `resolvers` - распознаватели.
* `evict` или `инвалидация` - это сброс, например сброс кеша.
* `асинхронний` - У програмуванні термін "асинхронний" використовується для опису підходу, 
коли задачі виконуються відокремлено одна від одної без очікування результату виконання попередньої задачі.
Наприклад, ExecutorService є асинхронним механізмом виконання, оскільки він дозволяє виконувати задачі в окремих 
потоках без очікування завершення попередньої задачі.
Це дозволяє програмі працювати більш ефективно, оскільки не потрібно чекати на результат виконання кожної задачі, 
щоб почати виконання наступної.
Асинхронність також дозволяє більш ефективно використовувати ресурси, оскільки можна розподілити задачі між доступними ресурсами 
(наприклад, процесорними ядрами) і виконувати їх паралельно.

### Проектирование и повышение производительности работы
* [Что такое SDLC? Этапы, методология и процессы жизненного цикла программного обеспечения.](https://habr.com/ru/company/dcmiran/blog/521718/)
  Фреймворк `SDLC` существует для помощи в сокращении времени вывода продукта на рынок, обеспечении более качественной производительности, 
экономии бюджета и повышения потенциальной пользы вашего продукта для заинтересованных сторон, о которых вы заботитесь. 
  Особенно хорошо `SDLC` помогает при разработке ПО, поскольку он заставляет вас трудиться в строгих рамках. 
  Другими словами, для обеспечения корректных действий в корректное время и по корректным причинам `SDLC` заставит вас следовать каждому необходимому шагу. 
  Думайте о `SDLC` как о плане по достижению успеха: слепое ему следование ничего вам не гарантирует, но повышает вероятность что вы останетесь довольны результатами.
* [SCRUM — это эффективное управление проектами.](https://brainrain.com.ua/скрам-это/)
  `Скрам` — это фреймворк управления, согласно которому одна или несколько кроссфункциональных самоорганизованных команд создают продукт инкрементами, то есть поэтапно. 
  В команде может быть около семи человек.


### Література

#### JAVA
* `Java. Эффективное программирование`, Джошуа Блох.
* `Рефакторинг. Улучшение проекта существующего кода`, Мартин Фаулер.
* `Чистый код. Создание, анализ и рефакторинг`, Роберт Мартин.
* `Spring 5 для профессионалов`, Юлиана Козмина и другие.
* `Test-Driven Java Development`, Viktor Farcic, Alex Garcia (на русском языке пока не вышла).



## English
* [Telling time](https://en.khanacademy.org/math/cc-third-grade-math/time/imp-time/a/telling-time-review)

> 1.2e-4 = 1.2 x 10-4 = 0.00012

### Література
[Учебник английского языка от программиста](https://urvanov.ru/2018/05/13/учебник-английского-языка/)

### Скорочення
* `BTW` - by the way.
* `ASAP` - as soon as possible.
* `AKA` - **also known as**, скор. від «також відомий як…» , псевдонім.


### Читання формул, математика:
* [Source - Baeldung: An Introduction to the Theory of Big-O Notation](https://www.baeldung.com/cs/big-o-notation)
  
  f(x) = O(g(x)), we say **f of x is big-O g of x**.

* [Exponents](https://www.mathsisfun.com/exponent.html)
  In 82 the "2" says to use 8 twice in a multiplication,
  so 82 = 8 × 8 = 64
  
  In words: 82 could be called "8 to the power 2" or "8 to the second power", or simply "8 squared"
  
  Exponents are also called Powers or Indices.

***

* `2 * 3 = 6` читається як **two times three is six**;
* `O(n2)` - **big O n square**;
* `120` - **one twenty**;

***

Число яке переноситься при підрахунку у стовбчик з одного у інший, англійською називають **[carry](https://en.wikipedia.org/wiki/Carry_(arithmetic))**.

***

* Поділено на половини: `divided into halves`
* Поділено на третини: `divided into thirds`
* Поділено на четверті: `divided into fourths` 

***

Ціле число: `whole number`
```text
What whole number comes right before 110?
```

***

Розрядність чисел: `place value`
```text
Why do we need to learn about place value?
```

***

> Якщо число непарне, тоді сума цифр є число парне.
> 
> Якщо число парне, тоді добуток його цифр є парним числом.
* Парні числа - `even numbers` - 2,4,6,8,10...
  Can be split into two equal groups.
* Непарні числа - `odd numbers` - 1,3,5,7,9...
  Cannot be split into two equal groups.

Парний добуток - `even product`

```text
Which of following have an even product?
```


Одиниця та десятки кожного заштрихованого числа в сумі кратна 9: 
```text
The ones digit and the tens digit of each shaded number add up to a multiple of 9
```



***

Крайня ліва цифра - `leftmost digit`
```text
We usually start by looking at the leftmost digit.
``` 

***

* Знак меньше `less` - `<`
* Знак більше `greater` - `>`
* Знак дорівнює `equal` - `=`
* Знак приблизно дорівнює `squiggly equal` або `almost equal` - `≈`

***


#### [Multiplication](https://en.khanacademy.org/math/cc-third-grade-math/intro-to-multiplication/imp-properties-of-multiplication/a/intro-to-multiplication-faq)

> Anything multiplied by `1` stays the same, anything multiplied by `0` is just `0`.

`6 × 2 = 12` читається як: `six times two would be equal to twelve` або `six twos`, тобто шість двійок,
- де `12` це добуток або `product`.
- `factor` - множник.

***


#### [Division](https://en.khanacademy.org/math/cc-third-grade-math/intro-to-division/imp-division-facts/a/intro-to-division-faq) 
> The first number in a division equation is the dividend, or the number we're dividing up. 
> The second number is the divisor, or the number of parts we're breaking the dividend into. 
> The answer to a division equation is called the quotient.

* `quotient` - частка.
* розділені порівну - `divided evenly`
* `6 ÷ 2` divided by, 2 means we are dividing 6 into 2 equal parts.

***


#### [Fractions](https://en.khanacademy.org/math/cc-third-grade-math/imp-fractions/imp-fractions-and-whole-numbers/a/understand-fractions-faq)
> `fraction` - дріб, `fractions` - дроби

> Верхнє число називається чисельником `numerator`, а нижнє - знаменником `denominator`.

* [Intro to fractions](https://en.khanacademy.org/math/cc-third-grade-math/imp-fractions/imp-fractions-intro/v/fraction-basics)

> **Note**<br>
> Знизу наведені приклади, які можуть записуватися як ділення з закінченням `1/4th`, але це для простоти запису дробей

* `1/3rd`,  `1 thirds` - читається як `one third of the whole` або `one third of the entire fraction` або `one of the three sections`. 
* `one whole` - одне ціле.