# Spring Boot

## CommandLineRunner
Один из способов популяции БД - создание бина:
```java
// Пример на ReactiveCrudRepository
@Bean
public CommandLineRunner initConfig(ReactiveCrudRepository<NodeRoot, String> repo) {
    return (p) -> {
        repo.deleteAll().block();
        repo.save(new NodeRoot("Bob")).block();
        repo.save(new NodeRoot("Matt")).block();
        repo.save(new NodeRoot("Elis")).block();
        repo.save(new NodeRoot("Ted")).block();
        repo.save(new NodeRoot("Jenny")).block();
        repo.save(new NodeRoot("Nora")).block();
    };
}
```
