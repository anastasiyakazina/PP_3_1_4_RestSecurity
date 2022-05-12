package web.service;

import web.model.Role;
import web.repositories.RoleRepo;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface RoleService  {

    Optional<Role> findById(Long id);

    Set<Role> findAllRoles();
}
