# Docker

> **Note**<br>
> Команди для роботи з терміналом cmd:
> * `CTRL + C` - зупинити контейнер;
> * `CTRL + L` - очистити екран терміналу;


## Початок роботи з Docker
Перевірити версію встановленого **Docker** можна за командою `docker -v`.
Якщо його немає - треба встановити [Docker Desktop](https://www.docker.com/products/docker-desktop/) - це і є **Docker**.

> **Note**<br>
> Отримати список команд з описом можна просто виконавши команду `docker` у терміналі.

***

Додатково може потребувати встановлення/налаштування **WSL2** (Windows Subsystem for Linux).
> **Warning**<br>
> Якщо процес **Vmmem** (це віртуалка **WSL**) жре достобіса пам'яті, тоді необхідно зробити наступне:
> 1. зупинити WSL командою в терміналі `wsl --shutdown`;
> 2. знайти файл `.wslconfig`, розташування за адресою `%UserProfile%/.wslconfig`;
> 3. якщо файлу немає - створити, якщо є - просто додати інфу:
> ```text
> [wsl2]
> memory=2GB
> ```
> 4. перезапустити Docker.
>
> [SOURCE](https://www.koskila.net/how-to-solve-vmmem-consuming-ungodly-amounts-of-ram-when-running-docker-on-wsl/)

***

### Images (Образи)
У **Docker** вже існує безліч готових образів (images), як офіційних (від популярних компаній), 
так і неофіційних (різні розробники включаючи і себе). 
Ці образи (як і інфу про них) можна знайти на [hub.docker.com](https://hub.docker.com/) в розділі **Explore**.

Завантажити образ можна за допомогою команди `docker pull ім'я_образу`, 
наприклад `docker pull nginx` (дивися інструкції для кожного образу).

Подивитися всі свої завантажені образи - [`docker images`](https://docs.docker.com/engine/reference/commandline/image/).

***

### Containers (Контейнери)
Контейнер - це працюючий екземпляр образу. Вони створюються під час запуску образу.

Запустити контейнер з конкретного образу - `docker run ім'я_образу`, - `docker run nginx:latest`,
де **nginx** це ім'я образу (в деяких терміналах замість NAME пишуть REPOSITORY), 
а **:latest** - це тег. 
Якщо не вказувати тег, а тільки ім'я, тоді за замовчуванням використовується тег `:latest`.

> **Note**<br>
> При такому запуску контейнера вікно терміналу перехоплює керування, і тут вже команди не працюватимуть, допоки працює цей контейнер.

Подивитися усі запущені контейнери - [`docker container ls`](https://docs.docker.com/engine/reference/commandline/container_ls/).
Це робиться у новій вкладці/екземпляру термінала, там де стартував контейнер - система не дасть цього зробити.

> **Note**<br>
> Для перегляду працюючих контейнерів також можна використовувати `docker ps`. 
> `docker container ls` це більш адаптація до команд терміналу.

Зупиняють контейнер комбінацією клавіш `CTRL + C`.
Може виконуватися декілька секунд.

> **Note**<br>
> Для запуску контейнера у відокремленому режимі (detached, `-d`) - `docker run -d nginx:latest`.
> Це дозволить запустити контейнер у фоні, і можна буде далі працювати у даному вікні терміналу.

Щоб зупинити контейнер, який працює у `detached mode`, - `docker stop ід_контейнеру або ім'я_контейнеру`. 
Подивитися ці дані можна командою `docker ps`, ім'я підхватує `TAB`.

> **Note**<br>
> За замовчуванням контейнери самі не видаляються.
> 
> Щоб подивитися всі контейнери - і ті що працюють, і ті що зупиненні - `docker container ls -a`.
> 
> Щоб видалити зупинений контейнер - `docker container rm ід_контейнеру`.
> 
> Щоб видалити працюючий контейнер - `docker container rm ід_контейнеру -f`,де `-f` це `--force`. 

***

### Ports (Порти)

> **Note**<br>
> Щоб звернутися до додатку, який працює в контейнері (наприклад, до якогось ендпоінту), треба зробити прив'язку портів (mapping).
> Інакше додаток немає сенсу, якщо ані він не може звернутися за межі контейнеру, і користувач не може звернутися до інфи в контейнері.
> 
> Тобто якщо контейнер зі SpringBoot успішно стартував на localhost на портах 80 (вхід і вихід - 8080),
> то це працює localhost контейнеру, а не своєї системи! Контейнер - це ізольована середа, 
> і саме тому треба ще прописувати порти. 
>
> ```dockerfile
> docker run -d -p 8080:80 nginx:latest
> ``` 
> Щоб не прописувати кожного разу порти при запуску контейнера, налаштування портів додають у Dockerfile проєкту!

Запускати контейнери можна на будь-якому вільному порті ОС.

Контейнер можна запустити одразу на кількох портах:
```dockerfile
docker run -d -p 3000:80 -p 8080:80 nginx:latest
```

***

Треба розуміти як записувати порти, наприклад `3000:80`, де `3000` - це звернення до порту своєї ОС (типу `http://localhost:3000/`), 
а `80` - це порт в контейнері (`http://localhost:80/`). 
Тобто запис `3000:80` каже, щоб все що є на `localhost:80` всередині контейнера, транслювалося на ОС `localhost:3000`. 

***

Подивитися логування (logs) своїх контейнерів (свого додатку, наприклад, що пише SpringBoot) - `docker container logs ід_контейнеру`.

***

Запустити зупинений контейнер - `docker start ід_контейнеру`, при цьому, писати порти не треба, 
оскільки ці налаштування були збережені під час запуску/створення контейнеру.



## Dockerfile
> [Best practices for writing Dockerfiles](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/)
> [Dockerfile reference - Документация с командами](https://docs.docker.com/engine/reference/builder/)

> **Warning**<br>
> **ERROR: failed to read dockerfile**
> Потрібно створити dockerfile з усіма необхідними командами

Docker can build images automatically by reading the instructions from a `Dockerfile`.
A `Dockerfile` is a text document that contains all the commands a user could call on the command line to assemble an image.

### Java example
Для использования **Docker** с **Java**, необходимо сначала написать инструкции Docker'у в `Dockerfile`.
Потом необходимо выполнить инструкции в командной строке системы.

#### Вариант 1 - простой
1. Создать `Dockefile` в корне проекта, по шаблону:


```dockerfile
FROM openjdk:11

# copy the packaged jar file into our docker image
# т.е. копирует с локальной среды jar-файл в docker и дает ему имя "boot-flux-mongo" 
COPY target/*.jar boot-flux-mongo.jar

# set the startup command tp execute the jar
CMD ["java", "-jar", "/boot-flux-mongo.jar"]
```

2. Выполнить Maven-команду `mvn clean package`, которая пакует проект в `.jar/.war` файл (тип указывается в `pom.xml`). 
* `mvn clean package` - Converts your .java source code into a .jar/.war file and puts it into the /target folder.

> Перевірити роботу нашого .jar файлу можемо запустити команду в терміналі:
> ```commandline
> java -jar target/pastebox-0.0.1-SNAPSHOT.jar
> ```

3. Создать образ на основе `Dockerfile` и контекста (*"The build’s context is the set of files at a specified location PATH or URL. The PATH is a directory on your local filesystem. The URL is a Git repository location."*).<br>
   Запускаем команду для сбора нашего проекта.
```dockerfile
docker build -t boot-flux-mongo:0.0.1-SNAPSHOT .
```
* `docker build` - строит образ по данным в `Dockerfile`
* `-t` - говорит использовать тег
* `boot-flux-mongo` - название образа
* `0.0.1-SNAPSHOT` - имя тега
* Между названием образа и именем тега ставится символ `:`
* `.` ищет `Dockerfile` в каталоге, вместо точки можно написать название файла с настройками для докера.

4. Запускам образ командой:
```dockerfile
docker run --rm -d -p 8080:8080 boot-flux-mongo:0.0.1-SNAPSHOT
```
* `--rm` - удалить контейнер после исполнения или остановки
* `-d` - запускает в режиме Detached (background)
* `-p 8080:8080` - запускает контейнер на портах 8080
* `boot-flux-mongo` - имя запускаемого образа

5. Для отображение логов контейнера:
```dockerfile
docker logs 7632c6573d85
```
* `7632c6573d85` - хеш контейнера.

6. Для остановки образа можно использовать команду
```dockerfile
docker container rm -f f899aec5eac0
```

7. После изменений в проекте необходимо проделать все шаги заново.

#### Вариант 2 - с использованием Maven
Самый большой образ. Инструкции в `Dockerfile`:
```dockerfile
# select parent image
FROM maven:3.6.3-jdk-11

# copy the source tree and the pom.xml to our new container
COPY ./ ./

# package our application code
RUN mvn clean package

# set the startup command to execute the jar
CMD ["java", "-jar", "target/boot-flux-mongo-0.0.1-SNAPSHOT.jar"]
```
Запускаем выполнение командой:
```shell
docker run --rm -d -p 8080:8080 boot-flux-mongo:0.0.1-SNAPSHOT
```


#### Вариант 3 - Multi-stage
Инструкции в `Dockerfile`:
```dockerfile
# the first stage of our build will use a maven 3.6.3 parent image
FROM maven:3.6.3-jdk-11 AS MAVEN_BUILD

# copy the pom and src code to the container
COPY ./ ./

# package our application code
RUN mvn clean package

# the second stage of our build will use open jdk 11
FROM openjdk

# copy only the artifacts we need from the first stage and discard the rest
COPY --from=MAVEN_BUILD /target/boot-flux-mongo-0.0.1-SNAPSHOT.jar /demo.jar

# set the startup command to execute the jar
CMD ["java", "-jar", "/demo.jar"]
```

* `FROM maven:3.6.3-jdk-11 AS MAVEN_BUILD` - первый stage, в котором мы говорим, что он содержит __Maven__ и __JDK__, и называем этот stage __MAVEN_BUILD__.

Собираем образ:
```shell
docker build -t boot-flux-mongo:0.0.1-SNAPSHOT .
```

Запускаем образ:
```shell
docker run --rm -d -p 8080:8080 boot-flux-mongo:0.0.1-SNAPSHOT
```


### [Usage](https://docs.docker.com/engine/reference/builder/#usage)
`docker build` command builds an image from a `Dockerfile` and a *context*.
The build’s context is the set of files at a specified location PATH or URL.
The PATH is a directory on your local filesystem.
The URL is a Git repository location.

> **Warning**
> Do not use your root directory, `/`, as the `PATH` as it causes the build to transfer the entire contents of your hard drive to the Docker daemon.

To increase the build’s performance, exclude files and directories by adding a `.dockerignore` file to the context directory.
For information about how to create a `.dockerignore` file see [the documentation on this page](https://docs.docker.com/engine/reference/builder/#dockerignore-file).

Traditionally, the `Dockerfile` is called `Dockerfile` and located in the root of the context.
You use the `-f` flag with `docker build` to point to a Dockerfile anywhere in your file system.

#### [FROM](https://docs.docker.com/engine/reference/builder/#from)
The `FROM` instruction initializes a new build stage and sets the Base Image for subsequent instructions.
```dockerfile
FROM [--platform=<platform>] <image> [AS <name>]

FROM [--platform=<platform>] <image>[:<tag>] [AS <name>]

FROM [--platform=<platform>] <image>[@<digest>] [AS <name>]
```
* `ARG` is the only instruction that may precede `FROM` in the Dockerfile.
* `FROM` can appear multiple times within a single `Dockerfile` to create multiple images or use one build stage as a dependency for another.
* Optionally a name can be given to a new build stage by adding `AS name` to the `FROM` instruction.
* The `tag` or `digest` values are optional. If you omit either of them, the builder assumes a `latest` tag by default. The builder returns an error if it cannot find the `tag` value.



### `Dockerfile` example

A Docker image consists of read-only layers each of which represents a Dockerfile instruction. The layers are stacked and each one is a delta of the changes from the previous layer.
Consider this `Dockerfile`:
```dockerfile
FROM ubuntu:18.04
COPY . /app
RUN make /app
CMD python /app/app.py
```


Each instruction creates one layer:
* `FROM` creates a layer from the ubuntu:18.04 Docker image.
* `COPY` adds files from your Docker client’s current directory.
* `RUN` builds your application with make.
* `CMD` specifies what command to run within the container.


## Docker commands
> [Docker run reference - Документация с командами](https://docs.docker.com/engine/reference/run/)

> `CTRL+C` - выйти с зависшего терминала.

The basic docker run command takes this form:
```shell
$ docker run [OPTIONS] IMAGE[:TAG|@DIGEST] [COMMAND] [ARG...]
```
The docker run command must specify an IMAGE to derive the container from. An image developer can define image defaults related to:

* detached or foreground running
* container identification
* network settings
* runtime constraints on CPU and memory

***

* `-d` - run the container in detached mode (in the background).
* `-p` - 80:80 - map port 80 of the host to port 80 in the container.
* `docker images` - отображает доступные образы в системе.
* `docker ps` - отображает доступные контейнеры в системе. Контейнер работает до тех пор, пока работает приложение.
* `docker ps -a` - отображает все контейнеры включая остановленные.
* `docker ps -a -q` - отображает только ид всех контейнеров.
* `docker run --name hello hello-world` - задает имя контейнеру `hello`.
* `docker run --name hello -d hello-world` - запуск контейнера в фоне.
* `docker run --name hello -d --rm hello-world` - контейнер после отработки или остановки сам удалится.
* `docker stop hello` - остановить контейнер, <u>далее указывается ID или имя контейнера</u> (`hello`).
* `docker container rm -f HASH` - принудительно останавливает и удаляет контейнер:
  * `-f` = force
  * `HASH` = хеш контейнера, Х: f899aec5eac0.
* `docker container ls` - показывает данные текущего контейнера.
* `docker rm f7b247168a6e` - удаляет контейнер, далее указывается ID или имя контейнера.
* `docker rm $(docker ps -qa)` - удаляет все контейнеры - команда в команде.
* `docker build -t hello-world .` - создает образ, где `hello-world` - имя образа. а точка говорит собрать из текущего каталога.
* `docker run hello-world` - запуск образа, где `hello-world` - имя образа. Запуск равносилен выполнению программы.
* `docker volume ls` - отображает список `volume`.
* `docker volume create web` - создает volume с именем `web`.

***

Приклад:

* `docker run --rm --name web -p 8080:8080 -e TZ=Europe/Kyiv -v /Users/.../web-hello/resources:/usr/app/resources web-hello`:
  * `docker run` = запуск контейнера.
  * `--rm` = удалить контейнер после исполнения или остановки.
  * `--name web` = запускает контейнер с именем web.
  * `-p 8080:8080` = запускает контейнер на портах 8080.
  * `-e TZ Europe/Kyiv` = установка переменной окружения, аналог в dockerfile - ENV TZ.
  * `web-hello` = имя образа.
  * `-v` = будет использоваться абсолютный адрес, т.е. при смене данных в приложении, docker будет подхватывать изменения".


## Options

### Detached vs foreground
When starting a Docker container, you must first decide if you want to run the container in the background in a “detached” mode or in the default foreground mode.

__Detached (`-d`)__
To start a container in detached mode, you use `-d=true` or just `-d` option.

__Foreground__
In foreground mode (the default when `-d` is not specified), `docker run` can start the process in the container and attach the console to the process’s standard input, output, and standard error.

```dockerfile
-a=[]           : Attach to `STDIN`, `STDOUT` and/or `STDERR`
-t              : Allocate a pseudo-tty
--sig-proxy=true: Proxy all received signals to the process (non-TTY mode only)
-i              : Keep STDIN open even if not attached
```
If you do not specify `-a` then Docker will attach to both stdout and stderr .



## Docker-compose

### docker-compose.yaml

Це файл на основі якого буде створений контейнер з усіма образами (images).

Якщо вказаний просто ім'я певного образу (`image: mongo-express`), тоді буде завантажиться остання версія - latest.
Якщо потрібна інша версія - `image: mongo-express:0.54.0`

```yaml
version: "3.8"
services:
    mongodb:  # налаштування контейнера для mongodb
        image: mongo    # вказуємо конкретний image - завантажить останню версію
        container_name: mongodb   # ім'я нашого контейнера
        ports: 
            - 27017:27017   # вказуємо порти [host:container] - дефолтні порти MongoDB 27017
        volumes: 
            - data:/data    # '/data' може бути розписана нижче у розділі volumes 
        environment:    # в нашому середовищі будуть такі змінні зі значеннями
            - MONGO_INITDB_ROOT_USERNAME=rootuser
            - MONGO_INITDB_ROOT_PASSWORD=rootpass
    mongo-express:  # налаштування контейнера для mongo-express
        image: mongo-express
        container_name: mongo-express
        restart: always
        ports:
            - 8081:8081
        environment:
            - ME_CONFIG_MONGODB_ADMINUSERNAME=rootuser
            - ME_CONFIG_MONGODB_ADMINPASSWORD=rootpass
            - ME_CONFIG_MONGODB_SERVER=mongodb
volumes: 
    data: {} 
    
networks:
    default: 
        name: mongodb_network
```

Запустити виконання файлу:
```commandline
docker-compose -f docker-compose.yaml up
```
Інші команди:
```commandline
docker compose down
docker compose up -d
docker compose stop
docker compose start
```

## Articles
> [Полное практическое руководство по Docker: с нуля до кластера на AWS](https://habr.com/ru/post/310460/)

