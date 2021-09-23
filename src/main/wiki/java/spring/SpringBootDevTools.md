# Spring Boot Developer Tools
[Documentation - 1.5.16.RELEASE](https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/using-boot-devtools.html#using-boot-devtools-livereload)
**Рекомендуется для разработки!**

> Время перезагрузки ~10 сек.

<details>
<summary>SHOW MENU</summary>

- [What is Spring Boot DevTools?](#what-is-spring-boot-devtools)
- [How to enable automatic restart in IntelliJ IDEA](#spring-boot-developer-tools-how-to-enable-automatic-restart-in-intellij-idea)

</details>



## What is Spring Boot DevTools?
Spring DevTools автоматически подхватывает изменения в проекте без перезагрузки!

First you need to add a dependency to your Maven:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
    <version>${springboot.version}</version>
</dependency>
```
* `scope` - says, that it will be work only in runtime.
* `optional` - says, that this is an additional feature.


## [Spring Boot Developer Tools: How to enable automatic restart in IntelliJ IDEA](https://dev.to/suin/spring-boot-developer-tools-how-to-enable-automatic-restart-in-intellij-idea-1c6i)
> To configure ths Spring DevTools library you need to do the following steps:
> 1. go under Setting -> Compiler and set "Build project automatically" checkbox
> 2. press Ctrl+Shift+A in IDEA, type "Registry". In the "Registry", scroll the list to find "compiler.automake.allow.when.app.running" option and enable it.
>
> After that when you change something in app and save new changes (Ctrl+s) the application has to make restart automatically.

> Чтобы Spring DevTools подхватывал изменения нужно предварительно сохраняться `Ctrl+S`.

> Также может понадобиться установить настройки конфигурации запуска файла в IntelliJ IDEA.
> Для этого заходим в `Edit configuration` выбираем настройки для `On Update action > Update trigger file`, и для `On frame deactivation > Update classes and resources`. 

## Problems
Бывает так, что DevTools обновляется дважды. Этот сорс может помочь:
[Application restarts twice on reload](https://github.com/spring-projects/spring-boot/issues/25269)
```properties
# application.properties
spring.devtools.restart.poll-interval=2s
spring.devtools.restart.quiet-period=1s
```

<hr>



