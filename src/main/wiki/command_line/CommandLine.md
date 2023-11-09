# Command line
* [Setting up Windows Subsystem for Linux with zsh + oh-my-zsh + ConEmu](https://blog.joaograssi.com/windows-subsystem-for-linux-with-oh-my-zsh-conemu/)
* [Написание HTTP-запросов с помощью Curl](http://rus-linux.net/lib.php?name=/MyLDP/internet/curlrus.html)
* [How to set JAVA_HOME on Windows 10?](https://mkyong.com/java/how-to-set-java_home-on-windows-10/)
* [How to install Maven on Windows](https://mkyong.com/maven/how-to-install-maven-in-windows/)

> В bash слешы указываются в другую сторону - /<br>
> В cmd - \

> Переменные указываются, если Linux: `$JAVA_HOME`, если Windows: `%JAVA_HOME%`.

> Для многострочного написания кода используй \  

> [Соответствие консольных команд Windows и Linux](https://white55.ru/cmd-sh.html) 

> Задаємо свою зміну оточення `PATH_TO_SAVE`:
> 
> ```shell
> export PATH_TO_SAVE=C:\\idea_projects\\tt_modules-writer-reader-docker\\recorder\\build\\libs\\data\\forecast.txt
> ```


## Commands
Клавиша `TAB` будет дописывать названия файлов, адреса...

* `ls` - краткая инфа по каталогам.
* `ls -lah` - отображает скрытые файлы и папки с подробной инфой.
* `ls -all` - отображает список доступных файлов в данном каталоге командной строки.
* `cd src/main` - переходим во внутырь каталога main.
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
* `mkdir myfolder` - створює 1 теку.
* `mkdir -p src/main/java/hello` - створює ієрархію тек, необхідна опція `-p`.
* `rm -rf myfolder` (використовуються опції `-r` та `-f`) - видаляє певну теку з внутрішньою ієрархією без підтвердження.
* `mv example-3 example` - переміщає теку example-3 у теку example.
* `more` - дозволяє показувати контент файлу, `more docker-compose.yaml`.

from course Terminal, [Bash & VIM Essentials by Amigoscode](https://amigoscode.com/courses/enrolled/855076):
* `man` - надає повний опис команди: `man man`, `man find`, `man grep`, `man ls`... 
* `clear` or `CTRL + L` - очистить термінал від виводу.
* `CTRL + A` - переміщає курсор на початок строки;
* `CTRL + E` - переміщає курсор у кінець строки;
* `ALT + B` - переміщає курсор на одне слово назад;
* `ALT + E` - переміщає курсор на одне слово уперед;
* `ALT + BACKSPACE` - видаляє від курсора до пробіла ліворуч;
* `ALT + D` - видаляє від курсора до пробіла праворуч;
* `SPACE` - дозволяє пролистувати сторінки, особливо коли скрол на миші не працює;
* `man` (`man cal`, `man man`) - це документація про додаток; 
* `pwd` (**present working directory**) - показує шлях теки в котрій знаходимося;
* `ls` - показує всім теперішнього каталогу;
* `ls -a` (`ls --all`) - показує вміст теперішнього каталогу;
* `ls -al` - показує вміст теперішнього каталогу з додатковою інфою;
  - якщо строка починається з `d` (`drwxr-xr-x`), тоді це тека. А якщо `-` - файл:
  ```shell
  [10:52:08] serhiy :: DESKTOP-3D6FM5E  ➜  ~ » ls -al
  total 292
  drwxr-x---  5 serhiy serhiy   4096 Mar 24 10:54 .
  drwxr-xr-x  3 root   root     4096 Mar 23 14:45 ..
  -rw-------  1 serhiy serhiy     49 Mar 23 16:20 .bash_history
  -rw-r--r--  1 serhiy serhiy    220 Mar 23 14:45 .bash_logout
  -rw-r--r--  1 serhiy serhiy   3844 Mar 23 16:20 .bashrc
  -rw-r--r--  1 serhiy serhiy   3846 Mar 23 16:20 .bashrc.save
  -rw-r--r--  1 serhiy serhiy  10374 Mar 23 18:42 .dircolors
  -rw-------  1 serhiy serhiy     20 Mar 24 10:50 .lesshst
  drwxr-xr-x  3 serhiy serhiy   4096 Mar 23 15:53 .local
  -rw-r--r--  1 serhiy serhiy      0 Mar 23 14:45 .motd_shown
  drwxr-xr-x 12 serhiy serhiy   4096 Mar 23 14:51 .oh-my-zsh
  -rw-r--r--  1 serhiy serhiy    807 Mar 23 14:45 .profile
  -rw-r--r--  1 serhiy serhiy     10 Mar 23 15:36 .shell.pre-oh-my-zsh
  -rw-r--r--  1 serhiy serhiy      0 Mar 23 14:47 .sudo_as_admin_successful
  -rw-r--r--  1 serhiy serhiy  49076 Mar 23 14:50 .zcompdump
  -rw-r--r--  1 serhiy serhiy  50441 Mar 23 18:25 .zcompdump-DESKTOP-3D6FM5E-5.8.1
  -r--r--r--  1 serhiy serhiy 116864 Mar 23 18:25 .zcompdump-DESKTOP-3D6FM5E-5.8.1.zwc
  -rw-------  1 serhiy serhiy   2119 Mar 24 10:54 .zsh_history
  -rw-r--r--  1 serhiy serhiy   3918 Mar 23 18:53 .zshrc
  -rw-r--r--  1 serhiy serhiy     31 Mar 23 14:50 .zshrc.pre-oh-my-zsh
  -rw-r--r--  1 serhiy serhiy   3866 Mar 23 16:29 .zshrc.save
  drwxr-xr-x 35 serhiy serhiy   4096 Mar 23 16:30 fonts
  ```
* `tree myfolder` - відображає ієрархію теки - потрібно встановити додаток **brew**: `sudo apt-get install build-essential` 
* `cat`, `less`, `head`, `tail` - `tail myfile.sql` - читає файл - увесь, частину, згори, знизу.
* `mv hello words` - перейменовує теку з **hello** на **words**.
* `mv query.sql /words` - переміщує файл **query.sql** у теку **words**.
* `touch query.sql` - створює файл **query.sql**.
* `rm query.sql` - видаляє файл **query.sql**.
* `rm query-*.sql` - видаляє усі файли котрі починаються з **query** і закінчуються **.sql**. 
  Example: **query-test.sql**, **query-1.sql**... 
* `echo "select * from customers;"` - виводить текст на екран **select * from customers;**.
* `echo "select * from customers;" > query.sql` - записує текст **select * from customers;** у файл **query.sql**.
* `echo "select * from orders;" >> query.sql` - додає текст **select * from orders;** у кінець файла **query.sql**.
* `cat query.sql` - виводить на екран вміст файлу **query.sql**.
* `cp query.sql query-copy.sql` - копіює вміст файлу **query.sql** у новий файл **query-copy.sql**.
* `find . -name query.sql` - шукає за ім'ям файл або теку **query.sql** у нинішній теці і у всіх вкладених.
  - `find . -type f -name query.sql` - шукає по типу (`f` - файл, `d` - тека)  за ім'ям **query.sql** у нинішній теці і у всіх вкладених.
  - `-iname` - дозволяє ігнорувати капс, а `-name` - реагує на капс.
  - `find . -type f -iname "query*.sql"` - шукає за маскою. 
  - `find . -empty` - шукає порожні теки та файли.
  - `find . -type f -iname "QUERY-co*.sql" -delete` - видаляє файли за вказаним пошуком
* `grep -rn "order" example` - шукає текст у файлах за патерном.
  - `r` - рекурсивно виконує пошук у кожній кладеній теці.
  - `n` - вказує номер строки.
* `grep -rni -A 1 -B 1 "customer" .` - шукає текст у файлах за патерном, але видає на 1 строку раніше `-B 1 ` і одну строку пізніше `-A 1`.
  
* `del /q/f/s %temp%\*` - Delete all Temp Files to Clear Space.
* `nslookup` - Resolving DNS into IP.
* `ipconfig/all` - Know your pc ip and all net details.
* `driverquery` - Know about all installed drivers.
* `systeminfo` - Information about your system.
* `<command> | clip`, `help | clip` - Copy the results of a DOS command.
* `NETSTAT -AN` - Show all connections.


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

