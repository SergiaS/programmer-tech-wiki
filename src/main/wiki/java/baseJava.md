# JAVA
Interesting notes about Java.

***

JVM executes Java bytecode;

JRE includes JVM and standard libraries: it is needed to run compiled programs;

JDK includes JRE and development tools: it is needed to develop programs. As a developer, you need to install JDK.

Before Java 11, if you wanted only to run a Java program, JRE was enough for you.
However, since Java 11 was released, for most JVM implementations JRE is no longer downloadable as a separate component. If you want to run programs in JVM 11 or newer, you have to install JDK.

*** 




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

## Notes
* `@SuppressWarnings("rawtypes")` - Аннотация пакета `package java.lang`, работает в режиме компиляции. 
  Указывает чтобы компилятор не показывал предупреждения.
  

***

## Other helpful Java info 
### Как узнать тип возвращаемого объекта
Нужно на объекте вызвать метод `getClass()`.


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
