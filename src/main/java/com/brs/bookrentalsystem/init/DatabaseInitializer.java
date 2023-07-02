package com.brs.bookrentalsystem.init;

import com.brs.bookrentalsystem.auth.enums.RoleNames;
import com.brs.bookrentalsystem.auth.model.UserAccount;
import com.brs.bookrentalsystem.auth.repo.UserAccountRepo;
import com.brs.bookrentalsystem.auth.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserAccountRepo userAccountRepo;
    private final UserAccountService userAccountService;

    Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Override
    public void run(String... args) throws Exception {

        UserAccount userSuperAdmin = new UserAccount("super admin", "superAdmin@yopmail.com", "superadmin", RoleNames.ROLE_SUPER_ADMIN);
        UserAccount userAdmin = new UserAccount("admin", "admin@yopmail.com", "admin", RoleNames.ROLE_ADMIN);
        UserAccount userLibrarian = new UserAccount("librarian", "librarian@yopmail.com", "librarian", RoleNames.ROLE_LIBRARIAN);
        List<UserAccount> defaultUsers = List.of(userLibrarian, userAdmin, userSuperAdmin);
        userAccountService.registerDefaultUsers(defaultUsers);
        logger.info("Created default users : {} {} {}", userSuperAdmin.getUserName(), userAdmin.getUserName(), userLibrarian.getUserName());

    }
}
