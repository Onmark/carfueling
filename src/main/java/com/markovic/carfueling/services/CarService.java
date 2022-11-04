package com.markovic.carfueling.services;

import com.markovic.carfueling.entities.Car;
import com.markovic.carfueling.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public void delete(Car car) {
        carRepository.delete(car);
    }

    public String[][] fuelMoneySpent() {
        return carRepository.fuelMoneySpent();
    }

}
