# JAVA
* [Список полезных ссылок для Java программиста](https://github.com/Vedenin/useful-java-links/tree/master/link-rus)
* [Лучшие плагины IntelliJ IDEA](https://habr.com/ru/post/486578/)

> Оператор `==` сравнивает ссылаются ли две ссылки на один и тот же объект, 
> в то время как `equals()` сравнивает содержат ли два объекта одни и те же данные.
> 
> Другими словами: `==` внешнее сравнение, а `equals()` - внутренее.

***

JVM executes Java bytecode;

JRE includes JVM and standard libraries: it is needed to run compiled programs;

JDK includes JRE and development tools: it is needed to develop programs. As a developer, you need to install JDK.

Before Java 11, if you wanted only to run a Java program, JRE was enough for you.
However, since Java 11 was released, for most JVM implementations JRE is no longer downloadable as a separate component. 
If you want to run programs in JVM 11 or newer, you have to install JDK.

*** 


## Features
* [Java Records (JEP 359)](https://habr.com/ru/post/487308/)


## NOTES
* Никогда не сравнивай числа с плавующей точкой через `==`.
Для данной операции используй `BigDecimal` и его методы.

* `@SuppressWarnings("rawtypes")` - Аннотация пакета `package java.lang`, работает в режиме компиляции.
  Указывает чтобы компилятор не показывал предупреждения.

* **Как узнать тип возвращаемого объекта?**
Нужно на объекте вызвать метод `getClass()`.


## Системы счисления

|Системы счисления               |Используемые символы  |Пример чисел|
|:------------------------------ |:-------------------  |:------------------|
|Двоичная (Binary)               |0, 1                  |<b>0B</b>111100    |
|Восьмеричная (Octal)            |0 - 7                 |<b>0</b>74         |
|Десятичная (Decimal Value)      |0 - 9                 |60                 |
|Шестнадцатеричная (Hexadecimal) |0 - 9, A - F          |0<b>x</b>3C        |


## Простые числовые типы
|Тип      |Описание|
|:--------|:--------------------------------------------------------------------------------  |
|boolean  |булев тип, может иметь значения `true` или `false`                                 |
|byte     |8-разрядное целое число                                                            |
|short    |16-разрядное целое число                                                           |
|int      |32-разрядное целое число                                                           |
|long     |64-разрядное целое число                                                           |
|char     |16-разрядное беззнаковое целое, представляющее собой символ UTF-16 (буквы и цифры) |
|float    |32-разрядное число в формате IEEE 754 с плавающей точкой                           |
|double   |64-разрядное число в формате IEEE 754 с плавающей точкой                           |


## [Security Tools and Commands](https://docs.oracle.com/en/java/javase/11/tools/keytool.html)
X.509, ssl, keytool...

> Строки в Java имеют формат Unicode.

> Java позволяет преобразовать строку в набор байт любой известной ей кодировки.
> Для этого есть специальные методы у класса String(!). 
> Также в Java есть специальный класс Charset, который описывает конкретную кодировку.

> Все анонимные классы на самом деле превращаются компилятором в обычные внутренние классы.


## [Character, кодировки](https://javarush.ru/quests/lectures/questmultithreading.level02.lecture10?post=full)
1) Как получить список всех кодировок, с которыми Java может работать?
2) Как получить текущую активную кодировку (Unicode)?
3) Как преобразовать строку в определенную кодировку?
4) Как преобразовать набор байт, которые я прочитал из файла в строку, если я знаю в какой кодировке они были в файле?
5) Как преобразовать набор байт из одной кодировки в другую?


## Компиляция класса (`.java` -> `.class`)
1. Для этого необходимо открыть терминал (консоль в IDEA) в папке с нужным файлом.
2. Выполнить команду `javac` с именем файла и расширением файла `.java`:
    ```shell
    javac Note5.java
    ```
3. Далее в данном каталоге появится файл `Note5.class` который можно открыть в IDEA.


## Работа с `.properties` файлами
* [Java Properties file exampless](https://mkyong.com/java/java-properties-file-examples/)
```java
private static final Properties properties = new Properties();
static {
    try(InputStream input = new FileInputStream("config.properties")) {
        properties.load(input);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```


## Методы улучшения качества кода
* [Topjava](https://github.com/JavaWebinar/topjava/blob/doc/doc/lesson04.md#-3-методы-улучшения-качества-кода)
  * [Codacy Check code](https://app.codacy.com/) - проверка стиля и поиск багов в коде
  * [Сборка и тесты Travis](https://www.travis-ci.com/)
    * [Что такое travis](https://habr.com/ru/post/140344/) 
* Plugins
  * IDEA Settings -> Plugins -> Browse repositories... Add [QAPlug: PMD/FindBugs/Checkstyle/Hammurapi](https://qaplug.com/about/)
    (Tools -> QAPlug -> Analyze Code...) 


## As In JAVA
Аналогичные методы в других языках:

| JAVA           | JS                              |
|:--------       |:-------                         |
| `s.charAt(n)`  | `charAt(n)`,<br>`myString[n]`   |
