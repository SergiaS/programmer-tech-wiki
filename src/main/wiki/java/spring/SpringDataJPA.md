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
Сначало нужно наследоваться от репозитория, например `JpaRepository<T, ID extends Serializable>`.

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
где, `?1` первый индекс (параметр) метода = `firstName`, `?2` = `secondName`

***

### @Param
Используется только с аннотацией `@Query` как указатель параметров.

Здесь необходима аннотация `@Param`, (сприг сам скажет):
```java
@Query("SELECT m FROM Meal m WHERE m.user.id=:userId")
List<Meal> getAll(@Param("userId") int userId);
```

A здесь, при запросах типа `?1`, не нужна аннотация `@Param`:
```java
@Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id=?1 and m.user.id=?2")
Meal getWithUser(int id, int userId);
```

***

### @Modifying
`@Modifying` говорит о том, что указанный метод должен быть интерпретирован как модифицирующий запрос.
Используется только с аннотацией `@Query`. Указывает, что запрос не для запроса типа **SELECT**, а для **INSERT**, **UPDATE**, **DELETE** и **DDL**.

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
> Служит решением проблемы **N+1**. 
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
С помощью Entity Graph можно задать для каждого запроса свою стратегию загрузки данных: **LAZY** либо **EAGER**.

> For queries with named parameters you need to use provide names for method parameters. Use @Param for query method parameters

Тип стратегии графа LOAD позволяет достать все значения сущности.

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
