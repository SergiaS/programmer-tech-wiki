# SQL
* [Объяснение SQL объединений JOIN: LEFT/RIGHT/INNER/OUTER](http://www.skillz.ru/dev/php/article-Obyasnenie_SQL_obedinenii_JOIN_INNER_OUTER.html)

Стандарты SQL определяют 4 уровня изолированности:
* READ_UNCOMMITTED
* READ_COMMITTED
* REPEATABLE_READ
* SERIALIZABLE

## DISTINCT
Устраняет дублирующие записи - будут отображены только уникальные записи.
```postgresql
SELECT DISTINCT u
FROM users u
RIGHT JOIN user_roles ur ON u.id = ur.user_id
WHERE u.id=100001
```


## Декартово произведения (Декартово произведение множеств)
Проблемой Декартова произведения: 
для каждого уникального пользователя количество записей в результирующей таблице будет повторяться столько раз, 
сколько у него было ролей.

Чтобы этого избежать, отдельным запросом получим из базы все роли, и сгруппируем их в `Map` по `userId`, 
где ключом будет являться `userId`, а значением — набор ролей пользователя. 
После чего пройдемся по всем пользователям, загруженным из базы, и установим каждому его роли.


## User with his Roles
ЗАДАЧА: достать юзера с его ролями. Дано две таблицы `user` и `user_roles`.
У одного рользователя может быть пара ролей.
Чтобы рез-т не выводился отдельно для каждой роли на юзера, нужно использовать агрегированную функцию
и сгрупировать по `id`:

```postgresql
SELECT u.*, string_agg(r.role, ',') AS roles
FROM users u
  JOIN user_roles r ON u.id=r.user_id
GROUP BY u.id
```


