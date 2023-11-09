# Enum
> `Enum` може використовувати тільки `private` і `default` (за замовчуванням) конструктор.

***

> `Enum` може імплементувати інтерфейс, а кожне його значення може перезаписувати методи (поліморфізм):

> <details>
> <summary>ПРИКЛАД 1</summary>
> 
> ```java
> public class TestNote {
>     public static void main(String[] args) {
>         System.out.println(Currency.DIME.color());   // silver
>         System.out.println(Currency.NICKLE.color()); // bronze
>         System.out.println(Currency.PENNY.color());  // copper
>     }
> 
>     interface MyInter{
>         String color();
>     }
> 
>     enum Currency implements MyInter {
>         PENNY(1) {
>             @Override
>             public String color() {
>                 return "copper";
>             }
>         },
>         NICKLE(5) {
>             @Override
>             public String color() {
>                 return "bronze";
>             }
>         },
>         DIME(10) {
>             @Override
>             public String color() {
>                 return "silver";
>             }
>         },
>         QUARTER(25) {
>             @Override
>             public String color() {
>                 return "silver";
>             }
>         };
> 
>         private int value;
> 
>         Currency(int value) {
>             this.value = value;
>         }
>     }
> }
> ```
> </details>


> <details>
> <summary>ПРИКЛАД 2</summary>
>
> ```java
> public class Test1 {
>   public static void main(String[] args) {
>     double x = 10.0;
>     double y = 5.0;
> 
>     for (MathOperation op : MathOperation.values()) {
>       System.out.printf("%.2f %s %.2f = %.2f%n", x, op, y, op.apply(x, y));
>     }
>   }
> }
> 
> // Створимо інтерфейс для опису операцій
> interface Operation {
>   double apply(double x, double y);
> }
> 
> // Створимо енум з підтримкою різних операцій
> enum MathOperation implements Operation {
>   ADD {
>     @Override
>     public double apply(double x, double y) {
>       return x + y;
>     }
>   },
>   SUBTRACT {
>     @Override
>     public double apply(double x, double y) {
>       return x - y;
>     }
>   },
>   MULTIPLY {
>     @Override
>     public double apply(double x, double y) {
>       return x * y;
>     }
>   },
>   DIVIDE {
>     @Override
>     public double apply(double x, double y) {
>       if (y != 0) {
>         return x / y;
>       }
>       throw new ArithmeticException("Division by zero");
>     }
>   }
> }
> ```
> </details>


***
