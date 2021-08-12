# NodeJS

### How to start app
В главном каталоге __frontend__ необходимо выполнить команду `npm start`.

By default application will lunch at the address `http://localhost:3000/`. 

### How to stop app
Use keyword `CTRL+C` to stop NodeJS server.


## Commands
`i` means __install__.


`npm -S i axios` - this command will install axios (promise-based HTTP Client).

`cat package.json` - читает в консоли файл со стеком технологий для текущего проекта.

## Errors
- When you use frontend with backend, and you get an error in your brother like: 
  `Access to XMLHttpRequest at 'http://localhost:8080/api/v1/user-profile' from origin 'http://localhost:3000' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.`
... You need to add to your backend REST-controller annotation `@CrossOrigin("*")`. 
