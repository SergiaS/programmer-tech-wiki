# Spring Web MVC

* [Spring Web MVC - DOCUMENTATION](https://docs.spring.io/spring-framework/docs/5.3.10/reference/html/web.html#mvc-servlet)
* [Spring MVC hello world example](https://mkyong.com/spring-mvc/spring-mvc-hello-world-example/)
* [habr - Spring Web MVC](https://habr.com/ru/post/490586/#:~:text=свойства%20не%20существует.-,Spring%20Web%20MVC,-Spring%20Web%20MVC)
* [habr - Как генерировать JSON/XML (представления) с помощью Spring Web MVC](https://habr.com/ru/post/490586/#:~:text=Как%20генерировать%20JSON%20/%20XML%20(представления)%20с%20помощью%20Spring%20Web%20MVC)
* [Spring MVC: создание веб-сайтов и RESTful сервисов](https://habr.com/ru/post/500572/)
* [Quick Guide to Spring Controllers](https://www.baeldung.com/spring-controllers)
* [Java Interview Series: Spring RESTful Web Services](https://medium.com/@.midi/interview-questions-on-spring-restful-web-services-86d0e5e28a14)
* [Обработка исключений в контроллерах Spring](https://habr.com/ru/post/528116/)
* [Spring и JDK 8: использование @Param и name/value в Spring MVC аннотациях не обязательно](https://habr.com/ru/post/440214/)
  > Аннотации `@PathVariable` и `@RequestParam` все еще часто нужны, чтобы приложение работало корректно.
  > Но их атрибуты **value/name** уже не обязательны: соответствие ищется по именам переменных.

***

> Если вы хотите обслуживать представления/view (HTML-страницы, которые отображаются на стороне сервера), вы должны использовать `@Controller`, а ваши методы контроллера должны возвращать имя вашего шаблона/страницы представления.
> Если вы планируете создать приложение RESTful, используйте `@RestController` или комбинацию `@Controller` и `@ResponseBody` вместе.

> `@Controller` должен возвращать представление **view** - имя страницы для отрисовки.
>
> [Как генерировать HTML представление (view) с помощью Spring Web MVC](https://habr.com/ru/post/490586/#:~:text=Как%20генерировать%20HTML%20представление%20(view)%20с%20помощью%20Spring%20Web%20MVC)

> `Spring Data Binding` - функциональность Spring преобразовывать данные в параметрах или теле запроса в экземпляры класса.
> Формат данных может быть как application/x-www-form-urlencoded (из html формы), так и JSON.
> Spring автоматически извлечет из запроса нужные данные и, используя отражение, сделает из них объект.
> Для этого объект должен иметь конструктор без параметров и сеттеры.

> You can use one of `WebInitializer` in place of `web.xml`:
> ```java
> public class WebInitializer
>     extends AbstractAnnotationConfigDispatcherServletInitializer {
>   
>     @Override
>     protected Class<?>[] getRootConfigClasses()
>     {
>         // TODO Auto-generated method stub
>         return null;
>     }
>   
>     @Override
>     protected Class<?>[] getServletConfigClasses()
>     {
>         // TODO Auto-generated method stub
>         return new Class[] { MVCconfig.class };
>     }
>   
>     @Override
>     protected String[] getServletMappings()
>     {
>         // TODO Auto-generated method stub
>         return new String[] { "/" };
>     }
> }
> ```

> Существует несколько различных библиотек шаблонов, которые хорошо интегрируются с Spring MVC, из которых вы можете выбрать: Thymeleaf, Velocity, Freemarker, Mustache и даже JSP (хотя это не библиотека шаблонов).


**Spring MVC** – это фреймворк для создания web приложений на Java, в основе которого лежит шаблон проектирования MVC.

* **<u>Model</u>** – контейнер для хранения данных.
* **<u>View</u>** – web страница, которую можно создать с помощью `html`, `jsp`, `Thymeleaf` и т.д. Часто при
  отображении _View_ использует данные из _Model_.
* **<u>Front Controller</u>** также известен под именем `DispatcherServlet`. Он является частью __Spring__. Остальные
  компоненты - _Model_ и _View_ - нужно создать самому.

## Конфигурирование на примере TopJava

Работа Spring MVC основана на паттерне Front Controller (Единая точка входа). Все запросы поступают в единый собственный
сервлет Spring, в котором происходит его перенаправление на нужный сервлет приложения.

Для работы со Spring MVC нужно заменить зависимость `spring-web` на `spring-webmvc`:

```xml

<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
</dependency>
```

После этого в `web.xml` необходимо сконфигурировать единую точку входа - Spring `DispatcherServlet`, в который будут
поступать все запросы к приложению:

```xml

<servlet>
    <servlet-name>mvc-dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
        <!--Spring MVC имеет собственный контекст, контексты объединяются в цепочке. 
        В итоге у нас будет два контекста - первый - контекст web-приложения, в котором находятся контроллеры
        и который обрабатывает запросы к приложению, второй - основной контекст приложения, в котором происходит 
        бизнес-логика. Ниже мы указываем путь к конфигурации web-контекста приложения-->
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring/spring-mvc.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>

        <!--Все запросы к приложения будут поступать в "/", этот сервлет-->
<servlet-mapping>
<servlet-name>mvc-dispatcher</servlet-name>
<url-pattern>/</url-pattern>
</servlet-mapping>
```

> Различные контексты не имеют доступа к бинам друг друга (исключение - дочерние контексты могут получать доступ к бинам родителя, но не наоборот),
> каждый контекст поднимает для себя свои собственные экземпляры, поэтому нужно следить за конфигурацией бинов и поднимать их в соответствующем контексте.

Конфигурацию **webmvc** контекста и диспетчер-сервлета определим в файле `spring-mvc.xml`.

**Сценарий обработки запроса**

* Запрос поступает в `dispatcher-servlet`, в нем определен набор Handler Mappings - классы, которые обрабатывают запросы
  в зависимости от их типа.
* Соответствующий запросу Handler делегирует обработку запроса нужному контроллеру.
* Контроллер необходимым образом обрабатывает запрос и возвращает View.
* View отображает результат выполнения запроса.

В нашем приложении будет два вида контроллеров: одни — работают с User Interface и отображают результат работы
приложения в браузере, другие — работают по REST-интерфейсу. Контроллеры помечаются аннотацией `@Controller`.

Паттерн и тип HTTP метода, по которым мы можем получить доступ к методу контроллера конфигурируются с помощью аннотации
`@RequestMapping(value = "/users", method = RequestMethod.GET)`. В последних версиях **Spring** можно сделать
проще: `@GetMapping("/users")`

При этом **Spring** внедрит в метод объект **Model**, в который мы можем добавлять атрибуты и передавать их из слоя
контроллера в слой представления.

Чтобы **Spring MVC** контекст мог осуществлять роутинг запросов по этим аннотациям, в конфигурации `spring-mvc.xml`
нужно вручную включить поддержку аннотаций:

```xml

<mvc:annotation-driven/>
```

Методы контроллера, помеченные аннотацией `@RequestMapping` (а также `@GetMapping`, `@PostMapping`, `@PutMapping`, ..)
после обработки запроса должны возвращать имя представления, в которое будет передана **Model**. Эта **View**
отобразится как результат выполнения запроса. Чтобы в этих методах возвращать только название нужной **View**, в
конфигурации нужно определить `ViewResolver`, который автоматически к этому названию добавит путь к **view** в
приложении и суффикс — формат **view**:

```xml

<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
      p:prefix="/WEB-INF/jsp/"
      p:suffix=".jsp"/>
```

Для того чтобы приложение имело доступ к статическим ресурсам (например, стили) - нужно добавить дополнительную
конфигурацию в `spring-mvc.xml`:

```xml

<mvc:resources mapping="/resources/**" location="/resources/"/>
```

> **Spring MVC** имеет конфигурацию по умолчанию. Если в `spring-mvc.xml` мы не укажем никаких **Handlers**, то их стандартный
> набор будет создан автоматически и приложение будет работать.
> Как только мы добавляем собственные **Handlers** в конфигурацию — настройки по умолчанию переопределяются и будут созданы
> только те бины, которые мы определили, стандартные **Handlers** созданы не будут.

## Чтение web.xml

```xml
<!-- Spring MVC -->
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
<servlet>
<servlet-name>mvc-dispatcher</servlet-name>
<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
<init-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:spring/spring-mvc.xml</param-value>
</init-param>
<load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
<servlet-name>mvc-dispatcher</servlet-name>
<url-pattern>/</url-pattern>
</servlet-mapping>
```

* Здесь инициализируем `DispatcherServlet` для того, чтобы читать наши запросы.
* В качестве параметра `DispatcherServlet` парсит файл `spring-mvc.xml` в котором указаны бины (это и будет его context)
  .
* Все запросы будут читаться по `/`.

## [How Spring MVC Framework works? How HTTP Request is processed?](https://javarevisited.blogspot.com/2017/06/how-spring-mvc-framework-works-web-flow.html)

Here is the flow of an HTTP-request in Java application created using the Spring MVC framework:

1) The client sends an HTTP-request to a specific URL.
2) `DispatcherServlet` of Spring MVC receives the request. It passes the request to a specific controller depending on
   the URL requested using `@Controller` and `@RequestMapping` annotations.
3) Spring MVC Controller then returns a logical view name and model to `DispatcherServlet`.
4) `DispatcherServlet` consults view resolvers until actual View is determined to render the output.
5) `DispatcherServlet` contacts the chosen view (like `Thymeleaf`, `Freemarker`, `JSP`) with model data and it renders
   the output depending on the model data.
6) The rendered output is returned to the client as a response.

```xml

<web-app>

    <!-- The front controller of this Spring Web application, responsible 
    for handling all application requests -->
    <servlet>
        <servlet-name>Spring MVC Dispatcher Servlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/config/web-application-config.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>example</servlet-name>
        <url-pattern>*</url-pattern>
    </servlet-mapping>

</web-app>
```

### Difference between Controller and RESTController

The flow of the RESTful Web Service request is also not very different from this. It follows the same path but in the
case of REST, the Controller methods are annotated with `@ResponseBody` which means it doesn't return a logical view
name to `DispatcherServlet`, instead it write the output directly to the HTTP response body.

## More about `DispatcherServlet`

* [How does Spring MVC Process HTTP Request [Flow]? DispatcherServlet Example Tutorial](https://www.java67.com/2019/08/how-dispatcherservlet-process-request-in-spring-mvc-application.html?fbclid=IwAR3dJogejj__xC0tbZEkeSw1o6o983fO5YFMQRv-ab-ZHgqgHG-B21lkmbk)

`DispatcherServlet` plays a significant role in Spring MVC. It acts as a front controller, and all incoming request
passes through it, of course, you can configure this in URL pattern of `DispatcherServlet`
declaration in `web.xml`, but this is the case for many Spring based web application.

## CORS

<u>Один из вариантов</u> - это [добавление аннотации](https://www.baeldung.com/spring-cors) `@CrossOrigin` над
метод-ом/ами или над классом. По умолчанию All origins are allowed.

***

<u>Второй вариант</u> - написание своего фильтра.

RESTful web-сервис будет включать CORS заголовки контроля доступа в свой ответ. Нужно написать фильтр, который добавляет
заголовки к ответу.

```java

@Component
public class BrowserCORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
```

Иначе возможна ошибка типа:
> Access to fetch at 'http://localhost:8080/' from origin 'null' has been blocked by CORS policy:
> No 'Access-Control-Allow-Origin' header is present on the requested resource.
> If an opaque response serves your needs, set the request's mode to 'no-cors' to fetch the resource with CORS disabled.

## DispatcherServlet by Java config

Сначало создаем веб-конфигурацию, где бин `ViewResolver` будет перенаправлять нас на страницы `.jsp`:

```java

@Configuration
@ComponentScan("sk.springdemo.mvc.controller")
public class AppConfig implements WebMvcConfigurer {
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
```

Далее указываем чтобы через `DispatcherServlet` проходили все запросы по адресу `/`. Первый простой вариант:

```java
// достаточно создать отдельный класс без аннотаций - прочитается автоматически
public class SpringWebAppInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
```

Второй вариант с конфигурированием:

```java
public class SpringWebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext webAppCxt = new AnnotationConfigWebApplicationContext();
        webAppCxt.register(AppConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webAppCxt);

        // register dispatcher servlet with servlet context
        ServletRegistration.Dynamic customDispatcherServlet = servletContext.addServlet("springDispatcherServlet", dispatcherServlet);
        customDispatcherServlet.setLoadOnStartup(1);
        customDispatcherServlet.addMapping("/");
    }
}
```

## RestTemplate

Для совершения HTTP-запросов из REST-клиента, можно использовать вспомогательный класс от __Spring__'a - `RestTemplate`.

## ResponseEntity<T>

`ResponseEntity` - это обертка http-response. При создании ресурса принято отдавать URL на имя этого ресурса. Поэтому
вместо сущность нужно возвращать `ResponseEntity` параметризированной сущностью.

```java
@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE) // использует JSON
public ResponseEntity<User> createWithLocation(@RequestBody User user){
        User created=super.create(user);
        URI uriOfNewResource=ServletUriComponentsBuilder.fromCurrentContextPath()
        .path(REST_URL+"/{id}")
        .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
        }
```

## MySpringMvcDispatcherSerlvetIntitializer

Класс создается вместо `web.xml`.

## Model object

Модель, это контейнер для любых данных. Для использования модели, в методе контроллера нужно указать
аргумент `Model model`. Добавить данные можно так:

```java
@RequestMapping("/showDetails")
public String showEmpDetails(HttpServletRequest request,Model model){

        String empName="Mr. "+request.getParameter("employeeName");
        model.addAttribute("nameAttribute",empName);

        return"show-emp-details-view";
        }
```

## Annotations

### @EnableWebMvc

`@EnableWebMvc` — добавление этой аннотации к классу импортирует конфигурацию Spring MVC из `WebMvcConfigurationSupport`
.

### @Controller

This annotation is used to make a class as a web controller, which can handle client requests and send a response back
to the client. This is a class-level annotation, which is put on top of your controller class. Similar to `@Service`
and `@Repository` it is also a stereotype annotation.

`@Controller` должен возвращать представление **view** - имя страницы для отрисовки.

***

### @RestController

> Now, you don't need to use `@Controller` and `@RestponseBody` annotation, instead you can use `@RestController` to provide the same functionality.

> `@RestController` simply returns the object and object data is directly written into HTTP response as JSON or XML.

This can also be done with traditional `@Controller` and use `@ResponseBody` annotation but since this is the default
behavior of RESTful Web services, Spring introduced `@RestController` which combined the behavior of `@Controller`
and `@ResponseBody` together.

In short, the following two code snippet are equal in Spring MVC:

```java

@Controller
@ResponseBody
public class MVCController {
    // your logic
}

@RestController
public class RestFulController {
    // your logic
}
```

***

### @ModelAttribute()

В зависимости от места использования (над методом или перед аргументом метода) данная аннотация выполняет разные
функции:

#### Над методом

```java
@ModelAttribute("headerMessage")
public String populateHeaderMessage(){
        return"Welcome to our website";
        }
```

Означает, что в каждой модели текущего контроллера будет добавлена пара ключ/значение - headerMessage/Welcome to our
website.

#### Перед аргументом метода

Аннотация `@ModelAttribute` поставленная перед аргументом метода, заполняет объект указанной модели в аргументе.

Что именно делает `@ModelAttribute`:

* Создание нового объекта
* Добавление значений из HTML-формы
* Добавление созданого объекта в модель

Равнозначный результат:

Пример без использования `@ModelAttribute`:

```java
@PostMapping()
public String create(@RequestParam("name") String name,
@RequestParam("surname") String surname,
@RequestParam("email") String email,Model model){
        Person person=new Person();
        person.setName(name);
        person.setSurname(surname);
        person.setEmail(email);

        // save person to DB

        model.addAttribute("person",person);

        return"successPage";
        }
```

Пример с использованием `@ModelAttribute`:

```java
@PostMapping
public String create(@ModelAttribute("person") Person person){
        // save person to DB

        return"successPage";
        }
```

Еще пример. Посколько в `model` не ложим никакого дополнительного аттрибута, тогда здесь можно
использовать `@ModelAttribute` у аргумента. Равнозначные методы:

```java
@GetMapping("/new")
public String newPerson(Model model){
        model.addAttribute("person",new Person());
        return"people/new";
        }
```

```java
@GetMapping("/new")
public String newPerson(@ModelAttribute("person") Person person){
        return"people/new";
        }
```

### @RequestParam

При работе с формами, аннотация `@RequestParam` позволяет нам связывать поле формы с параметром метода из _Controller_-а
или ссылки.

```text
http://localhost:8080/exchange?id=3
```

```java
@GetMapping("/exchange")
public String exchange(@RequestParam String id){
        // some code
        }
```

`@RequestParam` можно указывать и без имени - `@RequestParam("id")`. В таком случае будет взято имя параметра метода.

### @PathVariable

Аннотация `@PathVariable` используется для получения значения переменной из адреса запроса.

Имя переменной __Spring__ определяет по названию параметра на основе байт-кода. Т.е. повторение имен в аннотациях не
требуется.

```java
// Необязательно:
@GetMapping("/{id}")
public User get(@PathVariable("id") int id){
        // some code
        }

// Можно писать так - тоже самое:
@GetMapping("/{id}")
public User get(@PathVariable int id){
        // some code 
        }
```

### @ResponseBody

> Ответ от нашего приложения будет приходить в теле запроса.

> `@ResponseBody` The function of annotation is to convert the return value of the controller method to the specified format
> through the appropriate converter, Write to Response Object's body District, Usually used to return **JSON** Data or **XML** data .
>
> Be careful: View processor will not walk after using this annotation, Instead, the data is written directly into the input stream,
> His effect is equivalent to passing Response Object to output data in the specified format.

Сообщает Spring, что вы хотите записать свой Java-объект HealthStatus непосредственно в HttpResponse (например, в виде
XML или JSON).

```java

@Controller
public class HealthController {

    @GetMapping("/health")
    @ResponseBody //
    public HealthStatus health() {
        return new HealthStatus(); // возвращаете простой Java-объект
    }
}
```

### @ExceptionHandler

Аннотацией `@ExceptionHandler` отмечается метод, ответственный за обработку исключений.

```java
@ExceptionHandler
public ResponseEntity<EmployeeIncorrectData> handleException(NoSuchEmployeeException exception){
        EmployeeIncorrectData data=new EmployeeIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data,HttpStatus.NOT_FOUND);
        }
```

ResponseEntity - это обертка http-response. В параметризированный тип нужно добавить объект, который добавляется в
http-response body. Сработает тогда, когда будет выбрашенно указанное исключение.

### @ControllerAdvice

Аннотацией `@ControllerAdvice` отмечается класс, предоставляющий функциональность _Global Exception Handler_-а.

Любой класс с аннотацией `@ControllerAdvice` является глобальным обработчиком исключений, который очень гибко
настраивается.

__DOCUMENTATION:__ _By default, the methods in an @ControllerAdvice apply globally to all controllers._

### @PostMapping

Аннотация `@PostMapping` связывает HTTP-запрос, использующий HTTP-метод _POST_ с методом контроллера.

### @RequestBody

Аннотация `@RequestBody` связывает тело HTTP-метода с параметром метода _Controller_-а. Означает, что ответ от нашего
приложения будет приходить в теле запроса.

Технически `@RestController` - это просто комбинация `@Controller` и `@ResponseBody`.

Чтобы использовать информацию посылаемую в методе POST (тело метода POST) - используй аннотацию `@RequestBody`.

```java
@PostMapping("/employees")
public Employee addNewEmployee(@RequestBody Employee employee){
        employeeService.saveEmployee(employee);
        return employee;
        }
```

### @PutMapping

Аннотация `@PutMapping` связывает HTTP-запрос, использующий HTTP-метод _PUT_ с методом контроллера.

```java
@PutMapping("/employees")
public Employee updateEmployee(@RequestBody Employee employee){
        employeeService.saveEmployee(employee);
        return employee;
        }
```

### @DeleteMapping

Аннотация `@DeleteMapping` связывает HTTP-запрос, использующий HTTP-метод _DELETE_ с методом контроллера.

```java
@DeleteMapping("employees/{id}")
public String deleteEmployee(@PathVariable int id){
        employeeService.deleteEmployee(id);
        return"Employee with id = "+id+" was deleted";
        }
```

### @PatchMapping

Используется, когда модифицируется не всё entity, а его часть.

### @ResponseStatus(HttpStatus.NO_CONTENT)

Рекомендуется ставить над void методами.

### @Valid

Нужно указывать в контроллере - `@Valid` говорит, что аттрибут будет подтвергаться валидации.

#### BindingResult

Чтобы определить прошла ли валидация нормально, нужно в методе использовать второй параметр - `BindingResult`. Результат
валидации аттрибута `employee` будет помещен в этот параметр. Параметр `BindingResult` должен идти сразу после параметра
аттрибута модели!

```java
@RequestMapping("/showDetails")
public String showEmpDetails(@Valid @ModelAttribute("employee") Employee emp,BindingResult bindingResult){
        if(bindingResult.hasErrors()){ // успешна ли валидация
        return"ask-emp-details-view"; // если нет - возврат обратно
        }else{
        return"show-emp-details-view"; // если да - идем дальше
        }
        }
```

### @NotNull

В `@NotNull` идёт пустое преобразование из `null` значения в пустую строку - `""` длиной в `0`. Чтобы `@NotNull` работал
как надо, нужно дописать код, который будет конвертировать пустой `String` в `null`, и лишь потом пропускать это
свойство через валидацию. Возможно лучше использовать аннотацию `@NotEmpty`.

### @NotEmpty

Требует, чтобы поле было и _не null_ и _не пустым_ полем.

### @NotBlank

Поле не должно быть пустыми не должно быть заполнено только пробелами. Делает всё тоже самое что и аннотация `@NotEmpty`
+ проверяет чтобы поле не было из пробелов.

### @Min и @Max

Используются для валидации числовых значений. Значения включительны.

```java
@Min(value = 500, message = "must be greater than 499")
@Max(value = 1000, message = "must be less than 1001")
private int salary;
```

### @Pattern

`@Pattern` – значение поля должно соответствовать определённому _Регулярному Выражению_.

### @RestController

`@RestController` – это _Controller_, который управляет REST-запросами и -ответами.

## [Принципы REST, REST-контроллеры](https://github.com/JavaWebinar/topjava/blob/doc/doc/lesson07.md#-5-принципы-rest-rest-контроллеры)

REST - архитектурный стиль проектирования распределенных систем (типа клиент-сервер).

Чаще всего в REST сервер и клиент общаются посредством обмена JSON-объектами через HTTP-методы
GET/POST/PUT/DELETE/PATCH. Особенностью REST является отсутствие состояния (контекста) взаимодействий клиента и сервера.

> В `@RestController` к аннотации `@Controller` добавлена `@ResponseBody`.
> Т.е. ответ от нашего приложения будет не имя **View**, а данные в теле ответа.

В @RequestMapping, кроме пути для методов контроллера (value) добавляем параметр produces =
MediaType.APPLICATION_JSON_VALUE. Это означает, что в заголовки ответа будет добавлен тип ContentType="application/json"
- в ответе от контроллера будет приходить JSON-объект.

```java

```

## Формы Spring MVC

Чтобы использовать _Spring MVC_ теги для форм веб-страницы, нужно добавить в начало станицы __namespace__:

```html
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
```

* `form:form` – основная форма, содержащая в себе другие формы. Другими словами, форма-контейнер.

***

* `form:input` – форма, предназначенная для текста. Используется всего лишь одна строка.

***

* `form:select` – форма, предназначенная для реализации выпадающего списка.
* `form:option` – оборачивают опции.

Hardcoded вариант, с указанными значениями:

```html

<form:select path="department">
    <form:option value="Information Technology" label="IT"/>
    <form:option value="Human Resources" label="HR"/>
    <form:option value="Sales" label="Sales"/>
</form:select>
```

При выпадающем списке будет виден `label`, а значение этого `label` применится от `value`.

Вариант для использования коллекций - мапы:

```html

<form:select path="department">
    <form:options items="${employee.departments}"/>
</form:select>
```

При выпадающем списке будет видно значение мапы, а отображаться будет ключ.

***

* `form:radiobutton` – форма, предназначенная для реализации `radio button` (переключатель).

__Hardcoded значения при модели:__

```java
private String carBrand;
```

```html
BMW
<form:radiobutton path="carBrand" value="BMW"/>
Audi
<form:radiobutton path="carBrand" value="Audi"/>
MB
<form:radiobutton path="carBrand" value="MB"/>
```

__Мапа при модели:__

```java
private String carBrand;
private Map<String, String> carBrands;
```

```html

<form:radiobuttons path="carBrand" items="${employee.carBrands}"/>
```

***

* `form:checkbox` – форма, предназначенная дляреализации `check box` (флажок).

__Hardcoded variant:__

```java
private String[]languages; // in your model
```

```html
EN
<form:checkbox path="languages" value="English"/>
DE
<form:checkbox path="languages" value="Deutch"/>
FR
<form:checkbox path="languages" value="Franch"/>
```

To iterate each element you need to use `jstl-tag`:

```html
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="lang" items="${employee.languages}">
    <li>${lang}</li>
</c:forEach>
```

__Map variant:__

```java
private String[]languages; // in your model
private Map<String, String> languageList;
```

```html

<form:checkboxes path="languages" items="${employee.languageList}"/>
```

To iterate each element you need to use `jstl-tag`:

```html
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="lang" items="${employee.languages}">
    <li>${lang}</li>
</c:forEach>
```

***

* `form:errors` - Используется для отображения указаного сообщения об ошибке валидации.

```html

<form:errors path="name"/>
```

где свойство `name` в модели помечено аннотацией валидации, например:

```java
@Size(min = 2, message = "Name must be min 2 symbols")
private String name;
```

## Как передать PATCH, DELETE, PUT запросы

HTML понимает только два типа запрос - `GET` и `POST`. С помощью __Spring__ можно обойти эти ограничения и использовать
другие типы запросов.

`PATCH`, `DELETE`, `PUT` запросы передаются с помощью `POST` запроса, но в скрытом поле `_method` указывается желаемый
HTTP метод.
__Spring__ прочитает значение скрытого поля _method, увидит у него значение, например, `PATCH`, и не смотря на то, что
форма посылается с помощью `POST` запроса, __Spring__ будет считать, что форма посылается с помощью `PATCH` запроса.

```html

<form th:method="post" action="/person/1">
    <input type="hidden" name="_method" value="patch"/>
    <label for="name">Enter name: </label>
    <input type="text" id="name" name="name" value="Tom"/>
    <br/>
    <input type="submit" value="Update!"/>
</form>
```

В случае использования `Thymeleaf`, дописывать строку `_method` не нужно, `Thymeleaf` это сделает автоматически.

На стороне __Spring__ приложения, чтения поля `_method` реализуется с помощью фильтра - объект, который перехватывает
все входящие HTTP-запросы.

## Ошибки

### Request method 'POST' not supported

При использовании типов запросов, которые не поддерживает **HTML5**, например `PATCH` (код выше), ошибка появляется,
т.к. нет метода в контроллере по этому адресу с POST-запросом.

```java
@PatchMapping("/{id}")
public String update(@ModelAttribute("person") Person person,@PathVariable("id") int id){
        personDAO.update(id,person);
        return"redirect:/people";
        }
```

Чтобы ошибка ушла, нужно на стороне __Spring__ читать значение поля `_method` и направлять запрос на нужный метод
контроллера, т.е. `POST` запрос нужно направить на `PATCH` метод выше - для этого нужно создать фильтр.

У **Spring** уже есть фильтр, который читает значения с поля `_method` - `HiddenHttpMethodFilter`. Реализация
в `MySpringMvcDispatcherSerlvetIntitializer` будет такой:

```java
@Override
public void onStartup(ServletContext aServletContext)throws ServletException{
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
        }

private void registerHiddenFieldFilter(ServletContext aContext){
        aContext.addFilter("hiddenHttpMethodFilter",
        new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null,true,"/*");
        }
```

## Тестирование

`@WebAppConfiguration` - требуется для указания, что конфигурация будет использоваться для тестов.

### Тестирование веб на примере TopJava

Для тестирования web создадим вспомогательный класс `AbstractControllerTest`, от которого будут наследоваться все тесты
контроллеров. Его особенностью будет наличие `MockMvc` - эмуляции Spring MVC для тестирования web-компонентов.
Инициализируем ее в методе, отмеченном `@PostConstruct`:

```java

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles(resolver = ActiveDbProfileResolver.class, profiles = Profiles.REPOSITORY_IMPLEMENTATION)
public abstract class AbstractControllerTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
    }

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }
}
```

Для того, чтобы в тестах контроллеров не популировать базу перед каждым тестом, пометим этот базовый тестовый класс
аннотацией `@Transactional`. Теперь каждый тестовый метод будет выполняться в транзакции, которая будет откатываться
после окончания метода и возвращать базу данных в исходное состояние. Однако теперь в работе тестов могут возникнуть
нюансы, связанные с пропагацией транзакций: все транзакции репозиториев станут вложенными во внешнюю транзакцию теста.

Создадим тестовый класс для контроллера юзеров, он должен наследоваться от `AbstractControllerTest`. В `MockMvc`
используется паттерн проектирования **Builder**.

```java
public class RootControllerTest extends AbstractControllerTest {

    @Test
    public void getUsers() throws Exception {
        perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(UserTestData.user.getName()))
                        )
                )));
    }
}
```

В параметры метода `andExpect()` передается реализация `ResultMatcher`, в которой мы определяем как должен быть
обработан ответ контроллера.

## Internationalization i18n, Localization

* [TopJava - добавление смены локали](https://github.com/JavaWebinar/topjava/blob/doc/doc/lesson11.md#-2-hw10-optional-change-locale)
* [Spring MVC internationalization example](https://mkyong.com/spring-mvc/spring-mvc-internationalization-example/)

## Questions

### Как мы будем работать с нашими view?

Нужно создать бин по обработке _view_ в `applicationContext.xml`.

```xml

<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/view/"/>
    <property name="suffix" value=".jsp"/>
</bean>
```

Чтобы обращаться к своим _view_ по имени, необходимо прописать преффикс и суффикс. Конфигурация в примере выше,
автоматически будет искать в установленном месте и с указанным расширением.

***

### Почему @RequestParam не работает в запросах PUT и DELETE?

По спецификации Servlet API параметры в теле для методов PUT, DELETE, TRACE не обрабатываются (только в url). Можно:

* использовать POST
* передавать параметры в url
* использовать HttpPutFormContentFilter фильтр
* настроить Tomcat в обход спецификации. См. Handle request parameters for an HTTP PUT method
