package com.rta.carstore.business;

import com.rta.carstore.domain.search.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

interface ICarBusiness {

	Car findOne(String id);

	Car save(Car car);

	Page<Car> findAll(Pageable pageable);

	void delete(Car car);
}