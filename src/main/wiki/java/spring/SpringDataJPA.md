# [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [The Best Spring Data JPA Logging Configuration in Spring Boot](https://thorben-janssen.com/spring-data-jpa-logging/)

## Settings
Помимо указания адреса контекста (что и где будет сканироваться), нужно указать явно jpa namespace с репозиториями:
```java
<context:component-scan base-package="ru.javawebinar.**.repository.datajpa"/>
<jpa:repositories base-package="ru.javawebinar.**.repository.datajpa"/>
```
или использовать спец аннотацию `@EnableJpaRepositoties`.

***

## Как работает Spring Data JPA?
* Сначала интерфейс в `JpaRepositoryFactory.getRepositoryBaseClass` проксируется одной из имплементаций: `QueryDslJpaRepository` (при использовании **Unified Queries for Java**) или `SimpleJpaRepository`;
* Затем анализируются все методы — кандидаты на `Query` (`DefaultRepositoryInformation.isQueryMethodCandidate`);
Упрощено, туда попадает все с `@Query` аннотацией и все, чего нет в `JpaRepository`;
* Далее имена методов парсятся в `PartTree` через `PartTree.PREFIX_TEMPLATE`: __Pattern.compile("^(find|read|get|count|query)(\\p{Lu}.*?)??By")__ и ищется соответствия с пропертями сущности;
* Наконец, метод тривиально реализуется через **JPA Criteria API**.


## Использование
Сначала нужно наследоваться от репозитория, например `JpaRepository<T, ID extends Serializable>`.

Существует несколько типов репозиториев (реализаций), различающихся по набору возможностей:
* `Repository` — базовый тип репозиториев, не содержит каких-либо методов, так же является универсальным.
* `CrudRepository` — предоставляет базовый набор методов для доступа к данным. 
Данный интерфейс является универсальным и может быть использован не только в связке с **JPA**.
* `PagingAndSortingRepository` — универсальный интерфейс, расширяющий `CrudRepository` и добавляющий поддержку пейджинации и сортировки.
* `JpaRepository` — репозиторий, добавляющий возможности, специфичные для **JPA**.

И две реализации, которые можно использовать для каких-нибудь нетривиальных задач, вроде написания реализации какого-нибудь метода с нестандартным поведением:
* `QueryDslJpaRepository` — реализация `JpaRepository` для взаимодействия с `QueryDsl`.
* `SimpleJpaRepository` — простая реализация `JpaRepository`.


## Query methods
* [Supported keywords inside method names](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)
* [Supported query method subject keywords](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords)
* [Создание волшебного метода](https://alexkosarev.name/2017/02/09/spring-data-jpa-magic-methods/)


## Annotations
> Синхронные Query-методы не нуждаются в дополнительных аннотациях. 
> Аннотации нужны только методам, изменяющие записи в БД, а так же асинхронным методам.

***

### @Query
The `@Query` is an annotation that is used to execute both JPQL and native SQL queries.
```java
@Query(value = "SELECT * FROM employee WHERE first_name=?1 AND last_name=?2", nativeQuery = true)
Employee findByFirstNameAndLastName(String firstName, String lastName);
```
де, `?1` перший індекс (параметр) методу = `firstName`, `?2` = `secondName`

***

### @Param
Використовується тільки з анотацією `@Query` як вказівник параметрів.

Тут необхідна анотація `@Param`, (Spring сам підкаже):
```java
@Query("SELECT m FROM Meal m WHERE m.user.id=:userId")
List<Meal> getAll(@Param("userId") int userId);
```

A тут, при запитах типу `?1`, не потрібна анотація `@Param`:
```java
@Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id=?1 and m.user.id=?2")
Meal getWithUser(int id, int userId);
```

***

### @Modifying
`@Modifying` вказує на те, що вказаний метод слід інтерпретувати як модифікований запиту.
Використовується тільки з анотацією `@Query`. 
Вказує, що запит не призначений для запиту типу **SELECT**, а для **INSERT**, **UPDATE**, **DELETE** і **DDL**.
```java
@Modifying
@Query("DELETE FROM Item i WHERE i.id=:id AND i.order.id=:orderId")
int delete(@Param("id") int id, @Param("orderId") int orderId);
```


***

### @Async
Аннотация `@Async` указывает, что метод должен выполняться асинхронно.

***

### @ActiveProfile
Относится к тест модулю **Spring**, задает профили при поднятии **Spring** в тестах.

***

### @NamedEntityGraphs
Позволяет задать несколько графов `@NamedEntityGraph`, каждый со своим набором атрибутов.

Всё, что указано в  attributeNodes будет загружаться EAGER.
```java
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "post-entity-graph",
                attributeNodes = {
                        @NamedAttributeNode(value = "images")
                }
        )
})
@Entity
public class Post {
    // ...
}
```

***

### @EntityGraph
> Слугує вирішенням проблеми **N+1**. 
> Основной целью **JPA Entity Graph** является повышение производительности среды выполнения при загрузке связанных ассоциаций сущности и основных полей.
> `@EntityGraph` сокращает количество лишних запросов.

> С помощью `@EntityGraph` можно переопределять загрузку ассоциаций (коллекций) и аттрибутов.

> Если таблиц и связей много – часто рисуют графы (на бумаге) по получению нужных данных для каждого бизнес-процесса.

Аннотация должна использоваться над методами репозитория.
Сначала используйте `FetchType.LAZY` для вложенной сущности.
Потом нужно просто указать вложенное свойство в `attributePaths` как:
```java
@EntityGraph(attributePaths = {"meals", "roles"})
```

## [Entity Graph в Spring Data JPA](https://sysout.ru/entity-graph-v-spring-data-jpa/)
За допомогою **Entity Graph** можна задати для кожного запиту свою стратегію завантаження даних: **LAZY** чи **EAGER**.

> For queries with named parameters you need to use provide names for method parameters.
> Use `@Param` for query method parameters.

Тип стратегії графа `LOAD` дозволяє дістати усі значення сутності.
```java
@Query("SELECT u FROM User u WHERE u.id=:userId")
@EntityGraph(value = "Meal.user", type = EntityGraph.EntityGraphType.LOAD)
User getUserById(@Param("userId")int userId);
```
***

**Как можно изменить настройки `fetch` стратегии любых атрибутов Entity для отдельных запросов (`query`) или методов поиска (`find`), 
то если у Entity есть атрибут с `fetchType = LAZY`, но для конкретного запроса его требуется сделать `EAGER` или наоборот?**

Для этого существует EntityGraph API, используется он так: 
с помощью аннотации `@NamedEntityGraph` для Entity, создаются именованные EntityGraph объекты, которые содержат список атрибутов 
у которых нужно поменять `fetchType` на `EAGER`, а потом данное имя указывается в `hits` запросов или метода `find`. 
В результате `fetchType` атрибутов Entity меняется, но только для этого запроса. 

Существует две стандартных `property` для указания EntityGraph в `hit`:
1. `javax.persistence.fetchgraph` — все атрибуты перечисленные в EntityGraph меняют `fetchType` на `EAGER`, все остальные на `LAZY`.
2. `javax.persistence.loadgraph` — все атрибуты перечисленные в EntityGraph меняют `fetchType` на `EAGER`, все остальные сохраняют 
свой `fetchType` (то есть если у атрибута, не указанного в EntityGraph, `fetchType` был `EAGER`, то он и останется `EAGER`)

С помощью `@NamedSubgraph` можно также изменить `fetchType` вложенных объектов Entity.

***

Приклад 


## DomainClassConverter
> Під капотом працює клас спрінга `DomainClassConverter` - він звертається до репозиторію та одразу повертає результат.
```java
// Spring задіє DomainClassConverter
@GetMapping("/{id}")
public Post findById(@PathVariable("id") Post post) {
  return post;
}
```
```java
// Так пишуть зазвичай
@GetMapping("/{id}")
public Post findById(@PathVariable Integer id) {
  return postRepository.findById(id).get();
}
```


## PagingAndSortingRepository
* [Міні проєкт роботи PagingAndSortingRepository ](https://github.com/SergiaS/t_springboot/tree/spring-data-jpa-pagination)
* [Baeldung - Pagination and Sorting using Spring Data JPA](https://www.baeldung.com/spring-data-jpa-pagination-sorting)

### Simple example
1. Create your repository and implement `PagingAndSortingRepository`:
```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonRepository extends PagingAndSortingRepository<Person, Integer> {

  Page<Person> findAllByLastNameContains(String lastname, Pageable pageable);

}
```
2. Create your controller:
```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/people")
public class PeopleController {

  private final PersonRepository repository;

  public PeopleController(PersonRepository repository) {
    this.repository = repository;
  }

  // дефолтний метод
  @GetMapping("/all")
  public Page<Person> findAll(@RequestParam int page, @RequestParam int size) {
    PageRequest pr = PageRequest.of(page, size);
    return repository.findAll(pr);
  }

  // кастомний метод
  @GetMapping("/by")
  public Page<Person> findAllByLastname(
      @RequestParam String lastname, @RequestParam int page, @RequestParam int size) {
    Pageable pr = PageRequest.of(page, size, Sort.by("lastName"));
    return repository.findAllByLastNameContains(lastname, pr);
  }
}
```
Інтерфейс `Page` повертає окрім результату, ще й додаткову інформацію, завдяки котрій реалізувати пагінацію на фронтенді буде легше:

<details>
<summary>EXAMPLE</summary>

```json
{
  "content": [
    {
      "id": 19,
      "firstName": "Jerilyn",
      "lastName": "Bashirian",
      "phoneNumber": "(347) 904-8583",
      "email": "roman.stroman@gmail.com",
      "address": {
        "id": 20,
        "address": "8536 Howell Meadow",
        "city": "New Marylou",
        "state": "Virginia",
        "zip": "88005-3158"
      }
    },
    {
      "id": 55,
      "firstName": "Penelope",
      "lastName": "Bahringer",
      "phoneNumber": "(260) 885-8613",
      "email": "tyler.murazik@gmail.com",
      "address": {
        "id": 56,
        "address": "944 Timothy Keys",
        "city": "Kraigfort",
        "state": "New Hampshire",
        "zip": "71653-2861"
      }
    }
  ],
  "pageable": {
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "pageSize": 2,
    "pageNumber": 0,
    "unpaged": false,
    "paged": true
  },
  "last": false,
  "totalElements": 4,
  "totalPages": 2,
  "size": 2,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "first": true,
  "numberOfElements": 2,
  "empty": false
}
```
</details>
