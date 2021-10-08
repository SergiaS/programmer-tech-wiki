# NodeJS
Node Package Manager (npm) comes when you install [NodeJS](nodejs.org).

### How to start app
В главном каталоге __frontend__ необходимо выполнить команду `npm start`.

By default application will lunch at the address `http://localhost:3000/`. 

### How to stop app
Use keyword `CTRL+C` to stop NodeJS server.


## `npm` commands

[Do you know all the popular NPM commands?](https://dev.to/knowankit/do-you-know-all-the-popular-npm-commands-22ac?fbclid=IwAR1OpqQJ4G_A4eX1AdfFI1IXGm1PrITBp5IWv2VwrbAZhegiE6cvy2bP-aA)


* `i` or `install` - it will install for the dependencies for your project.

* `npm run devserver` - a development server that provides live reloading. 
  To stop server hit keywords `CTRL+BREAK` twice.

  **This should be used for development only.** 
  [Webpack module](https://www.npmjs.com/package/webpack-dev-server?activeTab=readme).

* `npm -S i axios` - this command will install axios (promise-based HTTP Client).

* `cat package.json` - читает в консоли файл со стеком технологий для текущего проекта.

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

