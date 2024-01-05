package cz.cvut.fit.tjv.kuchaj19.carleaseclient.web_ui;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Reservation;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.ReservationDTO;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final CarService carService;
    private final FeatureService featureService;
    private final MakeService makeService;
    private final UserService userService;
    ReservationController(ReservationService reservationService, CarService carService, FeatureService featureService, MakeService makeService, UserService userService) {
        this.reservationService = reservationService;
        this.carService = carService;
        this.featureService = featureService;
        this.makeService = makeService;
        this.userService = userService;
    }

    @GetMapping
    public String allOrFiltered(Model model, @RequestParam Optional<Long> user, @RequestParam Optional<Long> car) {
        if(user.isPresent() || car.isPresent()) {
            reservationService.setFilteredReservations(user, car);
            try {
                model.addAttribute("allReservations", reservationService.readFiltered());
            } catch (HttpClientErrorException.NotFound e) {
                model.addAttribute("allReservations", reservationService.readAll());
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "One of the search parameters is invalid");
            }
        } else {
            model.addAttribute("allReservations", reservationService.readAll());
        }
        return "reservation/reservations";
    }

    public String list(Model model) {
        model.addAttribute("allReservations", reservationService.readAll());
        return "reservation/reservations";
    }

    @GetMapping("/create")
    public String create(Model model, @RequestParam Optional<List<Long>> make, @RequestParam Optional<List<Long>> feature, @RequestParam Optional<Long> minPrice, @RequestParam Optional<Long> maxPrice, @RequestParam Optional<Long> timeStart, @RequestParam Optional<Long> timeEnd) {
        if(timeStart.isEmpty() || timeEnd.isEmpty()) {
            if(timeStart.isPresent() || timeEnd.isPresent()) {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Need to specify both time start and time end");
            }
        } else {
            model.addAttribute("searched", true);
            model.addAttribute("timeStart", timeStart.get());
            model.addAttribute("timeEnd", timeEnd.get());

            model.addAttribute("allUsers", userService.readAll());
            carService.setFilteredCars(make,feature,minPrice,maxPrice,timeStart,timeEnd);
            try {
                model.addAttribute("allCars", carService.readFiltered());
                // todo
            } catch (HttpClientErrorException.NotFound e) {
                model.addAttribute("searched", false);
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Something happened");
            }
        }
        model.addAttribute("allFeatures", featureService.readAll());
        model.addAttribute("allMakes", makeService.readAll());
        return "reservation/createReservation";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        reservationService.setCurrentReservation(id);
        Optional<Reservation> reservation = reservationService.readOne();
        if(reservation.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Reservation was not found");
            return list(model);
        }

        model.addAttribute("reservation", reservation.get());
        model.addAttribute("allCars", carService.readAll());
        model.addAttribute("allUsers", userService.readAll());
        return "reservation/editReservation";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam Long id) {
        reservationService.setCurrentReservation(id);
        try {
            reservationService.delete();
        }
        catch (HttpClientErrorException.NotFound e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Reservation was not found");
        }
        return list(model);
    }

    @PostMapping("/create")
    public String createSubmit(Model model, @ModelAttribute ReservationDTO data) {
        try {
            reservationService.create(data);
        } catch (HttpClientErrorException.Conflict e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "There was a conflict with another existing reservation");
        } catch (HttpClientErrorException.BadRequest | IllegalArgumentException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "One or more field contained invalid data");
        }
        return list(model);
    }

    @PostMapping("/edit")
    public String editSubmit(Model model, @ModelAttribute ReservationDTO data) {
        reservationService.setCurrentReservation(data.getId());
        try {
            reservationService.update(data);
        } catch (HttpClientErrorException.Conflict e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "There was a conflict with another existing reservation");
        } catch (HttpClientErrorException.BadRequest | IllegalArgumentException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "One or more field contained invalid data");
        }
        return list(model);
    }
}
