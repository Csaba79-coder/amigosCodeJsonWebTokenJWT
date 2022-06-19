package com.csaba79coder.amigoscodejsonwebtokenjwt;

import com.csaba79coder.amigoscodejsonwebtokenjwt.domain.Role;
import com.csaba79coder.amigoscodejsonwebtokenjwt.domain.User;
import com.csaba79coder.amigoscodejsonwebtokenjwt.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class AmigosCodeJsonWebTokenJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmigosCodeJsonWebTokenJwtApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(Role.builder()
                    .name("ROLE_USER").build());
            userService.saveRole(Role.builder()
                    .name("ROLE_MANAGER").build());
            userService.saveRole(Role.builder()
                    .name("ROLE_ADMIN").build());
            userService.saveRole(Role.builder()
                    .name("ROLE_SUPER_ADMIN").build());

            userService.saveUser(User.builder()
                    .name("John Travolta")
                    .username("John")
                    .password("1234")
                    .roles(new ArrayList<>()).build());
            userService.saveUser(User.builder()
                    .name("Will Smith")
                    .username("Will")
                    .password("1234")
                    .roles(new ArrayList<>()).build());
            userService.saveUser(User.builder()
                    .name("Jim Carry")
                    .username("Jim")
                    .password("1234")
                    .roles(new ArrayList<>()).build());
            userService.saveUser(User.builder()
                    .name("Arnold Schwarzenegger")
                    .username("Arnold")
                    .password("1234")
                    .roles(new ArrayList<>()).build());

            userService.addRoleToUser("John", "ROLE_USER");
            userService.addRoleToUser("John", "ROLE_MANAGER");
            userService.addRoleToUser("Will", "ROLE_MANAGER");
            userService.addRoleToUser("Jim", "ROLE_ADMIN");
            userService.addRoleToUser("Arnold", "ROLE_ADMIN");
            userService.addRoleToUser("Arnold", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("Arnold", "ROLE_USER");
        };
    }
}
