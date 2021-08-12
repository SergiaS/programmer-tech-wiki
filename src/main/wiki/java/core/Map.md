# Map interface

## Добавление
Пример добавления в мапу из другой коллекции - подсчет одиннаковых элементов.

```java
Map<Integer, Integer> map = new HashMap<>();
for (int n : array) {
    map.put(n, map.getOrDefault(n, 0) + 1);
}
```
Хорошо подходит для заполнения мапы в цикле, здесь нет необходимости использовать методы `computeIfPresent()`, `putIfAbsent()`... 

## Сортировка
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
