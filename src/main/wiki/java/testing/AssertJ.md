# AssertJ
* [Documentation](https://assertj.github.io/doc/)

## Examples

> <details>
> <summary>EXAMPLE - object assert with assertThat</summary>
>
> From [amigoscode - software-testing](https://github.com/amigoscode/software-testing/tree/master)
> 
> ```java
> import org.junit.jupiter.api.Test;
> import org.springframework.beans.factory.annotation.Autowired;
> import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
> 
> import java.util.Optional;
> import java.util.UUID;
> 
> import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
> 
> @DataJpaTest
> class CustomerRepositoryTest {
> 
>   @Autowired
>   private CustomerRepository underTest;
> 
>   @Test
>   void itShouldSelectCustomerByPhoneNumber() {
>     // Given
>     UUID id = UUID.randomUUID();
>     Customer customer = new Customer(id, "Bob", "0000");
> 
>     // When
>     underTest.save(customer);
> 
>     // Then
>     Optional<Customer> optionalCustomer = underTest.findById(id);
>     assertThat(optionalCustomer)
>         .isPresent()
>         .hasValueSatisfying(c -> {
> //          assertThat(c.getId()).isEqualTo(id);
> //          assertThat(c.getName()).isEqualTo("Bob");
> //          assertThat(c.getPhoneNumber()).isEqualTo("0000");
>           assertThat(c).isEqualToComparingFieldByField(customer);
>         });
>   }
> }
> ```
> </details>

