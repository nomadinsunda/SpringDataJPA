package com.intheeast.demo.repository;

import com.intheeast.demo.entity.User;
import java.util.List;

public interface UserRepositoryCustom {
    List<User> customQueryMethod();
}