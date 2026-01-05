package com.markovic.carfueling.controllers;

import com.markovic.carfueling.entities.Fueling;
import com.markovic.carfueling.services.CarService;
import com.markovic.carfueling.services.FuelingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars/{carId}/fuelings")
public class FuelingController {

    private final CarService carService;
    private final FuelingService fuelingService;

    public FuelingController(CarService carService, FuelingService fuelingService) {
        this.carService = carService;
        this.fuelingService = fuelingService;
    }

    // GET fuelings for car
    @GetMapping
    public ResponseEntity<List<Fueling>> getFuelings(@PathVariable Long carId) {
        return carService.findById(carId)
                .map(car -> ResponseEntity.ok(car.getFuelings()))
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE fueling
    @PostMapping
    public ResponseEntity<Fueling> addFueling(
            @PathVariable Long carId,
            @RequestBody Fueling fueling
    ) {
        return carService.findById(carId)
                .map(car -> {
                    fueling.setCar(car);
                    return ResponseEntity.ok(fuelingService.save(fueling));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE fueling
    @PutMapping("/{fuelingId}")
    public ResponseEntity<Fueling> updateFueling(
            @PathVariable Long carId,
            @PathVariable Long fuelingId,
            @RequestBody Fueling updated
    ) {
        return fuelingService.findById(fuelingId)
                .filter(f -> f.getCar().getId().equals(carId))
                .map(fueling -> {
                    fueling.setStationName(updated.getStationName());
                    fueling.setLiters(updated.getLiters());
                    fueling.setPricePerLiter(updated.getPricePerLiter());
                    fueling.setDate(updated.getDate());
                    return ResponseEntity.ok(fuelingService.save(fueling));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE fueling
    @DeleteMapping("/{fuelingId}")
    public ResponseEntity<Object> deleteFueling(
            @PathVariable Long carId,
            @PathVariable Long fuelingId
    ) {
        return fuelingService.findById(fuelingId)
                .filter(fueling -> fueling.getCar().getId().equals(carId))
                .map(fueling -> {
                    fuelingService.delete(fueling);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}