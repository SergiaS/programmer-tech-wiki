# SpringSecurity

* `{noop}` - means _[NoOpPasswordEncoder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/password/NoOpPasswordEncoder.html)_.



## Authentication

__Roles__

The roles while creating users have no significance while performing authentication, it will be used for authorization.

***


## Examples


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


