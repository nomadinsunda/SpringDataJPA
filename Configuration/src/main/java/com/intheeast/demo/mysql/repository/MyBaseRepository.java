package com.intheeast.demo.mysql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean  // 아래 인터페이스는 런타임때 구현체를 만들지 마라.
public interface MyBaseRepository<T, ID> extends Repository<T, ID> {
    Optional<T> findById(ID id);
    
    <S extends T> S save(S entity);
    
    <S extends T> List<S> saveAll(Iterable<S> entities);
    
    List<T> findAll();
}