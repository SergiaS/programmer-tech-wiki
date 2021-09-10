# AWS
AWS (Amazon Web Services).

First, we need to declare [AWS SDK Maven-dependency](https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk) in your project:
```xml
<dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk</artifactId>
    <version>1.12.62</version>
</dependency>
```

## AWSCLIV2
AWSCLIV2 (Amazon Web Service Command Line Interface Version 2).

* [Install or Update AWSCLIV2](https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2.html)
* [Set environment variables](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-envvars.html)


## EC2
EC2 (Elastic Cloud Compute).

### Security Group
[Security groups control the network traffic to our EC2 instances.](https://www.baeldung.com/ec2-java#1-creating-a-security-group)

Security groups don't allow any network traffic by default.
You have to configure your security group to allow traffic.

### Key pair
When launching an EC2 instance, we need to specify a key pair.

### How to delete instance
All you need to do it's just select the instance that you want to delete and choose action __TERMINATE__.
That's all. Instance will be removed in a couple of hours.

### How to connect to your instance
Во-первых, у тебя должен быть файл формата `.pem` - это ключи, без которых к инстансу не подключишся.

Далее в терминале (cmd, Bash, Power Shell...) переходим в директорию в которой лежит `.pem` файл, и запускаем команду со своим Public DNS, например: 
```shell
ssh -i "ec2-key-pair3.pem" ec2-user@ec2-3-66-217-181.eu-central-1.compute.amazonaws.com
```
Всё, мы на сервере:)

<hr>

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

<hr>

Если нужно скорировать файл с инстанса на локальный ПК:
```shell
scp -i ./ec2KP.pem ec2-user@3.120.152.58:/home/ec2-user/somefile.txt ~/Desktop/tr-files/somefile.txt
```

<hr>

Если нужно скопировать все файлы (без самой папки) с инстанса на локальный ПК:
```shell
scp -i ./ec2KP.pem -r ec2-user@3.120.152.58:/home/ec2-user/tr-files/* ~/Desktop/tr-files
```
Если вместо `/*` будет `*`, тогда будут скачаны все файлы с папкой включая скрытые.
