package com.markovic.carfueling.loaders;

import com.markovic.carfueling.entities.Car;
import com.markovic.carfueling.entities.Fueling;
import com.markovic.carfueling.repositories.CarRepository;
import com.markovic.carfueling.repositories.FuelingRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Component
public class DatabaseLoader implements CommandLineRunner {

    private CarRepository carRepository;
    private FuelingRepository fuelingRepository;


    public DatabaseLoader(CarRepository carRepository, FuelingRepository fuelingRepository) {
        this.carRepository = carRepository;
        this.fuelingRepository = fuelingRepository;

    }

    @Override
    public void run(String... args) {
        List<Car> cars= new ArrayList<>();
        cars.add(addCar("Hyundai I30","Diesel","2019","Ondrej"));
        cars.add(addCar("Skoda Octavia III","Benzin","2018","David"));
        cars.add(addCar("Kia CEED","Benzin","2022","Kuba"));

        for(Car car : cars) {
            for (int i = 0; i < 3; i++){
                addFueling("Benzina" + String.valueOf((int) (Math.random() * 20) + 1),
                        (int) (Math.random() * 40) + 5,
                        (int) (Math.random() * 10) + 35,
                        LocalDate.parse("2022-0"+String.valueOf((int) (Math.random() * 9) + 1 )+"-"+String.valueOf((int) (Math.random() * 20) + 10), DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)),
                        car);
        }
        }

    }


    public Car addCar(String fullName, String fuel, String productionYear, String owner) {
        Car car = new Car();
        car.setFullName(fullName);
        car.setFuel(fuel);
        car.setProductionYear(productionYear);
        car.setOwner(owner);
        carRepository.save(car);
        return car;

    }

    public void addFueling(String stationName, int liters, int pricePerLiters, LocalDate date, Car car) {
        Fueling fueling = new Fueling();
        fueling.setStationName(stationName);
        fueling.setLiters(liters);
        fueling.setPricePerLiter(pricePerLiters);
        fueling.setDate(date);
        fueling.setCar(car);
        fuelingRepository.save(fueling);
    }
}
