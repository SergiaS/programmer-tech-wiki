# Hibernate
* [Java Hibernate](https://medium.com/nuances-of-programming/java-hibernate-9a96e3bc889d)
* [The Best Spring Data JPA Logging Configuration in Spring Boot](https://thorben-janssen.com/spring-data-jpa-logging/)

***

Підвантажуємо зв'язані ліниві сутності:
```java
Hibernate.initialize(person.getItems());
```

***

__import javax.persistence__

Hibernate – самая популярная реализация спецификации JPA. Таким образом JPA описывает правила, а Hibernate реализует их.

Hibernate реализует спецификации JPA, а значит и аннотации JPA.
Сама команда Hibernate рекомендует использовать аннотации JPA (import javax.persistence), а не Hibernate.

**Использование спецификаций JPA (а не Hibernate например) позволяет лекго мигрировать с Hibernate на другую реализацию JPA - не придется менять весь код, т.к. другая реализация тоже будет использовать теже аннотации.**

***

Hibernate – это framework, который используется для сохранения, получения, изменения и удаления Java объектов из БД.

Плюсы Hibernate:
* Предоставляет технологию ORM
* Регулирует SQL запросы
* Уменьшает количество кода для написания

***

> Hibernate использует JDBC для работы с базой данных.

> Переопределять `equals()/hashCode()` необходимо, если:
> * использовать entity в `Set` (рекомендовано для Many-ассоциаций) либо как ключи в `HashMap`.
> * использовать reattachment of detached instances (т.е. манипулировать одним Entity в нескольких транзакциях/сессиях).

> **Note**<br> 
> Клас конфігурації **Hibernate** `Configuration` автоматично підхопить файл `hibernate.properties`, котрий повинен бути в теці `resources`

***

### Коли використовувати метод `load`
Метод `load` не робить запит до БД. Він поверне пустий об'єкт - де всі поля будуть `null`, окрім `id`.

Метод користний, коли необхідно зв'язати об'єкт:
```java
// створюємо новий товар - йому треба назначити власника
Item item = new Item("Some new item");

// Нам потрібен цей об'єкт тільки для налаштування зв'язку з новим item
// Значення полів цього Person не потрібні - тому робити зайвий запит до БД не будемо
Person personProxy = session.load(Person.class, id);

// До колонки FOREIGN_KEY об'єкта item буде покладено id цієї person 
// Перевірка що Person з таким id існує буде на стороні БД
item.setOwner(personProxy);

session.save(item);
```



## Конфигурационный файл `hibernate.cfg.xml`
Для корректной работы, мы должны передать __Hibernate__ подробную информацию, которая связывает наши Java-классы c таблицами в БД. 
Мы, также, должны указать значения определённых свойств __Hibernate__. 
Обычно, вся эта информация помещена в отдельный файл, либо XML-файл – `hibernate.cfg.xml`, либо – `hibernate.properties`.

Ключевые свойства, которые должны быть настроены в типичном приложении:
* `hibernate.dialect` - Указывает __Hibernate__ диалект БД. __Hibernate__, в свою очередь, генерирует необходимые SQL-запросы (например, `org.hibernate.dialect.MySQLDialect`, если мы используем __MySQL__).
* `hibernate.connection-driver_class` - Указывает класс JDBC драйвера.
* `hibernate.connection.url` - Указывает URL (ссылку) необходимой нам БД (например, `jdbc:mysql://localhost:3306/database`).
* `hibernate.connection.username` - Указывает имя пользователя БД (например, root).
* `hibernate.connection.password` - Указывает пароль к БД (например, password).
* `hibernate.connection.pool_size` - Ограничивает количество соединений, которые находятся в пуле соединений __Hibernate__.
* `hibernate.connection.autocommit` - Указывает режим `autocommit` для JDBC-соединения.

Стандартное название файла конфигурации `hibernate.cfg.xml` - его можно не писать.
Если же название другое - прописывать. Вообще всегда рекомендуется писать название файла конфигурации.
```java
SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)  // указываем entity-класс для работы с базой
                .buildSessionFactory();
```

***

Создание или обновление БД Hibernate'ом
Если БД нет, hibernate сам создает, если в конфиге у настройки hbm2ddl будет значение create.
```xml
<property name="hibernate.hbm2ddl.auto">create</property>
```

***

Пример рабочего конфига для версии 5.4.24
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/jpa_tech</property>
        <property name="hibernate.connection.username">hbstudent</property>
        <property name="hibernate.connection.password">hbstudent</property>
        <property name="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</property>

        <property name="hibernate.hbm2ddl.auto">create</property>
        <property name="hibernate.show_sql">true</property>
    </session-factory>
</hibernate-configuration>
```


## Entity
* Entity класс – это Java класс, который отображает информацию определённой таблицы в БД.
* Entity класс – это POJO класс, в котором мы используем определённые Hibernate аннотации для связи класса с таблицей из базы.
* POJO (Plain Old Java Object) – класс, удовлетворяющий ряду условий:
  * `private` поля, `getter`-ы и `setter`-ы, конструктор без аргументов и т.д.

## Состояния сущности
Экземпляр класса может находиться в одном из трёх состояний:
* `transient`. Это новый экземпляр устойчивого класса, который не привязан к сессии и ещё не представлен в БД.
  Он не имеет значения, по которому может быть идентифицирован.
* `persistent`. Мы можем создать переходный экземпляр класса, связав его с сессией.
  Устойчивый экземпляр класса представлен в БД, а значение идентификатора связано с сессией.
* `detached`. После того как сессия закрыта, экземпляр класса становится отдельным, независимым экземпляром класса.


## equals and hashcode
* [Ultimate Guide to Implementing equals() and hashCode() with Hibernate](https://thorben-janssen.com/ultimate-guide-to-implementing-equals-and-hashcode-with-hibernate/)

### When and Why you need to Implement equals() and hashCode()
1. You need to implement the `equals()` and `hashCode()` methods for primary key classes if you map **composite primary keys**.
2. If you **map an association to a Map**, your map key needs to implement the `equals()` and `hashCode()` methods. 
   So, if use an entity as the key, it needs to provide both methods.
3. You can map _one-to-many_ and _many-to-many_ associations to different sub-types of Collection. 
   If you use a **Set**, your entities have to have `equals()` and `hashCode()` methods.



## Annotations

### @MappedSuperClass
Аннотация используется, когда вы хотите "спрятать" общие поля для нескольких сущностей объектной модели.
При этом, сам аннотированный класс часто является абстрактным, и не рассматривается как отдельная сущность.
 
В БД всё будет выглядеть, как если бы поля базового класса были определены непосредственно в производном классе (в модели).

Mapped Superclass это класс от которого наследуются Entity, он может содержать аннотации JPA, 
однако сам такой класс не является Entity, ему необязательно выполнять все требования установленные для Entity (например, он может не содержать первичного ключа). 
Такой класс не может использоваться в операциях `EntityManager` или `Query`. 
Такой класс должен быть отмечен аннотацией MappedSuperclass или соответственно описан в xml файле.

***

### @Embeddable, @Embedded
> [Look an example](https://www.baeldung.com/jpa-embedded-embeddable)

Основная цель - композиции полей.

Для чего применять:
* Разбиение большого объекта на мелкие, объединенные по смыслу.
* Повторяющиеся поля в разных таблицах.
Исключение дублирования кода (полей).

***

### @Table
Аннотация `@Table` говорит о том, к какой именно таблице мы привязываем класс.

***

### @Entity
Аннотация `@Entity` говорит о том, что данный класс будет иметь отображение в БД.

***

### @Id
Аннотация `@Id` говорит о том, что в таблице, столбец связанный с данным полем является Primary Key.

Столбец с __Primary Key__ содержит уникальное значение и не может быть `null`.

***

### @GeneratedValue
Аннотация `@GeneratedValue` описывает стратегию по генерации значений для столбца с Primary Key.

__Стратегии:__
* `GenerationType.AUTO` – дефолтный тип. Выбор стратегии будет зависеть от типа базы с которой мы работаем. Но лучше самому указывать конкретную стратегию!
* `GenerationType.IDENTITY` полагается на автоматическое увеличение столбца в БД (autoincrement). Например, для __MySQL__, __PostgreSQL__.
* `GenerationType.SEQUENCE` полагается на работу __Sequence__, созданного в БД-х. Например, для БД ORACLE.
* `GenerationType.TABLE` полагается на значение столбца таблицы БД-х. Цель такой таблицы – поддержка уникальности значений.

По-умолчанию __Hibernate__ пытается найти sequence таблицу `hibernate_sequence`.

Настройка в конфиге `hbm2ddl.auto` говорит, что eсли указан параметр `true` – может создавать таблица `hibernate_sequence`.

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private int id;
```

***

### @Column
Аннотация `@Column` говорит о том, к какому именно столбцу из таблицы мы привязываем поле класса.

Наиболее часто используемые аттрибуты аннотации `@Column` такие:
* `name`. Указывает имя столбца в таблице;
* `unique`. Определяет, должно ли быть данноезначение уникальным;
* `nullable`. Определяет, может ли данное поле быть NULL, или нет;
* `length`. Указывает, какой размер столбца (например колчиство символов, при использовании String).

***

### @JoinColumn
__Join Table__ – это таблица, которая отображает связь между строками 2-х других таблиц.

Столбцы __Join Table__ – это __Primary Key__, которые ссылаются на __Primary Key__ связываемых таблиц.

В аннотации `@JoinTable`:
* Мы прописываем название таблицы, которая выполняет роль __Join Table__;
* В `joinColumns` мы указываем столбец таблицы __Join Table__, который ссылается на __Primary Key source таблицы__;
* В `inverseJoinColumns` мы указываем столбец таблицы __Join Table__, который ссылается на __Primary Key target таблицы__.
```java
@ManyToMany(cascade = CascadeType.ALL)
@JoinTable(
        name = "child_section",
        joinColumns = @JoinColumn(name = "section_id"),
        inverseJoinColumns = @JoinColumn(name = "child_id")
)
private List<Child> children;
```

Аннотация `@JoinColumn` указывает на столбец, который осуществляет связь с другим объектом. Здесь всегда нужно указать название столбца __Foreign Key__:
```java
@JoinColumn(name = "details_id")
private Detail detail;
```

При использовании связи __One-to-Many__ в аннотации `@JoinColumn` name будет ссылаться на __Foreign Key__ не из __source__, а из __target__ таблицы.
```java
@OneToMany(cascade = CascadeType.ALL)
@JoinColumn(name = "department_id")
List<Employee> emps;
```

***

### @OneToOne, @OneToMany, @ManyToOne и @ManyToMany
Cascade операций – это выполнение операции не только для __Entity__, на котором операция вызывается, но и на связанных с ним __Entity__.

* `@OneToOne` – (объект) - человек и документ.
* `@OneToMany` – (коллекция) - автор может иметь много книг.
* `@ManyToOne` (объект) - у многих книг может быть один автор.
* `@ManyToMany` - (коллекция) – у книги может быть несколько авторов, автор может иметь много книг.

Говорим __Hibernate__ пусть ищит связь у поля `empDetail`, саму связь мы не прописываем, она уже прописана у `empDetail`:
```java
@OneToOne(mappedBy = "empDetail")
```

#### Аттрибут orphanRemoval
`orphanRemoval = true` - все ссылки, которые осиротели, также удаляются.

```java
@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
private List<Meal> meals;
```

#### Аттрибут cascade
По умолчанию каскад не работает, поэтому его нужно прописывать:
```java
@OneToOne(cascade = CascadeType.ALL)
private User user;
```
Каскад всегда нужно ставить в отношении родителя, т.е. в @OneToMany:
* ПРАВИЛЬНО:
  ```java
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
  ```
* НЕ ПРАВИЛЬНО: 
  ```java
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  ```

`CascadeType.ALL` - В каскаде, выбранное действие происходят одновременно с родителем и расспостраняется на ссылаемую сущность.

***

### @Cache
Аннотация для кешировнаия с пакета `org.hibernate.annotations.Cache`.

***

### @BatchSize
Влиять на стратегию загрузки связанных коллекций можно при помощи аннотации `@BatchSize` (или атрибут batch-size в XML), 
которая устанавливает количество коллекций, которые будут загружаться в одном запросе.
```java
@BatchSize(size = 200)
private Set<Role> roles;
```

***

### @DynamicUpdate
Ставиться над классом, говорит Hibernate чтобы обновлял только изменённые поля, а не все как по дефолту.

***




## Работа с БД
> Любые изменения (обновления/удаления) в таблице подтверждаем методом `executeUpdate()` после __HQL__ запроса.

Для получения объектов из базы используется __HQL__ (Hibernate Query Language). __HQL__ очень схож с __SQL__.

__Примеры:__
```java
// получения списка:
List<Employee> emps = session.createQuery("from Employee where name='Jack' and salary > 600")
                    .getResultList();
```
```java
// обновление:
session.createQuery("update Employee set salary=1000 where name='Jack'")
        .executeUpdate();
```

### Каскады (Cascade)
> Есть SQL `ON .. CASCADE`, которая выполняется в базе данных, и есть аннотация в Hibernate, исполняемая в приложении.
> 
> Если каскады есть в БД, то указывать (по сути дублировае) в модели не нужно.
>
> Hibernate ничего не знает про записи которые удаляются по ON CASCADE в БД.

> При каскадном сохранении `session.save()` не работает, нужно использовать `session.persist()`.

> Под капотом при `CascadeType.ALL` работает метод `save()` в реализации __Hibernate__, но в реализации __JPA__ есть только `persist()`!

__Cascade операций__ – это выполнение операции не только для __Entity__, на котором операция вызывается, но и на связанных с ним __Entity__.

В связях не всегда нужно каскадное удаление, пример: Исключаем цепное удаление, т.е. `CascadeType.REMOVE` не будет!
```java
@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
```

***

**Из TopJava:**

При создании таблиц `user_roles` и `meals` для внешнего ключа мы указывали свойство `ON DELETE CASCADE`. Это означает, что при удалении
пользователя база данных автоматически будет удалять все записи, которые на него ссылались по внешнему ключу. Существует также
свойство `ON UPDATE CASCADE`, определив которое, при обновлении первичного ключа пользователя этот ключ обновлялся бы во всех зависимых
таблицах. При каскадных операциях на уровне базы данных могут возникнуть проблемы с консистентностью кэша второго уровня, так как все
операции производятся в обход Hibernate.

Hibernate тоже позволяет указывать `CascadeType` для управляемых сущностей (что не имеет абсолютно никакой связи с таким же свойством в базе данных).

> Для Hibernate допускается указывать `CascadeType` только для сущности `@OneToMany` - со стороны родителя!  
> Для этой цели со стороны дочерней сущности можно указать аннотацию `@OnDelete` над ссылкой на родительскую сущность.  
> Для коллекций элементов сущности все действия всегда распространяются каскадно!



## Relationships, отношения, связи
* [Стратегии загрузки коллекций в Hibernate](https://dou.ua/lenta/articles/hibernate-fetch-types/)

### Ассоциативное связывание
Связывание ассоциаций – это связывание (`mapping`) классов и отношений между таблицами в БД. 

Сущействует 4 типа таких зависимостей: 
* Many-to-One;
* One-to-One;
* One-to-Many;
* Many-to-Many.

### Типы загрузки стратегий
> `EAGER` - считается антипатерном, т.к. нельзя уйти в `LAZY`.

Типы загрузки данных:
- `EAGER` (нетерпеливая) загрузка – при её использовании связанные сущности загружаются сразу вместе с загрузкой основной сущности;
- `LAZY` (ленивая) загрузка – при её использовании связанные сущности НЕ загружаются сразу вместе с загрузкой основной сущности. 
Связанные сущности загрузятся только при первом обращении к ним. Множество (__MANY__) не подгружается по умолчанию - `LAZY`.

В большинстве случаев при большом количестве связанных сущностей целесообразнее использовать __Lazy loading__ по следующим причинам:
* __Lazy загрузка__ имеет лучший performance по сравнению с __Eager загрузкой__.
* Иногда при загрузке основной сущности, нам просто не нужны связанные с ней сущности. Поэтому их загрузка – это лишняя работа.


Дефолтный вариант загрузки зависит от типа связи. Типы выборки по умолчанию:
* __One-to-One__ = `EAGER`
* __One-to-Many__ = `LAZY`
* __Many-to-One__ = `EAGER`
* __Many-to-Many__ = `LAZY`

### Uni и Bi-directional associations
* __Uni-directional__ - это отношения, когда одна сторона о них не знает.
При Uni-directional (однонаправоеной) связи где работник ничего не знает о департаменте, а департамент знает о работнике - здесь департамент __source__ таблица, а работник - __target__.
* __Bi-directional__ - это отношения, когда обе стороны знают друг о друге.
В __Bi-directional__ отношениях с помощью аннотации `@OneToOne` и `mappedBy` мы показываем __Hibernate__, где нужно искать связь между классами.



## Cache
> Объекты и их зависимости кешируются отдельно.

> По умолчанию сущности не кешируются - требуется к ним добавить 2 аннотации: **@Cache** и/или **@Cacheble**

Кеширование является одним из способов оптимизации работы приложения, ключевой задачей которого является уменьшить количество прямых обращений к базе данных.

***

Чтобы указать Hibernate какие сущности будут кэшироваться, их нужно пометить аннотацией `@Cache` и в скобках указать необходимую стратегию кэширования.

Такая аннотация предоставляется как JPA, так и Hibernate, аннотация Hibernate позволяет определять дополнительные параметры кэширования.

***

**Стратегии кэширования**

Для кэширования определяются 4 стратегии, которые определяют его поведение в определенных ситуациях:

* Read-only
* Read-write
* Nonstrict-read-write
* Transactional

### Кэш первого уровня (First Level Cache)
> Кеш первого уровня (First-level cache) - включен по умолчанию всегда, его нельзя отключить, кэширует сущности на уровне сессии.

***

Кеш первого уровня всегда привязан к объекту сессии. Hibernate всегда по умолчанию использует этот кеш и его нельзя отключить.

* Включен автоматически и не требует настройки.
* Доступ только из текущей сессии (в новой сессии будут новые SQL запросы).
* Находит по первичному ключу.
* Располагается внутри сессии.

При использовании методов `save()`, `update()`, `saveOrUpdate()`, `load()`, `get()`, `list()`, `iterate()`, `scroll()` всегда будет задействован кеш первого уровня.

***

Кэш первого уровня – это кэш Сессии (Session), который является обязательным. Через него проходят все запросы. 
Перед тем, как отправить объект в БД, сессия хранит объект за счёт своих ресурсов.

***

В том случае, если мы выполняем несколько обновлений объекта, **Hibernate** старается отсрочить (насколько это возможно) обновление для того, чтобы сократить количество выполненных запросов.
Если мы закроем сессию, то все объекты, находящиеся в кэше теряются, а далее – либо сохраняются, либо обновляются.

***

### Кэш второго уровня (Second level Cache)
> Кеш второго уровня (Second-level cache) - используется на уровне фабрики сессий, __по умолчанию всегда отключен и не имеет реализации в Hibernate__, 
> для его использования нужно самостоятельно подключать сторонние реализации;

> Еще одна важная деталь про кеш второго уровня — Hibernate не хранит сами объекты ваших классов.
> Он хранит информацию в виде массивов строк, чисел и т.д. И идентификатор объекта выступает указателем на эту информацию.
> Концептуально это нечто вроде `Map`, в которой `id` объекта — ключ, а массивы данных — значение.
> Приблизительно можно представить себе это так: `1 -> { "Pupkin", 1, null , {1,2,5} }`
>
> Следует помнить — зависимости вашего класса по умолчанию также не кешируются.


Кэш второго уровня является необязательным (опциональным) и изначально Hibernate будет искать необходимый объект в кэше первого уровня.
В основном, кэширование второго уровня отвечает за кэширование объектов.

#### Подключение кэш 2 уровня Hibernate на примере Topjava
Существует множество сторонних реализаций кэша, которые можно подключить к Hibernate. Одна из самых распространенных - **EhCache**. 
Для того чтобы подключить к Hibernate **EhCache**, в файл `pom.xml` нужно добавить дополнительную зависимость:
```xml
<dependency>
   <groupId>org.hibernate</groupId>
   <artifactId>hibernate-jcache</artifactId>
   <version>${hibernate.version}</version>
 </dependency>
```
Теперь кэш подключен и его осталось лишь сконфигурировать в файле `spring-db.xml` для профилей `datajpa, jpa`:

```xml
<entry key="#{T(org.hibernate.cache.jcache.ConfigSettings).PROVIDER}" value="org.ehcache.jsr107.EhcacheCachingProvider"/>

<!--Кэш может делить свои области на регионы-->
<entry key="#{T(org.hibernate.cfg.AvailableSettings).CACHE_REGION_FACTORY}" value="org.hibernate.cache.jcache.internal.JCacheRegionFactory"/>

<!--Включаем кэш второго уровня (по умолчанию value = false)-->
<entry key="#{T(org.hibernate.cfg.AvailableSettings).USE_SECOND_LEVEL_CACHE}" value="true"/>

<!--Можно подключить кэширование запросов, по умолчанию оно отключено. Оставим отключенным, value = false-->
<entry key="#{T(org.hibernate.cfg.AvailableSettings).USE_QUERY_CACHE}" value="false"/>
```
Сам кэш более тонко настраивается в отдельном файле `ehcache.xml`.

В нем мы указываем какие таблицы будут кэшироваться, количество элементов, время и множество других параметров.

Чтобы указать Hibernate какие сущности будут кэшироваться, их нужно пометить аннотацией `@Cache` и в скобках указать необходимую стратегию кэширования.
Такая аннотация предоставляется как **JPA**, так и **Hibernate**, аннотация **Hibernate** позволяет определять дополнительные параметры кэширования.

Так как мы пометили `User` `@Cache`, то сущности будут заноситься в кэш второго уровня, но не будет кэшироваться коллекция ролей. 
Чтобы роли тоже кэшировались, нужно так же пометить это свойство пользователя аннотацией `@Cache`.

Теперь, при запуске тестов мы столкнемся с частой проблемой — так как перед каждым тестом мы повторно заполняем базу тестовыми данными, 
и делаем мы это в обход **Hibernate** - содержимое кэша 2 уровня и базы данных будут различаться.
Поэтому перед каждым тестом дополнительно нужно кэш инвалидировать — специально для этого мы создадим утильный класс `JpaUtil`, 
где определим метод, который будет получать текущую `SessionFactory` и инвалидировать кэш **Hibernate**. 
Объект этого класса внедрили в тесты, и, так как в `spring-db.xml` мы определили, что этот бин будет создаваться только для 
профилей `jpa, datajpa`, тесты для **JDBC** реализации перестанут работать. 
`JpaUtil` отсутствует в профиле `jdbc` и не может быть разрезолвен при поднятии **Spring контекста**.




***

### Кэш запросов (Query Cache)
* [How does Hibernate Query Cache work](https://vladmihalcea.com/how-does-hibernate-query-cache-work/)

> Кеш запросов (Query cache) - по умолчанию отключен, включается определением дополнительных параметров в конфигурационном файле. 
> В нем кэшируются идентификаторы объектов, которые соответствуют совокупности параметров совершенного ранее запроса;

В Hibernate предусмотрен кэш для запросов и он интегрирован с кэшем второго уровня. 
Это требует двух дополнительных физических мест для хранения кэшированных запросов и временных меток для обновления таблицы БД. 
Этот вид кэширования эффективен только для часто используемых запросов с одинаковыми параметрами.




## SessionFactory
> В __JPA__ аналог `EntityManagerFactory`.

`SessionFactory` – фабрика по производству сессий.

В Java приложении достаточно создать объект `SessionFactory` 1 раз, и затем можно его переиспользовать.

`SessionFactory` нужно в конце закрывать - лучше с помощью блока `finally`, а сессию оборачивать в `try`.

Самый важный и самый тяжёлый объект (обычно создаётся в единственном экземпляре, при запуске приложения). 
Нам необходима как минимум одна `SessionFactory` для каждой БД, каждая из которых конфигурируется отдельным конфигурационным файлом.

### Пример правильного построения SessionFactory
Объект `Configuration` используется для создания объекта `SessionFactory` и конфигурирует сам __Hibernate__ с помощью конифигурационного XML-файла, который объясняет, как обрабатывать объект `Session`.
```java
SessionFactory factory=new Configuration()
        .configure("hibernate.cfg.xml")
        .addAnnotatedClass(Child.class)
        .addAnnotatedClass(Section.class)
        .buildSessionFactory();
```

### [Как правильно строить SessionFactory](https://ru.stackoverflow.com/a/483422)
Как строить SessionFactory правильнее всего - полный ответ по ссылке с несколькими вариантами, главное ниже:

Правильнее всего делать вот так:
```java
return new Configuration().configure().buildSessionFactory();
```
Основная причина, на мой взгляд, это то что все выше перечисленные подходы не будут вообще (!) правильно работать с Hibernate 5.

Хотя `buildSessionFactory()` объявлена как `@Deprecated` в __Hibernate 4__, в __Hibernate 5__ она запилена обратно! 
Соответственно, выше приведенный код будет правильно работать во всех версиях __Hibernate__.




## Session
> В JPA аналог EntityManager. Часто используется термин "persistence context".

`Session` – это обёртка вокруг подключения к базе с помощью __JDBC__.

`Session` – это основа для работы с БД. Именно с помощью `Session` мы будем добавлять, получать и делать другие операции с Java объектами в БД.

* Для того чтобы создать сессию, с начало нужно создать `SessionFactory`.
* Жизненный цикл `Session` обычно не велик. Мы получаем `Session`, делаем с помощью неё определённые операции и она становится не нужной.

> `EntityManager` (объект JPA) может делегировать свою функциональность `Session` методом `getDelegate()`:
> ```java
> Session s = (Session) em.getDelegate();
> ```

Благодаря тому, что сессия является легковесным объектом, его создают (открывают сессию) каждый раз, когда возникает необходимость, а потом, когда необходимо, уничтожают (закрывают сессию). 
Мы создаём, читаем, редактируем и удаляем объекты с помощью сессий.

Мы стараемся создавать сессии при необходимости, а затем уничтожать их из-за того, что они не являются потоко-защищёнными и не должны быть открыты в течение длительного времени.


### flush()
* [FlushMode in JPA and Hibernate – What it is and how to change it](https://thorben-janssen.com/flushmode-in-jpa-and-hibernate/)

Метод `flush` позволяет нам синхронизировать in-memory состояние (объекта Session) Persistence контекста с БД (т.е. записывает изменения в БД).

С начало все изменения в сессии записываются в оперативную память (получили объекты, изменили данные), но эти изменения ещё не в БД, 
т.к. мы ещё не закомитили их, именно метод `session.flush()` очищает оперативную память от этих объектов и синхронизирует (применяет все изменения) в БД. 
Но т.к. коммита ещё не было, то эти данные ещё не подтверждены в БД.

Flush происходит в трех ситуациях:
> Когда вы делаете commit транзакции Hibernate;
> До того как выполняется запрос в БД (`entityManager` делает `flush()` до выполнения запроса. Это происходит не для каждого запроса! 
Важно учесть, что цель сессии Hibernate минимизировать количество операций записи в БД, поэтому она не делает `flush()`, когда не считает это необходимым);
> Когда вы вызываете `entityManager.flush()`.



## Transaction
Транзакции — это группа операций на чтение/запись, выполняющихся только если все операции из группы успешно выполнены.

В Hibernate транзакции обрабатываются менеджером транзакций.

***


Всегда с работой БД нужно открывать транзакцию - `session.beginTransaction()`, а в конце закрывать - `session.getTransaction().commit()`.
```java
public static void main(String[] args) {
    SessionFactory factory = new Configuration()
        .configure("hibernate.cfg.xml")
        .addAnnotatedClass(Employee.class)
        .buildSessionFactory();
    try {
        Session session = factory.getCurrentSession();
        Employee emp = new Employee("Sergio", "Kru", "IT", 500);
        session.beginTransaction();
        session.save(emp);
        session.getTransaction().commit();
    } finally {
        factory.close();
    }
}
```

> __Перед каждой транзакцией нужно получать новую сессию!__
```java
Session session = factory.getCurrentSession();
Employee emp = new Employee("Amy", "Stuart", "Sales", 800);
session.beginTransaction();
session.save(emp);
session.getTransaction().commit();

int myId = emp.getId();
session = factory.getCurrentSession();
session.beginTransaction();
Employee employee = session.get(Employee.class, myId);
session.getTransaction().commit();
```




## Запросы
### JOIN FETCH
Чтобы корневые сущности со связанными коллекциями были загружены в одном SQL запросе, используйте оператор __JOIN FETCH__.
```java
List books = getCurrentSession()
        .createQuery("select b from BookFetchModeJoin b join fetch b.authors a").list();
```

### SQL-операций
> Проверять запросы Hibernate нужно через `run`. Если делаете debug и брекпойнт, то могут делаться лишние запросы к базе (дебаггер дергает `toString`)

> Если приложение испытывает проблемы с производительностью, которые вызваны неправильным выбором стратегии загрузки, для анализа пригодятся следующие свойства Hibernate конфигурации:
> ```properties
> hibernate.show_sql=true
> hibernate.format_sql=true
> hibernate.use_sql_comments=true
> ```

> Согласно [JavaDocs](https://docs.jboss.org/hibernate/orm/3.5/javadocs/org/hibernate/event/def/AbstractFlushingEventListener.html#performExecutions%28org.hibernate.event.EventSource%29)
> Hibernate порядок SQL операций следующий:
> * `INSERT`
> * `UPDATE`
> * `DELETE` элементов коллекций
> * `INSERT` элементов коллекций
> * `DELETE`

> Стандарты SQL определяют 4 уровня изолированности:
> * READ_UNCOMMITTED
> * READ_COMMITTED
> * REPEATABLE_READ
> * SERIALIZABLE

### JPQL
> В JPQL нет `UPSERT`.
> 
> В запросах JPQL есть автоматический полиморфизм.

JPQL (Java Persistence query language) - это язык запросов, практически такой же как SQL, однако вместо имен и колонок таблиц БД, он использует имена классов Entity и их атрибуты. 
В качестве параметров запросов так же используются типы данных атрибутов Entity, а не полей БД.

В отличии от SQL, в запросах JPQL есть автоматический полиморфизм, т.е. есть каждый запрос к Entity возвращает не только объекты этого Entity, 
но так же объекты всех его классов-потомков, независимо от стратегии наследования (например, запрос `select * from Animal`, 
вернет не только объекты `Animal`, но и объекты классов `Cat` и `Dog`, которые унаследованы от `Animal`). 

Чтобы исключить такое поведение используется функция `TYPE` в `where` условии (например `select * from Animal a where TYPE(a) IN (Animal, Cat)` 
уже не вернет объекты класса `Dog`).

### SQL
> Создавать SQL запросы нужно через объект `NativeQuery`. Раньше использовали `SQLQuery`.

```java
NativeQuery query = session.createSQLQuery("select * from student where mark>60");
query.addEntity(Student.class);
List<Student> students = query.list();

for (Student o : students) {
    System.out.println(o);
}
```

### HQL
> Создавать HQL запросы нужно через объект `Query`.

> `%` - будь-яка кількість будь-яких символів:
> ```java
> // HQL: дістаємо всіх person у котрих name починається з `C`:
> List<Person> people = session.createQuery("FROM Person WHERE name LIKE 'C%'").getResultList();
> ```

Пример использования алиасов - особенно нужно, если юзаешь пару таблиц:
```java
Query q = session.createQuery("select id, name, mark from Student s where s.mark > 50");
List<Object[]> students = (List<Object[]>) q.list();
for (Object[] o : students) {
    System.out.println(o[0] + " : " + o[1] + " : " + o[2]);
}
```
```java
// HQL: дістаємо всіх person з age більшим за 30:
List<Person> people = session.createQuery("FROM Person WHERE age > 30").getResultList();
```
```java
// HQL: дістаємо всіх person у котрих name починається з `C`:
List<Person> people = session.createQuery("FROM Person WHERE name LIKE 'C%'").getResultList();
```
```java
// HQL: оновлюємо всіх person у котрих age меньший за 30:
session.createQuery("update Person set name='Test' where age < 30").executeUpdate();
```
```hql
-- дістати людину по id книги
SELECT DISTINCT p FROM Book b INNER JOIN Person p ON b.owner.id = p.id WHERE b.id=3
```
```hql
-- отримати всі книжки з власником, у котрих є власник
SELECT DISTINCT b FROM Book b INNER JOIN Person p ON b.owner.id IS NOT NULL
```
```hql
-- отримати всі книжки з власником, назви книжок яких починаються на: 
SELECT DISTINCT b FROM Book b INNER JOIN Person p ON b.title LIKE :searchRequest%
```

### Criteria
Criteria API это тоже язык запросов, аналогичным JPQL (Java Persistence query language), однако запросы основаны на методах и объектах, т.е. запросы выглядят так:
```java
CriteriaBuilder cb = ...;

CriteriaQuery<Customer> q = cb.createQuery(Customer.class);
Root<Customer> customer = q.from(Customer.class);
q.select(customer);
```

Отходят от этого, склоняются всё больше к спецификациям JPA.

Для создания Criteria используется метод `createCriteria()` интерфейса `Session`. 
Этот метод возвращает экземпляр сохряаняемого класса (persistent class) в результате его выполнения.

По сути это объектно-ориентированный способ создания запросов.


### Пример одинакового запроса на JPQL и SQL
```jpaql
em.createQuery("DELETE FROM Payout pa WHERE pa.user.id=:productId")
```
```sql
em.createNativeQuery("DELETE FROM payouts WHERE product_id=:productId")
        .setParameter("productId", product.getId())
        .executeUpdate();
```

### Вирішення проблеми N+1 в Hibernate
Необхідно написати грамотний `JOIN` - в результаті буде зроблений лише один запит, зотрий завантажить усі додаткові сутності.
```java
// SQL: A LEFT JOIN B
List<Person> people = session.createQuery("SELECT p FROM Person p LEFT JOIN FETCH p.items")
    .getResultList();
```
```java
// отримаємо подібний результат:
for (Person person : people) {
    System.out.println("Person " + person.getName() + " has: " + person.getItems());
}

Person Tom has: [Item{id=1, itemName='Book}, Item{id=2, itemName='AirPods}]
Person Tom has: [Item{id=1, itemName='Book}, Item{id=2, itemName='AirPods}]
Person Bob has: [Item{id=3, itemName='iPhone}]
Person Bob has: [Item{id=4, itemName='Kindle}, Item{id=5, itemName='TV}, Item{id=6, itemName='Playstation}]
Person Bob has: [Item{id=4, itemName='Kindle}, Item{id=5, itemName='TV}, Item{id=6, itemName='Playstation}]
Person Bob has: [Item{id=4, itemName='Kindle}, Item{id=5, itemName='TV}, Item{id=6, itemName='Playstation}]
```
... але, наступна проблема в тому, що дані дублюються. Тут краще зберігати дані у `Set`:
```java
Set<Person> people = new HashSet<Person>(session.createQuery("SELECT p FROM Person p LEFT JOIN FETCH p.items")
    .getResultList());

Person Bob has: [Item{id=3, itemName='iPhone}]
Person Tom has: [Item{id=1, itemName='Book}, Item{id=2, itemName='AirPods}]
Person Bob has: [Item{id=4, itemName='Kindle}, Item{id=5, itemName='TV}, Item{id=6, itemName='Playstation}]
```
... і не забуваймо що треба перевизначити методи `equals` та `hashCode` щоб вірно отримувати унікальних Person.


## Connection pool
Чтобы получить соединение с БД, __Hibernate__ использует JDBC-подключение, а это времязатратная операция. 
Поэтому, рекомендуется использовать __connection pool__, например - `c3p0` (подключить зависимость). 
Этот пул хранит соединения открытыми некоторое время, и закрывает их, когда они уже становятся не нужными. 
`c3p0` - __connection pool__, который позволяет экономить время на подключение к БД.
```xml
<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource"
          destroy-method="close">
    <property name="driverClass" value="com.mysql.cj.jdbc.Driver" />
    <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/my_db?useSSL=false" />
    <property name="user" value="hbstudent" />
    <property name="password" value="hbstudent" />
</bean>
```


## Debug
> Проверять запросы **Hibernate** нужно через `run`. Если делаете debug и брекпойнт, то могут делаться лишние запросы к базе (дебаггер дергает `toString`)!


## Errors
__Error executing DDL "alter table student_roles add constraint FK5wsgmwcdh1mu2aakbatae9ouh foreign key (student_id) references student (id)" via JDBC Statement__

При данной ошибке нужно установить необходимый диалект для используемой БД.
Например: `MySQL8Dialect`, `MySQLInnoDBDialect`, `MySQLMyISAMDialect`, `MySQL5Dialect`.
```properties
hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```
***

__SQL Error: 1046, SQLState: 3D000<br>
No database selected__

Нужно в конфиге, у настройки:
```xml
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/</property>
```
дописать схему или БД, взависимости от иерархии:
```xml
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/jpa_tech</property>
```

***

__SQL Error: 1146, SQLState: 42S02<br>
Table doesn't exist__

Необходимо в настройках выбрать другую версию диалекта, например с:
```xml
<property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
```
на:
```xml
<property name="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</property>
```

## Examples


### Приклад запуску програми

```java
// скорочена версія:
SessionFactory factory = new Configuration().configure()
        .addAnnotatedClass(Alien.class)
        .buildSessionFactory();          // загружает настройки
Session session = factory.openSession(); // открывает сессию
session.beginTransaction();              // открывает транзакцию
Alien a = session.get(Alien.class, 101); // достает объект
session.getTransaction().commit();       // закрывает транзакцию
session.close();                         // высвобождаем ресурсы
factory.close();
```
```java
// розширена версія:
import kru.sk.hibernatedemo.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class App {
  public static void main(String[] args) {
    Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

    // отримуємо SessionFactory - щоб отримати сесію
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    // отримуємо Session - щоб працювати з Hibernate
    Session session = sessionFactory.getCurrentSession();

    try {
      // перед роботою відкриваємо транзакцію
      session.beginTransaction();

      // тут працюємо з Hibernate - save, update, delete... з entity

      // після роботи закриваємо транзакцію
      session.getTransaction().commit();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      // закриваємо фабрику
      sessionFactory.close();
    }
  }
}
```

***

## Приклад роботи з entity без CASCADE
> **Note**<br>
> Не забуваємо про кеш сутності!


```java
// отримуємо всі item конкретної person - Hibernate сам створить запит до БД
Person person = session.get(Person.class, 3);
List<Item> items = person.getItems();
```
```java
// отримуємо власника
Item item = session.get(Item.class, 5);
System.out.println(item.getOwner());

@Entity
@Table(name = "person")
public class Person {
  @OneToMany(mappedBy = "owner")
  private List<Item> items;
}

@Entity
@Table(name = "item")
public class Item {
  @ManyToOne
  @JoinColumn(name = "person_id", referencedColumnName = "id")
  private Person owner;
}
```
```java
// додаємо новий item людині - з двох сторін
Person person = session.get(Person.class, 2);

Item newItem = new Item("Item for Hibernate", person);

// це є гарною практикою, щоб Hibernate не звертався до кешу зі старими даними
person.getItems().add(newItem);

session.save(newItem);
```
```java
// створює нову людину і новий предмет для неї
Person newPerson = new Person("Test newPerson", 33);
Item newItem = new Item("Item for Hibernate 2", newPerson);

// best practise - щоб Hibernate брав актуальні дані, а ні з кешу
newPerson.setItems(new ArrayList<>(Collections.singletonList(newItem)));

session.save(newPerson);
session.save(newItem);
```
```java
// видалити всі товари людини
Person person = session.get(Person.class, 3);

List<Item> items = person.getItems();
items.forEach(session::delete);

// best practice - тепер на другій стороні чистимо листа, щоб не з кешу інфа...
person.getItems().clear();
```
```java
// видалити людину
Person person = session.get(Person.class, 2);

// Hibernate видалить людину, а БД всім Item для person_id виставить null, тому що ON DELETE SET NULL
session.remove(person);

// best practice - оновлюємо кеш
person.getItems().forEach(i -> i.setOwner(null));
```
```java
// змінюємо власника предмета
Person person = session.get(Person.class, 4);
Item item = session.get(Item.class, 1);

// видаляємо предмет у теперішнього власника - оновлюємо кеш
item.getOwner().getItems().remove(item);

// назначаємо нового власника
item.setOwner(person);

// best practice - оновлюємо кеш
person.getItems().add(item);
```


***

## Приклад роботи з entity з CASCADE
> **Note**<br>
> Каскадування можна робити на рівні Hibernate - його функціоналом, але краще на рівні JPA! 
> Також каскадування можна робити на рівні БД! 

> Для оновлення кешу можна використовувати метод Hibernate refresh() - оновить дані для сутності в кешу.
> Також оновлення можна налаштувати для всіх залежних сутностей:
```java
// на рівні Hibernate
import javax.persistence.*;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "person")
public class Person {
	@OneToMany(mappedBy = "owner") // анотація JPA
	@Cascade(org.hibernate.annotations.CascadeType.REFRESH) // анотація Hibernate
	private List<Item> items;
}

// операції з Person...
session.refresh(person); // зробить новий запит для Person і всії її Item's
```


Налаштування CASCADE для автоматичного збереження Item для Person:
```java
// на рівні JPA:
import javax.persistence.*;

@Entity
@Table(name = "person")
public class Person {
	@OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
	private List<Item> items;
}

Person person = new Person("Test cascading", 30);
Item item = new Item("Test cascading item", person);
person.setItems(new ArrayList<>(Collections.singletonList(item)));  

// налаштований CASCADE збереже Item автоматично
session.persist(person); 
```
```java
// на рівні Hibernate
import javax.persistence.*;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "person")
public class Person {
	@OneToMany(mappedBy = "owner") // анотація JPA
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE) // анотація Hibernate
	private List<Item> items;
}

Person person = new Person("Test cascading", 30);
Item item = new Item("Test cascading item", person);
person.setItems(new ArrayList<>(Collections.singletonList(item)));

// налаштований CASCADE збереже Item автоматично
session.save(person);
```


## Questions

1. __Каким способом можно получить метаданные JPA (сведения о Entity типах, Embeddable и Managed классах и т.п.)?__<br>


Для получения такой информации в JPA используется интерфейс `Metamodel`. 
Объект этого интерфейса можно получить методом `getMetamodel()` у `EntityManagerFactory` или `EntityManager`.

***

