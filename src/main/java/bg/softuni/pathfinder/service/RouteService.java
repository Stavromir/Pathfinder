package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.binding.RouteAddBindingModel;
import bg.softuni.pathfinder.model.view.RouteDetailsViewModel;
import bg.softuni.pathfinder.model.view.RouteViewModel;

import java.io.IOException;
import java.util.List;

public interface RouteService {
    List<RouteViewModel> findAllRouteView();

    void addNewRoute(RouteAddBindingModel routeAddBindingModel, String username) throws IOException;

    RouteDetailsViewModel findRouteById(Long id);
}
