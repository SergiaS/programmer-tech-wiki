# SpringWeb
Spring MVC – это фреймворк для создания web приложений на Java, в основе которого лежит шаблон проектирования MVC.

* <b><u>Model</u></b> – контейнер для хранения данных.
* <b><u>View</u></b> – web страница, которую можно создать с помощью `html`, `jsp`, `Thymeleaf` и т.д.
  Часто при отображении _View_ использует данные из _Model_.
* <b><u>Front Controller</u></b> также известен под именем `DispatcherServlet`. Он является частью __Spring__. 
  Остальные компоненты - _Model_ и _View_ - нужно создать самому.

## Questions
### Как мы будем работать с нашими view?
Нужно создать бин по обработке _view_ в `applicationContext.xml`.
```xml
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/view/" />
    <property name="suffix" value=".jsp" />
</bean>
```
Чтобы обращаться к своим _view_ по имени, необходимо прописать преффикс и суффикс. 
Конфигурация в примере выше, автоматически будет искать в установленном месте и с указанным расширением.


## RestTemplate
Для совершения HTTP-запросов из REST-клиента, можно использовать вспомогательный класс от __Spring__'a - `RestTemplate`.


## ResponseEntity<T>
`ResponseEntity` - это обертка http-response.
При создании ресурса принято отдавать URL на имя этого ресурса. 
Поэтому вместо сущность нужно возвращать `ResponseEntity` параметризированной сущностью.
```java
@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE) // использует JSON
public ResponseEntity<User> createWithLocation(@RequestBody User user) {
    User created = super.create(user);
    URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(REST_URL + "/{id}")
            .buildAndExpand(created.getId()).toUri();
    return ResponseEntity.created(uriOfNewResource).body(created);
}
```


## MySpringMvcDispatcherSerlvetIntitializer
Класс создается вместо `web.xml`.


## Model object
Модель, это контейнер для любых данных.
Для использования модели, в методе контроллера нужно указать аргумент `Model model`.
Добавить данные можно так:
```java
@RequestMapping("/showDetails")
public String showEmpDetails(HttpServletRequest request, Model model){

    String empName = "Mr. " + request.getParameter("employeeName");
    model.addAttribute("nameAttribute", empName);

    return "show-emp-details-view";
}
```

## Annotations


### @ModelAttribute()
В зависимости от места использования (над методом или перед аргументом метода) данная аннотация выполняет разные функции:

#### Над методом
```java
@ModelAttribute("headerMessage")
public String populateHeaderMessage() {
    return "Welcome to our website";
}
```
Означает, что в каждой модели текущего контроллера будет добавлена пара ключ/значение - headerMessage/Welcome to our website.

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
                     @RequestParam("email") String email, Model model) {
    Person person = new Person();
    person.setName(name);
    person.setSurname(surname);
    person.setEmail(email);
    
    // save person to DB
    
    model.addAttribute("person", person);
    
    return "successPage";
}
```

Пример с использованием `@ModelAttribute`:
```java
@PostMapping
public String create(@ModelAttribute("person") Person person) {
    // save person to DB
    
    return "successPage";
}
```

Еще пример. Посколько в `model` не ложим никакого дополнительного аттрибута, тогда здесь можно использовать `@ModelAttribute` у аргумента. 
Равнозначные методы: 

```java
@GetMapping("/new")
public String newPerson(Model model) {
    model.addAttribute("person", new Person());
    return "people/new";
}
```
```java
@GetMapping("/new")
public String newPerson(@ModelAttribute("person") Person person) {
    return "people/new";
}
```


### @RequestParam
При работе с формами, аннотация `@RequestParam` позволяет нам связывать поле формы с параметром метода из _Controller_-а или ссылки.


### @PathVariable
Аннотация `@PathVariable` используется для получения значения переменной из адреса запроса.

Имя переменной __Spring__ определяет по названию параметра на основе байт-кода. Т.е. повторение имен в аннотациях не требуется.
```java
// Необязательно:
@GetMapping("/{id}")
public User get(@PathVariable("id") int id) { 
    // some code
}

// Можно писать так - тоже самое:
@GetMapping("/{id}")
public User get(@PathVariable int id) {
    // some code 
}
```


### @ExceptionHandler
Аннотацией `@ExceptionHandler` отмечается метод, ответственный за обработку исключений.
```java
@ExceptionHandler
public ResponseEntity<EmployeeIncorrectData> handleException(NoSuchEmployeeException exception){
    EmployeeIncorrectData data = new EmployeeIncorrectData();
    data.setInfo(exception.getMessage());
    return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
}
```
ResponseEntity - это обертка http-response. 
В параметризированный тип нужно добавить объект, который добавляется в http-response body. 
Сработает тогда, когда будет выбрашенно указанное исключение.


### @ControllerAdvice
Аннотацией `@ControllerAdvice` отмечается класс, предоставляющий функциональность _Global Exception Handler_-а.

__DOCUMENTATION:__ _By default, the methods in an @ControllerAdvice apply globally to all controllers._


### @PostMapping
Аннотация `@PostMapping` связывает HTTP-запрос, использующий HTTP-метод _POST_ с методом контроллера.


### @RequestBody
Аннотация `@RequestBody` связывает тело HTTP-метода с параметром метода _Controller_-а.
Означает, что ответ от нашего приложения будет приходить в теле запроса.

Чтобы использовать информацию посылаемую в методе POST (тело метода POST) - используй аннотацию `@RequestBody`.

```java
@PostMapping("/employees")
public Employee addNewEmployee(@RequestBody Employee employee) {
    employeeService.saveEmployee(employee);
    return employee;
}
```


### @PutMapping
Аннотация `@PutMapping` связывает HTTP-запрос, использующий HTTP-метод _PUT_ с методом контроллера.
```java
@PutMapping("/employees")
public Employee updateEmployee(@RequestBody Employee employee) {
    employeeService.saveEmployee(employee);
    return employee;
}
```


### @DeleteMapping
Аннотация `@DeleteMapping` связывает HTTP-запрос, использующий HTTP-метод _DELETE_ с методом контроллера.
```java
@DeleteMapping("employees/{id}")
public String deleteEmployee(@PathVariable int id) {
    employeeService.deleteEmployee(id);
    return "Employee with id = " + id + " was deleted";
}
```


### @PatchMapping
Используется, когда модифицируется не всё entity, а его часть.


### @ResponseStatus(HttpStatus.NO_CONTENT)
Рекомендуется ставить над void методами.


### @Valid
Нужно указывать в контроллере - `@Valid` говорит, что аттрибут будет подтвергаться валидации.

#### BindingResult
Чтобы определить прошла ли валидация нормально, нужно в методе использовать второй параметр - `BindingResult`. 
Результат валидации аттрибута `employee` будет помещен в этот параметр.
Параметр `BindingResult` должен идти сразу после параметра аттрибута модели!
```java
@RequestMapping("/showDetails")
public String showEmpDetails(@Valid @ModelAttribute("employee") Employee emp, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) { // успешна ли валидация
        return "ask-emp-details-view"; // если нет - возврат обратно
    } else {
        return "show-emp-details-view"; // если да - идем дальше
    }
}
```


### @NotNull
В `@NotNull` идёт пустое преобразование из `null` значения в пустую строку - `""` длиной в `0`. 
Чтобы `@NotNull` работал как надо, нужно дописать код, который будет конвертировать пустой `String` в `null`, и лишь потом пропускать это свойство через валидацию. 
Возможно лучше использовать аннотацию `@NotEmpty`.


### @NotEmpty
Требует, чтобы поле было и _не null_ и _не пустым_ полем.


### @NotBlank
Поле не должно быть пустыми не должно быть заполнено только пробелами. 
Делает всё тоже самое что и аннотация `@NotEmpty` + проверяет чтобы поле не было из пробелов.


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



## Формы Spring MVC
Чтобы использовать _Spring MVC_ теги для форм веб-страницы, нужно добавить в начало станицы __namespace__:
```html
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
```

* `form:form` – основная форма, содержащая в себе другие формы. Другими словами, форма-контейнер.

<hr>

* `form:input` – форма, предназначенная для текста. Используется всего лишь одна строка.

<hr>

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

<hr>

* `form:radiobutton` – форма, предназначенная для реализации `radio button` (переключатель). 

__Hardcoded значения при модели:__
```java
private String carBrand;
```
```html
BMW <form:radiobutton path="carBrand" value="BMW"/>
Audi <form:radiobutton path="carBrand" value="Audi"/>
MB <form:radiobutton path="carBrand" value="MB"/>
```
__Мапа при модели:__
```java
private String carBrand;
private Map<String, String> carBrands;
```
```html
<form:radiobuttons path="carBrand" items="${employee.carBrands}"/>
```

<hr>

* `form:checkbox` – форма, предназначенная дляреализации `check box` (флажок).

__Hardcoded variant:__
```java
private String[] languages; // in your model
```
```html
EN <form:checkbox path="languages" value="English"/>
DE <form:checkbox path="languages" value="Deutch"/>
FR <form:checkbox path="languages" value="Franch"/>
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
private String[] languages; // in your model
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

<hr>

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
HTML понимает только два типа запрос - `GET` и `POST`. С помощью __Spring__ можно обойти эти ограничения и использовать другие типы запросов.

`PATCH`, `DELETE`, `PUT` запросы передаются с помощью `POST` запроса, но в скрытом поле `_method` указывается желаемый HTTP метод.
__Spring__ прочитает значение скрытого поля _method, увидит у него значение, например, `PATCH`, и не смотря на то, что форма посылается с помощью `POST` запроса, __Spring__ будет считать, что форма посылается с помощью `PATCH` запроса.
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

На стороне __Spring__ приложения, чтения поля `_method` реализуется с помощью фильтра - объект, который перехватывает все входящие HTTP-запросы.


## Ошибки
### Request method 'POST' not supported
При использовании типов запросов, которые не поддерживает html5, например `PATCH` (код выше), ошибка появляется, т.к. нет метода в контроллере по этому адресу с POST-запросом.

```java
@PatchMapping("/{id}")
public String update(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
    personDAO.update(id, person);
    return "redirect:/people";
}
```

Чтобы ошибка ушла, нужно на стороне __Spring__ читать значение поля `_method` и направлять запрос на нужный метод контроллера, т.е. POST запрос нужно направить на PATCH метод выше - для этого нужно создать фильтр.

У Spring уже есть фильтр, который читает значения с поля `_method` - `HiddenHttpMethodFilter`.
Реализация в `MySpringMvcDispatcherSerlvetIntitializer` будет такой:

```java
@Override
public void onStartup(ServletContext aServletContext) throws ServletException {
    super.onStartup(aServletContext);
    registerHiddenFieldFilter(aServletContext);
}

private void registerHiddenFieldFilter(ServletContext aContext) {
    aContext.addFilter("hiddenHttpMethodFilter",
            new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
}
```

