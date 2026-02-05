package com.intheeast.demo.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intheeast.demo.entity.User;
import com.intheeast.demo.repository.UserRepository;
import com.intheeast.demo.repository.UserRepository.NamesOnly;
import org.hibernate.stat.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

@Service
public class UserService {
	
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	
	@Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserRepository userRepository;

    public List<User> findUsersByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public Collection<NamesOnly> findUsersByLastName(String lastName) {
        return measureQueryPerformance(lastName);/*userRepository.findByLastName(lastName);*/
    }

    public List<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
    
    public Collection<NamesOnly> measureQueryPerformance(String lastName) {
        Statistics stats = sessionFactory.getStatistics();
        stats.setStatisticsEnabled(true); // 통계 수집 활성화

        // 쿼리 실행
        Collection<NamesOnly> ret = userRepository.findByLastName(lastName);

        // 쿼리 성능 측정 결과 출력
        logger.info("Total queries executed: " + stats.getQueryExecutionCount());
        logger.info("Total time for queries: " + stats.getQueryExecutionMaxTime() + " ms");

//        System.out.println("Total queries executed: " + stats.getQueryExecutionCount());
//        System.out.println("Total time for queries: " + stats.getQueryExecutionMaxTime() + " ms");
//    
        return ret;
    }
}
