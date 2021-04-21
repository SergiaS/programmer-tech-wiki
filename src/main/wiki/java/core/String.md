# Строки и всё что касается их

Один из способ разбиения строки на части – с помощью класса `StringTokenizer`.
```java
String s = "Good news everyone!";
StringTokenizer tokenizer = new StringTokenizer(s, "ne");
while (tokenizer.hasMoreTokens()) {
    String token = tokenizer.nextToken();
    System.out.println(token);
}
```
```text
Good
ws
v
ryo
!
```

