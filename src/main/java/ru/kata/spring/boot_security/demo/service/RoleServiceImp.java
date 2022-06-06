package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoleServiceImp implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public void add(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role getById(int id) {
        return roleRepository.getById(id);
    }

    @Override
    public Set<Role> getRoles(int [] ides) {
        Set <Role> roles = new HashSet<>();
        for (int i = 0; i < ides.length; i++) {
            roles.add(roleRepository.getById(ides[i]));
        }
        return roles;
    }
}
