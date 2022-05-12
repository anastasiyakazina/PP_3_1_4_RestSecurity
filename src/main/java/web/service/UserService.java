package web.service;


import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User findByEmail(String email);

    Optional<User> findById(Long id);

    List<User> findAll();

    void save(User user);

    void deleteById(Long id);

    void saveAndFlush(User user);
}

