# Spring AOP
> В Spring транзакции поддерживаются на уровне аспектов.

> Для использования **Spring AOP** необходимо добавить новые зависимости (`spring-aop` и `aspectjtools`) в файл `pom.xml`.

> Несмотря на то, что используется Spring AOP, всё равно нужно подключить `AspectJ` - Spring AOP использует некоторые AspectJ классы и аннотации.

AOP (Аспектно-ориентированное программирование, Aspect Oriented Programming – AOP) -
парадигма программирования, основанная на идее разделения основного и служебного функционала. 
Служебный функционал записывается в Aspect-классы.
В основе Aspect заключена сквозная логика (**cross-cutting logic**).

В мире AOP любая не основная/не бизнес логика приложения называется сквозной логикой.
Это та функциональность, которая напрямую к основной логике приложения не относится!

**К сквозному функционалу относят:**
* Логирование
* Проверка прав (security check)
* Обработка транзакций
* Обработка исключений
* Кэширование
* И т.д.


## Аннотации

### @EnableAspectJAutoProxy
позволяет нам за кулисами использовать Spring AOP Proxy
```java
@Configuration
@ComponentScan("aop")
@EnableAspectJAutoProxy
public class MyConfig {
    // some code
}
```
После чего создаем класс аспект - помечаем аннотацией аспект (`@Aspect`).


### @Aspect 
`@Aspect` говорит о том, что это не простой класс, а Aspect. 
Поэтому к данному классу Spring будет относиться по другому.

Aspect – это класс, отвечающий за сквозную функциональность.


### Advice @AfterReturning
Выполняется только после нормального окончания метода с основной логикой, но до присвоения результата этого метода какой-либо переменной.
Поэтому с помощью `@AfterReturningAdvice` возможно изменять возвращаемый результат метода.
```java
@AfterReturning(
        pointcut = "execution(* getStudents())",
        returning = "students"
)
public void afterReturningGetStudentLoggingAdvice(List<Student> students) {
    Student firstStudent = students.get(0);

    String surname = firstStudent.getSurname();
    surname = "Mr. " + surname;
    firstStudent.setSurname(surname);

    double avgGrade = firstStudent.getAvgGrade();
    avgGrade = avgGrade+1;
    firstStudent.setAvgGrade(avgGrade);
}
```

### Advice @AfterThrowing
Выполняется после окончания работы метода, если в нём было выброшено исключение.

Не влияет на протекание программы при выбрасывании исключения. 
С помощью `@AfterThrowingAdvice` можно получить доступ к исключению, которое выбросилось из метода с основной логикой.


### Advice @After
Выполняется после окончания метода с основной логикой вне зависимости от того, завершается ли метод нормально или выбрасывается исключение.

**С помощью `@After` Advice невозможно:**
1) получить доступ к исключению, которое выбросилось из метода с основной логикой;
2) получить доступ к возвращаемому методом результату.


### Advice @Around
Выполняется до и после метода с основной логикой.

**С помощью `@Around` Advice возможно:**
1) произвести какие-либо действия до работы target метода;
2) произвести какие-либо действия после работы target метода;
3) получить результат работы target метода/изменить его;
4) предпринять какие-либо действия, если из target метода выбрасывается исключение.

```java
@Around("execution(public String returnBook())")
public Object aroundReturnBookLoggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    System.out.println("aroundReturnBookLoggingAdvice: try books returns");
    Object targetMethodResult = proceedingJoinPoint.proceed();
    System.out.println("aroundReturnBookLoggingAdvice: success books returns");
    return targetMethodResult;
}
```
Используя `@Around` Advice возможно предпринять следующие действия, если из target метода выбрасывается исключение:
* Ничего не делать
* Обрабатывать исключение
* Пробрасывать исключение дальше


## Понятия AOP

### Aspect («Аспект»)
> По сути, аспект - это класс.

Aspect – «сквозной» функционал.

Аспект – набор советов для каждого из которых определены точки соединения и область применения.

#### Порядок выполнения Aspect-ов
Если при вызове 1-го метода с бизнес-логикой срабатывают несколько Advice-ов, то нет никакой гарантии в порядке выполнения этих Advice-ов.
Для соблюдения порядка такие Advice-ы нужно распределять по отдельным упорядоченным Aspect-ам.
```java
@Component
@Aspect
@Order(20)
public class SecurityAspect {
    @Before("aop.aspects.MyPointcuts.allGetMethods()")
    public void beforeGetSecurityAdvice(){
        System.out.println("beforeGetSecurityAdvice: checking rights to taking book/magazine");
    }
}
```


### Advice («Совет»)
> По сути, Advice - это методы класса аспекта.

**Advice** – когда и что именно (какой код) нужно выполнить в точке соединения.


Это фактическое действие, которое должно быть предпринято до и/или после выполнения метода. 
Это конкретный код, который вызывается во время выполнения программы.

#### Типы советов
* **Before** – выполняется до метода с основной логикой
* **After returning** – выполняется только после нормального окончания метода с основной логикой
* **After throwing** – выполняется после окончания метода с основной логикой только, если было выброшено исключение
* **After/After finally** – выполняется после окончания метода с основной логикой
* **Around** – выполняется до и после метода с основной логикой


### Join Point («Точка сопряжения», «Точка соединения»)
**Join Point** – в каком месте (при каком действии) нужно выполнить совет.

Joint Point – это точка/момент в выполняемой программе когда следует применять Advice. 
Т.е. это точка переплетения метода с бизнес-логикой и метода со служебным функционалом.

Прописав Joint Pointв параметры метода Advice, мы получаем доступ к информации о сигнатуре и параметрах метода с бизнес-логикой.
```java
@Before("aop.aspects.MyPointcuts.allAddMethods()")
public void beforeAddLoggingAdvice(JoinPoint joinPoint) {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    System.out.println("methodSignature = " + methodSignature);
    System.out.println("methodSignature.getMethod() = " + methodSignature.getMethod());
    System.out.println("methodSignature.getReturnType() = " + methodSignature.getReturnType());
    System.out.println("methodSignature.getName() = " + methodSignature.getName());

    if (methodSignature.getName().equals("addBook")) {
        Object[] arguments = joinPoint.getArgs();

        for (Object o : arguments) {
            if (o instanceof Book) {
                Book myBook = (Book) o;
                System.out.println("Book Info: " + myBook.getName() + ", " +
                        "author: " + myBook.getAuthor() + ", " +
                        "year: " + myBook.getYearOfPublication());
            } else if (o instanceof String) {
                System.out.println("adding by: " + o);
            }
        }
    }
}
```


### Pointcut («Срез точек соединения»)
* [Примеры срезов 4.0.x](https://docs.spring.io/spring-framework/docs/4.0.x/spring-framework-reference/html/aop.html#aop-proxying)

**Pointcut** – выражение, описывающее где должен быть применён Advice. 
**<u>Это то что содержится в скобках аспекта.</u>**

**Pointcut** – область применения (поиск, сужение) точек соединения.

Срезом называется несколько объединённых точек (join points), в котором должен быть выполнен совет.

#### Шаблон
> Spring AOP использует AspectJ Point cut expression language. 
> Т.е. определённые правила в написании выражений для создания Pointcut
Шаблон Pointcut'а
* `*` - wild-card = любой
* `..` - любое кол-во, любых параметров, даже 0

- <u>**обязательно**</u>
- _опционально_

* <u>**execution(**</u> _modifiers-pattern?_ <u>**return-type-pattern**</u> _declaring-type-pattern?_
<u>**method-name-pattern(parameters-pattern)**</u> _throws-pattern?_ <u>**)**</u>

#### Пример простого pointcut'a
Соответствует методу без параметров, где бы он ни находился с access modifier = `public`, return type = `void` и именем = `getBook`:
```xml
execution(public void getBook())
```

Соответствует методу без параметров, из класса `UnivercityLibraryс` access modifier = `public`, return type = `void` и именем = `getBook`:
```xml
execution(public void aop.UniLibrary.getBook())
```

Соответствует методу без параметров, где бы он ни находился с access modifier = `public`, return type = `void` и именем, начинающимся на «`get`»:
```xml
execution(public void get*())
```

Соответствует методу без параметров, где бы он ни находился с любым access modifier, любым return type и именем = `getBook`:
```xml
execution(* getBook())
```

Соответствует методу без параметров, где бы он ни находился с любым access modifier, любым return type и любым именем:
```xml
execution(* *())
```

Соответствует методу с параметром `String`, где бы он ни находился с access modifier = `public`, return type = `void` и именем = `getBook`:
```xml
execution(public void getBook(String))
```

Соответствует методу с любым одним параметром, где бы он ни находился с access modifier = `public`, return type = `void` и именем = `getBook`:
```xml
execution(public void getBook(*))
```

Соответствует методу с любым количеством любого типа параметров, где бы он ни находился с access modifier = `public`, return type = `void` и именем = `getBook`:
```xml
execution(public void getBook(..))
```

Соответствует методу, первым параметром которого является `aop.Book`, а дальше может идти 0 и больше параметров любого типа, где бы этот метод ни находился с access modifier = `public`, return type = `void` и именем = `getBook`:
```xml
execution(public void getBook(aop.Book, ..))
```

Соответствует методу с любым количеством любого типа параметров, где бы он ни находился с любым access modifier, любым return type и любым именем:
```xml
execution(* *(..))
```

***

```xml
<aop:pointcut id="selectAll" expression="execution(* net.proselyte.aop.xml.SomeService.*(..))"/>
```
* `expression` - что нужно отлавливать, и с чем будет работать аспект.
* `execution` (тип точки сопряжения) - будет вызов какого-то метода. Есть и другие варианты для выбора.
* `*` - метод любого типа.
* `net.proselyte.aop.xml.SomeService` - какой класс вызывает.
* `*` - любой метод.
* `(..)` - любые параметры выбранного метода (`*`).


#### Объявление Pointcut
Для того чтобы не пользоваться copy-paste когда для нескольких Advice-ов подходит один и тот же Pointcut, есть возможность объявить данный Pointcut и затем использовать его несколько раз.
```java
@Pointcut(“pointcut_expression”)
private void pointcut_reference() {
    // some code 
}

@Before(“pointcut_reference() ”)
public void advice_name() { 
    // some code 
}
```

**Плюсы объявления Pointcut:**
* Возможность использования созданного Pointcut для множества Advice-ов.
* Возможность быстрого изменения Pointcute xpression для множества Advice-ов.
* Возможность комбинирования Pointcut-ов.

Комбинирование Pointcut-ов – это их объединение с помощью логических операторов `&&`, `||`, `!`.
```java
// example 1
@Component
@Aspect
public class LoggingAndSecurityAspect {

    @Pointcut("execution(* get*())")
    private void allGetMethods(){}

    @Before("allGetMethods()")
    public void beforeGetLoggingAdvice(){
        System.out.println("beforeGetLoggingAdvice: try to take a book/magazine");
    }

    @Before("allGetMethods()")
    public void beforeGetSecurityAdvice(){
        System.out.println("beforeGetSecurityAdvice: checking rights to taking book/magazine");
    }
}
```
```java
// example 2
@Component
@Aspect
public class LoggingAndSecurityAspect {

    @Pointcut("execution(* aop.UniLibrary.*(..))")
    private void allMethodsFromUniLibrary(){}

    @Pointcut("execution(public void aop.UniLibrary.returnMagazine())")
    private void returnMagazineFromUniLibrary(){}


    @Pointcut("allMethodsFromUniLibrary() && !returnMagazineFromUniLibrary()")
    private void allMethodsExceptReturnMagazineFromUniLibrary(){}

    @Before("allMethodsExceptReturnMagazineFromUniLibrary()")
    public void beforeAllMethodsExceptReturnMagazineAdvice(){
        System.out.println("beforeAllMethodsExceptReturnMagazineAdvice: Log #10");
    }
}
```

### Weaving («Вплетение»)
**Weaving** – процесс добавление аспектов к объектам.

Это процесс связывания аспектов с другими объектами приложения для создания совета.
Может быть вызван во время компиляции, загрузки или выполнения приложения.