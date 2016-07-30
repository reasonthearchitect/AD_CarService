package com.rta.carstore.facade;

import com.rta.carstore.domain.search.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for the Car facade pattern.
 */
public interface ICarFacade {

    Car save(Car car);

    Car findOne(String id);

    void delete(Car car);

    Page<Car> findAll(Pageable pageable);
}