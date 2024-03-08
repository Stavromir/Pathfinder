package bg.softuni.pathfinder.service.impl;

import bg.softuni.pathfinder.model.entity.RoleEntity;
import bg.softuni.pathfinder.model.service.UserServiceModel;
import bg.softuni.pathfinder.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class PathfinderUserDetailServiceImpl implements UserDetailsService {

    private final UserService userService;

    public PathfinderUserDetailServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return map(userService.findByUsername(username));
    }

    private static UserDetails map(UserServiceModel userServiceModel) {
        return User
                .withUsername(userServiceModel.getUsername())
                .password(userServiceModel.getPassword())
                .authorities(userServiceModel.getRoles().stream()
                        .map(PathfinderUserDetailServiceImpl::map).toList())
                .build();
    }

    private static GrantedAuthority map(RoleEntity roleEntity) {
        return new SimpleGrantedAuthority("ROLE_" + roleEntity.getRole().name());
    }



}
