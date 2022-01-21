# Collections framework
Коллекции в Java перед тем как сравнить объекты с помощью `equals` всегда ищут/сравнивают их с помощью метода `hashCode`.
И если у одинаковых объектов будут разные `hashCode`, то объекты будут считаться разными — до сравнения с помощью `equals` просто не дойдет.

***

## List
List - это упорядоченный список, здесь допустимы дублирующие значения, объекты хранятся в порядке их добавления в список. 
Доступ к элементам списка осуществляется по индексу.

**Основные реализации:**
* ArrayList
* LinkedList
* Vector
* Stack


### Инициализация
```java
List<String> list = new ArrayList<>(List.of("Ted","Bob","Lex","Liza","Tasha"));
```

Примеры с двойными фигурными скобками `{{}}` означают сначало анонимный класс, и потом блок инициализации.
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
* Сохраняет порядок при добавлении.
* Данный класс эквивалентный классу `Vector`, у которого методы синхронизированы, а у `ArrayList` нет.
* Методы `size`, `isEmpty`, `get`, `set`, `iterator` и `listIterator` выполняются за время `O(1)`.
* Вставка в середину выполняется всегда за `O(n)`.
* Вставка в конец (обычное добавление) в среднем случаи - `O(1)`, в худшем - `O(n)` если нет места в массиве, тогда нужно создать новый массив, и перенести туда все элементы.
* Чтение по индексу - `O(1)`.
* Поиск - `O(n)`, поскольку нужно перебрать `n` элементов до искомого.
* Удаление - `O(n)`, поскольку нужно сдвигать `n` элементов после удаления.



### Принцип работы LinkedList
Класс `LinkedList` реализует два интерфейса - `List` и `Deque`.
В основе лежит двунаправленный связный список, где каждый элемент имеет ссылку на предыдущий и следующий элементы. 
По этим ссылкам можно переходить от одного элемента к другому. При добавлении элемента просто меняются ссылки на предыдущий и следующий элементы. 
Так же, ввиду реализации, данную коллекцию можно использовать как `Stack` или `Queue`.

* При удалении элемента с `LinkedList` метод `remove` обновляет ссылку.
* Вставка и удаление из середины, чтение по индексу - `O(n)`, а из начала и конца - за константное `O(1)` (потому что, `LinkedList` хранит первую и последнюю ссылки).


### Принцип работы Vector
Реализация динамического массива объектов, методы которой синхронизированы.

Если не требуется потокобезопасность - рекомендуется использовать `ArrayList`.

### Принцип работы Stack
Расширяет класс Vector. 
Работает по принципу LIFO (Last-In-First-Out).





## Map interface
* [Обход Map'ы, подсчет количества вхождений подстроки](https://habr.com/ru/company/luxoft/blog/278313/)
```java
Map<String, PersonEmpl> map = new HashMap<>() {{
    put("IT", new PersonEmpl("Ted", 34));
    put("Market", new PersonEmpl("Liza", 27));
    put("Admin", new PersonEmpl("Carl", 30));
}};
```


### Принцип работы HashMap
* [Using a Custom Class as a Key in a Java HashMap](https://www.baeldung.com/java-custom-class-map-key?fbclid=IwAR11QS0x_2Y57f1FsI6fruEedzKgmocB1Xw1LmIts6L67NF0hOtNYHuv8Vg)

Пример работы алгоритма вставки пары ключ-значение:

1. У объекта вызывается метод `158-865-A.hashCode()` для получения хешкода.
2. Проверяется, есть ли существующий ключ с таким хешкодом.
3. Далее производится сравнение всех ключей в списке с методом equals - `158-865-A.equals(key)`.
   1. Первое сходство идентифицируется как уже существующий ключ, и его значение будет заменено на новое.
   2. Если сравнения не выявлено, вставляется новая пара ключ-значение.


***

* [How HashMap works in Java](https://javarevisited.blogspot.com/2011/02/how-hashmap-works-in-java.html?utm_source=dlvr.it&utm_medium=facebook&m=1)

HashMap состоит из «корзин» (bucket`ов). «корзины» — это элементы массива, которые хранят ссылки на списки элементов.

При добавлении новой пары ключ-значение, вычисляет хеш-код ключа, на основании которого вычисляется номер корзины (номер ячейки массива), в которую попадет новый элемент. 
Если корзина пустая, то в нее сохраняется ссылка на вновь добавляемый элемент, если же там уже есть элемент, то происходит последовательный переход по ссылкам между элементами в цепочке, 
в поисках последнего элемента, от которого и ставится ссылка на вновь добавленный элемент. Если в списке был найден элемент с таким же ключом, то он заменяется.

Добавление, поиск и удаление элементов выполняется за константное время. Хеш-функций должны равномерно распределять элементы по корзинам, 
в этом случае временная сложность для этих 3 операций будет не ниже O(log(n)), а в среднем случае как раз константное время.


### Добавление
Пример добавления в мапу из другой коллекции - подсчет одинаковых элементов.

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

### [How to sort HashMap by values in Java 8 - using Lambdas and Stream](https://www.java67.com/2017/07/how-to-sort-map-by-values-in-java-8.html)
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



## Set
`Set` - множество неповторяющихся элементов - каждый элемент хранится только в одном экземпляре.

**Основные реализации:**
* HashSet
* LinkedHashSet
* TreeSet (SortedSet)


### Принцип работы HashSet
Не отсортированная и не упорядоченная коллекция - стоит использовать, когда нужно иметь уникальные объекты и их порядок не важен.

* Использует `HashMap` для хранения данных.
* В качестве ключа и значения используется добавляемый элемент.
* Порядок элементов не гарантируется при добавлении.
* Использует `hashCode` для добавления объектов, сначала сравнение идет по hashCode, а потом по `equals`.
* Базовые операции (вставка, удаление, поиск и размер) выполняются за константное время.


### Принцип работы LinkedHashSet
Упорядоченная версия `HashSet` - использовать, когда требуется упорядоченность при итерации.

* Отличается от `HashSet` только тем, что в основе лежит `LinkedHashMap` вместо `HashMap`.
* Гарантирует, что благодаря двусвязному списку, порядок элементов при обходе коллекции будет идентичен порядку добавления элементов.


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

Подобным образом работает и TreeMap, только возвращен будет ключ со значением. 



## Queue
Отличительная особенность данной коллекции - Очереди обычно, но не обязательно, упорядочивают элементы в `FIFO` (First-In-First-Out). 

**Основные реализации:**
* Однонаправленная очередь PriorityQueue
* Двунаправленная очередь ArrayDeque
* Двусвязный список LinkedList



### Принцип работы PriorityQueue
Особенностью `PriorityQueue` является возможность управления порядком элементов. 
По-умолчанию, элементы сортируются с использованием «natural ordering», но это поведение может быть переопределено при помощи объекта `Comparator`, который задаётся при создании очереди.
- Не поддерживает `null`-значения и non-comparable объекты.
- Размер неограничен, можно задать при создании - увеличивается автоматически.
- Не является потобезопасной! Для этих целей в Java реализован класс `PriorityBlockingQueue`.
- Добавление/удаление элементов происходит за время `O(log(n))`.
- `PriorityQueue.toString` использует `iterator()`, который не гарантирует прохождение элементов приоритетной очереди в каком-либо определенном порядке.

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

### Принцип работы ArrayDeque
Представляет собой реализацию с использованием массивов, подобно `ArrayList`, но не позволяет обращаться к элементам по индексу и хранение `null`.
`ArrayDeque` реализует интерфейс Deque и определяет поведение двунаправленной очереди, которая работает как обычная однонаправленная очередь, либо как стек, 
действующий по принципу `LIFO` (последний вошел - первый вышел).

> DOC: Коллекция работает быстрее чем Stack, если используется как LIFO коллекция, а также быстрее чем LinkedList, если используется как FIFO.
- Методы позволяют обращаться как к голове, так и к хвосту: `addFirst`/`addLast`, `getFirst`/`getLast`, `offerFirst`/`offerLast`...




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

6. **Зачем нужен HashMap, если есть HashTable?**

HashTable - это устаревший класс и его использование не рекомендовано.

- Методы класса HashTable синхронизированы, что приводит к снижению производительности, а HashMap - нет;
- HashTable не может содержать элементы `null`, тогда как HashMap может содержать один ключ `null` и любое количество значений `null`;
- `Iterator` у HashMap, в отличие от `Enumeration` у HashTable, работает по принципу `fail-fast` (выдает исключение при любой несогласованности данных).

***

7. **Какое начальное количество корзин в HashMap?**

По умолчанию 16. 
Используя конструкторы с параметрами можно задавать свое начальное количество корзин.

***





