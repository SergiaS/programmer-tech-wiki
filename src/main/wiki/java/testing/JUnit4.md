# JUnit4

## @Rule
* [Механизм правил в JUnit - @Rule](http://blog.qatools.ru/junit/junit-rules-tutorial)
Дополнительный функционал для тестирования - упрощает дублирование кода.

Ситуация следующая - у тебя много классов с тестами, для каждого теста в классе требуется некая инициализация каких-то данных. 

И чтобы не дублировать в каждом классе `@Before` и `@After` - придумали рулы.

Сначало нужно создать класс, который будет имплементировать интерфейс `TestRule` и реализовать его метод `apply`. 

Пример показа/логирования времени работы каждого тестового метода:
```java
public class LogRule implements TestRule {
    private final Logger log = LoggerFactory.getLogger(LogRule.class);
    private final Clock clock = Clock.systemDefaultZone();
    private long currentTime = 0L;

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                currentTime = clock.millis();
                base.evaluate();
                log.info("{} time work: {} sec", description.getMethodName(), (clock.millis() - currentTime) / 1000.0);
            }
        };
    }
}
```

А вот далее всё что требуется просто добавить эту рулу с аннотацией в нужный тестовый класс:
```java
public class MealServiceTest {

    @Rule
    public LogRule logRule = new LogRule();

    @Autowired
    private MealService service;

    @Test
    public void delete() {
        service.delete(MEAL1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID, USER_ID));
    }
    // other code
}
```

Теперь каждое содержимое метода будет обёрнуто рулой. Сам метод в руле - `base.evaluate()`, а всю инфу с выполняемого класса можно достать в параметре `description`.


## Assumptions (предположения)
* [Запуск или игнорирование JUnit-теста при определенных условиях](https://iliachemodanov.ru/ru/blog-ru/12-tools/57-junit-ignore-test-by-condition-ru)

