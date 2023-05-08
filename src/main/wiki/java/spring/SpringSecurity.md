# SpringSecurity
* [[amigoscode]: Spring Security Architecture Explained](https://www.youtube.com/watch?v=h-9vhFeM3MY)
* [[Spring Blog]: Spring Security without the WebSecurityConfigurerAdapter](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)
* [[bezkoder]: WebSecurityConfigurerAdapter Deprecated in Spring Boot](https://www.bezkoder.com/websecurityconfigureradapter-deprecated-spring-boot/)
* [Spring Boot 3 + Spring Security 6 - JWT Authentication and Authorisation [NEW] [2023]](https://github.com/SergiaS/e-security)
* [[DOC] - Spring Security](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#web.security) || [DOC - Architecture](https://docs.spring.io/spring-security/reference/servlet/architecture.html)

* [[TopJava] - Форма логина / логаут.](https://github.com/JavaWebinar/topjava/blob/doc/doc/lesson09.md#-7--форма-логина--логаут)

> `{noop}` - means _[NoOpPasswordEncoder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/password/NoOpPasswordEncoder.html)_.
> **Креденшилы в Spring Security**: Пользователь по умолчанию `user`, а пароль - предоставляется при загрузке сервера в консоли IDEA (Using generated security password: faaa6e1b-4f13-44c3-a9cb-4f14392f569f).

> Модуль [Spring Security Taglib](https://docs.spring.io/spring-security/reference/servlet/integrations/jsp-taglibs.html) содержит набор тегов для jsp-страниц (зависимость `spring-security-taglibs`). 
> [Из TopJava](https://github.com/JavaWebinar/topjava/blob/doc/doc/lesson10.md#-4-spring-security-taglib-method-security-expressions)

> The _Security Filters_ are inserted into the `FilterChainProxy` with the _SecurityFilterChain API_. 
> [**<u>The order of Filter instances matters</u>**](https://docs.spring.io/spring-security/reference/servlet/architecture.html#servlet-security-filters).

> Ми можемо змінити дефолтного юзера на свого без конфігу - задав значення в `application.properties`:
> ```properties
> spring.security.user.name=Bob
> spring.security.user.password=password
> spring.security.user.roles=USER
> ```

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

> [Прибизна послідовність роботи створеного фільтра](https://www.youtube.com/watch?v=tfHfvy57H_w&list=PLbuI9mmWSoUEIatm6_1KPaJIJfYX5L4To&index=13)
>
> = CustomFilter
> 
> == get some customAuthentication object;
> 
> == AuthenticateManager.authenticate(customAuthentication);
> 
> === request will pass throw all your customProviders (which was added into config).



## Annotations

### @EnableGlobalMethodSecurity(prePostEnabled = true)
> The `@EnableGlobalMethodSecurity(prePostEnabled = true)` annotation is what enables the `@PreAuthorize` annotation.

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



## Spring Security architecture
* IMPORTANT [Spring Boot Security Configuration, practically explained — A deep intro to filter/token-based security](https://blog.devgenius.io/spring-boot-security-configuration-practically-explained-part6-a-deep-intro-to-56ce03860ad)

![img](https://raw.githubusercontent.com/SergiaS/programmer-tech-wiki/master/src/main/resources/img/spring_security_6_architecture.jpg)

І так, як ж працює Spring Security?

По-простому: є об'єкти, котрі потребують інших об'єктів - **Spring Security** складається з ланцюга фільтрів.

Розглянемо приклад, коли юзер робить запит з `username` та `password`:
1. **BasicAuthenticationFilter**<br>
Спочатку HTTP запит оброблюється фільтром `BasicAuthenticationFilter`.
Цей запит має містити header Basic  `Authorization` (Base64-encoded username/password token).
З отриманих даних `username` та `password` буде створений `Authentication` об'єкт, ще називають authentication token,
зазвичай використовується реалізація `UsernamePasswordAuthenticationToken` котра імплементує інтерфейс `Authentication`.
2. **AuthenticationManager**<br>
Далі, після створення об'єкта `Authentication` передається до однієї з реалізацій `AuthenticationManager` -
Менеджер аутентифікації не обов'язково налаштовувати у конфігурації - за замовчуванням автоматично буде задіяна реалізація
`ProviderManager`.
Реалізація на основі `AuthenticationManager` повинна перевизначати метод `Authentication authenticate(Authentication authentication) throws AuthenticationException;`.
Можна додавати декілька провайдерів - працюють як фільтри.
3. **AuthenticationProvider**<br>
Потім об'єкт `Authentication` передається до провайдера - `DaoAuthenticationProvider`, `JwtAuthenticationProvider`...
За замовчуванням `DaoAuthenticationProvider` дефолтна реалізація при обраному менеджері `ProviderManager`.
Якщо аутентифікація успішна - повернеться об'єкт `Authentication` (його реалізація).

Загалом вибір фільтрів, менеджерів аутентифікації, провайдерів аутентифікації... залежить від способу аутентифікації.

Наприклад, `UserDetailsService` використовується провайдером `DaoAuthenticationProvider`. 
Цей сервіс потрібен для отримання інфи про з БД для перевірки введених даних користувачем.
Інший провайдер буде використовувати інший спосіб перевірки даних (без 'UserDetailsService').



`Authentication`, котрий створюється, наприклад, з логіну і паролю, що передаються в запиті. 
Далі цей об'єкт передається до `AuthenticationManager` (наприклад, `ProviderManager`)<br>

`>` `AuthenticationManager`<br>

`>` `AuthenticationProvider`, дефолтні реалізації _DaoAuthenticationProvider_, _JWTAuthenticationProvider_...<br>

`>` `UserDetailsService`, check if the username-password pair is a valid one.<br>
Usually, (but not always and not necessarily), a custom AuthenticationManager or AuthenticationProvider uses a specific service. 
Many times, this service can be a custom implementation of the UserDetailsService interface and can deal with any type of data source (e.g.: in-memory, database, LDAP, etc).

`>` `UserDetails` - User has to implement UserDetails because it has `getAuthorities()` [roles and permissions], `getPassword()`,
`getUsername()`, `isAccountNotExpired()`, `isAccountNotLocked()`, `isCredentialsNotExpired()`, `isEnabled()`.


***

`Authentication` object is passed to a class that implements the `AuthenticationManager` interface.
An `AuthenticationManager` implementation should override the contract `authenticate()` method to authenticate the `Authentication` object.

`AuthenticationManager` is the API that defines how Spring Security's filters perform `Authentication` object.

Note that an `AuthenticationManager` instantiation automatically takes place by the **Spring Security** mechanism, even without any configuration.

`ProviderManager` is the most commonly used implementation of `AuthenticationManager`.


## Authentication

> [**Authentication: JWT usage vs session?**](https://stackoverflow.com/a/45214431)
>
> По суті, JWT не має переваг перед сесіями.
> JWT надають засоби підтримки стану сеансу на клієнті замість того, щоб робити це на сервері.

> Якщо використовується JWT токен, тоді сесії треба вимикати - обирається одна технологія, приклад:
>
> <details>
> <summary>ПРИКЛАД SECURITY КОНФІГУ З JWT-ФІЛЬТРОМ</summary>
>
> ```java
> import com.bezkoder.springjwt.security.jwt.AuthEntryPointJwt;
> import com.bezkoder.springjwt.security.jwt.AuthTokenFilter;
> import com.bezkoder.springjwt.security.services.UserDetailsServiceImpl;
> import org.springframework.beans.factory.annotation.Autowired;
> import org.springframework.context.annotation.Bean;
> import org.springframework.context.annotation.Configuration;
> import org.springframework.security.authentication.AuthenticationManager;
> import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
> import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
> import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
> import org.springframework.security.config.annotation.web.builders.HttpSecurity;
> import org.springframework.security.config.http.SessionCreationPolicy;
> import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
> import org.springframework.security.crypto.password.PasswordEncoder;
> import org.springframework.security.web.SecurityFilterChain;
> import org.springframework.security.web.access.AccessDeniedHandlerImpl;
> import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
> 
> @Configuration
> @EnableMethodSecurity(prePostEnabled = true)
> public class WebSecurityConfig {
>   @Autowired
>   UserDetailsServiceImpl userDetailsService;
> 
>   @Autowired
>   private AuthEntryPointJwt unauthorizedHandler;
> 
>   @Bean
>   public AuthTokenFilter authenticationJwtTokenFilter() {
>     return new AuthTokenFilter();
>   }
> 
>   @Bean
>   public DaoAuthenticationProvider authenticationProvider() {
>     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
> 
>     authProvider.setUserDetailsService(userDetailsService);
>     authProvider.setPasswordEncoder(passwordEncoder());
> 
>     return authProvider;
>   }
> 
>   @Bean
>   public PasswordEncoder passwordEncoder() {
>     return new BCryptPasswordEncoder();
>   }
> 
>   @Bean
>   public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
>     return authConfig.getAuthenticationManager();
>   }
> 
>   @Bean
>   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
>     http
>         .cors().and().csrf().disable()
>         .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
>         .exceptionHandling().accessDeniedHandler(new AccessDeniedHandlerImpl()).and() // just sends a 403 (Forbidden) response
>         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
>         .authorizeRequests().antMatchers("/api/auth/**").permitAll()
>         .antMatchers("/api/test/**").permitAll()
>         .anyRequest().authenticated();
> 
>     http.authenticationProvider(authenticationProvider());
> 
>     http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
> 
>     return http.build();
>   }
> }
> ```
>
> </details>

> `AuthenticationManager` в Spring Security передає об'єкт `Authentication` до `AuthenticationProvider` для виконання логіки аутентифікації.

### [Authentication Mechanisms](https://docs.spring.io/spring-security/reference/servlet/authentication/index.html#servlet-authentication-mechanisms)
* **Username and Password** - how to authenticate with a username/password.
Authentication, based on username and password — _Basic Authentication_.
> The Spring Basic Authentication follows the Basic HTTP Authentication standard and it is also known as “Basic Auth”, “http basic auth”, etc. 
> In short, when we want to send a http request, we must provide an Authorization Header with a value of a Base64-encoded username:password token prefixed by the 
> word indicating the scheme that should be used, which in the Basic Authentication is the word “Basic ” (A space should separate the Basic word and the Base64-encoded token). 
> The example below is taken from the official documentation: for a user with username “Aladdin” and password “open sesame” the following header would be presented:
>
> `Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==`
> 
> [SOURCE](https://blog.devgenius.io/spring-boot-security-configuration-practically-explained-part6-a-deep-intro-to-56ce03860ad)
* **OAuth 2.0 Login** - OAuth 2.0 Log In with OpenID Connect and non-standard OAuth 2.0 Login (i.e. GitHub).
* **SAML 2.0 Login** - SAML 2.0 Log In.
* **Remember Me** - how to remember a user past session expiration.
* **JAAS Authentication** - authenticate with JAAS.
* **Pre-Authentication** Scenarios - authenticate with an external mechanism such as SiteMinder or Java EE security but still use Spring Security for authorization and protection against common exploits.
* **X509 Authentication** - X509 Authentication.

***

Real authentication happens when an `Authentication` object has been authenticated.

The `Authentication` object (the authentication token) actually, can be either **un-authenticated** or **authenticated**.

When the `BasicAuthenticationFilter` (цей фільтр виконує аутентифікацію на основі HTTP Basic Authentication) processes and extracts the _username_ and _password_ it creates an instance of the 
`UsernamePasswordAuthenticationToken` class which actually is un-authenticated and puts it in the **SecurityContext** (using the `SecurityContextHolder`).

```java
UsernamePasswordAuthenticationToken authenticationToken = 
    new UsernamePasswordAuthenticationToken(username, password);

SecurityContextHolder.getContext()
    .setAuthentication(authenticationToken);
```
The `UsernamePasswordAuthenticationToken` class, (which implements the Authentication interface), is a well-known and widely used class.

If the `Authentication` object (the username/password credentials in the `UsernamePasswordAuthenticationToken`) is successfully authenticated, then, 
the `BasicAuthenticationFilter` puts the `Authentication` object with the **Principal** inside the `SecurityContextHolder`. 
Thus, the `SecurityContextHolder` is where **Spring Security** also stores the details of <u>_who is authenticated and her/his authorizations (authorities/roles)_</u>.

***

#### The SecurityContext and the SecurityContextHolder
The SecurityContext is the instantiated object of the `SecurityContext` interface, and it contains an `Authentication` object. 
The `SecurityContext` is wrapped by the `SecurityContextHolder` class, the "heart" of Spring Security’s authentication model. 
Via the `SecurityContextHolder`, the `Authentication` object can be set or picked up by an authentication manager (or authentication provider) and 
processed by an `AuthenticationProvider` via the `ProviderManager` or by a custom `AuthenticationManager`.

![img](https://miro.medium.com/max/828/1*_yurhMJBn0KJT5NfI37YOg.webp)



### Authentication Manager
> Any instantiation of the `BasicAuthenticationFilter` requires an instance of an `AuthenticationManager` to be injected (to be passed in its constructor).
> Also note, that the same is true for many other inbuilt filter classes.

From Spring Security version 6 (Spring Boot 3) we can create an `AuthenticationManager` bean by getting an instance of 
the `AuthenticationManagerBuilder` from the context as a shared object. Example:
```java
@Bean
public AuthenticationManager authManager(HttpSecurity http) throws Exception {
  AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    
  authenticationManagerBuilder.inMemoryAuthentication()
    .withUser("user")
    .password("password")
    .roles("ADMIN");
  
  return authenticationManagerBuilder.build();
}
```
The example above concerns a `AuthenticationManager` which can be used globally (because it is `@Bean` annotated). 
Without any security configuration (when just the security dependency is on the classpath), **Spring Boot** provides a default global 
`AuthenticationManager`, with only one default user (with username `user` and a randomly generated password which you can get from application logs).

Here we can add an in-memory user just for our demo purposes. For that, we can use the 
`AuthenticationManagerBuilder` with the simplest authentication possible, which is based on the `inMemoryAuthentication()`.
This adds in-memory authentication to the `AuthenticationManagerBuilder` using the `InMemoryUserDetailsManagerConfigurer` 
to allow customization of the in-memory authentication.
We use it just to add a user with username `user`, password `password` and roles `ADMIN`. 
As you can understand this actually represents the `Authentication` object e.g., an instance of the 
`UsernamePasswordAuthenticationToken` which is being used by the `AuthenticationManager`.

Finally, we can use the above-defined `AuthenticationManager` to authenticate an incoming request via the `BasicAuthenticationFilter`.


##  Authentication Provider
Any implementation of a custom Authentication Provider (based on the `AuthenticationProvider` interface) should use and 
override 2 contract methods.

> <details>
> <summary>EXAMPLE</summary>
> 
> ```java
> @Component
> public class MyFirstCustomAuthenticationProvider implements AuthenticationProvider {
> 
>   private String uname = "";  
>   private String upassw = "";
>   private String existingUserName = "Aladdin";
>   private String existingPassword = "open sesame";
> 
>   @Override
>   public Authentication authenticate(Authentication authentication) throws AuthenticationException {
>     uname = String.valueOf(authentication.getName());
>     upassw = String.valueOf(authentication.getCredentials());
>   
>     if (uname.equals(existingUserName) && upassw.equals(existingPassword)) {
>       UsernamePasswordAuthenticationToken authenticationToken;
>       authenticationToken = new UsernamePasswordAuthenticationToken(uname, null, getAuthorities());
>       return authenticationToken;
>     }
>     return null;
>   }
> 
>   @Override
>   public boolean supports(Class<?> authentication) {
>     return authentication.equals(UsernamePasswordAuthenticationToken.class);
>   }
> 
>   private List<GrantedAuthority> getAuthorities() {
>     List<GrantedAuthority> authorities = new ArrayList<>();
>     List<String> roles = Arrays.asList("ADMIN", "USER");
>     roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
>     return authorities;
>   }
> }
> ```
> </details>

Next, we have to use it in the app’s custom Security class with the `FilterChainBean`.

How we can get `AuthenticationManager`? From `AuthenticationManagerBuilder`:
```java
@Autowired
private AuthenticationManagerBuilder authenticationManagerBuilder;

@Bean
public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
  http
  .csrf().disable()
  // ...
  
  http.addFilter(new BasicAuthenticationFilter(authenticationManagerBuilder.getObject()));

  return http.build();
```

Instead of the `AuthenticationManagerBuilder`, we can also autowire the `AuthenticationConfiguration` bean itself. 
This is because the class has a very specific method, the `getAuthenticationManager()`, that could be a bit clearer for 
obtaining the `AuthenticationManager`.
```java
@Autowired
private AuthenticationConfiguration authenticationConfiguration;

@Bean
public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
  http
  .csrf().disable()
  // ...
  
  http.addFilter(new BasicAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()));

  return http.build();
```


## UserDetailsService

### Using a custom UserDetailsService with our custom AuthenticationProvider
There are several examples out there, even in the official documentation (that show how simple is to customize a `UserDetailsService`). 
Spring indeed provides us with a number of implementation classes and configurers that cover the most common 
cases e.g. (`InMemoryUserDetailsManager`, `JdbcDaoImpl`, `JdbcUserDetailsManager`, `LdapUserDetailsManager`, `LdapUserDetailsService`).


## JWT
* [Modifying our CustomRequestHeaderTokenFilter to support JWT Bearer token-based Authorization](https://blog.devgenius.io/spring-boot-security-configuration-practically-explained-part6-a-deep-intro-to-56ce03860ad) [scroll down]

Dependency(-ies)

We need a good library for doing the job with JWT tokens e.g.: encoding, generating, extracting, validating, and so on. 
There is a quite long list of such libraries and you can take a look at the Maven repository here on your own. 
A good choice for me is the jjwt-api. Below are the dependencies you should add to the app’s pom.xml:
```xml
<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.1</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.1</version>
    <scope>runtime</scope>
</dependency>
```

We can always define the constants we need externally, e.g. in `application.properties` file, and get them as environment variables instead making this class:
```java
public class SecurityConstants {

  public static final String JWT_SECRET = "t3pCSx2wx1ExbQ5z43XXB8my/KR24aon4EH/niU9iZi1I3S69rk1QhlMFFsTrZIY";
  //public static final long EXPIRATION_TIME = 864_000_000; // 10 days
  public static final long EXPIRATION_TIME = 36_000_000; // 10 hours
  //public static final long EXPIRATION_TIME = 3_600_000;// 1 hour
  //public static final long EXPIRATION_TIME = 600_000; // 10 minutes
  public static final String BEARER_TOKEN_PREFIX = "Bearer ";
  public static final String BASIC_TOKEN_PREFIX =  "Basic ";
  public static final String AUTH_HEADER = "Authorization";

  public static final String SIGN_IN_URI_ENDING = "/signin";

}
```

```java
@Component
public class CustomRequestHeaderTokenFilter extends OncePerRequestFilter {

  @Autowired
  CustomUserDetailsService userService;

  private final Log logger = LogFactory.getLog(getClass());
  private AuthenticationManager authManager;

  public CustomRequestHeaderTokenFilter(AuthenticationManager authenticationManager) {
    Assert.notNull(authenticationManager, "AuthenticationManager cannot be null");
    this.authManager = authenticationManager;
  }


  @Override
  protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
                                  jakarta.servlet.http.HttpServletResponse response,
                                  jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
    String uname = "";
    String upassw = "";   
    UsernamePasswordAuthenticationToken authentication;
    String uri = request.getRequestURI();
    logger.info("Request URI: " + uri);

    try {
        String headerToken = "";
        headerToken = request.getHeader(SecurityConstants.AUTH_HEADER);
        logger.info("Authorization Header value: " + headerToken);

        if (headerToken == null || (!headerToken.startsWith(SecurityConstants.BASIC_TOKEN_PREFIX) && !headerToken.startsWith(SecurityConstants.BEARER_TOKEN_PREFIX)))  {
          this.logger.trace("No Authorization header found!");
          filterChain.doFilter(request, response);
          return;
        }

        if (headerToken.startsWith(SecurityConstants.BASIC_TOKEN_PREFIX) && uri.endsWith(SecurityConstants.SIGN_IN_URI_ENDING)) {
          headerToken = StringUtils.delete(headerToken, SecurityConstants.BASIC_TOKEN_PREFIX).trim();
          uname = TokenUtils.decodedBase64(headerToken)[0];
          upassw = TokenUtils.decodedBase64(headerToken)[1];
          this.logger.trace(LogMessage.format("Credentials username '%s' and password '&s' have been found in Basic Authorization header", uname, upassw));
        
          authentication = new UsernamePasswordAuthenticationToken(uname, upassw); 
          Authentication authResult = this.authManager.authenticate(authentication);
          SecurityContextHolder.getContext().setAuthentication(authResult);
      
          logger.info("(Authenticated) Authentication: " + authResult.toString());
        } else if ( headerToken.startsWith(SecurityConstants.BEARER_TOKEN_PREFIX) && !uri.endsWith(SecurityConstants.SIGN_IN_URI_ENDING)) {
          headerToken = StringUtils.delete(headerToken, SecurityConstants.BEARER_TOKEN_PREFIX).trim();
          if (TokenUtils.isJWTTokenValid(headerToken)) {
            uname = TokenUtils.getUsernameFromJWTUserToken(headerToken);
            this.logger.trace(LogMessage.format("username '%s' extracted from Bearer Authorization header", uname ));
            
            //  User is an org.springframework.security.core.userdetails.User object
            User user = (User) userService.loadUserByUsername(uname);
            if (user != null) {
              authentication = new UsernamePasswordAuthenticationToken(uname, null, user.getAuthorities());
              SecurityContextHolder.getContext().setAuthentication(authentication);

              logger.info("(Authorized) Authentication: " + authentication.toString());

            }
          }
        }
      } catch (AuthenticationException ex) {
        this.logger.info("Failed to process authentication request: " + ex.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
    filterChain.doFilter(request, response);
  }

}
```


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
As you can see the code is quite simple, yet it deals with both: **Basic Authentication** and **JWT Bearer token Authorization**.
The **Basic Authentication** is required only for the `/singin` endpoint. 
When a **Bearer token** is sent, then it validates the token, extracts the username from the JWT claim(s), 
and fetches the user roles/authorities via a `UserDetailsService` bean (no authentication for the JWT bearer token).


### Security config for JWT token
```java
@Configuration
@EnableWebSecurity
public class CustomSecurityConfiguration {

  @Autowired
  private AuthenticationConfiguration authenticationConfiguration;

  @Bean
  public AuthenticationManager authManager() throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public CustomRequestHeaderTokenFilter customFilter() throws Exception {
    return new CustomRequestHeaderTokenFilter(authManager());
  }

  @Bean
  public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint((request, response, authEx) -> {
          response.setHeader("WWW-Authenticate", "Basic realm=\"Access to /signin authentication endpoint\"");
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.getWriter().write("{ \"Error\": \"" + authEx.getMessage() + " - You are not authenticated.\" }");
        })
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()

        .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, "/auth/signup").permitAll()
            .requestMatchers(HttpMethod.GET, "/auth/signin").authenticated())

        .authorizeHttpRequests(authorize -> authorize.requestMatchers("/users").hasRole("ADMIN")
            .requestMatchers("/items").hasAnyRole("ADMIN", "USER")
        )
    ;
    //http.addFilterBefore( new CustomRequestHeaderTokenFilter(authenticationConfiguration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class);    // OK ---
    http.addFilterBefore(customFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
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

> **Warning**<br>
> Never disable CSRF protection while leaving session management enabled! Doing so will open you up to a Cross-Site Request Forgery attack.

> **Note**<br>
> За замовчуванням у **Spring Security** увімкнутий захист **CSRF** (він вимагає передавати у кожному запиті **CSRF** токен - 
> захист від між сайтової підробки запитів) - тому, при переходах по сторінках можуть викидатися помилки `403`, 
> хоча наче все повинно працювати. 
> Для тестів можна вимкнути цей захист, додавши у свій конфіг з методом `configure(HttpSecurity http)` - команду `.csrf().disable()`.

> **Note**<br>
> Захистом буде наявність у кожної формі прихованого поля `CSRF_TOKEN`.
> Кожний токен генерується сервером тільки 1 раз - перед наданням форми, йде генерація цього токену - надається форма з токеном - 
> і на сервері очікується форма зі згенерованим токеном. 
> Якщо токени співпали - все ок.

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


## Auth0
* [Spring Security provides comprehensive OAuth 2 support](https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html)

Дана технологія дає можливість увійти на сайт під акаунтом Google, Facebook, GiHub, Okta...
Надає обмежений доступ до ресурсів клієнта.

### [The OAuth 2.1 Authorization Framework](https://datatracker.ietf.org/doc/html/draft-ietf-oauth-v2-1-07#name-introduction-4)

#### Roles
OAuth defines four roles:

* `"resource owner"`:
An entity capable of granting access to a protected resource. 
When the resource owner is a person, it is referred to as an end-user. 
This is sometimes abbreviated as "RO".
* `"resource server"`:
The server hosting the protected resources, capable of accepting and responding to protected resource requests using access tokens. 
The resource server is often accessible via an API. 
This is sometimes abbreviated as "RS".
* `"client"`:
An application making protected resource requests on behalf of the resource owner and with its authorization. 
The term "client" does not imply any particular implementation characteristics 
(e.g., whether the application executes on a server, a desktop, or other devices).
* `"authorization server"`:
The server issuing access tokens to the client after successfully authenticating the resource owner and obtaining authorization. 
This is sometimes abbreviated as "AS".

#### Protocol Flow
```text
// Abstract Protocol Flow

+--------+                               +---------------+
|        |--(1)- Authorization Request ->|   Resource    |
|        |                               |     Owner     |
|        |<-(2)-- Authorization Grant ---|               |
|        |                               +---------------+
|        |
|        |                               +---------------+
|        |--(3)-- Authorization Grant -->| Authorization |
| Client |                               |     Server    |
|        |<-(4)----- Access Token -------|               |
|        |                               +---------------+
|        |
|        |                               +---------------+
|        |--(5)----- Access Token ------>|    Resource   |
|        |                               |     Server    |
|        |<-(6)--- Protected Resource ---|               |
+--------+                               +---------------+
```

The abstract OAuth 2.1 flow, illustrated at figure, describes the interaction between the four roles and includes the following steps:¶

1. The client requests authorization from the resource owner. 
The authorization request can be made directly to the resource owner (as shown), or preferably indirectly via the authorization server as an intermediary.
2. The client receives an authorization grant, which is a credential representing the resource owner's authorization, 
expressed using one of the authorization grant types defined in this specification or using an extension grant type. 
The authorization grant type depends on the method used by the client to request authorization and the types supported by the authorization server.
3. The client requests an access token by authenticating with the authorization server and presenting the authorization grant.
4. The authorization server authenticates the client and validates the authorization grant, and if valid, issues an access token.
5. The client requests the protected resource from the resource server and authenticates by presenting the access token.
6. The resource server validates the access token, and if valid, serves the request.



### Приклад з сервісом **auth0.com**
Гарний приклад з сервісом **auth0.com** дивись на [github](https://github.com/oktadev/okta-spring-boot-react-crud-example/tree/auth0).

Треба замінити дані в файлі `application.properties`:
```commandline
spring.security.oauth2.client.provider.auth0.issuer-uri=https://dev-auth0-sk.eu.auth0.com/
spring.security.oauth2.client.registration.auth0.client-id=Rn6Jq8CouXqUmlxj1PZlUGHAbKrpMmAW
spring.security.oauth2.client.registration.auth0.client-secret=y5gqTBGBTA4Xi1SIYoNjserrsPGfV2bn8Y2SUK_f_ngW5wa4HNRaP7NPSKcAI5Nx
```
В акаунті на сервісі `https://manage.auth0.com/` додай Application:
provide a memorable name, and select Regular Web Application. Specify http://localhost:8080/login/oauth2/code/auth0 for the Callback URLs and http://localhost:3000,http://localhost:8080 for the Allowed Logout URLs.

запускай спрінг та реакт, і пробуй login/logout...

### Приклад для Google
* [Example of OAuth customizing](https://www.youtube.com/watch?v=6fyUJ5MFutc&list=PLbuI9mmWSoUEIatm6_1KPaJIJfYX5L4To&index=6)

Потрібно узяти свої дані з гугла, або створити новий клієнт (Create OAuth client ID).
Для цього переходимо на [Google Cloud](https://console.cloud.google.com/welcome?project=carfinder-1664296453545&hl=uk).
Далі у розділ `API & Services` > `Credentials`. Потім натискаємо на `Create Credentials` > `OAuth client ID`.

В полі `Application type` обираємо `Web application`. Називаємо на свій розсуд.
І додаємо у поле `Authorized redirect URIs` свою адресу, наприклад `http://localhost:8080/login/oauth2/code/google`.

Після натискання `Create` отримаємо дані:
```text
Your Client ID:
524929617099-np1rtk29d53oo9596ofcqd7bfpv1ro88.apps.googleusercontent.com
```
```text
Your Client Secret:
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
```

Також до конфігу security треба додати authorities:
```java
@Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      // ...
        .authorizeHttpRequests(authConfig ->{
              authConfig.requestMatchers(HttpMethod.GET,"/user").hasAnyAuthority("ROLE_USER","OIDC_USER");
      // ...
}
```


## Listeners
Можна додати до налаштувань біни, які інформуватимуть про успішну чи не вдалу аутентифікацію:
```java
@Bean
public ApplicationListener<AuthenticationSuccessEvent> successEvent() {
  return event -> {
    System.err.println("Success Login " + event.getAuthentication().getClass().getName() + event.getAuthentication().getName());
  };
}

@Bean
public ApplicationListener<AuthenticationFailureBadCredentialsEvent> failureEvent() {
  return event -> {
    System.err.println("Bad Credentials Login " + event.getAuthentication().getClass().getName());
  };
}
```



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
