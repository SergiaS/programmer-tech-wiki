# Array
All about arrays - `[]` in **Java**.

## 💡 Notes

### Як ефективно скопіювати масив?
Часто можна зустріти в коді ручне копіювання масивів з використанням циклів.
Однак існує метод `System.arraycopy`, котрий виконає копіювання значно швидше.

***

### Як відсортувати двовимірний масив?
Для цього потрібно написати компаратор як другий аргумент:
```java
// Сортування за першим індексом:
Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));

// Сортування матрьошки:
int[][] envelopes = new int[][]{{6,2},{6,4},{6,7},{6,3},{6,1},{6,5}}ж
Arrays.sort(envelopes, (a,b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
// результат: [[6, 1], [6, 2], [6, 3], [6, 4], [6, 5], [6, 7]]
```

Ще варіант сортування з різницею від більшої до меншої:
```java
int[][] arr = new int[][]{{10,16},{2,8},{1,6},{7,12}};

Arrays.sort(points, (a, b) -> (a[1] - a[0]) != (b[1] - b[0])
    ? (a[1] - a[0]) - (b[1] - b[0])
    : a[1] - b[1]);
// результат: [[1, 6], [7, 12], [2, 8], [10, 16]]
```

***

### Как отсортировать массив в обратном порядке?
С помощью метода `Arrays.sort(T[] a, Comparator<? super T> c)`, и на вход этому методу передать `Collections.reverseOrder()` как второй параметр.

Кроме прямой и обратной сортировки, бывает, возникает необходимость отсортировать массив строк независимо от регистра. 
Это легко сделать, передав `String.CASE_INSENSITIVE_ORDER` как второй параметр в Arrays.sort.

`Collections.sort`, к сожалению, позволяет отсортировать только реализации `List`.

```java
// сортування Integer у зворотному порядку:
Arrays.sort(nums, Collections.reverseOrder());
// сортування String у зворотному порядку:
Arrays.sort(strs, String.CASE_INSENSITIVE_ORDER);
// сортування String у зворотному порядку незалежно від регістру:
Arrays.sort(strs, Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
```

***

### По какому алгоритму сортирует Java?
В Java для простейших типов используется `quick sort`, а для объектов — `stable merge`.

***

### Что делать, если у нас есть массив, а метод принимает Iterable?
Предлагаю теперь перейти к такому вопросу, как передача массива в метод, требующий `Iterable`. Напомню, что `Iterable` — это интерфейс, который содержит метод `iterator()`, который должен возвращать `Iterator`.

Единственный выход в этой ситуации — преобразовать массив в коллекцию и уже её подать на вход такому методу.

***

### How to convert int[] into List<Integer> in Java?
* Source: [stackoverflow](https://stackoverflow.com/questions/1073919/how-to-convert-int-into-listinteger-in-java)

There is no shortcut for converting from `int[]` to `List<Integer>` as `Arrays.asList` does not deal with boxing and will just create a `List<int[]>` which is not what you want. You have to make a utility method.

All values of primitive types are stored in stack memory, but variables of reference types store addresses on objects located in heap memory.

One of the possible solution is use `Stream`:
```java
int[] nums = {1,2,3};

List<Integer> list = Arrays.stream(nums)
    .sorted()
    .boxed()
    .collect(Collectors.toList());
```

***




## Arrays class
Полезные методы для работы с массивами:
- Сортировка массива по возростанию: `Arrays.sort(arr);`
- Бинарный поиск - работает только в отсортировоном массиве. Если выдаёт некорректный результат, значит такого значения нет, и рассчитывается оно как = на каком бы месте было ищещуеся значение, далее ставим ему минус и к этому значению ещё -1: `Arrays.binarySearch(arr, 0);`
- Отображение содержимого
  - ОДНОМЕРНОГО массива в строку без перебора: `Arrays.toString(arr);` 
  - Для МНОГОМЕРНЫХ - `Arrays.deepToString(array);`
- Преобразование массива в лист: `Arrays.asList();` - фиксированный размер листа, `new ArrayList<Integer>(Arrays.asList(sourceArray));` - расширяемый.
- Сравнение массивов делаем так: `Arrays.compare(arr1, arr2);` Если первый массив меньше второго (идёт раньше в словаре) возвращает значение меньше 0; если равны - 0; если первый больше второго - больше 0.
- Сравнить 
  - ОДНОМЕРНЫЕ массивы по их содержанию: `Arrays.equals(arr1, arr2);` 
  - МНОГОМЕРНЫЕ: `Arrays.deepEquals(arr1, arr2);`
- Заполнить массив одинаковыми значениями: `Arrays.fill(arr, значение);` Еще можно заполнить определенным значением часть массива: `Arrays.fill(arr, с_индеса, по_индекс, значение);`
- Скопировать массив: `Arrays.copyOf(arr, длина_массива);` 
  ```java
  int[] arr2 = Arrays.copyOf(arr, 5);
  ```
  Если элементы не поместились (длина меньше длины существующего массива), то лишние значения игнорируются. 
  Если длина нового массива больше длины старого, ячейки заполняются нулями.
- Скопировать нужную часть массива "с / по индекс": `int [] arr2 = Arrays.copyOfRange(arr, 5, 10);` 
  ```java
  int[] x2 = Arrays.copyOfRange(x, 5, 10);
  ```
- Находит индекс первого расхождения в массивах метод `Arrays.mistmatch(arr1, arr2);` Если массивы одинаковые, метод возвращает -1; Если разные - индекс первого расхождения.
- Нельзя указывать размер массива одновременно явно ([1] - explicity) и неявно (`new Object() - implicity`): 
  ```java
  new Object[1]{new Object()}; // error
  ```
- Пример записи сложно читаемого массива:
  ```java
  String arr[] = new String[][]{new String[]{"privet","poka","ok"},{new String()},{null}}[2];
  ```
  т.е. из массива `new String[][]{new String[]{"privet","poka","ok"},{new String()},{null}}` присваем второй элемент `[2]` для `arr[]`, т.е. `null`.


## Convert List to Array
```java
Integer[] leftSubArr = list.toArray(new Integer[0]);
```
```java
LinkedList<int[]> ans = new LinkedList<>();
// ...
ans.toArray(new int[ans.size()][]);
```

## Sort

### Method `sort` with `Comparator`
This example sort array values and return indices.
```java
Integer[] idx = { 0, 1, 2, 3 };
float[] data = { 1.7f, -0.3f,  2.1f,  0.5f };

Arrays.sort(idx, new Comparator<Integer>() {
    @Override public int compare(final Integer o1, final Integer o2) {
        return Float.compare(data[o1], data[o2]);
    }
});

// [1, 3, 0, 2]
```

### Sorting 2d Arrays
```java
int[][] slots={{3,7},{1,2},{9,15},{6,8}};
Arrays.sort(slots, (a,b)->  a[0]-b[0]);
for(int[] slot : slots){
    System.out.println(slot[0]+" "+slot[1]);
}
// SOUT: 1 2, 3 7, 6 8, 9 15
```


### Inner sort by `Comparator`
Пример двух-уровневой сортировки с `Comparator`, с начало по элементам с первым индексом, потом с нулевым:
```java
Queue<int[]> values = new PriorityQueue<>(new Comparator<int[]>() {
    @Override
    public int compare(int[] o1, int[] o2) {
        int firstCompare = Integer.compare(o1[1], o2[1]);
        if (firstCompare == 0) {
            return Integer.compare(o1[0], o2[0]);
        }
        return firstCompare;
    }
});
```

### Пример переворачивания элементов стримом
На вход приходит `String[]`, нужно отсеять пустые элементы, перевернуть и соединить все элементы через пробел.

> Внимание на ПЕРЕВЕРНУТЬ массив, а не отсортировать в обратном порядке - методы `Comparator` не подходят, т.к. сначала будет проведена сама сортировка по алфавиту, а потом произведен обратный порядок.
> Нужно только зеркальное отражение элементов.

Для отзеркаливания элементов у класса `Arrays` нет метода, часто используют метод `Collections.reverse()`, но на вход нужен `List`!
Поэтому с начало конвертируем поток в `List`, применяем метод `Collections.reverse()`, и дальше можно конвертировать во что нужно.

```java
return Stream.of(s.split(" ")) // Make a stream of words
          .filter(s1 -> !s1.isEmpty()) // filter empty strings
          .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
              Collections.reverse(list);
              return list.stream();
          })) // reverse the words
          .collect(Collectors.joining(" ")); // join all the words into a string
```
