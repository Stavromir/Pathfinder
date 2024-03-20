package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.binding.RouteAddBindingModel;
import bg.softuni.pathfinder.model.entity.enums.CategoryNameEnum;
import bg.softuni.pathfinder.model.entity.enums.LevelEnum;
import bg.softuni.pathfinder.model.service.RouteServiceModel;
import bg.softuni.pathfinder.service.ImageCloudService;
import bg.softuni.pathfinder.service.RouteService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;
    private final ImageCloudService imageCloudService;
    private final ModelMapper modelMapper;

    public RouteController(RouteService routeService,
                           ImageCloudService imageCloudService,
                           ModelMapper modelMapper) {
        this.routeService = routeService;
        this.imageCloudService = imageCloudService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public RouteAddBindingModel routeAddBindingModel() {
        return new RouteAddBindingModel();
    }

    @ModelAttribute(name = "levels")
    public LevelEnum[] levelEnum() {
        return LevelEnum.values();
    }

    @ModelAttribute(name = "categories")
    public CategoryNameEnum[] categoryNameEnums () {
        return CategoryNameEnum.values();
    }

    @GetMapping("/all")
    public String all(Model model) {

        model.addAttribute("routes", routeService
                .findAllRouteView());

        return "routes";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {

        model.addAttribute("route", routeService.findRouteById(id));
        model.addAttribute("id", id);

        return "route-details";
    }

    @GetMapping("/add")
    public String add() {

        return "add-route";
    }

    @PostMapping("/add")
    public String add(@Valid RouteAddBindingModel routeAddBindingModel,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      @AuthenticationPrincipal UserDetails principal) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("routeAddBindingModel", redirectAttributes)
                    .addFlashAttribute("org.springframework.validation.BindingResult.routeAddBindingModel",
                            bindingResult);

            return "redirect:addRoute";
        }

        String username = principal.getUsername();

        routeService.addNewRoute(routeAddBindingModel, username);

        return "redirect:all";
    }
}
