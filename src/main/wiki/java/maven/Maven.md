# Maven
* [Maven в вопросах и ответах](http://java-online.ru/maven-faq.xhtml)

> Всё что находится в папке `src/main/resources`, при сборке Maven помещает в `classpath`.

> При удалении класса из исходников, его скомпилированная версия все еще будет находиться в `target` (и `classpath`). 
> В этом случае (или в любом другом, когда проект начинает глючить) сделайте `mvn clean`. 

## Добавления веб-модуля (`web.xml`) в проект (IntelliJ IDEA)
Для добавления `web.xml` и его структуры нужно зайти в:<br>
`Project Structure` > `Modules` > на проекте выбрать `+` > `Web` ...

в `Deployment Descriptors` указать адресс размещения, например:
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



## [Область действия зависимости `scope`](http://java-online.ru/maven-dependency.xhtml#scope)
Область действия зависимости scope определяет этап жизненного цикла проекта, в котором эта зависимость будет использоваться. Maven использует 6 областей :

* `compile` - область по умолчанию, использутся, если scope не определена. Compile зависимости доступны во всех `classpath` проекта;
* `provided` - очень похоже на compile, но эта зависимость в сборку не попадает. 
Предполагается, что зависимость (артефакт) уже присутствует в JDK или в WEB-контейнере. 
Эта область доступна только на этапах компиляции и тестирования и не является транзитивной;
* `runtime` - зависимость с данной областью видимости не обязательна для compilation и используется в фазе выполнения;
* `test` - зависимость используется при тестировании кода приложения;
* `system` - область похожа на provided за исключением того, что необходимо определить физическое расположение артефакта на диске. 
Артефакт с этой областью видимости maven не ищет в репозитории;
* `import` - эта область используется в зависимости секции `<dependencyManagement>` при сложных связях.
