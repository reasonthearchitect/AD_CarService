package com.rta.carstore.web.rest

import com.codahale.metrics.annotation.Timed
import com.rta.carstore.generated.web.rest.util.HeaderUtil
import com.rta.carstore.domain.search.Car
import com.rta.carstore.facade.ICarFacade
import com.rta.carstore.generated.web.rest.util.PaginationUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpHeaders

import javax.inject.Inject
import java.net.URI
import java.net.URISyntaxException
import java.util.List
import java.util.Optional

@RestController
@RequestMapping("/api")
public class CarRest {

	@Inject
	private ICarFacade carFacade;

	@RequestMapping(value = "/cars",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Car> createCar(@RequestBody Car car) throws URISyntaxException {
    	if (car.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new car cannot already have an ID").body(null);
        }
        Car result = this.carFacade.save(car);
        return ResponseEntity.created(new URI("/api/v1/cars/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("car", result.getId().toString()))
                .body(result);
    }

    @RequestMapping(value = "/cars",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Car> updateCar(@RequestBody Car car) throws URISyntaxException {
        //log.debug("REST request to update Car : {}", bird);
        if (car.getId() == null) {
            return createCar(car);
        }
        Car result = this.carFacade.save(car);
        return ResponseEntity.ok()
        		.headers(HeaderUtil.createEntityUpdateAlert("car", result.getId().toString()))
                .body(result);
    }

    @RequestMapping(value = "/cars/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCar(@PathVariable String id) {
        Car car = this.carFacade.findOne(id)
        ResponseEntity result = null;
        if (car == null) {
            result = ResponseEntity.noContent().header('failure', 'entity does not exist').body(null)
        } else {
            this.carFacade.delete(car);
            result = ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("car", id.toString())).build();
        }
        return result
    }

    @RequestMapping(value = "/cars/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Car> getCar(@PathVariable String id) {
        Car car = carFacade.findOne(id)
        ResponseEntity result = null;
        if (car != null) {
            result = new ResponseEntity<>(car, HttpStatus.OK)
        } else {
            result = new ResponseEntity<>(HttpStatus.NOT_FOUND)
        }
        return result
    }
    /**
     * GET  /cars -> get all the cars.
     */
    @RequestMapping(value = "/cars",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Car>> getAllCars(Pageable pageable)
            throws URISyntaxException {
        Page<Car> cars = this.carFacade.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(cars, "/api/v1/cars");
        return new ResponseEntity<>(cars.getContent(), headers, HttpStatus.OK);
    }
}