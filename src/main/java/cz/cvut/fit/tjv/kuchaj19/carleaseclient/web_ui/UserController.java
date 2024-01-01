package cz.cvut.fit.tjv.kuchaj19.carleaseclient.web_ui;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.User;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String allOrFiltered(Model model, @RequestParam Optional<String> email, @RequestParam Optional<String> name, @RequestParam Optional<String> phone) {
        if (email.isPresent() || name.isPresent() || phone.isPresent()) {
            userService.setFilteredUsers(email, name, phone);
            model.addAttribute("allUsers", userService.readFiltered());
        } else {
            model.addAttribute("allUsers", userService.readAll());
        }
        return "user/users";
    }


    private String list(Model model) {
        model.addAttribute("allUsers", userService.readAll());
        return "user/users";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        userService.setCurrentUser(id);
        Optional<User> user = userService.readOne();
        if(user.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "User was not found");
            return list(model);
        }

        model.addAttribute("user",user.get());
        return "user/editUser";
    }

    @PostMapping("/edit")
    public String editSubmit(Model model, @ModelAttribute User data) {
        userService.setCurrentUser(data.getId());
        try {
            userService.update(data);
        } catch (HttpClientErrorException.NotFound e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "User was not found");
        }
        return list(model);
    }

    @GetMapping("/create")
    public String create() {
        return "user/createUser";
    }

    @PostMapping("/create")
    public String createSubmit(Model model, @ModelAttribute User data) {
        try {
            userService.create(data);
        } catch (HttpClientErrorException.Conflict e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "There was a conflict with another existing user");
        } catch (HttpClientErrorException.BadRequest e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "One or more field contained invalid data");
        }
        return list(model);
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam Long id) {
        try {
            userService.setCurrentUser(id);
            userService.delete();
        } catch (HttpClientErrorException.NotFound e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", String.format("User with id %d was not found", id));
        }
        return list(model);
    }
}
