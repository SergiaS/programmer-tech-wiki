# Spring
* [Что такое Spring Framework? От внедрения зависимостей до Web MVC](https://habr.com/ru/post/490586/)
* [CORS with Spring](https://www.baeldung.com/spring-cors?fbclid=IwAR3CGmVujJAZ-22nqcCmOZ1E32Ib8L734yYB_pKiooaKnFIebFHz691f59o)
* [Spring изнутри. Этапы инициализации контекста](https://habr.com/ru/post/222579/)
* [Хороший пример Dependency Injection](https://medium.com/@anupama80286/dependency-injection-b7d93523016a)

> С 5-ой версии Spring поддерживает `slf4j`. Поддержка осуществляется в зависимости `spring-jcl`.

> По окончании работы со Spring нужно закрывать его контекст - `appCtx.close()`. 

> **Что такое схема в `spring-app.xml xsi:schemaLocation` и зачем она нужна?**<br>
> XML схема нужна для валидации xml, IDEA делает по ней автозаполнение.

> **Пример чтение кода Spring'а:**<br>
> ```xml
> <bean class="ru.javawebinar.topjava.service.UserService">
>     <property name="repository" ref="inmemoryUserRepository"/>
> </bean>
> ```
> Можно сказать так: создай и занеси в свой контекст экземпляр класса (бин) `UserService` и присвой его проперти `repository` бин `inmemoryUserRepository`, который возьми из своего контекста.

> [Вызов статического метода из конфигурации спринга](https://stackoverflow.com/questions/27296276/invoke-static-method-from-spring-config/27296470#27296470)
> ```xml
> <bean class="org.springframework.beans.factory.config.MethodInvokingBean">
>     <property name="staticMethod" value="org.slf4j.bridge.SLF4JBridgeHandler.install" />
> </bean>
> ```

> `spring-context-support` - поддерживает кеширование.
> Подключается аннотацией `@Cacheble`.

> После прочтения класса с аннотациями, в своем контексте **Spring** хранит не инстанс этого класса, а его прокси-обертку.

> Если нужно ускорить запус большого приложения, добавь зависимость `spring-context-indexer`. 
> После индексирования будет создан файл в `META-INF/spring.components`, и при старте приложения сканирование пакетов происходить НЕ будет, данные будут браться с этого файла.


## IoC - Inversion of Control (инверсия управления)
> Реализация контейнера IoC осуществляется в зависимости `spring-core`.
> IoC контейнер - это принцип реализации управления.
> Суть - сначало создается абстакция, потом подставляется реализация. Исключено жесткое связывание.

> По умолчанию Spring контейнер использует DynamicProxy, который работает на основе интерфейсов.

> Если используется инверсия контроля (IoC), нельзя создавать объекты через `new`.

> Для управления компонентами, из которых состоит приложение, Spring Container использует Внедрение Зависимостей (DI). Эти объекты называются Spring Beans.

### Что делает IoC контейнер
• Управляет зависимостями;
• Связывает объекты между собой;
• Управляет их жизненным циклом;
• Dependency Injection (DI) – в объект внедряется ссылка на другой объект.

### Метаданные
Spring Container получает инструкции какие объекты инстанциировать и как их конфигурировать через метаданные.

Метаданные могут быть получены несколькими способами:
– XML
– Аннотации Java
– Java код (Java config)

### Spring BeanFactory Container
Это самый простой контейнер, который обеспечивает базовую поддержку **DI** и который основан на интерфейсе `org.springframework.beans.factory.BeanFactory`.
`BeanFactory` обычно используется тогда, когда ресурсы ограничены (мобильные устройства). Поэтому, если ресурсы не сильно ограничены, то лучше использовать `ApplicationContext`.

**Загружает бины по необходимости! Т.е. пока не будет вызван метод `getBean()`.**

### Spring ApplicationContext Container
`ApplicationContext` является более сложным и более продвинутым Spring Container-ом. 
Так же, как `BeanFactory`, `ApplicationContext` загружает бины, связывает их вместе и конфигурирует их определённым образом. 
Но кроме этого, `ApplicationContext` обладает дополнительной функциональностью: распознание текстовых сообщений из файлов настройки и отображение событий, которые происходят в приложении различными способами. 
Этот контейнер определяется интерфейсом `org.springframework.context.ApplicationContext`.

**Сразу создает экземпляры всех бинов, даже если бин ещё не используется!**

### Реализации ApplicationContext
<u>Чаще всего используются следующие реализации `ApplicationContext`:</u>
* `FileSystemXmlApplicationContext`. Загружает данные о бине из XML файла. 
При использовании этой реализации в конструкторе необходимо указать полный адрес конфигурационного файла.
* `ClassPathXmlApplicationContext`. Этот контейнер также получает данные о бине из XML файла.
Но в отличие от `FileSystemApplicationContext`, в этом случае необходимо указать относительный адрес конфигурационного файла (`classpath`).
* `WebXmlApplicationContext`. Эта реализация `ApplicationContext` получает необходимую информацию из веб-приложения.

### init и destroy методы
Чаще всего `init-method` используется для открытия или настройки каких-либо ресурсов, например баз данных, стримов и т.д.
`destroy-method` чаще всего используется для их закрытия.

* У данных методов **access modifier** может быть любым
* У данных методов **return type** может быть любым.
  Но из-за того, что возвращаемое значение мы никак не можем использовать, чаще всего **return type** – это `void`.
* Называться данные методы могут как угодно.
* В данных методах не должно быть параметров.
* Если у бина `scope = prototype`, то:
  * `init-method` будет вызываться для каждого ново созданного бина.
  * для этого бина `destroy-method` вызываться не будет.
  * программисту необходимо самостоятельно писать код для закрытия/освобождения ресурсов, которые были использованы в бине.

### [Процесс инициализации Spring](https://javarush.ru/groups/posts/1676-spring-framework-vvedenie)
Короткая веррсия:
> Сначала парсится конфигурации, и на основе её создается `BeanDefinition`, который кладется в `HashMap`.
Приходит `BeanFactory` и из этой `HashMap` отдельно складывает все `BeanPostProcessor`’ы.
Создает из `BeanDefinition`’ов бины и кладет их в IoC-контейнер.
Тут приходят BPP и настраивают эти бины с помощью 2х методов.

Длинная версия:

`BeanDefinition` – это объект, который хранит в себе информацию о бине (мета информация).
Сюда входит: из какого класса его (бин) надо создать; scope; установлена ли ленивая инициализация;
нужно ли перед данным бином инициализировать другой и иные проперти, которые описаны в конфиге.

Все полученные `BeanDefinition`’ы складываются в `HashMap`, в которой идентификатором является имя бина (установленное вами
или присвоенное спрингом) и сам `BeanDefinition` объект.

В зависимости от того, какая у вас конфигурация, будет использоваться тот или иной механизм парсирования конфигурации.
Основные конфигурации это `ClassPathXmlApplicationContext` и `AnnotationConfigApplicationContext`.

После того, как все `BeanDefinition`’ы созданы на сцену выходит новый герой – `BeanFactory`.
Этот объект итерируется по `HashMap`’e с `BeanDefinition`’ами, создает на их основе бины и складывает в IoC контейнер.

Итерируясь по '`HashMap`’e с `BeanDefinition`’ами сперва создаются и кладутся отдельно (не в IoC контейнер) все `BeanPostProcessor`’ы.
После этого создаются обычные бины нашей бизнес-логики, складываются в IoC-контейнер и начинается их настройка с помощью отдельно отложенных BPP.

Происходит итерация по нашим бинам дважды.
В первый раз вызывается метод `postProcessorBeforeInitialization`, а во второй раз вызывается метод `postProcessorAfterInitialization`.
Это нужно для прокси-классов, при создании которого мета-информация теряется, поэтому обращаемся 2 раза когда нужно.


## Configuration

### Аналоги конфигураций разными способами
* [Configuring Spring MVC](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-config-enable)
* [Spring app migration: from XML to Java-based config](https://robinhowlett.com/blog/2013/02/13/spring-app-migration-from-xml-to-java-based-config/)


| JAVA config                                       | XML config                                                                |
|:--------------------------------------------------|:------------------------------------------------------------------------- |
| @Configuration                                    | `application-context.xml`                                                 |
| @Bean                                             | `<bean class="com.skprod.repository.UserRepository" id="userRepository"/>`|
| @ComponentScan(basePackages = "com.spring.rest")  | `<context:component-scan base-package="com.spring.mvc_hibernate_aop"/>`   |
| @EnableWebMvc                                     | `<mvc:annotation-driven/>`                                                |
| @EnableTransactionManagement                      | `<tx:annotation-driven transaction-manager="transactionManager"/>`        |


### Set up Environment and then load xml configuration
Бывают случаи, когда нужно настроить объект context (например, указать профили или определить драйвер БД (обычно при компиляции не работает - требует константу)), а потом загрузить ресурсы с `.xml`.
Для XML нужно использовать объект `GenericXmlApplicationContext`:
```java
try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
    ConfigurableEnvironment environment = appCtx.getEnvironment();
    environment.setActiveProfiles("postgres", "datajpa");
    appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
    Arrays.stream(appCtx.getBeanDefinitionNames()).forEach(System.out::println);
    appCtx.refresh();
```
Метод `refresh()` загружает или обновляет конфигурацию.



## Annotations
Аннотации – это специальные комментарии/метки/метаданные, которые нужны для передачи определённой информации.
Конфигурация с помощью аннотаций более короткий и быстрый способ, чем конфигурация с помощью XML файла.

Практически все XML-конфигурации можно повторить с помощью аннотаций.

По умолчанию использование аннотаций для Spring контейнера отключены. Использование аннотаций Spring - привязка к фреймворку. 
Поэтому лучше использовать как можно чаще стандартные аннотации для лучшей переносимости кода.

***

### Notes
`@PostConstruct` и `@PreDestroy` - Для работы этих аннотаций, которые были deprecated в версии java 9, а в java 11 удалены - нужно подключить зависимость:

[Javax Annotation API](https://stackoverflow.com/questions/46502001/how-to-get-access-to-javax-annotation-resource-at-run-time-in-java-9/46502132#46502132), например версии 1.3.2.

***

Стереотипы — это аннотации, обозначающие специальную функциональность. Все стереотипы включают в себя аннотацию `@Component`.

Аннотации-стереотипы (stereotypes): `@Component`, `@Controller`, `@RestController`, `@Service`, `@Repository` и `@Configuration`.

***

### Конфигурация с помощью аннотаций
Процесс состоит из 2-х этапов:
1. Сканирование классов и поиск аннотации `@Component`
2. Создание (регистрация) бина в Spring Container-е

Если к аннотации `@Component` не прописать bean id, то бину будет назначен дефолтный id: Cat -> cat..
Дефолтный bean id получается из имени класса, заменяя его первую заглавную букву на прописную.
Если имя класса начинается с 2 заглавных букв подряд, тогда дефолтный ид (имя бина) не меняется: SQLTest -> SQLTest.

### Пример конфигурации Spring Container-а с помощью Java кода

#### Способ 1:
Аннотация `@Configuration` означает, что данный класс является конфигурацией.
С помощью аннотации `@ComponentScan` мы показываем, какой пакет нужно сканировать на наличие бинови разных аннотаций.
При использовании конфигурации с помощью Java кода, Spring Container будет представлен классом `AnnotationConfigApplicationContext`.
```java
@Configuration
@ComponentScan("spring_introduction")
public class MyConfig {
    // ...
}
```

#### Способ 2:
* Данный способ не использует сканирование пакета и поиск бинов. Здесь бины описываются в конфиг классе.
* Данный способ не использует аннотацию `@Autowired`. Здесь зависимости прописываются вручную.
* Название метода – это **bean id**.
* Аннотация `@Bean` перехватывает все обращения к бину и регулирует его создание.
```java
@Configuration
public class MyConfig {

    @Bean
    @Scope("singleton")
    public Pet catBean() {
        return new Cat();
    }

    @Bean
    public Person personBean(){
        return new Person(catBean());
    }
}
```
***

### @Configuration
Используется для JavaConfig'a.
Эта аннотация, прописанная перед классом, означает, что класс может быть использован контейнером Spring IoC как конфигурационный класс для бинов.

***

### @Bean
Используется для JavaConfig'a.

Аннотация `@Bean`, прописанная перед методом, информирует Spring о том, что возвращаемый данным методом объект должен быть зарегистрирован, как бин.

**`@Bean` рекомендуется использовать вместе с `@Configuration`, а не с `@Component`.**

Аналог в XML тег `<bean>`.

***

### @ComponentScan
Ставится над классом, используется для конфигурирования Java кодом (JavaConfig).

Говорит Спрингу посмотреть на все классы Java в том же пакете, что и конфигурация контекста, если они выглядят как Spring Bean (помечены аннотацией `@Component`)!

Аналог в XML:
```xml
<context:component-scan base-package="ru.alishev.springcourse" />
```

***

### @Component
Spring создаст бин из класса над которым будет эта аннотация. 
Сканируется конфигурационным классом (`@Configuration`), у которого также должна быть аннотация `@ComponentScan.`

***

### @Controller
`@Controller` сообщает Spring, что этот класс хочет реагировать на HTTP-запросы и ответы, чтобы 'DispatcherServlet' знал об этом.


Это специализированный `@Component`.

***

### @Repository
Указывает, что класс является репозиторием для работы с БД.

Это специализированный `@Component`. Данная аннотация используется для **DAO**.

При поиске компонентов, Spring также будет регистрировать все **DAO** с аннотацией `@Repository` в **Spring Container-е**.

***

### @Service
Аннотация `@Service` отмечает класс, содержащий бизнес-логику. 
В иерархии компонентов приложения **Service** является соединительным звеном между **Controller-ом** и **DAO**.

`@Service` – это специализированный `@Component`. 
При поиске компонентов, Spring также будет регистрировать все классы с аннотацией `@Service` в Spring Container-е.

***

### @Controller
Сообщает Spring, что этот класс хочет реагировать на HTTP-запросы и ответы, чтобы `DispatcherServlet` знал об этом.

***

### @Autowired, @Lazy
> Если нужно сделать загрузку контекста с необязательным бином (например профиля), тогда нужно использовать аттрибут `required` у аннотации `@Autowired` или сразу использовать
> аннотацию `@Lazy`, которая инициализирует бин только после первого обращения к нему:
> ```java
> @Autowired(required = false)
> protected JpaUtil jpaUtil;
> ```
> ```java
> @Autowired
> @Lazy
> protected JpaUtil jpaUtil;
> ```

`@Lazy` - ленивая инициализация бина.

Обеспечивает контроль над тем, где и как автосвязывание должны быть осуществлено.
Можно применять `@Autowired` к сеттерам, обычным методам, конструкторам и свойствам.

Аннотация похожа на атрибут `autowire` в XML, но имеет возможность уточнять поиск бинов для внедрения.

**Как работает `@Autowired`:**
* **Spring** сначала сканирует все классы с аннотацией `@Component` и создает бины для этих классов;
* Затем **Spring** сканирует все созданные бины и проверяет, подходит ли хотя бы один бин в качестве зависимости там, где мы указали аннотацию `@Autowired`;
* Если **Spring** находит один подходящий бин, то этот бин внедряется в качестве зависимости;
* Если **Spring** НЕ находит подходящий бин - ошибка;
* Если **Spring** находит несколько подходящих бинов - ошибка (неоднознвчность) - для уточнения используй `@Qualifier`.

***

### @Qualifier
Аннотация предназначена для уточнения автоматического связывания.
Этот вид аннотаций используется вместе с аннотациями `@Autowired` когда возможна путаница при связывании (непонятно, с каким бином необходимо связать) и определяет конкретный бин. 
В реальной жизни может сложится ситуация, когда вы создаёте несколько бинов одного и того же типа, но в конкретном случае вам необходим конкретный бин. 
Для того, чтобы указать **Spring**, какой именно бин вам необходим, применяется аннотация `@Qualifier.`

***

### @Required
Означает, что требуется обязательное заполнение на этапе конфигурации.

Применяется к методам-сеттерам и означает, что **значение метода должно быть установлено в XML-файле**. 
Если этого не будет сделано, то мы получим `BeanInitializationException`.

***



## Event Processing
There are four components involved in event processing:
* __Source__ - to publish event in spring we must have `ApplicationEventPublisher` object.
* __Event__ - event must be extends from `ApplicationEvent`.
* __Listener__ - in _Spring IoC_ container acts as a _Listener_.
* __Handler__ - handler method must annotate with `@EventListener`.

### Events
Одним из ключевых элементов _Spring_ является `ApplicationContext`, который управляет жизненным циклом бинов. 
В процессе своей работы он вызывает целый ряд, так называемых, событий (`ContextStoppedEvent`, `ContextStartedEvent` и т.д.).
Обработка этих событий обеспечивается классов `ApplicationEvent` и интерфейсом `ApplicationListener`. 
И когда бин иплементирует интерфейс `ApplicationListener`, то каждый раз, когда вызывается то или иное событие, бин получает об этом информацию.

Обработка событий в _Spring_ однопоточна, а значит, если событие опубликовано, то все процессы будут блокированы, пока все адресаты не получат сообщение.

[Подробнее об некоторых Event'ах - proselyte](https://proselyte.net/tutorials/spring-tutorial-full-version/event-handling/)

***

#### How to consume the published events?
Starting from version 4.2, Spring supports an annotation-driven event listener – `@EventListener`.

We can make use of this annotation to automatically register an `ApplicationListener` based on the signature of the method:
```java
@EventListener
public void handleContextRefreshEvent(ContextStartedEvent ctxStartEvt) {
    System.out.println("Context Start Event received.");
}
```
A method annotated with `@EventListener` can return a non-void type. 
If the value returned is non-null, the eventing mechanism will publish a new event for it.


For listening to Multiple Events:
```java
@EventListener(classes = { ContextStartedEvent.class, ContextStoppedEvent.class })
public void handleMultipleEvents() {
    System.out.println("Multi-event listener invoked");
}
```
As of Spring Framework 4.2, the ApplicationEventPublisher interface provides a new overload for the publishEvent(Object event) method that accepts any object as the event. 
__Therefore, Spring events no longer need to extend the `ApplicationEvent` class.__

It is also possible to make the event listener conditional by defining a boolean _Spring Expression Language (SpEL)_ expression on the `@EventListener` annotation.
```java
@Component
public class AnnotationDrivenEventListener {
    @EventListener(condition = "#event.success")
    public void handleSuccessful(GenericSpringEvent<String> event) {
        System.out.println("Handling generic event (conditional).");
    }
}
```

## Profiles
* [Baeldung - Spring Profiles](https://www.baeldung.com/spring-profiles)
* [Spring Profiles Java and XML Configuration](https://memorynotfound.com/spring-profiles-java-xml-configuration/)

> Общие для нескольких профилей свойства можно выносить в общий блок, перечислив в декларации профилей их наименования через запятую. 
> Следует обратить особое внимание на то, что тег <beans> в котором объявляются профили и их конфигурация, может располагаться только в 
> самом конце файла конфигурации после всех остальных настроек. 
> Intellij Idea предоставляет интерфейс для переключения между профилями Spring, настроенными в конфигурации (такое переключение влияет 
> только на отображение файла конфигурации, но не оказывает никакого влияния на запуск и работу приложения).

> Profile names can also be prefixed with a NOT operator, e.g., !dev, to exclude them from a profile.

> Профили в Spring добавляются после бинов - в конце можно использовать тег beans.

### Автоматический выбор профиля базы: [ActiveProfilesResolver](https://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver)

> Автоматический выбор профиля базы при запуске приложения (тестов) в зависимости от присутствия драйвера базы в `classpath` (`ActiveDbProfileResolver`).

Сначала создаем резолвер:
```java
import org.springframework.lang.NonNull;
import org.springframework.test.context.ActiveProfilesResolver;

public class ActiveDbProfileResolver implements ActiveProfilesResolver {
    @Override
    public @NonNull
    String[] resolve(@NonNull Class<?> aClass) {
        return new String[]{Profiles.getActiveDbProfile()};
    }
}
```
Далее класс с профилями:
```java
import org.springframework.util.ClassUtils;

public class Profiles {
    public static final String
            JDBC = "jdbc",
            JPA = "jpa";

    public static final String REPOSITORY_IMPLEMENTATION = JPA;

    public static final String
            POSTGRES_DB = "postgres",
            HSQL_DB = "hsqldb";

    //  Get DB profile depending on DB driver in classpath
    public static String getActiveDbProfile() {
        if (ClassUtils.isPresent("org.postgresql.Driver", null)) {
            return POSTGRES_DB;
        } else if (ClassUtils.isPresent("org.hsqldb.jdbcDriver", null)) {
            return HSQL_DB;
        } else {
            throw new IllegalStateException("Could not find DB driver");
        }
    }
}
```
И над тестовым классом указываем наш резолвер:
```java
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
public class MealServiceTest {
    // some code
}
```
Сам профиль выбирается во вкладке Maven.

> Атрибуты `resolver` и `profiles` в одном `@ActiveProfiles` вместе не работают (см. org.springframework.test.context.support.ActiveProfilesUtils#resolveActiveProfiles). 
> `@ActiveProfiles` принимает в качестве параметра строку, либо массив строк. 
> В тестах можно задавать несколько `@ActiveProfiles` в разных классах, они суммируются.



## Transactions
* [Spring Transaction Management](https://www.tutorialspoint.com/spring/spring_transaction_management.htm)
* [Эффективное управление транзакциями в Spring](https://habr.com/ru/company/otus/blog/431508/)
* [Spring @Transactional - isolation, propagation](https://stackoverflow.com/questions/8490852/spring-transactional-isolation-propagation)

> Рекомендуется выполнять все операции (включая чтение) в транзакции.

> Откуда `@Transactional` вытягивает класс для работы с транзакцией, в составе какого бина он идет?
> 
> Если в контексте **Spring** есть `<tx:annotation-driven/>`, то подключается `BeanPostProcessors`, который проксирует классы (и методы), помеченные `@Transactional`.
> По умолчанию для `TransactionManager` используется бин с `id=transactionManager`.

> Сделаем в `UserService` метод enable, который принимает boolean (вкл./выкл. пользователя). 
> В методе загружаем из базы нужного пользователя, устанавливаем ему значение enabled и записываем обновленного пользователя обратно в базу. 
> `repository.save(user)` нужен только для JDBC реализации, в JPA изменения сущностей в `@Transactional` методах попадают в базу автоматически. 
> Метод помечен аннотацией `@Transactional`, чтобы все действия в методе выполнялись в одной транзакции. 

***

**На каком слое открывать транзакцию...**
Мы можем не открывать транзакцию на уровне сервиса, если у нас нет нескольких запросов к нескольким репозиториям. 
Т.е. если один репозиторий, тогда можно открывать их на уровне доступа к базе - репозиторий, если несколько - сервис.

***

Для подключения к Spring нужно добавить бин на основе JPA и указать в конфигурации специальную строку:
```xml
<tx:annotation-driven/>
		
<!-- Transaction manager for a single JPA EntityManagerFactory (alternative to JTA) -->
<bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager"
      p:entityManagerFactory-ref="entityManagerFactory"/>
```
> Чтобы посмотреть информацию о транзакциях (открытие/закрытие и пр.), можно выставить в конфигурации logback `<logger name="org.springframework.orm.jpa.JpaTransactionManager" level="debug"/>`

***


### Propagation
`PROPAGATION` определяет, как транзакции связаны друг с другом.

Основное распространение транзакций - `PROPAGATION`:
* `REQUIRED` - используется по умолчанию. Использует текущую транзакцию, если нет - будет создана новая.
* `SUPPORT` - использовать текущую транзакцию, если нет - новая НЕ создается, т.е. будет выполнен код без транзакции.
* `MANDATORY` - ожидает в этом месте открытую транзакцию, иначе - exception.
* `NOT_SUPPORTED` - перед выполнением блока кода существующая транзакция будет приостановлена.
* `NEVER` - транзакции не должно быть, если существует - exception.
* `NESTED` - вложенная - открывается новая транзакция внутри пришедшей.
* `REQUIRES_NEW` - всегда открывается новая транзакция внутри пришедшей.
  Приостанавливает текущую транзакцию, если таковая существует.

### Isolation
Определяет контракт данных между транзакциями.

* `ISOLATION_READ_UNCOMMITTED` - разрешает грязное чтение.
* `ISOLATION_READ_COMMITTED` - не разрешает грязное чтение.
* `ISOLATION_REPEATABLE_READ` - если строка читается дважды в одной транзакции, результат всегда будет одинаковым.
* `ISOLATION_SERIALIZABLE` - выполняет все транзакции последовательно.
  Если выполняется одна транзакция, тогда все последующие будут ожидать её завершения.


## Проксирование

### [TopJava - Принцип работы Spring Security. Проксирование](https://github.com/JavaWebinar/topjava/blob/doc/doc/lesson09.md#-9--принцип-работы-spring-security-проксирование)
Проксирование чаще всего задается аннотациями: при поднятии приложения и создании контекста на основе пре-процессоров Spring анализирует аннотации бинов и, 
находя указание к проксированию, создает прокси (обертку) над исходным объектом. В контекст Spring попадает уже не исходный инстанс класса, а его прокси. 

В Spring используется две стратегии проксирования:
* на основе JDK 4 Dynamic Proxy API - прокси-объект создаются как обертка ко всем интерфейсам, которые имплементирует сервис.
* на основе CGLib - когда нет интерфейсов, прокси объект создается на уровне модификации байт-кода класса.

По умолчанию, если класс имплементирует интерфейсы, проксирование происходит по стратегии Dynamic Proxy и в прокси мы имеем только методы интерфейсов. 
Стратегию проксирования можно поменять на CGLib, задав явно в конфигурациях параметра `proxy-target-class` или, как сделали мы, 
аннотацию `@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)`. 
В результате прокси нашего UserService сделано через CGLib ив нем доступны все его методы. 
Второй путь - создать и реализовать интерфейс, в котором есть всем методы класса.

Работа Spring Security основывается на цепочке Security-фильтров. 
HTTP запрос, перед тем как поступить в `DispatcherServlet` проходит цепочку фильтров (стандартная функциональность Servlet API). 
Spring предоставляет собственную цепочку стандартных фильтров и возможность отключать/заменять любые фильтры из этой цепочки или внедрять в нее собственные фильтры.




## Spring Internationalization

### По примеру TopJava
* [Learning the code way](http://learningviacode.blogspot.com/2012/07/reloadable-messagesources.html)
Spring нормально работает с кириллицей. 
Spring также автоматически может изменять локаль приложения, для этого в конфигурации `spring-mvc.xml` ему нужно определить 
`ReloadableResourceBundleMessageSource`, который будет отвечать за локализацию и указать для него путь к **Bundles** с локализованными данными.

В страницах **JSP** мы также должны указать, что теперь будем работать не через **JSTL**, а через **Spring локализацию**. 
Для этого в страницах удаляем тег `fmt:setBundle`. Теперь **Spring** автоматически будет подставлять сообщения в зависимости от локали. 
Но сейчас **Spring** работает на основании **JDK ResourceBundle** и он игнорирует свойство `p:cacheSeconds="5"`, так как ресурсы интернационализации будут кэшироваться Java. 
Чтобы ресурсы не кэшировались, нужно использовать бин `ReloadableResourceBundleMessageSource` с путем к локализации, отличным от `classpath` приложения.

```xml
<bean id="messageSource" class="org.springframework.context.support.ReloadableResourceBundleMessageSource"
        p:cacheSeconds="5"
        p:defaultEncoding="UTF-8">
    <property name="basenames" value="file:///#{systemEnvironment[TOPJAVA_ROOT]}/config/messages/app"/>
    <property name="fallbackToSystemLocale" value="false"/>
</bean>
```
Теперь ресурсы интернационализации не будут кэшироваться и их можно будет менять во время работы приложения "на ходу" (runtime).

> Выбор языка зависит от языка операционной системы и заголовка `Accept-Language`.

> Для тестирования локали можно поменять `Accept-Language`. 
> Для Хрома в `chrome://settings/languages` перетащить нужную локаль наверх или поставить плагин **Locale Switcher**.


## Questions
Q: **Как указать несколько разных имен в бине?**<br>
A: Внутри аттрибута `name` и тега `alias`.

***

Q: **Как указать тип конструктора, который инжектится?**<br>
A: `<constructor-arg type="java.lang.Integer"/>`.

***

Q: **Как инжектить другие бины использую конструктор?**<br>
A: `<constructor-arg ref="bean"/>`, где `ref` - это ссылка на другой бин.

***

Q: **Какой контейнер спринга поддерживает инжект бинов (dependencies injection)?**<br>
A: `BeanFactory`.

***

Q: **Как достать бин из контейнера?**<br>
A: `ctx.getBean("beanName")`.

***

Q: **Какие модули фреймворка Spring являются частью DataAccess?**<br>
A: JMS, JPA.

***

Q: **Какие зоны видимости (scope) у бина?**<br>
A: 
* `singleton` - Определяет один единственный бин для каждого контейнера Spring IoC (используется по умолчанию). Потокобезопасный.
* `prototype` - Позволяет иметь любое количество экземпляров бина. Не потокобезопасный!
* `request` - Создаётся один экземпляр бина на каждый HTTP-запрос. Касается исключительно `ApplicationContext`.
* `session` - Создаётся один экземпляр бина на каждую HTTP-сессию. Касается исключительно `ApplicationContext`.
* `application` - Создаётся один экземпляр бина на жизненный цикл `ServletContext`.
* `websocket` - Создаётся один экземпляр бина на конкретную WebSocket сессию.

Последние 4 используются при разработке веб-приложения.

***

Q: **Какая разница внедрения зависимостей через поля, сеттеры и конструкторы?**<br>
A: [source](https://habr.com/ru/post/334636/)
1. **Поля**. Не может использоваться для присвоения зависимостей `final`-полям, что приводит к тому, что ваши объекты становятся изменяемыми.
При внедрении прямо в поля вы не предоставляете прямого способа создания экземпляра класса со всеми необходимыми зависимостями. Это означает, что:
   * Путем вызова конструктора по-умолчанию можно создать объект с использованием `new` в состоянии, когда ему не хватает некоторых из его обязательных зависимостей, и использование приведет к `NullPointerException`. 
   * Такой класс не может быть использован вне DI-контейнеров (тесты, другие модули) и нет способа кроме рефлексии предоставить ему необходимые зависимости.

2. **Сеттеры**. Сеттеры следует использовать для инъекций опциональных зависимостей.
Класс должен быть способен функционировать, даже если они не были предоставлены. 
Зависимости могут быть изменены в любое время после создания объекта.  

3. **Конструкторы**. Инъекция через конструкторы хороша для обязательных зависимостей — тех, которые требуются для корректной функциональности объекта. 
Передавая их через конструктор, вы можете быть уверенными в том, что объект полностью готов к использованию с момента создания. 
Поля, присвоенные в конструкторе, также могут быть `final`, что позволяет объекту быть полностью неизменным или как минимум защищает необходимые поля.

***

Q: **Что соответствует унаследованию от другого бина?**<br>
A:

1. область видимости (scope);
2. значения в property;
3. аргументы конструктора;
4. init и destroy методы.

***

Q: **Что означает если бин имеет аттрибут `abstract="true"`?**<br>
A: Аттрибут класса может быть пропущен; Бин не реализован в контейнере.

***

Q: **Если у бина установлен аттрибут `parent`, значит ли это, что класс этого бина унаследован из класса родительского бина?**<br>
A: Нет.

***

Q: **Если бин `A` имеет аттрибут `depends-on="B"`, что это означает?**<br>
A: Бин `A` инициализируется после инициализации бина `B`. Т.е. бин `A` создастся после создания бина `B`.

***

Q: **Если бин имеет аттрибут `lazy-init="true"`, когда он будет инициализирован?**<br>
A: Когда бин получит контекст, т.е. когда бин будет инжектин (внедрен) в другой бин.

***

Q: **Какие этапы инициализации контекста Spring'а?**<br>
A:

Сначала парсится конфигурации, и на основе её создается `BeanDefinition`, который кладется в `HashMap`.
Приходит `BeanFactory` и из этой `HashMap` отдельно складывает все `BeanPostProcessor`’ы.
Создает из `BeanDefinition`’ов бины и кладет их в IoC-контейнер.
Тут приходят BPP и настраивают эти бины с помощью 2х методов.

***



