package com.markovic.carfueling.controllers;

import com.markovic.carfueling.entities.Car;
import com.markovic.carfueling.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/")
public class CarController {

    private CarRepository carRepository;

    @Autowired
    public CarController(
            CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping(value="/")
    public String cars(String fullName, Model model) {

        List<Car> cars = carRepository.findAll();
        if (cars != null) {
            model.addAttribute("cars", cars);
        }
        return "cars";
    }


    @PostMapping(value="/")
    public String addToCars(Car car) {
        carRepository.save(car);
        return "redirect:/";
    }
}

