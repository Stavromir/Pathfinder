package bg.softuni.pathfinder.service.impl;

import bg.softuni.pathfinder.model.entity.UserEntity;
import bg.softuni.pathfinder.model.entity.enums.LevelEnum;
import bg.softuni.pathfinder.model.service.UserServiceModel;
import bg.softuni.pathfinder.repository.UserRepository;
import bg.softuni.pathfinder.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {

        UserEntity user = modelMapper.map(userServiceModel, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
        user.setLevel(LevelEnum.BEGINNER);

        userRepository.save(user);

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
    public UserServiceModel findByUsername(String username) {

        return userRepository.findByUsername(username)
                .map(userEntity -> modelMapper.map(userEntity, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


}
