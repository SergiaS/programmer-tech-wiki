# Spring

## IoC - Inversion of Control (инверсия управления)
* Если используется инверсия контроля (IoC), нельзя создавать объекты через `new`.


## Configuration
| JAVA config                                       | XML config                                                                |
|:--------------------------------------------------|:--------------------------------------------------------------------------|
| @Configuration                                    | application-context.xml                                                   |
| @ComponentScan(basePackages = "com.spring.rest")  | <context:component-scan base-package="com.spring.mvc_hibernate_aop" />    |
| @EnableWebMvc                                     | <mvc:annotation-driven/>                                                  |
| @EnableTransactionManagement                      | <tx:annotation-driven transaction-manager="transactionManager" />         |

## Event Processing
There are four components involved in event processing:
* Source;
* Event;
* Listener;
* Handler.
