# Spring Boot
* [Developing with Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html)
* [How to avoid SSL validation in Spring Boot RestTemplate?](https://medium.com/@reachansari/how-to-avoid-ssl-validation-in-spring-boot-resttemplate-3876a7fc2c4a)
* [Как работает Spring Boot Auto-Configuration](https://habr.com/ru/post/487980/)
* [Quick Guide on Loading Initial Data with Spring Boot](https://www.baeldung.com/spring-boot-data-sql-and-schema-sql)
* [Common application Data properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties.data)
* [Database Initialization](https://docs.spring.io/spring-boot/docs/2.6.2/reference/html/howto.html#howto.data-initialization)
  > By default, SQL database initialization is only performed when using an embedded in-memory database.
* [SpringBoot Common Application Properties - JSON Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties.json)


> Spring Boot включает в себя интерфейс командной строки.

> Чтобы посмотреть какие бины инициализированы (автоконфигурация классов), нужно в `application.properties` добавить
> `logging.level.org.springframework=debug` и запустить приложение. Смотрим **Positive matches** и **Negative matches**.

> Якщо при запиті сталася помилка, більш детальну інфу цієї помилки можна повертати у відповіді додавши команди у `application.properties`:
> ```properties
> server.error.include-message=always
> server.error.include-binding-errors=always
> server.error.include-stacktrace=always
> ```
> Щоб дивитися **stacktrace** лише за запитом, тоді робимо запит з `?trace=true` виставляємо варіант `on_trace_param`
> ```properties
> server.error.include-stacktrace=on_trace_param
> ```

## Запуск SpringBoot через консоль
Запустити Spring Boot на віддаленому сервері зазвичай роблять через консоль, оскільки GUI немає:

1. Спочатку знаходимо теку з проєктом;

2. Далі необхідно запустити **Maven**, якщо його немає на сервері - тоді використовуй скрипти мавена які генерує **Spring Initializr**: `./mvnw` - для MacOC і Linux, `mvnw` для Windows; Запускаємо команду мавена:
    ```commandline
    mvnw package
    ```
3. Після того, як Maven збере на сервері build у один jar-файл, його треба запустити. Цей jar-файл лежить у теці `target` - переходимо в неї `cd target` і запускаємо:
    ```commandline
    java -jar SpringBootApp-0.0.1-Snapshot.jar
    ```
4. Зупинити сервер можна командою `CTRL + C`.


## MessageSource
Работа и локалями и интернализацией, обращается к папке `resources` и решает какую локаль брать.
Пример одной из общих реализаций:
```java
@Bean
public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource
      = new ReloadableResourceBundleMessageSource();
    
    messageSource.setBasename("classpath:messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
}
```


##[Spring Boot SSL [https] Example](https://howtodoinjava.com/spring-boot/spring-boot-ssl-https-example/)


## Дефолтная предзагрузка данных
* [Spring Boot: ApplicationRunner and CommandLineRunner](https://dzone.com/articles/spring-boot-applicationrunner-and-commandlinerunne)

### CommandLineRunner
Один из способов популяции БД - создание бина:
```java
// Пример на ReactiveCrudRepository
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
Можно использовать для простых приложений `CommandLineRunner`.
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(EmployeeRepository repository) {
    return args -> {
      log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
      log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
    };
  }
}
```

## Annotations

### @SpringBootApplication
Включает в себя сразу несколько аннотаций: `@Configuration`, `@EnableAutoConfiguration` и `@ComponentScan`.

* `@SpringBootApplication` is a convenience annotation that adds all of the following:
    * `@Configuration`: Tags the class as a source of bean definitions for the application context.
    * `@EnableAutoConfiguration`: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings. For example, if spring-webmvc is on the classpath, this annotation flags the application as a web application and activates key behaviors, such as setting up a `DispatcherServlet`.
    * `@ComponentScan`: Tells Spring to look for other components, configurations, and services in the `com/example` package, letting it find the controllers.
    
```java
package com.example.servingwebcontent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServingWebContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

}
```
The `main()` method uses Spring Boot’s `SpringApplication.run()` method to launch an application. 
Did you notice that there was not a single line of XML? **There is no `web.xml` file, either**. 
This web application is 100% pure Java and you did not have to deal with configuring any plumbing or infrastructure.


### @ConfigurationProperties
Щоб запрацювала анотація, необхідно підключити додаткову залежність:
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-configuration-processor</artifactId>
  <optional>true</optional>
</dependency>
```

> <details>
> <summary>ПОКАЗАТИ ПРИКЛАД</summary>
> 
> Маємо такі дані в файлі `application.properties`:
> ```properties
> info.app.name=Spring Boot Master Class Course
> info.app.description=Master Spring Boot
> info.app.version=1.0.0
> ```
> і такий клас, котрий підхватить все сам - тільки вкажи префікс
> ```java
> @Configuration
> @ConfigurationProperties(prefix = "info.app")
> public class InfoApp {
>   private String name;
>   private String description;
>   private String version;
> 
>   public String getName() {
>     return name;
>   }
> 
>   public void setName(String name) {
>     this.name = name;
>   }
> 
>   public String getDescription() {
>     return description;
>   }
> 
>   public void setDescription(String description) {
>     this.description = description;
>   }
> 
>   public String getVersion() {
>     return version;
>   }
> 
>   public void setVersion(String version) {
>     this.version = version;
>   }
> 
>   @Override
>   public String toString() {
>     return "InfoApp{" +
>         "name='" + name + '\'' +
>         ", description='" + description + '\'' +
>         ", version='" + version + '\'' +
>         '}';
>   }
> }
> ```
> Подивитися результат можна додавши бін з `CommandLineRunner`:
> ```java
> @Bean
> CommandLineRunner commandLineRunner(InfoApp infoApp) {
>   return args -> {
>     System.out.println("Command line runner hooray");
>     System.out.println(infoApp);
>   };
> }
> ```
> 
> </details>


## Application .properties / .yaml settings files

### Profiles
Додаток може мати декілька `application.properties` з різними налаштуваннями.

Spring дозволяє використовувати різні профілі, для того, щоб запустити програму з різними налаштуваннями.

Наприклад, у нас є 2 файла з різними налаштуваннями (1 - `application.properties`, 2 - `application-dev.properties`), один файл для розробки, а інший - для продакшна.
Ми можемо в стартових налаштуваннях IDEA (**Program arguments**) задати необхідний файл для запуску вказавши `--spring.profiles.active=dev`.



## Spring Tests
> With Integration tests you should only using MockMvc

### @SpringBootTest
Встановлюється над класом в тестах.
При тестуванні буде запускатися

### Tests Examples


> <details>
> <summary>Integration Testing</summary>
> Тестуємо контролер з ендпоінтами
> 
> ```java
> // простий репозиторій
> public interface PaymentRepository extends CrudRepository<Payment, Long> {
> }
> ```
> ```java
> // контролер для тестування
> @RestController
> @RequestMapping("/api/v1/payment")
> public class PaymentController {
> 
>   private final PaymentService paymentService;
> 
>   @Autowired
>   public PaymentController(PaymentService paymentService) {
>     this.paymentService = paymentService;
>   }
> 
>   @RequestMapping
>   public void makePayment(@RequestBody PaymentRequest paymentRequest) {
>     paymentService.chargeCard(paymentRequest.getPayment().getCustomerId(), paymentRequest);
>   }
> }
> ```
> ```java
> // тестування контролеру
> import com.amigoscode.testing.customer.Customer;
> import com.amigoscode.testing.customer.CustomerRegistrationRequest;
> import com.fasterxml.jackson.core.JsonProcessingException;
> import com.fasterxml.jackson.databind.ObjectMapper;
> import org.junit.jupiter.api.Test;
> import org.springframework.beans.factory.annotation.Autowired;
> import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
> import org.springframework.boot.test.context.SpringBootTest;
> import org.springframework.http.MediaType;
> import org.springframework.test.web.servlet.MockMvc;
> import org.springframework.test.web.servlet.ResultActions;
> 
> import java.math.BigDecimal;
> import java.util.Objects;
> import java.util.UUID;
> 
> import static org.assertj.core.api.Assertions.assertThat;
> import static org.junit.jupiter.api.Assertions.fail;
> import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
> import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
> import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
> 
> @SpringBootTest
> @AutoConfigureMockMvc
> public class PaymentIntegrationTest {
> 
>   @Autowired
>   private PaymentRepository paymentRepository;
> 
>   @Autowired
>   private MockMvc mockMvc;
> 
>   @Test
>   void itShouldCreatePaymentSuccessfully() throws Exception {
>     // Given a customer
>     UUID customerId = UUID.randomUUID();
>     Customer customer = new Customer(customerId, "James", "0000000");
> 
>     CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest(customer);
> 
>     // Register
>     ResultActions customerRegResultActions = mockMvc.perform(put("/api/v1/customer-registration")
>         .contentType(MediaType.APPLICATION_JSON)
>         .content(Objects.requireNonNull(objectToJson(customerRegistrationRequest))));
> 
>     // ... Payment
>     long paymentId = 1L;
> 
>     Payment payment = new Payment(
>         paymentId,
>         customerId,
>         new BigDecimal("100.00"),
>         Currency.GBP,
>         "x0x0x0x0",
>         "Zakat"
>     );
> 
>     // ... Payment request
>     PaymentRequest paymentRequest = new PaymentRequest(payment);
> 
>     // ... When payment is sent
>     ResultActions paymentResultActions = mockMvc.perform(post("/api/v1/payment")
>         .contentType(MediaType.APPLICATION_JSON)
>         .content(Objects.requireNonNull(objectToJson(paymentRequest))));
> 
>     // Then both customer registration and payment requests are 200 status code
>     customerRegResultActions.andExpect(status().isOk());
>     paymentResultActions.andExpect(status().isOk());
> 
>     // Payment is stored in db
>     // TODO: Do not use paymentRepository instead create an endpoint to retrieve payments for customers
>     assertThat(paymentRepository.findById(paymentId))
>         .isPresent()
>         .hasValueSatisfying(p -> assertThat(p).isEqualToComparingFieldByField(payment));
> 
>     // TODO: Ensure sms is delivered
>   }
> 
>   private String objectToJson(Object object) {
>     try {
>       return new ObjectMapper().writeValueAsString(object);
>     } catch (JsonProcessingException e) {
>       fail("Failed to convert object to json");
>       return null;
>     }
>   }
> }
> ```
> 
> </details>