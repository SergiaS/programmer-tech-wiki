# Postgres

## Indexes
* [Topjava - Сравнение времени выполнения для разных индексов](https://github.com/JavaWebinar/topjava/blob/doc/doc/meals_index.md)
* [Оптимизация запросов. Основы EXPLAIN в PostgreSQL](https://habr.com/ru/post/203320/)
* [Документация Postgres: индексы](https://postgrespro.ru/docs/postgresql/9.6/indexes.html)


> Индексы ускоряют операции чтения, но замедляют вставку и удаление.


## JOIN
Пример добавления столбца с ролями (`user_roles`) после данных с таблицы `users`, без объединения (`GROUP BY`):
```postgresql
SELECT *
FROM users u
RIGHT JOIN user_roles ur ON u.id = ur.user_id
```
