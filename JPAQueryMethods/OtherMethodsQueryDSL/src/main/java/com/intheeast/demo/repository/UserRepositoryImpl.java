package com.intheeast.demo.repository;

import com.intheeast.demo.entity.User;
import com.intheeast.demo.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.List;

@Primary
@Repository // 이 어노테이션을 추가하여 IoC 컨테이너에 빈으로 등록
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> customQueryMethod() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QUser user = QUser.user;

        return queryFactory.selectFrom(user)
                .where(user.age.gt(18)) // 나이가 18세 이상인 사용자 조회
                .fetch();
    }

}