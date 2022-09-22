package org.example;

import java.util.List;

public interface UserRepo {
    List<AppUser> findAllUsers();
}
