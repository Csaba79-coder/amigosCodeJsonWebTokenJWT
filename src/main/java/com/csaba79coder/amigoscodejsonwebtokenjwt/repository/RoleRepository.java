package com.csaba79coder.amigoscodejsonwebtokenjwt.repository;

import com.csaba79coder.amigoscodejsonwebtokenjwt.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
