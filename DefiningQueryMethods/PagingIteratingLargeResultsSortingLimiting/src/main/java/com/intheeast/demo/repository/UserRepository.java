package com.intheeast.demo.repository;

import com.intheeast.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // lastname을 기준으로 첫 10명의 User를 페이지 형태로 조회
    Page<User> queryFirst10ByLastname(String lastname, Pageable pageable);
    
    // lastname을 기준으로 상위 3명의 User를 Slice 형태로 조회
    Slice<User> findTop3ByLastname(String lastname, Pageable pageable);
    
    // lastname을 기준으로 첫 10명의 User를 Sort를 사용하여 리스트 형태로 조회
    List<User> findFirst10ByLastname(String lastname, Sort sort);
    
    // lastname을 기준으로 상위 10명의 User를 페이지 형태로 조회
    List<User> findTop10ByLastname(String lastname, Pageable pageable);
    
    List<User> findByLastname(String lastname, Sort sort, Limit limit);
}