package io.github.pascalgrimaud.repository.search;

import io.github.pascalgrimaud.domain.BankAccount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the BankAccount entity.
 */
public interface BankAccountSearchRepository extends ElasticsearchRepository<BankAccount, Long> {
}
