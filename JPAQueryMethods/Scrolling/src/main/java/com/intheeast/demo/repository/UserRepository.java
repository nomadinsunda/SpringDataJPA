package com.intheeast.demo.repository;

import com.intheeast.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Offset 기반 스크롤링을 위한 메서드
    Page<User> findAll(Pageable pageable);
    
    Window<User> findFirst5ByAge(Integer age, OffsetScrollPosition position);


    // Keyset 기반 스크롤링을 위한 메서드
    // Age를 기준으로 정렬
    Window<User> findByAgeGreaterThanOrderByAgeAsc(Integer age, KeysetScrollPosition position);
}