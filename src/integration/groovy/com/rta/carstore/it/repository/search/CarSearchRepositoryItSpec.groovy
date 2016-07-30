package com.rta.carstore.it.repository.search

import com.rta.carstore.it.AbstractItTest
import com.rta.carstore.domain.search.Car
import com.rta.carstore.repository.search.ICarSearchRepository

import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class CarSearchRepositoryItSpec extends AbstractItTest {

    @Autowired ICarSearchRepository carSearchRepository

    @Test
    def "a very basic integration test to ensure that everything is wired together properly"() {

        when:
        def rcar = this.carSearchRepository.save(new Car())

        then:
        rcar.id != null
        this.carSearchRepository.delete(rcar.id)
    }
}