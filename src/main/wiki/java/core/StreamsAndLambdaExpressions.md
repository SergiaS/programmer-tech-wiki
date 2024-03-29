# Streams and Lambda expressions
* [Sort using Lambda expressions](https://medium.com/@aishwarya.chamanoor/sort-using-lambda-expressions-java-19d0433abcb7)

Лямбди часто використовуються у стрімах, тому вони дуже пов'язані.

***

Приклад перетворення значень у масив:
```java
String[] details = e.getBindingResult().getFieldErrors().stream()
        .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
        .toArray(value -> new String[value]);
//        .toArray(String[]::new);
```

***

# Stream
> Исполнение стрима запускается только после выполнения терминальной операции!

Однакові способи створення стримів:
```java
List<String> names = List.of("Amigoscode", "Alex", "Zara");
Stream<String> stream = names.stream();
```
```java
Stream<String> namesStream = Stream.of("Amigoscode", "Alex", "Zara");
```


## Оператори Stream
> Терминальный оператор в цепочке может быть только один, а промежуточных - много.

> Обработка не начнётся до тех пор, пока не будет вызван терминальный оператор. 
> `list.stream().filter(s -> s > 5)` (не возьмёт ни единого элемента из списка);

> Экземпляр, стрима нельзя использовать более одного раза.

Операторы можно разделить на две группы:
- __Промежуточные__ (`intermediate`, ещё называют `lazy`) — обрабатывают поступающие элементы и возвращают стрим. 
  Промежуточных операторов в цепочке обработки элементов может быть много.
- __Терминальные__ (`terminal`, ещё называют `eager`, он же конечный) — обрабатывают элементы и завершают работу стрима, так что терминальный оператор в цепочке может быть только один.

## Возможные способы создания Stream:
- Пустой стрим: `Stream.empty()`
- Стрим из List: `list.stream()`
- Стрим из Map: `map.entrySet().stream()`
- Стрим из массива: `Arrays.stream(array)`
- Стрим из указанных элементов: `Stream.of("1", "2", "3")`

## boxed
Данный метод просто конвертирует `int` в `Integer`, `double` -> `Double`...
Когда нужно записать значение из stream'a в `List<Integer>`, необходимо использовать метод `boxed()`.
```java
List<Integer> list = students.stream()
                .mapToInt(el -> el.getCource())
                .boxed()
                .collect(Collectors.toList());
```

## Collectors
Колектори в Java Stream API - це спеціальні об'єкти, які використовуються для збору та обробки даних під час операцій над потоками. 
Колектори визначають, які дії виконувати з елементами потоку та як результат цих дій зберегти.

Колектори допомагають зручно обробляти дані у потоках та збирати результати в зручний формат, такий як списки, мапи або рядки.
Вони є потужним інструментом для операцій зі стрімами в Java.

<u>Ось декілька популярних колекторів і їх функції:</u>

***

`toList()` - цей колектор збирає всі елементи потоку в список.
```java
List<String> names = people.stream()
    .map(Person::getName)
    .collect(Collectors.toList());
```

***

`toSet()` - збирає унікальні елементи потоку в множину (Set).
```java
Set<Integer> uniqueNumbers = numbers.stream()
    .collect(Collectors.toSet());
```

***

`toMap()` - створює карту (Map) з потоку, де ви можете задати, як ключі і значення пов'язані.
```java
Map<String, Integer> nameToAge = people.stream()
    .collect(Collectors.toMap(Person::getName, Person::getAge));
```

***

`joining()` - збирає рядки потоку в один рядок, об'єднуючи їх.
```java
String combinedNames = people.stream()
    .map(Person::getName)
    .collect(Collectors.joining(", ")); // Об'єднати імена через кому та пробіл
```

***
        
`groupingBy()` - групує елементи потоку за заданим критерієм та повертає карту, де ключами є критерії, а значеннями - списки відповідних елементів.
```java
Map<String, List<Person>> peopleByCountry = people.stream()
    .collect(Collectors.groupingBy(Person::getCountry));
```

***

`summarizingInt(), summarizingLong(), summarizingDouble()` - 
збирають статистичну інформацію щодо числових значень в потоці (середнє, максимальне, мінімальне значення, тощо).
```java
IntSummaryStatistics stats = numbers.stream()
    .collect(Collectors.summarizingInt(Integer::intValue));
```

***

`reducing()` - дозволяє задати власну функцію акумуляції для збирання значень.
```java
int sum = numbers.stream()
    .collect(Collectors.reducing(0, (a, b) -> a + b)); // Знайдемо суму чисел
```



## Примеры стримов

### Example 1:
Сначала нужно превратить набор элементов/данных (что принимаем - лист, массив...) в другую сущность - в поток (вызвав метод `.stream`).
Далее можно делать любые преобразования.

Метод `.map()` позволяет легко изменять набор данных, а также получить (собрать новую коллекцию).
Сначала метод берет каждый элемент из текущего набора данных, и сопоставляет ему новый элемент нового набора данных.
Вот это сопоставление мы описываем с помощью лямбда выражений. MAP переводится как отображение (теория множеств).

```java
arr = Arrays.stream(arr).map(a -> a * 2).toArray();
list = list.stream().map(a -> a * 2).collect(Collectors.toList());
```

У нас есть какая-то переменная a, и эту переменную (т.е. каждый элемент из входящего массива/листа) мы соспоставляем этой же переменной умноженой на 2. 
Чтобы сохранить данные, нужно конвертнуть их в нужный нам тип, т.к. метод .map() возвращает нам новый стрим.

```java
// stream - Пример умножения элементов на 2

// Stream для массива
Arrays.stream(arr).map(a -> a * 2);

// Stream для листа
list.stream().map(a -> a * 2);
```
```java
// method - Пример умножения элементов на 2
for (int i = 0; i < 10; i++) {
    arr[i] = arr[i] * 2;
    list.set(i, list.get(i) * 2);
}
```

***

### Example 2:
* `reduce()` (уменьшение) - метод берет набор данных и сжимает его в какой-то один элемент, 
  например можно подсчитать сумму элементов, произведение... (арифметические операции).

  ```java
  // Обчислення мінімального значення всіх цілих чисел у списку:
  List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

  int min = numbers.stream()
        .reduce(Integer.MAX_VALUE, (a, b) -> Math.min(a, b));
  
  System.out.println(min); // 1
  ```
  ```java
  // Зведення всіх елементів потоку до одного значення:
  List<String> strings = Arrays.asList("Hello", "World!");

  String joined = strings.stream()
        .reduce("", (a, b) -> a + b);
  
  System.out.println(joined); // HelloWorld!
  ```
где:
* `acc` (аккумулятор / накопитель) временная переменная, а `b` текущий элемент набора.
* `.getAsInt()` - приведение к целому числу.

если временную переменную `acc` проинициализировать `0`, то можно обойтись без приведения к целому числу:
```java
int sum1 = Arrays.stream(arr3).reduce(0, (acc, b) -> acc + b);
```
```java
// stream - Пример reduce - посчёт суммы всех элементов

// Stream для массива
Arrays.stream(arr3).reduce((acc, b) -> acc + b);
// Stream для массива с присвоением значения для int
int sum1 = Arrays.stream(arr3).reduce((acc, b) -> acc + b).getAsInt();

// Stream для листа
list3.stream().reduce((acc, b) -> acc + b);
```
***

### Example 3:
Пример стрима с лямбдами из проекта Topjava (HW0):
```java
// Сначало узнаем кол-во каллорий за день
Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

// потом фильтруем по нужному нам времени и собирает новый лист
        return meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
```
***

### Example 4: [LeetCode - 451. Sort Characters By Frequency](https://leetcode.com/problems/sort-characters-by-frequency/discuss/646152/Slow-and-funny-Steam-only-Java-solution):
Не продуктивный, но очень наглядный метод работы стримов!
```java
public String frequencySort(String s) {
    return s.chars()
        .mapToObj(c -> (char)c)                                                     // map IntStream into Stream<Character>
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())) // collect into frequency map: Map<Character, Long> 
        .entrySet().stream()                                                        // establish a new stream: Stream<Map.Entry<Character, Long>>
        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))             // sort by frequency
        .flatMap(e -> Stream.generate(e::getKey).limit(e.getValue()))               // generate repeating sequence: e.g. ('a',3) -> ['a','a','a']
        .map(String::valueOf)                                                       // map Stream<Character> into Stream<String>
        .collect(Collectors.joining());                                             // perform final joining
}
```
***

### Example 5:
Приклад ефективної конкатенації через `StringBuilder`:
```java
String s1 = Arrays.stream(word1)
    .map(StringBuilder::new)
    .collect(Collectors.joining(""));
```
### Example 6
Приклади групування:
```java
// підрахунок однакових значень
public void groupingAndCounting() throws Exception {
  List<String> names = List.of(
          "John",
          "John",
          "Mariam",
          "Alex",
          "Mohammado",
          "Mohammado",
          "Vincent",
          "Alex",
          "Alex"
  );
  Map<String, Long> map = names.stream()
          .collect(Collectors.groupingBy(
                  Function.identity(),
                  Collectors.counting())
          );
  System.out.println(map);
}
```


# Lambda
* [myWiki - Functional Interface](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Interface.md#functional-interface-функциональный-интерфейс)
* [METANIT.COM - Встроенные функциональные интерфейсы](https://metanit.com/java/tutorial/9.3.php)

> Лямбда выражения можно использовать только когда работаем с функциональным интерфейсом.

## Лямбда в примерах

### Example 1:
* `()` - скобки нужно ставить когда есть несколько аргументов, например так:
```java
runner.run((x, y, z) -> sum(x, y, z))
```
Или когда нет ни одного аргумента:
```java
runner.run(() -> 0)
```
Когда есть всего один параметр, указывать скобки не обязательно.
```java
// lambda
runner.run(x ->  x + 5);
```
```java
// method
runner.run(new Executable() {
            @Override
            public int execute(int x) {
                return x + 5;
            }
        });
```

### Example 2:
`{}` - при описании нескольких действий/операций, нужно ставить точку с запятой `;`.
```java
// lambda
runner.run(() -> {
            System.out.println("Hello");
            System.out.println("Hello - 2");
            return 5;
        });
```
```java
// method
runner.run(new Executable() {
            @Override
            public int execute() {
                System.out.println("Hello");
                System.out.println("Hello - 2");
                return 1;
            }
        });
```

### Example 3:
Нельзя переприсваивать локальную переменную (менять ей значение) до или после лямбд, иначе будет ошибка.
Значение должно быть константной (постоянным).
Variable used in lambda expression should be final or effectively final.
```java
// lambda

// NORMAL:
int a = 1; // или так: final int a = 1;
runner.run((x, y) ->  x + y + a);

// ERROR:
int a = 1;
a = 2; // нельзя переприсваивать до / после
runner.run((x, y) ->  x + y + a);

// ERROR:
int a = 1;
runner.run((x, y) ->  x + y + a);
a = 2; // нельзя переприсваивать до / после
```

### Example 4:
У лямбда выражений нет своего scope (своей области видимости).
Т.е. создавать новые переменные в лямбде с одинаковыми именами переменных метода/класса нельзя - нет своего scope, как в методах.
```java
// lambda

// ERROR:
int a = 1;
runner.run((x, y) ->  {
    int a = 1; // ERROR, "a" уже есть
        return x + y}
    );
```
```java
// method
NORMAL:
        int a = 1;
        runner.run(new Executable() {
@Override
public int execute(int x, int y) {
        int a = 2;
        return x + y;
    }
});
```

### Example 5:
```java
// Пример реализации сортировки лямбдой
list.sort((o1,o2) -> {
    if (o1.length() > o2.length()) {
        return 1;
    } else if (o1.length() < o2.length()) {
        return -1;
    } else {
        return 0;
    }
});
```
```java
// Пример реализации сортировки Comparator'ом
list.sort(new Comparator<String>() {
   @Override
   public int compare(String o1, String o2) {
      if (o1.length() > o2.length()) {
         return 1;
      } else if (o1.length() < o2.length()) {
         return -1;
      } else {
         return 0;
      }
   }
});
```


### Example 6:
```java
// Приклад розбіру collect 
List<String> emails = MockData.getPeople().stream()
    .map(Person::getEmail)
    .collect(
        ArrayList::new,   // () -> new ArrayList<String>(),
        ArrayList::add,   // (strings, e) -> strings.add(e),
        ArrayList::addAll // (strings, c) -> strings.addAll(c)
    );

emails.forEach(System.out::println);
```



## Вопросы
1. __Расскажи про лямбда-выражения (`lambda`).__<br>
   * Лямбда - это блок кода, который можно передать в различные места, исходя из этого он может быть выполнен позже столько раз, сколько потребуется.
   * С помощью лямбд, можно реализовывать метод функционального интерфейса (своего рода реализация анонимного класса). 
   * Лямбды используют в работе со стримами.
   * Появилось в Java 8.


2. __Расскажи про `methods reference`?__<br>
   * Ссылочные методы — это новый полезный синтаксис, созданный чтобы ссылаться на существующие методы или конструкторы Java-классов или объектов через `::`
   * Часто ссылки на методы используются в стримах вместо лямбд (__ссылочные методы быстрее лямбд, но уступают в читаемости__).
   * Ссылки на методы бывают четырех видов:
     * Ссылка на конструктор: `SomeObject obj = SomeObject::new`
     * Ссылка на статический метод: `SomeObject::someStaticMethod`
     * Ссылка на нестатический метод объекта определенного типа: `SomeObject::someMethod`
     * Ссылка на обычный (нестатический) метод конкретного объекта: `obj::someMethod`


3. __Расскажи про стримы (`stream`)__<br>
   * Стримы — это способ обрабатывать структуры данных в функциональном стиле. 
     Это поток данных, который мы обрабатываем как бы работая со всеми данными одновременно, а не перебором, как при `for-each`.
   * Появилось в Java 8.
