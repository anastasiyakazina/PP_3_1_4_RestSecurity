package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.repositories.RoleRepo;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;

    @Autowired
    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }
    public Optional<Role> findById(Long id) {
        return roleRepo.findById(id);
    }

    public Set<Role> findAllRoles() {
        return new HashSet<>(roleRepo.findAll());
    }
}

