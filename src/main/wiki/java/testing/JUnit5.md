# JUnit 5
* [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
* [Туториал по JUnit 5 - Введение](https://habr.com/ru/post/590607/)

> JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage

## Миграция с JUnit4 на JUnit5 в TopJava

1. Для всех тестов теперь мы можем удалить `public`.
2. Аннотацию `@Before` исправим на `@BeforeEach` - теперь метод, который будет выполняться перед каждым тестом, помечается именно так.
3. В **Junit5** работа с исключениями похожа на **Junit4** версии 4.13: вместо ожидаемых исключений в параметрах аннотации `@Test(expected = Exception.class)` используется метод `assertThrows()`, в который
   первым аргументом мы передаем ожидаемое исключение, а вторым аргументом — реализацию функционального интерфейса `Executable` (кода теста, в котором ожидается возникновение исключения).
4. Метод `assertThrows()` возвращает исключение, которое было выброшено в переданном ему коде. Теперь мы можем получить это исключение, извлечь из него сообщение с помощью
   `e.getMessage()` и сравнить с ожидаемым.
5. Для теста на валидацию при проверке предусловия, только при выполнении которого будет выполняться следующий участок кода (например, в нашем случае тесты на валидацию выполнялись только в jpa
   профиле), - теперь нужно пользоваться утильным методом `Assumptions` (нам уже не требуется).
6. Проверку Root Cause - причины, из-за которой было выброшено пойманное исключение, мы будем делать позднее, при тестах на ошибки.
7. Из **JUnit5** исключена функциональность `@Rule`, вместо них теперь нужно использовать `Extensions`, которые могут встраиваться в любую фазу тестов. Чтобы добавить их в тесты, пометим базовый тестовый
   класс аннотацией `@ExtendWith`.

**JUnit** предоставляет нам набор коллбэков — интерфейсов, которые будут исполняться в определенный момент тестирования. Создадим класс `TimingExtension`, который будет засекать время выполнения тестовых
методов.  
Этот класс будет имплементировать маркерные интерфейсы — коллбэки **JUnit**:

- `BeforeTestExecutionCallback` - коллбэк, который будет вызывать методы этого интерфейса перед каждым тестовым методом.
- `AfterTestExecutionCallback` - методы этого интерфейса будут вызываться после каждого тестового метода;
- `BeforeAllCallback` - методы перед выполнением тестового класса;
- `AfterAllCallback` - методы после выполнения тестового класса;

Осталось реализовать соответствующие методы, которые описываются в каждом из этих интерфейсов, они и будут вызываться JUnit в нужный момент:

- в методе `beforeAll` (который будет вызван перед запуском тестового класса) создадим спринговый утильный секундомер `StopWatch` для текущего тестового класса;
- в методе `beforeTestExecution` (будет вызван перед тестовым методом) - запустим секундомер;
- в методе `afterTestExecution` (будет вызван после тестового метода) - остановим секундомер.
- в методе `afterAll` (который будет вызван по окончанию работы тестового класса) - выведем результат работы этого секундомера в консоль;

8. Аннотации `@ContextConfiguration` и `@ExtendWith(SpringExtension.class)` (замена `@RunWith`) мы можем заменить одной `@SpringJUnitConfiguration` (в старых версиях IDEA ее не понимает)


## JUnit4 на JUnit5
* Аннотация `@Test` переехала в другой пакет.
* Аннотация `@Before` сменилась на `@BeforeEach`.
* Аннотации `@BeforeClass` и `@AfterClass` сменились на `@BeforeAll` и `@AfterAll` соответственно.
* Класс `Assert` заменен на `Assertions`.


## Examples

> <details>
> <summary>Приклад параметризованого тесту - @ParameterizedTest</summary>
> Зменшує кількість коду та спрощує тестування
> 
> ```java
> import org.junit.jupiter.api.BeforeEach;
> import org.junit.jupiter.params.ParameterizedTest;
> import org.junit.jupiter.params.provider.CsvSource;
> 
> import static org.assertj.core.api.Assertions.assertThat;
> 
> class PhoneNumberValidatorTest {
>   
>   private PhoneNumberValidator underTest;
> 
>   @BeforeEach
>   void setUp() {
>     underTest = new PhoneNumberValidator();
>   }
> 
>   @ParameterizedTest
>   @CsvSource({
>       "+447000000000, true",
>       "+4470000000001, false",
>       "447000000000, false"
>   })
>   void itShouldValidatePhoneNumber(String phoneNumber, boolean expected) {
>     // When
>     boolean isValid = underTest.test(phoneNumber);
> 
>     // Then
>     assertThat(isValid).isEqualTo(expected);
>   }
> }
> ```
> 
> </details>


