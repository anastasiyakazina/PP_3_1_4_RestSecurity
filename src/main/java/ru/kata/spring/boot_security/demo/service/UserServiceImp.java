package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import java.util.List;

@Service ("userServiceImp")
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public User add(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user, int id) {
        User existingUser = userRepository.findById(id).orElseThrow();
        existingUser.setName(user.getName());
        existingUser.setLastName(user.getLastName());
        existingUser.setAge(user.getAge());
        existingUser.setEmail(user.getEmail());
        existingUser.setRoles(user.getRoles());
        return userRepository.save(existingUser);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getById(int id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public User findByUsername(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);
        user.getAuthorities().size();
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Пользователь с email = '%s' не найден", email));
        }
        return user;
    }
}
