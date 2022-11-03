package com.markovic.carfueling.repositories;


import com.markovic.carfueling.entities.Fueling;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelingRepository extends JpaRepository<Fueling, Long> {


}