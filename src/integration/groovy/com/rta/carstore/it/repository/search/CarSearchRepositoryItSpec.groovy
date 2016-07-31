package com.rta.carstore.it.repository.search

import com.rta.carstore.it.AbstractItTest
import com.rta.carstore.domain.search.Car
import com.rta.carstore.repository.search.ICarSearchRepository

import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest

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

    @Test
    def "to filter out stuff we do not need from the list based on Ids"() {

        setup:
        this.carSearchRepository.save([vin: "VIN1"] as Car)
        this.carSearchRepository.save([vin: "VIN2"] as Car)

        when:
        def carpage = this.carSearchRepository.findByVinNotIn(["VIN1"] as List<String>, new PageRequest(0, 20))

        then:
        1 == carpage.getContent().size()

        cleanup:
        this.carSearchRepository.deleteAll()
    }
}