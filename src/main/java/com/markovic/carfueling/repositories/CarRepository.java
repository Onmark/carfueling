package com.markovic.carfueling.repositories;


import com.markovic.carfueling.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {


}