# Jackson
* [Тестирование REST контроллеров через JSONassert и Матчеры](https://github.com/JavaWebinar/topjava/blob/doc/doc/lesson07.md#-8-тестирование-rest-контроллеров-через-jsonassert-и-матчеры)
* [Jackson – Bidirectional Relationships](https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion)
  * [Пример из TopJava](https://github.com/JavaWebinar/topjava/blob/doc/doc/lesson08.md#-3-hw7-optional-getwithmeals--тесты) 
* [Spring/Jackson + @JsonView: фильтруем JSON](https://habr.com/ru/post/307392/)
  > Дополнительная библиотека `jackson-datatype-jsr310` помогает отображать данные в нужном формате, например, подходит для работы с датами.
* [SpringBoot Common Application Properties - JSON Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties.json)
* [Аннотации Jackson](https://nsergey.com/jackson-annotations/)

## ObjectMapper
* [Кастомизация Jackson Object Mapper](https://github.com/JavaWebinar/topjava/blob/doc/doc/lesson07.md#-7-кастомизация-jackson-object-mapper)

`ObjectMapper` предназначен для сериализации и десериализации объекта.


## Сериалищация и Десеарилизация полей


```java
// Пример только записи
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
private String password;
```

```java
// Пример только чтения
@JsonProperty(access = JsonProperty.Access.READ_ONLY)
private Date registered = new Date();
```
