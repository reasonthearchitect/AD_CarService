package com.rta.carstore.test.web.rest

import com.rta.carstore.domain.search.CarListRequest
import com.rta.carstore.repository.search.ICarSearchRepository;
import com.rta.carstore.web.rest.CarRest
import com.rta.carstore.facade.ICarFacade
import com.rta.carstore.domain.search.Car
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus

import spock.lang.Specification

public class CarRestUnitSpec extends Specification {

	CarRest carRest;

	ICarFacade carFacade;

    ICarSearchRepository carSearchRepository;

	def setup() {
		this.carRest = new CarRest();
		this.carFacade = Mock(ICarFacade)
		this.carRest.carFacade = carFacade;
        this.carSearchRepository = Mock(ICarSearchRepository);
        this.carRest.carSearchRepository = carSearchRepository
	}

	def "simple unit test for creating an entity through rest"() {

		setup:
		def car = [id:"1"] as Car;

		when:
		def response = this.carRest.createCar([] as Car);

		then: 
		1 * carFacade.save(_) >> car;
		response != null;
		response.getBody().id == "1";
	}

	def "make sure that we cannot post an entity with an id"() {

		when:
		def response = this.carRest.createCar([id:"1"] as Car);

		then:
		0 * carFacade.save(_);
		response != null;
		response.getBody() == null;
	}

	def "make sure that we CAN update an entity withOUT an id"() {

		setup:
		def car = [id:"1"] as Car;

		when:
		def response = this.carRest.updateCar([] as Car);

		then:
		1 * carFacade.save(_) >> car;
		response != null;
		response.getBody().id == "1";
	}

	def "make sure that we CAN update an entity with an id"() {

		setup:
		def car = [id:"1"] as Car;

		when:
		def response = this.carRest.updateCar([id: "1"] as Car);

		then:
		1 * carFacade.save(_) >> car;
		response != null;
		response.getBody().id == "1";
	}

	def "delete an entity through the rest Api"(){

		when:
		def result = this.carRest.deleteCar("1");

		then:
		1 * carFacade.findOne(_) >> new Car()
		1 * carFacade.delete(_)
		result != null;
	}

	def "delete not called because no entity with the id exists"(){

		when:
		def result = this.carRest.deleteCar("1");

		then:
		1 * carFacade.findOne(_)
		0 * carFacade.delete(_)
		result != null;
	}

	def "find a get"() {

		when:
		def result = this.carRest.getCar("1");

		then:
		1 * carFacade.findOne(_) >> new Car(id: "1")
		result.statusCode == HttpStatus.OK
		result.getBody().id == "1"
	}

	def "no car found"() {

		when:
		def result = this.carRest.getCar("1");

		then:
		1 * carFacade.findOne(_) >> null
		result.statusCode == HttpStatus.NOT_FOUND
	}


	def "calling all cars" () {

		when:
		def result = this.carRest.getAllCars(null);

		then:
		1 * this.carFacade.findAll(_) >> new PageImpl<Car>([new Car(id: "1")]);
		result.statusCode == HttpStatus.OK
		result.body.size() == 1
	}

	def "filter cars without the vins listed in the request with null list"() {

		setup:
		def carSearchRepository = Mock(ICarSearchRepository);
        this.carRest.carSearchRepository = carSearchRepository

		when:
		def result = this.carRest.filterCarsByVinNotIn(new CarListRequest());

		then:
		0 * carSearchRepository.findByVinIn(_)
		1 * this.carFacade.findAll(_) >> new PageImpl<Car>([new Car(id: "1")]);
		result.statusCode == HttpStatus.OK
		result.body.size() == 1
	}

	def "filter cars without the vins listed in the request with empty list"() {

		setup:
		def carSearchRepository = Mock(ICarSearchRepository);
        this.carRest.carSearchRepository = carSearchRepository

		when:
		def result = this.carRest.filterCarsByVinNotIn(new CarListRequest(vins: new ArrayList<String>()));

		then:
		0 * carSearchRepository.findByVinIn(_)
		1 * this.carFacade.findAll(_) >> new PageImpl<Car>([new Car(id: "1")]);
		result.statusCode == HttpStatus.OK
		result.body.size() == 1
	}

    def "filter cars without the vins listed in the request"() {

        setup:
        def carSearchRepository = Mock(ICarSearchRepository);
        this.carRest.carSearchRepository = carSearchRepository


        when:
        def result = this.carRest.filterCarsByVinNotIn(new CarListRequest(vins: ["1"]));

        then:
        1 * carSearchRepository.findByVinNotIn(_, _) >> new PageImpl<Car>([new Car(id: "1")]);
        result.statusCode == HttpStatus.OK
        result.body.size() == 1
    }


    def "filter cars with the vins listed in the request with null list"() {

        setup:
        def carSearchRepository = Mock(ICarSearchRepository);
        this.carRest.carSearchRepository = carSearchRepository

        when:
        def result = this.carRest.filterCarsByVinIn(new CarListRequest());

        then:
        0 * carSearchRepository.findByVinIn(_)
        1 * this.carFacade.findAll(_) >> new PageImpl<Car>([new Car(id: "1")]);
        result.statusCode == HttpStatus.OK
        result.body.size() == 1
    }

    def "filter cars with the vins listed in the request with empty list"() {

        setup:
        def carSearchRepository = Mock(ICarSearchRepository);
        this.carRest.carSearchRepository = carSearchRepository

        when:
        def result = this.carRest.filterCarsByVinIn(new CarListRequest(vins: new ArrayList<String>()));

        then:
        0 * carSearchRepository.findByVinIn(_)
        1 * this.carFacade.findAll(_) >> new PageImpl<Car>([new Car(id: "1")]);
        result.statusCode == HttpStatus.OK
        result.body.size() == 1
    }

    def "filter cars with the vins listed in the request"() {

        when:
        def result = this.carRest.filterCarsByVinIn(new CarListRequest(vins: ["1"]));

        then:
        1 * carSearchRepository.findByVinIn(_, _) >> new PageImpl<Car>([new Car(id: "1")]);
        result.statusCode == HttpStatus.OK
        result.body.size() == 1
    }

    def "filter list of cars for the seller" () {

        when:
        def results = this.carRest.carListForSeller("Bob");

        then:
        1 * this.carSearchRepository.findAllBySeller(_, _) >> new PageImpl<Car>([new Car(id: "1")]);
        results.statusCode == HttpStatus.OK
        results.body.size() == 1
    }
}