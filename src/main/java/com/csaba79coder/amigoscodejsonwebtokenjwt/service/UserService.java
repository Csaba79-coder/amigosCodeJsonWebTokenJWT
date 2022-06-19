package com.csaba79coder.amigoscodejsonwebtokenjwt.service;

import com.csaba79coder.amigoscodejsonwebtokenjwt.domain.Role;
import com.csaba79coder.amigoscodejsonwebtokenjwt.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
