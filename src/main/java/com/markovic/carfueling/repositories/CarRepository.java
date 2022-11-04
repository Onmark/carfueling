package com.markovic.carfueling.repositories;


import com.markovic.carfueling.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = "SELECT C.FUEL, SUM(F.LITERS*F.PRICE_PER_LITER)\n" +
            "FROM CAR C \n" +
            "INNER JOIN FUELING F on F.CAR_ID=C.ID\n" +
            "GROUP BY C.FUEL", nativeQuery = true)
    String[][] fuelMoneySpent();
}