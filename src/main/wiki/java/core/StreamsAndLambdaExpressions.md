# Streams and Lambda expressions
Главное о стримах и лямбда выражениях.



# Stream

## Операторы стрима
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

## Примеры сьримов

### Example 1:
Сначало нужно превратить набор элементов/данных (что принимаем - лист, массив...) в другую сущность - в поток (вызвав метод `.stream`).
Далее можно делать любые преобразования.

Метод `.map()` позволяет легко изменять набор данных, а также получить (собрать новую коллекцию).
Сначало метод берет каждый элемент из текущего набора данных, и сопоставляет ему новый элемент нового набора данных.
Вот это сопостовление мы описываем с помощью лямбда выражений. MAP переводится как отображение (теория множеств).

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

<hr>

### Example 2:
* `reduce()` (уменьшение) - метод берет набор данных и сжимает его в какой-то один элемент, например можно подсчитать сумму элементов, произведение... (арифметические операции).

```java
int sum1 = Arrays.stream(arr3).reduce((acc, b) -> acc + b).getAsInt();
int sum2 = list3.stream().reduce((acc, b) -> acc * b).get();
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


<hr>



# Lambda
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

<hr>

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

<hr>

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


<hr>

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

<hr>

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


## Functional Interface
> The functional interface is an interface that contains only one abstract method. But it may contain more than one default and static method.

`@FunctionalInterface` annotation is used to represent a functional interface. 
This annotation is not mandatory, but it helps the compiler to throw errors if the interface is not a functional interface at compile time.

The java.util.function package contains more than 40 functional interfaces and they are organized into 4 categories. They are
* `Supplier<T> {...}`
* `Consumer<T> {...}`
* `Predicate<T> {...}`
* `Function<T, R> {...}`

<hr>

## `Function<T,R>`
`Function<T,R>` computes the argument type `T` and returns the type `R`. It contains an abstract method `R apply(T t)`.

Функциональный интерфейс `Function<T,R>` представляет функцию перехода от объекта типа `T` к объекту типа `R`.
```java
// Инкремент через интерфейс Function<T, R>
public static void main(String[] args) {
    Integer increment2 = incrementByOneFunction.apply(1);
    System.out.println(increment2);
}

static Function<Integer,Integer> incrementByOneFunction = number -> number + 1;
```

<hr>

## `BiFunction<T,U,R>`
Потребляет `T` и `U`, возвращает `R`. Вызов через метод `apply(T t, U u)`:
```java
incrementByOneAndMultiplyBiFunction.apply(1, 1);

// (n + 1) * n2 >>> Функция через интерфейс BiFunction<Integer, Integer, Integer>
BiFunction<Integer, Integer, Integer> incrementByOneAndMultiplyBiFunction =
        (numberToIncrementByOne, numberToMultiplyBy)
            -> (numberToIncrementByOne  + 1) * numberToMultiplyBy;
```
```java
// Аналог функции интерфейса BiFunction<Integer, Integer, Integer>
static int incrementByOneAndMultiply(int number, int numToMultiplyBy) {
    return (number + 1) * numToMultiplyBy;
}
```

<hr>

## `Consumer<T>`
`Consumer<T>` processes the argument type `T` and doesn’t return anything. It contains an abstract method `void accept(T t)`.

`Consumer<T>` выполняет некоторое действие над объектом типа `T`, при этом ничего не возвращая.
```java
// Функция через интерфейс Consumer<T>
static Consumer<Customer> greetCustomerConsumer = customer ->
        System.out.println("Hello " + customer.customerName +
            ", thans for registering phone number " +
            customer.customerPhoneNumber);
```
```java
// Аналог функции интерфейса Consumer<T>
static void greetCustomer(Customer customer) {
    System.out.println("Hello " + customer.customerName +
        ", thans for registering phone number " + 
        customer.customerPhoneNumber);
}
```

<hr>

## `BiConsumer<T, U>`
Потребляет два объекта, и ничего не возвращает.
```java
// Функция через интерфейс BiConsumer<T, U>
static BiConsumer<Customer, Boolean> greetCustomerConsumerV2 = (customer, showPhoneNumber) ->
    System.out.println("Hello " + customer.customerName +
        ", thans for registering phone number " +
        (showPhoneNumber ? customer.customerPhoneNumber : "********"));
```
```java
// Аналог функции интерфейса BiConsumer<T, U>
static void greetCustomerV2(Customer customer, boolean showPhoneNumber) {
    System.out.println("Hello " + customer.customerName +
        ", thans for registering phone number " +
        (showPhoneNumber ? customer.customerPhoneNumber : "********"));
}
```

<hr>

## `Supplier<T>`
`Supplier<T>` is used to get a result of type `T`. It contains an abstract method `T get()`.

`Supplier<T>` не принимает никаких аргументов, но должен возвращать объект типа `T`.
Supplier это поставщик, он ничего не берёт, он только доставляет.
```java
// Функция через интерфейс Supplier<T>
System.out.println(getDBConnectionUrlSupplier.get());

static Supplier<String> getDBConnectionUrlSupplier = ()
            -> "jdbc://localhost:5432/users";
```
```java
// Аналог функции интерфейса Supplier<T>
System.out.println(getDBConnectionUrlSupplier.get());

static String getDBConnectionUrl() {
    return "jdbc://localhost:5432/users";
}
```

<hr>

## `Predicate<T>`
`Predicate<T>` negates the argument type `T` and returns the type `boolean`. It contains an abstract method `boolean test(T t)`.

`Predicate<T>` проверяет соблюдение условия `boolean`.
```java
// Функция через интерфейс Predicate<T>
static Predicate<String> isPhoneNumberValidPredicate = 
    phoneNumber -> phoneNumber.startsWith("07");
```
```java
// Аналог функции интерфейса Predicate<T>
static boolean isPhoneNumberValid(String phoneNumber){
    return phoneNumber.startsWith("07");
}
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
