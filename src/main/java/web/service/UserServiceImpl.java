package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;
import web.repositories.UserRepo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }


    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    public void saveAndFlush(User user) {
        if (user.getPassword().equals("")) {
            user.setPassword(userRepo.findById(user.getId()).get().getPassword());
        } else {
            if (!passwordEncoder.matches(passwordEncoder.encode(user.getPassword()), userRepo.findById(user.getId()).get().getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }
        userRepo.saveAndFlush(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = findByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), convRoles(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> convRoles(Collection<Role> roles) {
        return roles.stream().map(x -> new SimpleGrantedAuthority(x.getRole())).collect(Collectors.toList());
    }
}