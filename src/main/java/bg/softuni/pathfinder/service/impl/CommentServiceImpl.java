package bg.softuni.pathfinder.service.impl;

import bg.softuni.pathfinder.model.binding.CommentAddDTO;
import bg.softuni.pathfinder.model.entity.CommentEntity;
import bg.softuni.pathfinder.model.entity.RouteEntity;
import bg.softuni.pathfinder.model.entity.UserEntity;
import bg.softuni.pathfinder.model.view.RouteDetailsViewModel;
import bg.softuni.pathfinder.repository.CommentRepository;
import bg.softuni.pathfinder.repository.RouteRepository;
import bg.softuni.pathfinder.repository.UserRepository;
import bg.softuni.pathfinder.service.CommentService;
import bg.softuni.pathfinder.service.RouteService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              RouteRepository routeRepository,
                              UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<CommentEntity> getCommentsByRoute(Long routeId) {

        RouteEntity route = getRouteEntity(routeId);

        List<CommentEntity> comments = commentRepository
                .findAllByRoute(route).orElse(null);

        return comments;
    }

    @Override
    public CommentEntity createComment (CommentAddDTO commentAddDTO, Long routeId, String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not exist"));

        RouteEntity route = getRouteEntity(routeId);


        CommentEntity comment = new CommentEntity()
                .setCreated(LocalDateTime.now())
                .setApproved(true)
                .setAuthor(user)
                .setTextContent(commentAddDTO.getTextContent())
                .setRoute(route);

        return commentRepository.save(comment);
    }

    @Override
    public CommentEntity getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not exist"));
    }

    private RouteEntity getRouteEntity(Long routeID) {
        return routeRepository.findById(routeID)
                .orElseThrow(() -> new IllegalArgumentException("Route not existing"));
    }
}
