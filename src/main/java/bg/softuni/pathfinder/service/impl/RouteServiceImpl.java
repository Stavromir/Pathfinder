package bg.softuni.pathfinder.service.impl;

import bg.softuni.pathfinder.model.entity.PictureEntity;
import bg.softuni.pathfinder.model.entity.RouteEntity;
import bg.softuni.pathfinder.model.service.RouteServiceModel;
import bg.softuni.pathfinder.model.view.RouteDetailsViewModel;
import bg.softuni.pathfinder.model.view.RouteViewModel;
import bg.softuni.pathfinder.repository.RouteRepository;
import bg.softuni.pathfinder.service.CategoryService;
import bg.softuni.pathfinder.service.RouteService;
import bg.softuni.pathfinder.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public RouteServiceImpl(RouteRepository routeRepository,
                            UserService userService,
                            CategoryService categoryService,
                            ModelMapper modelMapper) {
        this.routeRepository = routeRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RouteViewModel> findAllRouteView() {

        return routeRepository.findAll()
                .stream()
                .map(route -> {

                    RouteViewModel routeViewModel = modelMapper.map(route, RouteViewModel.class);

                    if (route.getPictures().isEmpty()) {
                        routeViewModel.setPictureUrl("/images/pic4.jpg");
                    } else {
                        routeViewModel.setPictureUrl(route.getPictures().stream().findFirst().get().getUrl());
                    }

                    return routeViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addNewRoute(RouteServiceModel routeServiceModel) {
        RouteEntity map = modelMapper.map(routeServiceModel, RouteEntity.class);
        routeRepository.save(map);
    }


    @Override
    public RouteDetailsViewModel findRouteById(Long id) {
        return routeRepository.findById(id)
                .map(route -> modelMapper.map(route, RouteDetailsViewModel.class))
                .orElse(null);
    }
}
