package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.entity.UserEntity;
import bg.softuni.pathfinder.model.service.UserServiceModel;

public interface UserService {
    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel findUserByUsernameAndPassword(String username, String password);

    void loginUser(Long id, String username);

    void logOutUser();


    UserServiceModel findById(Long id);


    boolean userAlreadyExist(String username, String email);

    UserEntity findCurrentLoginUserEntity();
}
