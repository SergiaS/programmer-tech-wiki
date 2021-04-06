# Docker

## Dockerfile
> [Best practices for writing Dockerfiles](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/)

> [Dockerfile reference - Документация с командами](https://docs.docker.com/engine/reference/builder/)

Docker can build images automatically by reading the instructions from a `Dockerfile`.
A `Dockerfile` is a text document that contains all the commands a user could call on the command line to assemble an image.

### Java example
Для использования Docker с Java, необходимо сначала написать инструкции Docker'у в `Dockerfile`.
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

>⚠️**Warning**<br>
>❌ Do not use your root directory, `/`, as the `PATH` as it causes the build to transfer the entire contents of your hard drive to the Docker daemon.

To increase the build’s performance, exclude files and directories by adding a `.dockerignore` file to the context directory.
For information about how to create a `.dockerignore` file see [the documentation on this page](https://docs.docker.com/engine/reference/builder/#dockerignore-file).

Traditionally, the `Dockerfile` is called `Dockerfile` and located in the root of the context.
You use the `-f` flag with `docker build` to point to a Dockerfile anywhere in your file system.

#### [FROM](https://docs.docker.com/engine/reference/builder/#from)
The FROM instruction initializes a new build stage and sets the Base Image for subsequent instructions.
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

The basic docker run command takes this form:
```shell
$ docker run [OPTIONS] IMAGE[:TAG|@DIGEST] [COMMAND] [ARG...]
```
The docker run command must specify an IMAGE to derive the container from. An image developer can define image defaults related to:

* detached or foreground running
* container identification
* network settings
* runtime constraints on CPU and memory

## Options

### Detached vs foreground
When starting a Docker container, you must first decide if you want to run the container in the background in a “detached” mode or in the default foreground mode.

#### Detached (`-d`)
To start a container in detached mode, you use `-d=true` or just `-d` option.

#### Foreground
In foreground mode (the default when `-d` is not specified), `docker run` can start the process in the container and attach the console to the process’s standard input, output, and standard error.

```dockerfile
-a=[]           : Attach to `STDIN`, `STDOUT` and/or `STDERR`
-t              : Allocate a pseudo-tty
--sig-proxy=true: Proxy all received signals to the process (non-TTY mode only)
-i              : Keep STDIN open even if not attached
```
If you do not specify `-a` then Docker will attach to both stdout and stderr .


## Articles
> [Полное практическое руководство по Docker: с нуля до кластера на AWS](https://habr.com/ru/post/310460/)
