# String и всё что касается их
* [Difference between @NotNull, @NotEmpty, and @NotBlank](https://stackoverflow.com/a/17137308)

Самый используемый тип в Java.
При каждом изменении будет возвращен новый объект.


## Format
|Format-Specifier      | Datatype related | Format printing | Formatting a string |
|:---------------------|:-----------------|:----------------|:--------------------|
| %d | int, short, byte, long | System.out.printf("Display a Integer %d",15000); | String.format("Display a Integer %d",15000); |
| %c | char | System.out.printf("Display a Character %c",'c'); | String.format("Display a Character %c",'c'); |
| %f | double, float | System.out.printf("Display a Floating-point Number %f",123.45); | String.format("Display a Floating-point Number %f",123.45); |
| %s | String | System.out.printf("Display a String %s","String"); | String.format("Display a String %s","String"); |


## StringTokenizer
Один из способ разбиения строки на части – с помощью класса `StringTokenizer`.
```java
String s = "Good news everyone!";
StringTokenizer tokenizer = new StringTokenizer(s, "ne");
while (tokenizer.hasMoreTokens()) {
    String token = tokenizer.nextToken();
    System.out.println(token);
}
```
```java
// result
Good
ws
v
ryo
!
```

***

Один из способ подсчета кол-ва вхождений:
```java
int stringTokenizer = new StringTokenizer(" " +testString + " ", ".").countTokens()-1;
System.out.println("stringTokenizer = " + stringTokenizer);
```


## [MessageFormat и ChoiceFormat by JavaRush](https://javarush.ru/groups/posts/590-klass-messageformat)
> Класс Java `MessageFormat` принимает набор объектов, форматирует их, а затем вставляет форматированные строки в шаблон в соответствующих местах. 
> Это своего рода альтернатива (или даже дополнение) к статическому методу `String.format`.

С помощью класса `ChoiceFormat` возможно сделать так, что в зависимости от значения переменной будет выбираться необходимый текст. 
Своего рода реализация оператора `if...else`.


## intern()
* [Все о String.intern()](https://habr.com/ru/post/79913/)

> Нужно интернировать строки тогда, когда они не являются константами, и вы хотите иметь возможность быстро сравнить их с 
> другими интернированными строками.


