# Reactive Java
Last update at 4/19/2021, 11:05AM

<details>
<summary>SHOW MENU</summary>

- [Handling Concurrent Requests](#handling-concurrent-requests)
- [API Design](#api-design)
- [What is Reactive Programming?](#what-is-reactive-programming)
- [What is a Reactive Streams Specification have?](#what-is-a-reactive-streams-specification-have)
  - [1. Publisher interface](#1-publisher-interface)
  - [2. Subscriber interface](#2-subscriber-interface)
  - [3. Subscription interface](#3-subscription-interface)
  - [4. Processor interface](#4-processor-interface)
- [What is a Reactive Library?](#what-is-a-reactive-library)
  - [Reactive Libraries](#reactive-libraries)
- [Reactor](#reactor)
  - [Flux](#flux)
  - [Mono](#mono)
  - [Функциональный стиль с использованием Handler, получаем строку](#функциональный-стиль-с-использованием-handler-получаем-строку)
  - [Функциональный стиль с использованием Handler, получаем JSON](#функциональный-стиль-с-использованием-handler-получаем-json)
  - [Декларативный стиль с использованием Controller](#декларативный-стиль-с-использованием-controller)
- [How to test?](#testing)
- [Build the simple Non Blocking API](#build-the-simple-non-blocking-api)
- [JUNIT : Test Reactive API using WebTestClient](#junit-test-reactive-api-using-webtestclient)
- [Junit : Test for infinite Non Blocking Sequence API using WebTestClient](#junit-test-for-infinite-non-blocking-sequence-api-using-webtestclient)
- [Пример популяции БД при старте приложения](#пример-популяции-бд-при-старте-приложения)
- [Spring WebClient](#spring-webclient)
  - Spring 5 WebClient and WebTestClient Tutorial with Examples
  - Spring WebClient – GET, PUT, POST, DELETE examples
- [Another information](#another-information)
  - Spring Reactive Examples

</details>


## Handling Concurrent Requests
> ПЕРЕКЛАД: Обробка паралельних запитів

By default, Tomcat sets maxThreads to 200, which represents the maximum number of threads allowed to run at any given time. Can be overriden in `application.properties` or `application.yml` file.

## API Design:
- **Asynchromous** and Non Blocking.
- Move away from **Thread per request** model.
- Use fewer threads.
- **Back Pressure** compatible.

## What is Reactive Programming?
* New programming paradigm.
* Asynchronous and Non Blocking.
* Data flow as an Event/Message Driven stream.
    * One Event or Message for a every result item from Data Source (DB, external service, file...).
    * One Event or Message for completion or error.
* Functional style code.
    * Similar to **Streams API**.
    * Easy to work with **Lambdas**.
* Back Pressure on Data Streams.

## What is a Reactive Streams Specification have?
Read more at [GitHub](https://github.com/reactive-streams/reactive-streams-jvm).

It has 4 interfaces (API Components):
1) Publisher
2) Subscriber
3) Subscription
4) Processor

### 1. Publisher interface
Represents the Data Source. It has 1 method.
```java
public interface Publisher<T> {
	public void subscriber(Subscriber<? super T> s);
}
```

### 2. Subscriber interface
Subscriber going to read the data from the Publisher. Basically Subscriber is synonym to Consumer.
It has 4 methods.
```java
public interface Subscriber<T> {
	public void onSubscribe(Subscription s);
	public void onNext(T t);
	public void onError(Throwable t);
	public void onComplete();
}
```

### 3. Subscription interface
It has 2 methods.
```java
public interface Subscription {
	public void request(long n);
	public void cancel();
}
```

### 4. Processor interface
This interface is the combination of both the Subscriber and Publisher interface. There are no any specific method inside this Processor interface.
```java
public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {
}
```

## What is a Reactive Library?
A: Implementation of Reactive Streams Specification (Publisher, Subscriber, Subscription, Processor).

### Reactive Libraries
- RxJava
- Reactor
- **Flow** Class - JDK9

## Reactor
> [DOCUMENTATION](https://projectreactor.io/docs)

Весь Reactor в основном построен на 2 объектах - __Flux__ (0 - N) и __Mono__ (0 - 1).
Они являются наследниками интерфейса Publisher.

Reactor or Project Reactor is recommended library to work with Spring Boot.
It comes with Spring Boot by default.

### [Flux](https://projectreactor.io/docs/core/release/reference/#flux)
![](https://projectreactor.io/docs/core/release/reference/images/flux.svg)


### [Mono](https://projectreactor.io/docs/core/release/reference/#mono)
![](https://projectreactor.io/docs/core/release/reference/images/mono.svg)

### Функциональный стиль с использованием Handler, получаем строку
Создаем бин `GreetingHandler`, который будем использовать в `RouterFunction`.

> **ServerRequest** represents the **HttpRequest**.<br>
> **ServerResponse** represents the **HttpResponse**.
```java
@Component
public class GreetingHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        BodyInserter<String, ReactiveHttpOutputMessage> body = 
                BodyInserters.fromValue("Hello, Spring!");
        
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(body);
    }

    public Mono<ServerResponse> index(ServerRequest serverRequest) {
        String user = serverRequest.queryParam("user")
                .orElse("Nobody");
        
        return ServerResponse
                .ok()
                .render("index", Map.of("user", user));

    }
}
```
```java
@Configuration
public class GreetingRouter {
    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {
        RequestPredicate route = RequestPredicates
                .GET("/hello")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN));

        return RouterFunctions
                .route(route, greetingHandler::hello)
                .andRoute(RequestPredicates.GET("/"),
                        greetingHandler::index
                );
    }
}
```
```html
// HTML
Hello, {{user}}
```
С таким кодом в строке запроса `/hello` получим вывод на странице __"Hello, Spring!"__, а на `/` - __"Hello, {{user}}"__, где {{user}} имя параметра user в строке, например `/?user=Bob`.

### Функциональный стиль с использованием Handler, получаем JSON
Пример с пагинацией и фильтрацией. Всё тоже самое что и выше, меняем код только в `GreetingHandler`.

```java
@Component
public class GreetingHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        Long start = request.queryParam("start")
                .map(Long::valueOf)
                .orElse(0L); // если null, то будет 0
        Long count = request.queryParam("count")
                .map(Long::valueOf)
                .orElse(3L); // если null, то будет 3

        Flux<Message> data = Flux
                .just(
                        "Hello, rective!",
                        "More then one",
                        "Third post",
                        "Fourth post",
                        "Fifth post"
                )
                .skip(start) // с какого элемента стартуем
                .take(count) // сколько элементов взять
                .map(Message::new);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data, Message.class);
    }

    public Mono<ServerResponse> index(ServerRequest serverRequest) {
        String user = serverRequest.queryParam("user")
                .orElse("Nobody");

        return ServerResponse
                .ok()
                .render("index", Map.of("user", user));
    }
}
```
Например, по адресу `/hello?start=2&count=3` c пагинацией получим JSON-результат:
```json
[
    {
        "data": "Third post"
    },
    {
        "data": "Fourth post"
    },
    {
        "data": "Fifth post"
    }
]
```
### Декларативный стиль с использованием Controller
Это полный аналог кода выше, только через Spring Rest. @RestController возвращает JSON.
```java
@RestController
@RequestMapping("/controller")
public class MainController {
    @GetMapping
    public Flux<Message> list(
            @RequestParam(defaultValue = "0") Long start,
            @RequestParam(defaultValue = "3") Long count
    ) {
        return Flux
                .just(
                        "Hello, rective!",
                        "More then one",
                        "Third post",
                        "Fourth post",
                        "Fifth post"
                )
                .skip(start) // с какого элемента стартуем
                .take(count) // сколько элементов взять
                .map(Message::new);
    }
}
```

## Testing
1. Example - expecting list in same order:
```java
// Flux
@Test
public void fluxTestElements_WithoutError() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
            .log();

    StepVerifier.create(stringFlux)
            .expectNext("Spring")
            .expectNext("Spring Boot")
            .expectNext("Reactive Spring")
            .verifyComplete();
}
```
```java
// Mono
@Test
public void monoTest() {
    Mono<String> stringMono = Mono.just("Spring");

    StepVerifier.create(stringMono.log())
            .expectNext("Spring")
            .verifyComplete();
}
```

2. Example - expecting an error (exception) or error message.
For expecting any type of error or it messages, you need to use `verify()` method.
Both of this we can't have!
```java
// Flux
@Test
public void fluxTestElements_WithoutError() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
            .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
            .log();

    StepVerifier.create(stringFlux)
            .expectNext("Spring", "Spring Boot", "Reactive Spring") // varargs
//               .expectError(RuntimeException.class)
            .expectErrorMessage("Exception Occurred")
            .verify();
}
```
```java
// Mono
@Test
public void monoTest_Error() {
    StepVerifier.create(Mono.error(new RuntimeException("Exception Occurred")).log())
            .expectError(RuntimeException.class)
            .verify();
}
```

3. Example - expecting some count of elements:
```java
@Test
public void fluxTestElementsCount_WithoutError() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
            .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
            .log();

    StepVerifier.create(stringFlux)
            .expectNextCount(3)
            .expectErrorMessage("Exception Occurred")
            .verify();
}
```

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) [Build the simple Non Blocking API](https://youtu.be/LHQM9ZCfPNA)
Full code look at [GitHub](https://github.com/SergiaS/learnReactiveSpring/commit/484b3aae5132efbcb88ca19943e0768a5e94035f).
```java
@RestController
public class FluxAndMonoController {

    @GetMapping("/flux")
    public Flux<Integer> returnFlux() {
        return Flux.just(1,2,3,4)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> returnFluxStream() {
        return Flux.just(1,2,3,4)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }
}
```
> Example WITHOUT dependency `spring-boot-starter-data-mongodb-reactive`.
* По ендпоиту `/flux` браузер будет ждать 4 секунды пока не получит весь ответ.
* По ендпоиту `/fluxstream` браузер будет отображать каждую секунду на один элемент больше.

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) [JUNIT : Test Reactive API using WebTestClient](https://youtu.be/ao21gIB4qKw)
Full code look at [GitHub](https://github.com/SergiaS/learnReactiveSpring/commit/70da4c7580c58a2a94c277b4dd4272f343bbf935).

Annotation `@WebFluxTest` will create the instance of `WebTestClient`.<br>
Annotation `@WebFluxTest` is scanning only `@Controller` annotation! If you initialized `WebTestClient` not by controller, you need add class annotation `@AutoConfigureWebTestClient`.
```java
@WebFluxTest
class FluxAndMonoControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void flux_approach1() {
        Flux<Integer> integerFlux = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange() // invoke the endpoint
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        // Next step - evaluate the values from integerFlux variable
        StepVerifier.create(integerFlux)
                .expectSubscription()
                .expectNext(1, 2, 3, 4)
                .verifyComplete();
    }

    @Test
    void flux_approach2() {
        webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange() // invoke the endpoint
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Integer.class)
                .hasSize(5);
    }

    @Test
    void flux_approach3() {
        List<Integer> expectedIntegerList = List.of(1, 2, 3, 4);

        EntityExchangeResult<List<Integer>> entityExchangeResult = webTestClient
                .get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange() // invoke the endpoint
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();

        Assertions.assertEquals(expectedIntegerList, entityExchangeResult.getResponseBody());
    }

    @Test
    void flux_approach4() {
        List<Integer> expectedIntegerList = List.of(1, 2, 3, 4);

        webTestClient
                .get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange() // invoke the endpoint
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .consumeWith(response -> {
                    Assertions.assertEquals(expectedIntegerList, response.getResponseBody());
                });
    }
}
```

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) [Junit : Test for infinite Non Blocking Sequence API using WebTestClient](https://youtu.be/nO8F4IO0BVo)
Full code look at [GitHub](https://github.com/SergiaS/learnReactiveSpring/commit/7e57e3c8725fddefeacac67551ca97d95faa5d1c).

Example for APPLICATION_STREAM endpoint.
```java
@RestController
public class FluxAndMonoController {
    @GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> returnFluxStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .log();
    }
}
```
```java
@WebFluxTest
class FluxAndMonoControllerTest {

    @Autowired
    WebTestClient webTestClient;
  
    @Test
    public void fluxStream() {
      Flux<Long> longStreamFlux = webTestClient.get().uri("/fluxstream")
              .accept(MediaType.APPLICATION_STREAM_JSON)
              .exchange() // invoke the endpoint
              .expectStatus().isOk()
              .returnResult(Long.class)
              .getResponseBody();
  
      StepVerifier.create(longStreamFlux)
              .expectNext(0L,1L,2L)
              .thenCancel()
              .verify();
    }
}
```
## Пример популяции БД при старте приложения
Один из способов популяции БД с использованием `ReactiveCrudRepository` - создание бина `CommandLineRunner`:
```java
@Bean
public CommandLineRunner initConfig(ReactiveCrudRepository<NodeRoot, String> repo) {
    return (p) -> {
        repo.deleteAll().block();
        repo.save(new NodeRoot("Bob")).block();
        repo.save(new NodeRoot("Matt")).block();
        repo.save(new NodeRoot("Elis")).block();
        repo.save(new NodeRoot("Ted")).block();
        repo.save(new NodeRoot("Jenny")).block();
        repo.save(new NodeRoot("Nora")).block();
    };
}
```
Другой вариант - имплементировать `CommandLineRunner` и переопределить метод `run()`.


## Spring WebClient
- [Spring 5 WebClient and WebTestClient Tutorial with Examples](https://www.callicoder.com/spring-5-reactive-webclient-webtestclient-examples/)
- [Spring WebClient – GET, PUT, POST, DELETE examples](https://howtodoinjava.com/spring-webflux/webclient-get-post-example/)

## Another information
- [Spring Reactive Examples](https://github.com/lokeshgupta1981/SpringExamples/tree/master/spring-reactive)


...
  
