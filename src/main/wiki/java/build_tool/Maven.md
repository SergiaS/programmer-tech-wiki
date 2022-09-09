# Maven
* [Maven в вопросах и ответах](http://java-online.ru/maven-faq.xhtml)

> Каталог `.mvn` нужен если на компе не установлены средства сборки Maven,
> а также 2 командых файла запуска `mvnw` и `mvnw.cmd`.

> Всё что находится в папке `src/main/resources`, при сборке Maven помещает в `classpath`.

> При удалении класса из исходников, его скомпилированная версия все еще будет находиться в `target` (и `classpath`). 
> В этом случае (или в любом другом, когда проект начинает глючить) сделайте `mvn clean`. 

> Если зависимость имеет `<scope>runtime</scope>`, то она не будет индексироваться IDEA.


## Установка Maven
Maven нужно установить, чтобы использовать его команды. Есть 2 способа:

1. [Установка Maven на ОС](https://maven.apache.org/install.html)
> Важный момент - [скачивать](https://maven.apache.org/download.cgi) нужно БИНАРНУЮ версию zip-архива, чтобы была папке `bin`.

2. Использовать wrapper'ы (как при [spring-initializr](https://start.spring.io/)). Maven будет виден только проекту 
> Каталог `.mvn` нужен если на компе не установлены средства сборки Maven, а также 2 командых файла запуска `mvnw` и `mvnw.cmd`.


## Репозитории Мавена
* [Maven Central Repository Search](https://search.maven.org/)
  * `-Mx` означают предварительные milestone версии


## Добавления веб-модуля (`web.xml`) в проект (IntelliJ IDEA)
Для добавления `web.xml` и его структуры нужно зайти в:
`Project Structure` > `Modules` > на проекте выбрать `+` > `Web` ...

в `Deployment Descriptors` указать адрес размещения, например:
```text
C:\java\projects\tt_rss-reader\src\main\webapp\WEB-INF\web.xml
```
... а в `Web Resource Directories`, например:
```text
C:\java\projects\tt_rss-reader\src\main\webapp
```
... после этих настроек папки и `web.xml` появятся сами.


## Простой pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>tt_rss-reader</artifactId>
    <packaging>jar</packaging>
    <version>1.0-SNAPSHOT</version>
    <name>RSS Reader</name>
    <description>Simple RSS Reader app</description>
    
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>11</java.version>
        <rome.version>1.0</rome.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>com.rometools</groupId>
            <artifactId>rome</artifactId>
            <version>1.16.0</version>
        </dependency>
    </dependencies>

    <build>
        <finalName>RSS Reader</finalName>
        <defaultGoal>package</defaultGoal>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
    
</project>
```
Плагин `maven-compiler-plugin` нужен для автоматического определения версии Java, иначе будет сбрасываться на ранние.

> В версии `Maven 4.0.0` данный плагин добавлять не нужно, достаточно объявить ключевые поля в `<properties>`:
```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <java.version>11</java.version>
    <maven.compiler.source>${java.version}</maven.compiler.source>
    <maven.compiler.target>${java.version}</maven.compiler.target>
</properties>
```
**Почему-то, изменения подхватываются только при добавление/удалении любой зависимости, а не при изменении инфы в `pom.xml`.** 




## Plugins

### [Maven WAR Plugin](https://www.baeldung.com/maven-generate-war-file?fbclid=IwAR1py7FSLmUKST6qMN3lInaFacDjdFd2D2-jpz_IcL_YNMfF2T573r7OzD8#maven-war-plugin)
The Maven WAR plugin is responsible for collecting and compiling all the dependencies, classes, and resources of the web application into a web application archive.
```xml
<plugin>
    <artifactId>maven-war-plugin</artifactId>
    <version>3.3.1</version>
</plugin>
```

### [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/usage.html)
* [Плагин тестирования maven-surefire-plugin](http://java-online.ru/maven-plugins.xhtml#maven-surefire-plugin)

Плагин предназначен для запуска тестов и генерации отчетов по результатам их выполнения.

По умолчанию привязан к фазе тест, ищет код проекта в `src/test/java`.

Плагин запускает код JUnit'а, и далее ищет все классы в `src/test/java`. 

По умолчанию на тестирование запускаются все java-файлы, наименование которых начинается с «Test» и заканчивается «Test» или «TestCase» :

* `**/Test*.java`
* `**/*Test.java`
* `**/*TestCase.java`

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.22.2</version>
    <configuration>
        <argLine>-Dfile.encoding=UTF-8</argLine>
    </configuration>
</plugin>
```

### [Maven Compiler Plugin](http://java-online.ru/maven-plugins.xhtml#maven-compiler-plugin)
Доступен по умолчанию, но практически в каждом проекте его приходится переобъявлять. 
В простейшем случае плагин позволяет определить версию java машины (JVM), для которой написан код приложения, и версию java для компиляции кода.

По умолчанию плагин `maven-compiler-plugin` ищет код проекта в `src/main/java`.



## Profiles
Maven позволяет запускать проект с разными конфигурациями, и эти конфигурации задаются профилями.

Профили в Intellij IDEA переключаются в боковой вкладке Maven - там появится вкладка Profiles.

При смене профиля нужно обязательно делать **Reload All Maven Projects** чтобы проект переконфигурился с другими зависимостями.

<u>Пример работы с Topjava:</u>
> Для переключения на HSQLDB необходимо:
> * поменять в окне Maven Projects профиль (Profiles) - выключить `postgres`, включить `hsqldb` - и сделать *Reimport All Maven Projects* (1-я кнопка);
> * поменять `Profiles.ACTIVE_DB = HSQLDB`;
> * почистить проект `mvn clean` (фаза clean не выполняется автоматически, чтобы каждый раз не перекомпилировать весь проект).
> 
> Для корректного отображения неактивного профиля в IDEA проверьте флаг Inactive profile highlighting и сделайте проекту clean


## [Область действия зависимости `scope`](http://java-online.ru/maven-dependency.xhtml#scope)
Область действия зависимости scope определяет этап жизненного цикла проекта, в котором эта зависимость будет использоваться. Maven использует 6 областей :

* `compile` - область по умолчанию, используется, если scope не определена. Compile зависимости доступны во всех `classpath` проекта;
* `provided` - очень похоже на compile, но эта зависимость в сборку не попадает. 
Предполагается, что зависимость (артефакт) уже присутствует в JDK или в WEB-контейнере. 
Эта область доступна только на этапах компиляции и тестирования и не является транзитивной;
* `runtime` - зависимость с данной областью видимости не обязательна для compilation и используется в фазе выполнения;
* `test` - зависимость используется при тестировании кода приложения;
* `system` - область похожа на provided за исключением того, что необходимо определить физическое расположение артефакта на диске. 
Артефакт с этой областью видимости maven не ищет в репозитории;
* `import` - эта область используется в зависимости секции `<dependencyManagement>` при сложных связях.


## Теги

### `<dependencyManagement>` 
`dependencyManagement` - это зависимости, которые в .jar не включаются. 
Здесь просто указывается, какой версии зависимости будем использовать. 
Т.е. здесь будем использовать зависимости с такой-то версией, а в основном проекте можно версию не прописывать, и версия будет браться отсюда.

Когда мы используем несколько дополнительных зависимостей например от JUnit, и эти зависимости имеют разную версию. 
Таким образом, в сборку могут попасть несколько версий отдой зависимостей. А указав в dependencyManagement конкретную - попадёт одна.


## Исключение пакета из зависимости
Из TopJava:

Для более удобного сравнения объектов в тестах мы будем использовать библиотеку **Harmcrest** с **Matcher'ами**, которая позволяет делать сложные проверки. 
С **Junit** по умолчанию подтягивается **Harmcrest** core, но нам потребуется расширенная версия:
в `pom.xml` из зависимости **Junit** исключим дочернюю `hamcrest-core` и добавим `hamcrest-all`.
```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>${junit.version}</version>
    <scope>test</scope>
    <exclusions>
        <exclusion>
            <artifactId>hamcrest-core</artifactId>
            <groupId>org.hamcrest</groupId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.hamcrest</groupId>
    <artifactId>hamcrest-all</artifactId>
    <version>${hamcrest.version}</version>
    <scope>test</scope>
</dependency>
```



