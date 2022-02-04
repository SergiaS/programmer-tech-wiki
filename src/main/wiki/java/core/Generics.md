# Generics
> Для JVM не существует понятия дженерикс, при компиляции все данные о типе стираются.
> Вместо `Т` пишется `Object` (т.к. это первоначальный класс).
> А если `<T extends Number>` - то здесь высокосидящий класс будет `Number`, и вместо всех `T` тоже будет `Number`.

> Поскольку информация о дженериках стирается, перегруженные методы создавать невозможно!


## Примеры
Дженерик наследуемый от класса и мплементируемый двумя интерфейсами:
```java
class Info<T extends Number&I1&I2> {...}
```

## Wildcard
* [Использование wildcard в Generics Java](https://ru.stackoverflow.com/a/361843)

У дженериков есть понятие [Wildcard](https://docs.oracle.com/javase/tutorial/java/generics/wildcards.html).

Они в свою очередь делятся на три типа:
* Unbounded Wildcards, или просто вайлдкард - `<?>` означает, что вместо вопроса может быть подставлен любой класс.
    ```java 
    static void showListIngo(List<?> list) {...}
    ```
  **Используй как правило:** _Когда используется **Wildcard** параметры, вы не можете вызывать методы, которые изменяют наш объект._

* Upper Bounded Wildcards - `<? extends Number>`. Здесь вместо `?` подойдут все классы, которые наследуются от `Number` - `Integer`, `Double`...:
    ```java
    static double sum(ArrayList<? extends Number> list) {...}
    ```

* Lower Bounded Wildcards - `<? super Integer>`. Здесь вместо `?` подойдёт только класс, от которого наследуется `Number` - т.е. `Object`:
    ```java
    static double sum(ArrayList<? super Number> list) {...}
    ```

С **Wildcard** действует так называемый **Get Put principle**. Можно их выразить в следующем виде:
- Producer Extends Consumer Super
- Используй `extends` если нужно положить значение (это Producer Extends)
- Используй `super` если нужно положить значение (это Consumer Super - потребитель)

