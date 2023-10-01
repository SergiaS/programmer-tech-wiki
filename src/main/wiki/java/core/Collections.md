# Collections framework

* [Big-O summary for Java Collections Framework](https://stackoverflow.com/a/559862)

***

* [List](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#list)
* [Set](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#set)
* [Queue](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#queue)
* [Map](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#map-interface)

> Коллекции в Java перед тем как сравнить объекты с помощью `equals` всегда ищут/сравнивают их с помощью метода `hashCode`.
> И если у одинаковых объектов будут разные `hashCode`, то объекты будут считаться разными — до сравнения с помощью `equals` просто не дойдет.

> В коллекциях хранятся не объекты, а лишь ссылки на эти объекты. А у всех ссылок размер один и тот же, и он известен.

***

## List
`List` - это упорядоченный список, здесь допустимы дублирующие значения, объекты хранятся в порядке их добавления в список.

Доступ к элементам списка осуществляется по индексу.

**Основные реализации:**
* [ArrayList](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#принцип-работы-arraylist)
* [LinkedList](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#принцип-работы-linkedlist)
* [Vector](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#принцип-работы-vector)
* [Stack](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#принцип-работы-stack)


### Инициализация
```java
List<String> list = new ArrayList<>(List.of("Ted","Bob","Lex","Liza","Tasha"));
```

Примеры с двойными фигурными скобками `{{}}` означают сначала анонимный класс, и потом блок инициализации.
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

### Принцип работы ArrayList
`ArrayList` - это динамически расширяемый массив. Он упорядочен.

Следует применять, когда предполагается частое обращение к элементам по индексу - `O(1)`.
Но данную коллекцию рекомендуется избегать, если требуется частое удаление/добавление элементов в середину коллекции.

* При создании пустого `ArrayList` в памяти выделяется место под массив на 10 элементов.
* Все элементы листа хранятся в памяти подряд. 
* По умолчанию, когда заканчивается выделенная под элементы память, `ArrayList` увеличивает свой размер на половину.
* Сохраняет порядок при добавлении.
* Данный класс эквивалентный классу `Vector`, у которого методы синхронизированы, а у `ArrayList` нет.
* Методы `size`, `isEmpty`, `get`, `set`, `iterator` и `listIterator` выполняются за время `O(1)`.
* Вставка в середину выполняется всегда за `O(n)`.
* Вставка в конец (обычное добавление) в среднем случаи - `O(1)`, в худшем - `O(n)` если нет места в массиве, тогда нужно создать новый массив, и перенести туда все элементы.
* Чтение по индексу - `O(1)`.
* Поиск - `O(1)` если первый, и `O(n)` если нужно перебрать `n` элементов до искомого.
* Удаление - `O(1)` если последний, и `O(n)` если нужно сдвигать `n` элементов после удаления.

#### Особенности ArrayList
- Пустой лист создается на 10 элементов по умолчанию - выделяется под память. Но заполнять лист следует с нулевого индекса! При таком коде будет ошибка:
  ```java
  ArrayList<String> list = new ArrayList<>(); // на этой строке длина листа равна 0
  list.add(1, "ok"); // здесь ложем объект в лист под индексом 1 в лист с размером 0 - будет IndexOutOfBoundsException
  ```
- Метод `add(1, "ok")` добавит "ok" в лист под индексом 1, при этом другие элементы просто сдвинутся.
- Метод `set()` работает как `replace()`. Им можно заменять значения элементов по индексу.
- Метод `remove()`, можно удалять по индексу объекта, так и по значению объекта. Если у объекта не переопределен метод `equals`, метод `remove` будет удалять объекты сравнивая их по значению, а не по ссылке.
- Методы `indexOf()` и `lastIndexOf()` сравнивают значения через `equals`.
- Преобразование массива в лист: `Arrays.asList();`
- Для сортировки листа используем метод `sort()` класса `Collections`: `Collections.sort(list);`
- Метод `equals()` в листе перезаписан.
- Метод `removeAll()` удаляет элементы в первом листе, которые указанны во втором листе - `list1.removeAll(list2);`
- Метод `retainAll()` сохраняет только те элементы, которые есть в первом и втором листе - `list1.retainAll(list2);`
- Метод `containsAll()` возвращает `boolean`, если все значения есть `true`, иначе `false` - `list1.containsAll(list2);`
- Метод `subList()` возвращает часть листа (с индекса, по индекс): `list2 = list1.subList(1, 4);`. 
 `subList()` даёт представление листа (ссылки объектов), а не его копию! Поэтому оригинальный лист можно изменить манипулирую листом2.
- Методы `List.of()` и `List.copyOf()` позволяют быстро инициализировать лист: `List<String> list = List.of("one","two","three");`.
 Но после этого лист изменять нельзя (add/set/remove)!!! Также не могут содержать значение `null`.
- В коллекции хранятся не объекты, а лишь ссылки на эти объекты. А у всех ссылок размер один и тот же, и он известен.


### Принцип работы LinkedList
Класс `LinkedList` реализует два интерфейса - `List` и `Deque`.
В основе лежит двунаправленный связный список, где каждый элемент имеет ссылку на предыдущий и следующий элементы. 
По этим ссылкам можно переходить от одного элемента к другому. При добавлении элемента просто меняются ссылки на предыдущий и следующий элементы. 
Так же, ввиду реализации, данную коллекцию можно использовать как `Stack` или `Queue`.

* При удалении элемента с `LinkedList` метод `remove` обновляет ссылку.
* Вставка и удаление из середины, чтение по индексу - `O(n)`, а из начала и конца - за константное `O(1)` (потому что, `LinkedList` хранит первую и последнюю ссылки).
* Хранит информацию нодами - использует внутри статический внутренний класс Node, который хранит значение, и ссылки на предыдущий и следующий элементы.

### Принцип работы Vector
Реализация динамического массива объектов, методы которой синхронизированы.
Если не требуется потокобезопасность - рекомендуется использовать `ArrayList`.

* По умолчанию, `Vector` удваивает свой размер, когда заканчивается выделенная под элементы память.

### Принцип работы Stack
Расширяет класс Vector. 
Работает по принципу LIFO (Last-In-First-Out).



## Set
`Set` - множество неповторяющихся элементов - каждый элемент хранится только в одном экземпляре.

**Основные реализации:**
* [HashSet](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#принцип-работы-hashset)
* [LinkedHashSet](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#принцип-работы-linkedhashset)
* [TreeSet (SortedSet)](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#принцип-работы-treeset)


### Принцип работы HashSet
Не отсортированная и не упорядоченная коллекция - стоит использовать, когда нужно иметь уникальные объекты и их порядок не важен.

* Использует `HashMap` для хранения данных - обеспечивает временную сложность выполнения операций аналогично `HashMap`.
* В качестве ключа и значения используется добавляемый элемент.
* Порядок элементов не гарантируется при добавлении.
* Использует `hashCode` для добавления объектов, сначала сравнение идет по `hashCode`, а потом по `equals`.
* Базовые операции (вставка, удаление, поиск и размер) выполняются за среднее время `O(1)`, а за худшее - `O(n)`.


### Принцип работы LinkedHashSet
Упорядоченная версия `HashSet` - использовать, когда требуется упорядоченность при итерации.

* Отличается от `HashSet` только тем, что в основе лежит `LinkedHashMap` вместо `HashMap`.
* Гарантирует, что благодаря двусвязному списку, порядок элементов при обходе коллекции будет идентичен порядку добавления элементов.
* Базовые операции (вставка, удаление, поиск и размер) выполняются за среднее время `O(1)`, а за худшее - `O(n)`.

### Принцип работы TreeSet
По умолчанию сортирует коллекцию в возврастающем порядке, можно передать в конструктор свой компаратор.

У класса `TreeSet` присутствуют свои уникальные методы, такие как `ceiling` (возврат равного или следующего большого ключа) и `floor` (возврат равного или следующего меньшего ключа):
```java
TreeSet<Integer> set = new TreeSet<>();
set.add(2);
set.add(8);
set.add(3);
set.add(9);
set.add(4);

System.out.println(set);            // [2, 3, 4, 8, 9]
System.out.println(set.ceiling(7)); // 8
System.out.println(set.ceiling(8)); // 8
System.out.println(set.floor(8));   // 8
System.out.println(set.floor(7));   // 4
```

* Подобным образом работает и TreeMap, только возвращен будет ключ со значением. 
* Операции `put()`, `contains()` и `remove()` выполняются за худшее и лучшее время `O(log(n))`.


## Queue
Отличительная особенность данной коллекции - очереди обычно, но не обязательно, упорядочивают элементы в `FIFO` (First-In-First-Out). 

**Основные реализации:**
* [Однонаправленная очередь PriorityQueue](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#принцип-работы-priorityqueue)
* [Двунаправленная очередь ArrayDeque](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#принцип-работы-arraydeque)
* [Двусвязный список LinkedList](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#принцип-работы-linkedlist)

### Принцип работы PriorityQueue
Особенностью `PriorityQueue` является возможность управления порядком элементов. 
По-умолчанию, элементы сортируются с использованием «natural ordering», но это поведение может быть переопределено при помощи объекта `Comparator`, который задаётся при создании очереди.

> При просмотре всех элементов может быть не соответствие натуральной сортировке. Но при вызове одиночных элементов всё будет ок!

* Не поддерживает `null`-значения и non-comparable объекты.
* Размер неограничен, можно задать при создании - увеличивается автоматически.
* Не является потобезопасной! Для этих целей в Java реализован класс `PriorityBlockingQueue`.
* Добавление/удаление элементов происходит за время `O(log(n))`.
* `PriorityQueue.toString` использует `iterator()`, который не гарантирует прохождение элементов приоритетной очереди в каком-либо определенном порядке.

[451. Sort Characters By Frequency](https://leetcode.com/problems/sort-characters-by-frequency/discuss/1534282/Simple-Solution-using-Heap):
```java
class Solution {
    public String frequencySort(String s) {
        int[] f = new int[128];
        for(char c : s.toCharArray())
            f[c]++;
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        for(int i = 0; i < f.length; i++)
            pq.offer(new int[] {i, f[i]});
        
        StringBuilder sb = new StringBuilder();
        while(pq.peek()[1] != 0) {
            int[] pair = pq.poll();
            int count = pair[1];
            while(count-- > 0)
                sb.append((char) pair[0]);
        }
        return sb.toString();
    }
}
```
***
[692. Top K Frequent Words](https://leetcode.com/problems/sort-characters-by-frequency/discuss/1534282/Simple-Solution-using-Heap):
```java
public class Note1 {
  public static void main(String[] args) throws IOException {
    Note1 n = new Note1();
    System.out.println(n.topKFrequent(new String[]{"i","love","leetcode","i","love","coding"}, 2));
    System.out.println(n.topKFrequent(new String[]{"c","b","c","a","b","c","a","a","c"}, 2));
  }

  public List<String> topKFrequent(String[] words, int k) {
    Map<String, Integer> map = new HashMap<>();
    for (String word : words) {
      map.put(word, map.getOrDefault(word, 0) + 1);
    }

    PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
        (a, b) -> a.getValue() == b.getValue() ? b.getKey().compareTo(a.getKey()) : a.getValue() - b.getValue()
    );

    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      pq.offer(entry);
      if (pq.size() > k)
        pq.poll();
    }

    List<String> result = new LinkedList<>();
    while (!pq.isEmpty())
      result.add(0, pq.poll().getKey());

    return result;
  }
}
```

### Принцип работы ArrayDeque
Представляет собой реализацию с использованием массивов, подобно `ArrayList`, но не позволяет обращаться к элементам по индексу и хранение `null`.
`ArrayDeque` реализует интерфейс Deque и определяет поведение двунаправленной очереди, которая работает как обычная однонаправленная очередь, либо как стек, 
действующий по принципу `LIFO` (последний вошел - первый вышел).

> DOC: Коллекция работает быстрее чем Stack, если используется как LIFO коллекция, а также быстрее чем LinkedList, если используется как FIFO.
- Методы позволяют обращаться как к голове, так и к хвосту: `addFirst`/`addLast`, `getFirst`/`getLast`, `offerFirst`/`offerLast`...
- Time Complexity - Most ArrayDeque operations run in amortized constant time. 
  Amortized constant time operation means most of the time the operation cost will `O(1)`, except possibly in some cases, for eg. when the ArrayDeque needs to be resized:
  - `O(1)` для методів `offer`, `peak`, `poll`, `size`, `remove`.  


## Map interface
* [Приклад використання Мапи](https://leetcode.com/problems/split-array-into-consecutive-subsequences/discuss/106496/Java-O(n)-Time-O(n)-Space)

**Map** - это отдельная коллекция во главе которой используется интерфейс Map. 
Главное отличие - сохранение ключа со значением.
Повторяющиеся ключи не допустимы, будут перезатерты их значения.

**Основные реализации:**
* [HashMap](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#принцип-работы-hashmap)
* [LinkedHashMap](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#принцип-работы-linkedhashmap)
* [TreeMap](https://github.com/SergiaS/programmer-tech-wiki/blob/master/src/main/wiki/java/core/Collections.md#принцип-работы-treemap)


### Принцип работы HashMap
> Очень важно использовать в качестве ключа - Immutable объекты (можно например поставить полям и классу final), т.к. после изменения объекта, меняется его hashcode:
> ```java
> Map<Student, Double> map = new HashMap<>();
> Student st1 = new Student("Bob", "Singler", 3);
> map.put(st1, 7.5);
> System.out.println(map.containsKey(st1));   // true
> System.out.println(st1.hashCode());         // 635006189
> st1.course = 4;                             // меняем значение
> System.out.println(map.containsKey(st1));   // 635006190
> System.out.println(st1.hashCode());         // false
> ```

Позволяет использовать `null` в качестве значения или ключа и не является упорядоченной.

***

#### [Обход Map'ы, подсчет количества вхождений подстроки](https://habr.com/ru/company/luxoft/blog/278313/)
```java
Map<String, PersonEmpl> map = new HashMap<>() {{
    put("IT", new PersonEmpl("Ted", 34));
    put("Market", new PersonEmpl("Liza", 27));
    put("Admin", new PersonEmpl("Carl", 30));
}};
```

***

#### [Using a Custom Class as a Key in a Java HashMap](https://www.baeldung.com/java-custom-class-map-key?fbclid=IwAR11QS0x_2Y57f1FsI6fruEedzKgmocB1Xw1LmIts6L67NF0hOtNYHuv8Vg)

Пример работы алгоритма вставки пары ключ-значение:

1. У объекта вызывается метод `158-865-A.hashCode()` для получения хеш-кода.
2. Проверяется, есть ли существующий ключ с таким хеш-кодом.
3. Далее производится сравнение всех ключей в списке с методом equals - `158-865-A.equals(key)`.
   1. Первое сходство идентифицируется как уже существующий ключ, и его значение будет заменено на новое.
   2. Если сравнения не выявлено, вставляется новая пара ключ-значение.

***

#### [How HashMap works in Java](https://javarevisited.blogspot.com/2011/02/how-hashmap-works-in-java.html?utm_source=dlvr.it&utm_medium=facebook&m=1)

HashMap состоит из «корзин» (bucket`ов). «корзины» — это элементы массива, которые хранят ссылки на списки элементов (или дерева, в зависимости от заполненности бакета).

При добавлении новой пары ключ-значение, вычисляет хеш-код ключа, на основании которого вычисляется номер корзины (номер ячейки массива), в которую попадет новый элемент.
Если корзина пустая, то в нее сохраняется ссылка на вновь добавляемый элемент, если же там уже есть элемент, то происходит последовательный переход по ссылкам между элементами в цепочке,
в поисках последнего элемента, от которого и ставится ссылка на вновь добавленный элемент. Если в списке был найден элемент с таким же ключом, то он заменяется.

* Добавление, поиск и удаление элементов выполняется за константное время `O(1)`.
* Хеш-функции должны равномерно распределять элементы по корзинам, в этом случае временная сложность для этих 3 операций будет не ниже `O(n)`, 
  а в среднем случае как раз константное время `O(1)`.

***

#### Добавление
Пример добавления в мапу из другой коллекции - подсчет одинаковых элементов.
```java
Map<Integer, Integer> map = new HashMap<>();
for (int n : array) {
    map.put(n, map.getOrDefault(n, 0) + 1);
}
```

Хорошо подходит для заполнения мапы в цикле, здесь нет необходимости использовать методы `computeIfPresent()`, `putIfAbsent()`...

***

#### Сортировка по значению в обратном порядке
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

***

#### [Пример сортировки HashMap](https://mishrasuraj.medium.com/sorting-hashmap-in-java-8-6fadc1cacb2b)
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

#### [How to sort HashMap by values in Java 8 - using Lambdas and Stream](https://www.java67.com/2017/07/how-to-sort-map-by-values-in-java-8.html)

Интересный момент - для сборки мапы и сохранения порядка (после сортировки) нужно использовать `LinkedHashMap`, - в противном случае порядок не сохранится!
```java
// wrong way
// now, let's collect the sorted entries in Map
Map<String, Integer> sortedByPrice = ItemToPrice.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue())
        .collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));
```
> The `Map` returned by the previous statement was not sorted because the order was lost while collecting results in Map you need to use the `LinkedHashMap` to preserve the order
```java
// right way
Map<String, Integer> sortedByValue=ItemToPrice.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue())
        .collect(toMap(Map.Entry::getKey,
                       Map.Entry::getValue,(e1,e2)->e1,LinkedHashMap::new));
```
This is the right way to sort a `Map` by values in Java 8 because now the ordering will not be lost as `Collector` is using `LinkedHashMap` to store entries.


#### Top K Frequent Words
```java
public List<String> topKFrequent(String[] words, int k) {
  Map<String, Integer> map = new HashMap<>();
  for (String word : words) {
    map.put(word, map.getOrDefault(word, 0) + 1);
  }

  Map<String, Integer> sortedMap = map.entrySet()
      .stream()
      .sorted(Map.Entry.<String, Integer>comparingByValue()
          .reversed().thenComparing(Map.Entry.comparingByKey()))
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
          (e1, e2) -> e1, LinkedHashMap::new));

  List<String> result = new ArrayList<>();
  for (String s : sortedMap.keySet()) {
    result.add(s);
    if (--k == 0) break;
  }

  return result;
}
```



### Принцип работы LinkedHashMap
Упорядоченная реализация хэш-таблицы.

* Поддерживает порядок вставки.
* Медленнее, чем `HashMap`.
* Ожидается, что итерация быстрее, чем в `HashMap`.


### Принцип работы TreeMap
Реализация, основанная на красно-чёрных деревьях.
Обеспечивает хранение элементов множества ключей в порядке возрастания или сортировка по Компаратору.

* DOC: время выполнения `O(log(n))` для методов `containsKey`, `get`, `put` и `remove`.
* Данная имплементация не синхронизирована. Для этих целей есть статический класс `SynchronizedSortedMap` в классе `Collections`.

### Методы Map
- Метод `computeIfPresent()` позволяет редактировать значение, если такой ключ есть:
  ```java
  balloon.computeIfPresent(text.charAt(i), (k, v) -> v + 1);
  ```
  раньше писал так:
  ```java
  if (balloon.containsKey(text.charAt(i))) {
      balloon.put(text.charAt(i), balloon.get(text.charAt(i)) + 1);
  }
  ```
- `computeIfAbsent()` позволяет редактировать значение, если ключа нет.
- `remove()` позволяет удалить из `Map` пару, если совпадает и ключ, и значение:
  ```java
  map.remove("surname", "Vasichkin");
  ```
  можно также удалить только по ключу: 
  ```java
  map.remove("surname");
  ```
- Метод `merge()` позволяет объединить значение в `Map` с другим, если такой пары в `Map` нет, она будет создана:
  ```java
  map.put("question", "Bla?");
  map.merge("question", " Blabla", (oldVal, newVal) -> oldVal + newVal);
  System.out.println(map.get("question")); // Bla? Blabla
  ```



## Обход коллекции

### Цикл foreach
- Невозможно изменить значения элементов массива примитивного типа данных.
- Возможно изменить значения элементов (значения объектов) массива ссылочного типа данных, но заменить элемент невозможно.
- С помощью цикла `foreach` нельзя проводить динамическую инициализацию массива!
- Внутри цикла `foreach` можно работать только с элементами одного массива.
- Цикл `foreach` не может изменять значения у `String` и `StringBuilder`, а `for` может:
```java
for (StringBuilder sb : array) {
    sb = new StringBuilder("hello"); // значения изменены НЕ будут! 
} 

for (int i = 0; i < array.length; i++) {
    array[i] = new StringBuilder("hello"); // будут другие значения! 
}
```
- С помощью методов можно менять само значение объектов, например в `StringBuilder` использовать `append()`.

### Iterator
Итератор - это повторитель. Пример записи (аналогично и `ListIterator`):
```java
Iterator<String> it = list.iterator();
while(it.hasNext()){
    System.out.println(it.next());
}
```
Отличие от `foreach` заключается в том, что здесь можно удалить элемент листа.

## Interview questions
***

1. **Какова роль `equals` и `hashCode` в HashMap?**

`hashCode` позволяет определить корзину для поиска элемента, а `equals` используется для сравнения ключей элементов в списке внутри корзины и искомого ключа.

***

2. **Как и когда происходит увеличение количества корзин в HashMap?**

Помимо `capacity` в HashMap есть еще параметр `loadFactor`, на основании которого, вычисляется предельное количество занятых корзин (`capacity * loadFactor`).
По умолчанию `loadFactor = 0,75`. По достижению предельного значения, число корзин увеличивается в 2 раза. 
Для всех хранимых элементов вычисляется новое «местоположение» с учетом нового числа корзин.

***

3. **Почему нельзя использовать массивы в качестве ключа в HashMap?**

Потому что хеш-код массива вычисляется при его создании и не зависит от кол-ва элемента в нем (метод вычисления хэш-кода массива не переопределен и 
вычисляется по стандартному `Object.hashCode` на основании адреса массива).

Так же у массивов не переопределен `equals` и выполняется сравнение указателей.
Это приводит к тому, что обратиться к сохраненному с ключом-массивом элементу не получится при использовании другого массива такого же размера и с такими же элементами, 
доступ можно осуществить лишь в одном случае — при использовании той же самой ссылки на массив, что использовалась для сохранения элемента.

***

4. **Сколько переходов происходит в момент вызова `HashMap.get(key)` по ключу, который есть в таблице?**

Если ключ равен `null`: 1 - выполняется единственный метод `getForNullKey()`.
Любой ключ отличный от `null`: 4 - вычисление хэш-кода ключа; определение номера корзины; поиск значения; возврат значения.

***

5. **Сколько создается новых объектов, когда вы добавляете новый элемент в HashMap?**

Один новый объект статического вложенного класса `Entry<K,V>`.

***

6. **Навіщо потрібен `HashMap`, якщо є `Hashtable`?**

`Hashtable` - це застарілий клас та його використання не рекомендовано.

- Методи класу `HashTable` синхронізовані, що призводить до зниження продуктивності, а `HashMap` - ні;
- `Hashtable` не може містити ключи та елементи `null`, тоді як HashMap може містити один ключ `null` і будь-яку кількість значень `null`;
- `Iterator` у HashMap, на відміну від `Enumeration` у HashTable, працює за принципом `fail-fast` (видає виключення за будь-якої неузгодженості даних).


> <details>
> <summary>Приклад fail-fast з HashMap і fail-save з ConcurrentHashMap</summary>
> 
> Для перевірки **fail-save** просто зміни у двох місцях `HashMap` на `ConcurrentHashMap`.
> ```java
> // приклад fail-fast ітератора з HashMap
> public static void main(String[] args) throws InterruptedException {
>   HashMap<Integer, String> map = new HashMap<>();
>   map.put(1, "Bob");
>   map.put(2, "Carl");
>   map.put(3, "Mina");
>   map.put(4, "Chuck");
>   map.put(5, "Moris");
>   System.out.println(map);
> 
>   Runnable runnable1 = () -> {
>     Iterator<Integer> iterator = map.keySet().iterator();
>     while (iterator.hasNext()) {
>       try {
>         Thread.sleep(100);
>       } catch (InterruptedException e) {
>         throw new RuntimeException(e);
>       }
>       Integer i = iterator.next();
>       System.out.println(i + " : " + map.get(i));
>     }
>   };
> 
>   Runnable runnable2 = () -> {
>     try {
>       Thread.sleep(300);
>     } catch (InterruptedException e) {
>       e.printStackTrace();
>     }
>     map.put(6, "Jina");
>   };
> 
>   Thread t1 = new Thread(runnable1);
>   Thread t2 = new Thread(runnable2);
>   t1.start();
>   t2.start();
>   t1.join();
>   t2.join();
> 
>   System.out.println(map);
> }    
> ```
> </details>


***

7. **Какое начальное количество корзин в HashMap?**

По умолчанию 16. 
Используя конструкторы с параметрами можно задавать свое начальное количество корзин.

***





