# Interface
> Все интерфейсы по умолчанию `public` и `abstract`, поэтому писать это не обязательно.

> В Java 9 появилась возможность использовать `private` методы в интерфейсах.

## Functional Interface (Функциональный интерфейс)
Функциональный интерфейс — это интерфейс, содержащий один нереализованный (абстрактный) метод, других методов (статических или дефолтных) может быть любое кол-во.

- Методы с модификатором `default` позволяют добавлять новые методы в интерфейсы, не нарушая их существующую реализацию.
- Методы `static` в интерфейсе работают так же, как и `static` методы в классе. 
  Наследовать `static` методы нельзя, как нельзя вызывать `static` метод из класса-наследника.
- **Функциональные интерфейсы могут содержать дополнительно абстрактные методы класса Object**.

Приклад реалізації функціонального інтерфейсу:
```java
interface Bob {
    void name(String str);
}

public class Note {
  public static void main(String[] args) {
    Bob bob = (str) -> {
      System.out.println(str);
    };
    bob.name("bobby"); // sout: "bobby"
  }
}
```

***

### @FunctionalInterface
`@FunctionalInterface` annotation is used to represent a functional interface.

**This annotation is not mandatory, but it helps the compiler to throw errors if the interface is not a functional interface at compile time.**

The `java.util.function` package contains more than 40 functional interfaces and they are organized into 4 categories.

***

### `Function<T,R>`
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

***

### `BiFunction<T,U,R>`
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

***

### `Consumer<T>`
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

***

### `BiConsumer<T, U>`
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

***

### `Supplier<T>`
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

***

### `Predicate<T>`
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
