# SpringSecurity
* [DOC - Spring Security](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#web.security) || [DOC - Architecture](https://docs.spring.io/spring-security/reference/servlet/architecture.html)

* [TopJava - Форма логина / логаут.](https://github.com/JavaWebinar/topjava/blob/doc/doc/lesson09.md#-7--форма-логина--логаут)

* `{noop}` - means _[NoOpPasswordEncoder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/password/NoOpPasswordEncoder.html)_.
* **Креденшилы в Spring Security**: Пользователь по умолчанию `user`, а пароль - предоставляется при загрузке сервера в консоли IDEA (Using generated security password: faaa6e1b-4f13-44c3-a9cb-4f14392f569f).

* Модуль [Spring Security Taglib](https://docs.spring.io/spring-security/reference/servlet/integrations/jsp-taglibs.html) содержит набор тегов для jsp-страниц (зависимость `spring-security-taglibs`). [Из TopJava](https://github.com/JavaWebinar/topjava/blob/doc/doc/lesson10.md#-4-spring-security-taglib-method-security-expressions)

> **Warning**<br>
> `WebSecurityConfigurerAdapter` is deprecated from 2.7.x version.
> Use a `SecurityFilterChain` bean to configure **HttpSecurity** or a `WebSecurityCustomizer` bean to configure `WebSecurity`.
>
> [ARTICLE](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)
> 
> <details>
> <summary>CODE EXAMPLE</summary>
> 
> ```java
> // конфігурація з WebSecurityConfigurerAdapter
> @EnableWebSecurity
> public class SecurityConfig extends WebSecurityConfigurerAdapter {
>   @Override
>   protected void configure(HttpSecurity http) throws Exception {
>     http
>         .csrf().disable()
>         .authorizeRequests()
>         .antMatchers("/").permitAll()
>         .antMatchers("/user").hasRole("USER")
>         .antMatchers("/admin").hasRole("ADMIN")
>         .anyRequest().authenticated()
>         .and()
>         .httpBasic();
>   }
> }
> ```
> Той самий код тільки в іншій обгортці:
> ```java
> // конфігурація без WebSecurityConfigurerAdapter
> @EnableWebSecurity
> public class SecurityConfig {
>   public SecurityFilterChain configure(HttpSecurity http) throws Exception {
>     return http
>         .csrf().disable()
>         .authorizeRequests()
>         .antMatchers("/").permitAll()
>         .antMatchers("/user").hasRole("USER")
>         .antMatchers("/admin").hasRole("ADMIN")
>         .anyRequest().authenticated()
>         .and()
>         .httpBasic();
>   }
> }
> ```
> </details>

> **Note**
>
> **Автентифікація** (Authentication - Who is this user?) – це підтвердження того, ким є користувач <u>на вході</u>. 
> Це проходження перевірки автентичності.
> 
> **Авторизація** (Authorization - Are they allowed to do this?) – це те, що користувачу дозволяється робити <u>після входу</u>. 
> Це надання і перевірка прав на вчинення будь-яких дій в системі.
> Авторизація може бути двох видів - roles (набір дій - USER, ADMIN) або authorities (певні дії - SHOW_ACCOUNT, WITHDRAW, SEND_MONEY)


## Annotations

### @EnableGlobalMethodSecurity(prePostEnabled = true)
Говорит чтобы использовалась конфигурация с аннотациями (указывается в конфиге):
```java
// без @EnableGlobalMethodSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    // используем свою конфигурацию вместо базовой (super.configure(http);)
                .csrf().disable() // выключить защиту от подмнены сайтов
                .authorizeRequests() // авторизовать запросы
                    .antMatchers("/").permitAll() // кто и куда имеет доступ
                    .antMatchers(HttpMethod.GET,"/api/**").hasAuthority(Permission.DEVELOPERS_READ.getPermission())
                    .antMatchers(HttpMethod.POST,"/api/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
                    .antMatchers(HttpMethod.DELETE,"/api/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
                .anyRequest() // каждый запрос...
                    .authenticated() // должен быть аутентифицирован
                    .and() // и
                    .httpBasic(); // использовать аутентификацию Basic (пропускать запросы через базовую реализацию)
    }
}
```
```java
// с @EnableGlobalMethodSecurity
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                .anyRequest()
                    .authenticated()
                    .and()
                    .httpBasic();
    }
}
```

В самих аннотациях всё прописано, например `@PreAuthorize` в контроллерах над методами:
```java
@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestControllerV1 {

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    public Developer getById(@PathVariable long id) {
            return developers.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
```


### @PreAuthorize
Авторизація на рівні методів. 
Спочатку треба налаштувати свій конфіг - поставити над класом анотацію з параметром `@EnableGlobalMethodSecurity(prePostEnabled = true)`.
Тепер можна використовувати анотацію `@PreAuthorize` над методами - вона буде перевіряти ролі юзерів.

> **Note**<br>
> Зазвичай цю анотацію не використовують в контролерах, наприклад в сервісі:
```java
@Service
public class AdminService {

  @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_SOME_OTHER')")
  public void doAdminStuff() {
    System.out.println("Only admin here");
  }
}
```



## Authentication

__Roles__

The roles while creating users have no significance while performing authentication, it will be used for authorization.

***

### formLogin
Аутентификация с помощью формы - запрос будет перенаправлен на базовую страницу с формой.
Можно настроить свою страницу.


## Examples

### Create custom form page

> Зі SpringSecurity непотрібно реалізовувати ще один метод для обробки даних з форми

> Назви полів формі повинні бути саме username і password!

```java
// простий контролер
@Controller
@RequestMapping("/auth")
public class AuthController {

  @GetMapping("/login")
  public String loginPage() {
    return "auth/login";
  }
}
```
```java
// конфіг SpringSecurity - відповідає за сторінки входу, помилок...
// конфіг авторизації
@Override
protected void configure(HttpSecurity http) throws Exception {
  http
      .csrf().disable() // вимикаємо захист від міжсайтової підробки запитів
      // налаштування авторизації
      .authorizeRequests() // усі запити авторизувати...
      .antMatchers("/auth/login", "/error") // ...через ці сторінки...
        .permitAll() // ...доступні усім
      .anyRequest() // усі інші запити...
        .authenticated() // ...повинні пройти аутентифікацію
      .and()
      // налаштування своєї сторінки з логіном
      .formLogin().loginPage("/auth/login")
      .loginProcessingUrl("/process_login") // куди відправляти дані з форми
      .defaultSuccessUrl("/hello", true) // перенаправлення у разі вірних креденшелів
      .failureUrl("/auth/login?error"); // перенаправить у разі помилки
}
```
```thymeleafexpressions
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8">
  <title>Login page</title>
</head>
<body>
  <form name="f" method="post" action="/process_login">
    <label for="username">Enter username:</label>
    <input type="text" name="username" id="username">
    <br/>
    <label for="password">Enter password:</label>
    <input type="password" name="password" id="password">
    <br/>
    <input type="submit" th:value="login">

    <div th:if="${param.error}" style="color: red">
      Wrong username or password!
    </div>
  </form>
</body>
</html>
```


### [Configure Spring Security for In-Memory authentication and authorization](https://medium.com/@ritesh.panigrahi/spring-security-in-memory-authentication-and-authorization-dcb9cc8baf19)

__Authentication__

To configure authentication in spring security, we need to configure the `AuthenticationManager` (which handles the authentication obviously) using the builder class `AuthenticationManagerBuilder`.

To get `AuthenticationManagerBuilder` we need to extend our config class with `WebSecurityConfigurerAdapter` and then override it's `configure` method which takes `AuthenticationManagerBuilder` as a parameter.

```java
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("demo1").password("{noop}test1").roles("USER")
            .and()
            .withUser("demo2").password("{noop}test2").roles("ADMIN");
    }
}
```

> In Spring Security 5 we can’t store plain text passwords directly we need to tell spring which encoder we are using to encode the password in {xxxx}.

***

__Authorization__

For Authorization, we need to get hold of `HttpSecurity`.

As we have already extended the class `WebSecurityConfigurerAdapter`, we need to override the `configure` method which takes `HttpSecurity` as a parameter.

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/admin").hasRole("ADMIN")
            .antMatchers("/user").hasAnyRole("USER","ADMIN")
            .antMatchers("/").permitAll()
            .and().formLogin();
}
```

***

* [From TopJava](https://github.com/JavaWebinar/topjava/blob/doc/doc/lesson08.md#-8-добавление-spring-security)


## CSRF
* [Using Spring Security CSRF Protection](https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html#servlet-csrf-using)

> **Note**<br>
> За замовчуванням у **Spring Security** увімкнутий захист **CSRF** (він вимагає передавати у кожному запиті **CSRF** токен - 
> захист від між сайтової підробки запитів) - тому, при переходах по сторінках можуть викидатися помилки `403`, 
> хоча наче все повинно працювати. 
> Для тестів можна вимкнути цей захист, додавши у свій конфіг з методом `configure(HttpSecurity http)` - команду `.csrf().disable()`.

> **Note**<br>
> Захистом буде наявність у кожної формі прихованого поля `CSRF_TOKEN`.
> Кожний токен генерується сервером тільки 1 раз - перед наданням форми, йде генерація цього токену - надається форма з токеном - 
> і на сервері очікується форма зі згенерований токеном. 
> Якщо співпали токени - все ок.

> На усіх сторінках окрім сторінки з логіном приховане поле з `_csrf` вставляється автоматично thymeleaf'ом.
> ```thymeleafexpressions
> <input type="hidden" name="_csrf" value="f4259851-4e97-4cc1-9fda-27545596fb97">
> ```

> При увімкнутому **CSRF**, сторінка `/logout` буде працювати тільки через POST-запит.
> ```thymeleafexpressions
> <form th:action="@{/logout}" th:method="POST">
>     <input type="submit" value="Logout"/>
> </form>
> ```


### [TopJava - Межсайтовая подделка запроса (CSRF)](https://github.com/JavaWebinar/topjava/blob/doc/doc/lesson10.md#-9-межсайтовая-подделка-запроса-csrf)
В конфигурации `spring-security.xml` ранее мы принудительно отключили защиту от CSRF.
Удалим или закомментируем эту строчку. Теперь Spring Security добавит дополнительный `CsrfFilter` в свою цепочку.
Этот фильтр для каждого не-(GET, HEAD, TRACE, OPTIONS) запроса будет проверять наличие специального заголовка или скрытого поля.
Чтобы видеть, как обрабатываются запросы, в настройках логирования для класса `CsrfFilter` установим уровень *debug*.  
```xml
<logger name="org.springframework.security.web.csrf.CsrfFilter" level="debug"/>
```

В `bodyHeader.jsp` для `logout` заменим тип запроса с GET на POST, чтобы для него действовала защита от CSRF (невозможно злонамеренно разлогинить).
Для POST запросов из JSP форм, вместо добавления в форму поля CSRF токена, мы можем использовать тег Spring `form:form` - Spring Security добавит это поле сам.

Для добавления CSRF токена в AJAX запросы в `headTag.jsp` объявим тэги `meta` с `name="csrf"` и `name= "csrf_header"`, в аттрибуты `content` поместим значения, которые нам предоставляет Spring Security.
Чтобы к каждому AJAX запросу не добавлять CSRF header, в `topjava.common.js`  настроим через jQuery сразу все AJAX запросы.
Заголовок и токен получим из тэгов `meta` которые мы определили в `headTag.jsp`.

Запросы, которые отправляются на сервер через POST с типом `MediaType.APPLICATION_JSON_VALUE` также защищены в браузере правилом *same origin policy*:
невозможно сделать из браузера POST запрос с типом "application/json" на сайт с другим доменным именем без специального разрешения.


## Questions

***

### Почему при выполнении тестов AdminRestControllerTest не задействуется Spring Security?
Для этого в `MockMvc` надо **явно** добавлять security filter.

***

### Почему не нужен csrf для REST и нельзя подделать JSON запрос с вредоносного сайта?
Попробуйте выполнить AJAX запрос из вашего js скрипта на url, домен которого отличается от вашего (например 'http://topjava.herokuapp.com/meals/ajax/admin/users/{id}'). В консоли браузера
будет `XMLHttpRequest cannot load`... - <a href="https://developer.chrome.com/extensions/xhr">нарушение same origin policy</a>. Формам же разрешается делать submit (через `action=..`) на другой домен,
но невозможно cделать `Content-Type`, отличный от <a href="http://htmlbook.ru/html/form/enctype">стндартных enctype</a> и методов <a href="http://htmlbook.ru/html/form/method">кроме get и post</a>.
Таким образом `consumes = MediaType.APPLICATION_JSON_VALUE` для POST защищает приложение от CSRF.
