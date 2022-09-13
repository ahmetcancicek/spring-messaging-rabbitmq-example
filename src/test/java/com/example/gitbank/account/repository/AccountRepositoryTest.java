package com.example.gitbank.account.repository;

import com.example.gitbank.account.model.Account;
import com.example.gitbank.account.model.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:sql/account.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AccountRepositoryTest {

    @Autowired
    protected TestEntityManager testEntityManager;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void givenValidAccount_whenSaveAccount_thenReturnAccount() {
        // given
        Account account = Account.builder()
                .name("My Debit USD")
                .currency(Currency.USD)
                .balance(BigDecimal.TEN)
                .build();

        // when
        accountRepository.save(account);
        Account expectedAccount = testEntityManager.find(Account.class, account.getId());

        // then
        assertEquals(account, expectedAccount);
    }

    @Test
    public void givenExistingAccount_whenFindById_thenReturnAccount() {
        // given
        String accountId = "fdbf99be-337c-11ed-a261-0242ac120002";

        // when
        Optional<Account> account = accountRepository.findById(accountId);

        // then
        assertTrue(account.isPresent());
    }
}
