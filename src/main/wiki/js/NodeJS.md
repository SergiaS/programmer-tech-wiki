# NodeJS
Node Package Manager (npm) comes when you install [NodeJS](nodejs.org).

> How to update npm on Windows (not LTS)?
> Open your powershell in administrator mode and run following command:
> ```commandline
> npm install -g npm
> ```

**Node JS** це не мова програмування, а платформа, що побудована на базі движка V8 і мови C++. 
Кожен браузер має свій движок, котрий виконує код, наприклад у Google Chrome це був движок V8, у FireFox - Spider Monkey, у IE - Chakra... 
Сам по собі движок розуміє код (синтаксис JS), котрий виконує і переводить у користувацькі дії.

**Node JS** як раз представляє собою движок, що дістали з браузера, котрий можна інтегрувати куди завгодно і тим самим
ви отримуєте можливість роботи з **JS** за межами браузера.



## `npm` commands
[Do you know all the popular NPM commands?](https://dev.to/knowankit/do-you-know-all-the-popular-npm-commands-22ac?fbclid=IwAR1OpqQJ4G_A4eX1AdfFI1IXGm1PrITBp5IWv2VwrbAZhegiE6cvy2bP-aA)

* `npm init -y` - ініціалізує проєкт NodeJS та генерує `package.json`.

* `i` or `install` - it will install for the dependencies for your project.
* `-S` is the same as `--save`.

* `npm run devserver` - a development server that provides live reloading. 
  To stop server hit keywords `CTRL+BREAK` twice.

  **This should be used for development only.** 
  [Webpack module](https://www.npmjs.com/package/webpack-dev-server?activeTab=readme).

* `npm -S i axios` - this command will install axios (promise-based HTTP Client).

* `cat package.json` - читає у консолі файл зі стеком технологій для поточного проекту.




## Errors
- When you use frontend with backend, and you get an error in your brother like: 
  `Access to XMLHttpRequest at 'http://localhost:8080/api/v1/user-profile' from origin 'http://localhost:3000' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.`
... You need to add to your backend REST-controller annotation `@CrossOrigin("*")`. 



## Packages

### [co package](https://www.npmjs.com/package/co)
To use package you need to import it:
```js
import co from "co";
```

***

### create-react-app package
Встановлює React.

> `create-react-app` пропонує стандартну нормалізацію стилів: 
> 
> To start using it, add `@import-normalize;` anywhere in your CSS file(s).
> * [Adding a CSS Reset](https://create-react-app.dev/docs/adding-css-reset/)


***

### [react-icons](https://react-icons.github.io/react-icons/) package
Велика база іконок.

***

### [react-select](https://react-select.com/home) package
Звичайний select важко стилізувати, тому тут допоможе цей пакет.

***


## package.json

***

> Якщо є така помилка:
> Access to **XMLHttpRequest** at `http://localhost:8080/api/v1/students` from origin `http://localhost:3000` has been blocked by CORS policy: 
> No `Access-Control-Allow-Origin` header is present on the requested resource. Роби так:

Файл в якому зберігаються настройки проекту такі як версії фреймворків.

Щоб не писати в своїх скриптах повну адресу свого домена `http://localhost:8080/api/v1/students`
у файлі `package.json` перед останньою дужкою `}` можна додати...
```json
"proxy": "http://localhost:8080"
```
...далі в своїх скриптах вже можна опустити домен і писати `api/v1/students`.


## Як додати React до свого Java проекту?
Повинен бути встановлений **NodeJS**.

1. Переходимо у терміналі до теки `src` та набираємо команду `cd src`.
2. Ініціалізуємо React командою `npx create-react-app frontend` - у src буде створена тека frontend, в якій і буде react.

### How to start app
У головному каталозі __frontend__ необхідно виконати команду `npm start`.

By default, application will lunch at the address `http://localhost:3000/`.

### How to stop app
Use keyword `CTRL+C` to stop NodeJS server.


## Зв'язування серверів FrondEnd з BackEnd
Щоб відповідь генерувалась на React та поверталась на вказану адресу.

**Спосіб зробити це руцями:**

Потрібно згенерувати файли командою `npm run build`.

Далі з'явиться тека `build` - її потрібно перенести до своїх ресурсів у теку `static`.

Спосіб, щоб згенеровані файли в React переправлялися автоматично в потрібний каталог - використовуй плагін Maven `frontend-maven-plugin` з налаштуваннями:
```xml
<profiles>
  <profile>
    <id>build-frontend</id>
    <activation>
      <activeByDefault>true</activeByDefault>
    </activation>
    <build>
      <plugins>
        <plugin>
          <groupId>com.github.eirslett</groupId>
          <artifactId>frontend-maven-plugin</artifactId>
          <version>1.11.2</version>
          <configuration>
            <nodeVersion>v4.6.0</nodeVersion>
            <workingDirectory>src/frontend</workingDirectory>
          </configuration>
          <executions>
            <execution>
              <id>install node and npm</id>
              <goals>
                <goal>install-node-and-npm</goal>
              </goals>
              <configuration>
                <nodeVersion>v16.13.2</nodeVersion>
                <npmVersion>8.8.0</npmVersion>
              </configuration>
            </execution>
            <execution>
              <id>npm install</id>
              <goals>
                <goal>npm</goal>
              </goals>
              <configuration>
                <arguments>install</arguments>
              </configuration>
            </execution>
            <execution>
              <id>npm run build</id>
              <goals>
                <goal>npm</goal>
              </goals>
              <configuration>
                <arguments>run build</arguments>
              </configuration>
            </execution>
          </executions>
        </plugin>
        <plugin>
          <artifactId>maven-resources-plugin</artifactId>
          <version>3.2.0</version>
          <executions>
            <execution>
              <id>copy-build-folder</id>
              <phase>process-classes</phase>
              <goals>
                <goal>copy-resources</goal>
              </goals>
              <configuration>
                <resources>
                  <resource>
                    <directory>src/frontend/build</directory>
                  </resource>
                </resources>
                <outputDirectory>${basedir}/target/classes/static</outputDirectory>
              </configuration>
            </execution>
          </executions>
        </plugin>
      </plugins>
    </build>
  </profile>
</profiles>
```

Що робить плагін на прикладі консолі пошагово:
* переходить в потрібну теку `cd src/frontend`;
* встановить всі необхідні залежності командою `npm install`;
* збере все до купи `npm run build`;

Плагін запускається командою в терміналі `mvn clean install`.

Усі сконфігуровані файли з **React** потраплять до теки `target` - вміст якої відображається в браузері при старті сервера Spring'a.

Далі запускаємо наш `.jar` командою `java -jar ./target/{your-project-name}-0.0.1-SNAPSHOT.jar`.
Далі наш джарнік стартує.


## [package gh-pages](https://www.npmjs.com/package/gh-pages)
`gh-pages` - це залежність для розробки, вона буде закидувати наші веб-файли на певну гілку нашого репозиторія.

Для встановлення залежності потрібно:

- Спочатку потрібно встановити залежність. Пишемо у терміналі команду `npm install gh-pages --save-dev`, 
  де `--save-dev` - ключ, щоб залежність не збиралася до підсумкового проєкту.
- Додати налаштування у файл `package.json`:
  - додати адресу своєї сторінки на GitHub-Pages перед `"dependencies"`, наприклад:
    ```json
    "homepage": "https://sergias.github.io/react-movies",
    ```
  - додати у кінець категорії `"scripts"` команди:
    ```json
    "predeploy": "npm run build",
    "deploy": "gh-pages -d build",
    ```
    де `gh-pages` - це ім'я гілки Git на яку будуть завантажуватися файли.
- Тепер потрібно лише набрати команду `npm run build`, і для всіх наших веб-файлів автоматично зробиться `commit` і `push`.
- Щоб **GitHub-Pages** працювало, треба її налаштувати (як мінімум зробити репозиторію публічний доступ).

> Якщо є проблема з оновленням/пушем, використай команду `gh-pages -d build` і спробуй ще раз.
> 
> Або спробуй команду `git push origin gh-pages`.
