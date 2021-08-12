# Array
All about arrays - `[]`.

## 💡 Notes

#### Как эффективно скопировать массив?
Часто можно встретить в коде ручное копирование массивов с использованием циклов.
Однако существует метод `System.arraycopy`, который выполнит копирование гораздо быстрее.

***

#### Как отсортировать массив в обратном порядке?
С помощью метода `Arrays.sort(someArray)`, и на вход этому методу передать `Collections.reverseOrder()` как второй параметр.

Кроме прямой и обратной сортировки, бывает, возникает необходимость отсортировать массив строк независимо от регистра. Это легко сделать, передав `String.CASE_INSENSITIVE_ORDER` как второй параметр в Arrays.sort.

`Collections.sort`, к сожалению, позволяет отсортировать только реализации `List`.

***

#### По какому алгоритму сортирует Java?
В Java для простейших типов используется `quick sort`, а для объектов — `stable merge`.

***

#### Что делать, если у нас есть массив, а метод принимает Iterable?
Предлагаю теперь перейти к такому вопросу, как передача массива в метод, требующий `Iterable`. Напомню, что `Iterable` — это интерфейс, который содержит метод `iterator()`, который должен возвращать `Iterator`.

Единственный выход в этой ситуации — преобразовать массив в коллекцию и уже её подать на вход такому методу.

***

#### How to convert int[] into List<Integer> in Java?
There is no shortcut for converting from `int[]` to `List<Integer>` as `Arrays.asList` does not deal with boxing and will just create a `List<int[]>` which is not what you want. You have to make a utility method.

All values of primitive types are stored in stack memory, but variables of reference types store addresses on objects located in heap memory.

Source: [stackoverflow](https://stackoverflow.com/questions/1073919/how-to-convert-int-into-listinteger-in-java)

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
  int [] arr2 = Arrays.copyOf(arr, 5);
  ```
  Если элементы не поместились (длина меньше длины существующего массива), то лишние значения игнорируются. 
  Если длина нового массива больше длины старого, ячейки заполняются нулями.
- Скопировать нужную часть массива "с / по индекс": `int [] arr2 = Arrays.copyOfRange(arr, 5, 10);` 
  ```java
  int [] x2 = Arrays.copyOfRange(x, 5, 10);
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

### Inner sort by `Comparator`
Пример двух-уровневой сортировки с `Comparator`, сначало по элементам с первым индексом, потом с нулевым:
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
