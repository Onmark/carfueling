package com.markovic.carfueling.controllers;

import com.markovic.carfueling.entities.Car;
import com.markovic.carfueling.entities.Fueling;

import com.markovic.carfueling.services.CarService;
import com.markovic.carfueling.services.FuelingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.Link;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    private CarService carService;
    private FuelingService fuelingService;

    @Autowired
    public CarController(
            CarService carService,FuelingService fuelingService) {
        this.carService = carService;
        this.fuelingService=fuelingService;
    }

    /*
        Listing cars
    */
    @GetMapping(value="/")
    public String cars(Model model) {

        List<Car> cars = carService.findAll();
        if (cars != null) {
            model.addAttribute("cars", cars);
        }
        return "cars/cars";
    }

     /*
        Car submitting
     */

    @GetMapping("/submit")
    public String submitCarForm(Model model) {
        model.addAttribute("car",new Car());
        return "cars/submitCar";
    }


    @PostMapping("/submit")
    public String createCar(@Valid Car car, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if( bindingResult.hasErrors() ) {
            logger.info("Validation errors were found while submitting a new car.");
            model.addAttribute("car",car);
            return "cars/submitCar";
        } else {
            // save our link
            carService.save(car);
            logger.info("New car was saved successfully");
            redirectAttributes
                    .addAttribute("id",car.getId())
                    .addFlashAttribute("success",true);
            return "redirect:/";
        }
    }

    /*
        Car deleting
     */
    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        Optional<Car> car = carService.findById(id);
        Car currentCar = car.get();
        carService.delete(currentCar);
        return "redirect:/";
    }

    /*
        Car updating
     */
    @GetMapping("/update/{id}")
    public String updateCarForm(@PathVariable Long id,Model model) {
        Optional<Car> car = carService.findById(id);
        Car currentCar = car.get();
        model.addAttribute("car",currentCar);
        return "cars/submitCar";
    }

    @PostMapping("/update/{id}")
    public String updateCar(@Valid Car car, Model model) {
        carService.save(car);
        return "redirect:/";
    }




    /*
        Fuelings per car id
     */
    @GetMapping("/{id}")
    public String fuelings(@PathVariable Long id, Model model) {
        Optional<Car> car = carService.findById(id);
        if( car.isPresent() ) {
            Car currentCar = car.get();
            Fueling fueling = new Fueling();
            fueling.setCar(currentCar);
            model.addAttribute("fueling",fueling);
            model.addAttribute("car",currentCar);
            return "fuelings/fuelings";
        } else {
            return "redirect:/";
        }
    }

    /*
        Fueling submitting
     */

    @GetMapping("/{id}/fuelings/submit")
    public String submitFuelingForm(@PathVariable Long id,Model model) {
        Optional<Car> car = carService.findById(id);
        if( car.isPresent() ) {
            Car currentCar = car.get();
        Fueling fueling = new Fueling();
        fueling.setCar(currentCar);
        model.addAttribute("fueling",fueling);
        model.addAttribute("car",currentCar);
        return "fuelings/submitFueling";
    } else {
        return "redirect:/";
    }
    }

    @PostMapping("/{id}/fuelings/submit")
    public String createFueling(@PathVariable Long id, @Valid Fueling fueling,BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if( bindingResult.hasErrors() ) {
            logger.info("Validation errors were found while submitting a new car.");
            model.addAttribute("fueling",fueling);
            model.addAttribute("car",carService.findById(id).get());
            return "fuelings/submitFueling";
        } else {
            // save our link
            fuelingService.save(fueling);
            logger.info("New fueling was saved successfully");
            redirectAttributes
                    .addAttribute("id",fueling.getId())
                    .addAttribute("car",carService.findById(id).get())
                    .addFlashAttribute("success",true);
            return "redirect:/" + fueling.getCar().getId();
        }
    }

    /*
    Fueling deleting

    @GetMapping("{id}/delete/{id}")
    public String deleteFueling(@PathVariable Long id) {
        Optional<Car> car = carService.findById(id);
        Car currentCar = car.get();
        carService.delete(currentCar);
        return "redirect:/";
    }

    /*
        Fueling updating

    @GetMapping("{id}/update/{id}")
    public String updateFuelingForm(@PathVariable Long id,Model model) {
        Optional<Car> car = carService.findById(id);
        Car currentCar = car.get();
        model.addAttribute("car",currentCar);
        return "cars/submitCar";
    }

    @PostMapping("{id}/update/{id}")
    public String updateFueling(@Valid Car car, Model model) {
        carService.save(car);
        return "redirect:/";
    }
    */
}

