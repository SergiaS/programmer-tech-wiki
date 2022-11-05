# HTTP

> Механізм СЕСІЇ + КУКІ має більше переваг, але JWT використовують коли це виправдано, наприклад при мікросервірній архітектурі.

> JWT токени прийнято передавати під ключем `Authorization` зі словом **Bearer**:
> ```txt
> Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJpc3MiOiJzcHJpbmctc2VjdXJpdHktd2l0aC1qd3QiLCJleHAiOjE2NjQ4MDQzOTAsImlhdCI6MTY2NDgwMDc5MCwidXNlcm5hbWUiOiJ1c2VyMTAwIn0.1pP_8hbe60QQpF0_zeds5EYwRvY1Ildw53AMsx8Q_NM
> ```