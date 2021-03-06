package io.github.pascalgrimaud.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.pascalgrimaud.domain.BankAccount;

import io.github.pascalgrimaud.repository.BankAccountRepository;
import io.github.pascalgrimaud.repository.search.BankAccountSearchRepository;
import io.github.pascalgrimaud.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing BankAccount.
 */
@RestController
@RequestMapping("/api")
public class BankAccountResource {

    private final Logger log = LoggerFactory.getLogger(BankAccountResource.class);
        
    @Inject
    private BankAccountRepository bankAccountRepository;

    @Inject
    private BankAccountSearchRepository bankAccountSearchRepository;

    /**
     * POST  /bank-accounts : Create a new bankAccount.
     *
     * @param bankAccount the bankAccount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bankAccount, or with status 400 (Bad Request) if the bankAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bank-accounts")
    @Timed
    public ResponseEntity<BankAccount> createBankAccount(@Valid @RequestBody BankAccount bankAccount) throws URISyntaxException {
        log.debug("REST request to save BankAccount : {}", bankAccount);
        if (bankAccount.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("bankAccount", "idexists", "A new bankAccount cannot already have an ID")).body(null);
        }
        BankAccount result = bankAccountRepository.save(bankAccount);
        bankAccountSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/bank-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bankAccount", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bank-accounts : Updates an existing bankAccount.
     *
     * @param bankAccount the bankAccount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bankAccount,
     * or with status 400 (Bad Request) if the bankAccount is not valid,
     * or with status 500 (Internal Server Error) if the bankAccount couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bank-accounts")
    @Timed
    public ResponseEntity<BankAccount> updateBankAccount(@Valid @RequestBody BankAccount bankAccount) throws URISyntaxException {
        log.debug("REST request to update BankAccount : {}", bankAccount);
        if (bankAccount.getId() == null) {
            return createBankAccount(bankAccount);
        }
        BankAccount result = bankAccountRepository.save(bankAccount);
        bankAccountSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bankAccount", bankAccount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bank-accounts : get all the bankAccounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bankAccounts in body
     */
    @GetMapping("/bank-accounts")
    @Timed
    public List<BankAccount> getAllBankAccounts() {
        log.debug("REST request to get all BankAccounts");
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return bankAccounts;
    }

    /**
     * GET  /bank-accounts/:id : get the "id" bankAccount.
     *
     * @param id the id of the bankAccount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bankAccount, or with status 404 (Not Found)
     */
    @GetMapping("/bank-accounts/{id}")
    @Timed
    public ResponseEntity<BankAccount> getBankAccount(@PathVariable Long id) {
        log.debug("REST request to get BankAccount : {}", id);
        BankAccount bankAccount = bankAccountRepository.findOne(id);
        return Optional.ofNullable(bankAccount)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bank-accounts/:id : delete the "id" bankAccount.
     *
     * @param id the id of the bankAccount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bank-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteBankAccount(@PathVariable Long id) {
        log.debug("REST request to delete BankAccount : {}", id);
        bankAccountRepository.delete(id);
        bankAccountSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bankAccount", id.toString())).build();
    }

    /**
     * SEARCH  /_search/bank-accounts?query=:query : search for the bankAccount corresponding
     * to the query.
     *
     * @param query the query of the bankAccount search 
     * @return the result of the search
     */
    @GetMapping("/_search/bank-accounts")
    @Timed
    public List<BankAccount> searchBankAccounts(@RequestParam String query) {
        log.debug("REST request to search BankAccounts for query {}", query);
        return StreamSupport
            .stream(bankAccountSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
