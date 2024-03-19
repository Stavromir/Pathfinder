package bg.softuni.pathfinder.repository;

import bg.softuni.pathfinder.model.entity.CommentEntity;
import bg.softuni.pathfinder.model.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Optional<CommentEntity>  findAllByRoute(RouteEntity route);
}
