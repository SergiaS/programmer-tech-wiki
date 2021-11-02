# TomCat

> У TomCat есть свой сервлет-api, и в сборку проекта зависимость `servlet-api` НЕ должна идти (она там не нужна) - противном случае будут ошибки.
> Поэтому, у данной зависимости должна быть область действия зависимости scope `provided`:
> ```xml
> <scope>provided</scope> 
> ```

> Деплоиться в Tomcat лучше как `war exploded`: нет упаковки в war и при нажатой кнопке `Update Resources on Frame Deactivation` можно обновляться css, html, jsp без передеплоя. 
> При изменении `web.xml`, добавлении методов, классов необходим redeploy.

### Запуск с консоли
1. Открываем `cmd`.
2. Вводим: `catalina.bat start`. 
   Прибл. расположение `C:\dev\apache-tomcat-9.0.34\bin\catalina.bat`
   Если надо в режиме дебага: `catalina.bat jpda start`.

### Запуск в браузере
1. В главной папке TomCat открыть файл `TOMCAT_HOME\conf\tomcat-users.xml`
2. И добавить строку (пользователя) до закрытия тега `<tomcat-users>`:
   ```xml
   <user username="tomcat" password="tomcat" roles="tomcat,manager-gui,admin-gui"/>
   ```
3. Перейти на страницу `http://localhost:8080/manager/html` и войти с креденшилами `tomcat`.
