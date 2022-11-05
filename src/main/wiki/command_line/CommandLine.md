# Command line
* [Написание HTTP-запросов с помощью Curl](http://rus-linux.net/lib.php?name=/MyLDP/internet/curlrus.html)
* [How to set JAVA_HOME on Windows 10?](https://mkyong.com/java/how-to-set-java_home-on-windows-10/)
* [How to install Maven on Windows](https://mkyong.com/maven/how-to-install-maven-in-windows/)

> В bash слешы указываются в другую сторону - /<br>
> В cmd - \

> Переменные указываются, если Linux: `$JAVA_HOME`, если Windows: `%JAVA_HOME%`.

> Для многострочного написания кода используй \  

> [Соответствие консольных команд Windows и Linux](https://white55.ru/cmd-sh.html) 

## Commands
Клавиша `TAB` будет дописывать названия файлов, адреса...

* `ls` - краткая инфа по каталогам.
* `ls -lah` - отображает скрытые файлы и папки с подробной инфой.
* `ls -all` - отображает список доступных файлов в данном каталоге командной строки.
* `cd src/main` - переходим во внутырь каталога main.
* `pwd` - показывает адрес директории где ты сейчас находишся.
* `cd /` - перемещаешся в корень.
* `cd ..` - перемещаешся в уровень назад.
* `cd /d C:\dev` - переключиться на нужный адресс/папку.
* `start .` - откроет текущюю папку с файлами.
* `echo "hello world" > somefile.txt` - создаст текст указанный в кавычках и сохранит его в указанный файл somefile.txt.
* `mv plank.jpg somefile.txt ./tr-files` - переносит указанные файлы в указанную папку.
* `echo %TOPJAVA_ROOT%` - Выводит адрес системной переменной (можно посмотреть в "переменные среды" в MY PC).
* `whoami` - name of your computer (username).
* `dir` - return the list of non-hidden files and folders in your current directory. If you want to view hidden files use `dir /A`.
* `C:\users\student> help cd` - command can take any command as a parameter and return all the available options.
* `mkdir -p src/main/java/hello` - создаст иерархию папок, очень полезно использовать в терминале IntelliJ IDEA.

### Сокращения
* `-d` for `--data`.
* `-H` for `--header`.

## HotKeys
* `Ctrl + D` - выход. Иногда может глючить - не реагирует. 


## Примеры использования

### Освобождение занятого порта
Сначало, смотрим кем занят порт командой:
```shell
netstat -a -n -o | find "8080"
```
Далее копируем с результата *Process Id* и добавляем в команду вместо `< Process Id >`:
```shell
taskkill  /F  /PID < Process Id >
```
Команда убьет процесс и освободит порт.



### Работа с AWS EC2 SSH
Если нужно зайти на инстанс AWS EC2:
  ```shell
  ssh -i "ec2KP.pem" ec2-user@ec2-3-120-152-58.eu-central-1.compute.amazonaws.com
  ```

***

Если нужно скопировать файлы с локального ПК на инстанс амазона. Будут залиты все файлы найденные в папке без самой папки:
  ```shell
  scp -i ./ec2KP.pem -r ./tr-files/* ec2-user@3.120.152.58:/home/ec2-user
  ```
где:
* `scp` - команда передачи файлов (from/to) путём копирования. Под капотом для передачи файлов использует *ssh*.
* `-i ./ec2KP.pem` - использует ключ, который дает доступ к инстансу.
* `-r ./tr-files/*` - путь на локальном ПК от куда будут копироваться файлы. Сама папка скопирована не будет.
* `/*` - все файлы без папки.
* `ec2-user@3.120.152.58:` - адрес инстанса - пользователь (ec2-user - дефолтный пользователь каждого инстанса) и публичный ip.
* `/home/ec2-user` - путь на инстансе куда копировать файлы.

Если появляются проблемы типа запрета на доступ, посмотри какие разрешения есть в корне инстанса.
Сначало используй команду `cd /` для перехода в корень, потом `ls` - будет отображен список файлов и папок, где имена юзеров это доступы к папкам на чтение и запись.
Далее сверь команду - под каким юзером ты это делаешь, и есть ли разрешение у данного юзера.

***

Если нужно скорировать файл с инстанса на локальный ПК:
```shell
scp -i ./ec2KP.pem ec2-user@3.120.152.58:/home/ec2-user/somefile.txt ~/Desktop/tr-files/somefile.txt
```

***

Если нужно скопировать все файлы (без самой папки) с инстанса на локальный ПК:
```shell
scp -i ./ec2KP.pem -r ec2-user@3.120.152.58:/home/ec2-user/tr-files/* ~/Desktop/tr-files
```


### [Test a REST API with curl](https://www.baeldung.com/curl-rest?fbclid=IwAR0oPNaS1TxkFapkgbg7ByWaX47XwmdKqxiMveclZXX2vldmRDRR5STB70k)
**curl** is a command-line tool for transferring data, and it supports about 22 protocols, including HTTP.

**curl** supports over 200 command-line options.

```shell
curl -v http://www.example.com/
```
`-v` - Verbose - the commands provide helpful information such as the resolved IP address, the port we're trying to connect to, and the headers.

***

#### Post (Project: t_spring)
Многострочность не работает во многих терминалах.

Отправка json-запроса в строке (2 варианта для Git-Bash):
> Если отправлять данные на киррилице - будут ошибки.
```shell
curl -X POST http://localhost:8080/users -H 'Content-Type: application/json' -d '{"userName":"my_login","password":"my_password"}'

curl -d '{"userName":"mendigo","password":"asdasdasd"}' -H 'Content-Type: application/json' http://localhost:8080/users
```
> **ВНИМАНИЕ!** Для Windows другой синтаксис - нужно использовать `"`, и экранировать каждую кавычку в запросе.
Windows command prompt has no support for single quotes like the Unix-like shells.
```shell
curl -d "{\"userName\":\"mendigo\",\"password\":\"asdasdasd\"}" -H "Content-Type: application/json" http://localhost:8080/users
```

Отправка json-запроса в файле:
```shell
curl -d @request.json -H "Content-Type: application/json" http://localhost:8080/users
```


## Пример работы CURL

### Отправка сообщения на почту GMail by curl
> Обязательно нужно своя почта `gmail`.

> Также нужно разрешить принимать аккаунтом не безопасные данные - [здесь](https://myaccount.google.com/u/2/lesssecureapps?pli=1)

```shell
curl --ssl-reqd \
 --url 'smtps://smtp.gmail.com:465' \
 --user 'YOUR_EMAIL@gmail.com:YOUR_ACCOUNT_PASSWORD' \
 --mail-from 'YOUR_EMAIL@gmail.com' \
 --mail-rcpt 'EMAIL_TO_SEND@gmail.com' \
 --upload-file msg.txt
```

### Get request by curl
В тестовом задании `tt_currency-converter` отправляю запрос на локалку и в ответе приходит json:
```shell
curl http://localhost:8080/nbu
```

С форматированием pretty json:
```shell
curl http://localhost:8080/nbu | json_pp
```
Для UTF-8 есть код с использованием конвертера `iconv`:
```shell
curl http://localhost:8080/nbu | iconv -t utf-8
```

***

Данная команда сохранит json в файл:
```shell
curl http://localhost:8080/nbu | iconv -t utf-8 > myJson.json
```

***

### Запросы cURL для Windows (cmd)
Мои команды из ДЗ по TopJava (vol.24)

|Controller method  | REST address       | cURL command |
|:-------           |:--------           |:-------- |
|getAll             | /rest/meals        | `curl -v http://localhost:8080/topjava/rest/meals` |
|get                | /rest/meals/100002 | `curl -v http://localhost:8080/topjava/rest/meals/100002` |
|delete             | /rest/meals/100002 | `curl -X DELETE http://localhost:8080/topjava/rest/meals/100002` |
|createWithLocation | /rest/meals        | `curl -X POST http://localhost:8080/topjava/rest/meals -H "content-type:application/json" -d "{ \"dateTime\":\"2021-12-01T11:30:00\",\"description\":\"BLA\",\"calories\":\"555\"}"` |
|update             | /rest/meals/100011 | `curl -X PUT http://localhost:8080/topjava/rest/meals/100011 -H "content-type:application/json" -d "{ \"id\":\"100011\",\"dateTime\":\"2021-12-01T11:30:00\",\"description\":\"BLA\",\"calories\":\"666\"}"` |
|getBetween         | /rest/meals/filter | `curl -v http://localhost:8080/topjava/rest/meals/filter?start=2020-01-30T00:15:30&end=2020-01-30T23:15:30` |
