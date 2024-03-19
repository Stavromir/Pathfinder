package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.binding.CommentAddDTO;
import bg.softuni.pathfinder.model.entity.CommentEntity;

import java.util.List;

public interface CommentService {

    List<CommentEntity> getCommentsByRoute(Long routeId);

    public CommentEntity createComment (CommentAddDTO commentAddDTO, Long routeId, String username);
}
