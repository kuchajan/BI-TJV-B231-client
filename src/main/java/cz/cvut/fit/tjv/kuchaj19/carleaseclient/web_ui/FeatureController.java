package cz.cvut.fit.tjv.kuchaj19.carleaseclient.web_ui;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Feature;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.service.FeatureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Controller
@RequestMapping("/features")
public class FeatureController {
    private final FeatureService featureService;

    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("allFeatures", featureService.readAll());
        return "feature/features";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        featureService.setCurrentFeature(id);
        Optional<Feature> feature = featureService.readOne();
        if(feature.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Feature was not found");
            return list(model);
        }

        model.addAttribute("feature",feature.get());
        return "feature/editFeature";
    }

    @PostMapping("/edit")
    public String editSubmit(Model model, @ModelAttribute Feature data) {
        featureService.setCurrentFeature(data.getId());
        try {
            featureService.update(data);
        } catch (HttpClientErrorException.NotFound e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Feature was not found");
        }
        return list(model);
    }

    @GetMapping("/create")
    public String create() {
        return "feature/createFeature";
    }

    @PostMapping("/create")
    public String createSubmit(Model model, @ModelAttribute Feature data) {
        try {
            featureService.create(data);
        } catch (HttpClientErrorException.Conflict e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "There was a conflict with another existing feature");
        } catch (HttpClientErrorException.BadRequest e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "One or more field contained invalid data");
        }
        return list(model);
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam Long id) {
        try {
            featureService.setCurrentFeature(id);
            featureService.delete();
        } catch (HttpClientErrorException.NotFound e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", String.format("Feature with id %d was not found", id));
        }
        return list(model);
    }
}
