package com.intheeast.demo.repository;

import com.intheeast.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	interface NamesOnly {
        String getFirstName();
        String getLastName();
    }
	
	Collection<NamesOnly> findByLastName(String lastName);

	List<User> findByFirstName(String firstName);

    List<User> findByEmail(String email);

}
