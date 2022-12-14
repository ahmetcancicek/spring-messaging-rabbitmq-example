package com.example.gitbank.customer.repository;

import com.example.gitbank.customer.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:sql/customer.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CustomerRepositoryTest {

    @Autowired
    protected TestEntityManager testEntityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void givenValidCustomer_whenSaveCustomer_thenReturnCustomer() {
        // given
        Customer customer = Customer.builder()
                .firstName("George")
                .lastName("Chair")
                .securityNo(UUID.randomUUID().toString())
                .dateOfBirth(LocalDate.of(1980, 11, 12))
                .build();

        // when
        customerRepository.save(customer);
        Customer expectedCustomer = testEntityManager.find(Customer.class, customer.getId());

        // then
        assertEquals(customer, expectedCustomer);
    }

    @Test
    public void givenExistingCustomer_whenFindById_thenReturnCustomer() {
        // given
        String id = "b9823d94-ed64-4ec3-a500-9dae984dd524";

        // when
        Optional<Customer> customer = customerRepository.findById(id);

        // then
        assertTrue(customer.isPresent());
    }

    @Test
    public void givenExistingCustomer_whenFindBySecurityNo_thenReturnCustomer() {
        // given
        String securityNo = "b9823d94-ed64-4ec3-a500-9dae984dd524";

        // when
        Optional<Customer> customer = customerRepository.findBySecurityNo(securityNo);

        // then
        assertTrue(customer.isPresent());
    }

    @Test
    public void givenExistingCustomer_whenExistsBySecurityNo_thenReturnTrue() {
        // given
        String securityNo = "b9823d94-ed64-4ec3-a500-9dae984dd524";

        // when
        Boolean result = customerRepository.existsBySecurityNo(securityNo);

        // then
        assertTrue(result);
    }

    @Test
    public void givenExistingCustomer_whenExistsBySecurityNoAndIdNot_thenReturnFalse() {
        // given
        String securityNo = "b9823d94-ed64-4ec3-a500-9dae984dd524";
        String id = "b9823d94-ed64-4ec3-a500-9dae984dd524";

        // when
        Boolean result = customerRepository.existsBySecurityNoAndIdNot(securityNo, id);

        // then
        assertFalse(result);
    }
}