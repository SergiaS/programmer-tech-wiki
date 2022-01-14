# TomCat

> Логи сервера можно включить в конфе IDEA (`Edit Configurations...` > `Logs`). Найти файлы можно в папке IDEA
> ```yaml
> C:\Users\SK88\AppData\Local\JetBrains\IntelliJIdea2021.2\tomcat\
> ```

> У TomCat есть свой сервлет-api, и в сборку проекта зависимость `servlet-api` НЕ должна идти (она там не нужна) - противном случае будут ошибки.
> Поэтому, у данной зависимости должна быть область действия зависимости scope `provided`:
> ```xml
> <scope>provided</scope> 
> ```

> Деплоиться в Tomcat лучше как `war exploded`: нет упаковки в war и при нажатой кнопке `Update Resources on Frame Deactivation` 
> могут обновляться `css`, `html`, `jsp` без передеплоя. 
> При изменении `web.xml`, добавлении методов, классов необходим redeploy.



## Запуск с консоли
1. Открываем `cmd`.
2. Вводим: `catalina.bat start`. 
   Прибл. расположение `C:\dev\apache-tomcat-9.0.34\bin\catalina.bat`
   Если надо в режиме дебага: `catalina.bat jpda start`.

## Запуск в браузере
1. В главной папке TomCat открыть файл `TOMCAT_HOME\conf\tomcat-users.xml`
2. И добавить строку (пользователя) до закрытия тега `<tomcat-users>`:
   ```xml
   <user username="tomcat" password="tomcat" roles="tomcat,manager-gui,admin-gui"/>
   ```
3. Перейти на страницу `http://localhost:8080/manager/html` и войти с креденшилами `tomcat`.


## Конфигурирование Tomcat через maven plugin. Jndi-lookup.
**Из лекции TopJava:**

Многие настройки сервера(web-контейнера) можно вынести в отдельный файл-конфигурацию. 
Настройки TomCat определим в отдельном файле `context.xml`.

Настройка пула TomCat для соединения с базой данных
```xml
<!-- Имя ресурса, к которому мы будем получать доступ из приложения по JNDI -->
    <Resource name="jdbc/topjava"
              auth="Container"
              type="javax.sql.DataSource"
              
              <!-- Настройки подключения к базе данных -->
              url="jdbc:postgresql://localhost:5432/topjava"
              username="user"
              password="password"
              driverClassName="org.postgresql.Driver"
              
              <!-- Настройки пула коннектов к базе данных -->
              validationQuery="SELECT 1"
              maxTotal="10"
              minIdle="2"
              maxWaitMillis="20000"
              initialSize="2"
              maxIdle="5"
              testOnBorrow="true"
              removeAbandonedOnBorrow="true"
              testWhileIdle="true"/>
```
Для того чтобы **TomCat** при запуске создавал пул коннектов, требуется добавить maven плагин в секцию **build**
```xml
<!-- http://stackoverflow.com/questions/4305935/is-it-possible-to-supply-tomcat6s-context-xml-file-via-the-maven-cargo-plugin#4417945 -->
<!-- https://codehaus-cargo.github.io/cargo/Tomcat+9.x.html -->
<plugin>
    <groupId>org.codehaus.cargo</groupId>
    <artifactId>cargo-maven3-plugin</artifactId>
    <version>1.9.5</version>
    <configuration>
        <container>
            <containerId>tomcat9x</containerId>
            <systemProperties>
                <file.encoding>UTF-8</file.encoding>
                <!-- Активные профили Spring, с которыми будет запускаться приложение-->
                <spring.profiles.active>tomcat,datajpa</spring.profiles.active>
            </systemProperties>
            <!-- Для создания пула коннектов томкату нужен драйвер postgres, поэтому добавляем его в зависимости-->
            <dependencies>
                <dependency>
                    <groupId>org.postgresql</groupId>
                    <artifactId>postgresql</artifactId>
                </dependency>
            </dependencies>
        </container>
        <configuration>
            <configfiles>
                <configfile>
                    <!-- Путь к файлу, в котором определены настройки пула коннектов-->
                    <file>src/main/resources/tomcat/context.xml</file>
                    <todir>conf/Catalina/localhost/</todir>
                    <tofile>${project.build.finalName}.xml</tofile>
                </configfile>
            </configfiles>
        </configuration>
        <deployables>
            <deployable>
                <groupId>ru.javawebinar</groupId>
                <artifactId>topjava</artifactId>
                <type>war</type>
                <properties>
                    <context>${project.build.finalName}</context>
                </properties>
            </deployable>
        </deployables>
    </configuration>
</plugin>
```
В `spring-db.xml` создаем новый профиль `tomcat`, для него будет создаваться бин dataSource с помощью соединения, которое будет получено из пула коннектов **TomCat** по **JNDI**. 
При этом в профиле мы можем указать расположение файла свойств, в котором будут описаны дополнительные параметры пула коннектов контейнера сервлетов - у нас это файл `tomcat.properties`.

С плагином мы можем сконфигурировать **Tomcat** прямо в `pom.xml` и запустить его с задеплоенным туда нашим приложением WAR из командной строки без **IDEA** и без инсталляции **Tomcat**. 
По умолчанию он скачивает его из центрального maven-репозитория (можно также указать свой в `<container><home>${container.home}</home></container>`). 
При запуске **Tomcat** из **IDEA** запускается **Tomcat**, путь к которому мы прописали в конфигурации запуска (со своими настройками).

***

> Томкат сам управляет пулом коннектов? На каждый запрос в браузере будет даваться свой коннект?

Да, в Томкате есть реализация пула коннектов **tomcat-jdbc** (мы его подключаем со `scope=provided`). 
Если запускаемся с профилем `tomcat`, приложение на каждую транзакцию (или операцию не в транзакции) берет коннект к базе из пула, 
сконфигурированного в подкладываемом **Tomcat** `context.xml`.

***

> Для чего мы делаем профиль `tomcat`? Возможно два варианта запуска приложения: либо `cargo`, либо `tomcat`? И если мы запускаем через 
> `tomcat` то в `spring-db.xml` через **jee:jndi-lookup** подтягивается конфигурация `tomcata` из `\src\main\resources\tomcat\context.xml`?

1. Есть **cargo-maven3-plugin** который автоматически запускает **Tomcat** и деплоит туда наше приложение. 
Т.е. это тоже деплой в **Tomcat**, но через **Maven**.
2. В xml конфигурации **Tomcat** можно настраивать ресурсы (кроме пула коннектов к БД могут быть, например, JMS или настройки Mail).
Это никак не связано с cargo плагином. 
В **Spring** этот сконфигурированный ресурс контейнера сервлетов подлючается через **jee:jndi-lookup**. 
Т.к. у нас несколько вариантов конфигурирования `DataSource`, мы этот вариант сделали в `spring-db.xml` в профиле `tomcat`.
3. Плагин **cargo** позволяет задавать xml конфигурацию запускаемого **Tomcat** (у нас `src/main/resources/tomcat/context.xml`). 
И в параметрах запуска мы задаем активные профили **Spring** `tomcat,datajpa` через `spring.profiles.active`. 
Таким образом, мы в плагине конфигурируем **Tomcat**, деплоим в него приложение и задаем приложению активные профили **Spring** для `DataSource` из конфигурации **Tomcat**.

Еще раз: плагин `cargo` и `JNDI` - это две не связанные между собой вещи, просто мы добавили их в проект в одном патче.
Плагин запускается после сборки проекта. Запуск из командной строки:
```commandline
mvn clean package -DskipTests=true org.codehaus.cargo:cargo-maven3-plugin:1.9.5:run
```


