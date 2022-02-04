# Exceptions
> **Compile time error** - ошибки во время написания/компиляции кода. Например, неправильный синтаксис.

> **Run time error** - ошибки во время запуска кода. Например, компилятор не видит ошибки в выражении "5/0", 
> но при запуске будет ошибка - делить на 0 нельзя.


## [Встроенные исключения Java](http://developer.alexanderklimov.ru/android/java/exception.php#system)
Существуют несколько готовых системных исключений. Большинство из них являются подклассами типа RuntimeException и их не нужно включать в список throws. 
Вот небольшой список непроверяемых исключений:

| Тип исключения                    | Описание Unchecked исключений (java.lang)
|:-------                           |:--------
| `ArithmeticException`             | арифметическая ошибка, например, деление на нуль
| `ArrayIndexOutOfBoundsException`  | выход индекса за границу массива
| `ArrayStoreException`             | присваивание элементу массива объекта несовместимого типа
| `ClassCastException`              | неверное приведение
| `EnumConstantNotPresentException` | попытка использования неопределённого значения перечисления
| `IllegalArgumentException`        | неверный аргумент при вызове метода
| `IllegalMonitorStateException`    | неверная операция мониторинга (например, ожидание не заблокированного потока исполнения)
| `IllegalStateException`           | некорректное состояние приложения
| `IllegalThreadStateException`     | запрашиваемая операция несовместима с текущим потоком
| `IndexOutOfBoundsException`       | тип индекса вышел за допустимые пределы
| `NegativeArraySizeException`      | создан массив отрицательного размера
| `NullPointerException`            | неверное использование пустой ссылки
| `NumberFormatException`           | неверное преобразование строки в числовой формат
| `SecurityException`               | попытка нарушения безопасности
| `StringIndexOutOfBoundsException` | попытка использования индекса за пределами строки
| `TypeNotPresentException`         | тип не найден
| `UnsupportedOperationException`   | обнаружена неподдерживаемая операция

Список проверяемых системных исключений, которые можно включать в список `throws`:

| Тип исключения                    | Описание Unchecked исключений (java.lang)
|:-------                           |:--------
| `ClassNotFoundException`          | класс не найден
| `CloneNotSupportedException`      | попытка клонировать объект, который не реализует интерфейс `Cloneable`
| `IllegalAccessException`          | запрещен доступ к классу
| `InstantiationException`          | попытка создать объект абстрактного класса или интерфейса
| `InterruptedException`            | поток прерван другим потоком
| `NoSuchFieldException`            | запрашиваемое поле не существует
| `NoSuchMethodException`           | запрашиваемый метод не существует
| `ReflectiveOperationException`    | суперкласс исключений, связанных с рефлексией

* Источник: Шилдт - JAVA 8. Полное руководство.


### try-with-resources
Для того чтобы использовать класс в **try-with-resources** он должен имплементировать интерфейс **AutoCloseable** (имеет 1 метод close).


