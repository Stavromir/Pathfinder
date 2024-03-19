package bg.softuni.pathfinder.service.impl;

import bg.softuni.pathfinder.model.binding.CommentAddDTO;
import bg.softuni.pathfinder.model.entity.CommentEntity;
import bg.softuni.pathfinder.model.entity.RouteEntity;
import bg.softuni.pathfinder.model.view.RouteDetailsViewModel;
import bg.softuni.pathfinder.repository.CommentRepository;
import bg.softuni.pathfinder.repository.RouteRepository;
import bg.softuni.pathfinder.service.CommentService;
import bg.softuni.pathfinder.service.RouteService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final RouteRepository routeRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              RouteRepository routeRepository) {
        this.commentRepository = commentRepository;
        this.routeRepository = routeRepository;
    }


    public List<CommentEntity> getCommentsByRoute(Long routeID) {
        RouteEntity route = routeRepository.findById(routeID)
                .orElseThrow(() -> new IllegalArgumentException("Route not existing"));

        List<CommentEntity> comments = commentRepository
                .findAllByRoute(route).orElseThrow(() -> new IllegalArgumentException("Comments not existing"));

        return comments;
    }

    public CommentEntity createComment (CommentAddDTO commentAddDTO, Long routeId, String username) {

    }



}
