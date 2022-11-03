package com.markovic.carfueling.controllers;

import com.markovic.carfueling.entities.Car;
import com.markovic.carfueling.entities.Fueling;

import com.markovic.carfueling.services.CarService;
import com.markovic.carfueling.services.FuelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class CarController {

    private CarService carService;
    private FuelingService fuelingService;

    @Autowired
    public CarController(
            CarService carService,FuelingService fuelingService) {
        this.carService = carService;
        this.fuelingService=fuelingService;
    }

    @GetMapping(value="/")
    public String cars(Model model) {

        List<Car> cars = carService.findAll();
        if (cars != null) {
            model.addAttribute("cars", cars);
        }
        return "cars";
    }


    @GetMapping("/{id}")
    public String read(@PathVariable Long id, Model model) {
        Optional<Car> car = carService.findById(id);
        if( car.isPresent() ) {
            Car currentCar = car.get();
            Fueling fueling = new Fueling();
            fueling.setCar(currentCar);
            model.addAttribute("fueling",fueling);
            model.addAttribute("car",currentCar);
            return "fuelings";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping(value="/")
    public String addComment(Car car) {
        carService.save(car);
        return "redirect:/";
    }

    @PostMapping("/{id}")
    public String addFueling(@PathVariable Long id, @Valid Fueling fueling){
            fuelingService.save(fueling);
        return "redirect:/" + fueling.getCar().getId();
    }
}

