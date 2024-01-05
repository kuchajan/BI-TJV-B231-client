package cz.cvut.fit.tjv.kuchaj19.carleaseclient.web_ui;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Car;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.CarDTO;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Feature;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.service.CarService;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.service.FeatureService;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.service.MakeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final FeatureService featureService;
    private final MakeService makeService;

    public CarController(CarService carService, FeatureService featureService, MakeService makeService) {
        this.carService = carService;
        this.featureService = featureService;
        this.makeService = makeService;
    }

    @GetMapping
    public String allOrFiltered(Model model, @RequestParam Optional<List<Long>> make, @RequestParam Optional<List<Long>> feature, @RequestParam Optional<Long> minPrice, @RequestParam Optional<Long> maxPrice, @RequestParam Optional<Long> timeStart, @RequestParam Optional<Long> timeEnd) {
        if (make.isPresent() || feature.isPresent() || minPrice.isPresent() || maxPrice.isPresent() || timeStart.isPresent() || timeEnd.isPresent()) {
            carService.setFilteredCars(make, feature, minPrice, maxPrice, timeStart, timeEnd);
            try {
                model.addAttribute("allCars", carService.readFiltered());
            }
            catch (HttpClientErrorException.BadRequest e) {
                model.addAttribute("allCars", carService.readAll());
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Invalid search parameters");
            }
        } else {
            model.addAttribute("allCars", carService.readAll());
        }
        model.addAttribute("allFeatures", featureService.readAll());
        model.addAttribute("allMakes", makeService.readAll());
        return "car/cars";
    }


    private String list(Model model) {
        model.addAttribute("allCars", carService.readAll());
        model.addAttribute("allFeatures", featureService.readAll());
        model.addAttribute("allMakes", makeService.readAll());
        return "car/cars";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        carService.setCurrentCar(id);
        Optional<Car> car = carService.readOne();
        if(car.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Car was not found");
            return list(model);
        }

        model.addAttribute("allMakes", makeService.readAll());
        featureService.setFilteredFeatures(Optional.of(id), Optional.of(true));
        Collection<Feature> remainingFeatures = featureService.readFiltered();
        model.addAttribute("remainingFeatures", remainingFeatures);
        model.addAttribute("car",car.get());

        return "car/editCar";
    }

    @PostMapping("/edit")
    public String editSubmit(Model model, @ModelAttribute CarDTO data) {
        carService.setCurrentCar(data.getId());
        try {
            carService.update(data);
        } catch (HttpClientErrorException.NotFound e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Car was not found");
        }
        return list(model);
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("allMakes", makeService.readAll());
        model.addAttribute("allFeatures", featureService.readAll());
        return "car/createCar";
    }

    @PostMapping("/create")
    public String createSubmit(Model model, @ModelAttribute CarDTO data) {
        try {
            carService.create(data);
        } catch (HttpClientErrorException.Conflict e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "There was a conflict with another existing car");
        } catch (HttpClientErrorException.BadRequest | IllegalArgumentException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "One or more field contained invalid data");
        }
        return list(model);
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam Long id) {
        carService.setCurrentCar(id);
        try {
            carService.delete();
        } catch (HttpClientErrorException.NotFound e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", String.format("Car with id %d was not found", id));
        } catch (HttpClientErrorException.Forbidden e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Not allowed to delete this car, it is used in a reservation");
        }
        return list(model);
    }
}
