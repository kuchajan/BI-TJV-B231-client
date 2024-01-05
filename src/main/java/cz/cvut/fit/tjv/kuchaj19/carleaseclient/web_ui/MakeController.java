package cz.cvut.fit.tjv.kuchaj19.carleaseclient.web_ui;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Make;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.service.MakeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Controller
@RequestMapping("/makes")
public class MakeController {
    private final MakeService makeService;

    public MakeController(MakeService makeService) {
        this.makeService = makeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("allMakes", makeService.readAll());
        return "make/makes";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        makeService.setCurrentMake(id);
        Optional<Make> make = makeService.readOne();
        if(make.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Make was not found");
            return list(model);
        }

        model.addAttribute("make",make.get());
        return "make/editMake";
    }

    @PostMapping("/edit")
    public String editSubmit(Model model, @ModelAttribute Make data) {
        makeService.setCurrentMake(data.getId());
        try {
            makeService.update(data);
        } catch (HttpClientErrorException.NotFound e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Make was not found");
        }
        return list(model);
    }

    @GetMapping("/create")
    public String create() {
        return "make/createMake";
    }

    @PostMapping("/create")
    public String createSubmit(Model model, @ModelAttribute Make data) {
        try {
            makeService.create(data);
        } catch (HttpClientErrorException.Conflict e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "There was a conflict with another existing make");
        } catch (HttpClientErrorException.BadRequest e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "One or more field contained invalid data");
        }
        return list(model);
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam Long id) {
        try {
            makeService.setCurrentMake(id);
            makeService.delete();
        } catch (HttpClientErrorException.NotFound e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", String.format("Make with id %d was not found", id));
        } catch (HttpClientErrorException.Forbidden e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Not allowed to delete this make, it is used in a car");
        }
        return list(model);
    }
}
