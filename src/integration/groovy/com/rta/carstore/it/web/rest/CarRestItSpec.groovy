package com.rta.carstore.it.web.rest

import com.rta.carstore.domain.search.Car
import com.rta.carstore.it.AbstractItTest
import com.rta.carstore.repository.search.ICarSearchRepository
import com.rta.carstore.web.rest.CarRest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus

/**
 * Generated
 */
class CarRestItSpec extends AbstractItTest {

    @Autowired
    CarRest carRest

    @Autowired
    ICarSearchRepository carSearchRepository

    def setup() {
        this.carSearchRepository.deleteAll()
    }

    @Test
    def "make sure I cannot post a record with an id"() {

        when:
        def response = this.carRest.createCar(new Car(id: "1"))

        then:
        response.getStatusCode() == HttpStatus.BAD_REQUEST
        response.getHeaders().get("Failure") != null
    }

    @Test
    def "make sure i can post a car without an id"() {

        when:
        def response = this.carRest.createCar(new Car())

        then:
        response.getStatusCode() == HttpStatus.CREATED
        response.getBody().id != null
    }

    @Test
    def "make sure I cannot put a record with an id"() {

        when:
        def response = this.carRest.updateCar(new Car(id: "1"))

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody().id != null
    }

    @Test
    def "make sure i can put a car without an id"() {

        when:
        def response = this.carRest.updateCar(new Car())

        then:
        response.getStatusCode() == HttpStatus.CREATED
        response.getBody().id != null
    }

    @Test
    def "returns a no content status code and message on the header"() {

        when:
        def response = this.carRest.deleteCar("10000000")

        then:
        response.getStatusCode() == HttpStatus.NO_CONTENT
        response.getHeaders().get('failure') != null
    }

    @Test
    def "returns an ok status code when deleting a car that exists"() {

        setup:
        def car = this.carSearchRepository.save(new Car())

        when:
        def response = this.carRest.deleteCar(car.id)

        then:
        car.id
        response.getStatusCode() == HttpStatus.OK
        this.carSearchRepository.findOne(car.id) == null
    }

    @Test
    def "cannot find the entity with the id" () {

        when:
        def response = this.carRest.getCar("doesNotExist")

        then:
        response.getStatusCode() == HttpStatus.NOT_FOUND
    }

    @Test
    def "finds the entity ith the id" () {

        setup:
        def rcar = this.carSearchRepository.save(new Car())

        when:
        def response = this.carRest.getCar(rcar.id)

        then:
        response.getStatusCode() == HttpStatus.OK
    }

    @Test
    def "we should find no records but get a status of ok"() {

        when:
        def response = this.carRest.getAllCars(new PageRequest(0, 1))

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody().size() == 0
        response.getHeaders().get('X-Total-Count').get(0)   == '0'
    }

    @Test
    def "we should find no records but get a status of ok and be redirected to the previous page"() {

        when:
        def response = this.carRest.getAllCars(new PageRequest(1, 1))

        then:
        response.getStatusCode() == HttpStatus.OK
        response.getBody().size() == 0
        response.getHeaders().get('X-Total-Count').get(0)   == '0'
    }

    @Test
    def "we should find three pages and all the headers starting with the second page"() {

        setup:
        this.carSearchRepository.save(new Car())
        this.carSearchRepository.save(new Car())
        this.carSearchRepository.save(new Car())

        when:
        def response = this.carRest.getAllCars(new PageRequest(1, 1))

        then:
        response.getStatusCode()    == HttpStatus.OK
        response.getBody().id       != null
        response.getHeaders().get('X-Total-Count').get(0)   == '3'
    }
}
