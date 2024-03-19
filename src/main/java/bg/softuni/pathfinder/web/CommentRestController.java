package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.view.CommentViewModel;
import bg.softuni.pathfinder.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping("/api/{routeId}/comments")
    public ResponseEntity<List<CommentViewModel>> getRouteComments(@PathVariable("routeId") Long routeId) {
        List<CommentViewModel> commentViewModels = commentService.getCommentsByRoute(routeId)
                .stream()
                .map(comment -> {
                    CommentViewModel commentViewModel = new CommentViewModel()
                            .setId(comment.getId())
                            .setAuthorFullName(comment.getAuthor().getFullName())
                            .setTextContent(comment.getTextContent())
                            .setCreated(comment.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));

                    return commentViewModel;
                }).collect(Collectors.toList());

        return ResponseEntity.ok(commentViewModels);
    }
}
