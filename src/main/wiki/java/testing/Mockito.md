# Mockito 
* [SITE](https://site.mockito.org/) || [DOC](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)

Mockito - це платформа для тестування з відкритим кодом для Java, випущена в рамках ліцензії MIT. 
Структура дозволяє створювати тестові об'єкти для розробки на основі тестів (TDD) або керування поведінкою (BDD). 
Мockito дозволяє макетувати об'єкт створення, перевірка та виправлення. 
Чому так багато розробників використовують Mockito? Автоматизовані тести - це захист. 
Вони запускаються та повідомляють користувача, якщо система зламана так що код “порушника” може бути зафіксовано дуже швидко.

Фреймворк Mockito надає ряд можливостей для створення згаданих вище «моків» замість реальних класів або інтерфейсів при написанні JUnit тестів.
Найбільшого поширення набули такі можливості Mockito:
- створення mock-об'єктів для класів та інтерфейсів;
- перевірка виклика методу і значень параметрів переданих методу;
- використання концепції «часткової заглушки», при якій мок створюється на клас з визначенням поведінки, необхідної для деяких методів класу;
- підключення до реального класу «шпигуна» (spy) для контролю виклику методів.

Mockito - це основа для модульних тестів у Java. 
Вона була розроблена таким чином, щоб бути інтуїтивно зрозумілою для використання, коли потрібні тести.



## Examples

* [Mocking Exception Throwing using Mockito](https://www.baeldung.com/mockito-exceptions) by Baeldung

### ...by Amigoscode software-testing course

> <details>
> <summary>Ways to creating mocks</summary>
> Це дає змогу використовувати дані тільки для тесту без маніпуляцій з зміни реальних даних
> 
> ```java
> // 1st way
> import org.junit.jupiter.api.BeforeEach;
> import org.mockito.Mock;
> import org.mockito.MockitoAnnotations;
> 
> class CustomerRegistrationServiceTest {
> 
>   @Mock
>   private CustomerRepository customerRepository;
> 
>   private CustomerRegistrationService underTest;
> 
>   @BeforeEach
>   void setUp() {
>     MockitoAnnotations.initMocks(this);
>     underTest = new CustomerRegistrationService(customerRepository);
>   }
> }
> ```
> ```java
> // 2nd way
> import org.junit.jupiter.api.BeforeEach;
> 
> import static org.mockito.Mockito.mock;
> 
> class CustomerRegistrationServiceTest {
> 
>   private CustomerRepository customerRepository = mock(CustomerRepository.class);
> 
>   private CustomerRegistrationService underTest;
> 
>   @BeforeEach
>   void setUp() {
>     underTest = new CustomerRegistrationService(customerRepository);
>   }
> }
> ```
> </details>

> <details>
> <summary>Mocking and Argument Captor</summary>
>
> ```java
> // test class
> import org.junit.jupiter.api.BeforeEach;
> import org.junit.jupiter.api.Test;
> import org.mockito.ArgumentCaptor;
> import org.mockito.Captor;
> import org.mockito.Mock;
> import org.mockito.MockitoAnnotations;
> 
> import java.util.Optional;
> import java.util.UUID;
> 
> import static org.assertj.core.api.Assertions.assertThat;
> import static org.mockito.BDDMockito.given;
> import static org.mockito.BDDMockito.then;
> 
> class CustomerRegistrationServiceTest {
> 
>   @Mock
>   private CustomerRepository customerRepository;
> 
>   @Captor
>   private ArgumentCaptor<Customer> customerArgumentCaptor;
> 
>   private CustomerRegistrationService underTest;
> 
>   @BeforeEach
>   void setUp() {
>     MockitoAnnotations.initMocks(this);
>     underTest = new CustomerRegistrationService(customerRepository);
>   }
> 
>   @Test
>   void itShouldSaveNewCustomer() {
>     // Given
>     String phoneNumber = "000099";
>     Customer customer = new Customer(UUID.randomUUID(), "Carl", phoneNumber);
> 
>     // ... a request
>     CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);
> 
>     // ... No customer with phone number passed
>     given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
>         .willReturn(Optional.empty());
> 
>     // When
>     underTest.registerNewCustomer(request);
> 
>     // Then
>     then(customerRepository).should().save(customerArgumentCaptor.capture());
>     Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
>     assertThat(customerArgumentCaptorValue).isEqualTo(customer);
>   }
> }
> ```
> ```java
> // class with business logic
> import org.springframework.beans.factory.annotation.Autowired;
> import org.springframework.stereotype.Service;
> 
> import java.util.Optional;
> import java.util.UUID;
> 
> @Service
> public class CustomerRegistrationService {
> 
>   private final CustomerRepository customerRepository;
> 
>   @Autowired
>   public CustomerRegistrationService(CustomerRepository customerRepository) {
>     this.customerRepository = customerRepository;
>   }
> 
>   public void registerNewCustomer(CustomerRegistrationRequest request) {
>     String phoneNumber = request.getCustomer().getPhoneNumber();
> 
>     Optional<Customer> customerOptional = customerRepository
>         .selectCustomerByPhoneNumber(phoneNumber);
> 
>     if (customerOptional.isPresent()) {
>       Customer customer = customerOptional.get();
>       if (customer.getName().equals(request.getCustomer().getName())) {
>         return;
>       }
>       throw new IllegalStateException(String.format("phone number [%s] is taken", phoneNumber));
>     }
> 
>     if (request.getCustomer().getId() == null) {
>       request.getCustomer().setId(UUID.randomUUID());
>     }
> 
>     customerRepository.save(request.getCustomer());
>   }
> }
> ```
</details>

> <details>
> <summary>Should Never</summary>
> 
> ```java
> // test class
> import org.junit.jupiter.api.BeforeEach;
> import org.junit.jupiter.api.Test;
> import org.mockito.ArgumentCaptor;
> import org.mockito.Captor;
> import org.mockito.Mock;
> import org.mockito.MockitoAnnotations;
> 
> import java.util.Optional;
> import java.util.UUID;
> 
> import static org.assertj.core.api.Assertions.assertThat;
> import static org.mockito.ArgumentMatchers.any;
> import static org.mockito.BDDMockito.given;
> import static org.mockito.BDDMockito.then;
> import static org.mockito.Mockito.never;
> 
> class CustomerRegistrationServiceTest {
> 
>   @Mock
>   private CustomerRepository customerRepository;
> 
>   @Captor
>   private ArgumentCaptor<Customer> customerArgumentCaptor;
> 
>   private CustomerRegistrationService underTest;
> 
>   @BeforeEach
>   void setUp() {
>     MockitoAnnotations.initMocks(this);
>     underTest = new CustomerRegistrationService(customerRepository);
>   }
> 
>   @Test
>   void itShouldSaveNewCustomer() {
>     // Given
>     String phoneNumber = "000099";
>     Customer customer = new Customer(UUID.randomUUID(), "Carl", phoneNumber);
> 
>     // ... a request
>     CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);
> 
>     // ... No customer with phone number passed
>     given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
>         .willReturn(Optional.empty());
> 
>     // When
>     underTest.registerNewCustomer(request);
> 
>     // Then
>     then(customerRepository).should().save(customerArgumentCaptor.capture());
>     Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
>     assertThat(customerArgumentCaptorValue).isEqualTo(customer);
>   }
> 
>   @Test
>   void itShouldNotSaveCustomerWhenCustomerExists() {
>     // Given
>     String phoneNumber = "000099";
>     Customer customer = new Customer(UUID.randomUUID(), "Carl", phoneNumber);
> 
>     // ... a request
>     CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);
> 
>     // ... No customer with phone number passed
>     given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
>         .willReturn(Optional.of(customer));
> 
>     // When
>     underTest.registerNewCustomer(request);
> 
>     // Then
>     then(customerRepository).should(never()).save(any());
> //    then(customerRepository).should().selectCustomerByPhoneNumber(phoneNumber);
> //    then(customerRepository).shouldHaveNoMoreInteractions();
>   }
> }
> ```
> ```java
> // class with business logic
> import org.springframework.beans.factory.annotation.Autowired;
> import org.springframework.stereotype.Service;
> 
> import java.util.Optional;
> 
> @Service
> public class CustomerRegistrationService {
> 
>   private final CustomerRepository customerRepository;
> 
>   @Autowired
>   public CustomerRegistrationService(CustomerRepository customerRepository) {
>     this.customerRepository = customerRepository;
>   }
> 
>   public void registerNewCustomer(CustomerRegistrationRequest request) {
>     String phoneNumber = request.getCustomer().getPhoneNumber();
> 
>     Optional<Customer> customerOptional = customerRepository
>         .selectCustomerByPhoneNumber(phoneNumber);
> 
>     if (customerOptional.isPresent()) {
>       Customer customer = customerOptional.get();
>       if (customer.getName().equals(request.getCustomer().getName())) {
>         return;
>       }
>       throw new IllegalStateException(String.format("phone number [%s] is taken", phoneNumber));
>     }
> 
>     customerRepository.save(request.getCustomer());
>   }
> }
> ```
> 
> </details>


> <details>
> <summary>Testing Exceptions</summary>
> Приклад, як тестувати на викид помилки:
>
> ```java
> // test class
> import org.junit.jupiter.api.BeforeEach;
> import org.junit.jupiter.api.Test;
> import org.mockito.Mock;
> import org.mockito.MockitoAnnotations;
> 
> import java.util.Optional;
> import java.util.UUID;
> 
> import static org.assertj.core.api.Assertions.assertThatThrownBy;
> import static org.mockito.ArgumentMatchers.any;
> import static org.mockito.BDDMockito.given;
> import static org.mockito.BDDMockito.then;
> import static org.mockito.Mockito.never;
> 
> class CustomerRegistrationServiceTest {
> 
>   @Mock
>   private CustomerRepository customerRepository;
> 
>   private CustomerRegistrationService underTest;
> 
>   @BeforeEach
>   void setUp() {
>     MockitoAnnotations.initMocks(this);
>     underTest = new CustomerRegistrationService(customerRepository);
>   }
> 
>   @Test
>   void itShouldThrowWhenPhoneNumberIsTaken() {
>     // Given a phone number and a customer
>     String phoneNumber = "000099";
>     Customer customer = new Customer(UUID.randomUUID(), "Maryam", phoneNumber);
>     Customer customerTwo = new Customer(UUID.randomUUID(), "John", phoneNumber);
> 
>     // ... a request
>     CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);
> 
>     // ... an existing customer is returned
>     given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
>         .willReturn(Optional.of(customerTwo));
> 
>     // When
>     // Then
>     assertThatThrownBy(() -> underTest.registerNewCustomer(request))
>         .isInstanceOf(IllegalStateException.class)
>         .hasMessageContaining(String.format("phone number [%s] is taken", phoneNumber));
> 
>     // Finally
>     then(customerRepository).should(never()).save(any(Customer.class));
>   }
> }
> ```
> ```java
> // class with business logic
> @Service
> public class CustomerRegistrationService {
> 
>   private final CustomerRepository customerRepository;
> 
>   @Autowired
>   public CustomerRegistrationService(CustomerRepository customerRepository) {
>     this.customerRepository = customerRepository;
>   }
> 
>   public void registerNewCustomer(CustomerRegistrationRequest request) {
>     String phoneNumber = request.getCustomer().getPhoneNumber();
> 
>     Optional<Customer> customerOptional = customerRepository
>         .selectCustomerByPhoneNumber(phoneNumber);
> 
>     if (customerOptional.isPresent()) {
>       Customer customer = customerOptional.get();
>       if (customer.getName().equals(request.getCustomer().getName())) {
>         return;
>       }
>       throw new IllegalStateException(String.format("phone number [%s] is taken", phoneNumber));
>     }
> 
>     customerRepository.save(request.getCustomer());
>   }
> }
> ```
> 
> </details>

> <details>
> <summary>Query tests</summary>
> Тестування створеного запиту до репозиторію:
>
> ```java
> // репозиторій
> import org.springframework.data.jpa.repository.Query;
> import org.springframework.data.repository.CrudRepository;
> import org.springframework.data.repository.query.Param;
> 
> import java.util.Optional;
> import java.util.UUID;
> 
> public interface CustomerRepository extends CrudRepository<Customer, UUID> {
> 
>   @Query(
>       value = 
>           "SELECT id, name, phone_number " +
>           "FROM customer " +
>           "WHERE phone_number = :phone_number",
>       nativeQuery = true
>   )
>   Optional<Customer> selectCustomerByPhoneNumber(@Param("phone_number") String phoneNumber);
> 
> }
> ```
> ```java
> // тести на репозиторій
> import org.junit.jupiter.api.Test;
> import org.springframework.beans.factory.annotation.Autowired;
> import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
> import org.springframework.dao.DataIntegrityViolationException;
> 
> import java.util.Optional;
> import java.util.UUID;
> 
> import static org.assertj.core.api.Assertions.assertThat;
> import static org.assertj.core.api.Assertions.assertThatThrownBy;
> 
> @DataJpaTest(
>     properties = {
>         "spring.jpa.properties.javax.persistence.validation.mode=none"
>     }
> )
> class CustomerRepositoryTest {
> 
>   @Autowired
>   private CustomerRepository underTest;
> 
>   @Test
>   void itShouldSelectCustomerByPhoneNumber() {
>     // Given
>     UUID id = UUID.randomUUID();
>     String phoneNumber = "0000";
>     Customer customer = new Customer(id, "Bob", phoneNumber);
> 
>     // When
>     underTest.save(customer);
> 
>     // Then
>     Optional<Customer> optionalCustomer = underTest.selectCustomerByPhoneNumber(phoneNumber);
>     assertThat(optionalCustomer)
>         .isPresent()
>         .hasValueSatisfying(c -> {
>           assertThat(c).isEqualToComparingFieldByField(customer);
>         });
>   }
> 
>   @Test
>   void itNotShouldSelectCustomerByPhoneNumberWhenNumberDoesNotExists() {
>     // Given
>     String phoneNumber = "0000";
> 
>     // When
>     Optional<Customer> optionalCustomer = underTest.selectCustomerByPhoneNumber(phoneNumber);
> 
>     // Then
>     assertThat(optionalCustomer).isNotPresent();
>   }
> 
>   @Test
>   void itShouldSaveCustomer() {
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
> 
>   @Test
>   void itShouldNotSaveCustomerWhenNameIsNull() {
>     // Given
>     UUID id = UUID.randomUUID();
>     Customer customer = new Customer(id, null, "0000");
> 
>     // When
>     // Then
>     assertThatThrownBy(() -> underTest.save(customer))
>         .hasMessageContaining("not-null property references a null or transient value : com.amigoscode.testing.customer.Customer.name")
>         .isInstanceOf(DataIntegrityViolationException.class);
> 
>   }
> 
>   @Test
>   void itShouldNotSaveCustomerWhenPhoneNumberIsNull() {
>     // Given
>     UUID id = UUID.randomUUID();
>     Customer customer = new Customer(id, "Alex", null);
> 
>     // When
>     // Then
>     assertThatThrownBy(() -> underTest.save(customer))
>         .hasMessageContaining("not-null property references a null or transient value : com.amigoscode.testing.customer.Customer.phoneNumber")
>         .isInstanceOf(DataIntegrityViolationException.class);
> 
>   }
> }
> ```
> 
> </details>

> <details>
> <summary>Тестування сервісу</summary>
> 
> ```java
> // треба протестувати метод сервісу chargeCard
> import java.util.Arrays;
> import java.util.Optional;
> import java.util.UUID;
> 
> @Service
> public class PaymentService {
> 
>   private final CustomerRepository customerRepository;
>   private final PaymentRepository paymentRepository;
>   private final CardPaymentCharger cardPaymentCharger;
> 
>   @Autowired
>   public PaymentService(CustomerRepository customerRepository, PaymentRepository paymentRepository, CardPaymentCharger cardPaymentCharger) {
>     this.customerRepository = customerRepository;
>     this.paymentRepository = paymentRepository;
>     this.cardPaymentCharger = cardPaymentCharger;
>   }
> 
>   void chargeCard(UUID customerId, PaymentRequest paymentRequest) {
>     // 1. Does customer exists if not throw
>     Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
>     if (optionalCustomer.isEmpty())
>       throw new IllegalStateException(String.format("There is no customer with id [%s]", customerId));
> 
>     // 2. Do we support the currency if not throw
>     Payment payment = paymentRequest.getPayment();
> 
>     Currency[] values = Currency.values();
> 
>     if (Arrays.stream(values).noneMatch(e -> e.equals(payment.getCurrency())))
>       throw new IllegalStateException("The Currency does not support");
> 
> 
>     // 3, Charge card
>     CardPaymentCharge cardPaymentCharge = cardPaymentCharger.chargeCard(
>         payment.getSource(),
>         payment.getAmount(),
>         payment.getCurrency(),
>         payment.getDescription()
>     );
> 
>     // 4. If not debited throw
>     if (!cardPaymentCharge.isCardDebited())
>       throw new IllegalStateException(String.format("The card is not debited for customer [%s]", customerId));
> 
>     // 5. Insert payment
>     payment.setCustomerId(customerId);
> 
>     paymentRepository.save(payment);
> 
>     // 6. TODO: send sms
>   }
> }
> ```
> ```java
> // тестування сервісу
> import org.junit.jupiter.api.BeforeEach;
> import org.junit.jupiter.api.Test;
> import org.mockito.ArgumentCaptor;
> import org.mockito.Mock;
> import org.mockito.MockitoAnnotations;
> 
> import java.math.BigDecimal;
> import java.util.Optional;
> import java.util.UUID;
> 
> import static org.assertj.core.api.Assertions.assertThat;
> import static org.mockito.BDDMockito.given;
> import static org.mockito.BDDMockito.then;
> import static org.mockito.Mockito.mock;
> 
> class PaymentServiceTest {
> 
>   @Mock
>   private CustomerRepository customerRepository;
>   @Mock
>   private PaymentRepository paymentRepository;
>   @Mock
>   private CardPaymentCharger cardPaymentCharger;
> 
>   private PaymentService underTest;
> 
>   @BeforeEach
>   void setUp() {
>     MockitoAnnotations.initMocks(this);
>     underTest = new PaymentService(customerRepository, paymentRepository, cardPaymentCharger);
>   }
> 
>   @Test
>   void itShouldChargeCardSuccessfully() {
>     // Given
>     UUID customerId = UUID.randomUUID();
> 
>     // ... Customer exists
>     given(customerRepository.findById(customerId)).willReturn(Optional.of(mock(Customer.class)));
> 
>     // ... Payment request
>     PaymentRequest paymentRequest = new PaymentRequest(
>         new Payment(
>             null,
>             null,
>             new BigDecimal("100.00"),
>             Currency.USD,
>             "card123xx",
>             "Donation"
>         )
>     );
> 
>     // ... Card is charged successfully
>     given(cardPaymentCharger.chargeCard(
>         paymentRequest.getPayment().getSource(),
>         paymentRequest.getPayment().getAmount(),
>         paymentRequest.getPayment().getCurrency(),
>         paymentRequest.getPayment().getDescription()
>     )).willReturn(new CardPaymentCharge(true));
> 
>     // When
>     underTest.chargeCard(customerId, paymentRequest);
> 
>     // Then
>     ArgumentCaptor<Payment> paymentArgumentCaptor =
>         ArgumentCaptor.forClass(Payment.class);
> 
>     then(paymentRepository).should().save(paymentArgumentCaptor.capture());
> 
>     Payment paymentArgumentCaptorValue = paymentArgumentCaptor.getValue();
>     assertThat(paymentArgumentCaptorValue)
>         .isEqualToIgnoringGivenFields(
>             paymentRequest.getPayment(),
>             "customerId");
> 
>     assertThat(paymentArgumentCaptorValue.getCustomerId()).isEqualTo(customerId);
>   }
> }
> ```
> 
> </details>

> <details>
> <summary>Тестування сервісу, ще приклад</summary>
>
> ```java
> // треба протестувати метод сервісу
> @Service
> public class PaymentService {
> 
>   private static final List<Currency> ACCEPTED_CURRENCIES = List.of(Currency.USD, Currency.GBP);
> 
>   private final CustomerRepository customerRepository;
>   private final PaymentRepository paymentRepository;
>   private final CardPaymentCharger cardPaymentCharger;
> 
>   @Autowired
>   public PaymentService(CustomerRepository customerRepository, PaymentRepository paymentRepository, CardPaymentCharger cardPaymentCharger) {
>     this.customerRepository = customerRepository;
>     this.paymentRepository = paymentRepository;
>     this.cardPaymentCharger = cardPaymentCharger;
>   }
> 
>   void chargeCard(UUID customerId, PaymentRequest paymentRequest) {
>     // 1. Does customer exists if not throw
>     Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
>     if (optionalCustomer.isEmpty())
>       throw new IllegalStateException(String.format("Customer with id %s not found", customerId));
> 
>     // 2. Do we support the currency if not throw
>     boolean isCurrencySupported = ACCEPTED_CURRENCIES.contains(paymentRequest.getPayment().getCurrency());
> 
>     if (!isCurrencySupported)
>       throw new IllegalStateException(String.format(
>           "The Currency %s does not support",
>           paymentRequest.getPayment().getCurrency()));
> 
>     // 3, Charge card
>     CardPaymentCharge cardPaymentCharge = cardPaymentCharger.chargeCard(
>         paymentRequest.getPayment().getSource(),
>         paymentRequest.getPayment().getAmount(),
>         paymentRequest.getPayment().getCurrency(),
>         paymentRequest.getPayment().getDescription()
>     );
> 
>     // 4. If not debited throw
>     if (!cardPaymentCharge.isCardDebited())
>       throw new IllegalStateException(String.format("The card is not debited for customer %s", customerId));
> 
>     // 5. Insert payment
>     paymentRequest.getPayment().setCustomerId(customerId);
> 
>     paymentRepository.save(paymentRequest.getPayment());
>   }
> }
> ```
> ```java
> // тестування сервісу
> import org.junit.jupiter.api.BeforeEach;
> import org.junit.jupiter.api.Test;
> import org.mockito.ArgumentCaptor;
> import org.mockito.Mock;
> import org.mockito.MockitoAnnotations;
> 
> import java.math.BigDecimal;
> import java.util.Optional;
> import java.util.UUID;
> 
> import static org.assertj.core.api.Assertions.assertThat;
> import static org.assertj.core.api.Assertions.assertThatThrownBy;
> import static org.mockito.BDDMockito.given;
> import static org.mockito.BDDMockito.then;
> import static org.mockito.Mockito.mock;
> 
> class PaymentServiceTest {
> 
>   @Mock
>   private CustomerRepository customerRepository;
>   @Mock
>   private PaymentRepository paymentRepository;
>   @Mock
>   private CardPaymentCharger cardPaymentCharger;
> 
>   private PaymentService underTest;
> 
>   @BeforeEach
>   void setUp() {
>     MockitoAnnotations.initMocks(this);
>     underTest = new PaymentService(customerRepository, paymentRepository, cardPaymentCharger);
>   }
> 
>   @Test
>   void itShouldChargeCardSuccessfully() {
>     // Given
>     UUID customerId = UUID.randomUUID();
> 
>     // ... Customer exists
>     given(customerRepository.findById(customerId)).willReturn(Optional.of(mock(Customer.class)));
> 
>     // ... Payment request
>     PaymentRequest paymentRequest = new PaymentRequest(
>         new Payment(
>             null,
>             null,
>             new BigDecimal("100.00"),
>             Currency.USD,
>             "card123xx",
>             "Donation"
>         )
>     );
> 
>     // ... Card is charged successfully
>     given(cardPaymentCharger.chargeCard(
>         paymentRequest.getPayment().getSource(),
>         paymentRequest.getPayment().getAmount(),
>         paymentRequest.getPayment().getCurrency(),
>         paymentRequest.getPayment().getDescription()
>     )).willReturn(new CardPaymentCharge(true));
> 
>     // When
>     underTest.chargeCard(customerId, paymentRequest);
> 
>     // Then
>     ArgumentCaptor<Payment> paymentArgumentCaptor =
>         ArgumentCaptor.forClass(Payment.class);
> 
>     then(paymentRepository).should().save(paymentArgumentCaptor.capture());
> 
>     Payment paymentArgumentCaptorValue = paymentArgumentCaptor.getValue();
>     assertThat(paymentArgumentCaptorValue)
>         .isEqualToIgnoringGivenFields(
>             paymentRequest.getPayment(),
>             "customerId");
> 
>     assertThat(paymentArgumentCaptorValue.getCustomerId()).isEqualTo(customerId);
>   }
> 
>   @Test
>   void itShouldThrowWhenCardIsNotCharged() {
>     // Given
>     UUID customerId = UUID.randomUUID();
> 
>     // ... Customer exists
>     given(customerRepository.findById(customerId)).willReturn(Optional.of(mock(Customer.class)));
> 
>     // ... Payment request
>     PaymentRequest paymentRequest = new PaymentRequest(
>         new Payment(
>             null,
>             null,
>             new BigDecimal("100.00"),
>             Currency.USD,
>             "card123xx",
>             "Donation"
>         )
>     );
> 
>     // ... Card is not charged successfully
>     given(cardPaymentCharger.chargeCard(
>         paymentRequest.getPayment().getSource(),
>         paymentRequest.getPayment().getAmount(),
>         paymentRequest.getPayment().getCurrency(),
>         paymentRequest.getPayment().getDescription()
>     )).willReturn(new CardPaymentCharge(false));
> 
>     // When
>     // Then
>     assertThatThrownBy(() -> underTest.chargeCard(customerId, paymentRequest))
>         .isInstanceOf(IllegalStateException.class)
>         .hasMessageContaining("The card is not debited for customer " + customerId);
> 
>     // ... No interaction with paymentRepository
>     then(paymentRepository).shouldHaveNoInteractions();
>   }
> 
>   @Test
>   void itShouldNotChargeCardAndThrowWhenCurrencyNotSupported() {
>     // Given
>     UUID customerId = UUID.randomUUID();
> 
>     // ... Customer exists
>     given(customerRepository.findById(customerId)).willReturn(Optional.of(mock(Customer.class)));
> 
>     // ... Euros
>     Currency currency = Currency.EUR;
> 
>     // ... Payment request
>     PaymentRequest paymentRequest = new PaymentRequest(
>         new Payment(
>             null,
>             null,
>             new BigDecimal("100.00"),
>             currency,
>             "card123xx",
>             "Donation"
>         )
>     );
> 
>     // ... No interaction with cardPaymentCharger
>     then(cardPaymentCharger).shouldHaveNoInteractions();
> 
>     // When
>     // Then
>     assertThatThrownBy(() -> underTest.chargeCard(customerId, paymentRequest))
>         .isInstanceOf(IllegalStateException.class)
>         .hasMessageContaining(String.format("The Currency %s does not support", currency));
> 
>     // ... No interaction with cardPaymentCharger
>     then(cardPaymentCharger).shouldHaveNoInteractions();
> 
>     // ... No interaction with paymentRepository
>     then(paymentRepository).shouldHaveNoInteractions();
>   }
> 
>   @Test
>   void itShouldNotChargeAndThrowWhenCustomerNotFound() {
>     // Given
>     UUID customerId = UUID.randomUUID();
> 
>     // Customer not found in db
>     given(customerRepository.findById(customerId)).willReturn(Optional.empty());
> 
>     // When
>     // Then
>     assertThatThrownBy(() -> underTest.chargeCard(customerId, new PaymentRequest(new Payment())))
>         .isInstanceOf(IllegalStateException.class)
>         .hasMessageContaining(String.format("Customer with id %s not found", customerId));
> 
>     // ... No interactions with PaymentCharger nor PaymentRepository
>     then(cardPaymentCharger).shouldHaveNoInteractions();
>     then(paymentRepository).shouldHaveNoInteractions();
>   }
> }
> ```
>
> </details>


> <details>
> <summary>Stripe API testing</summary>
>
> ```java
> // Integrated API service
> package com.amigoscode.testing.payment.stripe;
> 
> import com.amigoscode.testing.payment.CardPaymentCharge;
> import com.amigoscode.testing.payment.CardPaymentCharger;
> import com.amigoscode.testing.payment.Currency;
> import com.stripe.exception.StripeException;
> import com.stripe.model.Charge;
> import com.stripe.net.RequestOptions;
> import org.springframework.beans.factory.annotation.Autowired;
> import org.springframework.stereotype.Service;
> 
> import java.math.BigDecimal;
> import java.util.HashMap;
> import java.util.Map;
> 
> @Service
> public class StripeService implements CardPaymentCharger {
> 
>   private final StripeApi stripeApi;
> 
>   private final static RequestOptions requestOptions = RequestOptions.builder()
>       .setApiKey("sk_test_4eC39HqLyjWDarjtT1zdp7dc")
>       .build();
> 
>   @Autowired
>   public StripeService(StripeApi stripeApi) {
>     this.stripeApi = stripeApi;
>   }
> 
>   @Override
>   public CardPaymentCharge chargeCard(String cardSource,
>                                       BigDecimal amount,
>                                       Currency currency,
>                                       String description) {
>     Map<String, Object> params = new HashMap<>();
>     params.put("amount", amount);
>     params.put("currency", currency);
>     params.put("source", cardSource);
>     params.put("description", description);
> 
>     try {
>       Charge charge = stripeApi.create(params, requestOptions);
>       return new CardPaymentCharge(charge.getPaid());
>     } catch (StripeException e) {
>       throw new IllegalStateException("Cannot make stripe charge", e);
>     }
>   }
> }
> ```
> ```java
> // Integrated API service tests
> package com.amigoscode.testing.payment.stripe;
> 
> import com.amigoscode.testing.payment.CardPaymentCharge;
> import com.amigoscode.testing.payment.Currency;
> import com.stripe.exception.StripeException;
> import com.stripe.model.Charge;
> import com.stripe.net.RequestOptions;
> import org.junit.jupiter.api.BeforeEach;
> import org.junit.jupiter.api.Test;
> import org.mockito.ArgumentCaptor;
> import org.mockito.Mock;
> import org.mockito.MockitoAnnotations;
> 
> import java.math.BigDecimal;
> import java.util.Map;
> 
> import static org.assertj.core.api.Assertions.assertThat;
> import static org.assertj.core.api.Assertions.assertThatThrownBy;
> import static org.mockito.ArgumentMatchers.any;
> import static org.mockito.ArgumentMatchers.anyMap;
> import static org.mockito.BDDMockito.given;
> import static org.mockito.BDDMockito.then;
> import static org.mockito.Mockito.doThrow;
> import static org.mockito.Mockito.mock;
> 
> class StripeServiceTest {
> 
>   private StripeService underTest;
> 
>   @Mock
>   private StripeApi stripeApi;
> 
>   @BeforeEach
>   void setUp() {
>     MockitoAnnotations.initMocks(this);
>     underTest = new StripeService(stripeApi);
>   }
> 
>   @Test
>   void itShouldChargeCard() throws StripeException {
>     // Given
>     String cardSource = "0x0x0x";
>     BigDecimal amount = new BigDecimal("10.00");
>     Currency currency = Currency.USD;
>     String description = "Zakat";
> 
>     Charge charge = new Charge();
>     charge.setPaid(true);
>     given(stripeApi.create(anyMap(), any())).willReturn(charge);
> 
>     // When
>     CardPaymentCharge cardPaymentCharge = underTest.chargeCard(cardSource, amount, currency, description);
> 
>     // Then
>     ArgumentCaptor<Map<String, Object>> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
>     ArgumentCaptor<RequestOptions> optionsArgumentCaptor = ArgumentCaptor.forClass(RequestOptions.class);
> 
>     then(stripeApi).should().create(mapArgumentCaptor.capture(), optionsArgumentCaptor.capture());
> 
>     Map<String, Object> requestMap = mapArgumentCaptor.getValue();
> 
>     assertThat(requestMap.keySet()).hasSize(4);
> 
>     assertThat(requestMap.get("amount")).isEqualTo(amount);
>     assertThat(requestMap.get("currency")).isEqualTo(currency);
>     assertThat(requestMap.get("source")).isEqualTo(cardSource);
>     assertThat(requestMap.get("description")).isEqualTo(description);
> 
>     RequestOptions options = optionsArgumentCaptor.getValue();
> 
>     assertThat(options).isNotNull();
> 
>     assertThat(cardPaymentCharge).isNotNull();
> 
>     assertThat(cardPaymentCharge.isCardDebited()).isTrue();
>   }
> 
>   @Test
>   void itShouldWhenApiThrowsException() throws StripeException {
>     // Given
>     String cardSource = "0x0x0x";
>     BigDecimal amount = new BigDecimal("10.00");
>     Currency currency = Currency.USD;
>     String description = "Zakat";
> 
>     // Throw exception when stripe api is called
>     StripeException stripeException = mock(StripeException.class);
>     doThrow(stripeException)
>         .when(stripeApi).create(anyMap(), any());
> 
>     // When
>     // Then
>     assertThatThrownBy(() -> underTest.chargeCard(cardSource, amount, currency, description))
>         .isInstanceOf(IllegalStateException.class)
>         .hasMessageContaining("Cannot make stripe charge")
>         .hasRootCause(stripeException);
>   }
> }
> ```
> 
> </details>


