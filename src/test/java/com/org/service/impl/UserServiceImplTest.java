package com.org.service.impl;

import com.org.entity.User;
import com.org.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class UserServiceImplTest {

    private UserRepo userRepo = Mockito.mock(UserRepo.class);
    private UserServiceImpl userService = new UserServiceImpl(userRepo);

    @Test
    void testLoadUserByUsername_valid() {
        User mockUser = new User();

        String username = "RohitSharma";
        String hashedPass = "$2a$10$xiP7GRqR.CKJT8wwmPsJqefv3Q24g.eF8pht6QgVatYFk607jBdM.";

        mockUser.setUsername(username);
        mockUser.setPassword(hashedPass);
        mockUser.setRole("ADMIN");

        Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.of(mockUser));

        UserDetails ud = userService.loadUserByUsername(username);

        Assertions.assertEquals(username,ud.getUsername());
        Assertions.assertEquals(hashedPass,ud.getPassword());
        Mockito.verify(userRepo).findByUsername(username);
    }
}
