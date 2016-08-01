package com.rta.carstore.repository.search

import com.rta.carstore.domain.search.Car
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository


interface ICarSearchRepository extends ElasticsearchRepository<Car, String> {

    Page<Car> findByVinNotIn(Collection<String> ids, Pageable pageable);

    Page<Car> findByVinIn(Collection<String> ids, Pageable pageable);
}
