package com.markovic.carfueling.controllers;

import com.markovic.carfueling.entities.Car;
import com.markovic.carfueling.entities.Fueling;
import com.markovic.carfueling.services.CarService;
import com.markovic.carfueling.services.FuelingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
            CarService carService, FuelingService fuelingService) {
        this.carService = carService;
        this.fuelingService = fuelingService;
    }

    /*
        Listing cars
    */
    @GetMapping(value = "/")
    public String cars(Model model) {

        List<Car> cars = carService.findAll();

        if (cars != null) {
            String[][] moneySpent = carService.fuelMoneySpent();
            model.addAttribute("cars", cars);
            model.addAttribute("moneySpent", moneySpent);
        }
        return "cars/cars";
    }

     /*
        Car submitting
     */

    @GetMapping("/submit")
    public String submitCarForm(Model model) {
        model.addAttribute("car", new Car());
        return "cars/submitCar";
    }


    @PostMapping("/submit")
    public String createCar(@Valid Car car, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while submitting a new car.");
            model.addAttribute("car", car);
            return "cars/submitCar";
        } else {
            carService.save(car);
            logger.info("New car was saved successfully");
            redirectAttributes
                    .addAttribute("id", car.getId())
                    .addFlashAttribute("success", true);
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
        for (Fueling fueling : currentCar.getFuelings()) {
            fuelingService.delete(fueling);
        }
        carService.delete(currentCar);
        return "redirect:/";
    }

    /*
        Car updating
     */
    @GetMapping("/update/{id}")
    public String updateCarForm(@PathVariable Long id, Model model) {
        Optional<Car> car = carService.findById(id);
        Car currentCar = car.get();
        model.addAttribute("car", currentCar);
        return "cars/submitCar";
    }

    @PostMapping("/update/{id}")
    public String updateCar(@Valid Car car, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while updating a car.");
            model.addAttribute("car", car);
            return "cars/submitCar";
        } else {
            carService.save(car);
            logger.info("Car was updated successfully");
            redirectAttributes
                    .addAttribute("id", car.getId())
                    .addFlashAttribute("update", true);
            return "redirect:/";
        }
    }

}

