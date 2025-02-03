package hexlet.code.service;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.dto.UserUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsManager {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void createUser(UserDetails userData) {
        createAndReturnUser((UserCreateDTO) userData);
    }

    public User createAndReturnUser(UserCreateDTO data) {
        var user = userMapper.map(data);
        return userRepository.save(user);
    }

    @Override
    public void updateUser(UserDetails userData) {
        // updateAndReturnUser((UserDTO) userData);
    }

    public User updateAndReturnUser(Long id, UserUpdateDTO data) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userMapper.update(data, user);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changePassword'");
    }

    @Override
    public boolean userExists(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'userExists'");
    }
}
