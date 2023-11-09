# Comparation
Articles about `Comparable` and `Comparator` interfaces.

Эти интерфейсы нужны для сравнения объектов в коллекциях или массивах.

> Отличие `Comparable<T>` от `Comparator<T>` в том, что `Comparable` содержит в себе только один абстрактный метод `compareTo`,
> а `Comparator` ещё и статические и дефолтные. Если просто имплементировать и реализовать их абстрактные методы, разницы особой нет,
> но с помощью `Comparator` можно создавать объекты сортировки и добавлять их в разные методы.

> В основном данный интерфейс `Comparable` используют для реализации сортировки объекта/модели по `id`,
> а все остальные параметры сортируют через дополнительные `Comparator`'ы.



## Comparable
Переводится как "возможный для сравнения" или "способный быть сравнимым", относится к объекту.

Интерфейс `Comparable<T>` используется для сравнения объектов, используя естественный порядок.

Интерфейс с одним методом - `int compareTo(T o)`, который необходимо имплементировать и реализовать перед использованием.
Позволяет сравнивать текущий объект `this` с объектом переданным в параметре `o`.

> Если текущий объект больше объекта в параметре - нужно вернуть положительное число, если меньше - отрицательное число, а если равны - вернуть `0`.

Существует много реализаций, вот самые простые: 
```java
// Вариант 1 - сравнение по id:
@Override
public int compareTo(Employee anotherEmp) {
    if (this.id == anotherEmp.id) return 0;
    else if (this.id < anotherEmp.id) return -1;
    else return 1;
}
```
```java
// Вариант 2:
return this.id - anotherEmp.id;
```
```java
// Вариант 3:
return Integer.compare(this.id, anotherEmp.id);
```
```java
// Вариант 4 - сравнение по имени, потом по фамилии:
int res = this.name.compareTo(anotherEmp.name);
if (res == 0)
    res = this.surname.compareTo(anotherEmp.surname);
return res;
```


## Comparator
Интерфейс `Comparator<T>` используется для сравнения объектов, используя НЕ естественный порядок.

Это функциональный интерфейс, его можно имплементировать и реализовать один метод `int compare(T o1, T o2)`, или реализовать как лямбду.

> В методе `sort` высший приоритет имеет `Comparator`, даже если в объекте метод `compareTo` перезаписан.

```java
List<Employee> list = new ArrayList<>();
Employee emp1 = new Employee(100, "Zuck", "Vito", 2223);
Employee emp2 = new Employee(15, "Ivan", "Petrov", 6542);
Employee emp3 = new Employee(123, "Chem", "Sidorov", 8542);
list.add(emp1);
list.add(emp2);
list.add(emp3);
System.out.println("Before:\n" + list);
Collections.sort(list, new SalaryComparator()); // вставляем нужный компаратор 
System.out.println("After:\n" + list);
```
```java
// Компаратор по полю id
class IdComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee emp1, Employee emp2) {
        if (emp1.id == emp2.id) return 0;
        else if (emp1.id < emp2.id) return -1;
        else return 1;
    }
}
```
```java
// Компаратор по полю name
class NameComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee emp1, Employee emp2) {
        return emp1.name.compareTo(emp2.name);
    }
}
```
```java
// Компаратор по полю salary
class SalaryComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee emp1, Employee emp2) {
        return emp1.salary - emp2.salary;
    }
}
```


