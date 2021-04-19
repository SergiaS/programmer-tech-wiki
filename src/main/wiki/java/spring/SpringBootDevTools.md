# Spring Boot Developer Tools
**Рекомендуется для разработки!**

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
