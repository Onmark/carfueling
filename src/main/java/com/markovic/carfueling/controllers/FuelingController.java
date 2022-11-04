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
import java.util.Optional;

@Controller
public class FuelingController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    private CarService carService;
    private FuelingService fuelingService;

    @Autowired
    public FuelingController(
            CarService carService, FuelingService fuelingService) {
        this.carService = carService;
        this.fuelingService = fuelingService;
    }


    /*
        Fuelings per car id
     */

    @GetMapping("/{id}")
    public String fuelings(@PathVariable Long id, Model model) {
        Optional<Car> car = carService.findById(id);
        if (car.isPresent()) {
            Car currentCar = car.get();
            Fueling fueling = new Fueling();
            fueling.setCar(currentCar);
            model.addAttribute("fueling", fueling);
            model.addAttribute("car", currentCar);
            return "fuelings/fuelings";
        } else {
            return "redirect:/";
        }
    }


    /*
        Fueling submitting
     */

    @GetMapping("/{carId}/fuelings/submit")
    public String submitFuelingForm(@PathVariable Long carId, Model model) {
        Optional<Car> car = carService.findById(carId);
        if (car.isPresent()) {
            Car currentCar = car.get();
            Fueling fueling = new Fueling();
            fueling.setCar(currentCar);
            model.addAttribute("fueling", fueling);
            model.addAttribute("car", currentCar);
            return "fuelings/submitFueling";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/{carId}/fuelings/submit")
    public String createFueling(@PathVariable Long carId, @Valid Fueling fueling, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while submitting a new car.");
            model.addAttribute("fueling", fueling);
            model.addAttribute("car", carService.findById(carId).get());
            return "fuelings/submitFueling";
        } else {
            fuelingService.save(fueling);
            logger.info("New fueling was saved successfully");
            redirectAttributes
                    .addAttribute("id", fueling.getId())
                    .addAttribute("car", carService.findById(carId).get())
                    .addFlashAttribute("success", true);
            return "redirect:/" + String.valueOf(carId);
        }
    }

    /*
    Fueling deleting
    */
    @GetMapping("{carId}/delete/{id}")
    public String deleteFueling(@PathVariable Long carId, @PathVariable Long id) {
        Optional<Fueling> fueling = fuelingService.findById(id);
        Fueling currentFueling = fueling.get();
        fuelingService.delete(currentFueling);
        return "redirect:/" + String.valueOf(carId);
    }

    /*
        Fueling updating
    */
    @GetMapping("{carId}/update/{id}")
    public String updateFuelingForm(@PathVariable Long carId, @PathVariable Long id, Model model) {
        Optional<Fueling> fueling = fuelingService.findById(id);
        Fueling currentFueling = fueling.get();
        model.addAttribute("fueling", currentFueling);
        model.addAttribute("car", currentFueling.getCar());
        return "fuelings/submitFueling";
    }

    @PostMapping("{carId}/update/{id}")
    public String updateFueling(@PathVariable Long carId, @Valid Fueling fueling, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while submitting a new car.");
            model.addAttribute("fueling", fueling);
            model.addAttribute("car", carService.findById(carId).get());
            return "fuelings/submitFueling";
        } else {
            fuelingService.save(fueling);
            logger.info("New fueling was saved successfully");
            redirectAttributes
                    .addAttribute("id", fueling.getId())
                    .addAttribute("car", carService.findById(carId).get())
                    .addFlashAttribute("update", true);
            return "redirect:/" + String.valueOf(carId);
        }
    }

}
