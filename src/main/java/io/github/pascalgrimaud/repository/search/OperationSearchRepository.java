package io.github.pascalgrimaud.repository.search;

import io.github.pascalgrimaud.domain.Operation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Operation entity.
 */
public interface OperationSearchRepository extends ElasticsearchRepository<Operation, Long> {
}
