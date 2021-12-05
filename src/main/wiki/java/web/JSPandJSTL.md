# JSP and JSTL

Ко всему, что находится в папке webapp, можно получить доступ из браузера.


## Локализация
> Локаль приложения определяется на основе локали операционной системы и свойств браузера. 
> Чтобы проверить работу локализации можно из браузера в заголовке запроса указать Content-Language:"en-US". 
> Этот заголовок будет считан в сервлете, и приложение определит требуемую локаль.


### На примере TopJava

Для локализации стандартными средствами java можно использовать **Bundle** - это набор файлов properties, где определены ключ и значение. 
В зависимости от локали автоматически будет выбран нужный файл .properties, из которого по ключу страница будет получать текст на 
нужном языке и подставлять в места, где указаны соответствующие ключи.
```xml
<fmt:setBundle basename="messages.app"/>
```
Для локализации нашего приложения создадим в папке `resources/messages` два файла - `app.properties` и `app_ru.properties`, 
в которых мы и пропишем ключи и соответствующие им значения на русском и английском языках. При этом нужно иметь в виду, что **локализация 
в jpa/jstl не работает с UTF-8**, поэтому для отображения текста на кириллице приходится записывать его в виде 
набора кодов unicode (Intellij IDEA предоставляет нам удобный функционал для работы с этими кодами - в настройках _File encoding_ включить галочку **Transparent native-to ascii conversion**).

> Это было до Java 9. Теперь можно не парится и писать напрямую в UTF-8

На каждой странице будут дублироваться верхняя часть(header) и нижняя часть(footer), поэтому сделаем их в виде фрагментов, 
которые будут включаться в каждую страницу с помощью тега
```xml
<jsp:include page="fragments/bodyHeader.jsp"/>
```
Для того чтобы на странице JSP понимало, с каким объектом оно работает (а в IDEA работали автодополнения), 
мы можем явно указать с каким типом объекта мы будем работать. Для этого мы используем тег:
```xml
<jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
```
После этого на странице мы сможем работать с объектом в java-вставках `< % >` и с помощью expression language `${ }`.
Без этого тэга приложение работать тоже будет, не будет IDEA интергации. Не забывайте про getter-ы, JSP обращается к объектам через них!

