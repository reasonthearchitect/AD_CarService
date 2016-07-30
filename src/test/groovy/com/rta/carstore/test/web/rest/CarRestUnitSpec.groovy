package com.rta.carstore.test.web.rest;

import com.rta.carstore.web.rest.CarRest
import com.rta.carstore.facade.ICarFacade
import com.rta.carstore.domain.search.Car
import org.springframework.http.HttpStatus

import spock.lang.Specification

public class CarRestUnitSpec extends Specification {

	CarRest carRest;

	def setup() {
		this.carRest = new CarRest();
	}

	def "simple unit test for creating an entity through rest"() {

		setup:
		def carFacade = Mock(ICarFacade)
		this.carRest.carFacade = carFacade;
		def car = [id:"1"] as Car;

		when:
		def response = this.carRest.createCar([] as Car);

		then: 
		1 * carFacade.save(_) >> car;
		response != null;
		response.getBody().id == "1";
	}

	def "make sure that we cannot post an entity with an id"() {

		setup:
		def carFacade = Mock(ICarFacade)
		this.carRest.carFacade = carFacade;

		when:
		def response = this.carRest.createCar([id:"1"] as Car);

		then:
		0 * carFacade.save(_);
		response != null;
		response.getBody() == null;
	}

	def "make sure that we CAN update an entity withOUT an id"() {

		setup:
		def carFacade = Mock(ICarFacade)
		this.carRest.carFacade = carFacade;
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
		def carFacade = Mock(ICarFacade)
		this.carRest.carFacade = carFacade;
		def car = [id:"1"] as Car;

		when:
		def response = this.carRest.updateCar([] as Car);

		then:
		1 * carFacade.save(_) >> car;
		response != null;
		response.getBody().id == "1";
	}

	def "delete an entity through the rest Api"(){

		setup:
		def carFacade = Mock(ICarFacade)
		this.carRest.carFacade = carFacade;

		when:
		def result = this.carRest.deleteCar("1");

		then:
		1 * carFacade.findOne(_) >> new Car()
		1 * carFacade.delete(_)
		result != null;
	}

	def "delete not called because no entity with the id exists"(){

		setup:
		def carFacade = Mock(ICarFacade)
		this.carRest.carFacade = carFacade;

		when:
		def result = this.carRest.deleteCar("1");

		then:
		1 * carFacade.findOne(_)
		0 * carFacade.delete(_)
		result != null;
	}
}