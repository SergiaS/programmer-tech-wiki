# Spring Expression Language (SpEL)
* [Spring Expression Language Guide](https://www.baeldung.com/spring-expression-language)

Помогает указывать названия с помощью констант, а не писать вручную сами названия.
Это сделано для устранения ошибок при ручном наборе - если что-то указано неверно и 
нет интеграции (ничего не сигнализирует о проблеме) - программа работать не будет - 
и нужно искать ошибку.

Синтаксис самого выражения:
```spel
"#{T(org.hibernate.cfg.AvailableSettings).FORMAT_SQL}"
```

Пример в контексте спринга:
```xml
<property name="jpaPropertyMap">
    <map>
        <entry key="#{T(org.hibernate.cfg.AvailableSettings).FORMAT_SQL}" value="${hibernate.format_sql}"/>
        <entry key="#{T(org.hibernate.cfg.AvailableSettings).USE_SQL_COMMENTS}" value="${hibernate.use_sql_comments}"/>
    </map>
</property>
```

