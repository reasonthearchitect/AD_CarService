package com.rta.carstore.web.rest

import com.codahale.metrics.annotation.Timed
import com.rta.carstore.domain.search.CarListRequest
import com.rta.carstore.generated.web.rest.util.HeaderUtil
import com.rta.carstore.domain.search.Car
import com.rta.carstore.facade.ICarFacade
import com.rta.carstore.generated.web.rest.util.PaginationUtil
import com.rta.carstore.repository.search.ICarSearchRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpHeaders

import javax.inject.Inject

@RestController
@RequestMapping("/api")
public class CarRest {

	@Inject
	private ICarFacade carFacade;

    @Autowired ICarSearchRepository carSearchRepository;

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
    public ResponseEntity<List<Car>> getAllCars(Pageable pageable)
            throws URISyntaxException {
        Page<Car> cars = this.carFacade.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(cars, "/api/cars");
        return new ResponseEntity<>(cars.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/carlistnotwatching",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Car>> filterCarsByVinNotIn(@RequestBody CarListRequest request) {
        PageRequest pr = new PageRequest(0, 200);
        if (request.vins == null || request.vins.isEmpty()) {
            return getAllCars(pr);
        } else {
            Page<Car> cars = this.carSearchRepository.findByVinNotIn(request.vins, pr);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(cars, "/api/carlistnotwatching");
            return new ResponseEntity<>(cars.getContent(), headers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/carlistwatching",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Car>> filterCarsByVinIn(@RequestBody CarListRequest request) {
        PageRequest pr = new PageRequest(0, 200);
        if (request.vins == null || request.vins.isEmpty()) {
            return this.getAllCars(pr);
        } else {
            Page<Car> cars = this.carSearchRepository.findByVinIn(request.vins, pr);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(cars, "/api/carlistwatching");
            return new ResponseEntity<>(cars.getContent(), headers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/filteredsellerscars/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Car>> carListForSeller(@PathVariable String name) {
        PageRequest pr = new PageRequest(0, 200);
    
        Page<Car> cars = this.carSearchRepository.findAllBySeller(name, pr);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(cars, "/api/filteredsellerscars");
        return new ResponseEntity<>(cars.getContent(), headers, HttpStatus.OK);
    }
}