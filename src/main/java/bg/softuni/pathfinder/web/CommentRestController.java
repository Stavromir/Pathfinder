package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.binding.CommentAddDTO;
import bg.softuni.pathfinder.model.entity.CommentEntity;
import bg.softuni.pathfinder.model.entity.UserEntity;
import bg.softuni.pathfinder.model.view.CommentViewModel;
import bg.softuni.pathfinder.repository.UserRepository;
import bg.softuni.pathfinder.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/{routeId}/comments/{commentId}")
    public ResponseEntity<CommentViewModel> getComment (@PathVariable("routeId") Long routeId,
                                                        @PathVariable("commentId") Long commentId) {

        CommentEntity commentById = commentService.getCommentById(commentId);

        CommentViewModel commentViewModel = getCommentViewModel(commentById);

        return ResponseEntity.ok(commentViewModel);
    }


    @GetMapping("/api/{routeId}/comments")
    public ResponseEntity<List<CommentViewModel>> getRouteComments(@PathVariable("routeId") Long routeId) {

        List<CommentViewModel> commentViewModels = commentService.getCommentsByRoute(routeId)
                .stream()
                .map(CommentRestController::getCommentViewModel).collect(Collectors.toList());

        return ResponseEntity.ok(commentViewModels);
    }

    @PostMapping(value = "/api/{routeId}/comments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentViewModel> createComment (@RequestBody CommentAddDTO commentAddDTO,
                                                           @PathVariable("routeId") Long routeId,
                                                           @AuthenticationPrincipal UserDetails userDetails,
                                                           UriComponentsBuilder uriComponentsBuilder) {

        System.out.println();

        CommentEntity comment = commentService
                .createComment(commentAddDTO, routeId, userDetails.getUsername());

        CommentViewModel commentViewModel = getCommentViewModel(comment);

        return ResponseEntity.created(
                uriComponentsBuilder.path("/api/{routeId}/comments/{commentId}").build(routeId, comment.getId())
        ).body(commentViewModel);
    }

    private static CommentViewModel getCommentViewModel(CommentEntity comment) {
        return new CommentViewModel()
                .setId(comment.getId())
                .setAuthorFullName(comment.getAuthor().getFullName())
                .setTextContent(comment.getTextContent())
                .setCreated(comment.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
    }
}




























