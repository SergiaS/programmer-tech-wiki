# Validation
First, add dependence *[org.hibernate.validator](https://mvnrepository.com/artifact/org.hibernate.validator/hibernate-validator)* to your `pom.xml`:
```xml
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>7.0.1.Final</version>
</dependency>
```

> Необходимо использовать аннотации с пакета __javax.validation.constraints__, а не от hibernate.
> Это сделано с целью устранения жестного связывания, например, в случае перехода на другой фреймворк.

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
