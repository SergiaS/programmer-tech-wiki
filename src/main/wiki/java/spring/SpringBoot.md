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
