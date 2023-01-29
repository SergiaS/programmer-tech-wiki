# Hamcrest

## Пример тестирования веба из TopJava
```java
// моковая конфигурация
@ContextConfiguration({
    "classpath:spring/spring-app.xml",
    "classpath:spring/spring-mvc.xml",
    "classpath:spring/spring-db.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ActiveProfiles(resolver = ActiveDbProfileResolver.class, profiles = Profiles.REPOSITORY_IMPLEMENTATION)
public abstract class AbstractControllerTest {

  private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

  static {
    CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
    CHARACTER_ENCODING_FILTER.setForceEncoding(true);
  }

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @PostConstruct
  private void postConstruct() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        .addFilter(CHARACTER_ENCODING_FILTER)
        .build();
  }

  protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
    return mockMvc.perform(builder);
  }
}
```
```java
// тест веб данных
import org.junit.Test;
import ru.javawebinar.topjava.UserTestData;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class RootControllerTest extends AbstractControllerTest {

  @Test
  public void getUsers() throws Exception {
    perform(get("/users"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("users"))
        .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
        .andExpect(model().attribute("users", hasSize(2)))
        .andExpect(model().attribute("users", hasItem(
            allOf(
                hasProperty("id", is(START_SEQ)),
                hasProperty("name", is(UserTestData.user.getName()))
            )
        )));
  }
}
```
