# Reactive Java

<details>
<summary>SHOW MENU</summary>

- [Handling Concurrent Requests]()
- [API Design]()
- [What is Reactive Programming?]()
- [What is a Reactive Streams Specification have?]()
  - [1. Publisher interface]()
  - [2. Subscriber interface]()
  - [3. Subscription interface]()
  - [4. Processor interface]()
- [What is a Reactive Library?]()
  - [Reactive Libraries]()
- [Reactor]()
  - [Flux]()
  - [Mono]()
  - [Функциональный стиль с использованием Handler, получаем строку]()
  - [Функциональный стиль с использованием Handler, получаем JSON]()
  - [Декларативный стиль с использованием Controller]()
  
- [Another information]()

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

## Another information
...
  
