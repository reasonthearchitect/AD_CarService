package com.rta.carstore.facade.impl;

import com.rta.carstore.business.ICarBusiness;
import com.rta.carstore.domain.search.Car;
import com.rta.carstore.facade.ICarFacade;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Facade code for the Car.
 */
@Service
public class CarFacade implements ICarFacade {

    //@Inject private CarMapper carMapper;

    @Inject
    private ICarBusiness carBusiness;

    @Override
    @Transactional
    public Car save(Car car) {
        return this.carBusiness.save(car);
    }

    @Override
    @Transactional(readOnly = true)
    public Car findOne(String id){
       return this.carBusiness.findOne(id);
    }

    @Override
    @Transactional
    public void delete(Car car) {
    	this.carBusiness.delete(car);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Car> findAll(Pageable pageable) {
    	return this.carBusiness.findAll(pageable);
    }
}
