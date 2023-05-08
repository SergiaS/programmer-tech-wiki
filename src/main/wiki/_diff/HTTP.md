# HTTP

> Механізм СЕСІЇ + КУКІ має більше переваг, але JWT використовують коли це виправдано, наприклад при мікросервірній архітектурі.

### JWT
* [JWT.io](https://jwt.io/)
* [All keys generator](https://www.allkeysgenerator.com/)
* [Що повинно бути у токена (payload) - Registered Claim Names](https://www.rfc-editor.org/rfc/rfc7519#section-4.1)

> Do not put secret information in the payload or header elements of a JWT unless it is encrypted!

> **Access Token:**
> * Sent as JSON;
> * Client stores in memory;
> * Do NOT store in local storage or cookie.
> *
> * Issued at Authorization;
> * Client uses for API Access until expires;
> * Verified with Middleware;
> * New token issued at Refresh request (by Refresh Token).
>
> **Refresh Token:**
> * Sent as httpOnly cookie;
> * Not accessible via JavaScript;
> * Must have expiry at some point.
> *
> * Issued at Authorization;
> * Client uses to request new Access Token;
> * Verified with endpoint & database;
> * Must be allowed tp expire or logout.


> For JWT tokens required minimum 256-bit encrypted key

JWT токени (access-token) прийнято передавати під ключем `Authorization` зі словом **Bearer**:
```txt
Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJpc3MiOiJzcHJpbmctc2VjdXJpdHktd2l0aC1qd3QiLCJleHAiOjE2NjQ4MDQzOTAsImlhdCI6MTY2NDgwMDc5MCwidXNlcm5hbWUiOiJ1c2VyMTAwIn0.1pP_8hbe60QQpF0_zeds5EYwRvY1Ildw53AMsx8Q_NM
```

> **Note**<br>
> 
> This notes for React app:
> * Store access token in your app state.
> * Don't put it in local storage or in a cookie, that can be accessed by JS.
> * Store refresh token in a http only secure cookie, that is not accessed by JS.


### Як згенерувати ключі RSA
> **Для чого потрібні private та public ключі, і що вони роблять?**
>
> JWT складається з трьох частин: заголовку (header), корисної навантаження (payload) та підпису (signature). 
> Заголовок містить інформацію про формат токена та алгоритм, яким був підписаний токен. 
> Корисна навантаження містить інформацію, яку ви хочете передати. 
> Підпис - це електронний підпис, який забезпечує цілісність та автентичність токена.
>
> Для генерації підпису використовуються ключі. 
> Якщо використовуються симетричні ключі, то один ключ використовується для створення підпису, а інший ключ - для його перевірки. 
> У випадку асиметричних ключів використовується приватний ключ для підписування токена, а публічний ключ використовується для перевірки підпису.
>
> Отже, приватний ключ забезпечує конфіденційність, тобто інформація зашифрована тільки знайомим та довіреним особам. 
> Публічний ключ використовується для перевірки підпису токена. 
> Якщо цифровий підпис був створений приватним ключем, то він може бути перевірений тільки з використанням відповідного публічного ключа. 
> Якщо підпис не співпадає з тим, що було створено з використанням публічного ключа, то токен вважається недійсним.
>
> Отже, використання приватного та публічного ключа дозволяє забезпечити конфіденційність та цілісність передачі даних при використанні JWT.
>
> Джерело: **ChatGPT**

***

* SOURCE: Dan Vega, [Spring Security JWT](https://www.danvega.dev/blog/2022/09/06/spring-security-jwt/)

When you use the JWT customizer you need to provide one of the following:

* Supply a Jwk Set Uri via OAuth2ResourceServerConfigurer.JwtConfigurer.jwkSetUri
* Supply a JwtDecoder instance via OAuth2ResourceServerConfigurer.JwtConfigurer.decoder
* Expose a JwtDecoder bean.


#### SIGNING JSON WEB TOKENS
The next step is to create a new JwtDecoder bean, but I think we need to talk about what we are going to do here. 
As you learned earlier there are 3 parts to the JWT, the header, payload, and signature. 
The signature is created using by encrypting the header + payload and a secret (or private key).

A JWT can be encrypted using either a symmetric key (shared secret) or asymmetric keys (the private key of a private-public pair).

* **Symmetric key**: 
The same key is used for both encryption (when the JWT is created) and decryption (MobileTogether Server uses the key to verify the JWT). 
The symmetric key—also known as the shared secret—is stored as a setting in MobileTogether Server. 
See Symmetric Key: Shared Secret for details of working with symmetric keys.
* **Asymmetric keys**: 
Different keys are used for encryption (private key) and decryption (public key). 
The public key is stored as a setting in MobileTogether Server so that the JWT can be verified. 
For information about using asymmetric encryption for JWTs, see Asymmetric Keys: Public Key.

There are pros/cons to each, but it is generally recommended that you use Asymmetric keys so that is the approach you will take here.


#### RSA PUBLIC & PRIVATE KEYS
You are going to create a public/private key pair. 
This is something that you can do via code, but I think it might make more sense if you do it manually here. 
I would create these in a new folder under `/src/main/resources/certs`. 
I am going to use OpenSSL which is installed by default on macOS, but you should be able to install it on whatever OS you’re using.

Normally you could get away with running the first 2 commands. 
The reason for the 3rd command is that the private key needs to be in PEM-encoded PKCS#8 format. 
Switch to that certs directory and run each of the following commands separately.

```properties
# create rsa key pair
openssl genrsa -out keypair.pem 2048

# extract public key from the private key
openssl rsa -in keypair.pem -pubout -out public.pem

# create private key in PKCS#8 format
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```
If everything runs without error and you have both a public and private key you can delete keypair.pem