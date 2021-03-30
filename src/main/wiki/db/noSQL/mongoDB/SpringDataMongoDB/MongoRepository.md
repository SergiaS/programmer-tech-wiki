# MongoRepository<T, ID>
`T` - модель.
`ID` - тип получаемого ид.

<details>
<summary><b>Open Menu</b></summary>

- [Paging and Sorting](#paging-and-sorting)
  * [Example](#-1-example)
- [How to create methods that can fire out custom queries?](#how-to-create-methods-that-can-fire-out-custom-queries)
  * [Query by Example Executor](#-1-query-by-example-executor)
  * [Query by Method Names](#-2-query-by-method-names)
  * [Query by Based on JSON](#-3-query-by-based-on-json)

</details>

# Paging and Sorting:
## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://youtu.be/Alh03DoBo3M?t=1447">Example</a>

```java
// controller
@GetMapping("/page")
public Map<String, Object> getAllEmployeeInPage(
        @RequestParam(name = "pageno", defaultValue = "0") int pageNo,
        @RequestParam(name = "pagesize", defaultValue = "2") int pageSize,
        @RequestParam(name = "sortby", defaultValue = "id") String sortBy) {
    return employeeService.getAllEmployeeInPage(pageNo, pageSize, sortBy);
}

// service
public Map<String, Object> getAllEmployeeInPage(int pageNo, int pageSize, String sortBy) {
    Map<String, Object> response = new HashMap<>();

    Sort sort = Sort.by(sortBy);

    Pageable page = PageRequest.of(pageNo, pageSize, sort);
    Page<Employee> employeePage = employeeRepository.findAll(page);
    response.put("data", employeePage.getContent());
    response.put("Total No of page", employeePage.getTotalPages());
    response.put("Total No of elements", employeePage.getTotalElements());
    response.put("Current page No.", employeePage.getNumber());

    return response;
}

// repository
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
```

# How to create methods that can fire out custom queries?
Поскольку `MongoRepository<T, ID>` это интерфейс в котором нельзя написать реализацию методов с различными запросами, существует 3 способа как написать запросы:

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://youtu.be/Alh03DoBo3M?t=2087">Query by Example Executor</a>
This is interface that allow query execution by Example. You can use method of MongoRepository - `findAll(Example<S> example)`.

> **ВАЖНО! Модель НЕ должна содержать примитивы!**
> 
Другими словами, будет создан запрос по примеру модели, в теле запроса будет JSON со строк-ой/-ами из модели, а в теле ответа - объекты с таким полем:
```json
// request
{
    "firstName": "Auqa"
}

// response
[
    {
        "id": "6062f21db3edcd61cdb75ba2",
        "firstName": "Auqa",
        "lastName": "Man",
        "address": {
            "line1": "Sea",
            "line2": "water",
            "city": "many water",
            "state": "gazer",
            "zipCode": 5122
        },
        "salary": 7777.0
    }
    /* and another objects with that field */
]
```
Пример реализации:
```java
// controller
@GetMapping("/example")
public List<Employee> getAllByExample(@RequestBody Employee emp) {
    return employeeService.getAllByExample(emp);
}

// service
public List<Employee> getAllByExample(Employee emp) {
    Example<Employee> e = Example.of(emp);
    return employeeRepository.findAll(e);
}

// repository
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
```
Пример с матчером `ExampleMatcher` - поиск на совпадение в конце значения типа `String`:
```java
// service
public List<Employee> getAllByExample(Employee emp) {
    ExampleMatcher matcher = ExampleMatcher.matchingAny()
            .withMatcher("firstName",
                    ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.ENDING));
    Example<Employee> e = Example.of(emp, matcher);
    return employeeRepository.findAll(e);
}
```
## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://youtu.be/Alh03DoBo3M?t=2500">Query by Method Names</a>
This approach use three things:
   * 1) Prefix (findBy, readBt, getBy...); 
   * 2) Property expression; 
   * 3) Keywords (AND, OR, LESSTHAN, StartingWith...).
> По сути принцип работы как у волшебных методов Spring Data JPA. Этим способом можно создать базовые запросы, но не очень сложные!
> 
> Query by method name [Supported keywords for query methods](https://docs.spring.io/spring-data/mongodb/docs/1.2.0.RELEASE/reference/html/mongo.repositories.html)
```java
// controller
@GetMapping("/firstname") // http://localhost:8080/employee/firstname?firstname=Bat
public List<Employee> getAllByFirstName(@RequestParam(name = "firstname") String firstName) {
    return employeeService.getAllByFirstName(firstName);
}

// service
public List<Employee> getAllByFirstName(String firstName) {
    return employeeRepository.findByFirstName(firstName);
}

// repository
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findByFirstName(String firstName);
}
```
Example, if we need create a query for search inside another model - `findByAddressZipCode()`:
* In phase 1, if will go to entity class Employee and look for "AddressZip". It won't find and then go to phase 2.
* In phase 2 entity class Employee will look for Address. It found Address, then inside Address will look for "ZipCode".
```java
// model Employee
class Employee{
    String firstName;
    String lastName;
    Address address; // <- search inside this model
    Float salary;
}

// model Address
class Address{
    String line1;
    String line2;
    String city;
    String state;
    int zipCode;
}

// repository
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findByAddressZipCode(int zipcode);
}
```

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. <a href="https://youtu.be/Alh03DoBo3M?t=2954">Query by Based on JSON</a>
Here we need to use `@Query` annotation. Inside of the `value` field you need to write JSON-query.

Пример запроса в котором ищется `salary` больше чем:
```java
// repository
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
   @Query(value = "{'salary': {$gte:?0}}")
   List<Employee> getAllBySalaryGreaterThan(Float salary);
}
```
Дополнительно можно указать в аннотации `@Query` в поле `fields` какие поля заполнять, где 0 = `false` (будет `null`), 1 = `true`. 
```java
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
   @Query(value = "{'salary': {$gte:?0}}", fields = "{'firstName':1, 'id':0}")
   List<Employee> getAllBySalaryGreaterThan(Float salary);
}
```
Если необходимо скрыть поля с null, в Spring Boot можно добавить фичю от Jackson в `application.properties`:
```properties
spring.jackson.default-property-inclusion=non_null
```
