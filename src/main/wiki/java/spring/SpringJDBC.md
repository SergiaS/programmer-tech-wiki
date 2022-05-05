# [Spring Data JDBC](https://spring.io/projects/spring-data-jdbc)


## JdbcTemplate
* [Java Concepts: JdbcTemplate Querying Spring](https://medium.com/beingcoders/java-concepts-jdbctemplate-querying-spring-3192bed61f44)

`JdbcTemplate` работает через сеттеры. 
Вместе с конструктором по умолчанию их нужно добавить в свою модель.



## RowMapper
`RowMapper<T>` отображает/переводит строки из таблицы в сущности (модели). Используется при выборке с БД методом `jdbcTemplate.query()`.

`RowMapper<T>` это параметризированный интерфейс, перебирает ResultSet за тебя. Для использования, нужно имплементировать в свой класс представления сущности, например - UserRowMapper. 
А потои засеттить поля - `user.setId(resultSet.getInt("id"))`, `user.setId(resultSet.getString("name"))`...

```java
private static final class MP3RowMapper implements RowMapper<MP3> {
    @Override
    public MP3 mapRow(ResultSet rs, int rowNum) throws SQLException {
        MP3 mp3 = new MP3();
        mp3.setId(rs.getInt("id"));
        mp3.setName(rs.getString("name"));
        mp3.setAuthor(rs.getString("author"));
        return mp3;
    }
}

@Override
public List<MP3> getMP3ListByAuthor(String author) {
    String sql = "select * from mp3 where lower(author) like :author";

    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("author", "%" + author.toLowerCase() + "%");

    return jdbcTemplate.query(sql, params, new MP3RowMapper());
}
```

Либо можно использовать одну из дефолтных реализаций, которая сама просеттит все поля в сущности, 
например `BeanPropertyRowMapper` - он используется для простого использования.
```java
BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
```

### BeanPropertyRowMapper
`BeanPropertyRowMapper` работает через отражение. 
Ему нужны геттеры/сеттеры и имена полей должны "совпадать" с колонками `ResultSet` 
(Column values are mapped based on matching the column name as obtained from result set metadata to public setters for the corresponding properties. 
The names are matched either directly or by transforming a name separating the parts with underscores to the same name using "camel" case).



## SimpleJdbc
* [JDBC Operations Using SimpleJdbc Classes](https://www.baeldung.com/spring-jdbc-jdbctemplate#jdbc-operations-using-simplejdbc-classes)

`SimpleJdbc` - предоставляет простой путь конфигурирования и запуска SQL операторов.


## Batch
Это групповая операция, которая накапливает в себе SQL, а потом выполняет их за один запрос.


## Examples

### Пример записи в БД
Имплементировать можно используя объект 
* `MapSqlParameterSource` - implementation that holds a given Map of parameters;
* или `BeanPropertySqlParameterSource` - implementation that obtains parameter values from bean properties of a given JavaBean object.

Пример имплементации...
```java
private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
private final JdbcTemplate jdbcTemplate;
private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
private final SimpleJdbcInsert insertUser;
```
...`MapSqlParameterSource`:
```java
MapSqlParameterSource map = new MapSqlParameterSource()
        .addValue("id", payout.getId())
        .addValue("date_time", payout.getDateTime())
        .addValue("amount", payoutAmount)
        .addValue("notes", payout.getNotes())
        .addValue("user_id", userId);

if (payout.isNew()) {
    Number newId = simpleJdbcInsert.executeAndReturnKey(map);
    payout.setId(newId.intValue());
} else {
    if (namedParameterJdbcTemplate.update(
            "UPDATE payouts " +
               "SET date_time=:date_time, amount=:amount, notes=:notes " +
             "WHERE id=:id AND user_id=:user_id", map) == 0)
    return null;
}
```

...или `BeanPropertySqlParameterSource`:
```java
BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

if (user.isNew()) {
    int newId = insertUser.executeAndReturnKey(parameterSource).intValue();
    user.setId(newId);
} else if (namedParameterJdbcTemplate.update(
        "UPDATE users " +
        "SET name=:name, email=:email, password=:password, registered=:registered," +
            "enabled=:enabled, profit_per_day=:profitedPerDay " +
        "WHERE id=:id", parameterSource) == 0) {
    return null;
}
return user;
```


### Пример инициализация и популирование DB
* [Initializing a Database by Using Spring XML](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#jdbc-initializing-datasource-xml)

Сначала нужно добавить namespaces:
```
xmlns:jdbc="http://www.springframework.org/schema/jdbc"
xsi:schemaLocation=http://www.springframework.org/schema/jdbc 
                   http://www.springframework.org/schema/jdbc/spring-jdbc.xsd
```
```xml
<jdbc:initialize-database data-source="dataSource" enabled="${database.init}">
    <jdbc:script location="classpath:db/initDB.sql"/>
    <jdbc:script encoding="utf-8" location="classpath:db/populateDB.sql"/>
</jdbc:initialize-database>
```
где, `${database.init}` - значение с property файла (`true`), а `dataSource` - бин коннекта к БД:
```xml
<bean id="dataSource"
      class="org.springframework.jdbc.datasource.DriverManagerDataSource">
    <property name="driverClassName" value="org.postgresql.Driver"/>
    <property name="url" value="${database.url}"/>
    <property name="username" value="${database.username}"/>
    <property name="password" value="${database.password}"/>
</bean>
```

### Пример поочередной вставки в БД
```java
public int insert(MP3 mp3) {
    String sqlInsertAuthor = "insert into author (name) VALUES (:authorName)"; // запрос для автора

    Author author = mp3.getAuthor();

    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("authorName", author.getName());

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(sqlInsertAuthor, params, keyHolder);

    int author_id = keyHolder.getKey().intValue(); // получаем id вставленного автора

    String sqlInsertMP3 = "insert into mp3 (author_id, name) VALUES (:authorId, :mp3Name)";  // запрос для mp3

    params = new MapSqlParameterSource();
    params.addValue("mp3Name", mp3.getName());
    params.addValue("authorId", author_id);

    return jdbcTemplate.update(sqlInsertMP3, params); 
}
```

