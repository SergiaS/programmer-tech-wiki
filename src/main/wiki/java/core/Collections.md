# Collections framework



<hr>

## Initialization
Примеры с двойными фигурными скобками `{{}}` означают сначало анонимный класс, и потом блок инициализации.

### List
```java
List<String> list = new ArrayList<>(List.of("Ted","Bob","Lex","Liza","Tasha"));
```

```java
List<String> list = new ArrayList<>() {{
    add("Ted");
    add("Bob");
    add("Lex");
    add("Liza");
    add("Tasha");
}};
```

```java
public static void main(String[] args) {
    calc(new ArrayList<>(){{add("2");}});
}

private static void calc(List<String> list) {
    System.out.println(list); // 2
}
```

### Map
```java
Map<String, PersonEmpl> map = new HashMap<>() {{
    put("IT", new PersonEmpl("Ted", 34));
    put("Market", new PersonEmpl("Liza", 27));
    put("Admin", new PersonEmpl("Carl", 30));
}};
```

<hr>

## Map interface

### Добавление
Пример добавления в мапу из другой коллекции - подсчет одиннаковых элементов.

```java
Map<Integer, Integer> map = new HashMap<>();
for (int n : array) {
    map.put(n, map.getOrDefault(n, 0) + 1);
}
```
Хорошо подходит для заполнения мапы в цикле, здесь нет необходимости использовать методы `computeIfPresent()`, `putIfAbsent()`...

### Сортировка по значению в обратном порядке
Пример сортировки по значению в обратном порядке через `Comparator` через stream.
```java
// to List
List<Integer> list = map.entrySet().stream()
        .sorted(Entry.<Integer, Integer>comparingByValue().reversed())
        .limit(k)
        .map(Entry::getKey)
        .collect(Collectors.toList());
```

```java
// to int[] array
int[] arr = map.entrySet().stream()
        .sorted(Entry.<Integer, Integer>comparingByValue().reversed())
        .limit(k)
        .map(Entry::getKey)
        .mapToInt(i -> i)
        .toArray();
```

### [Пример сортировки HashMap](https://mishrasuraj.medium.com/sorting-hashmap-in-java-8-6fadc1cacb2b)
```java
Map<String, PersonEmpl> map = new HashMap<>() {{
    put("JAVA", new PersonEmpl("Ted", 34));
    put("Perl", new PersonEmpl("Liza", 27));
    put("Python", new PersonEmpl("Carl", 30));
    put("C++", new PersonEmpl("Zina", 33));
    put("C#", new PersonEmpl("Tomas", 41));
    put("GO", new PersonEmpl("Lucas", 31));
    put("Ruby", new PersonEmpl("Sam", 24));
    put("Scala", new PersonEmpl("Simona", 29));
    put("JS", new PersonEmpl("Andy", 32));
    put("C", new PersonEmpl("Sara", 29));
}};
```

Sort by name - declarative:
```java
List<Map.Entry<String, PersonEmpl>> mapEmpl = new ArrayList<>(map.entrySet());
Collections.sort(mapEmpl, (a,b) -> a.getValue().getName().compareTo(b.getValue().getName()));
mapEmpl.forEach(System.out::println);
```

Provide a sorting logic as lambda function:
```java
map.entrySet().stream()
        .sorted((a,b) -> a.getValue().getName().compareTo(b.getValue().getName()))
        .forEach(System.out::println);
```
