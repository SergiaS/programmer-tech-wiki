# Serialization
* [Serialization and Deserialization in JAVA](https://prashant007.medium.com/serialization-and-deserialization-in-java-ce3fe636da88)
  
Serialization is the way to transform objects into a byte stream.

Deserialization is the just opposite of what serialization does, convert byte streams to the object.

Important points to note about Serialization:
1. `static` fields in a class don’t serialize.
2. `transient` keyword is used to make field ignore while serializing (these will take default value).
3. When a class implements a Serializable interface, all its subclasses are serializable as well. 
   When an object has a reference to another object, these objects must implement the serializable interface, or else `NotSerializableException` will be thrown.
4. If one of the fields in a serializable object consists of an array of objects, then all these objects must be serializable as well, or else a `NotSerializableException` will be thrown.


## Как сериализовать и десериализовать объект?
Для сериализации объектов в поток используется класс `ObjectOutputStream`. Он записывает данные в поток.
Для создания объекта `ObjectOutputStream` в конструктор передается поток, в который производится запись:
```java
// ObjectOutputStream(OutputStream out)
try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.dat"))) {
    Person p = new Person("Sam", 33, 178, true);
    oos.writeObject(p);
}
catch(Exception ex){
    System.out.println(ex.getMessage());
}
```

Класс `ObjectInputStream` отвечает за обратный процесс - чтение ранее сериализованных данных из потока. В конструкторе он принимает ссылку на поток ввода:
```java
// ObjectInputStream(InputStream in)
try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
    oos.writeObject(people);
    System.out.println("File has been written");
}
catch(Exception ex){
    System.out.println(ex.getMessage());
}
```
