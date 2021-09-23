# Maven

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
Плагин `maven-compiler-plugin` нужен для автоматической версии Java, иначе будет сбрасываться на рание.

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
