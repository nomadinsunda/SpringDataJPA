package com.intheeast.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import com.intheeast.demo.dto.NamesOnlyDTO;
import com.intheeast.demo.entity.Person;

import java.util.Collection;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {

    // 인터페이스 기반 프로젝션
    interface NamesOnly {
        String getFirstname();
        String getLastname();
    }

    // 재귀적 프로젝션
    interface PersonSummary {
        String getFirstname();
        String getLastname();
        AddressSummary getAddress();

        interface AddressSummary {
            String getCity();
        }
    }

    // 성으로 검색하는 프로젝션 메서드
    Collection<NamesOnly> findByLastname(String lastname);

    // 이름으로 검색하는 재귀적 프로젝션 메서드
    Collection<PersonSummary> findByFirstname(String firstname);

    // DTO 기반 프로젝션 메서드
    Collection<NamesOnlyDTO> findByLastnameStartingWith(String prefix);

    // 동적 프로젝션 메서드
    <T> Collection<T> findByLastname(String lastname, Class<T> type);
}
