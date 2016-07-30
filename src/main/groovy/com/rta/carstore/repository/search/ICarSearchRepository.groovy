package com.rta.carstore.repository.search

import com.rta.carstore.domain.search.Car
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

/**
 * Spring Data ElasticSearch repository for the Car entity.
 */
interface ICarSearchRepository extends ElasticsearchRepository<Car, String> {
}
