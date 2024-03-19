package bg.softuni.pathfinder.init;

import bg.softuni.pathfinder.model.entity.CommentEntity;
import bg.softuni.pathfinder.model.entity.RouteEntity;
import bg.softuni.pathfinder.model.entity.UserEntity;
import bg.softuni.pathfinder.repository.CommentRepository;
import bg.softuni.pathfinder.repository.RouteRepository;
import bg.softuni.pathfinder.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DBinit implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

    }


//    private final UserRepository userRepository;
//    private final RouteRepository routeRepository;
//    private final CommentRepository commentRepository;
//
//    public DBinit(UserRepository userRepository, RouteRepository routeRepository, CommentRepository commentRepository) {
//        this.userRepository = userRepository;
//        this.routeRepository = routeRepository;
//        this.commentRepository = commentRepository;
//    }
//
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        UserEntity user = userRepository.findById(2L).get();
//        RouteEntity route = routeRepository.findById(2L).get();
//
//        CommentEntity commentEntity = new CommentEntity()
//                .setApproved(true)
//                .setAuthor(user)
//                .setCreated(LocalDateTime.now())
//                .setRoute(route)
//                .setTextContent("loklslaldlaldallasllxlaslxlasl");
//
//        commentRepository.save(commentEntity);
//    }
}
