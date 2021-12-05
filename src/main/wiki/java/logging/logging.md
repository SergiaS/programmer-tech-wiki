# Logging
Фреймворки логирования: log4j, logback, slf4j

> > **Из TopJava:**
> А зачем мы использовали logback? Почему SLF4J нас не устроило? Почему реализация логирования не log4j?
>
> `slf4j-api` это API. Там есть только пустая реализация `org.slf4j.helpers.NOPLogger` (можно посмотреть в исходниках).
> Logback для новых проектов стал стандарт. spring-petclinic и spring-boot используют его по умолчанию.
> Вместе с `logback-classic` подтягивается транзитивная зависимость `slf4j-api`.
> Можно было бы включить `logback-classic` в `pom.xml` со scope:compile и не включать `slf4j-api`.
> Нам версия `slf4j-api` понадобится, когда мы добавим в проект бриджи.
> ```xml
> <dependency>
>     <groupId>org.slf4j</groupId>
>     <artifactId>slf4j-api</artifactId>
>     <version>${slf4j.version}</version>
>     <scope>compile</scope>
> </dependency>
> 
> <dependency>
>     <groupId>ch.qos.logback</groupId>
>     <artifactId>logback-classic</artifactId>
>     <version>${logback.version}</version>
>     <scope>runtime</scope>
> </dependency>
> ```

## Settings
> Для JPA, чтобы посмотреть информацию о транзакциях (открытие/закрытие и пр.), можно выставить в конфигурации logback `<logger name="org.springframework.orm.jpa.JpaTransactionManager" level="debug"/>`




## SLF4J 
* [SLF4J](http://www.slf4j.org/)
* [What is the fastest way of (not) logging](http://www.slf4j.org/faq.html#logging_performance)

> Поддержка в Spring с 5 версии.

> Не делать конкатенацию строк при логгировании сообщений, если уровень логирования в конфигурации выставлен выше уровня логирования в коде

> Лучше использовать параметризованные сообщения вместо канкатенаций.
>
> DOC: The following two lines will yield the exact same output. However, the second form will outperform the first form by a factor of at least 30, in case of a disabled logging statement:
> ```java
> logger.debug("The new entry is " + entry + ".");
> logger.debug("The new entry is {}.", entry);
> ```


## Logback
* [Logback Project](http://logback.qos.ch/)

### Подключение цветов и аппендера
```xml
<appender name="console" class="ch.qos.logback.core.ConsoleAppender">
	<encoder>
		<charset>UTF-8</charset>
		<pattern>%d{HH:mm:ss.SSS} %highlight(%-5level) %cyan(%class{50}.%M:%L) - %msg%n</pattern>
	</encoder>
</appender>

<appender name="result" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
        <charset>UTF-8</charset>
        <pattern>%magenta(%msg%n)</pattern>
    </encoder>
</appender>
<logger name="result" level="INFO" additivity="false">
    <appender-ref ref="result"/>
</logger>
```

