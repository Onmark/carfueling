package com.markovic.carfueling.services;

import com.markovic.carfueling.entities.Car;
import com.markovic.carfueling.entities.Fueling;
import com.markovic.carfueling.repositories.FuelingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuelingService {

    private final FuelingRepository fuelingRepository;

    public FuelingService(FuelingRepository fuelingRepository) {
        this.fuelingRepository = fuelingRepository;
    }

    public Fueling save(Fueling fueling) {
        return fuelingRepository.save(fueling);
    }

    public void delete(Fueling fueling) {
        fuelingRepository.delete(fueling);
    }

    public Optional<Fueling> findById(Long id) {
        return fuelingRepository.findById(id);
    }


}
