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


## Серіалізація та Десерілізація полей

### @JsonProperty
```java
// Приклад тільки запису даних
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
private String password;
```

```java
// Приклад тільки читання даних
@JsonProperty(access = JsonProperty.Access.READ_ONLY)
private Date registered = new Date();
```

### @JsonView
* [My Example](https://github.com/SergiaS/example_spring/tree/5b1d69fa1030416a95c0f6e097b0da57858f430e)
* [Jackson @JsonView examples - mkyong](https://mkyong.com/java/jackson-jsonview-examples/)



## Створюємо запити
В прикладі використовуються об'єкти зі **Spring Web** - потрібні такі залежності
```xml
<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-databind</artifactId>
  <version>2.13.4</version>
</dependency>
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-web</artifactId>
  <version>5.3.23</version>
</dependency>
```
```java
// GET
RestTemplate restTemplate = new RestTemplate();

String url = "https://reqres.in/api/users/2";
System.out.println(restTemplate.getForObject(url, String.class));
```
```java
// POST
RestTemplate restTemplate = new RestTemplate();

// створюємо JSON який покладемо у запит
Map<String, String> jsonToSend = new HashMap<>();
jsonToSend.put("name", "Test name");
jsonToSend.put("job", "Test job");

HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonToSend);

String url = "https://reqres.in/api/users/";
System.out.println(restTemplate.postForObject(url, request, String.class));
```