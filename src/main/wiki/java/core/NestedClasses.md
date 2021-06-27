# Nested Classes
![Nested Classes Schema](https://cdn.javarush.ru/images/article/10ac2dc4-b564-4868-83cf-77792005a358/800.webp)

Сначала создается новый экземпляр `new` класса (не обязательно абстрактного), потом переопределяются методы или создаются новые методы/переменные внутри фигурных скобок `{` `}`.

В примере ниже в анонимном классе создается метод printName и в конце вызывается:
```java
new Solution("The Darkside Hacker") {
    void printName() { // создаем новый метод
        System.out.println(getName());
    }
}.printName(); // вызываем созданный новый метод
```


## [Вложенные внутренние классы - JavaRush](https://javarush.ru/groups/posts/2181-vlozhennihe-vnutrennie-klassih)
1. Объект внутреннего класса не может существовать без объекта «внешнего» класса.
2. У объекта внутреннего класса есть доступ к переменным «внешнего» класса.
3. Объект внутреннего класса нельзя создать в статическом методе «внешнего» класса.
    * Логика здесь та же: статические методы и переменные могут существовать и вызваться даже при отсутствии объекта. 
      Но без объекта «внешнего» класса доступа к внутреннему классу у нас не будет. Явное противоречие! 
      Поэтому наличие статических переменных и методов во внутренних классах запрещено.
4. Внутренний класс не может содержать статические переменные и методы.
5. При создании объекта внутреннего класса важную роль играет его модификатор доступа.
    * Внутренний класс можно обозначить стандартными модификаторами доступа — `public`, `private`, `protected` и `package private`.
      Модификаторы доступа для внутренних классов работают так же, как и для обычных переменных.
      
## [Статические вложенные классы - JavaRush](https://javarush.ru/groups/posts/2183-staticheskie-vlozhennihe-klassih)
В чем же отличие между статическим и нестатическим вложенными классами?
1. Объект статического класса не хранит ссылку на конкретный экземпляр внешнего класса.
    * Объект статического вложенного класса вполне может существовать сам по себе.
    * При этом неважно, какой модификатор доступа имеет статическая переменная во внешнем классе. 
      Даже если это `private`, доступ из статического вложенного класса все равно будет. 
      Это касается не только доступа к статическим переменным, но и к статическим методам.
2. Разный доступ к переменным и методам внешнего класса.
    * Статический вложенный класс может обращаться только к статическим полям внешнего класса.

## [Анонимные классы - JavaRush](https://javarush.ru/groups/posts/2193-anonimnihe-klassih)
> **Анонимный класс не может содержать статические переменные и методы.**

> **У анонимных внутренних классов не может быть конструктора.**

Анонимный класс — это полноценный внутренний класс. Поэтому у него есть доступ к переменным внешнего класса, в том числе к статическим и приватным.



## Пример анонимного класса с JavaRush
```java
public class Solution {
    private String name;

    Solution(String name) {
        this.name = name;
    }

    protected String getName() {
        return name;
    }

    private void sout() {
        new Solution("The Darkside Hacker") {
            void printName() {
                System.out.println(getName());
            }
        }.printName();
    }

    public static void main(String[] args) {
        new Solution("Риша").sout();
    }
}
```
Если у метода `getName()` модификатор доступа будет `protected` - тогда вывод будет:
```java
// console output:
The Darkside Hacker
```
Если у метода `getName()` модификатор доступа будет `privet` - тогда вывод будет:
```java
// console output:
Риша
```


## [Сложный пример на JavaRush - Наследование от внутреннего класса](https://javarush.ru/tasks/com.javarush.task.task24.task2406)
TASK: Внутри класса `Solution` создай 2 внутренних public класса `Apt3Bedroom`, `BigHall`.
Унаследуй их от `Apartments` и `Hall`.

```java
import java.math.BigDecimal;

public class Solution {

    public class Building {

        public class Hall {
            private BigDecimal square;

            public Hall(BigDecimal square) {
                this.square = square;
            }
        }

        public class Apartments {

        }
    }

    public static void main(String[] args) {

    }
}
```

<details>
<summary>SHOW SOLUTION (task2406) = |</summary>

```java
import java.math.BigDecimal;

public class Solution {

    public class Building {

        public class Hall {
            private BigDecimal square;

            public Hall(BigDecimal square) {
                this.square = square;
            }
        }

        public class Apartments {

        }
    }

|   public class Apt3Bedroom extends Building.Apartments {
|       public Apt3Bedroom(Building building) {
|           building.super();
|       }
|   }

|   public class BigHall extends Building.Hall {
|       public BigHall(Building building) {
|           building.super(new BigDecimal(2000));
|       }
|   }

    public static void main(String[] args) {

    }
}
```

</details>


## Локальные классы
Локальные классы — классы внутри методов.
Они работают как обычные внутренние классы, но их можно использовать в пределах того метода, где их объявили.<br>

Класс, объявленный внутри метода, может использовать локальные переменные этого метода. 
Но есть одно ограничение – переменные можно только «читать», изменять их нельзя.<br>
Поэтому классы, объявленные внутри метода, могут иметь доступ только к тем переменным метода, которые помечены ключевым словом final.

Пример, использования добавления к объекту `Car` локальной переменной метода `policeNumber`.
```java
class Car {
   public ArrayList<Car> createPoliceCars(int count) {
      ArrayList<Car> result = new ArrayList<Car>();
      for (int i = 0; i < count; i++) {
|        final int number = i;
|        result.add(new Car() {
|           int policeNumber = number;
|        });
      }
      return result;
   }
}
```

