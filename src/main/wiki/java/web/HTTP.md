# HTTP

> Креденшилы в header'ы можно устанавливать и посылать запрос, если на сервере используется базовая авторизация (http-basic).
> При переадресации использование header'ов бесполезно.

***

* [Руководство по выбору между GET и POST](https://handynotes.ru/2009/08/get-versus-post.html)

![img](https://raw.githubusercontent.com/SergiaS/programmer-tech-wiki/master/src/main/resources/img/get-vs-post.png)

***

## REST
* [Java Interview Series: Spring RESTful Web Services](https://medium.com/@.midi/interview-questions-on-spring-restful-web-services-86d0e5e28a14)
* [Java Interview Series: REST API](https://medium.com/@.midi/rest-api-interview-questions-2f2ef9329a13)
* [10 Best Practices for Better RESTful API](https://medium.com/@mwaysolutions/10-best-practices-for-better-restful-api-cbe81b06f291)


## Browser

***

> Как можно в браузере сбросить введенный пароль базовой авторизации?

Проще всего делать новый запрос в новой анонимной вкладке (`Ctrl+Shift+N` в Chrome)

***

> Как по REST определяется залогиненный юзер?
> Аутентификация происходит при каждом запросе?

[Способы RESTful Authentication](https://stackoverflow.com/questions/319530/restful-authentication).
Мы будем использовать 2:

Basic Authentication для REST контроллеров с аутентификацией при каждом запросе
cookie + http session для UI-контроллеров на следующем уроке

***



