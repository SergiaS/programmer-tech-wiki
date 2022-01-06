# JPA
* [JPA и Hibernate в вопросах и ответах](https://habr.com/ru/post/265061/)
* [Java Persistence/OneToMany](https://en.wikibooks.org/wiki/Java_Persistence/OneToMany)
* [How to use JPA2's @Cacheable instead of Hibernate's @Cache](https://stackoverflow.com/questions/3663979/how-to-use-jpa2s-cacheable-instead-of-hibernates-cache)

> В **JPA** реализации используется `EntityManagerFactory`, а в **Hibernate** - `SessionFactory`.
>
> В **JPA** реализации используется `EntityManager`, а в **Hibernate** - `Session`. 
> Часто используется термин *persistence context*.

> Если проводить аналогию с обычным **JDBC**, то `EntityManagerFactory` будет аналогом `DataSource`, а `EntityManager` - аналогом `Connection`.

> `Entity` - класс (объект Java), который в **ORM** маппится в таблицу DB.

> `EntityManager` - это по сути прокси-обертка над **Hibernate Session**, которая создается каждый раз при открытии транзакции.

> В __JPQL__ нет `UPSERT`.

* **ORM (Object-to-Relational Mapping)** – это преобразование объекта в строку в таблице и обратное преобразование.

* Чтобы использоать JPA нам требуется некоторая реализацию, при помощи которой мы будем пользоваться технологией.
Реализации JPA ещё называют **JPA Provider**. Одной из самых заметных реализаций JPA является **Hibernate**.

* **JPA Provider** должен быть указан до перечисления классов.

* **JPA API** имеет адрес пакетов `javax.persistence.*`

* **Persistence Unit** — это некоторое объединение конфигураций, метаданных и сущностей. 
И чтобы **JPA** заработал, нужно описать хотя бы один **Persistence Unit** в конфигурационном файле, который имеет название `persistence.xml`.

* **Чистого JPA не существует, т.е. это всего лишь интерфейс, спецификация? 
Говорим JPA, подразумеваем какой-то ORM фрэймворк? 
А что тогда используют чистый jdbc, Spring-jdbc, MyBatis?
MyBatis не реализует JPA?**<br>
ORM это технология связывания БД и объектов приложения, а JPA - это JavaEE спецификация (API) этой технологии. Реализации JPA - Hibernate, OpenJPA, EclipceLink, но, например, Hibernate может работать по собственному API (без JPA, которая появилась позже). 
Spring-JDBC, MyBatis, JDBI не реализуют JPA, это обертки к JDBC. Все ORM и JPA также реализованы поверх JDBC.


## EntityManagerSession
> `EntityManagerSession` потокобезопасный, один экземпляр может использоваться для многопоточности.

Основные функции/операции `EntityManager`?
1. Для операций над Entity:
   * `persist` - добавление Entity под управление JPA;
   * `merge` - обновление;
   * `remove` - удаления;
   * `refresh` - обновление данных;
   * `detach` - удаление из управление JPA;
   * `lock` - блокирование Entity от изменений в других thread;
2. Получение данных:
   * `find` - поиск и получение Entity;
   * `createQuery`;
   * `createNamedQuery`;
   * `createNativeQuery`;
   * `contains`;
   * `createNamedStoredProcedureQuery`;
   * `createStoredProcedureQuery`;
3. Получение других сущностей JPA:
   * `getTransaction`;
   * `getEntityManagerFactory`;
   * `getCriteriaBuilder`;
   * `getMetamodel`;
   * `getDelegate`;
4. Работа с `EntityGraph`:
   * `createEntityGraph`;
   * `getEntityGraph`;
5. Общие операции над `EntityManager` или всеми Entities:
   * `close`;
   * `isOpen`;
   * `getProperties`;
   * `setProperty`;
   * `clear`.



## EntityManager
`EntityManager` - это интерфейс, который описывает API для всех основных операций над Entity, получение данных и других сущностей JPA. 
По сути главный API для работы с JPA.

***

Существует два типа `EntityManager` - Управляемый контейнер и Управляемое приложение.

*** 

Получение доступа к EntityManager. В скобках метода фабрики - **createEntityManagerFactory**, указывается сущность, описанная в persistence.xml
```java
EntityManager em = Persistence.createEntityManagerFactory("movie-unit")
        .createEntityManager();
```




### Состояния сущностей
* [Руководство для начинающих по переходам между состояниями в JPA/Hibernate](http://akorsa.ru/2016/07/rukovodstvo-dlya-nachinayushhih-po-perehody-mezhdu-sostoyaniyami-v-jpa-hibernate/)
* [JPA EntityManager: управляем сущностями](https://easyjava.ru/data/jpa/jpa-entitymanager-upravlyaem-sushhnostyami/)

Согласно документации Hibernate сущность может быть в одном из 4 состояний:
> `new/transient`: 
состояние, в котором, новый созданный объект, о чьем существовании не знает СУБД и он не привязан к persistence контексту.
> `persistent`: 
сущность привязана к persistence контексту (находясь в кэше первого уровня) и есть запись в БД отбражающая эту сущность. Т.е., в этом состоянии любые объекта отражаются в БД.
> `detached`: 
сущность была привязана к persistence контексту, но контекст закрылся, а сущность осталась.
> `removed`: 
сущность в данном состоянии помечена как удаленная и persistence контекст удалит ее из БД, когда наступит flush-time.

Передвижение объекта из одного состояния в другое выполняется вызовом методов `EntityManager`:
> `persist()`
> `merge()`
> `remove()`

#### Этапы жизни сущности
```java
// 1. New или Transient (временный)
Category cat = new Category();
cat.setTitle("new category");

// 2. Managed или Persistent
entityManager.persist(cat);

// 3. Транзакция завершена, все сущности в контексте detached
entityManager.getTransaction().begin();
entityManager.getTransaction().commit();

// 4. Сущность изымаем из контекста, она становится detached
entityManager.detach(cat);

// 5. Сущность из detached можно снова сделать managed
Category managed = entityManager.merge(cat);

// 6. И можно сделать Removed. Интересно, что cat всё равно detached
entityManager.remove(managed);
```


## Annotations - javax.persistence

### @MappedSuperclass
Указавается над классом родительского класса - как бы расширяет зону видимости модели.

### @Access
Открывает доступ к полям или методам родительского класса.
Работает в паре с `@MappedSuperclass`.
```java
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBaseEntity {
    // some code
}
```

### @Entity
Главная аннотация, которая делает Entity из обычного POJO-класса.

### @Table
Не обязательная аннотация, если только не нужно уточнить параметры mapping-таблицы.

### @Id
Помечает первичный ключ.

### @GeneratedValue
Генерация ID при добавлении новой записи.

### @Column
Не обязательная аннотация, если только не нужно уточнить параметры столбца.
```java
@Column(name = "name", nullable = false)
protected String name;
```

### @Transient
Аннотацей можно исключить поля и свойства Entity из маппинга (property or field is not persistent).

### @Enumerated
Указывается ожидаемый тип поля, метода или класса.
Ставится над `enum` или коллекцией `enum`.

### @ElementCollection
Указывается над коллекцией.
Нужно для выбора стратегии взаимодействия - `EAGER` или `LAZY`.

### @CollectionTable
Указывается над коллекцией.
```java
@Enumerated(EnumType.STRING)
@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
     uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_roles")})
@Column(name = "role")
@ElementCollection(fetch = FetchType.EAGER)
private Set<Role> roles;
```

### @Cacheable
If you want to selectively cache entities using the `@Cacheable` annotation, you're supposed to specify a `<shared-cache-mode>` in the `persistence.xml`.

`@Cacheable` — позволяет включить или выключить использование кеша второго уровня (second-level cache) для данного Entity (если провайдер JPA поддерживает работу с кешированием и настройки кеша (second-level cache) стоят как ENABLE_SELECTIVE или DISABLE_SELECTIVE. 

> Обратите внимание свойство наследуется и если не будет перекрыто у наследников, то кеширование измениться и для них тоже.

### @Enumerated
**DOC**: Specifies that a persistent property or field should be persisted as a enumerated type. 
The `@Enumerated` annotation may be used in conjunction with the Basic annotation, or in conjunction with the `@ElementCollection` annotation 
when the element collection value is of basic type. 
If the enumerated type is not specified or the `@Enumerated` annotation is not used, the EnumType value is assumed to be ORDINAL.
```java
@Enumerated(EnumType.STRING)
@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
     uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_roles")})
@Column(name = "role")
@ElementCollection(fetch = FetchType.EAGER)
private Set<Role> roles;
```

### @UniqueConstraint
**RIGHT:**
Аннотация `@UniqueConstraint` предназначена для аннотирования нескольких уникальных ключей на уровне таблицы:
```java
@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_role_unique_idx")})
private Strring user_role;
```

**WRONG:**
При применении аннотации к полю вылетет ошибка - `@UniqueConstraint is dissallowed for this location`:
```java
@UniqueConstraint(columnNames={"user_role"})
private String user_role;
```

### @ElementCollection
Значения `@ElementCollection` всегда хранятся в отдельных таблицах, которые задаются аннотацией `@CollectionTable`. 
`@CollectionTable` отпределяет имя таблицы и `@JoinColumn` или `@JoinColumns` в случае составного первичного ключа.
```java
@Enumerated(EnumType.STRING)
@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
     uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_roles")})
@Column(name = "role")
@ElementCollection(fetch = FetchType.EAGER)
private Set<Role> roles;
```

### @CollectionTable
Отражает таблицу которая используется для маппинга коллекций базового или встраиваемого типов...

**DOC**: Specifies the table that is used for the mapping of collections of basic or embeddable types. Applied to the collection-valued field or property.
```java
@Enumerated(EnumType.STRING)
@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
     uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
@Column(name = "role")
@ElementCollection(fetch = FetchType.EAGER)
private Set<Role> roles;
```

### @PersistenceContext
**DOC**: Expresses a dependency on a container-managed EntityManager and its associated persistence context.

Как гласит глава "7.1 Persistence Contexts" спецификации JPA, сущности в мире JPA живут в некотором пространстве, которое называется "Контекст персистенции" (или Контексте постоянства, Persistence Context). 
Но напрямую мы не работаем с Persistence Context. Для этого мы используем `EntityManager` или "менеджер сущностей". 
Именно он знает про контекст и про то, какие там живут сущности. Мы же взаимодействуем с Entity Manager'ом.

The default persistence context type is `PersistenceContextType.TRANSACTION`.
Установка типа persistence EXTENDED важна, когда вы хотите сохранить контекст персистентности для всего жизненного цикла сеансового компонента с состоянием.
Another is:
```java
@PersistenceContext(type = PersistenceContextType.EXTENDED)
private EntityManager em;
```
```java
@Repository
public class JpaUserRepository {

    @PersistenceContext
    private EntityManager em;

    public User save(User user) {
        if (user.isNew()) {
            em.persist(user);
            return user;
        } else {
            return em.merge(user);
        }
    }
}
```

### @Basic
Basic — указывает на простейший тип маппинга данных на колонку таблицы базы данных. 
Также в параметрах аннотации можно указать `fetch` стратегию доступа к полю и является ли это поле обязательным или нет.
```java
// Example 1:
@Basic
protected String name;

// Example 2:
@Basic(fetch=LAZY)
protected String getName() { return name; }
```

### @AttributeOverride, @AttributeOverrides, @AssociationOverride и @AssociationOverrides
Какими аннотациями можно перекрыть связи (override entity relationship) или атрибуты, унаследованные от суперкласса, или заданные в embeddable классе при использовании этого embeddable класса в одном из entity классов и не перекрывать в остальных?

Для такого перекрывания существует четыре аннотации:
1. `@AttributeOverride` чтобы перекрыть поля, свойства и первичные ключи,
2. `@AttributeOverrides` аналогично можно перекрыть поля, свойства и первичные ключи со множественными значениями,
3. `@AssociationOverride` чтобы перекрывать связи (override entity relationship),
4. `@AssociationOverrides` чтобы перекрывать множественные связи (multiple relationship),

### @OrderBy и @OrderColumn
Аннотации служат для установки порядка выдачи элементов коллекций Entity.
```java
// Example 1:
@Entity
public class Student {
   @ManyToMany(mappedBy = "students")
   @OrderBy // PK is assumed
   public List<Course> getCourses() {
      // ...
   }
}

// Example 2:
@Entity
public class Person {
   @ElementCollection
   @OrderBy("zipcode.zip, zipcode.plusFour")
   public Set<Address> getResidences() {
       // ...
   }
}
```

### @NamedQueries
> При записи в базу через `namedQuery` валидация entity не работает, только валидация в БД.
```java
@NamedQueries({
        @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id"),
        @NamedQuery(name = User.BY_EMAIL, query = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=?1"),
        @NamedQuery(name = User.ALL_SORTED, query = "SELECT u FROM User u LEFT JOIN FETCH u.roles ORDER BY u.name, u.email"),
})
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends AbstractBaseEntity {

    public static final String DELETE = "User.delete";
    public static final String BY_EMAIL = "User.getByEmail";
    public static final String ALL_SORTED = "User.getAllSorted";
    ...
}
```

### @Fetch
`FetchMode` в Hibernate говорит как мы хотим, чтоб связанные сущности или коллекции были загружены:
* `SELECT` — используя по дополнительному SQL запросу на коллекцию,
* `JOIN` — в одном запросе с корневой сущностью, используя SQL оператор JOIN,
* `SUBSELECT`— в дополнительном запросе, используя SUBSELECT.
```java
@Fetch(FetchMode.SUBSELECT)
private Set<Role> roles;
```

### @BatchSize
Можем влиять на стратегию загрузки связанных коллекций при помощи аннотации `@BatchSize` (или атрибут batch-size в XML), 
которая устанавливает количество коллекций, которые будут загружаться в одном запросе.
```java
@BatchSize(size = 200)
private Set<Role> roles;
```



## Annotations - javax.validation

### @NotBlank
The annotated element must not be null and must contain at least one non-whitespace character. Accepts `CharSequence`.

### @Email
The string has to be a well-formed email address. 
Exact semantics of what makes up a valid email address are left to Jakarta Bean Validation providers. 
Accepts `CharSequence`. `null` elements are considered valid.

### @Size
The annotated element size must be between the specified boundaries (included).

Supported types are:
> `CharSequence` (length of character sequence is evaluated)
> `Collection` (collection size is evaluated)
> `Map` (map size is evaluated)
> `Array` (array length is evaluated)

`null` elements are considered valid.
```java
@Size(max = 100)
private String name;

@Size(min = 5, max = 100)
private String street;
```



## Transactions
* [@Transactional в тестах. Настройка EntityManagerFactory](https://habr.com/ru/post/232381/)

> Начало и завершение транзакции приводят к увеличению продолжительности обработки и повышенному расходованию ресурсов.

> Generally we should simply avoid starting DB transactions for `read-only` operations as they are unnecessary, can lead to database deadlocks, worsen performance and throughput.
>
> It’s worth mentioning that we can’t avoid read transactions when using **Spring Data JPA** since read methods on CRUD Repository are already marked with `@Transactional(readOnly = true)`.

***

Для подключения к Spring нужно добавить бин на основе JPA и указать в конфигурации спецальную строку:
```xml
<tx:annotation-driven/>
		
<!-- Transaction manager for a single JPA EntityManagerFactory (alternative to JTA) -->
<bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager"
      p:entityManagerFactory-ref="entityManagerFactory"/>
```
> Чтобы посмотреть информацию о транзакциях (открытие/закрытие и пр.), можно выставить в конфигурации logback `<logger name="org.springframework.orm.jpa.JpaTransactionManager" level="debug"/>`

***

JTA-транзакции - Распределенные транзакции когда используется больше одной БД.



## Relationships
* [JPA JoinColumn vs mappedBy](https://stackoverflow.com/questions/11938253/jpa-joincolumn-vs-mappedby)



## Cache
Стратегии кеширования:

Стратегии кеширования (хранения объектов в кеше) определяют поведения кеша в определенных ситуациях. Выделяют четыре группы:
* `Read-only` (Самый быстрый доступ)
* `Nonstrict-read-write`
* `Read-write`
* `Transactional` (Самый медленный доступ, но актуальные данные)

*** 

**Cache region**

Регион или область — это логический разделитель памяти вашего кеша. 
Для каждого региона можна настроить свою политику кеширования (для `EhCache` в том же `ehcache.xml`). 
Если регион не указан, то используется регион по умолчанию, который имеет полное имя вашего класса для которого применяется кеширование. 

В коде выглядит так:
```java
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "STATIC_DATA")
```
А для кеша запросов так:
```java
query.setCacheRegion("STATIC_DATA");
```
или в случае критерии
```java
criteria.setCacheRegion("STATIC_DATA");
```

***

**Отображение кеширования**

Во время разработки приложения, особенно сначала, очень удобно видеть действительно ли кешируются те или иные запросы, 
для этого нужно указать фабрике сессий следующие свойства:
```xml
<property name="hibernate.show_sql" value="true"/>
<property name="hibernate.format_sql" value="true"/>
```
В дополнение фабрика сессий также может генерировать и сохранять статистику использования всех объектов, регионов, зависимостей в кеше:
```xml
<property name="hibernate.generate_statistics" value="true"/>
<property name="hibernate.cache.use_structured_entries" value="true"/>
```
Для этого есть объекты `Statistics` для фабрики и `SessionStatistics` для сессии.

Методы сессии:
* `flush()` — синхронизирует объекты сессии с БД, и в то же время обновляет сам кеш сессии.
* `evict()` — нужен для удаления объекта из кеша сессии.
* `contains()` — определяет находится ли объект в кеше сессии или нет.
* `clear()` — очищает весь кеш.

***

### Second Level Cache
> Отличия от 1-го уровня:
> * Больше настроек
> * Доступ между сессиями
> * Подключение любой библиотеки кэширования
> * Более сложная и обширная тема

> Для работы с кэшем второго уровня (second level cache) в JPA описан `Cache` интерфейс, содержащий большое количество методов
> по управлению кэшем второго уровня (second level cache), если он поддерживается провайдером JPA, конечно.
> Объект данного интерфейса можно получить с помощью метода `getCache()` у `EntityManagerFactory`.

JPA говорит о пяти значениях `shared-cache-mode` из `persistence.xml`, который определяет как будет использоваться second-level cache:
1) `ALL` — все Entity могут кэшироваться в кеше второго уровня,
2) `NONE` — кеширование отключено для всех Entity,
3) `ENABLE_SELECTIVE` — кэширование работает только для тех Entity, у которых установлена аннотация Cacheable (`true`) или её xml эквивалент, для всех остальных кэширование отключено,
4) `DISABLE_SELECTIVE` — кэширование работает для всех Entity, за исключением тех у которых установлена аннотация Cacheable (`false`) или её xml эквивалент,
5) `UNSPECIFIED` — кеширование не определенно, каждый провайдер JPA использует свою значение по умолчанию для кэширования,


### Кеш запросов (Query cache)
Кеш запросов тоже по умолчанию отключен. 
Для включения нужно добавить следующую строку в конфигурационный файл:
```xml
<property name="cache.use_query_cache">true</property>
```
Далее пишется сам запрос через объект `Query`, который потом кешируется методом `setCacheable`. Пример ниже.
```java
Session session1 = sf.openSession();
session1.beginTransaction();

Query q1 = session1.createQuery("from Alien where aid=101");
q1.setCacheable(true);
a = (Alien) q1.uniqueResult();
System.out.println(a);

session1.getTransaction().commit();
session1.close();

// **********************
Session session2 = sf.openSession();
session2.beginTransaction();

Query q2 = session2.createQuery("from Alien where aid=101");
q2.setCacheable(true);
a = (Alien) q2.uniqueResult();
System.out.println(a);

session2.getTransaction().commit();
session2.close();
```

*** 

Кеш запросов похож на кеш второго уровня. 
Но в отличии от него — ключом к данным кеша выступает не идентификатор объекта, а совокупность параметров запроса. 
А сами данные — это идентификаторы объектов соответствующих критериям запроса. 
Таким образом, этот кеш рационально использовать с кешем второго уровня.

***

Кеш запросов кеширует не объекты, а ИД результатов. Потом кеш запросов лезит в кеш второго уровня.

***

Кеш запросов, пожалуй, самый не эффективный из всех. Этому есть несколько причин:
> Прежде всего ключом к данным этого кеша выступает не только параметры запроса, но и сам запрос. 
Это особенно важно, когда запросов много и они большие.
> Кеш запросов очень часто сбрасывается. 
Т.е., если хоть одна из таблиц, которые участвуют в запросе, была модифицирована, то кеш будет сброшен и запрос выполнен по новой.

Поэтому использовать его следует очень осторожно. 
И помните — нету смысла кешировать все подряд. 
Кешируйте только те запросы, которые действительно могут ускорить работу вашего приложения и те запросы, 
кеш для которых будет очень редко сбрасываться.



## Configuration

### Генерация схемы базы данных по Java Entity 
* [JPA Database Schema Generation](https://www.javacodegeeks.com/2015/03/jpa-database-schema-generation.html)
* [Hibernate: hbm2ddl.auto=update und autoincrement](https://stackoverflow.com/questions/7793395/hibernate-hbm2ddl-auto-update-und-autoincrement)

**На примере TopJava**

JPA 2.1 предоставляет возможность генерировать базу данных по сущностям. Для этого в `spring-db.xml` укажем следующие параметры:
```xml
<!--Действие, которое выполнится - схема будет сброшена и создана снова-->
<entry key="#{T(org.hibernate.cfg.AvailableSettings).HBM2DDL_SCRIPTS_ACTION}" value="drop-and-create"/>

<!--Расположение скриптов создания схемы-->
<entry key="#{T(org.hibernate.cfg.AvailableSettings).HBM2DDL_SCRIPTS_CREATE_TARGET}" value="${TOPJAVA_ROOT}/config/ddl/create.ddl"/>

<!--Расположение скриптов сброса схемы-->
<entry key="#{T(org.hibernate.cfg.AvailableSettings).HBM2DDL_SCRIPTS_DROP_TARGET}" value="${TOPJAVA_ROOT}/config/ddl/drop.ddl"/>

<!--Можно настроить автоматическое обновление схемы базы данных при изменении сущностей-->
<entry key="#{T(org.hibernate.cfg.AvailableSettings).HBM2DDL_AUTO}" value="create"/>
```
> Автоматическую генерацию не рекомендуется использовать для реального приложения, так как генерируемые команды часто некорректны. 
> Чтобы они были более правильными — нужно указывать дополнительные условия и ограничения в аннотациях при описании entity.

### persistence.xml
> Файл должен распологаться в `resources/META-INF/persistence.xml`.
> 
> Traditionally, the `persistence.xml` is located in a `META-INF` folder that needs to reside in the root of the Java classpath. 
> If you’re using **Maven**, you can store it in the `resources` folder, like this:
> ```xml
> src/main/resources/META-INF/persistence.xml
> ```




### orm.xml
If you are using annotations, you can still use XML mappings to override the static annotation metadata with the one provided via an `orm.xml` configuration file.

By default, the `orm.xml` configuration file is located in the `META-INF` folder. 
If you want to use a different file location, you can use the mapping-file XML element in the `persistence.xml` file, like this:
```xml
<mapping-file>file:///D:/Vlad/Work/Examples/mappings/orm.xml</mapping-file>
```



## Errors
> ошибки типа: **entities share the same JPA entity name**, **DuplicateMappingException**

Это настройка должна помочь:
Использование `<exclude-unlisted-classes/>` или `<exclude-unlisted-classes>true</exclude-unlisted-classes>` означает, что провайдер должен игнорировать классы. 
Hibernate поумолчанию сканирует все классы на аннотации `@Entity`. Данная настройка это отключает.

Опускание `<exclude-unlisted-classes>` или использование `<exclude-unlisted-classes>false</exclude-unlisted-classes>` вызывает JPA провайдер для сканирования местоположения пути к классам файла сохранения для JPA-аннотированных классов.

***



## *Questions:*

1. **В чем разница между `persist` и `merge`?**

`merge`, в отличие от `persist`, делает запрос в базу данных, если entity нет в текущей сессии.
Entity, переданный в `merge`, не меняется. Нужно использовать возвращаемый результат.

* [StackOverflow](https://stackoverflow.com/questions/1069992/jpa-entitymanager-why-use-persist-over-merge)

***

2. **Какая разница в методах `get`, `load` и `find`?**

Все они на первый взгляд делают одно и тоже, но подкапотом отличаются.

`find` - __javax.persistence__, `get` и `load` - __hibernate__

***

3. **Какая разница между `persist` и `merge`?**

`merge`, в отличие от `persist`, делает запрос в БД, если Entity нет в текущей сессии.
Entity, переданный в `merge`, не меняется. Нужно использовать возвращаемый результат.

Другими словами, `persist` - позволяет отражать сущность сразу в БД при изменении данных объекта, в то время как, 
`merge` - не будет отражать изменения объекта в БД.
```java
if (product.isNew()) {
    em.persist(product);
    product.setNotes("BOOM"); // изменения отобразяться в БД
} else {
    em.merge(product);
    product.setNotes("123");  // изменения НЕ отобразяться в БД
}
```

[StackOverflow](https://stackoverflow.com/questions/1069992/jpa-entitymanager-why-use-persist-over-merge/17042557#17042557)

***

4. **Какая разница между `persist` и `save`?**

Метод Hibernate `save`, имеет идентичный результат с вызовом метода JPA `persist`. 
Однако, в отличие от `persist`, метод `save` возвращает идентификатор сущности.

***

5. **Расскажи про метод `update`**

Метод `update` инициирует событие `SaveOrUpdateEvent`, которое обрабатывается обработчиком `DefaultSaveOrUpdateEventListener`. 
Следовательно, метод update эквивалентен методам save и `saveOrUpdate`.

***

6. **В чем разница между `transaction-type="JTA"` и `transaction-type="RESOURCE_LOCAL"`?**

По умолчанию `JTA` в среде **JavaEE** и `RESOURCE_LOCAL` в среде **JavaSE**.

***

7. **Какие два вида кэшей (cache) вы знаете в JPA и для чего они нужны?**

**JPA** говорит о двух видов кэшей (cache):
* **first-level cache** (кэш первого уровня) — кэширует данные одной транзакции,
* **second-level cache** (кэш второго уровня) — кэширует данные дольше, чем одна транзакция. 
Провайдер JPA может, но не обязан реализовывать работу с кэшем второго уровня. 
Такой вид кэша позволяет сэкономить время доступа и улучшить производительность, однако оборотной стороной является 
возможность получить устаревшие данные.

***


***


