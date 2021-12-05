# Servlets
* [Как работает сервлет](https://metanit.com/java/javaee/4.8.php)

> Все методы в качестве параметра принимают два объекта: `HttpServletRequest` - хранит информацию о запросе и `HttpServletResponse` - управляет ответом на запрос.



## javax.servlet-api
Для работы с сервлетами нужна зависимость `javax.servlet-api` ([актуальная версия на репозитории Maven](https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api)):
```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
    <scope>provided</scope>
</dependency>
```



## web.xml
Файл `web.xml` хранит информацию о конфигурации приложения.
Он не является обязательной частью приложения, его можно заменить аннотациями.
Данный файл должен располагаться в папке `WEB-INF` приложения.
В зависимости от способа разработки, может потребоваться создать `web.xml`. 
Его базовый шаблон:
```xml
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
		 http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">


</web-app>
```
Для запуска приложения нужно создать Tomcat-конфигурацию (выбрать версию сервера, указать артефакт exploded...).



## JSP
* [Java Server Pages JSP](http://java-online.ru/jsp.xhtml)
* [Java объекты, доступные в JSP](https://stackoverflow.com/questions/1890438/how-to-get-parameters-from-the-url-with-jsp#1890462)
* [Guide to JavaServer Pages (JSP)](https://www.baeldung.com/jsp)

**Java Server Pages (JSP)** - это одна из технологий J2EE, которая представляет собой расширение технологии сервлетов для упрощения работы с Web-содержимым.

Разработчики Java Server Pages могут использовать компоненты JavaBeans.

> Прямая замена JSP [Thymeleaf](http://www.thymeleaf.org) (в Spring-Boot по умолчанию).
> JSP не умирает, потому что просто и дешево. Кроме того включается в большинство веб-контейнеров (в Tomcat его реализация Jasper).

***

При отправке данных, например через форму или `button` с типом `submit`, можно добавить параметы (ключ/значения) в GET-запрос.
Для этого можно использовать `input` со скрытым типом `type="hidden"`.
```html
<input type="hidden" name="action" value="filter">
```
где:
* `name` - ключ;
* `value` - значение.

```html
?action=filter
```

### JSP bean
> `jsp:useBean` нужен IDEA для автодополнений - она понимает тип переменной, которая уже доступна в JSP (например через `setAttribute`).
> И еще эта переменная становится доступной в java вставках. Для вывода в JSP это тэг не обязателен.
> Если тип переменной JSP не совпадает с тем, что в `jsp:useBean`, будет ошибка.


При работе в html файле и инициализации javaBean, можно обращаться к полю java-объекта напрямую, даже если у поля выставлен модификатор доступа private и нет геттера - пример из topjava:
```java
// модель
public class MealTo {
   private final LocalDateTime dateTime;
   private final String description;
   private final int calories;
   private final boolean excess;
}

// сервлет
request.setAttribute("mealsTo", 
        MealsUtil.filteredByStreams(MealsUtil.meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
```
```html
<c:forEach var="meal" items="${mealsTo}">
   <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.MealTo"/>
   <tr class="${meal.excess ? 'exceeded' : 'notExceeded'}">
       <td>${meal.dateTime}</td>
       <td>${meal.description}</td>
       <td>${meal.calories}</td>
   </tr>
</c:forEach>
```
Ту же интеграцию можно получить используя `requestScope`:
```html
<c:forEach items="${requestScope.meals}" var="meal">
   <tr class="${meal.excess ? 'excess' : 'normal'}">
       <td>${fn:formatDateTime(meal.dateTime)}</td>
       <td>${meal.description}</td>
       <td>${meal.calories}</td>
       <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
       <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
   </tr>
</c:forEach>
```

### JSTL
* [JSTL: Шаблоны для разработки веб-приложений в java](https://javatutor.net/articles/jstl-patterns-for-developing-web-application-1)
* [Baeldung - A Guide to the JSTL Library](https://www.baeldung.com/jstl)

Для работы с jstl нужно подключить зависимость:
```xml
<dependency>
   <groupId>javax.servlet</groupId>
   <artifactId>jstl</artifactId>
   <version>1.2</version>
</dependency>
```
Теперь теги добавленные вверху jsp-страницы будут активны для работы, например:
```thymeleafexpressions
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

***

Начиная с `Servlet API 2.3` пул сервлетов не создается, 
[создается только один инстанс сервлетов](https://stackoverflow.com/questions/6298309/how-many-instances-of-servlet-are-created-by-container-after-loading-it-singlet).

***

[JSP позволяет использовать ряд объектов](https://stackoverflow.com/a/1890462). 
Например, параметр запроса `param.action`, который не кладется в атрибуты, т.е. достается значение ключа `action` указанное в строке запроса.

#### Working with LocalDateTime
Для работы с датой и временем нужно импортировать тег:
```thymeleafexpressions
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
```
Пример форматирования объекта `LocalDateTime`, - на входе один паттерн, на выход - другой:
```html
<td>
   <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
   <fmt:formatDate value="${parsedDateTime}" pattern="dd.MM.yyyy HH:mm"/>
</td>
```

***

**NOTES**
* `pageScope` - Контекст страницы (т.е. переменные объявленные на этой странице и доступные только для этой страницы).
* `requestScope` - Доступ к таким переменным имеют все страницы, сервлеты обслуживающие один, текущий, вот этот самый, запрос пользователя.
* `sessionScope` - Доступ к переменным сохраняется на протяжении всего сеанса пользователя (пока не закроет браузер или не истечет предельное время бездействия).
* `applicationScope` - Доступ к переменным сохраняется изо всех страниц размещенных внутри веб-приложения (самый глобальный контекст).
* `param` - В этом контексте находятся все переменные, полученные страницей от пользователя либо как параметры адресной строки, либо как поля html-формы.
* `paramValues` - Список значений тех переменных, которые были переданы в страницу пользователем, правда, формат отличен от предыдущего случая. 
   Если там param фактически имел тип `HashMap<String, String>`, то здесь `HashMap<String, String[]>`.
* `header` - В этом объекте хранится информация об http-заголовках которые были переданы от браузера клиента вашему веб-серверу.
* `headerValues` - Список значений http-заголовков.
* `initParam` - Конфигурационные параметры, указанные для вашей страницы, сервлета в файле `web.xml`.
* `cookie` - Список переменных помещенных внутрь cookie.
* `pageContext` - Ссылка на объект pageContext (см. описание служебных объектов автоматически создаваемых внутри jsp-страницы).



## Работа со Spring на примере TopJava
Для работы с web с помощью Spring подключим к проекту следующие зависимости:
```xml
<!--TomCat - web-контейнер. Позволяет работать с jsp, сервлетами -->
<dependency>
   <groupId>org.apache.tomcat</groupId>
   <artifactId>tomcat-servlet-api</artifactId>
   <version>${tomcat.version}</version>
   <scope>provided</scope>
</dependency>

<!--java standart tag library - не предоставляется TomCat, нужно добавлять вручную -->
<dependency>
   <groupId>javax.servlet</groupId>
   <artifactId>jstl</artifactId>
   <version>1.2</version>
</dependency>
        
<!--Spring библиотека для работы с web -->
<dependency>
   <groupId>org.springframework</groupId>
   <artifactId>spring-web</artifactId>
   <version>${spring.version}</version>
</dependency>
```
При старте web-приложения в контейнере сервлетов требуется инициализировать контекст спринга.
Запустить Spring можно с помощью `ContextLoaderListener`, который будет отслеживать работу веб-приложение и при инициализации сервлета 
поднимать Spring context в методе `contextInitialized` и отключать контекст спринга при остановке приложения в методе `contextDestroyed`. 
Для этого нужно определить этот `ContextListener` в `web.xml`:
```xml
<listener>
  <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```
Чтобы **Listener** смог поднять контекст спринга — ему нужно указать в `web.xml` путь к конфигурационным файлам и задать необходимые профили:
```xml
<context-param>
   <param-name>spring.profiles.default</param-name>
   <param-value>postgres,datajpa</param-value>
</context-param>

<context-param>
   <param-name>contextConfigLocation</param-name>
   <param-value>
      classpath:spring/spring-app.xml
      classpath:spring/spring-db.xml
   </param-value>
</context-param>
```
Для каждого сервлета, при инициализации, после создания запускается метод `init(ServletConfig config)`, где мы можем получить текущий контекст Spring. 
Для web приложений определяется свой собственный `WebApplicationContext`, который может работать с сервлетами. 
В `UserServlet` мы можем получить контекст с помощью метода `getRequiredWebApplicationContext(getServletContext())` из `WebApplicationContextUtils`. 
Из полученного контекста мы можем получать бины Spring, например, объект `UserService`, и работать через него с пользователями.
