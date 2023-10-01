# Patterns
Шаблони проєктування / патерни.

[Типи патернів](https://refactoring.guru/uk/design-patterns/catalog):
* **Породжувальні** - Відповідають за зручне та безпечне створення нових об'єктів або навіть цілих сімейств об'єктів.
* **Структурні** - Відповідають за побудову зручних в підтримці ієрархій класів.
* **Поведінкові** - Вирішують завдання ефективної та безпечної взаємодії між об'єктами програми.

***

> Патерн **Repository** надає більшу абстракцію аніж патерн **DAO**.
> 
> **Repository** зазвичай більш високорівневий, ближчий до бізнес логіки (не пишемо SQL запити, працюємо з сутностями).<br>
> **DAO** зазвичай більш низькорівневий, ближче до БД (можемо писати SQL запити).
>
> В складних додатках можуть бути і **DAO** і **Repository**.
> 
> **DAO** - для більш складних маніпуляцій з даними і з БД, де зазвичай потрібно писати самому **SQL**.<br>
> **Repository** - для стандартних операцій з даними (наприклад, CRUD).


***

## Builder - API chaining
* [The mighty Builder Pattern in OOP](https://medium.com/@mohithmarisetti_58912/the-mighty-builder-pattern-in-object-oriented-programming-cbd480675487)
* [Recursive generics in OOP](https://medium.com/@mohithmarisetti_58912/recursive-generics-in-object-oriented-programming-bceb42df047f)

***


## DTO (Data-Transfer-Object)
* [Гарний приклад + код](https://youtu.be/OAheRYUC6mc)

> Синоніми: DTO, TO

> Это представление вида - то что будет возвращаться на веб слой (frontend).
> Сам слой `TO` работает со слоем `DAO` или контроллером.
> 
> Source: TopJava

З ним працюються на рівні контролерів, нижче не прокидують!

Для запитів приймається методом контролера і конвертується у об'єкт моделі.

Для відповідей конвертується з об'єкта моделі і відправляється клієнту.

### Навіщо потрібен DTO?
Модель - бізнес логіка.
DTO - об'єкт для передачі даних.

**Модель може відрізнятися від того, що приходить від клієнта при створенні або того, що ми хочемо відправляти клієнту (різний набір полів).**

Модель і DTO можуть повністю співпадати - таке буває часто.
Але DTO все одно використовується навідь у такому випадку, томущо це полегшуєподальші зміни. 
Це хороший стиль програмування - розділяти модель та об'єкт для передачі даних.


### Что делают хорошие DTO?
Очень важно понимать, что вы не обязаны использовать DTO.
Это прежде всего паттерн и ваш код может работать отлично и без него.

- Если вы используете одно представление данных на оба слоя, вы вполне можете использовать ваши сущности в качестве DTO.



***


## DAO (Data Access Object)
Шаблон проектирования для работы с базой данных.

Представьте, что вы пишете Java класс, который позволяет вам получить доступ к таблице пользователей в вашей базе данных.
Вы бы назвали эти классы DAO (объект доступа к данным) или репозитории.

> **Note**<br>
> [GitHub](https://github.com/SergiaS/t_spring/tree/spring_by_alishev)

***


## Combinator pattern
Отлично подойдёт для валидации в стиле `Function<T, R>`.


## Adapter
Когда объект не наследуется, а внедряется как свойство:
```java
@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    User getByEmail(String email);

    @EntityGraph(attributePaths = {"meals", "roles"})
    @Query("SELECT u FROM User u WHERE u.id=?1")
    User getWithMeals(int id);
}
```
```java
@Repository
public class DataJpaUserRepository implements UserRepository {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    private final CrudUserRepository crudRepository;

    public DataJpaUserRepository(CrudUserRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public User save(User user) {
        return crudRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }
    
    // other code
```
