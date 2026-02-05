package com.intheeast.demo.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.intheeast.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    long countByLastname(String lastname);

    long deleteByEmail(String email); // 이메일로 삭제 메서드 추가

    List<User> removeByEmail(String email); // 이메일로 삭제 후 목록 리턴 메서드 추가

	Optional<User> findByEmail(String email);
	
    // 파생된 쿼리 메서드
    List<User> findByFirstnameStartingWith(String prefix);

    // SELECT u.id, u.first_name, u.last_name
    // FROM User u
    // WHERE u.last_name LIKE '%' || 'Smith' || '%'
    List<User> findByLastnameContaining(String substring);

    List<User> findByEmailNotContaining(String substring);	
    
    // 이메일 주소와 성을 기준으로 Person 엔티티 검색
    List<User> findByEmailAndLastname(String emailAddress, String lastname);

    // 성 또는 이름을 기준으로 중복을 제거한 User 리스트 검색
    List<User> findDistinctByLastnameOrFirstname(String lastname, String firstname);

    // 성을 기준으로 대소문자를 무시하고 User 리스트 검색
    List<User> findByLastnameIgnoreCase(String lastname);

    // 성과 이름을 기준으로 대소문자를 무시하고 User 리스트 검색
    List<User> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);

    // 성을 기준으로 이름을 오름차순으로 정렬하여 User 리스트 검색
    List<User> findByLastnameOrderByFirstnameAsc(String lastname);
    // 성을 기준으로 이름을 내림차순으로 정렬하여 User 리스트 검색
    List<User> findByLastnameOrderByFirstnameDesc(String lastname);
    
    // 특정 범위 내의 사용자 검색
    List<User> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // 특정 날짜 이전의 사용자 검색
    List<User> findByCreationDateLessThan(LocalDateTime date);

    // 이메일에 특정 문자열이 포함된 사용자 검색
    List<User> findByEmailContaining(String keyword);

}