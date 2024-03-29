# Algorithms ⚠️
* [How to swap two numbers without using a temporary variable?](https://www.geeksforgeeks.org/swap-two-numbers-without-using-temporary-variable/)

***

**In-place algorithm**. Не использовать (создавать) вспомогательные структуры данных - 
значит нужно решать через замену и обменивания элементов.

***

Очереди относятся к категории FIFO («первым вошел, первым вышел»), а стек - к категории LIFO («последним пришел, первым вышел»).

***

DFS использует стек, а BFS — очередь.

***

Деревья составляют подкатегорию графов, поэтому любое дерево является графом, но граф необязательно является деревом.

***

В предыдущей главе рассматривался поиск в ширину.
Этот алгоритм находит путь с минимальным количеством сегментов (граф на первом рисунке).
А если вы захотите найти самый быстрый путь (второй граф)?
Быстрее всего это делается при помощи другого алгоритма, который называется __алгоритмом Дейкстры__ - состоит из четырех шагов:
1. Найти узел с наименьшей стоимостью (то есть узел, до которого можно добраться за минимальное время).
2. Проверить, существует ли более дешевый путь к соседям этого узла, и если существует, обновить их стоимости.
3. Повторять, пока это не будет сделано для всех узлов графа.
4. Вычислить итоговый путь (об этом в следующем разделе!).

***

Поиск в ширину вычисляет кратчайший путь в невзвешенном графе.

Алгоритм Дейкстры вычисляет кратчайший путь во взвешенном графе.

Алгоритм Дейкстры работает только в том случае, если все веса положительны.

При наличии отрицательных весов используйте алгоритм Беллмана Форда.

***

💡 ПРИМЕРЫ СЛОЖНОСТИ - Бхаргава А. - Грокаем Алгоритмы. Иллюстрированное пособие для программистов и любопытствующих - 2017

C простым поиском мы последовательно проверяем каждое число, одно за другим. Если список состоит из 100 чисел, может потребоваться до 100 попыток. 
Для списка из 4 миллиардов чисел потребуется до 4 миллиардов попыток. Таким образом, максимальное количество попыток совпадает с размером списка. 
Такое время выполнения называется __линейным__.

С бинарным поиском дело обстоит иначе. Если список состоит из 100 элементов, потребуется не более 7 попыток. 
Для списка из 4 миллиардов эле ментов потребуется не более 32 попыток. 
Впечатляет, верно? Бинарный поиск выполняется за __логарифмическое время__.

***

В «О-большое» игнорируются числа, задействованные в операциях сложения, вычитания, умножения или деления. 
Ни одно из следующих значений не является правильной записью «О-большое»: О(n+26), О(n-26), О(n*26), О(n/26). Все они эквивалентны О(n).

Почему? Константа - это просто число, почти всегда игнорируется в Big-O; в этом вопросе 26 является константой.

***

Когда в нашей программе есть один цикл, время выполнения растёт линейно: чем больше элементов, тем дольше выполнение.

Получается, что алгоритм кода выше работает за линейное время (n). В таких случаях говорят, что "сложность алгоритма" равна O(n).

***

Перемену мест (поменять местами) в массивах или листах часто называют `swap`.

***

Какие преимущества Collections.addAll перед Arrays.asList?

Начнём с того, что при создании коллекций на основе массива `Collections.addAll` работает намного быстрее, чем метод `addAll` коллекции с подачей на вход `Arrays.asList`. 
Кроме того, `Collections.addAll` работает не только с `List`, но и с любой другой коллекцией.
А ещё при использовании этого метода не возникает проблемы изменения размера.

***



## Time and Space Complexity (сложность по времени и памяти)
Эффективность алгоритмов определяют по количеству затраченных операций (Time Complexity - Big-O) и кол-ву затраченной памяти (Space Complexity).


### Сложность алгоритмов по Big-O
Хорошо описано в книге `Бхаргава А. - Грокаем Алгоритмы. Иллюстрированное пособие для программистов и любопытствущих - 2017, стр. 32 - Наглядное представление «О-большое»`.

О-большое (Big-O) не сообщает скорость в секундах, а позволяет сравнить количество операций. Оно указывает, насколько быстро возрастает время выполнения алгоритма.

__Основные моменты Big-O:__
* Скорость алгоритмов измеряется не в секундах, а в темпе роста количества операций.
* По сути формула описывает, насколько быстро возрастает время выполнения алгоритма с увеличением размера входных данных.
* Бремя выполнения O(log п) быстрее О(п), а с увеличением размера списка, в котором ищется значение, оно становится намного быстрее.

__Популярные разновидности «О-большого» в порядке убывания скорости выполнения:__

|Symbol              | Complexity                     | Description                            | Example                       |
|--------------------| -------------------------|----------------------------------------|-------------------------------|
| O(1)               | Константная (Постоянная) | Время не зависит от входных данных.    | array[10]                     |
| O(log<sub>n</sub>) | Логарифмическая          | Время выполнения алгоритма растет логарифмически с увеличением размера входного массива. | Бинарный поиск |
| O(n)               | Линейная                 | Сложность алгоритма линейно растет с увеличением входного массива. Алгоритм легко узнать по наличию цикла с итерацией по каждому элементу входного массива. | Простой поиск |
| O(n * log n)       | Линейно-логарифмическая  | Грокаем алгоритмы - стр.95, Средний и худший случай | Некоторые алгоритмы типа «разделяй и властвуй» попадают в эту категорию. |
| O(n<sup>2</sup>) или O(n * n)   | Квадратичное время       |                                        | Медленные алгоритмы сортировки (сортировка пузырьком, сортировка вставками) |
| O(n!)              | Факториальное время      |                                        | Очень медленные алгоритмы (решение задачи коммивояжёра полным перебором) |

__LINKS:__
* [Know Thy Complexities!](https://www.bigocheatsheet.com/)
* [Таблица сложностей по времени - WIKI](https://ru.wikipedia.org/wiki/Временная_сложность_алгоритма)
* [Временная сложность Java Collections Framework](https://habr.com/ru/post/237043/)





## Структуры данных
Структура данных – это контейнер, который хранит информацию в определенном виде. 
Из-за такой «компоновки» она может быть эффективной в одних операциях и неэффективной в других. 
Цель разработчика – выбрать из существующих структур оптимальный для конкретной задачи вариант.

__LINKS:__
* [Структуры данных в Java. Полезные методы вспомогательных классов](https://habr.com/ru/company/epam_systems/blog/476098/)

__NOTES:__
- При использовании массива все задачи хранятся в памяти непрерывно (т.е. рядом друг с другом).
- Чтение в связнных списках происходит медленно только при обращении к случайным элементам списка.

### PriorityQueue
Класс PriorityQueue был введен в Java 1.5 и является частью Java Collections Framework. PriorityQueue является неограниченной очередью. 
Элементы упорядочены по умолчанию в естественном порядке или же отсортированы с помощью компаратора.

PriorityQueue не позволяет добавлять null-значения и non-comparable объекты. 
Размер приоритетной очереди (PriorityQueue) неограничен, но мы можем указать начальный размер в момент его создания. 
Когда мы добавляем элементы в приоритетную очередь, её размер увеличивается автоматически.

PriorityQueue не является потобезопасной! Для этих целей в Java реализован класс PriorityBlockingQueue, реализующий интерфейс BlockingQueue. 
Именно он используется в многопоточной среде.

В PriorityQueue добавление/удаление элементов происходит за время O(log(n)).


***

## Типы алгоритмов

### Рекурсия 
В каждой рекурсивной функции должно быть два случая: базовый и рекурсивный.

Создание рекурсии:
1. Сначала определяется базовый случай. Это должен быть простейший случай из всех возможных.
2. Потом рекурсивный. Задача делится или сокращается до тех пор, пока не будет сведена к базовому случаю.



### Алгоритм сортировки пузырьком (Bubble Sort)
Сортировка пузырьком — одна из самых простых и неэффективных сортировок.
Её ещё иногда называют "глупой сортировкой".

Идея: имеем два элемента (например, `a=6`, `b=5`), мы должны переставить местами `a` и `b`, если `a` больше чем `b` (если `a > b`).
Если элемент с индексом `а` больше, чем элемент с индексом `b`, (`array[a] > array[b]`), такие элементы надо поменять местами.

```java
boolean needIteration = true;
while (needIteration) {
    needIteration = false;
    for (int i = 1; i < arr.length; i++) {
        if (arr[i] < arr[i - 1]) {
            swap(arr, i, i - 1);
            needIteration = true;
        }
    }
}
```
Поскольку здесь используется цикл в цикле (n * n или n<sup>2</sup>), сложность данного алгоритма будет __O(n<sup>2</sup>)__.


### Алгоритм сортировки выбором (Selection Sort)
Идея: каждый проход выбирать самый минимальный элемент и смещать его в начало.

При этом каждый новый проход начинать сдвигаясь вправо, то есть первый проход — с первого элемента, второй проход — со второго.

```java
for (int min = 0; min < arr.length-1; min++) {
    int least = min;
    for (int j = min + 1; j < arr.length; j++) {
        if (arr[j] < arr[least]) {
            least = j;
        }
    }
    int tmp = arr[min];
    arr[min] = arr[least];
    arr[least] = tmp;
}
```

### Алгоритм сортировки слиянием (merge sort)
Сложность алгоритма — логарифмическая `O(n log n)`.

```java
public static void mergeSort(int[] a, int n) {
    if (n < 2) {
        return;
    }
    int mid = n / 2;
    int[] l = new int[mid];
    int[] r = new int[n - mid];

    for (int i = 0; i < mid; i++) {
        l[i] = a[i];
    }
    for (int i = mid; i < n; i++) {
        r[i - mid] = a[i];
    }
    mergeSort(l, mid);
    mergeSort(r, n - mid);

    merge(a, l, r, mid, n - mid);
}

public static void merge(int[] a, int[] l, int[] r, int left, int right) {
    int i = 0, j = 0, k = 0;

    while (i < left && j < right) {
        if (l[i] <= r[j]) {
            a[k++] = l[i++];
        } else {
            a[k++] = r[j++];
        }
    }

    while (i < left) {
        a[k++] = l[i++];
    }

    while (j < right) {
        a[k++] = r[j++];
    }
}
```
Source - Baeldung: [Article](https://www.baeldung.com/java-merge-sort) | [GitHub](https://github.com/eugenp/tutorials/blob/master/algorithms-sorting/src/main/java/com/baeldung/algorithms/mergesort/MergeSort.java)


### Алгоритм быстрой сортировки (quick sort)
Данную сортировку ещё называют сортировкой Хоара.

Используется в `Arrays.sort()`.

* [Пояснения и анимация](http://me.dt.in.th/page/Quicksort/)

Алгоритм быстрой сортировки — это рекурсивный алгоритм, использует стратегию «разделяй и властвуй».
Ее суть заключается в том, что задача разбивается на подзадачи до тех пор, пока не будет элементарной единицы. 
Так и с алгоритмом: массив делится на несколько массивов, каждый из которых сортируется по отдельности и потом объединяется в один массив.

Для начала необходимо определить опорный элемент (pivot).

```java
// Легкий вариант - пример с дополнительными массивами:
public static Integer[] quickSort(Integer[] array) {
    if (array.length <= 1) {
        return array;
    }

    int pivot = array[array.length-1];
    List<Integer> left = new ArrayList<>();
    List<Integer> right = new ArrayList<>();

    for (int i = 0; i < array.length-1; i++) {
        if (array[i] < pivot) {
            left.add(array[i]);
        } else {
            right.add(array[i]);
        }
    }

    Integer[] leftSubArr = left.toArray(new Integer[0]);
    Integer[] rightSubArr = right.toArray(new Integer[0]);

    List<Integer> res = new ArrayList<>();
    Collections.addAll(res, quickSort(leftSubArr));
    res.add(pivot);
    Collections.addAll(res, quickSort(rightSubArr));

    return res.toArray(new Integer[0]);
}
```
```java
// Сложный вариант - in-place, т.е. без доп. структур:
public static void quickSort(int[] arr, int leftBorder, int rightBorder) {
    if (leftBorder < rightBorder) {
        int pivot = arr[rightBorder];
        int i = leftBorder - 1;
    
        for (int j = leftBorder; j < rightBorder; j++) {
        if (arr[j] < pivot) {
        i++;
    
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        }
    }
    int tempVal = arr[i+1];
    arr[i+1] = arr[rightBorder];
    arr[rightBorder] = tempVal;

    int partitionIndex = i + 1;

    quickSort(arr, leftBorder, partitionIndex-1);
    quickSort(arr, partitionIndex+1, rightBorder);
    }
}
```

### Two-pointers
Данный способ позволяет значительно увеличить эффективность алгоритма (занимает меньше времени на работу и памяти - time and space complexity) путём манипулирования двумя индексами массива или листа за один цикл.
Нужно использовать при отсортированном массиве.
```java
// The time complexity of this solution is O(n) and space complexity is O(1)
public class TwoPointer {

  public static void main(String[] args) {
    System.out.println(twoSums(new int[]{1, 1, 2, 3, 4, 6, 8, 9}, 11));
  }

  public static boolean twoSums(int[] array, int target) {
    int pointerOne = 0;
    int pointerTwo = array.length - 1;

    while (pointerOne < pointerTwo) {
      int sum = array[pointerOne] + array[pointerTwo];

      if (sum == target) {
        return true;
      } else if (sum < target) {
        pointerOne++;
      } else {
        pointerTwo--;
      }
    }
    return false;
  }
}
```

### Backtracking
<a href="https://leetcode.com/problems/generate-parentheses/discuss/1545154/Java-or-TC%3A-O(4Nsqrt(N))-or-SC%3A-O(N)-or-Backtracking-and-Iterative-Solutions">
22. Generate Parentheses
</a>

### Dynamic programming
Своего рода оптимизация рекурсии. Эффективное манипулирование состоянием объекта.
Тема достаточно сложная. 
Часто манипулируют индексами массива, иногда двухмерного.
А если кол-во элементов не известно - мапами.
Из плюсов - `time complexity` лучше.
Динамическое программирование также работает с теорией графов. 

Есть несколько подходов выполнения данного алгоритма:
> Часто можно встретить комбинацию подходов.
* Recursion
* Мемоизация (memoization, store-memoize (not memorize!), memo, top-down, сверху вниз, нисходящее динамическое программирование).
    > Мемоизация — оптимизационная техника, которая позволяет запоминать результаты вычислений и потом переиспользовать их тогда, когда нужно сделать такие же вычисления.
      Также эту технику описывают как «сверху вниз».
      То есть вы сначала решаете большую проблему «сверху» — Фибоначчи(5), а потом спускаетесь.
      <br>[dou.ua](https://dou.ua/lenta/columns/about-dynamic-programming/)
    
    > Нисходящее динамическое программирование: задача разбивается на подзадачи меньшего размера, они решаются и затем комбинируются для решения исходной задачи.
      Используется запоминание для решений уже решенных подзадач.
      <br>[wikipedia.org](https://ru.wikipedia.org/wiki/Динамическое_программирование)      
  
* Табуляция (Bottom-up, Восходящее динамическое программирование).
    > Табуляция — оптимизационная техника, которая начинает решать подзадачи с самой простой и потом при дальнейшем продвижении решает все более сложные подзадачи, пока не будет решена основная задача. При этом для решения более сложных подзадач используются решения более простых подзадач.
      В отличие от мемоизации, этот подход называют «снизу вверх» из-за того, что вы сначала беретесь за самые простые задачи.
      <br>[dou.ua](https://dou.ua/lenta/columns/about-dynamic-programming/)
    
    > Восходящее динамическое программирование: все подзадачи, которые впоследствии понадобятся для решения исходной задачи просчитываются заранее и затем используются для построения решения исходной задачи.
      Этот способ лучше нисходящего программирования в смысле размера необходимого стека и количества вызова функций, но иногда бывает нелегко заранее выяснить, решение каких подзадач нам потребуется в дальнейшем.
      <br>[wikipedia.org](https://ru.wikipedia.org/wiki/Динамическое_программирование)


### Monotonic Queue
[Гарне пояснення з візуалізацією](https://youtu.be/DfljaUwZsOk?t=506)
Ще називають Increasing або Decreasing Queue.


***


## Приклади

### [509. Fibonacci Number](https://leetcode.com/problems/fibonacci-number/)
Подход Bottom-up. Оперирование с двумя числами:
```java
public int fib(int n) {
    if (n < 3) return n == 1 || n == 2 ? 1 : 0;

    Integer[] F = new Integer[n+1];
    F[0] = 0; F[1] = 1; F[2] = 1;

    for (int i = 3; i < F.length; i++) {
        F[i] = F[i-1] + F[i-2];
    }

    return F[n];
}
```

### [1137. N-th Tribonacci Number](https://leetcode.com/problems/n-th-tribonacci-number/)
Подход Bottom-up. Оперирование с тремя числами:
```java
public int tribonacci(int n) {
    if (n <= 2) return n == 2 || n == 1 ? 1 : 0;

    int[] dp = new int[n+1];
    dp[0] = 0; dp[1] = 1; dp[2] = 1;

    for (int i = 3; i < dp.length; i++) {
        dp[i] = dp[i-3] + dp[i-2] + dp[i-1];
    }

    return dp[dp.length-1];
}
```

### [70. Climbing Stairs](https://leetcode.com/problems/climbing-stairs/)
[Другие подходы](https://leetcode.com/problems/climbing-stairs/discuss/963994/Java-from-Recursion-to-DP)
|| [Ещё подходы](https://leetcode.com/problems/climbing-stairs/discuss/980814/Java-all-approach-Recursive-Memoization-DP-Optimized)

Пример с подходом __Recustion + Memorization (Top Down Approach)__ и через `HashMap`.
Top Down наверное поточу, что при каждой стеке вызова n будет всё ниже
```java
public int climbStairs(int n) {
    Map<Integer, Integer> memo = new HashMap<>();
    memo.put(1, 1);
    memo.put(2, 2);
    return climbStairs(n, memo);
}

private int climbStairs(int n, Map<Integer, Integer> memo) {
    if (memo.containsKey(n)) {
        return memo.get(n);
    }
    memo.put(n, climbStairs(n - 1, memo) + climbStairs(n - 2, memo));
    return memo.get(n);
}
```

### [1143. Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/discuss/1177214/JAVA-4-Different-solutions)
Графическое объяснение:

<a align="center" href="http://piccy.info/view3/15101063/963be0aa8d5a11939718e6eb3ae42b44/" target="_blank"><img src="http://i.piccy.info/i9/1f3ab91d2d4b59569698b44b622d3a1f/1633252657/30033/1443672/calc_500.jpg" alt="Piccy.info - Free Image Hosting" border="0" /></a><a href="http://i.piccy.info/a3c/2021-10-03-09-17/i9-15101063/392x425-r" target="_blank"><img src="http://i.piccy.info/a3/2021-10-03-09-17/i9-15101063/392x425-r/i.gif" alt="made by me" border="0" /></a>

[Визуализатор алгоритма](https://algorithm-visualizer.org/dynamic-programming/longest-common-subsequence)
```java
// Tabulation (DP) #9 ms
public int longestCommonSubsequence(String text1, String text2) {
    int n = text1.length();
    int m = text2.length();
    
    int[][] dp = new int[n + 1][m + 1];
    
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
            if (text1.charAt(i -1) == text2.charAt(j - 1)) {
                dp[i][j] = 1 + dp[i - 1][j - 1];
            } else {
                 dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
    }
    return dp[n][m];
}
```


### [931. Minimum Falling Path Sum](https://leetcode.com/problems/minimum-falling-path-sum)
```java
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int[][] dp = new int[n][n];
        // Initialize the first row of dp with the first row of matrix
        for (int j = 0; j < n; j++) {
            dp[0][j] = matrix[0][j];
        }
        // Iterate over the remaining rows of dp
        for (int i = 1; i < n; i++) {
            // For each column of dp, find the minimum sum of the previous row's adjacent cells
            for (int j = 0; j < n; j++) {
                // The left cell is dp[i-1][j-1] if j > 0, otherwise it is Integer.MAX_VALUE
                int left = (j > 0) ? dp[i-1][j-1] : Integer.MAX_VALUE;
                // The middle cell is dp[i-1][j]
                int middle = dp[i-1][j];
                // The right cell is dp[i-1][j+1] if j < n-1, otherwise it is Integer.MAX_VALUE
                int right = (j < n-1) ? dp[i-1][j+1] : Integer.MAX_VALUE;
                // The minimum sum is the minimum of left, middle and right plus the current cell value
                dp[i][j] = Math.min(left, Math.min(middle, right)) + matrix[i][j];
            }
        }
        // The answer is the minimum value in the last row of dp
        int ans = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            ans = Math.min(ans, dp[n-1][j]);
        }
        return ans;
    }
}

```


# Математика
> Логарифм по смыслу противоположен возведению в степень.
> 
> **Пример:** log<sub>10</sub>100 по сути означает, сколько раз нужно перемножить 10, чтобы получить 100. 
> Ответ `2`: `10 × 10`, т.е. log<sub>10</sub> 100 = 2.
> 
> 10<sup>2</sup> = 100 ◄► log<sub>10</sub>100 = 2<br>
> 10<sup>3</sup> = 1000 ◄► log<sub>10</sub>1000 = 3<br>
> 2<sup>3</sup> = 8 ◄► log<sub>2</sub>8 = 3<br>
> 2<sup>4</sup> = 16 ◄► log<sub>2</sub>16 = 4<br>
> 2<sup>5</sup> = 32 ◄► log<sub>2</sub>32 = 5<br>


> Как узнать делится ли N число 2,3,4....
>
> 100 : 2 | чётные<br>
> 105 : 3 | сумма цифер делится на 3 без остатка?<br>
> 100 : 4 | число делится на 4 когда две последние цифры 00; и последние две цифры дают двухзначное число, которое делится на 4.<br>
> 100 : 8 | число делится на 8 когда три последние цифры 00; и последние три цифры дают трехзначное число, которое делится на 4.



# BFS (Breath-First-Search / Поиск в ширину)
Пример подсчета суммы на самом ПОСЛЕДНЕМ уровне:
```java
public int deepestLeavesSum(TreeNode root) {
    if (root == null) return 0;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);

    int sum = 0;
    while (queue.isEmpty()) {
        int count = queue.size();
        sum = 0;
        for (int i = 0; i < count; i++) {
            TreeNode tmp = queue.poll();
            sum += tmp.val;
            if (tmp.left != null) {
                queue.add(tmp.left);
            }
            if (tmp.right != null) {
                queue.add(tmp.right);
            }
        }
    }
    return sum;
}
```


# Modulo
> СИНОНІМИ: модуль, ділення по модулю
> 
> ВИКОРИСТОВУЮТЬ ЗНАЧЕННЯ: `1e7`, `10000000007`, `%`, 10<sup>9</sup> + 7, `10^9 + 7`



# Цікаві рішення, які можуть знадобитися при розробці

***

[491. Non-decreasing Subsequences](https://leetcode.com/problems/non-decreasing-subsequences/)

Знайти лише унікальні неспадні підмасиви з кількістю елементів понад 2. 
[SOLUTION](https://leetcode.com/problems/non-decreasing-subsequences/solutions/3075034/easy-solution-fully-explained-c-python3-java/)

***