package com.rta.carstore.business.impl;

import com.rta.carstore.business.ICarBusiness;
import com.rta.carstore.domain.search.Car;
import com.rta.carstore.repository.search.ICarSearchRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;

@Service
class CarBusiness implements ICarBusiness {

	@Autowired(required = true)
	private ICarSearchRepository carRepository;

	@Override
	public Car findOne(String id) {
		return this.carRepository.findOne(id);
	}

	@Override
	public Car save( Car car) {
		return this.carRepository.save(car);
	}

	@Override
	public Page<Car> findAll(Pageable pageable) {
		return this.carRepository.findAll(pageable);
	}

	@Override
	public void delete(Car car) {
		this.carRepository.delete(car.id)
	}
}