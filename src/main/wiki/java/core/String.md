# Строки и всё что касается их

## Format
|Format-Specifier      | Datatype related | Format printing | Formatting a string |
|:---------------------|:-----------------|:----------------|:--------------------|
| %d | int, short, byte, long | System.out.printf("Display a Integer %d",15000); | String.format("Display a Integer %d",15000); |
| %c | char | System.out.printf("Display a Character %c",'c'); | String.format("Display a Character %c",'c'); |
| %f | double, float | System.out.printf("Display a Floating-point Number %f",123.45); | String.format("Display a Floating-point Number %f",123.45); |
| %s | String | System.out.printf("Display a String %s","String"); | String.format("Display a String %s","String"); |


## Split by StringTokenizer
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

## [MessageFormat и ChoiceFormat by JavaRush](https://javarush.ru/groups/posts/590-klass-messageformat)
> Класс Java `MessageFormat` принимает набор объектов, форматирует их, а затем вставляет форматированные строки в шаблон в соответствующих местах. 
> Это своего рода альтернатива (или даже дополнение) к статическому методу `String.format`.

С помощью класса `ChoiceFormat` возможно сделать так, что в зависимости от значения переменной будет выбираться необходимый текст. 
Своего рода реализация оператора `if...else`.
