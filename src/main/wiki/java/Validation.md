# Validation

> **Note**<br>
> `org.hibernate.validator` це окремий проект від **Hibernate**!

First, add dependence *[org.hibernate.validator](https://mvnrepository.com/artifact/org.hibernate.validator/hibernate-validator)* 
to your `pom.xml`:
```xml
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>6.2.4.Final</version>
</dependency>
```

> Необходимо использовать аннотации с пакета `javax.validation.constraints` (версія залежності до 7, далі буде `jakarta`), а не от **Hibernate**.
> Это сделано с целью устранения жесткого связывания, например, в случае перехода на другой фреймворк.

> **WARNING**<br>
> **Hibernate Validator 7** and below doesn't work with Spring 5!
> You need to use **Hibernate Validator 6.2.4.Final** for work with Spring 5.
>
> [stackoverflow](https://stackoverflow.com/a/69076232)

Далее в модели указываем валидацию с аннотаций:
```java
public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Min(value = 0, message = "Age should be greater than 0")
    private int age;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    // other code
}
```

Потом в контроллере нужно добавить аннотацию `@Valid` к модели - означает, что модель будет проходить валидацию.

В случае ошибки при валидации, информация сохраняется в объект `BindingResult`.
Объект `BindingResult` всегда должен идти после валидируемой модели:
```java
@PostMapping
public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        return "people/new";
    }
    personDAO.save(person);
    return "redirect:/people";
}
```
Наличие ошибок можно проверить с помощью метода `hasErrors()` у объекта `bindingResult`.

И если ошибки были, мы вернём юзера на ту же страницу на которой он заполнял форму + теперь будут отображены сами ошибки, в данном случае с помощью Thymeleaf.
```html
<form th:method="POST" th:action="@{/people}" th:object="${person}">
  <label for="name">Enter name: </label>
  <input type="text" th:field="*{name}" id="name"/>
  <div style="color: red" th:if="${#fields.hasErrors('name')}" th:errors="*{name}">Name error</div>
  <br/>
  <label for="age">Enter age: </label>
  <input type="text" th:field="*{age}" id="age"/>
  <div style="color: red" th:if="${#fields.hasErrors('age')}" th:errors="*{age}">Age error</div>
  <br/>
  <label for="email">Enter email: </label>
  <input type="text" th:field="*{email}" id="email"/>
  <div style="color: red" th:if="${#fields.hasErrors('email')}" th:errors="*{email}">Email error</div>
  <br/>
  <input type="submit" value="Create!"/>
</form>
```

***

[Валидация для сохранения в БД через JPA](https://github.com/JavaWebinar/topjava/blob/doc/doc/lesson10.md#-3-%D1%80%D0%B5%D1%84%D0%B0%D0%BA%D1%82%D0%BE%D1%80%D0%B8%D0%BD%D0%B3-jquery-%D0%BA%D0%BE%D0%BD%D0%B2%D0%B5%D1%80%D1%82%D0%BE%D1%80%D1%8B-%D0%B8-%D0%B3%D1%80%D1%83%D0%BF%D0%BF%D1%8B-%D0%B2%D0%B0%D0%BB%D0%B8%D0%B4%D0%B0%D1%86%D0%B8%D0%B8-%D0%BF%D0%BE-%D1%83%D0%BC%D0%BE%D0%BB%D1%87%D0%B0%D0%BD%D0%B8%D1%8E)

Для JPA можно задать группу валидацию, которая будет управлять валидацией перед сохранением в БД. Создадим маркерный интерфейс `View.Persist extends Default` и
в `spring-db.xml` сконфигурируем `entityManagerFactory`, задав ему эту группу для `pre-persist` и `pre-update`.
Теперь, перед любым сохранением в ДБ, Hibernate будет валидировать все дефолтные поля (тк мы отнаследовались от `Default`), и, дополнительно к ним, поля, помеченные нашим маркерным `View.Persist`,
а в контроллерах будут валидироваться только дефолтные поля. Поле `Meal#user` пометим `@NotNull(View.Persist.class)` - теперь оно будет валидироваться только при сохранении в БД.


