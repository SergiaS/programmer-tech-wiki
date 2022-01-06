# Exceptions

## Описание эксепшинов

| Тип исключения                    | Описание Unchecked исключений (java.lang)
|:-------                           |:--------
| `ArithmeticException`             | арифметическая ошибка, например, деление на нуль
| `ArrayIndexOutOfBoundsException`  | выход индекса за границу массива
| `ArrayStoreException`             | присваивание элементу массива объекта несовместимого типа
| `ClassCastException`              | неверное приведение
| `EnumConstantNotPresentException` | попытка использования неопределённого значения перечисления
| `IllegalArgumentException`        | неверный аргумент при вызове метода
| `IllegalMonitorStateException`    | неверная операция мониторинга (например, ожидание незаблоктрованного потока исполнения)
| `IllegalStateException`           | некорректное состояние приложения
| `IllegalThreadStateException`     | запрашиваемая операция несовместима с текущим потоком
| `IndexOutOfBoundsException`       | тип индекса вышел за допустимые пределы
| `NegativeArraySizeException`      | создан массив отрицательного размера
| `NullPointerException`            | неверное использование пустой ссылки
| `NumberFormatException`           | неверное преобразование строки в числовой формат
| `SecurityException`               | попытка нарушения безопасности
| `StringIndexOutOfBoundsException` | попытка использования индекса за пределами строки
| `TypeNotPresentException`         | тип не найден
| `UnsupportedOperationException`   | обнаружена неподдерживаемая операция Список проверяемых системных исключений, которые можно включать в список throws.
| `ClassNotFoundException`          | класс не найден
| `CloneNotSupportedException`      | попытка клонировать объект, который не реализует интерфейс Cloneable
| `IllegalAccessException`          | запрещен доступ к классу
| `InstantiationException`          | попытка создать объект абстрактного класса или интерфейса
| `InterruptedException`            | поток прерван другим потоком
| `NoSuchFieldException`            | запрашиваемое поле не существует
| `NoSuchMethodException`           | запрашиваемый метод не существует
| `ReflectiveOperationException`    | Суперкласс исключений, связанных с рефлексией

* Источник: Шилдт - JAVA 8. Полное руководство.

***

