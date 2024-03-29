# Postgres
* [YouTube - Базы данных, Наиль Алишев](https://www.youtube.com/playlist?list=PLcBbiWbF2bIzfWAcWvu5M56hk25vJrRFK)

> Якщо потрібно використати у назві слово яке вже зарезервоване - **PostgreSQL** не дозволить це зробити. 
> Це слово слід обернути у подвійні лапки - `"Order"`.

> Тип змінної `SERIAL` (застарілий, хоча йдосі використовується) це синтетичний цукор, при використанні котрої буде застосований `sequence`.
> Цей створенний `sequence` буде прив'язаний до цієї змінної, і по звернені буде збільшуватися на 1 (як `AUTOINCREMENT` в **MySQL**).
>
> Замість `SERIAL` використовують інший синтаксис -`int GENERATED BY DEFAULT AS IDENTITY`:
> ```sql
> -- не використовуюnь після Postgres 10
> create table Person (
>     id SERIAL,
>     name varchar
> );
> ```
> ```sql
> -- новий синтаксис на заміну SERIAL
> create table Person2 (
>     id int GENERATED BY DEFAULT AS IDENTITY,
>     name varchar
> );
> ```

> Тестові дані для практики - [Northwind database for Postgres](https://github.com/pthom/northwind_psql)
> 
> Достатньо створити БД через **pgAdmin** -> завантажити файл `northwind.sql` -> відкрити його в **pgAdmin** та виконати.  


## INDEX
* [Topjava - Сравнение времени выполнения для разных индексов](https://github.com/JavaWebinar/topjava/blob/doc/doc/meals_index.md)
* [Оптимизация запросов. Основы EXPLAIN в PostgreSQL](https://habr.com/ru/post/203320/)
* [Документация Postgres: индексы](https://postgrespro.ru/docs/postgresql/9.6/indexes.html)

> **Note**<br>
> Індекси прискорюють операції читання, але уповільнюють вставку та видалення.
>
> Працюють на основі бінарного пошуку.

Плюси індексів:
* при вибірці пошук буде працювати набагато швидше.

Мінуси індексів:
* потребують місця;
* уповільнюють вставку та видалення оскільки індекс треба підтримувати у відсортованому стані.

> **Warning**<br>
> Рекомендується використовувати індекси лише для тих колонок, по котрим буде проводитися пошук найчастіше.
>
> В іншому випадку, якщо буде проводитися вставка або видалення досить часто - індекси будуть працювати повільніше, ніж без них.

> **Note**<br>
> Колонки в **PostgreSQL** за замовчуванням індексуються.


## JOIN
Пример добавления столбца с ролями (`user_roles`) после данных с таблицы `users`, без объединения (`GROUP BY`):
```sql
SELECT *
FROM users u
         RIGHT JOIN user_roles ur ON u.id = ur.user_id
```
***
Приклад JOIN з відношенням Many-to-Many з проміжною таблицею:
```sql
SELECT actor.name, movie.name
FROM actor
         JOIN actor_movie
              ON actor.actor_id = actor_movie.actor_id
         JOIN movie
              ON actor_movie.movie_id = movie.movie_id;
```

***

Цей код прихначений для тестів JOIN:

> <details>
> <summary>Код створення БД та популяції даних</summary>
>
> ```sql
> DROP TABLE IF EXISTS employees;
> DROP TABLE IF EXISTS departments;
> 
> DROP SEQUENCE IF EXISTS departments_department_id_seq;
> DROP SEQUENCE IF EXISTS employees_employee_id_seq;
> 
> -- Створення таблиці departments
> CREATE TABLE departments (
>   department_id SERIAL PRIMARY KEY,
>   department_name VARCHAR(255) NOT NULL
> );
> 
> -- Додавання даних до таблиці departments - #5
> INSERT INTO departments (department_name) VALUES
>   ('HR'),
>   ('IT'),
>   ('Finance'),
>   ('Marketing'),
>   ('Sales');
> 
> -- Створення таблиці employees
> CREATE TABLE employees (
>   employee_id SERIAL PRIMARY KEY,
>   first_name VARCHAR(255) NOT NULL,
>   last_name VARCHAR(255) NOT NULL,
>   department_id INT,
>   hire_date DATE
> );
> 
> -- Додавання даних до таблиці employees - #20
> INSERT INTO employees (first_name, last_name, department_id, hire_date) VALUES
>   ('John', 'Doe', 2, '2021-01-15'),
>   ('Alice', 'Smith', NULL, '2019-05-20'),
>   ('Bob', 'Johnson', 3, '2022-11-30'),
>   ('Eva', 'Wilson', 4, NULL),
>   ('Michael', 'Brown', 5, '2023-02-18'),
>   ('Laura', 'Miller', 2, '2018-09-05'),
>   ('Sam', 'Davis', NULL, '2021-12-10'),
>   ('Olivia', 'Martinez', 3, '2017-04-22'),
>   ('William', 'Lee', 4, '2016-08-07'),
>   ('Sophia', 'Clark', 2, NULL),
>   ('Daniel', 'Hall', 2, '2019-10-28'),
>   ('Emma', 'Harris', 1, '2018-06-03'),
>   ('Joseph', 'White', NULL, NULL),
>   ('Mia', 'Turner', 4, '2022-03-08'),
>   ('James', 'Moore', 3, '2017-09-16'),
>   ('Chloe', 'Jackson', 1, '2021-08-22'),
>   ('William', 'Evans', 2, NULL),
>   ('Liam', 'Thomas', NULL, '2019-01-02'),
>   ('Charlotte', 'Walker', 4, '2015-12-04'),
>   ('Benjamin', 'Anderson', NULL, '2018-07-29');
> ```
> </details>


## CASCADE
* [GitHub - Приклади](https://github.com/SergiaS/example_spring/blob/31ef68542bdcdb8fee9b5548ebc97af6b88e11bd/src/main/resources/CASCADE_lesson.sql)


## Examples 
```sql
-- подивитися дані таблиці
SELECT table_name, column_name, data_type
FROM information_schema.columns
WHERE table_name = 'user_roles';
```
```sql
-- часто використовую для тестів
-- спочатку якщо є таблиця або сіквенс - дропнить їх 
-- створить таблицю з автоінкрементом + заповнить даними
DROP TABLE IF EXISTS Person;

DROP SEQUENCE IF EXISTS person_id_seq;

CREATE SEQUENCE person_id_seq START WITH 1;

CREATE TABLE Person(
    id   int PRIMARY KEY DEFAULT nextval('person_id_seq'),
    name varchar(100),
    age  int
);

INSERT INTO Person(name, age) VALUES ('Test person', 20);
```
```sql
-- почати номер SEQUENCE з:
ALTER SEQUENCE student_seq RESTART WITH 1001;
```
```sql
-- очистити таблицю:
TRUNCATE TABLE person;

TRUNCATE TABLE Measurement;
TRUNCATE TABLE Sensor CASCADE;
```
```sql
-- змінити таблицю - додати стовбчик
ALTER TABLE person ADD COLUMN address varchar NOT NULL;
```
```sql
-- додати дані
INSERT INTO person(name, age, email, address)
VALUES ('Bobcat', 23, 'bobcat@gmail.com', 'USA, Chicago, 08973'),
       ('Jack Dilan', 32, 'jdilan@yahoo.com', 'USA, Canzas, 45428'),
       ('Mina Santiago', 25, 'minasantiago@gmail.com', 'Mexico, Mexico, 22252'),
       ('Marta Richards', 46, 'martarichards@yahoo.com', 'USA, Texas, 78945')
;
```
```sql
-- JOIN таблиць Book і Person - отримати людину, котра має книгу з вказаним id
SELECT Person.*
FROM Book
         JOIN Person ON Book.person_id = Person.id
WHERE Book.id = ?
```
```sql
-- змінити таблицю - додати стовпці
ALTER TABLE Person ADD COLUMN date_of_birth DATE;

ALTER TABLE Person ADD COLUMN created_at TIMESTAMP;
```
```sql
-- створюємо автоінкремент:

-- або одразу при створені поля
id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY

-- або через сіквенс:
CREATE SEQUENCE sensor_id_seq START WITH 1;

CREATE TABLE Sensor (
    id   int PRIMARY KEY DEFAULT nextval('sensor_id_seq')
);
```