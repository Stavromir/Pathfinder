package bg.softuni.pathfinder.service.impl;

import bg.softuni.pathfinder.model.entity.UserEntity;
import bg.softuni.pathfinder.model.entity.enums.LevelEnum;
import bg.softuni.pathfinder.model.service.UserServiceModel;
import bg.softuni.pathfinder.repository.UserRepository;
import bg.softuni.pathfinder.service.UserService;
import bg.softuni.pathfinder.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {

        UserEntity user = modelMapper.map(userServiceModel, UserEntity.class);
        user.setLevel(LevelEnum.BEGINNER);

        userRepository.save(user);

    }

    @Override
    public UserServiceModel findUserByUsernameAndPassword(String username, String password) {

        return userRepository
                .findByUsernameAndPassword(username, password)
                .map(userEntity -> modelMapper.map(userEntity, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public void loginUser(Long id, String username) {
        currentUser.setId(id);
        currentUser.setUsername(username);
    }

    @Override
    public void logOutUser() {
        currentUser.setUsername(null);
        currentUser.setId(null);
    }

    @Override
    public UserServiceModel findById(Long id) {

        return userRepository.findById(id)
                .map(userEntity -> modelMapper.map(userEntity, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public boolean userAlreadyExist(String username, String email) {

        return userRepository.findByUsername(username).isPresent() ||
                userRepository.findByEmail(email).isPresent();
    }

    @Override
    public UserEntity findCurrentLoginUserEntity() {
        return userRepository.findById(currentUser.getId())
                .orElse(null);
    }


}
