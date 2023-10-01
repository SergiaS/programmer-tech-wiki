# Усе про String
Инфа о классах по работы со строками:
* [String](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/String.md#string)
* [StringBuilder](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/String.md#stringbuilder)
* [StringBuffer](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/String.md#stringbuffer)

> String Pool є частиною Купи (heap).

## Какая разница между String, StringBuilder и StringBuffer?
`String` - значения хранятся в пуле стрингов.
Это неизменяемый объект - тут всегда будет возвращаться новый экземпляр - ссылка на другой объект.

`StringBuilder` - то же самое что и `StringBuffer`, только не синхронизирован, и работает быстрее. 
Изменяемый объект - после операций с объектом, ссылка меняться не будет.

`StringBuffer` - значения хранятся в стеке. 
Данный класс синхронизирован, он предназначен для многопоточности. Из-за многопоточности работает медленнее.


## String 
* [Difference between @NotNull, @NotEmpty, and @NotBlank](https://stackoverflow.com/a/17137308)

Самый используемый тип в Java.
При каждом изменении будет возвращен новый объект.


### Format
|Format-Specifier      | Datatype related | Format printing | Formatting a string |
|:---------------------|:-----------------|:----------------|:--------------------|
| %d | int, short, byte, long | System.out.printf("Display a Integer %d",15000); | String.format("Display a Integer %d",15000); |
| %c | char | System.out.printf("Display a Character %c",'c'); | String.format("Display a Character %c",'c'); |
| %f | double, float | System.out.printf("Display a Floating-point Number %f",123.45); | String.format("Display a Floating-point Number %f",123.45); |
| %s | String | System.out.printf("Display a String %s","String"); | String.format("Display a String %s","String"); |


### StringTokenizer
Один из способов разбиения строки на части – с помощью класса `StringTokenizer`.
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

Один из способов подсчета кол-ва вхождений:
```java
int stringTokenizer = new StringTokenizer(" " +testString + " ", ".").countTokens()-1;
System.out.println("stringTokenizer = " + stringTokenizer);
```


### [MessageFormat и ChoiceFormat by JavaRush](https://javarush.ru/groups/posts/590-klass-messageformat)
> Класс Java `MessageFormat` принимает набор объектов, форматирует их, а затем вставляет форматированные строки в шаблон в соответствующих местах. 
> Это своего рода альтернатива (или даже дополнение) к статическому методу `String.format`.

С помощью класса `ChoiceFormat` возможно сделать так, что в зависимости от значения переменной будет выбираться необходимый текст. 
Своего рода реализация оператора `if...else`.


### intern()
* [Все о String.intern()](https://habr.com/ru/post/79913/)

> Нужно интернировать строки тогда, когда они не являются константами, и вы хотите иметь возможность быстро сравнить их с 
> другими интернированными строками.


### replace()
Метод `replace()` возвращает новый объект при использовании `String (s2.replace("k", "k"))`, а при использовании `char` и одинаковых заменах - 
возвращает тот же объект (`s2.replace('v', 'v')` или когда нет сходства) - то же самое и метод `trim()` (лучше использовать `strip()`).
```java
String s = new String("privetpr0asd");
System.out.println(s.indexOf("t"));         // 5 - priveTpr0asd
System.out.println(s.indexOf("r", 2));      // 7 - privetpR0asd
System.out.println(s.startsWith("pr"));     // true - PRivetpr0asd
System.out.println(s.startsWith("pr", 6));  // true - privetPR0asd
System.out.println(s.endsWith("sd"));       // true - privetpr0aSD
```

### join()
Метод `join()` позволяет объединять массивы и листы в одну строку разделяя указанным разделителем:
```java
String[] strArr = {"d","e","z"};
String s = String.join("=", strArr); // "d=e=z"
```

### Робота з символами - Characters, char
Для перебору строки посимвольно можна скористатися методом `toCharArray()`.

Приклад підрахунку символів
```java
String s = "abbaa";
int[] hash = new int[26];
for (int i = 0; i < s.length(); i++) {
    hash[s.charAt(i)  - 'a']++;
}
```


## StringBuilder
* `StringBuilder` отличается от `String` тем, что он mutable (т.е. изменяемый).
* Первоначальная вместимость `StringBuilder` по умолчанию составляет 16 символом. Размер можно указать самому `new StringBuilder(50)`.


### Methods
* `setCharAt(int index, char ch)` - will replace char at index;
* Метод `insert()` является расширенным методом `append()` по функциональности.
  `insert(...)` - will insert input at index. Also, you can set subsequence of *array* or *CharSequence*.
  ```java
  StringBuilder sb = new StringBuilder("abcde");
  sb.setCharAt(1, 'z');
  System.out.println(sb); // azcde
  ```
* В методе `indexOf()` класса `StringBuilder` можно использовать только значения типа `String`, но не `char`.
* `subsequence()` является аналогом `substring()` у `String`.
* Класс `StringBuilder` не переопределяет методы `equals()` и `hashCode()`. `equals` работает как `==`.
* Метод `delete()` удаляет символы начиная с определенного индекса и заканчивая определенным индексом.
* Метод `deleteCharAt()` определяет позицию (индекс) символа, которую удалит.
* Метод `replace()` состоит из 3 параметров - с индекса, по индекс, на что меняет.



## StringBuffer
`StringBuffer` похож по своим свойствам со `StringBuilder`.
Функциональность у этих классов практически одинаковая. `StringBuffer` предпочтительнее для многопоточности.
Класс `StringBuffer` не переопределяет методы `equals()` и `hashCode()`. `equals` работает как `==`.

