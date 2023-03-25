package com.github.yildizmy.repository;

import com.github.yildizmy.model.Role;
import com.github.yildizmy.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> getReferenceByTypeIsIn(Set<RoleType> types);
}
