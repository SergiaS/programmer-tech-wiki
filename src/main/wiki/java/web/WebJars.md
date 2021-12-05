# [WebJars](https://www.webjars.org/)
**WebJars** — библиотеки на стороне клиента (`JavaScript` библиотека и/или `CSS` модуль), упакованные в `JAR`.

* `jQuery` - самая распространенная утилитная JavaScript-библиотека;
* `Bootstrap` - фреймворк CSS-стилей;
* `Datatables` - плагин для отрисовки таблиц;
* `datetimepicker` - плагин для работы с датой и временем;
* `noty` - для работы с уведомлениями;

***

**[Из TopJava](https://github.com/JavaWebinar/topjava/blob/doc/doc/lesson08.md#-5-bootstrap):**

В `spring-mvc.xml` мы должны явно указать маппинг на WebJars-ресурсы, с которыми будет работать приложение:
```xml
<mvc:resources mapping="/webjars/**" location="classpath:/META-INF/resources/webjars/"/>
```

> А где реально этот путь "classpath:/META-INF/resources/webjars"?

Внутри подключаемых webjars ресурсы лежат по пути `/META-INF/resources/webjars/...` Не поленитесь посмотреть на них через `Ctrl+Shift+N`. 
Все подключаемые jar попадают в classpath, и ресурсы доступны по этому пути.

> У меня webjars-зависимость лежит внутри ".m2\repository\org\webjars\". С чем это может быть связано?

Maven скачивает все зависимости в local repository, который по умолчанию находится в `~/.m2`. 
Каталог по умолчанию можно переопределить в `APACHE-MAVEN-HOME\conf\settings.xml`, элемент `localRepository`.

> WEBJARS лежат вообще в другом месте WEB-INF\lib\. Биндим mapping="/webjars/*" на реальное положение jar в war-e, откуда Spring знает, где искать наш jQuery?

В war в `WEB-INF/lib/*` лежат все jar, которые попадают к classpath. Spring при обращении по url `/webjars/` ищет по пути
биндинга `<mvc:resources mapping="/webjars/ " location="classpath:/META-INF/resources/webjars/"/>`
по всему classpath (то же самое, как распаковать все jar в один каталог) в `META-INF/resources/webjars/`. 
В этом месте во всех jar, которые мы подключили из webjars, лежат наши ресурсы.

> Оптимально ли делать доступ к статическим ресурсам (css, js, html) через webjars ?

На продакшене под нагрузкой статические ресурсы лучше всего держать не в war, а снаружи. Доступ к ним делается либо через 
[конфигурирование Tomcat](https://www.techsupper.com/2017/05/serve-static-resources-from-external-folder-outside-webapps-tomcat.html).  
Но чаще всего для доступа к статике ставят прокси, например [Nginx](https://nginx.org/ru/).

***


