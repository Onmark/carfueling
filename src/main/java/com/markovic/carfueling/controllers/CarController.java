package com.markovic.carfueling.controllers;

import com.markovic.carfueling.entities.Car;
import com.markovic.carfueling.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // GET all cars
    @GetMapping
    public List<Car> getAllCars() {
        return carService.findAll();
    }

    // GET one car (with fuelings)
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCar(@PathVariable Long id) {
        return carService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE car
    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carService.save(car);
    }

    // UPDATE car
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(
            @PathVariable Long id,
            @RequestBody Car updatedCar
    ) {
        return carService.findById(id)
                .map(car -> {
                    car.setFullName(updatedCar.getFullName());
                    car.setFuel(updatedCar.getFuel());
                    car.setProductionYear(updatedCar.getProductionYear());
                    car.setOwner(updatedCar.getOwner());
                    return ResponseEntity.ok(carService.save(car));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE car
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCar(@PathVariable Long id) {
        return carService.findById(id)
                .map(car -> {
                    carService.delete(car);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
