package io.github.pascalgrimaud.repository;

import io.github.pascalgrimaud.domain.BankAccount;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BankAccount entity.
 */
@SuppressWarnings("unused")
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {

    @Query("select bankAccount from BankAccount bankAccount where bankAccount.user.login = ?#{principal.username}")
    List<BankAccount> findByUserIsCurrentUser();

}
