package bg.softuni.pathfinder.service.impl;

import bg.softuni.pathfinder.model.binding.RouteAddBindingModel;
import bg.softuni.pathfinder.model.entity.PictureEntity;
import bg.softuni.pathfinder.model.entity.RouteEntity;
import bg.softuni.pathfinder.model.entity.UserEntity;
import bg.softuni.pathfinder.model.service.RouteServiceModel;
import bg.softuni.pathfinder.model.view.RouteDetailsViewModel;
import bg.softuni.pathfinder.model.view.RouteViewModel;
import bg.softuni.pathfinder.repository.RouteRepository;
import bg.softuni.pathfinder.repository.UserRepository;
import bg.softuni.pathfinder.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ImageCloudService imageCloudService;
    private final PictureService pictureService;

    public RouteServiceImpl(RouteRepository routeRepository,
                            UserRepository userRepository,
                            ModelMapper modelMapper,
                            ImageCloudService imageCloudService,
                            PictureService pictureService) {
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.imageCloudService = imageCloudService;
        this.pictureService = pictureService;
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
    public void addNewRoute(RouteAddBindingModel routeAddBindingModel, String username) throws IOException {

        RouteEntity routeEntity = modelMapper.map(routeAddBindingModel,
                RouteEntity.class);
        routeEntity.setGpxCoordinates(new String(routeAddBindingModel
                .getGpxCoordinates().getBytes()));

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String picURL = imageCloudService.saveImageInCloud(routeAddBindingModel.getPicture());

        PictureEntity picture = new PictureEntity()
                .setRoute(routeEntity)
                .setTitle(routeAddBindingModel.getName())
                .setAuthor(user)
                .setUrl(picURL);

        routeEntity.setAuthor(user);
        routeEntity.getPictures().add(picture);

        routeRepository.save(routeEntity);
    }


    @Override
    public RouteDetailsViewModel findRouteById(Long id) {
        return routeRepository.findById(id)
                .map(route -> modelMapper.map(route, RouteDetailsViewModel.class))
                .orElse(null);
    }
}
