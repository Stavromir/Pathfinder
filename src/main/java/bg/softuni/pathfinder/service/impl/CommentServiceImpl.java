package bg.softuni.pathfinder.service.impl;

import bg.softuni.pathfinder.repository.CommentRepository;
import bg.softuni.pathfinder.service.CommentService;
import org.springframework.stereotype.Component;

@Component
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }



}
