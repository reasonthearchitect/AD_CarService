package com.rta.carstore.test.facade

import com.rta.carstore.business.ICarBusiness
import com.rta.carstore.domain.search.Car
import com.rta.carstore.facade.ICarFacade
import com.rta.carstore.facade.impl.CarFacade
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

/**
 * Unit spec for the Car facade.
 */
class CarFacadeUnitSpec extends Specification {

    ICarFacade carFacade;

    def setup() {
        this.carFacade = new CarFacade();
    }

    def "simple unit test from the generation framework for the save function provided"() {

        setup:
        def carBusiness = Mock(ICarBusiness);
        this.carFacade.carBusiness = carBusiness;
        def car = [id: "1"]as Car;

        when:
        def rcar = this.carFacade.save(car);

        then:
        1 * carBusiness.save(_) >> car;
    }

    def "simple unit test to find one"(){

        setup:
        def carBusiness = Mock(ICarBusiness);
        this.carFacade.carBusiness = carBusiness;
        def rcar =[id: "1"]as Car;

        when:
        def result = this.carFacade.findOne("1");

        then:
        1 * carBusiness.findOne(_) >> rcar;
        rcar != null;
    }

    def "simple unit test for deleting an entity"() {

        setup:
        def carBusiness = Mock(ICarBusiness);
        this.carFacade.carBusiness = carBusiness;

        when:
        this.carFacade.delete([] as Car);

        then:
        1 * carBusiness.delete(_);
    }

    def "simple unit test for finding all the objects with pagination"() {

        setup:
        def carBusiness = Mock(ICarBusiness);
        this.carFacade.carBusiness = carBusiness;
        PageImpl<Car> page = new PageImpl<Car>([[id:"1"] as Car, [id:"2"] as Car],new PageRequest(2, 2), 12);

        when:
        Page<Car> cars = this.carFacade.findAll(new PageRequest(1, 1))

        then:
        1 * carBusiness.findAll(_) >> page;
        cars.getContent().size() == 2;
    }
}
