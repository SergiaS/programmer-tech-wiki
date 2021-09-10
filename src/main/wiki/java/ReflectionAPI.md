# Reflection API

## Methods

> Примечание: `getFields()` и `getDeclaredFields()` не возвращают поля класса-родителя!
> 
* `getFields()` - Метод возвращает массив со всеми ДОСТУПНЫМИ полями класса.
* `getDeclaredFields()` - Метод возвращает массив полей класса, но теперь и `private` и `protected`.
* `setAccessible(true)` - Т.к. поле не было публичным (public) следует дать доступ для работы с ним. 
  Метод `setAccessible(true)` разрешает нам дальнейшую работу.
