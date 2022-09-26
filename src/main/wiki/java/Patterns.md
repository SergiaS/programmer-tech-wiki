# Patterns
Шаблоны проектирования / паттерны 


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
Это представление вида - то что будет возвращаться на веб слой (frontend).
Сам слой `TO` работает со слоем `DAO` или контроллером.

### Синонимы
DTO, TO

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
