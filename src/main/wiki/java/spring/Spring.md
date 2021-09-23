# Spring

* [Developing with Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html)


## IoC - Inversion of Control (инверсия управления)
* Если используется инверсия контроля (IoC), нельзя создавать объекты через `new`.


## Configuration
| JAVA config                                       | XML config                                                                |
|:--------------------------------------------------|:--------------------------------------------------------------------------|
| @Configuration                                    | application-context.xml                                                   |
| @ComponentScan(basePackages = "com.spring.rest")  | <context:component-scan base-package="com.spring.mvc_hibernate_aop" />    |
| @EnableWebMvc                                     | <mvc:annotation-driven/>                                                  |
| @EnableTransactionManagement                      | <tx:annotation-driven transaction-manager="transactionManager" />         |


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


